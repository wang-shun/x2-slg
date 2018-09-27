package com;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.output.FileWriterWithEncoding;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 *
 *2016-7-20  20:17:02
 *@author ye.yuan
 *
 */
public class AppStart {
	
	
	Properties conf;
	
	List<AttributeBean> attributeList = new ArrayList<>();
	
	Configuration cfg;
	
	Configuration cfg1;

	public static void main(String[] args) {
		new AppStart().start();
	}
	
	public AppStart() {
		
	}
	
	public void start(){
		try {
			conf = new Properties();
			conf.load(new FileInputStream("./config/config.properties"));
			List<String>  list = Files.readAllLines(new File("./config/library.txt").toPath(), Charset.forName("UTF-8"));
			for(int i=2;i<list.size();i++){
				String[] split = list.get(i).split("\t");
				AttributeBean attributeBean = new AttributeBean(Integer.parseInt(split[0]), "long", split[1],split[2]);
				attributeList.add(attributeBean);
			}
			File dir1 = new File("./config/ftl/data");
			cfg1 = new Configuration();
			cfg1.setDirectoryForTemplateLoading(dir1);
			cfg1.setObjectWrapper(new DefaultObjectWrapper());
			outputFiles(dir1.listFiles(), dir1.getName(),cfg1);
			
			File dir2 = new File("./config/ftl/control");
			cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(dir2);
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			outputFiles(dir2.listFiles(), dir2.getName(),cfg);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void outputFiles(File [] files,String dir,Configuration cfg){
		for(int i=0;i<files.length;i++){
			if(files[i].isDirectory()){
				outputFiles(files[i].listFiles(),files[i].getName(),cfg);
				
			}else{
				String name = files[i].getName().split("\\.")[0];
				String substring2 = name.substring(1);
				String substring = name.substring(0,1);
				String upperCase = substring.toUpperCase();
				String className = upperCase+substring2;
				if(dir.equals("control")){
					output(files[i].getName(),(String)conf.get("control.url"),  className+".java",getDataModel(className),cfg);
				}else if(dir.equals("data")){
					output(files[i].getName(),(String)conf.get("data.url"),  className+".java",getDataModel(className),cfg);
				}
			}
		}
	}
	
	private void output(String ftlName,String outputPathDir,String javaFileName,Map<String, Object> dataModel,Configuration cfg){
		try {
			Template template = cfg.getTemplate(ftlName);
			if(template==null){
				template = cfg1.getTemplate(ftlName);
			}
			generateCodeFile(template, dataModel, outputPathDir, javaFileName);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
    public Map<String, Object> getDataModel(String className) {
        Map<String, Object> dataModel = new HashMap<String, Object>();
        dataModel.put("className", className);
        dataModel.put("fields", attributeList);
        return dataModel;
    }
    
    public Map<String, Object> getDataModel2() {
        Map<String, Object> dataModel = new HashMap<String, Object>();
        dataModel.put("fields", attributeList);
        return dataModel;
    }
    
    protected boolean generateCodeFile(Template template, Map<String, Object> dataModel, 
            String dir, String fileName) {
        //创建 package文件夹
        File dirFile = new File(dir);
        dirFile.mkdirs();
        //代码文件
        File codeFile = new File(dirFile, fileName);
        try {
            FileWriterWithEncoding codeFileWriter;
            codeFileWriter = new FileWriterWithEncoding(codeFile, StandardCharsets.UTF_8);
            template.process(dataModel, codeFileWriter);
            //关闭流
            codeFileWriter.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
	
	
}
