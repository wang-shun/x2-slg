local this = GSToGS;

GCToGS = {
	<#list messages as message>
	<#if>
	--${message.explain}
	EmsgTocs_${message.messageName} = ${message.id},
	</#if>
	</#list>	
};
