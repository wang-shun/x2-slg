/**
 * Copyright (c) 2014, http://www.moloong.com/ All Rights Reserved. 
 * 
 */  
package com.moloong.messageGenerator.core.generator.imp;

import java.io.IOException;
import java.util.List;

import com.moloong.messageGenerator.bean.Bean;
import com.moloong.messageGenerator.bean.Message;
import com.moloong.messageGenerator.bean.messagedirectory.MessageDirectoryFactory;
import com.moloong.messageGenerator.bean.messagexml.IMessageXML;
import com.moloong.messageGenerator.core.CodeType;
import com.moloong.messageGenerator.core.Struct;
import com.moloong.messageGenerator.core.TemplateConfig;
import com.moloong.messageGenerator.core.generator.AbstractCodeGenerator;
import com.moloong.messageGenerator.core.generator.ICodeGenerator;
import com.moloong.messageGenerator.core.project.Project;

import freemarker.template.Template;

/**  
 * @Description:AS代码生成器
 * @author ye.yuan
 * @date 2011年4月12日 下午3:16:27  
 *
 */
public class ASCodeGenerator extends AbstractCodeGenerator implements ICodeGenerator {

    private static ASCodeGenerator instance = new ASCodeGenerator();
    public static ASCodeGenerator getInstance() {
        return instance;
    }
    
    /*
     * <p>Title: generateMessageFiles</p> 
     * <p>Description: </p> 
     * @param project
     * @param messageXML 
     * @see com.moloong.messageGenerator.core.generator.ICodeGenerator#generateMessageFiles(com.moloong.messageGenerator.core.project.Project, com.moloong.messageGenerator.bean.messagexml.IMessageXML) 
     */ 
    @Override
    public void generateMessageFiles(Project project, IMessageXML messageXML) {
        if (project.getCodeType() != CodeType.AS || !project.getConfig().isGenerate()) {
            return;
        }
        
        //生成bean
        for (Bean bean : messageXML.getBeans().values()) {
            generateBean(project, bean);
        }
        
        //生成message和handler
        for (Message message : messageXML.getMessages()) {
            if (project.getMessageTypes().contains(message.getType())) {
                generateMessage(project, message);
            }
            
            if (project.getHandlerTypes().contains(message.getType())) {
                generateHandler(project, message);
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
         * 先生成message.xml文件，messagepool类是根据message.xml生成的
         */
        //生成消息目录文件message.xml
        super.generateDirectoryXml(project, messages);
        //生成messagepool类
        super.generateMessagePool(project, MessageDirectoryFactory.INSTANCE.getMessageDirectory(project));
    }
    
    /**
     * @Description:生成bean
     * @param project
     * @param bean
     * @return
     */
    private boolean generateBean(Project project, Bean bean) {
        Template template;
        try {
            template = TemplateConfig.getInstance().getTemplate(CodeType.AS, "Bean.ftl");
            generateClassFile(project, template, bean.getDataModel(), Struct.BEAN);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    /**
     * @Description:生成message
     * @param project
     * @param message
     * @return
     */
    private boolean generateMessage(Project project, Message message) {
        Template template;
        try {
            template = TemplateConfig.getInstance().getTemplate(CodeType.AS, "Message.ftl");
            generateClassFile(project, template, message.dataModel(), Struct.MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * @Description:生成handler
     * @param project
     * @param message
     * @return
     */
    private boolean generateHandler(Project project, Message message) {
        Template template;
        try {
            template = TemplateConfig.getInstance().getTemplate(CodeType.AS, "Handler.ftl");
            generateClassFile(project, template, message.handlerDataModel(), Struct.HANDLER);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
