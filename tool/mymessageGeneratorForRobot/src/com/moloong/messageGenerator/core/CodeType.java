/**
 * Copyright (c) 2014, http://www.moloong.com/ All Rights Reserved. 
 * 
 */  
package com.moloong.messageGenerator.core;

/**  
 * @Description:代码类型枚举
 * @author ye.yuan
 * @date 2011年4月11日 下午3:33:13  
 *
 */
public enum CodeType {

    JAVA("java"),AS("as"),CPP("cpp");
    
    /**扩展名*/
    final String extension;

    CodeType(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }
}
