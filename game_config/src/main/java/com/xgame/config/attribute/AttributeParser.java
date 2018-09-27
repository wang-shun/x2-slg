package com.xgame.config.attribute;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 *属性解析者
 *2017-1-02  14:41:04
 *@author ye.yuan
 *
 */
public class AttributeParser {

	public static void parse(String conf,AttributeConfMap attributeConfMap) {
		String[] split = conf.split(";");
		for (int n = 0; n < split.length; n++) {
			String[] split2 = split[n].split(":");
			String[] split3 = split2[0].split(",");
			AttributeAppenderConf attributeConf = new AttributeAppenderConf();
			attributeConf.setId(Integer.parseInt(split3[0]));
			attributeConf.setCarriers(new int[split3.length - 1]);
			for (int i = 1; i < split3.length; i++) {
				attributeConf.getCarriers()[i - 1] = Integer.parseInt(split3[i]);
			}
			String[] split4 = split2[1].split(",");
			attributeConf.setLevelAttributes(new double[split4.length+1]);
			attributeConf.getLevelAttributes()[0] = 0.0d;
			for(int i=0;i<split4.length;i++){
				int level = i+1;
				attributeConf.getLevelAttributes()[level] = Double.parseDouble(split4[i]);
				//解析LibraryConfs
				for(int j=0;j<attributeConf.getCarriers().length;j++){
					int nodeId = attributeConf.getCarriers()[j];
					LibraryConf libraryConf = attributeConfMap.getLibraryConfs().get(level, nodeId);
					if(libraryConf==null){
						attributeConfMap.getLibraryConfs().put(level, nodeId, libraryConf = new LibraryConf());
					}
					libraryConf.put(attributeConf.getId(), attributeConf.getLevelAttributes()[level]);
				}
			}
			
			attributeConfMap.put(attributeConf.getId(), attributeConf);
		}
	}
	
	
	/**
	 * 转换属性
	 * @param conf
	 * @return
	 */
	public static Map<Integer, Double> parse(String conf) {
		Map<Integer, Double> attrMap = new ConcurrentHashMap<Integer, Double>();
		String[] split = conf.split(";");
		for (int n = 0; n < split.length; n++) {
			String[] split2 = split[n].split(":");
			String[] split3 = split2[0].split(",");
			attrMap.put(Integer.valueOf(split3[0]), Double.valueOf(split2[1]));
		}
		return attrMap;
	}
	
}
