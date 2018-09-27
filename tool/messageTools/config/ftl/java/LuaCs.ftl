local this = GSToGS;

GCToGS = {
	<#list messages as message>
	--${message.explain}
	EmsgTocs_${message.messageName} = ${message.id?c},
	</#list>	
};
