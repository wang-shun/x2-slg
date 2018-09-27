package com.moloong.messageGenerator.bean.messagexml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.moloong.messageGenerator.bean.Bean;
import com.moloong.messageGenerator.bean.Field;
import com.moloong.messageGenerator.bean.Message;
import com.moloong.messageGenerator.bean.messagedirectory.IMessageDirectory;

/**  
 * @Description:MessageXML工厂类    
 * @author ye.yuan
 * @date 2011年4月16日 下午6:09:41  
 *
 */
public enum MessageXMLFactory {

    INSTANCE;
    
    /**
     * 创建MessageXML对象
     * @param xmlfile
     * @return
    */
    public IMessageXML create(File xmlfile) {
       return parse(xmlfile);
    }
    
    /**
     * 解析xml为MessageXML对象
     * @param xmlfile
     * @return
    */
    private MessageXML parse(File xmlfile) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(xmlfile);
            //读取beans
            Map<String, Bean> beans = readBeans(document);
            
            //读取messages
            List<Message> messages = readMessages(document, beans);
            messages.addAll(readCallback(document, beans));
            return new MessageXML(beans, messages, xmlfile);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        } catch (SAXException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 将xml定义的bean节点转化为bean对象
     * @param document
     * @param beans
    */
    private HashMap<String, Bean> readBeans(Document document) {
        HashMap<String, Bean> beans = new HashMap<String, Bean>();
        Node messagesNode = document.getElementsByTagName("protocols").item(0);
        if(messagesNode==null||messagesNode.getAttributes().getNamedItem("package")==null){
        	System.out.println(document.getNodeName());
        }
        //包名
        String packageName = messagesNode.getAttributes().getNamedItem("package").getTextContent();
        
        /*
         * 循环将xml中定义的pro节点专抓为pro对象
         */
        NodeList beanNodes = document.getElementsByTagName("pro");
        for (int i = 0; i < beanNodes.getLength(); i++) {
            Node beanNode = beanNodes.item(i);
            NamedNodeMap beanAttributes = beanNode.getAttributes();
            
            Bean bean = new Bean();
            bean.setBeanName(beanAttributes.getNamedItem("className").getTextContent());
            bean.setPackageName(packageName);
            bean.setExplain(beanAttributes.getNamedItem("explain").getTextContent());
            bean.setFields(new ArrayList<Field>());
            //C++引用的文件
            bean.setReferenceHeaders(new HashSet<String>());
            int index = 1;
            Node subNode = beanNode.getFirstChild();
            while (subNode != null) {
            	NamedNodeMap fieldAttributes = subNode.getAttributes();
            	if("import".equals(subNode.getNodeName())){
            		bean.getImports().add(fieldAttributes.getNamedItem("ref").getTextContent());
            	}
            	else if ("field".equals(subNode.getNodeName()) || "list".equals(subNode.getNodeName())) {
                    Field field = new Field();
                    field.setClassName(fieldAttributes.getNamedItem("type").getTextContent().trim());
                    field.setName(fieldAttributes.getNamedItem("name").getTextContent());
                    field.setExplain(fieldAttributes.getNamedItem("explain").getTextContent());
                    field.setId(index);
                    //设置listtype
                    if ("list".equals(subNode.getNodeName())) {
                        field.setListType(1);
                        //c++list的引用头文件
                        bean.getReferenceHeaders().add("<vector>");
                    } else {
                        field.setListType(0);
                    }
                    /*
                     * bean在引用bean时，只写的name，而不是全名，java和as在同一个包下不用引入包，c++需要
                     * referenceBeans是message引用的bean的fullname的list，用于生成message时的“import”
                     */
                    if (beans.containsKey(field.getClassName())) {
                        //生成c++引用的头文件
                        bean.getReferenceHeaders().add("\"" + field.getClassName() + ".h\"");
                    }
                    bean.getFields().add(field);
                    index++;
                }
            	else if("extends".equals(subNode.getNodeName())){
            		String textContent = fieldAttributes.getNamedItem("name").getTextContent();
            		String[] split = textContent.split("\\.");
            		bean.setExtendsName(split[split.length-1]);
            		bean.getImports().add(textContent);
                }
            	else if("implement".equals(subNode.getNodeName())){
            		bean.getImplementList().add(fieldAttributes.getNamedItem("name").getTextContent());
                }
                subNode = subNode.getNextSibling();
            }
            
            beans.put(bean.getBeanName(), bean);
        }
        
        return beans;
    }
    
    /**
     * 读取messages，将xml定义的message节点转换为message对象
     * @param document
     * @return 
    */
    private List<Message> readMessages(Document document, Map<String, Bean> beans) {
        List<Message> messages = new ArrayList<Message>();
        Node messagesNode = document.getElementsByTagName("protocols").item(0);
        //包名
        String packageName = messagesNode.getAttributes().getNamedItem("package").getTextContent();
        //消息的前缀ID
        String packageId = messagesNode.getAttributes().getNamedItem("id").getTextContent();
        IMessageDirectory.PACKAGEIDS.add(Integer.parseInt(packageId));
        NodeList messageNodes = document.getElementsByTagName("message");
        for (int i = 0; i < messageNodes.getLength(); i++) {
            Node messageNode = messageNodes.item(i);
            NamedNodeMap messageAttributes = messageNode.getAttributes();
            Message message = new Message();
            message.setPackageId(Integer.parseInt(packageId));
            message.setId(Integer.parseInt(packageId + messageAttributes.getNamedItem("id").getTextContent()));
            message.setFuncId(Integer.parseInt(packageId));
            message.setMsgId(Integer.parseInt(messageAttributes.getNamedItem("id").getTextContent()));
            message.setMessageName(messageAttributes.getNamedItem("className").getTextContent());
            message.setType(messageAttributes.getNamedItem("handlerEnum").getTextContent());
            message.setPackageName(packageName);
            message.setSync(Boolean.parseBoolean(messageAttributes.getNamedItem("sync").getTextContent()));
            message.setExplain(messageAttributes.getNamedItem("explain").getTextContent());
            //消息引用的bean
            message.setReferenceBeans(new HashSet<String>());
            message.setReferenceHeaders(new HashSet<String>()); 
            
            if (messageAttributes.getNamedItem("queue") != null) {
                message.setQueue(messageAttributes.getNamedItem("queue").getTextContent());
            }
            if (messageAttributes.getNamedItem("server") != null) {
                message.setServer(messageAttributes.getNamedItem("server").getTextContent());
            }
            if (messageAttributes.getNamedItem("commandEnum") != null) {
                message.setCommandEnum(messageAttributes.getNamedItem("commandEnum").getTextContent());
            }
            message.setFields(new ArrayList<Field>());
            int index = 1;
            Node subNode = messageNode.getFirstChild();
            while (subNode != null) {
            	NamedNodeMap fieldAttributes = subNode.getAttributes();
            	if("import".equals(subNode.getNodeName())){
            		message.getReferenceBeans().add(fieldAttributes.getNamedItem("ref").getTextContent());
            	}
            	else if ("field".equals(subNode.getNodeName()) || "list".equals(subNode.getNodeName())) {
                    Field field = new Field();
                    field.setClassName(fieldAttributes.getNamedItem("type").getTextContent().trim());
                    field.setName(fieldAttributes.getNamedItem("name").getTextContent());
                    field.setExplain(fieldAttributes.getNamedItem("explain").getTextContent());
                    field.setId(index);
                    if ("list".equals(subNode.getNodeName())) {
                        field.setListType(1);
                        //c++list的引用头文件
                        message.getReferenceHeaders().add("<vector>");
                    } else {
                        field.setListType(0);
                    }
                    
                    /*
                     * message在引用bean时，只写的name，而不是全名
                     * referenceBeans是message引用的bean的fullname的list，用于生成message时的“import”
                     */
                    if (beans.containsKey(field.getClassName())) {
                        Bean bean = (Bean) beans.get(field.getClassName());
                        message.getReferenceBeans().add(bean.getPackageName() + ".bean." + bean.getBeanName());
                        //生成c++引用的头文件
                        message.getReferenceHeaders().add("\"" + bean.getBeanName() + ".h\"");
                    }
                    message.getFields().add(field);
                    index++;
                }
            	else if("extends".equals(subNode.getNodeName())){
                	message.setExtendsName(fieldAttributes.getNamedItem("name").getTextContent());
                }
            	else if("implement".equals(subNode.getNodeName())){
            		message.getImplementList().add(fieldAttributes.getNamedItem("name").getTextContent());
                }
                subNode = subNode.getNextSibling();
            }
            messages.add(message);
        }
        return messages;
    }
    
    private List<Message> readCallback(Document document, Map<String, Bean> beans) {
    	List<Message> messages = new ArrayList<Message>();
        Node messagesNode = document.getElementsByTagName("protocols").item(0);
          //包名
        String packageName = messagesNode.getAttributes().getNamedItem("package").getTextContent();
          //消息的前缀ID
        String packageId = messagesNode.getAttributes().getNamedItem("id").getTextContent();
        NodeList messageNodes = document.getElementsByTagName("callback");
        Message resMessage = null;
        Message reqMessage = null;
        for (int i = 0; i < messageNodes.getLength(); i++) {
            Node messageNode = messageNodes.item(i);
            NamedNodeMap messageAttributes = messageNode.getAttributes();
            String sync = messageAttributes.getNamedItem("sync").getTextContent();
            String queue = messageAttributes.getNamedItem("queue").getTextContent();
            String commandEnum = messageAttributes.getNamedItem("commandEnum").getTextContent();
            String explain = messageAttributes.getNamedItem("explain").getTextContent();
            NodeList childNodes = messageNode.getChildNodes();
            int length = childNodes.getLength();
            for(int j=0;j<length;j++){
            	Node item = childNodes.item(j);
            	if(Node.ELEMENT_NODE==item.getNodeType()){
            		NamedNodeMap attributes = item.getAttributes();
            		Message message = new Message();
            		message.setId(Integer.parseInt(packageId + attributes.getNamedItem("id").getTextContent()));
            		message.setFuncId(Integer.parseInt(packageId));
                    message.setMsgId(Integer.parseInt(attributes.getNamedItem("id").getTextContent()));
            		message.setMessageName(attributes.getNamedItem("className").getTextContent());
            		message.setPackageName(packageName);
            		message.setSync(Boolean.parseBoolean(sync));
            		message.setExplain(explain);
            		message.setQueue(queue);
            		message.setCommandEnum(commandEnum);
            		message.setReferenceBeans(new HashSet<String>());
                    message.setReferenceHeaders(new HashSet<String>());
            		if(item.getNodeName().equals("req")){
            			message.setType("CS");
            			message.setMsgType(1);
            			reqMessage = message;
            		}
            		if(item.getNodeName().equals("res")){
            			message.setType("SC");
            			message.setMsgType(2);
            			resMessage = message;
            		}
            		message.setFields(new ArrayList<Field>());
            		int index = 1;
            		Node subNode = item.getFirstChild();
            		while (subNode != null) {
            			NamedNodeMap fieldAttributes = subNode.getAttributes();
            			if("import".equals(subNode.getNodeName())){
            				message.getReferenceBeans().add(fieldAttributes.getNamedItem("ref").getTextContent());
            			}
            			else if ("field".equals(subNode.getNodeName()) || "list".equals(subNode.getNodeName())) {
            				Field field = new Field();
            				field.setClassName(fieldAttributes.getNamedItem("type").getTextContent().trim());
            				field.setName(fieldAttributes.getNamedItem("name").getTextContent());
            				field.setExplain(fieldAttributes.getNamedItem("explain").getTextContent());
            				field.setId(index);
            				if ("list".equals(subNode.getNodeName())) {
            					field.setListType(1);
            					//c++list的引用头文件
            					message.getReferenceHeaders().add("<vector>");
            				} else {
            					field.setListType(0);
            				}
            				
            				/*
            				 * message在引用bean时，只写的name，而不是全名
            				 * referenceBeans是message引用的bean的fullname的list，用于生成message时的“import”
            				 */
            				if (beans.containsKey(field.getClassName())) {
            					Bean bean = (Bean) beans.get(field.getClassName());
            					message.getReferenceBeans().add(bean.getPackageName() + ".bean." + bean.getBeanName());
            					//生成c++引用的头文件
            					message.getReferenceHeaders().add("\"" + bean.getBeanName() + ".h\"");
            				}
            				message.getFields().add(field);
            				index++;
            			}
            			else if("extends".equals(subNode.getNodeName())){
            				message.setExtendsName(fieldAttributes.getNamedItem("name").getTextContent());
                        }
            			else if("implement".equals(subNode.getNodeName())){
            				message.getImplementList().add(fieldAttributes.getNamedItem("name").getTextContent());
                        }
            			subNode = subNode.getNextSibling();
            		}
            		messages.add(message);
            	}
            }
            reqMessage.setMsgName(resMessage);
        }
        return messages;
    }
    
    /**    
     *     
     * 项目名称：messageGenerator    
     * 类名称：MessageXML    
     * 类描述：IMessageXML接口默认实现    
     * 创建人：liushilong    
     * 创建时间：2011年3月23日 下午9:44:27    
     * 修改人：liushilong    
     * 修改时间：2011年3月23日 下午9:44:27    
     * 修改备注：    
     * @version     
     *     
     */
    private class MessageXML implements IMessageXML{
        
        /**xml中的beans*/
        private final Map<String, Bean> beans;
        /**xml中的messages*/
        private final List<Message> messages;
        private final File xmlfile;
        
        public MessageXML(Map<String, Bean> beans, List<Message> messages, File xmlfile) {
            this.beans = beans;
            this.messages = messages;
            this.xmlfile = xmlfile;
        }

        @Override
        public Map<String, Bean> getBeans() {
            return beans;
        }

        @Override
        public List<Message> getMessages() {
            return messages;
        }

        @Override
        public File getXmlfile() {
            return xmlfile;
        }

    }
}
