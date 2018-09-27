package com.xgame.playersearch.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xgame.base.controller.BaseController;
import com.xgame.common.Pagination;
import com.xgame.playersearch.entity.Player;
import com.xgame.playersearch.service.IPlayerService;
import com.xgame.playersearch.util.AddTalentEventResolve;
import com.xgame.playersearch.util.AllianceAidEventResolve;
import com.xgame.playersearch.util.BuildingRewardEventResolve;
import com.xgame.playersearch.util.CommanderChangeNameEventResolve;
import com.xgame.playersearch.util.CommanderLevelUpEventResolve;
import com.xgame.playersearch.util.CreateAllianceEventResolve;
import com.xgame.playersearch.util.CreateRoleEventResolve;
import com.xgame.playersearch.util.CurrencyEventResolve;
import com.xgame.playersearch.util.DonateEventResolve;
import com.xgame.playersearch.util.EventTypeConst;
import com.xgame.playersearch.util.FinishTaskEventResolve;
import com.xgame.playersearch.util.GetImplantedEventResolve;
import com.xgame.playersearch.util.ImplantedCompoundEventResolve;
import com.xgame.playersearch.util.ImplantedProduceEventResolve;
import com.xgame.playersearch.util.ItemChangeEventResolve;
import com.xgame.playersearch.util.LevelupBuildEventResolve;
import com.xgame.playersearch.util.MaterialsCompoundEventResolve;
import com.xgame.playersearch.util.RemoveObstacleEventResolve;
import com.xgame.playersearch.util.ResearchLevelUpEndEventResolve;
import com.xgame.playersearch.util.ResearchLevelUpEventResolve;
import com.xgame.playersearch.util.SendAllianceNoticeEventResolve;
import com.xgame.playersearch.util.SoldierChangeEventResolve;
import com.xgame.playersearch.util.SpeedUpEventResolve;
import com.xgame.playersearch.util.UnlockPeijianEventResolve;
import com.xgame.playersearch.util.UseEquipmentEventResolve;
import com.xgame.util.aop.SystemControllerLog;

@Controller
@RequestMapping("/playersearch/player")
public class PlayerSearchController extends BaseController {
	
	@Resource
	private IPlayerService playerService;
	
