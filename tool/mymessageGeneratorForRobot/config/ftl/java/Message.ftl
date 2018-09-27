<#import "Class.ftl" as Class>
package ${package}.message;

<#if imports??>
<#list imports as import>
import ${import};
</#list>
</#if>
<#list fields as field>
<#if field.listType==1>
import java.util.List;
import java.util.ArrayList;
<#break>
</#if>
</#list>
<#if msg.msgType==2>
import com.xgame.msglib.able.MessageResCallbackable;
import com.xgame.msglib.ResMessage;
<#elseif msg.msgType==1>
import com.xgame.msglib.able.MessageReqCallbackable;
import com.xgame.msglib.ReqMessage;
<#elseif msg.type=="SC">
import com.xgame.msglib.ResMessage;
<#elseif msg.type=="CS">
import com.xgame.msglib.ReqMessage;
</#if>
import com.xgame.msglib.able.Communicationable;
/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * ${explain}消息
 */
<#if msg.msgType==2>
public class ${className}Message extends ResMessage implements MessageResCallbackable{
<#elseif msg.msgType==1>
public class ${className}Message extends ReqMessage implements MessageReqCallbackable{
<#elseif msg.type=="CS">
public class ${className}Message extends ReqMessage{
<#elseif msg.type=="SC">
public class ${className}Message extends ResMessage{
</#if>
	
	public static final int ID=${messageId?c};
	
	public static final int FUNCTION_ID=${funcId?c};
	
	public static final int MSG_ID=${msgId?c};
	
	<#list fields as field>
	<#if field.listType==1>
	/*${field.explain}*/
	@MsgField(Id = ${field.id})
	public List<${.globals[field.className]!field.className}> ${field.name} = new ArrayList<${.globals[field.className]!field.className}>();
	<#else>
	/*${field.explain}*/
	@MsgField(Id = ${field.id})
	public ${field.className} ${field.name};
	</#if>
	</#list>
	
	
	@Override
	public int getId() {
		return ID;
	}

	@Override
	public String getQueue() {
		<#if queue??>
		return "${queue}";
		<#else>
		return null;
		</#if>
	}
	
	@Override
	public String getServer() {
		<#if server??>
		return "${server}";
		<#else>
		return null;
		</#if>
	}
	
	@Override
	public boolean isSync() {
		return ${sync?c};
	}

	@Override
	public CommandEnum getCommandEnum() {
	    <#if commandEnum??>
		return Communicationable.CommandEnum.${commandEnum};
		<#else>
		return null;
		</#if>
	}

	@Override
	public HandlerEnum getHandlerEnum() {
		 <#if type??>
		return Communicationable.HandlerEnum.${type};
		<#else>
		return null;
		</#if>
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		<#list fields as field>
		<#if field.listType==1>
		//${field.explain}
		buf.append("${field.name}:{");
		for (int i = 0; i < ${field.name}.size(); i++) {
			<#if field.className=="int">
			buf.append(${field.name}.get(i) +",");
			<#elseif field.className=="short">
			buf.append(${field.name}.get(i) +",");
			<#elseif field.className=="float">
			buf.append(${field.name}.get(i) +",");
			<#elseif field.className=="long">
			buf.append(${field.name}.get(i) +",");
			<#elseif field.className=="byte">
			buf.append(${field.name}.get(i) +",");
			<#elseif field.className=="boolean">
			buf.append(${field.name}.get(i) +",");
			<#elseif field.className=="double">
			buf.append(${field.name}.get(i) +",");
			<#elseif field.className=="String">
			buf.append(${field.name}.get(i).toString() +",");
			<#else>
			buf.append(${field.name}.get(i).toString() +",");
			</#if>
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		<#else>
		//${field.explain}
		<#if field.className=="int">
		buf.append("${field.name}:" + ${field.name} +",");
		<#elseif field.className=="short">
		buf.append("${field.name}:" + ${field.name} +",");
		<#elseif field.className=="float">
		buf.append("${field.name}:" + ${field.name} +",");
		<#elseif field.className=="long">
		buf.append("${field.name}:" + ${field.name} +",");
		<#elseif field.className=="byte">
		buf.append("${field.name}:" + ${field.name} +",");
		<#elseif field.className=="boolean">
		buf.append("${field.name}:" + ${field.name} +",");
		<#elseif field.className=="double">
		buf.append("${field.name}:" + ${field.name} +",");
		<#elseif field.className=="String">
		if(this.${field.name}!=null) buf.append("${field.name}:" + ${field.name}.toString() +",");
		<#else>
		if(this.${field.name}!=null) buf.append("${field.name}:" + ${field.name}.toString() +",");
		</#if>
		</#if>
		</#list>
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}