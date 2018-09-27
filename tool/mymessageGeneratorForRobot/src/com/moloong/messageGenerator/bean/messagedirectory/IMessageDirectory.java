package com.moloong.messageGenerator.bean.messagedirectory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

import com.moloong.messageGenerator.bean.Message;

/**  
 * @Description:和message.xml对应的消息目录对象，由MessageDirectoryFactory工厂类生成    
 * @author ye.yuan
 * @date 2011年4月15日 上午9:42:15  
 *
 */
public interface IMessageDirectory {

    /**获取所有的消息Node*/
    Map<Integer, Node> getMessageNodes();
    
    /**增加消息Node*/
    boolean addMessageNodes(List<Message> addedMessages);
    /**获取消息目录(message.xml)对象*/
    public File getDirectoryXmlFile();
    
    public List<Integer> PACKAGEIDS = new ArrayList<Integer>();
}
