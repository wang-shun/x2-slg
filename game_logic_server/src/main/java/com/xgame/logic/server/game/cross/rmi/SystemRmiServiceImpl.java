package com.xgame.logic.server.game.cross.rmi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.data.sprite.SpriteType;
import com.xgame.logic.server.LogicBootstrap;
import com.xgame.logic.server.core.fight.FightCalc;
import com.xgame.logic.server.core.system.GlobalManager;
import com.xgame.logic.server.core.system.LogicServer;
import com.xgame.logic.server.core.system.entity.GlobalEnity;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.armshop.ArmyShopManager;
import com.xgame.logic.server.game.world.SpriteManager;
import com.xgame.logic.server.game.world.constant.WorldConstant;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.entity.sprite.ResourceSprite;

/**
 * 系统rmi
 * @author jacky.jiang
 *
 */
@Component
public class SystemRmiServiceImpl implements SystemRmiService {
	
	@Autowired
	private ArmyShopManager armyShopManager;
	@Autowired
	private SpriteManager spriteManager;
	@Autowired
	private FightCalc fightCalc;
	@Autowired
	private GlobalManager globalManager;

	@Override
	public void shutdown() {
		LogicServer logicServer = InjectorUtil.getInjector().getBean(LogicServer.class);
		
		GlobalEnity globalEnity = globalManager.getGlobalEntity();
		globalEnity.setShutdownTime(System.currentTimeMillis());
		globalManager.saveGlobalEntity(globalEnity);
		
		logicServer.shutdown();
		LogicBootstrap.close();
		System.exit(0);
	}

	@Override
	public void refreshConfig() {
		InjectorUtil.getInjector().configSystem.start();
	}

	@Override
	public void unionTest() {
		fightCalc.test();
	}

	@Override
	public void exportWorldPoint() {
		try {
			File file = new  File("d:\\worldpoint.txt");
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write("x");
			fileWriter.write("	");
			fileWriter.write("y");
			fileWriter.write("	");
			fileWriter.write("精灵类型");
			fileWriter.write("	");
			fileWriter.write("等级");
			fileWriter.write("\r\n");
			
			int i = 1;
			Map<Serializable, SpriteInfo> map = spriteManager.getEntityCache();
			for(SpriteInfo spriteInfo : map.values()) {
				i++;
				int x = spriteInfo.getIndex() % WorldConstant.X_GRIDNUM;
				int y = spriteInfo.getIndex() / WorldConstant.X_GRIDNUM;
				fileWriter.write(x+"");
				fileWriter.write("	");
				fileWriter.write(y+"");
				fileWriter.write("	");
				if(spriteInfo.getSpriteType() == SpriteType.RESOURCE.getType()) {
					ResourceSprite resourceSprite = spriteInfo.getParam();
					if(resourceSprite != null) {
						fileWriter.write("资源点");
						fileWriter.write("	");
						fileWriter.write(resourceSprite.getLevel()+"");
					}
				} else if(spriteInfo.getSpriteType() == SpriteType.CAMP.getType() || spriteInfo.getSpriteType() == SpriteType.TERRITORY.getType() || spriteInfo.getSpriteType() == SpriteType.NONE.getType() || spriteInfo.getSpriteType() == SpriteType.PLAYER.getType())  {
					fileWriter.write("空地");
				} else  if(spriteInfo.getSpriteType() == SpriteType.BLOCK.getType()){
					fileWriter.write("障碍点");
				}
				
				fileWriter.write("\r\n");
				
				if(i /1000 > 0) {
					System.err.println("i======================================="+i);
				}
			}
		
			fileWriter.flush();
			fileWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
		
}
