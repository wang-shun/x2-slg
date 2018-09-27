local this = GSToGC;

GSToGC = {
	<#list messages as message>
	--${message.explain}
	EmsgTocs_${message.messageName} = ${message.id?c},
	</#list>	
};

GSTOGC_MsgHandlerMap = {
	<#list messages as message>
	--${message.explain}
	[GSToGC.EmsgTocs_${message.messageName}] = GSCtrl_GameLogic_MsgHandler.onNetMsg_${message.messageName},
	</#list>
}