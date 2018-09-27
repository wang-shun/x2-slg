package ${packageName};

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.output.FileWriterWithEncoding;

import com.xgame.framework.config.LogbackConfigurer;
import com.xgame.logic.server.LogicBootstrap;
import com.xgame.logic.server.layer.utils.InjectorUtil;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 *
 *2016-10-12  20:39:10
 *@author ye.yuan
 *
 */
public enum AttributeEnum {

	
	<#list attributes as attribute>
	/**
	 *${attribute.see}
	 */
	<#if attribute.id2==0>
	${attribute.name}(${attribute.id}),
	<#else>
	${attribute.name}(${attribute.id},${attribute.id2}),
	</#if>
	</#list>
	;
	
	/**
	 * 属性id
	 */
	int  id;
	
	/**
	 * 关联的百分比属性id
	 */
	int  relationId;
	
	/**
	 * 全部属性枚举
	 */
	static Map<Integer, AttributeEnum> attributeEnums = new HashMap<>();
	
	/**
	 * 关联后的属性枚举   即传入 201 或 101  都将返回 101枚举    300的全在里面
	 */
	static Map<Integer, AttributeEnum> relationEnums = new HashMap<>();

	static{
		for(int i=0;i<values().length;i++){
			if(values()[i].id!=0&&values()[i].relationId!=0||values()[i].id>=300&&values()[i].id<400){
				relationEnums.put(values()[i].id,values()[i]);
				relationEnums.put(values()[i].relationId,values()[i]);
			}
			attributeEnums.put(values()[i].id, values()[i]);
		}
	}
	
	public static AttributeEnum valueOf(int attrId) {
		return attributeEnums.get(attrId);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public static void main(String[] args) {
		 FileWriterWithEncoding codeFileWriter = null;
		try {
			LogbackConfigurer.init();
			LogicBootstrap.initSpring();
			Map<Integer, String[]> map = new HashMap<>();
			File file = new File(InjectorUtil.getInjector().configSystem.path+"library.txt");
			List<String> lines = Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));
			for(int i=3;i<lines.size();i++){
				String [] values = lines.get(i).split("\t");
				map.put(Integer.parseInt(values[0]), values);
			}
			List<AttributeField> enumObjs = new ArrayList<>();
			for(int i = 100;i<200;i++){
				String[] values = map.get(i);
				if(values==null)continue;
				enumObjs.add(new AttributeField(values[1].toUpperCase(), values[2],Integer.parseInt(values[0]), i+100));
			}
			for(int i = 200;i<400;i++){
				String[] values = map.get(i);
				if(values==null)continue;
				enumObjs.add(new AttributeField(values[1].toUpperCase(), values[2],Integer.parseInt(values[0]), 0));
			}
			String packageName = AttributeEnum.class.getPackage().getName();
			Map<String, Object> dataModel = new HashMap<String, Object>();
			dataModel.put("attributes", enumObjs);
			dataModel.put("packageName", packageName);
			String src = AttributeEnum.class.getResource("/").getPath();
			File dir2 = new File(src+"/xgame/");
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(dir2);
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			Template template = cfg.getTemplate("AttributeEnum.ftl");
	        //代码文件
	        File codeFile = new File(src.replace("bin","src/main/java/"+packageName.replace(".", "/")),"AttributeEnum.java");
	        codeFileWriter = new FileWriterWithEncoding(codeFile, StandardCharsets.UTF_8);
	        template.process(dataModel, codeFileWriter);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
        	//关闭流
            try {
            	if(codeFileWriter!=null){
            		codeFileWriter.close();
            	}
			} catch (IOException e) {
				e.printStackTrace();
			}
            System.exit(-1);
        }
	}
	
	public static class AttributeField{
		public String name;
		public String see;
		public int id;
		public int id2;
		public AttributeField(String name,String see, int id, int id2) {
			this.name = name;
			this.see=see;
			this.id = id;
			this.id2 = id2;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getId2() {
			return id2;
		}
		public void setId2(int id2) {
			this.id2 = id2;
		}
		public String getSee() {
			return see;
		}
		public void setSee(String see) {
			this.see = see;
		}
	}
	
	public int getRelationId() {
		return relationId;
	}

}
