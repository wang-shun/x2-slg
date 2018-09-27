package com.xgame.config;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.xgame.utils.ClassUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2016-12-05 19:44:04
 */
@Slf4j
@Component
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ConfigSystem {

	public static final String suffix = ".txt";

	@Value("${xgame.config.path}")
	public String path;

	@Value("${xgame.world.server.config.path}")
	public String programConfigPath;

	@PostConstruct
	public void start() {
//		configMap = new HashMap<String, BasePriFactory>();
		readFile(new File(path));
	}

//	private Map<String, BasePriFactory> configMap;

	private void readFile(File file) {
		try {
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					readFile(files[i]);
				}
			} else {
				parse(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getConfigContent(String fileName, boolean isProgramCfg) {
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa"+fileName+"==================="+isProgramCfg);
		System.out.println("1111111111111111111111111"+fileName+"==================="+isProgramCfg);
		System.out.println("2222222222222222222222222"+fileName+"==================="+isProgramCfg);

		try {
			
			if (isProgramCfg) {
				fileName = programConfigPath + "/" + fileName + ".txt";
				log.error("programConfigPath:"+fileName);
			} else {
				fileName = path + "/" + fileName + ".txt";
				log.error("path:"+fileName);
			}
			
			byte[] fileBytes = Files.readAllBytes(new File(fileName).toPath());

			String fileStr = new String(fileBytes);	
			log.error("file size :{}", fileStr.length());
			return fileStr;

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return null;
	}

	public void parse(File file) {
		
		String pirName = file.getName().replace(".txt", "");
		String className = toClassName(pirName) + "PirFactory";
		try {
			Map<String, Class<BasePriFactory>> subClasses = ClassUtil.getSubClasses("com.xgame.config", BasePriFactory.class);
			Class<BasePriFactory> priClass = subClasses.get(className);
			if(priClass == null){
				System.out.print("");
			}
			BasePriFactory priFactory = (BasePriFactory) priClass.getMethod("getInstance").invoke(priClass);
			if (priFactory == null) {
				log.error(" config not find : " + file.getCanonicalPath());
				System.exit(-1);
				return;
			}
			Map factory = new HashMap<>();
			Method[] methods = priFactory.getClass().getDeclaredMethods();
			log.info("--------------------->load config " + file.getCanonicalPath());
			List<String> lines = Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));
			String[] fields = lines.get(1).split("\t");
			String[] fieldTypes = lines.get(2).split("\t");
			for (int i = 3; i < lines.size(); i++) {
				Object key = null;
				String[] values = lines.get(i).split("\t");
				BaseFilePri newPri = (BaseFilePri) priFactory.newPri();
				Map<String, String> fieldValues = new HashMap<>();
				for (int j = 0; j < fields.length; j++) {
					if (fields[j].equals("#"))
						continue;
					String field = (j < fields.length ? fields[j] : "").trim();
					String fieldType = (j < fieldTypes.length ? fieldTypes[j] : "").trim();
					String value = (j < values.length ? values[j] : "".trim());
					if (value.equals("") || value == null)
						continue;
					Field javaField =null;
					try{
						javaField = newPri.getClass().getDeclaredField(field);
					}catch(Exception e){
						javaField = newPri.getClass().getDeclaredField(field);
					}
					if (javaField == null) {
						log.error(" field not find, fieldName: " + field);
						System.exit(-1);
					}
					
					Object setValue = null;
					try {
						setValue = convert(fieldType, value);
					} catch(Exception e) {
						String s = String.format("字段类型转换出错表名:[%s],字段名:[%s]", file.getName(), field);
						log.error(s);
						throw new RuntimeException(s);
					}
					
					javaField.setAccessible(true);
					javaField.set(newPri, setValue);
					if (j == 0)
						key = setValue;
					if (setValue instanceof String && priFactory.verify((String) setValue)) {
						fieldValues.put(javaField.getName(), (String) setValue);
					}
				}
				if (key == null) {
					log.error(" mainKey not find, ClassName: " + newPri.getClass().getName());
					continue;
				}

				priFactory.init(newPri);
				for (int j = 0; j < methods.length; j++) {
					if (methods[j].isAnnotationPresent(ConfParse.class)) {
						ConfParse annotation = methods[j].getAnnotation(ConfParse.class);
						if (fieldValues.containsKey(annotation.value())) {
							String annovalue = null;
							try {
								annovalue = fieldValues.get(annotation.value());
								methods[j].invoke(priFactory, annovalue, newPri);
							} catch (Exception e) {
								log.error(String.format("pirName=%s, errorFiled=%s, errorValue=%s", pirName, annotation.value(), annovalue), e);
								System.exit(-1);
							}
						}
					}
				}
				priFactory.load(newPri);
				factory.put(key, newPri);
			}
			if (!factory.isEmpty()) {
				priFactory.factory = factory;
			}
			
			priFactory.loadOver(programConfigPath, factory);
//			configMap.put(pirName, priFactory);
		} catch (Exception e) {
			log.error(String.format("pirName=%s", pirName), e);
			System.exit(-1);
		}
	}

	public Object convert(String type, String value) {
		switch (type) {
		case "int": {
			if (value.equals("")) {
				return 0;
			} else {
				return Integer.valueOf(value);
			}
		}
		case "long": {
			if (value.equals("")) {
				return 0;
			} else {
				return Long.parseLong(value);
			}
		}
		case "str":
			return String.valueOf(value);
		case "f":
			if (value.equals("")) {
				return 0f;
			} else {
				return Double.valueOf(value);
			}
		}
		return value;
	}

	public String toClassName(String className) {
		return className.substring(0, 1).toUpperCase() + className.substring(1);
	}

//	public BasePriFactory getConfig(String configName) {
//		return this.configMap.get(configName);
//	}

}