package com.xgame.config;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.xgame.common.AppConfig;


/**
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2016-12-05 19:44:04
 */
@Component
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ConfigSystem {
	
	private static final Logger log = LoggerFactory.getLogger(ConfigSystem.class);

	public static final String suffix = ".txt";
	
	public static final String[] resolveFileArr = {"building.txt","CXlanguage_ZhCN.txt","CXlanguage_ZhTW.txt","CHlanguage_ZhCN.txt","Chlanguage_ZhTW.txt"};
	
	public static final Set<String> resolveFileSet = new HashSet<String>(Arrays.asList(resolveFileArr));
	
	public String path = AppConfig.getProperty("xgame.config.path");
	
	public String programConfigPath = AppConfig.getProperty("xgame.world.server.config.path");

	@PostConstruct
	public void start() {
		readFile(new File(path));
	}

	private void readFile(File file) {
		try {
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					readFile(files[i]);
				}
			} else {
				if(resolveFileSet.contains(file.getName())){
					parse(file);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getConfigContent(String fileName, boolean isProgramCfg) {
		try {

			if (isProgramCfg) {
				fileName = programConfigPath + "/" + fileName + ".txt";
			} else {
				fileName = path + "/" + fileName + ".txt";
			}

			byte[] fileBytes = Files.readAllBytes(new File(fileName).toPath());

			String fileStr = new String(fileBytes);

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
			Map<String, Class<BasePriFactory>> subClasses = ClassUtil.getSubClasses("com.xgame.config",
					BasePriFactory.class);
			Class<BasePriFactory> priClass = subClasses.get(className);

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
					Field javaField = newPri.getClass().getDeclaredField(field);
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
								log.error(String.format("pirName=%s, errorFiled=%s, errorValue=%s", pirName,
										annotation.value(), annovalue), e);
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

}