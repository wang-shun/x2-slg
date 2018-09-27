package com.moloong.messageGenerator.bean.messagexml;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.moloong.messageGenerator.bean.Bean;
import com.moloong.messageGenerator.bean.Message;

/**  
 * @Description:和xml消息定义文件丢的java接口，对象由工厂类（MessageXMLFactory）生成
 * @author ye.yuan
 * @date 2011年4月16日 下午6:09:18  
 *
 */
public interface IMessageXML {
    
    public Map<String, Bean> getBeans();
    public List<Message> getMessages();
    //对应的xml对象
    public File getXmlfile();
}
