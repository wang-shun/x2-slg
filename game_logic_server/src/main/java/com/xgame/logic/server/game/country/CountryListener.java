package com.xgame.logic.server.game.country;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.core.gamelog.event.IListener;

@Component
public class CountryListener implements IListener {

	@Autowired
	private EventBus eventBus;
	
	@PostConstruct
	public void init() {
		eventBus.addEventListener(this);
	}

	public void onAction(IEventObject event) {
		int type = event.getType();
		switch (type) {
		case EventTypeConst.EVENT_ATTRIBUTE_REFRESH: {
//			Player player = event.getPlayer();
//			AttributeRefreshEventObject attributeRefreshEventObject = (AttributeRefreshEventObject) event;
//			List<Integer> attrIds = attributeRefreshEventObject.getAttrIds();

//			if (attrIds.contains(AttributeEnum.CASH_SAFEGUARD_RATE.getId())) {
//				player.getCountryManager().getMoneyResourceControl().mathCapacityAndProtect(player);
//			} else if (attrIds.contains(AttributeEnum.EARTH_SAFEGUARD_RATE.getId())) {
//				player.getCountryManager().getRareResourceCountrol().mathCapacityAndProtect(player);
//			} else if (attrIds.contains(AttributeEnum.STEEL_SAFEGUARD_RATE.getId())) {
//				player.getCountryManager().getSteelResourceControl().mathCapacityAndProtect(player);
//			} else if (attrIds.contains(AttributeEnum.OIL_SAFEGUARD_RATE.getId())) {
//				player.getCountryManager().getOilResourceControl().mathCapacityAndProtect(player);
//			}
		}
			break;
		}
	}

	@Override
	public int[] focusActions() {
		return new int[]{EventTypeConst.EVENT_ATTRIBUTE_REFRESH};
	}
}
