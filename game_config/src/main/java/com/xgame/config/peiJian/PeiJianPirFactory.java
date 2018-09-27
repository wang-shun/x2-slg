package com.xgame.config.peiJian;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
import com.xgame.config.attribute.AttributeConfMap;
import com.xgame.config.attribute.AttributeParser;
import com.xgame.config.items.ItemsPir;
import com.xgame.config.items.ItemsPirFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2016-12-20 10:17:50
 */
@Slf4j
public class PeiJianPirFactory extends BasePriFactory<PeiJianPir> {

	private Map<String, String> pjMetaConfigMap = new HashMap<String, String>();

	public void init(PeiJianPir pir) {
		pir.attr = new AttributeConfMap(pir.id);
	}

	@Override
	public void load(PeiJianPir pir) {
		((AttributeConfMap) pir.attr).confId = pir.id;
	}

	/**
	 * 自定义解析 cao3
	 */
	@ConfParse("cao3")
	public void cao3Pares(String conf, PeiJianPir pir) {
		String[] split = conf.split(";");
		if (split.length >= 2) {
			pir.cao3 = Integer.valueOf(split[0]);
		}
	}

	/**
	 * 自定义解析 icon
	 */
	@ConfParse("icon")
	public void iconPares(String conf, PeiJianPir pir) {

	}

	/**
	 * 自定义解析 showId
	 */
	@ConfParse("showId")
	public void showIdPares(String conf, PeiJianPir pir) {

	}

	/**
	 * 自定义解析 ACTId
	 */
	@ConfParse("ACTId")
	public void ACTIdPares(String conf, PeiJianPir pir) {

	}

	/**
	 * 自定义解析 cao5
	 */
	@ConfParse("cao5")
	public void cao5Pares(String conf, PeiJianPir pir) {

	}

	/**
	 * 自定义解析 shuiPing
	 */
	@ConfParse("shuiPing")
	public void shuiPingPares(String conf, PeiJianPir pir) {

	}

	/**
	 * 自定义解析 attr
	 */
	@ConfParse("attr")
	public void attrPares(String conf, PeiJianPir pir) {
		AttributeParser.parse(conf, pir.getAttr());
	}

	/**
	 * 自定义解析 decay
	 */
	@ConfParse("decay")
	public void decayPares(String conf, PeiJianPir pir) {

	}

	@Override
	public PeiJianPir newPri() {
		return new PeiJianPir();
	}

	public static PeiJianPir get(Object key) {
		return instance.factory.get(key);
	}

	/* single instance */
	/**
	 * 饿汉单列
	 */
	private static final PeiJianPirFactory instance = new PeiJianPirFactory();

	public static PeiJianPirFactory getInstance() {
		return instance;
	}

	@Override
	public void loadOver(String programConfigPath, Map<Integer, PeiJianPir> data) {
		// Alex 自建武器自己的配置加载完毕后 , 就要根据配件的 showid 加载对应的 配件的自描述配置

		log.debug("自建武器自己的配置加载完毕后 , 就要根据配件的 showid 加载对应的 配件的自描述配置");
		readFile(new File(programConfigPath + "/program/zijian/"));
	}

	/**
	 * 根据配件的showid 返回它的自描述配置内容
	 * */
	public String getPjMetaConfig(String showId) {
		if (this.pjMetaConfigMap.containsKey(showId)) {
			return this.pjMetaConfigMap.get(showId);
		}
		 
		return null;
	}
	
	/**
	 * 根据品质 配件组ID查找配件
	 * @param itemType
	 * @param itemQuality
	 * @return
	 */
	public PeiJianPir getPeiJianPirByType6AndQuality(int type6, int itemQuality) {
		Iterator<Map.Entry<Integer, PeiJianPir>> entriesIterator = PeiJianPirFactory.getInstance().getFactory().entrySet().iterator();
		
		// 获取相同品质和类型的道具
		while (entriesIterator.hasNext()) {
			PeiJianPir configModel= entriesIterator.next().getValue();
			if(configModel.getQuality() == itemQuality && type6 == configModel.getType6())
				return configModel;
		}
		return null;
	}
	
	public Map<String, String> getPjMetaConfig() {
		return this.pjMetaConfigMap;
	}

	private void readFile(File file) {
		try {
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					readFile(files[i]);
				}
			} else {
				parse(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void parse(File file) {
		String fileName = file.getName();

		fileName = fileName.trim();

		fileName = fileName.replace(".txt", "");

		try {
			byte[] fileBytes = Files.readAllBytes(file.toPath());

			String fileStr = new String(fileBytes);

			pjMetaConfigMap.put(fileName, fileStr);

		} catch (Exception e) {

		}
	}
}