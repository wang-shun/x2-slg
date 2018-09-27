package com.moloong.messageGenerator.core;

/**  
 * @Description:bean结构类型    
 * @author ye.yuan
 * @date 2011年4月16日 下午6:11:48  
 *
 */
public enum Struct {
    
	BEAN("bean"),
	HANDLER("handler"),
//	JOBHANDLER("jobhandler"),
	MESSAGE("message"),
	MESSAGEPOOL("messagepool");
	
	/**类型名*/
    final String typeName;

    Struct(String extension) {
        this.typeName = extension;
    }

    public String getTypeName() {
        return typeName;
    }
}