	@RequestMapping(value="/loglist",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="查看玩家日志")
	public ModelAndView loglist(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="pageNumber",defaultValue="1") int pageNumber,@RequestParam(name="pageSize",defaultValue="20") int pageSize){
		ModelAndView mav = new ModelAndView("/playersearch/loglist");
		return mav;
	}
	
	@RequestMapping(value="/logjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON logjson(HttpServletRequest request,HttpServletResponse response){
		String page =request.getParameter("page");
		String rows =request.getParameter("rows");
		Map<String,Object> params = new  HashMap<String,Object>();
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		String roleId = request.getParameter("roleId");
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		params.put("roleId", roleId);
		Pagination pagination = playerService.getLogPage(Integer.parseInt(page), Integer.parseInt(rows), params);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONObject jsonMap = new JSONObject();
		jsonMap.put("total", pagination.getTotal());
		JSONArray jsonArr = new JSONArray();
		for(Object obj : pagination.getList()){
			JSONObject jsonObj = JSON.parseObject(JSON.toJSONString(obj));
			if(jsonObj.getDate("createTime") != null){
				jsonObj.put("createTime", sdf.format(jsonObj.getDate("createTime")));
			}
			
			String content = jsonObj.getString("content");
			int type = jsonObj.getIntValue("type");
			switch (type) {
			case EventTypeConst.EVENT_CURRENCY_INCREASE:
				content = new CurrencyEventResolve().resolve(content);
				break;
			case EventTypeConst.EVENT_CURRENCY_DECREMENT:
				content = new CurrencyEventResolve().resolve(content);
				break;
			case EventTypeConst.EVENT_CREATE_ROLE:
				content = new CreateRoleEventResolve().resolve(content);
				break;
			case EventTypeConst.EVENT_BUILD_LEVELUP_START:
				content = new LevelupBuildEventResolve().resolve(content);
				break;
			case EventTypeConst.EVENT_SPEED_UP:
				content = new SpeedUpEventResolve().resolve(content);
				break;
			case EventTypeConst.ITEM_CHANGE:
				content = new ItemChangeEventResolve().resolve(content);
				break;
			case EventTypeConst.REMOVE_OBSTACLE:
				content = new RemoveObstacleEventResolve().resolve(content);
				break;
			case EventTypeConst.UNLOCK_PEIJIAN:
				content = new UnlockPeijianEventResolve().resolve(content);
				break;	
			case EventTypeConst.SOLDIER_CHANGE:
				content = new SoldierChangeEventResolve().resolve(content);
				break;
			case EventTypeConst.BUILDING_REWARD:
				content = new BuildingRewardEventResolve().resolve(content);
				break;
			case EventTypeConst.RESEARCH_LEVEL_UP:
				content = new ResearchLevelUpEventResolve().resolve(content);
				break;
			case EventTypeConst.RESEARCH_LEVEL_UP_END:
				content = new ResearchLevelUpEndEventResolve().resolve(content);
				break;
			case EventTypeConst.FINISH_TASK:
				content = new FinishTaskEventResolve().resolve(content);
				break;
			case EventTypeConst.IMPLANTED_PRODUCE:
				content = new ImplantedProduceEventResolve().resolve(content);
				break;
			case EventTypeConst.IMPLANTED_COMPOUND:
				content = new ImplantedCompoundEventResolve().resolve(content);
				break;
			case EventTypeConst.GET_IMPLANTED:
				content = new GetImplantedEventResolve().resolve(content);
				break;
			case EventTypeConst.MATERIALS_COMPOUND:
				content = new MaterialsCompoundEventResolve().resolve(content);
				break;
			case EventTypeConst.EVENT_COMMAND_LEVELUP:
				content = new CommanderLevelUpEventResolve().resolve(content);
				break;
			case EventTypeConst.EVENT_COMMANDER_CHANGE_NAME:
				content = new CommanderChangeNameEventResolve().resolve(content);
				break;
			case EventTypeConst.ADD_TALENT:
				content = new AddTalentEventResolve().resolve(content);
				break;
			case EventTypeConst.USE_EQUIPMENT:
				content = new UseEquipmentEventResolve().resolve(content);
				break;
			case EventTypeConst.EVENT_CREATE_ALLIANCE:
				content = new CreateAllianceEventResolve().resolve(content);
				break;
			case EventTypeConst.ALLIANCE_DONATE:
				content = new DonateEventResolve().resolve(content);
				break;
			case EventTypeConst.ALLIANCE_AID:
				content = new AllianceAidEventResolve().resolve(content);
				break;
			case EventTypeConst.ALLIANCE_PUBLISH_RECRUIT:
				content = new SendAllianceNoticeEventResolve().resolve(content);
				break;
			default:
				break;
			}
			jsonObj.put("content", content);
			jsonArr.add(jsonObj);
		}
		jsonMap.put("rows", jsonArr);
		return jsonMap;
	}
	
	/**
	 * 异步获取玩家信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/playerjson",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSON playerjson(HttpServletRequest request,HttpServletResponse response){
		JSONArray jsonArr = new JSONArray();
		String roleId = request.getParameter("roleId");
		
		if(StringUtils.isNoneEmpty(roleId)){
			JSONObject playerRTInfo = addPlayerRealTimeInfo(Integer.parseInt(roleId.substring(0, 4)),Long.parseLong(roleId));
			if(playerRTInfo != null){
				JSONObject jsonObject = new JSONObject();
				JSONObject basics = playerRTInfo.getJSONObject("basics");
				if(basics != null){
					jsonObject.put("roleId", roleId);
					jsonObject.put("serverArea", roleId.substring(0, 4));
					if(basics.getString("roleName") != null){
						jsonObject.put("roleName", basics.getString("roleName"));
					}
				}
				JSONObject vipInfo = playerRTInfo.getJSONObject("vipInfo");
				if(vipInfo != null){
					if(vipInfo.getLong("vipLevel") != null){
						jsonObject.put("vipLevel", vipInfo.getLong("vipLevel"));
					}
				}
				jsonArr.add(jsonObject);
			}
		}
		return jsonArr;
	}
	
	@RequestMapping(value="/info",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="查看玩家信息")
	public ModelAndView info(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="pageNumber",defaultValue="1") int pageNumber,@RequestParam(name="pageSize",defaultValue="20") int pageSize){
		ModelAndView mav = new ModelAndView("/playersearch/info");
		String roleId = request.getParameter("roleId");
		if(StringUtils.isNoneEmpty(roleId)){
			JSONObject playerRTInfo = addPlayerRealTimeInfo(Integer.parseInt(roleId.substring(0, 4)),Long.parseLong(roleId));
			if(playerRTInfo != null){
				//基本信息
				JSONObject playerBasicInfo = this.getPlayerBasicInfo(playerRTInfo, roleId);
				mav.addObject("player", playerBasicInfo);
				//货币信息
				JSONObject currencyObj = playerRTInfo.getJSONObject("currency");
				if(currencyObj != null){
					JSONArray currency = this.getCurrency(currencyObj);
					mav.addObject("currency", currency);
				}
				//背包
				JSONObject playerBagObj = playerRTInfo.getJSONObject("playerBag");
				//道具背包信息
				if(playerBagObj != null){
					if(playerBagObj.getJSONObject("itemTable") != null){
						JSONArray itemTable = this.getItemPlayerBag(playerBagObj.getJSONObject("itemTable"));
						mav.addObject("itemTable", itemTable);
					}
				}
				//装备背包信息
				if(playerBagObj != null){
					if(playerBagObj.getJSONObject("equipmentMap") != null){
						JSONArray equipmentMap = this.getEquipmentPlayerBag(playerBagObj.getJSONObject("equipmentMap"));
						mav.addObject("equipmentMap", equipmentMap);
					}
				}
				//士兵信息
				JSONObject soldierDataObject = playerRTInfo.getJSONObject("soldierData");
				if(soldierDataObject != null){
					if(soldierDataObject.getJSONObject("soldiers") != null){
						JSONArray soldiers = this.getSoldiers(soldierDataObject.getJSONObject("soldiers"));
						mav.addObject("soldiers", soldiers);
					}
				}
				//活跃
				JSONObject activeInfoObj = playerRTInfo.getJSONObject("activeInfo");
				//已激活宝箱
				if(activeInfoObj != null){
					if(activeInfoObj.getJSONObject("rewards") != null){
						JSONArray rewards = this.getRewards(activeInfoObj.getJSONObject("rewards"));
						mav.addObject("rewards", rewards);
					}
				}
				//活跃完成状态
				if(activeInfoObj != null){
					if(activeInfoObj.getJSONObject("activeItems") != null){
						JSONArray activeItems = this.getActiveItems(activeInfoObj.getJSONObject("activeItems"));
						mav.addObject("activeItems", activeItems);
					}
				}
				//领地信息
				JSONObject playerTerritory = playerRTInfo.getJSONObject("playerTerritory");
				mav.addObject("playerTerritory", playerTerritory);
				//科研信息
				JSONObject techsObject = playerRTInfo.getJSONObject("techs");
				if(techsObject != null){
					if(techsObject.getJSONObject("techs") != null){
						JSONArray techs = this.getTechs(techsObject.getJSONObject("techs"));
						mav.addObject("techs", techs);
					}
				}
				//联盟战队信息
				JSONObject playerAllianceBattleTeam = playerRTInfo.getJSONObject("playerAllianceBattleTeam");
				mav.addObject("playerAllianceBattleTeam", playerAllianceBattleTeam);
				//角色信息
				JSONObject playerInfo = playerRTInfo.getJSONObject("playerInfo");
				mav.addObject("playerInfo", playerInfo);
				//指挥官信息
				JSONObject commanderDataObj = playerRTInfo.getJSONObject("commanderData");
				if(commanderDataObj != null){
					JSONArray commander = this.getCommander(commanderDataObj);
					mav.addObject("commander", commander);
				}
				
			}
		}
		mav.addObject("roleId", roleId);
		return mav;
	}
	
	/**
	 * 解析玩家基本信息
	 * @param playerRTInfo
	 * @param roleId
	 * @return
	 */
	private JSONObject getPlayerBasicInfo(JSONObject playerRTInfo,String roleId){
		JSONObject jsonObject = new JSONObject();
		JSONObject basics = playerRTInfo.getJSONObject("basics");
		if(basics != null){
			jsonObject.put("roleId", roleId);
			jsonObject.put("serverArea", roleId.substring(0, 4));
			if(basics.getString("roleName") != null){
				jsonObject.put("roleName", basics.getString("roleName"));
			}
		}
		JSONObject vipInfo = playerRTInfo.getJSONObject("vipInfo");
		if(vipInfo != null){
			if(vipInfo.getLong("vipLevel") != null){
				jsonObject.put("vipLevel", vipInfo.getLong("vipLevel"));
			}
		}
		return jsonObject;
	}
	
	/**
	 * 解析玩家资源信息
	 * @param currencyObj
	 * @return
	 */
	private JSONArray getCurrency(JSONObject currencyObj){
		JSONObject temp = null;
		/*开始解析玩家currency信息*/
		JSONArray currency = new JSONArray();
		temp = new JSONObject();
		temp.put("f1", "钢铁");
		temp.put("f2", currencyObj.get("steel"));
		currency.add(temp);
		temp = new JSONObject();
		temp.put("f1", "钻石");
		temp.put("f2", currencyObj.get("diamond"));
		currency.add(temp);
		temp = new JSONObject();
		temp.put("f1", "石油");
		temp.put("f2", currencyObj.get("oil"));
		currency.add(temp);
		temp = new JSONObject();
		temp.put("f1", "黄金");
		temp.put("f2", currencyObj.get("money"));
		currency.add(temp);
		temp = new JSONObject();
		temp.put("f1", "体力");
		temp.put("f2", currencyObj.get("vitality"));
		currency.add(temp);
		temp = new JSONObject();
		temp.put("f1", "稀土");
		temp.put("f2", currencyObj.get("rare"));
		currency.add(temp);
		temp = new JSONObject();
		temp.put("f1", "能量");
		temp.put("f2", currencyObj.get("power"));
		currency.add(temp);
		return currency;
	}
	
	/**
	 * 获取道具背包信息
	 * @param itemTableObject
	 * @return
	 */
	private JSONArray getItemPlayerBag(JSONObject itemTableObject){
		JSONArray itemTable = new JSONArray();
		for(String key : itemTableObject.keySet()){
			if(!"@type".equals(key)){
				itemTable.add(itemTableObject.getJSONObject(key));
			}
		}
		return itemTable;
	}
	
	/**
	 * 获取装备背包信息
	 * @param equipmentMapObject
	 * @return
	 */
	private JSONArray getEquipmentPlayerBag(JSONObject equipmentMapObject){
		JSONArray equipmentMap = new JSONArray();
		for(String key : equipmentMapObject.keySet()){
			if(!"@type".equals(key)){
				equipmentMap.add(equipmentMapObject.getJSONObject(key));
			}
		}
		return equipmentMap;
	}
	
	/**
	 * 获取科研信息
	 * @param techsObject
	 * @return
	 */
	private JSONArray getTechs(JSONObject techsObject){
		JSONArray techs = new JSONArray();
		for(String key : techsObject.keySet()){
			if(!"@type".equals(key)){
				techs.add(techsObject.getJSONObject(key));
			}
		}
		return techs;
	}
	
	/**
	 * 已激活宝箱
	 * @param techsObject
	 * @return
	 */
	private JSONArray getRewards(JSONObject rewardsObject){
		JSONArray rewards = new JSONArray();
		for(String key : rewardsObject.keySet()){
			if(!"@type".equals(key)){
				rewards.add(rewardsObject.getJSONObject(key));
			}
		}
		return rewards;
	}
	
	/**
	 * 活跃完成状态
	 * @param techsObject
	 * @return
	 */
	private JSONArray getActiveItems(JSONObject activeItemsObject){
		JSONArray activeItems = new JSONArray();
		for(String key : activeItemsObject.keySet()){
			if(!"@type".equals(key)){
				activeItems.add(activeItemsObject.getJSONObject(key));
			}
		}
		return activeItems;
	}
	
	/**
	 * 士兵信息
	 * @param soldiersObject
	 * @return
	 */
	private JSONArray getSoldiers(JSONObject soldiersObject){
		JSONArray soldiers = new JSONArray();
		for(String key : soldiersObject.keySet()){
			if(!"@type".equals(key)){
				JSONObject obj = soldiersObject.getJSONObject(key);
				obj.put("vector", "(x:"+obj.getJSONObject("vector").getIntValue("x")+",y:"+obj.getJSONObject("vector").getIntValue("y")+")");
				soldiers.add(obj);
			}
		}
		return soldiers;
	}
	
	/**
	 * 士兵信息
	 * @param soldiersObject
	 * @return
	 */
	private JSONArray getCommander(JSONObject commanderDataObject){
		JSONArray commander = new JSONArray();
		commander.add(commanderDataObject);
		return commander;
	}
}
