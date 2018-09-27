/**
 * Copyright (c) 2014, http://www.moloong.com/ All Rights Reserved. 
 * 
 */  
package com.moloong.messageGenerator.core.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Properties;

/**  
 * @Description:项目配置类
 * @author ye.yuan
 * @date 2011年4月11日 下午3:28:36  
 *
 */
public class ProjectConfig {

    
    /**配置文件路径*/
    private static final String DEFAULT_CONFIG_PATH = "projectConfig.properties";
    
    /**配置属性*/
    private static final Properties configs = new Properties();
    
//    private static final Properties configsProject = new Properties();
    
    public static String ROOT_PATH;
    
    public static String JAVA_REF_PROJECT;
    
    public static String GEN_PROJECT_NAME;
    
    /**配置文件中项目的"path(源代码路径)"键值*/
    final String pathKey;
    
    /**配置文件中项目的"generate(是否创建)"键值*/
    final String generateKey;
    
    /**配置文件中项目的"messagePoolClass(消息池类)"键值*/
    final String messagePoolClassKey;
    
    /**配置文件中项目的"message.xml(消息目录)文件路径"键值*/
    final String directoryXmlPathKey;
    
    private String genPath;
    
    static {
        initConfig();
    }
    
    /**
     * 初始化项目配置信息
    */
    public static void initConfig() {
        try {
//            String configPath = getConfigFilePath();
//            File configFile = new File(configPath);
//            InputStream inputStream;
//            if (!configFile.exists()) {
//                inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(DEFAULT_CONFIG_PATH);
//            }else {
//                inputStream = new FileInputStream(configPath);
//            }
//        	inputStream.close();
            try {
            	configs.load(new FileInputStream("./config/projectConfig.properties"));
            	ROOT_PATH = configs.getProperty("root.path");
            	JAVA_REF_PROJECT = configs.getProperty("java.ref.project").replace(".", ROOT_PATH);
            	GEN_PROJECT_NAME = configs.getProperty("gen.project.name");
    		} catch (FileNotFoundException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 保存配置,项目打成jar包后不能修改jar包中的配置，所以将配置保存在user.home/messageGenerator目录下
     * 在程序退出时保存用户更改的配置数据，下次打开程序时看到的是修改的配置
    */
    public static void saveConfig() {
        try {
            String configPath = getConfigFilePath();
            new File(configPath).getParentFile().mkdirs();
            FileOutputStream fos = new FileOutputStream(configPath);
            
            //将此 Properties 表中的属性列表（键和元素对）写入输出流
            configs.store(fos, "modiyfy config time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            
            //关闭输出流
            fos.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description:获取配置文件目录（包含了配置信息和模板配置信息）
     * @return
     */
    private static String getConfigDirPath() {
        
        return System.getProperty("user.home") + File.separator + "messageGenerator" + File.separator;
    }
    /**
     * @Description:获取项目配置文件路径
     * @return
     */
    private static String getConfigFilePath() {
        String configPath = getConfigDirPath() + DEFAULT_CONFIG_PATH;
        return configPath;
    }
    
    public ProjectConfig(String pathKey, String generateKey,
            String messagePoolClassKey, String directoryXmlPathKey) {
        
        this.pathKey = pathKey;
        this.generateKey = generateKey;
        this.messagePoolClassKey = messagePoolClassKey;
        this.directoryXmlPathKey = directoryXmlPathKey;
        String path = configs.getProperty(pathKey);
        if (path == null) {
            throw new NoSuchElementException();
        }
        genPath = path.replace(".", ROOT_PATH);
    }

    /**
     * 获取源代码path
     * @return
     */
    public String getPath() {
        return genPath;
    }

    /**
     * 修改path
     * @param path
     */
    public void setSrcPath(String path) {
        configs.setProperty(pathKey, path);
    }
    
    /**
     * 是否创建代码
     * @return
     */
    public Boolean isGenerate() {
        
        return Boolean.valueOf(configs.getProperty(generateKey, "true"));
    }
    
    /**
     * 修改是否创建代码
     * @param generate
     */
    public void setGenerate(Boolean generate) {
        
        if (generate == null) {
            throw new NullPointerException();
        }
        configs.setProperty(generateKey, generate.toString());
    }
    
    /**
     * 获取messagePoolClass类名
     * @return
     */
    public String getMessagePoolClass() {
        
        return configs.getProperty(messagePoolClassKey, "com.game.message.pool.MessagePool");
    }
    
    /**
     * message.xml消息目录文件
     * @return
     */
    public String getDirectoryXmlPath() {
        
        return configs.getProperty(directoryXmlPathKey, "/message.xml");
    }
    

    public String getGenerateKey() {
        return generateKey;
    }

    public String getMessagePoolClassKey() {
        return messagePoolClassKey;
    }

    public String getDirectoryXmlPathKey() {
        return directoryXmlPathKey;
    }

    public String getPathKey() {
        return pathKey;
    }

	public static Properties getConfigs() {
		return configs;
	}
    
}
