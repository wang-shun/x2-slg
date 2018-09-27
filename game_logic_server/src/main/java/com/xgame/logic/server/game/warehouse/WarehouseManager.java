package com.xgame.logic.server.game.warehouse;

import java.util.Iterator;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.component.AbstractComponent;
import com.xgame.logic.server.game.country.entity.XBuild;
import com.xgame.logic.server.game.country.structs.build.resource.ResourceControl;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.warehouse.message.ResResourceMessage;

/**
 *
 *2016-12-29  11:57:26
 *@author ye.yuan
 *
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WarehouseManager extends AbstractComponent<Player>{

	
	public void send(){
		ResResourceMessage resResourceMessage = new ResResourceMessage();
		if(e.getCountryManager().getMoneyResourceControl() != null) {
			fillPro(e.getCountryManager().getMoneyResourceControl(), resResourceMessage);
		}
		
		if(e.getCountryManager().getOilResourceControl() != null) {
			fillPro(e.getCountryManager().getOilResourceControl(), resResourceMessage);
		}
		
		if(e.getCountryManager().getRareResourceCountrol() != null) {
			fillPro(e.getCountryManager().getRareResourceCountrol(), resResourceMessage);
		}
		
		if(e.getCountryManager().getSteelResourceControl() != null) {
			fillPro(e.getCountryManager().getSteelResourceControl(), resResourceMessage);
		}
		
		e.send(resResourceMessage);
	}
	
	public void fillPro(ResourceControl resourceControl,ResResourceMessage resResourceMessage){
		if(resourceControl!=null) {
			Iterator<XBuild> iterator = resourceControl.getBuildMap().values().iterator();
			while (iterator.hasNext()) {
				XBuild xBuild = (XBuild) iterator.next();
				resResourceMessage.resources.add(resourceControl.mathResourceNum(e, xBuild.getUid()));
			}
		}
	}
	
}	
