package com.xgame.logic.server.game.bag.entity.type;

import com.xgame.logic.server.game.bag.entity.ItemContext;
import com.xgame.logic.server.game.bag.entity.ItemControl;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 普通道具 type=6
 * 普通道具的使用逻辑在各业务模块内实现
 * 此处只进行背包内调用可否使用的判断
 * 道具的增减在ItemManager中
 * @author lmj
 *
 */
public class CommonItemControl extends ItemControl {
	//背包内普通道具的使用写死
	/**元旦活动头像*/
	public static final int ITEM_YDTX = 210101;
	/**春节活动头像*/
	public static final int ITEM_CJTX = 210102;
	/**元宵节活动头像*/
	public static final int ITEM_YXTX = 210103;
	/**情人节活动头像*/
	public static final int ITEM_QRTX = 210104;
	/**端午节活动头像*/
	public static final int ITEM_DWTX = 210105;
	/**中秋节活动头像*/
	public static final int ITEM_ZQTX = 210106;
	/**国庆节活动头像*/
	public static final int ITEM_GQTX = 210107;
	/**圣诞节活动头像*/
	public static final int ITEM_SDTX = 210108;
	/**排行榜第一名头像*/
	public static final int ITEM_PHTX = 210109;
	/**军团战第一名头像*/
	public static final int ITEM_JTTX = 210110;
	/**元旦活动挂件*/
	public static final int ITEM_YDGJ = 210201;
	/**春节活动挂件*/
	public static final int ITEM_CJGJ = 210202;
	/**元宵节活动挂件*/
	public static final int ITEM_YXGJ = 210203;
	/**情人节活动挂件*/
	public static final int ITEM_QRGJ = 210204;
	/**端午节活动挂件*/
	public static final int ITEM_DWGJ = 210205;
	/**中秋节活动挂件*/
	public static final int ITEM_ZQGJ = 210206;
	/**国庆节活动挂件*/
	public static final int ITEM_GQGJ = 210207;
	/**圣诞节活动挂件*/
	public static final int ITEM_SDGJ = 210208;
	/**排行榜第一名挂件*/
	public static final int ITEM_PHGJ = 210209;
	/**军团战第一名挂件*/
	public static final int ITEM_JTGJ = 210210;
	
	
	@Override
	public boolean use(Player player, int itemId, int num, ItemContext rewardContext, Object ... param) {
		if(num < 1) {
			return false;
		}
		return false;
	}
	
	public CommonItemControl(int type) {
		super(type);
	}
}
