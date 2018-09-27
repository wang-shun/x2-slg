package com.moloong.messageGenerator.bean;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**  
 * @Description:Bean pojo定义  
 * @author ye.yuan
 * @date 2011年4月16日 下午6:09:55  
 *
 */
public class Bean  extends Templater{
    /**生成的Bean的Class名称*/
	private String beanName;
	/**包名*/
	private String packageName;
	/**说明*/
	private String explain;
	/**bean的字段*/
	private List<Field> fields;
	/**需要导入的包*/
	private Set<String> imports = new HashSet<>();
	
	/**只包含引用头文件名称，主要用于c++代码生成引用头文件,java和as同一个package下需要引用*/ 
    private Set<String> referenceHeaders;
    
    public Bean() {
    	extendsName = "XPro";
	}

    /**
	 * 获取模板数据模型
	 * @return
	*/
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map<String, Object> getDataModel() {
        Map<String, Object> dataModel = new HashMap<String, Object>();
        dataModel.put("package", this.getPackageName());
        dataModel.put("className", this.getBeanName());
        dataModel.put("explain", this.getExplain());
        dataModel.put("fields", this.getFields());
        dataModel.put("extends", this.getExtendsName());
        dataModel.put("implements", this.getImplementList());
        Iterator iter = this.getFields().iterator();
        HashSet imports = new HashSet();
        
        while (iter.hasNext()) {
            Field field = (Field) iter.next();
            if (field.getClassName().indexOf("com.") != -1) {
                imports.add(field.getClassName());
            }
        }
        
        // 增加引用bean的import
        imports.addAll(this.imports);
        
        dataModel.put("imports", imports.toArray(new String[0]));
        //C++引入的头文件
        dataModel.put("referenceHeaders", referenceHeaders);
        return dataModel;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map<String, Object> getDataModelForAS() {
        Map<String, Object> dataModel = new HashMap<String, Object>();
        dataModel.put("package", this.getPackageName());
        dataModel.put("className", this.getBeanName());
        dataModel.put("explain", this.getExplain());
        dataModel.put("fields", this.getFields());
        
        Iterator iter = this.getFields().iterator();
        HashSet imports = new HashSet();
        
        while (iter.hasNext()) {
            Field field = (Field) iter.next();
            //as代码特殊处理的地方
            if ("long".equals(field.getClassName())) {
                imports.add("com.game.utils.long");
            } else if (field.getClassName().indexOf("com.") != -1) {
                imports.add(field.getClassName());
            }
        }
        
        dataModel.put("imports", imports.toArray(new String[0]));
        return dataModel;
    }

    public String getBeanName() {
        return this.beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
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

    public Set<String> getImports() {
        return this.imports;
    }

    public void setImports(Set<String> imports) {
        this.imports = imports;
    }

    public Set<String> getReferenceHeaders() {
        return referenceHeaders;
    }

    public void setReferenceHeaders(Set<String> referenceHeaders) {
        this.referenceHeaders = referenceHeaders;
    }
    
}
