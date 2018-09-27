/**
 * Copyright (c) 2014, http://www.moloong.com/ All Rights Reserved. 
 * 
 */  
package com.moloong.messageGenerator.core.generator.imp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

import com.moloong.messageGenerator.bean.Bean;
import com.moloong.messageGenerator.bean.Message;
import com.moloong.messageGenerator.bean.messagedirectory.IMessageDirectory;
import com.moloong.messageGenerator.bean.messagedirectory.MessageDirectoryFactory;
import com.moloong.messageGenerator.bean.messagexml.IMessageXML;
import com.moloong.messageGenerator.core.CodeType;
import com.moloong.messageGenerator.core.TemplateConfig;
import com.moloong.messageGenerator.core.generator.AbstractCodeGenerator;
import com.moloong.messageGenerator.core.generator.ICodeGenerator;
import com.moloong.messageGenerator.core.project.Project;

import freemarker.template.Template;

/**  
 * @Description:
 * @author ye.yuan
 * @date 2011年4月12日 下午3:16:03  
 *
 */
public class CppCodeGenerator extends AbstractCodeGenerator implements ICodeGenerator {

    private static CppCodeGenerator instance = new CppCodeGenerator();
    public static CppCodeGenerator getInstance() {
        return instance;
    }
    
    private final static String CPP = ".cpp";
    private final static String HEADER = ".h";
    private final static String MESSAGE_PATH = File.separator + "message";
    /*
     * <p>Title: generateMessageFiles</p> 
     * <p>Description: </p> 
     * @param project
     * @param messageXML 
     * @see com.moloong.messageGenerator.core.generator.ICodeGenerator#generateMessageFiles(com.moloong.messageGenerator.core.project.Project, com.moloong.messageGenerator.bean.messagexml.IMessageXML) 
     */ 
    @Override
    public void generateMessageFiles(Project project, IMessageXML messageXML) {
        if (project.getCodeType() != CodeType.CPP || !project.getConfig().isGenerate()) {
            return;
        }
        
        //生成bean
        for (Bean bean : messageXML.getBeans().values()) {
            generateBean(project, bean);
            generateBeanHeader(project, bean);
        }
        
        //生成message和handler
        for (Message message : messageXML.getMessages()) {
            if (project.getMessageTypes().contains(message.getType())) {
                generateMessage(project, message);
                generateMessageHeader(project, message);
            }
        }
    }

    /*
     * <p>Title: generateDirectoryFiles</p> 
     * <p>Description: </p> 
     * @param project
     * @param messages 
     * @see com.moloong.messageGenerator.core.generator.ICodeGenerator#generateDirectoryFiles(com.moloong.messageGenerator.core.project.Project, java.util.List) 
     */ 
    @Override
    public void generateDirectoryFiles(Project project, List<Message> messages) {
        /*
         * 先生成message.xml文件，messagemanager类是根据message.xml生成的
         */
        //生成消息目录文件message.xml
        super.generateDirectoryXml(project, messages);
        //生成messagemanager类
        generateMessageManager(project, MessageDirectoryFactory.INSTANCE.getMessageDirectory(project));
    }

    /**
     * @Description:生成bean类
     * @param project
     * @param bean
     * @return
     */
    private boolean generateBean(Project project, Bean bean) {
        try {
            Template beanCppTemplate = TemplateConfig.getInstance().getTemplate(CodeType.CPP, "BeanCpp.ftl");
            String dir = project.getConfig().getPath() + MESSAGE_PATH;
            String fileName = bean.getBeanName() + CPP;
            
            super.generateCodeFile(beanCppTemplate, bean.getDataModel(), dir, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    /**
     * @Description:生成bean类的头文件
     * @param project
     * @param bean
     * @return
     */
    private boolean generateBeanHeader(Project project, Bean bean) {
        try {
            Template messageCppTemplate = TemplateConfig.getInstance().getTemplate(CodeType.CPP, "BeanHeader.ftl");
            String dir = project.getConfig().getPath() + MESSAGE_PATH;
            String fileName = bean.getBeanName() + HEADER;
            
            super.generateCodeFile(messageCppTemplate, bean.getDataModel(), dir, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    /**
     * @Description:生成message类
     * @param project
     * @param message
     * @return
     */
    private boolean generateMessage(Project project, Message message) {
        try {
            Template messageCppTemplate = TemplateConfig.getInstance().getTemplate(CodeType.CPP, "MessageCpp.ftl");
            String dir = project.getConfig().getPath() + MESSAGE_PATH;
            String fileName = message.getMessageName() + "Message" + CPP;
            
            super.generateCodeFile(messageCppTemplate, message.dataModel(), dir, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    /**
     * @Description:生成message头文件
     * @param project
     * @param message
     * @return
     */
    private boolean generateMessageHeader(Project project, Message message) {
        try {
            Template messageCppTemplate = TemplateConfig.getInstance().getTemplate(CodeType.CPP, "MessageHeader.ftl");
            String dir = project.getConfig().getPath() + MESSAGE_PATH;
            String fileName = message.getMessageName() + "Message" + HEADER;
            
            super.generateCodeFile(messageCppTemplate, message.dataModel(), dir, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    /**
     * @Description:生成messagemanager管理类
     * @param project
     * @param messageDirectory
     * @return
     */
    private boolean generateMessageManager(Project project, IMessageDirectory messageDirectory) {
        // 类全名
        String fullClassName = project.getConfig().getMessagePoolClass();
        // 包名
        String packagePath = fullClassName.substring(0, fullClassName.lastIndexOf('.'));
        // 类名
        String className = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);

        Template template = null;
        try {
            template = TemplateConfig.getInstance().getTemplate(project.getCodeType(), "MessageManagerCpp.ftl");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, Object> dataModel = new HashMap<String, Object>();
        dataModel.put("package", packagePath);
        dataModel.put("className", className);
        
        List<Map<String, String>> messageNodes = new ArrayList<Map<String, String>>();
        for (Node node : messageDirectory.getMessageNodes().values()) {
            Map<String, String> messageNode = new HashMap<String, String>();
            messageNode.put("id", node.getAttributes().getNamedItem("id").getTextContent());
            messageNode.put("name", node.getAttributes().getNamedItem("name").getTextContent());
            messageNode.put("message", node.getAttributes().getNamedItem("message").getTextContent());
            messageNode.put("handler", node.getAttributes().getNamedItem("handler").getTextContent());
            
            messageNodes.add(messageNode);
        }
        dataModel.put("catalogs", messageNodes);
        
        String dir = project.getConfig().getPath();
        String fileName = "MessageManager" + CPP;
        return super.generateCodeFile(template, dataModel, dir, fileName);
    }
}
