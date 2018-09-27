package com.moloong.messageGenerator.bean.proto;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;

import com.moloong.messageGenerator.bean.messagexml.IMessageXML;

/**
 *
 *2016-8-01  12:19:22
 *@author ye.yuan
 *
 */
public enum ProtoFactory {
	
	INSTANCE;

	/**
     * 创建MessageXML对象
     * @param xmlfile
     * @return
    */
    public void create(String url) {
       parse(url);
    }
    
    
    private void parse(String url){
    	try {
			List<String> lines = Files.readAllLines(new File(url).toPath(), Charset.forName("UTF-8"));
			Iterator<String> iterator = lines.iterator();
			while (iterator.hasNext()) {
				String string = (String) iterator.next();
				System.out.println(string);
			}
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	
}
