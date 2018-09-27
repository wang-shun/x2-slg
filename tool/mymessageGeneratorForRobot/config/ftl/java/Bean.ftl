<#import "Class.ftl" as Class>
package ${package}.bean;

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

<#if extends=="XPro">
import com.xgame.msglib.XPro;
</#if>
import com.xgame.msglib.annotation.MsgField;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * ${explain}
 */
public class ${className} extends ${extends} {

	<#list fields as field>
	<#if field.listType==1>
	//${field.explain}
	@MsgField(Id = ${field.id})
	public List<${.globals[field.className]!field.className}> ${field.name} = new ArrayList<${.globals[field.className]!field.className}>();
	<#else>
	//${field.explain}
	@MsgField(Id = ${field.id})
	public ${field.className} ${field.name};
	
	</#if>
	</#list>
	
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
			buf.append(${field.name}.get(i) + ${field.name} +",");
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