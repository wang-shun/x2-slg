package ${package}.handler{

	import ${package}.message.${className}Message;
	import net.Handler;

	public class ${className}Handler extends Handler {
	
		public override function action(): void{
			var msg: ${className}Message = ${className}Message(this.message);
			//TODO 添加消息处理
		}
	}
}