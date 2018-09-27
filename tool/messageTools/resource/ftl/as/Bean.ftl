<#import "Class.ftl" as Class>
package ${package}.bean{
	<#if imports??>
	<#list imports as import>
	import ${import};
	</#list>
	</#if>
	import net.Bean;
	
	/** 
	 * @author messageGenerator
	 * 
	 * @version 1.0.0
	 * 
	 * @since 2014-3-20
	 * 
	 * ${explain}
	 */
	public class ${className} extends Bean {
	
		<#list fields as field>
		<#if field.listType==1>
		//${field.explain}
		private var _${field.name}: Vector.<${.globals[field.className]!field.className}> = new Vector.<${.globals[field.className]!field.className}>();
		<#else>
		//${field.explain}
		private var _${field.name}: ${.globals[field.className]!field.className};
		
		</#if>
		</#list>
		/**
		 * 写入字节缓存
		 */
		override protected function writing(): Boolean{
			<#list fields as field>
			<#if field.listType==1>
			//${field.explain}
			writeShort(_${field.name}.length);
			for (var i: int = 0; i < _${field.name}.length; i++) {
				<#if field.className=="int">
				writeInt(_${field.name}[i]);
				<#elseif field.className=="short">
				writeShort(_${field.name}[i]);
				<#elseif field.className=="float">
				writeFloat(_${field.name}[i]);
				<#elseif field.className=="long">
				writeLong(_${field.name}[i]);
				<#elseif field.className=="byte">
				writeByte(_${field.name}[i]);
				<#elseif field.className=="String">
				writeString(_${field.name}[i]);
				<#else>
				writeBean(_${field.name}[i]);
				</#if>
			}
			<#else>
			//${field.explain}
			<#if field.className=="int">
			writeInt(_${field.name});
			<#elseif field.className=="short">
			writeShort(_${field.name});
			<#elseif field.className=="float">
			writeFloat(_${field.name});
			<#elseif field.className=="long">
			writeLong(_${field.name});
			<#elseif field.className=="byte">
			writeByte(_${field.name});
			<#elseif field.className=="String">
			writeString(_${field.name});
			<#else>
			writeBean(_${field.name});
			</#if>
			</#if>
			</#list>
			return true;
		}
		
		/**
		 * 读取字节缓存
		 */
		override protected function reading(): Boolean{
			<#list fields as field>
			<#if field.listType==1>
			//${field.explain}
			var ${field.name}_length : int = readShort();
			for (var i: int = 0; i < ${field.name}_length; i++) {
				<#if field.className=="int">
				_${field.name}[i] = readInt();
				<#elseif field.className=="short">
				_${field.name}[i] = readShort();
				<#elseif field.className=="float">
				_${field.name}[i] = readFloat();
				<#elseif field.className=="long">
				_${field.name}[i] = readLong();
				<#elseif field.className=="byte">
				_${field.name}[i] = readByte();
				<#elseif field.className=="String">
				_${field.name}[i] = readString();
				<#else>
				_${field.name}[i] = readBean(${field.className}) as ${field.className};
				</#if>
			}
			<#else>
			//${field.explain}
			<#if field.className=="int">
			_${field.name} = readInt();
			<#elseif field.className=="short">
			_${field.name} = readShort();
			<#elseif field.className=="float">
			_${field.name} = readFloat();
			<#elseif field.className=="long">
			_${field.name} = readLong();
			<#elseif field.className=="byte">
			_${field.name} = readByte();
			<#elseif field.className=="String">
			_${field.name} = readString();
			<#else>
			_${field.name} = readBean(${field.className}) as ${field.className};
			</#if>
			</#if>
			</#list>
			return true;
		}
		
		<#list fields as field>
		<#if field.listType==1>
		/**
		 * get ${field.explain}
		 * @return 
		 */
		public function get ${field.name}(): Vector.<${.globals[field.className]!field.className}>{
			return _${field.name};
		}
		
		/**
		 * set ${field.explain}
		 */
		public function set ${field.name}(value: Vector.<${.globals[field.className]!field.className}>): void{
			this._${field.name} = value;
		}
		
		<#else>
		/**
		 * get ${field.explain}
		 * @return 
		 */
		public function get ${field.name}(): ${.globals[field.className]!field.className}{
			return _${field.name};
		}
		
		/**
		 * set ${field.explain}
		 */
		public function set ${field.name}(value: ${.globals[field.className]!field.className}): void{
			this._${field.name} = value;
		}
		
		</#if>
		</#list>
	}
}