package com.xgame.logic.server.game.war.entity;

import lombok.extern.slf4j.Slf4j;

import com.xgame.config.ConfigSystem;
import com.xgame.config.peiJian.PeiJianPirFactory;
import com.xgame.logic.server.core.fight.FightCalc;
import com.xgame.logic.server.core.fight.luaj.lib.jse.CoerceJavaToLua;
import com.xgame.logic.server.core.fight.luaj.vm2.LuaValue;

/**
 * lua执行上线文
 * @author jacky.jiang
 *
 */
@Slf4j
public class LuaContext {
	private LuaValue sysCfgLuaValue;
	private LuaValue pjCfgLuaValue;
	private String path;
	private boolean isInit = false;
	
	public void init(ConfigSystem configSystem) {
		if (!this.isInit) {
			this.isInit = true;
			this.sysCfgLuaValue = CoerceJavaToLua.coerce(configSystem);
			this.pjCfgLuaValue = CoerceJavaToLua.coerce(PeiJianPirFactory.getInstance());
			path = FightCalc.class.getResource("/com/xgame/logic/server/core/fight/lua/").getPath();
			log.error("path============================={}", path);
			System.setProperty("luaj.package.path", path + "?.lua");
		}
	}
	
	
	public LuaValue getSysCfgLuaValue() {
		return sysCfgLuaValue;
	}
	public void setSysCfgLuaValue(LuaValue sysCfgLuaValue) {
		this.sysCfgLuaValue = sysCfgLuaValue;
	}
	public LuaValue getPjCfgLuaValue() {
		return pjCfgLuaValue;
	}
	public void setPjCfgLuaValue(LuaValue pjCfgLuaValue) {
		this.pjCfgLuaValue = pjCfgLuaValue;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isInit() {
		return isInit;
	}
	public void setInit(boolean isInit) {
		this.isInit = isInit;
	}
}
