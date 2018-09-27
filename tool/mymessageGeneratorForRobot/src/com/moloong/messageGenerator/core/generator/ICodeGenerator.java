package com.moloong.messageGenerator.core.generator;

import java.util.List;

import com.moloong.messageGenerator.bean.Message;
import com.moloong.messageGenerator.bean.messagexml.IMessageXML;
import com.moloong.messageGenerator.core.project.Project;

/**  
 * @Description:代码生成器接口
 * @author ye.yuan
 * @date 2011年4月12日 上午11:57:03  
 *
 */
public interface ICodeGenerator {

    /**
     * 根据xml消息定义对象和项目生成代码文件(包括bean，message,handler代码文件或者头文件等)
     * java，as：bean，message,handler
     * C++：bean,message的头文件和class文件
     * @param messageXML
     * @param project
     * @return
    */
    void generateMessageFiles(Project project, IMessageXML messageXML);
    
    /**
     * 生成目录管理文件
     * java，as：messages.xml和messagepool类
     * C++：messages.xml和messagemanage类
     * @param messages  当前所有xml中定义的消息对象
     * @return
     */
    void generateDirectoryFiles(Project project, List<Message> messages);
}
