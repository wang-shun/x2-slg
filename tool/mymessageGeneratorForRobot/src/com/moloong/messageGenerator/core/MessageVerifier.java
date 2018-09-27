package com.moloong.messageGenerator.core;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.w3c.dom.Node;

import com.moloong.messageGenerator.bean.Message;
import com.moloong.messageGenerator.bean.messagedirectory.IMessageDirectory;
import com.moloong.messageGenerator.bean.messagedirectory.MessageDirectoryFactory;
import com.moloong.messageGenerator.bean.messagexml.IMessageXML;
import com.moloong.messageGenerator.core.project.Project;

/**  
 * @Description:消息校验器（读取所有的xml数据到内存，在内存中校验）   
 * @author ye.yuan
 * @date 2011年4月16日 下午6:11:33  
 *
 */
public enum MessageVerifier {

    INSTANCE;

    /**
     * 校验xml消息定义文件 
     * 1.每个xml文件中message的Id和name都必须唯一而且合法
     * 2.每个xml中定义的消息ID是否全在每个项目中全局唯一 
     * 3.每个xml消息ID是否在其他xml消息文件中存在
     * 
     * @param messageXMLs
     * @return
     */
    public boolean verify(List<IMessageXML> messageXMLs) {
    	
    	//检查项目路径存在否
        for (Project project : Project.values()) {
            if (project.getConfig().isGenerate() && !project.existPath()) {
                MessageDialog.openWarning(Display.getDefault().getActiveShell(), "警告", project.name() + "项目配置的项目路径不存在，请检查你的项目配置，再生成代码文件！");
                return false;
            }
        }
    	
        List<String> failureResults = new ArrayList<String>();
        
        /*
         * 校验新增的每个xml 每个xml文件中message的Id和name都必须唯一
         */
        for (int i = 0; i < messageXMLs.size(); i++) {

            List<Message> messages = messageXMLs.get(i).getMessages();
            for (int j = 0; j < messages.size(); j++) {
                // 被校验的message
                Message verifiedMessage = messages.get(j);
                for (int k = j + 1; k < messages.size(); k++) {
                    // 校验的message
                    Message verifyMessage = messages.get(k);
                    String filePath = messageXMLs.get(i).getXmlfile().getAbsolutePath();
                    // 消息ID定义不正确
                    if (verifyMessage.getId() <= 0) {
                        failureResults.add(filePath + "文件中消息Id:" + verifiedMessage.getId() + "定义不正确！");
                    }

                    // 消息Name定义不正确
                    if (verifyMessage.getMessageName() == null
                            || "".equalsIgnoreCase(verifyMessage.getMessageName().trim())) {
                        failureResults.add(filePath + "文件中消息名称:" + verifiedMessage.getId() + "定义不正确！");
                    }

                    // 类名重复
                    if (verifiedMessage.getMessageName().equalsIgnoreCase(verifyMessage.getMessageName())) {
                        failureResults.add(filePath + "文件中类:" + verifiedMessage.getMessageName() + "重复定义！");
                    }

                    // id重复
                    if (verifiedMessage.getId() == verifyMessage.getId()) {
                        failureResults.add(filePath + "文件中消息Id:" + verifiedMessage.getId() + "重复！");
                    }

                }
            }
        }

        /*
         * 校验新增的每个xml中定义的消息ID是否在每个需要创建代码项目中全局唯一
         */
        for (Project project : Project.values()) {
        	if (!project.getConfig().isGenerate()) {
				continue;
			}
            for (int i = 0; i < messageXMLs.size(); i++) {

                // 被校验的消息定义文件
                IMessageXML verifiedMessageXML = messageXMLs.get(i);

                for (int j = 0; j < verifiedMessageXML.getMessages().size(); j++) {

                    // 被校验的message
                    Message verifiedMessage = verifiedMessageXML.getMessages().get(j);
                    Node messageNode;

                    // ID是否已经在项目的message.xml中存
                    IMessageDirectory messageDirectory = MessageDirectoryFactory.INSTANCE.getMessageDirectory(project);
                    if ((messageNode = messageDirectory.getMessageNodes()
                            .get(Integer.valueOf(verifiedMessage.getId()))) != null) {
                        String name = messageNode.getAttributes().getNamedItem("name").getTextContent();

                        if (!name.equals(verifiedMessage.getMessageName())) {
                            failureResults.add(String.format("xml文件：%s\n\t消息编号：%d,\n\t消息名称:%s,已经在项目%s中的message.xml定义", verifiedMessageXML
                                    .getXmlfile().getName(), verifiedMessage.getId(), name, project.name()));
                        }
                    }

                    // ID是否在其他xml消息文件中存在
                    for (int k = i + 1; k < messageXMLs.size(); k++) {
                        // 校验的消息定义文件
                        IMessageXML verifyMessageXML = messageXMLs.get(k);

                        for (int l = 0; l < verifyMessageXML.getMessages().size(); l++) {
                            // 校验的message
                            Message verifyMessage = verifyMessageXML.getMessages().get(l);
                            if (verifiedMessage.getId() == verifyMessage.getId()) {
                                failureResults.add(String.format("xml文件（%s）和xml文件（%s）消息编号（%d）相同", verifiedMessageXML
                                        .getXmlfile().getName(), verifyMessageXML.getXmlfile().getName(),
                                        verifiedMessage.getId()));
                            }
                        }
                    }
                }
            }
        }

        boolean success = failureResults.size() < 1;
        if (!success) {
            StringBuilder failureResultsBuilder = new StringBuilder();
            for (String failureResult : failureResults) {
                failureResultsBuilder.append(failureResult + "\n");
            }
            failureResultsBuilder.append("xml消息定义文件有错误，请先修正xml后再生成文件！" + "\n");
            MessageDialog.openWarning(Display.getDefault().getActiveShell(), "警告", failureResultsBuilder.toString());
        }
        
        return success;
    }

}
