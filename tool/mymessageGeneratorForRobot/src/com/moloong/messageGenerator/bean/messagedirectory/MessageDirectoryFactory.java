package com.moloong.messageGenerator.bean.messagedirectory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.moloong.messageGenerator.bean.Message;
import com.moloong.messageGenerator.core.project.Project;

/**  
 * @Description:生成IMessageDirectory类实例    
 * @author ye.yuan
 * @date 2011年4月16日 下午6:09:05  
 *
 */
public enum MessageDirectoryFactory {

    INSTANCE;
    
    /**IMessageDirectory的缓存*/
    private static final Map<Project, IMessageDirectory> messageDirectories = new ConcurrentHashMap<Project, IMessageDirectory>();
    
    /**
     * 创建和message.xml消息目录文件对应的MessageDirectory类
     * @param project
     * @return
    */
    public synchronized IMessageDirectory getMessageDirectory(Project project) {
        try {
        	//message.xml文件
            File directoryXml;
            //message.xml文件对应的目录对象
            IMessageDirectory messageDirectory;
            
            //存在则直接从缓存中取，不存在则创建directoryXml
            if (!(directoryXml = project.getDirectoryXmlFile()).exists() || directoryXml.isDirectory()) {
            	  try {
            	      //message.xml不存在则清除内存对象的缓存
                      messageDirectories.remove(project);
                      directoryXml.createNewFile();
                      FileWriter fileWriter = new FileWriter(directoryXml);
                      fileWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<messages>\n</messages>");
                      fileWriter.close();
                  } catch (IOException  e) {
                      e.printStackTrace();
                      return null;
                  }
            }
            
            //缓存存在则从缓存中取
            if ((messageDirectory = messageDirectories.get(project)) != null) {
				return messageDirectory;
			}
            
            /*
             * 解析xml为对象
             */
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(directoryXml);
            /*
             * 将message.xml中的节点转化为以消息Id为key的map对象
             */
            Map<Integer, Node> originalMessageNodes = new HashMap<Integer, Node>();
            NodeList nodes = document.getElementsByTagName("message");
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                //将node的属性转化为hashmap类型
                String id = node.getAttributes().getNamedItem("id").getTextContent();
                originalMessageNodes.put(Integer.parseInt(id), node);
            }
            
            messageDirectory = new MessageDirectory(project, originalMessageNodes);
            messageDirectories.put(project, messageDirectory);
            return messageDirectory;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (SAXException e) {
            e.printStackTrace();
            return null;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**    
     *     
     * 项目名称：messageGenerator    
     * 类名称：MessageDirectory    
     * 类描述：和message.xml对应的消息目录对象默认实现，一个项目一个MessageDirectory对象
     * 创建人：liushilong    
     * 创建时间：2011年3月24日 下午12:41:29    
     * 修改人：liushilong    
     * 修改时间：2011年3月24日 下午12:41:29    
     * 修改备注：    
     * @version     
     *     
     */
    private class MessageDirectory implements IMessageDirectory {

        private Project project;
        
        /**消息Id为key的map缓存对象*/
        private Map<Integer, Node> messageNodes = new ConcurrentHashMap<Integer, Node>();
        
        public MessageDirectory(Project project, Map<Integer, Node> messageNodes) {
            this.project = project;
            this.messageNodes = messageNodes;
        }

        @Override
        public Map<Integer, Node> getMessageNodes() {
            return messageNodes;
        }

        /**
         * 增加消息节点
         */
        @Override
        public boolean addMessageNodes(List<Message> addedMessages) {
        	try {
        	    File directoryXml;
                if ((directoryXml = project.getDirectoryXmlFile()) == null) {
                    throw new NullPointerException("message.xml文件不存在");
                }
        	     
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = builder.parse(directoryXml);
                Element root = document.getDocumentElement();
                
                for (Message addedMessage : addedMessages) {
                	/*
                	 * 项目的HandlerTypes包含消息的类型而且消息目录中不存在消息节点则新增消息节点
                	 */
                    if (project.getHandlerTypes().contains(addedMessage.getType()) && !messageNodes.containsKey(Integer.valueOf(addedMessage.getId()))) {
                    	Element element = document.createElement("message");
                        element.setAttribute("id", String.valueOf(addedMessage.getId()));
                        element.setAttribute("name", addedMessage.getMessageName());
                        element.setAttribute("message", addedMessage.getPackageName() + ".message." + addedMessage.getMessageName() + "Message");
                        element.setAttribute("handler", addedMessage.getPackageName() + ".handler." + addedMessage.getMessageName() + "Handler");
                        root.appendChild(element);
                        
                        messageNodes.put(Integer.valueOf(addedMessage.getId()), (Node)element);
                    }
                }
                
                /*
                 * 保存消息目录文件(message.xml)
                 */
                TransformerFactory transFactory = TransformerFactory.newInstance();
                Transformer transformer = transFactory.newTransformer();
                transformer.setOutputProperty("encoding", "UTF-8");
                transformer.setOutputProperty("indent", "yes");
                
                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult();
                FileOutputStream directoryXmlOS = new FileOutputStream(directoryXml);
                
                result.setOutputStream(directoryXmlOS);
                transformer.transform(source, result);
                
                directoryXmlOS.close();	
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
        	
        	return true;
        }

        /*
         * <p>Title: getDirectoryXmlFile</p> 
         * <p>Description: </p> 
         * @return 
         * @see com.moloong.messageGenerator.bean.messagedirectory.IMessageDirectory#getDirectoryXmlFile() 
         */ 
        @Override
        public File getDirectoryXmlFile() {
            return project.getDirectoryXmlFile();
        }
    }
    
}
