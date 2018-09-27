package com.conf.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.output.FileWriterWithEncoding;

import com.conf.app.util.Kit;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 *
 *2016-12-02  13:54:36
 *@author ye.yuan
 *
 */
public class ConfigTool {
	
	/**
	 * 
	 */
	public final static ConfigTool configTool = new ConfigTool();
	
	public static String classPath;
	
	public static String packageParentName;
	
	public static String packageParentName2;
	
	public static Configuration cfgGener;
	
	public Map<String, ParseFileBean> parseFileBeans = new HashMap<>(); 
	
	Properties properties = new Properties();
	
	String [] configPath;
	
	public List<ParseFileBean> fileBeans = new ArrayList<>();

	public void load(){
		try {
			properties = new Properties();
			properties.load(new FileInputStream("./config/setup.properties"));
			configPath = properties.getProperty("configPath").split(";");
			classPath = properties.getProperty("classPath");
			packageParentName = properties.getProperty("packageParentName"); 
			packageParentName2 = packageParentName.replaceAll("\\\\", ".").substring(0,packageParentName.length()-1);
			cfgGener = new Configuration();
			cfgGener.setDirectoryForTemplateLoading(new File("./config/ftl"));
			cfgGener.setObjectWrapper(new DefaultObjectWrapper());
			parsePath();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void parsePath(){
		for(int i=0;i<configPath.length;i++){
			File file = new File(configPath[i]);
			readFile(file);
		}
//		genConfigSystem();
	}
	
	
	private void readFile(File file){
		try {
			if(file.isDirectory()){
				File[] files = file.listFiles();
				for(int i=0;i<files.length;i++){
					readFile(files[i]);
				}
			}else{
				ParseFileBean parseFileBean = new ParseFileBean(file);
				parseFileBeans.put(parseFileBean.getFileName(), parseFileBean);
				genFile(parseFileBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void genFile(ParseFileBean parseFileBean){
		try {
			Iterator<List<ConfBean>> iterator = parseFileBean.getConfs().iterator();
			while (iterator.hasNext()) {
				List<ConfBean> confBeans = iterator.next();
				String url = Kit.genUrl(parseFileBean).toString();
				String packages = url.toString().replaceAll("\\\\", ".");
				Map<String, Object> dataModel = new HashMap<>();
				dataModel.put("parseFileBean", parseFileBean);
				dataModel.put("confBeans", confBeans);
				dataModel.put("package", packages);
				//pir
				Template template = cfgGener.getTemplate("Pir.ftl");
				File dir = new File(classPath+url.toString());
				dir.mkdirs();
				File codeFile = new File(classPath,url+"\\"+parseFileBean.getFileName()+".java");
				FileWriterWithEncoding codeFileWriter = new FileWriterWithEncoding(codeFile, StandardCharsets.UTF_8);
				template.process(dataModel, codeFileWriter);
				
				//pirFactory
				codeFile = new File(classPath,url+"\\"+parseFileBean.getFileFactoryName()+".java");
				if(!codeFile.exists()){
					template = cfgGener.getTemplate("PirFactory.ftl");
					codeFileWriter = new FileWriterWithEncoding(codeFile, StandardCharsets.UTF_8);
					template.process(dataModel, codeFileWriter);
				}
			}
			fileBeans.add(parseFileBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
//	private void genConfigSystem(){
//		try {
//			Map<String, Object> dataModel = new HashMap<>();
//			dataModel.put("package1", ConfigTool.packageParentName2);
//			dataModel.put("parseFileBeans", fileBeans);
//			dataModel.put("xdate",Kit.dateStr(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
//			//ConfigSystem
//			Template template = cfgGener.getTemplate("ConfigSystem.ftl");
//			File codeFile = new File(classPath,ConfigTool.packageParentName+"ConfigSystem.java");
//			FileWriterWithEncoding codeFileWriter = new FileWriterWithEncoding(codeFile, StandardCharsets.UTF_8);
//			template.process(dataModel, codeFileWriter);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	
}
