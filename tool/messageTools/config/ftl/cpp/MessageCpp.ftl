#include "${className}Message.h"

/**
 * 读取字节缓存
 */
bool ${className}Message::read_from(ByteBuf& buf)
{
	<#list fields as field>
	<#if field.listType==1>
	//${field.explain}
	int ${field.name}_length = readInt32(buf);
	for (int i = 0; i < ${field.name}_length; i++) {
		<#if field.className=="int">
		${field.name}.push_back(readInt32(buf));
		<#elseif field.className=="short">
		${field.name}.push_back(readInt16(buf));
		<#elseif field.className=="float">
		${field.name}.push_back(readFloat(buf));
		<#elseif field.className=="long">
		${field.name}.push_back(readInt64(buf));
		<#elseif field.className=="byte">
		${field.name}.push_back(readInt8(buf));
		<#elseif field.className=="String">
		${field.name}.push_back(readString(buf, ${field.name}.at(i)));
		<#else>
		${field.name}.push_back(readBean(buf, ${field.name}.at(i)));
		</#if>
	}
	<#else>
	//${field.explain}
	<#if field.className=="int">
	${field.name} = readInt32(buf);
	<#elseif field.className=="short">
	${field.name} = readInt16(buf);
	<#elseif field.className=="float">
	${field.name} = readFloat(buf);
	<#elseif field.className=="long">
	${field.name} = readInt64(buf);
	<#elseif field.className=="byte">
	${field.name} = readInt8(buf);
	<#elseif field.className=="String">
	${field.name} = readString(buf, ${field.name});
	<#else>
	${field.name} = new ${field.className}();
	readBean(buf, *${field.name});
	</#if>
	</#if>
	</#list>
	
	return true;
}

/**
 * 写入字节缓存
 */
bool ${className}Message::write_to(ByteBuf& buf)
{
	<#list fields as field>
	<#if field.listType==1>
	//${field.explain}
	writeShort(buf, ${field.name}.size());
	for (int i = 0; i < ${field.name}.size(); i++) {
		<#if field.className=="int">
		writeInt32(buf, ${field.name}.at(i));
		<#elseif field.className=="short">
		writeInt16(buf, ${field.name}.at(i));
		<#elseif field.className=="float">
		writeFloat(buf, ${field.name}.at(i));
		<#elseif field.className=="long">
		writeInt64(buf, ${field.name}.at(i));
		<#elseif field.className=="byte">
		writeInt8(buf, ${field.name}.at(i));
		<#elseif field.className=="String">
		writeString(buf, ${field.name}.at(i));
		<#else>
		writeBean(buf, ${field.name}.at(i));
		</#if>
	}
	<#else>
	//${field.explain}
	<#if field.className=="int">
	writeInt32(buf, ${field.name});
	<#elseif field.className=="short">
	writeInt16(buf, ${field.name});
	<#elseif field.className=="float">
	writeFloat(buf, ${field.name});
	<#elseif field.className=="long">
	writeInt64(buf, ${field.name});
	<#elseif field.className=="byte">
	writeInt8(buf, ${field.name});
	<#elseif field.className=="String">
	writeString(buf, ${field.name});
	<#else>
	writeBean(buf, *${field.name});
	</#if>
	</#if>
	</#list>
	
	return true;
}

int ${className}Message::getId()
{
  	return ${messageId?c};
}

