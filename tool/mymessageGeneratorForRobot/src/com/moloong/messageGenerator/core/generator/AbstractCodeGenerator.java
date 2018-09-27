/**
 * Copyright (c) 2014, http://www.moloong.com/ All Rights Reserved. 
 * 
 */  
package com.moloong.messageGenerator.core.generator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.w3c.dom.Node;

import com.moloong.messageGenerator.bean.Message;
import com.moloong.messageGenerator.bean.messagedirectory.IMessageDirectory;
import com.moloong.messageGenerator.bean.messagedirectory.MessageDirectoryFactory;
import com.moloong.messageGenerator.core.Struct;
import com.moloong.messageGenerator.core.TemplateConfig;
import com.moloong.messageGenerator.core.project.Project;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**  
 * @Description:代码创建器的策略类
 * @author ye.yuan
 * @date 2011年4月12日 上午11:59:49  
 *
 */
public abstract class AbstractCodeGenerator {

    /**
     * @Description:生成代码文件
     * @param template  模板
     * @param dataModel 数据模型
     * @param dir       生成代码的目录
     * @param fileName  生成代码的文件名（带扩展名）
     * @return
     */
    protected boolean generateCodeFile(Template template, Map<String, Object> dataModel,String dir, String fileName) {
        //创建 package文件夹
        File dirFile = new File(dir);
        dirFile.mkdirs();
        
        //代码文件
        File codeFile = new File(dirFile, fileName);
        
        try {
            FileWriterWithEncoding codeFileWriter = new FileWriterWithEncoding(codeFile, StandardCharsets.UTF_8);
            template.process(dataModel, codeFileWriter);
            //关闭流
            codeFileWriter.close();
            
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (TemplateException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * @Description:生成类文件，主要是java和as类文件，C++的类要复写这个方法
     * @param project
     * @param template
     * @param dataModel
     * @param type
     * @return
     */
    public boolean generateClassFile(Project project, Template template, 
            Map<String, Object> dataModel, Struct type){
        //包路径
/*        String packagePath = project.getConfig().getPath() + File.separator + "src" + File.separator
                + (dataModel.get("package") + "").replaceAll("\\.", "\\\\");*/
        String packagePath = project.getConfig().getPath() + File.separator
                + (dataModel.get("package") + "").replaceAll("\\.", "\\\\");
        //类名
        String className = dataModel.get("className") + "";
        
        switch (type) {
            case MESSAGE:
                packagePath = packagePath + File.separator + type.getTypeName();
                className += "Message";
                break;
            case HANDLER:
                packagePath = packagePath + File.separator + type.getTypeName();
                className += "Handler";
                break;
//            case JOBHANDLER:
//                packagePath = packagePath + File.separator + type.getTypeName();
//                className += "JobHandler";
//                break;
            case BEAN:
                packagePath = packagePath + File.separator + type.getTypeName();
                break;
            case MESSAGEPOOL:
                break;
            default:
                break;
        }
        className = className + "." + project.getCodeType().getExtension();
        
        //类文件
        File classFile = new File(packagePath, className);
        //handler只新增不覆盖
        if (classFile.exists()) {
            if (type == Struct.HANDLER){// || type == Struct.JOBHANDLER ) {
                return true;
            }
        }
        
        return generateCodeFile(template, dataModel, packagePath, className);
    }
    
    /**
     * @Description:生成消息目录xml文件即/src/message.xml
     * @param project
     * @param messages
     */
    protected boolean generateDirectoryXml(Project project, List<Message> messages) {
        return MessageDirectoryFactory.INSTANCE.getMessageDirectory(project).addMessageNodes(messages);
    }
    
    /**
     * @Description:生成messagepool目录查找类
     * @param project
     * @param messageDirectory
     * @return
     */
    protected boolean generateMessagePool(Project project, IMessageDirectory messageDirectory) {
        // 类全名
        String fullClassName = project.getConfig().getMessagePoolClass();
        // 包名
        String packagePath = fullClassName.substring(0, fullClassName.lastIndexOf('.'));
        // 类名
        String className = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);

        try {
//        	Template template2 = TemplateConfig.getInstance().getTemplate(project.getCodeType(), "MessagePool.ftl");
            Template template = TemplateConfig.getInstance().getTemplate(project.getCodeType(), "MessagePool.ftl");
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
            generateClassFile(project, template, dataModel, Struct.MESSAGEPOOL);
        } catch (Exception e) {
            e.printStackTrace();
//            String messageStr = project.name() + ":" + className + "生成失败";
            return false;
        }
        return true;
    }
}
