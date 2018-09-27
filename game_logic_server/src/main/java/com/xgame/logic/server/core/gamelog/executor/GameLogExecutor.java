package com.xgame.logic.server.core.gamelog.executor;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.xgame.logic.server.core.gamelog.GameLogManager;
import com.xgame.logic.server.core.gamelog.logs.BaseLog;
import com.xgame.logic.server.core.gamelog.logs.ColumnInfo;
import com.xgame.logic.server.core.gamelog.logs.MetaData;
import com.xgame.logic.server.core.gamelog.logs.TableCompar;
import com.xgame.logic.server.core.gamelog.task.DBLogTask;
import com.xgame.logic.server.core.gamelog.task.FileLogTask;
import com.xgame.utils.ClassUtil;

/**
 * 日志异步执行器
 * @author jacky.jiang
 *
 */
@Component
@Slf4j
public class GameLogExecutor {

	/* static f */
//	private static final Logger logger = Logger.getLogger(GameLogExecutor.class);
	
	@Value("${logdbpool.drivers}")
	private String drivers;
	@Value("${logdbpool.url}")
	private String url;
	@Value("${logdbpool.user}")
	private String user;
	@Value("${logdbpool.password}")
	private String password;
	@Value("${logdbpool.validationquery}")
	private String validationquery;
	
	/**
	 * jdbc数据库控制器
	 */
	private ComboPooledDataSource ds;

	/**
	 * 插入sql的线程池
	 */
	private ThreadPoolExecutor dbexecutor;

	/**
	 * 以文件形式写入本地线程池
	 */
	private ThreadPoolExecutor fileexecutor;

	/**
	 * sql数据库队列
	 */
	private BlockingQueue<Runnable> dbqueue = new LinkedBlockingQueue<>();

	/**
	 * 文件缓存本地数据库队列
	 */
	private BlockingQueue<Runnable> filequeue = new LinkedBlockingQueue<>();

	/**
	 * 构造器初始化队列
	 */
	@PostConstruct
	public void init() {
		//数据库插入队列
		this.dbexecutor = new ThreadPoolExecutor(5, 8, 0L,
				TimeUnit.MILLISECONDS, this.dbqueue, new ThreadFactory() {
					public Thread newThread(Runnable r) {
						Thread thread = new Thread(r);
						thread.setName("gamelogThread");
						return thread;
					}
				});
		//文本队列
		this.fileexecutor = new ThreadPoolExecutor(1, 3, 0L, TimeUnit.MILLISECONDS, this.filequeue);
		try {
			this.ds = new ComboPooledDataSource();
			this.ds.setDriverClass(drivers);
			this.ds.setJdbcUrl(url);
			this.ds.setPassword(password); 
			this.ds.setUser(user);
			this.ds.setInitialPoolSize(10);
			this.ds.setAcquireIncrement(5);
			this.ds.setMinPoolSize(10);
			this.ds.setMaxPoolSize(30);
			this.ds.setMaxIdleTime(28800);
			this.ds.setCheckoutTimeout(120000);
			this.ds.setIdleConnectionTestPeriod(1800);
			this.ds.setPreferredTestQuery(validationquery);
			checkTable();
			log.info("启动日志线程池完毕");
		} catch (Exception ex) {
			log.error("初始化日志服务失败：", ex);
		}
		
		log.info("初始化日志数据库服务结束");
	}
	
	/**
	 * 异步提交线程队列执行日志插入  并触发 订阅者   订阅者是否同步根据订阅者监听信息走
	 * 基础提示,  自定义的参数BaseLog 只读,不能改变属性
	 * 
	 * @param bean
	 */
	public void execute(BaseLog bean) {
		int dbsize = this.dbexecutor.getQueue().size();
		int filesize = this.fileexecutor.getQueue().size();
		if (dbsize <= 80000) {
			this.dbexecutor.execute(new DBLogTask(bean, this.ds));
		} else if (filesize <= 20000) {
			this.fileexecutor.execute(new FileLogTask(bean));
		}
	}

