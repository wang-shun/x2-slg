package com.moloong.messageGenerator.core.generator.imp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

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
import com.moloong.messageGenerator.core.project.ProjectConfig;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**  
 * @Description:java代码生成器
 * @author ye.yuan
 * @date 2011年4月12日 下午2:34:15  
 *
 */
public class JavaCodeGenerator extends AbstractCodeGenerator implements ICodeGenerator {
	
	Configuration cfg;
	
    private static JavaCodeGenerator instance = new JavaCodeGenerator();
    public static JavaCodeGenerator getInstance() {
        return instance;
    }
    
	private JavaCodeGenerator() {
    	cfg = new Configuration();
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
        if (project.getCodeType() != CodeType.JAVA || !project.getConfig().isGenerate()) {
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
//        super.generateDirectoryXml(project, messages);
//        //生成messagepool类
//        super.generateMessagePool(project, MessageDirectoryFactory.INSTANCE.getMessageDirectory(project));
//        generateIdFile(project);   
    }
    
    protected void generateIdFile(Project project) {
    	
    	
//    	try {
//    		StringBuffer buffer = new StringBuffer();
//    		buffer.append("\""+System.getProperty("java.home")+"\\bin\\java.exe\" -cp ").append(ProjectConfig.JAVA_REF_PROJECT);
//			File file = new File(ProjectConfig.ROOT_PATH+"\\"+ProjectConfig.GEN_PROJECT_NAME+"\\.classpath");
//			SAXReader reader = new SAXReader();
//			Document doc = reader.read(file);
//			Element element = doc.getRootElement();
//			Iterator<?> elementIterator = element.elementIterator("classpathentry");
//			while (elementIterator.hasNext()) {
//				Element el = (Element) elementIterator.next();
//				Attribute attribute = el.attribute("path");
////				System.out.println(attribute.getValue());
//				String value = attribute.getValue();
//				if(!value.endsWith(".jar"))continue;
//				value = value.replace("/", "\\");
//				buffer.append(";");
//				if(value.startsWith("\\")){
//					buffer.append(ProjectConfig.ROOT_PATH+value);
//				}else{
//					buffer.append(value);
//				}
//			}
//			buffer.append(" com.message.MessageSystem");
//			System.out.println(buffer.toString());
//			try {
//				Process exec = Runtime.getRuntime().exec(buffer.toString());
//				exec.waitFor();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			System.out.println();
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		}
    	
//    	Integer[] array = IMessageDirectory.PACKAGEIDS.toArray(new Integer[0]);
//    	Arrays.sort(array);
//    	String path = project.getConfig().getPath();
        // 包名
//        String dir = path + File.separator
//                 + project.getConfig().getMessagePoolClass().substring(0, project.getConfig().getMessagePoolClass().lastIndexOf('.')).replaceAll("\\.", "\\\\");
//        String name = dir+File.separator+"MsgFunction.properties";
//        Properties properties = new Properties();
//        InputStream fis;
//		try {
//			File dirFile = new File(name);
//			dirFile.delete();
//        	dirFile.createNewFile();
//			fis = new FileInputStream(name);
//	        properties.load(fis);
//	        OutputStream fos = new FileOutputStream(name);
//	        for(int i=0;i<array.length;i++){
//	        	System.out.println(array[i].hashCode());
//	        	properties.setProperty(array[i]+"", array[i]+"");
//	        }
//	        properties.store(fos, " Updata ");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//        String msgFunction = readMsgFunction(new File(name));
//        //创建 package文件夹
//        File dirFile = new File(dir);
//        dirFile.mkdirs();
//        FileOutputStream outputStream=null;
//        try {
//        	outputStream = new FileOutputStream(name);
//        	outputStream.write(b, off, len);
//            FileWriterWithEncoding codeFileWriter = new FileWriterWithEncoding(codeFile, StandardCharsets.UTF_8);
//            template.process(dataModel, codeFileWriter);
//            //关闭流
//            codeFileWriter.close();
//            
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        } catch (TemplateException e) {
//            e.printStackTrace();
//            return false;
//        }
    }
    
    public String readMsgFunction(File file){
    	String result = null;
    	FileReader fileReader = null;
    	BufferedReader bufferedReader = null;
    	try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			String read = null;
			while ((read=bufferedReader.readLine())!=null) {
				result = result + read+"\r\n";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    	return result;
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
            template = TemplateConfig.getInstance().getTemplate(CodeType.JAVA, "Bean.ftl");
            super.generateClassFile(project, template, bean.getDataModel(), Struct.BEAN);
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
            template = TemplateConfig.getInstance().getTemplate(CodeType.JAVA, "Message.ftl");
            super.generateClassFile(project, template, message.dataModel(), Struct.MESSAGE);
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
            template = TemplateConfig.getInstance().getTemplate(CodeType.JAVA, "Handler.ftl");
            super.generateClassFile(project, template, message.handlerDataModel(), Struct.HANDLER);
//            if (message.getType().equals("CS")) {
//                template = TemplateConfig.getInstance().getTemplate(CodeType.JAVA, "JobHandler.ftl");
//                super.generateClassFile(project, template, message.getHandlerDataModel(), Struct.JOBHANDLER);
//            } else {
//                template = TemplateConfig.getInstance().getTemplate(CodeType.JAVA, "Handler.ftl");
//            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
