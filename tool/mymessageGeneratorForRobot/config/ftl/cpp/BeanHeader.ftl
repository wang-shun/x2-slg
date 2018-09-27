#ifndef _SHELL_${className?upper_case}_BEAN_H_
#define _SHELL_${className?upper_case}_BEAN_H_

#include "Message.h"
<#if referenceHeaders??>
<#list referenceHeaders as referenceHeader>
#include ${referenceHeader}
</#list>
</#if>

class ${className} : public Bean
{
	public:
	<#list fields as field>
	<#if field.listType==1>
	//${field.explain}
	std::vector<${field.className}> ${field.name};
	<#else>
	//${field.explain}
	<#if field.className=="int">
	int32 ${field.name};
	<#elseif field.className=="short">
	int16 ${field.name};
	<#elseif field.className=="float">
	float ${field.name};
	<#elseif field.className=="long">
	int64 ${field.name};
	<#elseif field.className=="byte">
	int8 ${field.name};
	<#elseif field.className=="String">
	std::string ${field.name};
	<#else>
	${field.className}* ${field.name};
	</#if>
	</#if>
	</#list>
	
	bool read_from(ByteBuf& buf);
	bool write_to(ByteBuf& buf);
};

#endif
