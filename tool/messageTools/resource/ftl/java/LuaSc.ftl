local this = GSToGC;

GSToGC = {
	<#list messages as message>
	<#if>
	--${message.explain}
	EmsgTocs_${message.messageName} = ${message.id},
	</#if>
	</#list>	
};

GSTOGC_MsgHandlerMap = {
	<#list messages as message>
	<#if>
	--${message.explain}
	[GSToGC.${message.messageName}] = GSCtrl_GameLogic_MsgHandler.onNetMsg_${message.id},
	</#if>
	</#list>
}