package com.xgame.logic.server.game.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.gate.SessionManager;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.game.alliance.enity.Alliance;
import com.xgame.logic.server.game.notice.bean.NoticeBean;
import com.xgame.logic.server.game.notice.message.ResNoticeMessage;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 公告管理器
 * @author jacky.jiang
 *
 */
@Component
public class NoticeManager {
	
	@Autowired
	private SessionManager sessionManager;
	
	/**
	 * 发送公告
	 * @param player
	 * @param id
	 * @param obj
	 */
	public void sendNotice(Player player, int id, Object... obj) {
		NoticeBean noticeBean = createNotice(id, obj);
		
		// 返回公告信息
		ResNoticeMessage resNoticeMessage = new ResNoticeMessage();
		resNoticeMessage.noticeBean = noticeBean;
		player.send(resNoticeMessage);
	}
	
	/**
	 * 发送军团公告
	 * @param player
	 * @param id
	 * @param obj
	 */
	public void sendAllianceNotice(Player player, Alliance alliance, int id, Object... obj) {
		// 返回公告信息
		NoticeBean noticeBean = createNotice(id, obj);
		ResNoticeMessage resNoticeMessage = new ResNoticeMessage();
		resNoticeMessage.noticeBean = noticeBean;
		sessionManager.writePlayers(alliance.getAllianceMember(), resNoticeMessage);
	}
	
	/**
	 * 发送世界公告
	 * @param player
	 * @param id
	 * @param obj
	 */
	public void sendWorldNotice(int id, Object... obj) {
		// 返回公告信息
		NoticeBean noticeBean = createNotice(id, obj);
		ResNoticeMessage resNoticeMessage = new ResNoticeMessage();
		resNoticeMessage.noticeBean = noticeBean;
		sessionManager.broadcast(resNoticeMessage);
	}
	
	/**
	 * 公告id
	 * @param id
	 * @param obj
	 * @return
	 */
	public NoticeBean createNotice(int id, Object... obj) {
		NoticeBean noticeBean = new NoticeBean();
		String toJson = JsonUtil.toJSON(obj);
		noticeBean.noticeId = id;
		noticeBean.param = toJson;
		return noticeBean;
	}
}
