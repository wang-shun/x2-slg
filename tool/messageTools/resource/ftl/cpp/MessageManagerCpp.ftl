#include "MessageManager.h"
<#if catalogs??>
<#list catalogs as catalog>
#include "message/${catalog.name}Message.h"
</#list>
</#if>

__gnu_cxx::hash_map<int, Message*> MessageManager::id2message;

void MessageManager::init()
{
	<#if catalogs??>
	<#list catalogs as catalog>
	id2message[${catalog.id}] = new ${catalog.name}Message();
	</#list>  
	</#if>
}

Message* MessageManager::getMsg(int id)
{
  	return id2message[id];
}

