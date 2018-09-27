package com.xgame.logic.server.game.shop.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.language.view.success.SuccessTipEnum;
import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.shop.message.ReqBuyItemMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqBuyItemHandler extends PlayerCommand<ReqBuyItemMessage>{
    @Override
    public void action() {
        int code = InjectorUtil.getInjector().shopManager.buyItemAndTopic(player, message.id, message.num);
        if(code == -1) {
        	Language.ERRORCODE.send(player, ErrorCodeEnum.E120_SHOP.CODE2); //购买成功
        } else if(code == -2) {
        	Language.ERRORCODE.send(player, ErrorCodeEnum.E120_SHOP.CODE3);
        } else if(code == -3) {
        	Language.ERRORCODE.send(player, ErrorCodeEnum.E120_SHOP.CODE4);
        } else if(code == -4) {
        	Language.ERRORCODE.send(player, ErrorCodeEnum.E120_SHOP.CODE5);
        } else {
        	Language.SUCCESSTIP.send(player, SuccessTipEnum.E120_SHOP.CODE1);
        }
    }
}
