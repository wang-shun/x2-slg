package com.xgame.logic.server.core.gamelog.task;

import java.sql.Connection;
import java.sql.Statement;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

import com.xgame.logic.server.core.gamelog.logs.BaseLog;

/**
 *	@author ye.yuan
 *	@date 2016-7-06  10:11:09
 *	@version 1.0
 */
@Slf4j
public class DBLogTask implements Runnable {
	
	
	/**
	 * 数据库连接元
	 */
	private DataSource ds;
	
	/**
	 * 本次使用bean
	 */
	private BaseLog bean;

	public DBLogTask(BaseLog bean, DataSource ds) {
		this.ds = ds;
		this.bean = bean;
	}

	public void run() {
		String buildSql = "";
		String buildCreateTableSql = "";
		Connection connection = null;
		Statement statement = null;
		try {
			buildSql = this.bean.buildInsertSql();
			connection = this.ds.getConnection();
			statement = connection.createStatement();
			try {
				statement.executeUpdate(buildSql);
			} catch (Exception e) {
				buildCreateTableSql = this.bean.buildCreateTableSql(this.bean.getTime().longValue());
				statement.execute(buildCreateTableSql);
				statement.executeUpdate(buildSql);
			}

			if (log.isDebugEnabled()) {
				log.debug(buildCreateTableSql);
				log.debug(buildSql);
			}
		} catch (Exception e) {
			log.error(this.bean.getClass().getName() + ":" + e.getMessage());
			log.error(e + ":" + buildCreateTableSql + "\n" + buildSql);
			this.bean.logToFile();
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (Exception e2) {
					log.error(e2.toString());
					e2.printStackTrace();
				}
			}
			if (connection != null)
				try {
					connection.close();
				} catch (Exception e) {
					log.error(e.toString());
					e.printStackTrace();
				}
		}
	}

	public BaseLog getBean() {
		return this.bean;
	}

	public void setBean(BaseLog bean) {
		this.bean = bean;
	}
}