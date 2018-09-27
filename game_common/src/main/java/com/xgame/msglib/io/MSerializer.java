package com.xgame.msglib.io;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * Created by vyang on 6/17/16.
 */
public class MSerializer {

	public static <T> byte[] encode(T message) {
		Schema<T> schema = RuntimeSchema.getSchema((Class<T>) message.getClass());
		LinkedBuffer buffer = LinkedBuffer.allocate();
		return ProtobufIOUtil.toByteArray(message, schema, buffer);
	}

	public static <T> T decode(byte[] data, Class<T> clazz) {
		Schema<T> schema = RuntimeSchema.getSchema(clazz);
		T message = schema.newMessage();
		ProtobufIOUtil.mergeFrom(data, message, schema);
		return message;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> void xdecode(byte[] data, T xMessage) {
		Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(xMessage.getClass());
		ProtobufIOUtil.mergeFrom(data, xMessage, schema);
	}

}
