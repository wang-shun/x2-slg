package com.xgame.logic.server.game.world.entity;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.utils.FileUtils;

/**
 *
 * 2016-8-31 22:19:22
 *
 * @author ye.yuan
 *
 */
@Component
public class MapConfigLoader {

	/**
	 * 加载地图配置信息
	 * @return
	 */
	public List<MapJsonData> loadMapConfig() {
		String _path = InjectorUtil.getInjector().path + "/program/map/test.json";
		String mapData = FileUtils.readTxt(new File(_path));
		List<MapJsonData> jsonData = JsonUtil.parseArray(mapData, MapJsonData.class);
		return jsonData;	
	}
}