	/**
	 * 检查表
	 * 
	 * @see 启动后把日志
	 */
	public void checkTable() {
		Collection<Class<BaseLog>> templateClasses = ClassUtil.getSubClasses(GameLogManager.class.getPackage().getName(), BaseLog.class).values();
		Set<Class<BaseLog>> subClasses = new HashSet<>();
		for (Class<BaseLog> class1 : templateClasses) {
			subClasses.addAll(ClassUtil.getSubClasses(class1.getPackage().getName(), class1).values());
		}
		Connection connection = null;
		try {
			connection = this.ds.getConnection();
			List<String> tableName = getTableName(connection);
			long currentTimeMillis = System.currentTimeMillis();
			for (Class<?> cls : subClasses)
				try {
					BaseLog bean = (BaseLog) cls.newInstance();
					bean.load(this);
					String buildTableName = bean.buildTableName(currentTimeMillis).toLowerCase();
					log.info("检测查表" + buildTableName);
					if (tableName.contains(buildTableName)) {
						List<ColumnInfo> columnDefine = getColumnDefine(connection, buildTableName);
						Iterator<ColumnInfo> iterator = columnDefine.iterator();
						while (iterator.hasNext()) {
							ColumnInfo next = (ColumnInfo) iterator.next();
							if (next.getName().equalsIgnoreCase("id")) {
								iterator.remove();
								break;
							}
						}
						HashMap<String, ColumnInfo> dbmatedata = new HashMap<>();
						for (ColumnInfo columnInfo : columnDefine) {
							dbmatedata.put(columnInfo.getName(), columnInfo);
						}
						List<ColumnInfo> codeDefine = new ArrayList<>();
						List<MetaData> metaDataSet = bean.getMetadata();
						for (MetaData md : metaDataSet) {
							codeDefine.add(md.toColumnInfo());
						}
						List<String> compartor = TableCompar.getInstance().compartor(buildTableName, codeDefine, columnDefine);
						if (compartor.size() > 0) {
							Statement createStatement = connection.createStatement();
							for (String string : compartor) {
								log.info("检查到变更" + string);
								createStatement.addBatch(string);
							}
							createStatement.executeBatch();
						}
					}
					log.info(buildTableName + "检查结束");
				} catch (Exception e) {
					log.error(cls.getName() + ":" + e.getMessage());
					e.printStackTrace();
				}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}	
			}
		}
	}

	/**
	 * 返回这个连接的已有表集合
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List<String> getTableName(Connection conn) throws SQLException {
		ResultSet tableRet = conn.getMetaData().getTables(null, null, null, null);
		List<String> tablenames = new ArrayList<>();
		while (tableRet.next()) {
			tablenames.add(tableRet.getString("TABLE_NAME"));
		}
		return tablenames;
	}

	/**
	 * 获得行信息
	 * @param conn
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public static List<ColumnInfo> getColumnDefine(Connection conn,String tableName) throws SQLException {
		DatabaseMetaData metaData = conn.getMetaData();
		ResultSet columns = metaData.getColumns(null, "%", tableName, "%");
		ResultSet primaryKey = metaData.getPrimaryKeys(null, "%", tableName);
		primaryKey.next();
		List<ColumnInfo> infos = new ArrayList<>();
		while (columns.next()) {
			ColumnInfo info = new ColumnInfo();
			info.setName(columns.getString("COLUMN_NAME").toLowerCase());
			info.setType(columns.getString("TYPE_NAME").toLowerCase());
			info.setSize(Integer.valueOf(columns.getInt("COLUMN_SIZE")));
			info.setNullable(Boolean.valueOf(columns.getBoolean("IS_NULLABLE")));
			info.setPrimary(primaryKey.getString(4));
			infos.add(info);
		}
		return infos;
	}

}
