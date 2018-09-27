package com.message;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.xgame.framework.config.LogbackConfigurer;
import com.xgame.logic.server.LogicBootstrap;
import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.msglib.ResMessage;
import com.xgame.msglib.XMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.utils.ClassUtil;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * 消息系统所有消息注册等处理类 2016-11-25 14:46:21
 *
 * @author ye.yuan
 *
 */
@Component
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MessageSystem implements ApplicationContextAware {

	/**
	 * 已注册的消息节点 每个节点包括 一个处理器(handler) 一个消息(ReqMessage)
	 */
	private static final Map<Integer, MessageNode<? extends XMessage>> nodes = new HashMap<>();

	public ApplicationContext applicationContext;

	/**
	 * 注册消息到项目下
	 */
	@PostConstruct
	public void start() {
		try {
			// 利用spring拿到handler
			Map<String, PlayerCommand> beans = applicationContext
					.getBeansOfType(PlayerCommand.class);
			Iterator<PlayerCommand> iterator = beans.values().iterator();
			while (iterator.hasNext()) {
				PlayerCommand playerCommand = (PlayerCommand) iterator.next();
				// 拿到类的泛型型参
				if (!(playerCommand.getClass().getGenericSuperclass() instanceof ParameterizedType))
					continue;
				ParameterizedType superclass = (ParameterizedType) playerCommand
						.getClass().getGenericSuperclass();
				// 有个handler 和 message 构建一个节点
				MessageNode node = new MessageNode(playerCommand.getClass(),
						(Class<?>) superclass.getActualTypeArguments()[0]);
				// 获取该请求消息的消息id 作为注册键 缓存入map
				try {
					Field field = node.getMessage().getField("ID");
					Object id = field.get(node.getMessage());
					if (id != null) {
						nodes.put((int) id, node);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 以消息id 获得一个节点
	 * 
	 * @param messageId
	 * @return
	 */
	public static MessageNode<? extends Communicationable> getMessageNode(
			int messageId) {
		return nodes.get(messageId);
	}

	/* 以下是生产枚举方法 项目运行中无需使用 */

	/**
	 * 生成messageEnum 枚举 这个每个开发者使用生产给自己看的
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		FileWriterWithEncoding codeFileWriter = null;
		try {
			LogbackConfigurer.init();
			LogicBootstrap.initSpring();
			// 生成req 和 handler
			List<MessageEnumObj> enumObjs = new ArrayList<MessageSystem.MessageEnumObj>(
					nodes.size());
			List<MessageEnumObj> enumObjs2 = new ArrayList<MessageSystem.MessageEnumObj>(
					nodes.size());
			Iterator<MessageNode<? extends XMessage>> iterator = nodes.values()
					.iterator();
			while (iterator.hasNext()) {
				MessageNode<? extends XMessage> next = iterator.next();
				ParameterizedType superclass = (ParameterizedType) next
						.getHandler().getGenericSuperclass();
				Class<?> messageClass = (Class<?>) superclass
						.getActualTypeArguments()[0];
				MessageEnumObj enumObj = new MessageEnumObj();
				Field msgIdField = messageClass.getField("ID");
				Field idField = messageClass.getField("MSG_ID");
				Field funcIdField = messageClass.getField("FUNCTION_ID");
				enumObj.funcId = (int) funcIdField.get(messageClass);
				enumObj.id = (int) idField.get(messageClass);
				enumObj.msgId = (int) msgIdField.get(messageClass);
				enumObj.type = 1;
				enumObj.handlerSimpleName = next.getHandler().getSimpleName();
				enumObj.messageSimpleName = messageClass.getSimpleName();
				enumObj.handlerName = next.getHandler().getName();
				enumObj.messageName = messageClass.getName();
				enumObjs.add(enumObj);
			}
			// 生成res
			Map<String, Class<ResMessage>> beansOfType = ClassUtil
					.getSubClasses("com.xgame.logic.server", ResMessage.class);
			Iterator<?> iterator2 = beansOfType.values().iterator();
			while (iterator2.hasNext()) {
				Class<?> messageClass = (Class<?>) iterator2.next();
				MessageEnumObj enumObj = new MessageEnumObj();
				Field msgIdField = messageClass.getField("ID");
				Field idField = messageClass.getField("MSG_ID");
				Field funcIdField = messageClass.getField("FUNCTION_ID");
				enumObj.funcId = (int) funcIdField.get(messageClass);
				enumObj.id = (int) idField.get(messageClass);
				enumObj.msgId = (int) msgIdField.get(messageClass);
				enumObj.type = 2;
				enumObj.messageSimpleName = messageClass.getSimpleName();
				enumObj.messageName = messageClass.getName();
				enumObjs2.add(enumObj);
			}
			// 根据消息id排序
			Collections.sort(enumObjs,
					Comparator.comparing(MessageEnumObj::getMsgId));
			Collections.sort(enumObjs2,
					Comparator.comparing(MessageEnumObj::getMsgId));
			Map<String, Object> dataModel = new HashMap<String, Object>();
			dataModel.put("fields", enumObjs);
			dataModel.put("fields2", enumObjs2);
			String src = MessageSystem.class.getResource("/").getPath();
			File dir2 = new File(src + "/xgame/");
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(dir2);
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			Template template = cfg.getTemplate("MessageEnum.ftl");
			// 代码文件
			File codeFile = new File(src.replace("bin",
					"src/main/java/com/message"), "MessageEnum.java");
			codeFileWriter = new FileWriterWithEncoding(codeFile,
					StandardCharsets.UTF_8);
			template.process(dataModel, codeFileWriter);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭流
			try {
				if (codeFileWriter != null) {
					codeFileWriter.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.exit(-1);
		}
	}

	public static class MessageEnumObj {
		private int msgId;
		private int funcId;
		private int id;
		private int type;
		private String handlerSimpleName;
		private String messageSimpleName;
		private String handlerName;
		private String messageName;

		public int getFuncId() {
			return funcId;
		}

		public void setFuncId(int funcId) {
			this.funcId = funcId;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public String getHandlerSimpleName() {
			return handlerSimpleName;
		}

		public void setHandlerSimpleName(String handlerSimpleName) {
			this.handlerSimpleName = handlerSimpleName;
		}

		public String getMessageSimpleName() {
			return messageSimpleName;
		}

		public void setMessageSimpleName(String messageSimpleName) {
			this.messageSimpleName = messageSimpleName;
		}

		public String getHandlerName() {
			return handlerName;
		}

		public void setHandlerName(String handlerName) {
			this.handlerName = handlerName;
		}

		public String getMessageName() {
			return messageName;
		}

		public void setMessageName(String messageName) {
			this.messageName = messageName;
		}

		public int getMsgId() {
			return msgId;
		}

		public void setMsgId(int msgId) {
			this.msgId = msgId;
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

}
