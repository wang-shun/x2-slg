package com.moloong.messageGenerator.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**  
 * @Description:消息pojo类定义
 * @author ye.yuan
 * @date 2011年4月16日 下午6:10:22  
 *
 */
public class Message extends Templater{
	
	private int packageId;
    /**消息ID*/
	private int id;
	
	private int msgId;
	
	private int funcId;
	/**消息类型*/
	private String type;
	/**生成的消息的的Class名称*/
	private String messageName;
	/**包名*/
	private String packageName;
	/**消息说明*/
	private String explain;
	private String queue;
	private String server;
	private String commandEnum;
	
	private boolean sync;
	/**字段s*/
	private List<Field> fields;
	/**包s*/
	private Set<String> imports;
	
	private int msgType;
	
	private Message msgName;
	
	/**message在本文件引用bean时，只写的name，而不是全名,bean的引用由于在同一个包不用import
	 * referenceBeans是message引用的bean的fullname的list，用于生成message时的“import”
	 * */
	private Set<String> referenceBeans;
	
	/**只包含引用头文件名称，主要用于c++代码生成引用头文件*/ 
	private Set<String> referenceHeaders;
	
    /**
	 * 获取message模板数据模型
	 * @return
	*/
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map<String, Object> dataModel() {
        Map<String, Object> dataModel = new HashMap<String, Object>();
        dataModel.put("messageId", id);
        dataModel.put("msgId", msgId);
        dataModel.put("funcId", funcId);
        dataModel.put("package", this.getPackageName());
        dataModel.put("className", this.getMessageName());
        dataModel.put("explain", this.getExplain());
        dataModel.put("commandEnum", this.getCommandEnum());
        dataModel.put("type", this.getType());
        dataModel.put("server", this.getServer());
        dataModel.put("queue", this.getQueue());
        dataModel.put("sync", this.getSync());
        dataModel.put("fields", this.getFields());
        dataModel.put("extends", this.getExtendsName());
        dataModel.put("implements", this.getImplementList());
        if(!fields.isEmpty()){
        	referenceBeans.add("com.xgame.msglib.annotation.MsgField");
        }
        //新增
        dataModel.put("msgType", msgType);
        dataModel.put("msg", this);
//        if(msgName!=null){
//        	dataModel.put("msgName", msgName.messageName);
//        }
        Iterator iter = this.getFields().iterator();
        HashSet imports = new HashSet();
        
        while (iter.hasNext()) {
            Field field = (Field) iter.next();
            if (field.getClassName().indexOf("com.") != -1) {
                imports.add(field.getClassName());
            }
        }
        // 增加引用bean的import
        imports.addAll(referenceBeans);
        dataModel.put("imports", imports.toArray(new String[0]));
        //C++引入的头文件
        dataModel.put("referenceHeaders", referenceHeaders);
        return dataModel;
    }
    
    /**
     * 获取message模板数据模型
     * @return
    */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map<String, Object> dataModelForAS() {
        Map<String, Object> dataModel = new HashMap<String, Object>();
        dataModel.put("messageId", Integer.valueOf(this.getId()));
        dataModel.put("package", this.getPackageName());
        dataModel.put("className", this.getMessageName());
        dataModel.put("explain", this.getExplain());
        dataModel.put("fields", this.getFields());
        //新增
        dataModel.put("msgType", type);
        
        Iterator iter = this.getFields().iterator();
        HashSet imports = new HashSet();
        
        while (iter.hasNext()) {
            Field field = (Field) iter.next();
            if ("long".equals(field.getClassName())) {
                imports.add("com.game.utils.long");
            } else if (field.getClassName().indexOf("com.") != -1) {
                imports.add(field.getClassName());
            }
        }
        
        //增加引用bean的import
        imports.addAll(referenceBeans);
        
        dataModel.put("imports", imports.toArray(new String[0]));
        return dataModel;
    }

	/**
	 * 获取handler模板数据模型
	 * @return
	*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, Object> handlerDataModel() {
		Map<String, Object> dataModel = new HashMap();
		dataModel.put("package", this.getPackageName());
		dataModel.put("className", this.getMessageName());
		dataModel.put("explain", this.getExplain());
		dataModel.put("messageId", Integer.valueOf(this.getId()));
		dataModel.put("sync", this.getSync());
		List imports = new ArrayList();
		imports.add(this.getPackageName() + ".message." + this.getMessageName() + "Message");
		if(msgType == 1){
			imports.add(msgName.getPackageName() + ".message." + msgName.getMessageName() + "Message");
		}
		dataModel.put("imports", imports);
		dataModel.put("msg", this);
		dataModel.put("referenceHeaders", getReferenceHeaders());
		
		return dataModel;
	}
	
	public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessageName() {
        return this.messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<Field> getFields() {
        return this.fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public String getExplain() {
        return this.explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getQueue() {
        return this.queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getServer() {
        return this.server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Set<String> getImports() {
        return this.imports;
    }

    public void setImports(Set<String> imports) {
        this.imports = imports;
    }
    
    public Set<String> getReferenceBeans() {
        return referenceBeans;
    }

    public void setReferenceBeans(Set<String> referenceBeans) {
        this.referenceBeans = referenceBeans;
    }

    public Set<String> getReferenceHeaders() {
        return referenceHeaders;
    }

    public void setReferenceHeaders(Set<String> referenceHeaders) {
        this.referenceHeaders = referenceHeaders;
    }

	public boolean getSync() {
		return sync;
	}

	public void setSync(boolean sync) {
		this.sync = sync;
	}

	public String getCommandEnum() {
		return commandEnum;
	}

	public void setCommandEnum(String commandEnum) {
		this.commandEnum = commandEnum;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public Message getMsgName() {
		return msgName;
	}

	public void setMsgName(Message msgName) {
		this.msgName = msgName;
	}

	public int getPackageId() {
		return packageId;
	}

	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}

	public int getMsgId() {
		return msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	public int getFuncId() {
		return funcId;
	}

	public void setFuncId(int funcId) {
		this.funcId = funcId;
	}
}
