package com.moloong.messageGenerator.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**  
 * @Description:模板配置
 * @author ye.yuan
 * @date 2011年4月15日 下午2:10:10  
 *
 */
public class TemplateConfig {

    private final static TemplateConfig instance;
    
    static {
        initConfigFiles();
        instance = new TemplateConfig();
    }
    
    public static TemplateConfig getInstance() {
       
        return instance;
    }
    
    /**模板配置*/
    private HashMap<CodeType, Configuration> templateConfigs = new HashMap<CodeType, Configuration>();
    
    /**java jar包模板路径*/
    private static final String DEFAULT_JAVATPLPATH = "/ftl/java/";
    /**as jar包模板路径*/
    private static final String DEFAULT_ASTPLPATH = "/ftl/as/";
    /**c++ jar包模板路径*/
    private static final String DEFAULT_CPPTPLPATH = "/ftl/cpp/";
    
    /**java 模板路径*/
    private static String JAVATPLPATH;
    /**as 模板路径*/
    private static String ASTPLPATH;
    /**c++ 模板路径*/
    private static String CPPTPLPATH;
    
    //初始化配置文件，将配置文件从jar包拷贝到user.home目录下，在jar包读取模板文件很麻烦
    public static void initConfigFiles() {
        JAVATPLPATH = getProjectConfDir() + DEFAULT_JAVATPLPATH;
        ASTPLPATH = getProjectConfDir() + DEFAULT_ASTPLPATH;
        CPPTPLPATH = getProjectConfDir() + DEFAULT_CPPTPLPATH;
        
        new File(JAVATPLPATH).mkdirs();
        new File(ASTPLPATH).mkdirs();
        new File(CPPTPLPATH).mkdirs();
        
        /*
         * 拷贝java模板文件
         */
//        copyTemplateFile(DEFAULT_JAVATPLPATH, "Bean.ftl", JAVATPLPATH);
//        copyTemplateFile(DEFAULT_JAVATPLPATH, "Class.ftl", JAVATPLPATH);
//        copyTemplateFile(DEFAULT_JAVATPLPATH, "Handler.ftl", JAVATPLPATH);
//        copyTemplateFile(DEFAULT_JAVATPLPATH, "Message.ftl", JAVATPLPATH);
//        copyTemplateFile(DEFAULT_JAVATPLPATH, "MessagePool.ftl", JAVATPLPATH);
//        copyTemplateFile(DEFAULT_JAVATPLPATH, "JobHandler.ftl", JAVATPLPATH);
        
        /*
         * 拷贝as模板文件
         */
//        copyTemplateFile(DEFAULT_ASTPLPATH, "Bean.ftl", ASTPLPATH);
//        copyTemplateFile(DEFAULT_ASTPLPATH, "Class.ftl", ASTPLPATH);
//        copyTemplateFile(DEFAULT_ASTPLPATH, "Handler.ftl", ASTPLPATH);
//        copyTemplateFile(DEFAULT_ASTPLPATH, "Message.ftl", ASTPLPATH);
//        copyTemplateFile(DEFAULT_ASTPLPATH, "MessagePool.ftl", ASTPLPATH);
//        copyTemplateFile(DEFAULT_ASTPLPATH, "JobHandler.ftl", ASTPLPATH);
        
        /*
         * 拷贝cpp模板文件
         */
//        copyTemplateFile(DEFAULT_CPPTPLPATH, "BeanCpp.ftl", CPPTPLPATH);
//        copyTemplateFile(DEFAULT_CPPTPLPATH, "BeanHeader.ftl", CPPTPLPATH);
//        copyTemplateFile(DEFAULT_CPPTPLPATH, "MessageCpp.ftl", CPPTPLPATH);
//        copyTemplateFile(DEFAULT_CPPTPLPATH, "MessageHeader.ftl", CPPTPLPATH);
//        copyTemplateFile(DEFAULT_CPPTPLPATH, "MessageManagerCpp.ftl", CPPTPLPATH);
//        copyTemplateFile(DEFAULT_CPPTPLPATH, "JobHandler.ftl", CPPTPLPATH);
    }
    
    /**
     * @Description:从jar包将模板文件拷贝user/home下，方便读取
     * @param templateDir
     * @param templateName
     * @param targetDir
     */
    public static void copyTemplateFile(String templateDir, String templateName, String  targetDir) {
        
        String templatePath = templateDir + templateName;
        File targetTemplateFile = new File(targetDir + templateName);
        try {
            InputStream templateFileInputStream = getInputStreamByJarTemplateFile(templatePath);
            FileUtils.copyInputStreamToFile(templateFileInputStream, targetTemplateFile);
            templateFileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @Description:根据jar包中的模板文件路径的输入流
     * @param templatePath
     * @return
     */
    private static InputStream getInputStreamByJarTemplateFile(String templatePath) {
        
        return TemplateConfig.class.getResourceAsStream(templatePath);
    }
    
    /**
     * @Description:获取配置文件目录（包含了配置信息和模板配置信息）
     * @return
     */
    public static String getConfigDirPath() {
        return System.getProperty("user.home") + File.separator + "messageGenerator" + File.separator;
    }
    
    public static String getProjectConfDir() {
        return "./config/";
    }
    
    private TemplateConfig(){
        //初始化java模板配置
        initConfig(CodeType.JAVA, getProjectConfDir() + DEFAULT_JAVATPLPATH);
        //初始化as模板配置
        initConfig(CodeType.AS, getProjectConfDir() + DEFAULT_ASTPLPATH);
        //初始化cpp模板配置
        initConfig(CodeType.CPP, getProjectConfDir() + DEFAULT_CPPTPLPATH);
    }
    
    /**
     * 初始化freemaker模板配置
     * @param codeType      代码类型
     * @param templateDir  模板目录
    */
    private void initConfig(CodeType codeType,String templateDir) {
        Configuration cfg = new Configuration();
        try {
            cfg.setDefaultEncoding("UTF-8");
            
            File templateDirFile = new File(templateDir);
            cfg.setDirectoryForTemplateLoading(templateDirFile);
            cfg.setObjectWrapper(new DefaultObjectWrapper());
            
            templateConfigs.put(codeType, cfg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 获取模板
     * @param codeType
     * @param templateName
     * @return
     * @throws IOException
    */
    public Template getTemplate(CodeType codeType, String templateName) 
            throws IOException {
        
        return templateConfigs.get(codeType).getTemplate(templateName);
    }
}
