package com.xgame.logic.server.core.net.process;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xgame.framework.network.server.Command;
import com.xgame.framework.network.server.CommandMeta;
import com.xgame.logic.server.core.system.LogicServer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CommandFactory implements ApplicationContextAware {
	private Map<Integer, CommandMeta> commandMetaInfo;
//	private ApplicationContext springContext;

	public CommandFactory() {
		commandMetaInfo = Maps.newHashMap();
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		Reflections ref = new Reflections(LogicServer.class.getPackage()
				.getName());
		Set<Class<?>> handlerTypes = ref
				.getTypesAnnotatedWith(MessageHandler.class);
		for (Class<?> handlerType : handlerTypes) {
			if (!Command.class.isAssignableFrom(handlerType)) {
				log.error("{} is not a command", handlerType);
				continue;
//				throw new RuntimeException(
//						"@MessageHandler can only be placed to Command class");
			}
			MessageHandler anno = handlerType.getAnnotation(MessageHandler.class);
			CommandMeta meta = new CommandMeta();
			meta.setMessageId(anno.value());
			meta.setType((Class<? extends Command>) handlerType);
			Type t = ((ParameterizedType) handlerType.getGenericSuperclass())
					.getActualTypeArguments()[0];
			try {
				meta.setDataType((Class<?>) t);
			} catch (Exception e) {
				log.error("command {} has unknown data type {} ", handlerType,
						t);
				throw new RuntimeException(
						"command factory init error, unknown data type " + t
								+ " for command " + handlerType);
			}
			commandMetaInfo.put(meta.getMessageId(), meta);
		}
		if (log.isInfoEnabled()) {
			List<Integer> keys = Lists.newArrayList(commandMetaInfo.keySet());
			Collections.sort(keys);
			for (Integer key : keys) {
				log.info("message {} -> {}", key, commandMetaInfo.get(key));
			}
		}
	}

//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public StatefulCommand getCommandAndParseData(int messageId, ByteBuf rawData)
//			throws UnknownMessageException {
//		CommandMeta meta = commandMetaInfo.get(messageId);
//		if (meta == null) {
//			throw new UnknownMessageException("message not found: " + messageId);
//		}
//		StatefulCommand cmd = (StatefulCommand) springContext.getBean(meta
//				.getType());
//		byte[] buffer = new byte[rawData.readableBytes()];
//		rawData.readBytes(buffer);
//		cmd.setMeta(meta);
//		if (meta.getDataType().equals(MsgNull.class)) {
//			cmd.setData(MsgNull.EMPTY);
//		} else {
//			cmd.setData(MSerializer.decode(buffer, meta.getDataType()));
//		}
//		return cmd;
//	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
//		this.springContext = applicationContext;
	}
}
