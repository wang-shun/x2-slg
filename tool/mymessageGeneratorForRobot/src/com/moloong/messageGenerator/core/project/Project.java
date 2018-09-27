/**
 * Copyright (c) 2014, http://www.moloong.com/ All Rights Reserved. 
 * 
 */  
package com.moloong.messageGenerator.core.project;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.moloong.messageGenerator.core.CodeType;

/**  
 * @Description:项目枚举
 * @author ye.yuan
 * @date 2011年4月11日 下午3:29:51  
 *
 */
public enum Project {

    GAME("game.path", "game.generate", "game.messagePoolClass", "game.directoryXmlPath", CodeType.JAVA, 
            new String[]{"SC", "CS"}, new String[]{"CS"}),
    
    ROBOT("robot.path", "robot.generate", "robot.messagePoolClass", "robot.directoryXmlPath", CodeType.JAVA, 
            new String[]{"SC", "CS"}, new String[]{"SC", "CS"}),
   
    AS("as.path", "as.generate", "as.messagePoolClass", "as.directoryXmlPath", CodeType.AS, 
            new String[]{"SC", "CS"}, new String[]{"SC"}),
    
    CPP("cpp.path", "cpp.generate", "cpp.messagePoolClass", "cpp.directoryXmlPath", CodeType.CPP, 
            new String[]{"SC", "CS"}, new String[]{"SC"});
    
    /**项目配置*/
    final ProjectConfig config;
    
    /**代码类型*/
    final CodeType codeType;
    
    /**包含本项目的message类型*/
    final List<String> messageTypes;
    
    /**包含本项目的handler类型*/
    final List<String> handlerTypes;
    
    Project(String srcPathKey, String generateKey, String messagePoolClassKey, String directoryXmlPathKey, 
            CodeType codeType, String[] messageTypes, String[] handlerTypes) {
        
        this.config = new ProjectConfig(srcPathKey, generateKey, messagePoolClassKey, directoryXmlPathKey);
        
        this.codeType = codeType;
        this.messageTypes = Arrays.asList(messageTypes);
        this.handlerTypes = Arrays.asList(handlerTypes);
        
    }

    /**
     * 获取message.xml对应的file对象
     * @param project
     * @return
     */
    public File getDirectoryXmlFile() {

        String srcPath = config.getPath() + File.separator;
        File srcFile = new File(srcPath);
        if (!srcFile.exists() || !srcFile.isDirectory()) {
            srcFile.mkdirs();
        }
        return new File(config.getPath(), config.getDirectoryXmlPath());
    }
    
    /**
     * 项目的配置的目录是否存在
     * @return
    */
    public boolean existPath() {
        
        return new File(config.getPath()).exists();
    }
    
    public List<String> getMessageTypes() {
        return messageTypes;
    }

    public List<String> getHandlerTypes() {
        return handlerTypes;
    }

    public CodeType getCodeType() {
        return codeType;
    }

    public ProjectConfig getConfig() {
        return config;
    }
    
}
