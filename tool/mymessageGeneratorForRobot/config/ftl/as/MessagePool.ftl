package ${package} {
	
	import flash.utils.Dictionary;	<#if catalogs??>
	<#list catalogs as catalog>
	import ${catalog.message};
	import ${catalog.handler};
	</#list>
	</#if>
	

	/** 
	 * @author messageGenerator
	 * 
	 * @version 1.0.0
	 * 
	 * 引用类列表
	 */
	public class MessagePool {
		//消息类字典
		private var messages:Dictionary = new Dictionary();
		//处理类字典
		private var handlers:Dictionary = new Dictionary();
		
		public function MessagePool(){
			<#if catalogs??>
			<#list catalogs as catalog>
			register(${catalog.id}, ${catalog.name}Message, ${catalog.name}Handler);
			</#list>
			</#if>
		}
		
		private function register(id: int, messageClass: Class, handlerClass: Class): void{
			messages[id] = messageClass;
			handlers[id] = handlerClass;
		}
		
		public function getMessage(id: int) : Message{
			if(messages[id] == null) return null;
			else return new messages[id] as Message;
		}
		
		public function getHandler(id: int) : Handler{
			if(handlers[id] == null) return null;
			else return new handlers[id] as Handler;
		}
	}
}