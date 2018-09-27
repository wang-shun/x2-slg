package com.xgame.framework.protocal;


public class Messges {
 
//	/**
//	 * protocols
//	 */
//	@MsgStruct
//	public static final class MsgString {
//		@MsgField(Id = 1)
//		public String Value;
//	}
//
//	@MsgStruct
//	public static final class MsgBytes {
//		@MsgField(Id = 1)
//		public byte[] Value;
//	}
//
//	@MsgStruct
//	public static final class MsgInt32 {
//		@MsgField(Id = 1)
//		public int Value;
//	}
//
//	@MsgStruct
//	public static final class MsgInt64 {
//		@MsgField(Id = 1)
//		public long Value;
//	}
//	
//	
//	
//	
//	@MsgStruct
//	public static final class MsgRequestGameServerInfo {
//		@MsgField(Id = 1)
//		public String username;
//		
////		@MsgField(Id = 2)
////		public int area;//游戏区,默认0 , 一般是在游戏内创建新角色时才会有具体值
//	}
//	
//	@MsgStruct
//	public static final class MsgCommonInfo {
//		@MsgField(Id = 1)
//		public String content;
//		
//		@MsgField(Id = 2)
//		public int type;//0 error 1 msg
//	}
//
//	// /////////游戏服务器信息
//	@MsgStruct
//	public static final class MsgGameServerInfo {
//		@MsgField(Id = 1)
//		public String ip;
//
//		@MsgField(Id = 2)
//		public int port;
//		
//		@MsgField(Id = 3)
//		public long roleid;
//	}
//
//	// Town && Building
//	@MsgStruct
//	public static final class MsgPosition {
//		@MsgField(Id = 1)
//		public float x;
//
//		@MsgField(Id = 2)
//		public float y;
//	}
//
//	// ////////登录用户凭证
//	@MsgStruct
//	public static final class MsgLogin {
//		@MsgField(Id = 1)
//		public String username;
//
//		@MsgField(Id = 2)
//		public String password;
//		
//		@MsgField(Id = 2)
//		public Long roleid;
// 
//	}
//
//	// /////////角色基本信息
//	@MsgStruct
//	public static final class MsgUserInfo {
//		@MsgField(Id = 1)
//		public String nickname;
//
//		@MsgField(Id = 2)
//		public float resSY;
//
//		@MsgField(Id = 3)
//		public float resXT;
//
//		@MsgField(Id = 4)
//		public float resGC;
//
//		@MsgField(Id = 5)
//		public float resCP;
//
//		@MsgField(Id = 6)
//		public float resZS;
//
//		@MsgField(Id = 7)
//		public long spriteId;
//
//		@MsgField(Id = 8)
//		public MsgPosition worldLocation;
//
//		@MsgField(Id = 9)
//		public String union;// 联盟
//		
//		@MsgField(Id = 10)
//		public MsgPlayerBag playerBag; // 背包
//
//		@MsgField(Id = 11)
//		public MsgPlayerEquipmentBag playerEquipmentBag; // 背包
//		
//		@MsgField(Id = 12)
//		public int server;// 所在区
//		
//		@MsgField(Id = 13)
//		public MsgPlayerEquipmentFragmentBag playerEquipmentFragmentBag; // 装备碎片背包
//	}
//	
//	// 角色基地内的建筑物们
//	@MsgStruct
//	public static final class ReqGameServerInfoMessage1 {
//		@MsgField(Id = 1)
//		public String userName;
//	}
//
//	// 角色当前的建筑队列
//	@MsgStruct
//	public static final class MsgProduce {
//		@MsgField(Id = 1)
//		public ProduceType type;
//
//		@MsgField(Id = 2)
//		public float starttime;
//
//		@MsgField(Id = 3)
//		public float endtime;
//
//		@MsgField(Id = 4)
//		public String name;
//
//		@MsgField(Id = 5)
//		public int num;
//
//		@MsgField(Id = 6)
//		public long buildingid;
//
//		@MsgField(Id = 7)
//		public float speed;
//	}
//
//	// 服务器向客户端推送精灵对象信息, 被动或主动推送
//	@MsgStruct
//	public static final class MsgSprite {
//		@MsgField(Id = 1)
//		public List<SingleMsgSprite> spriteList;
//	}
//
//	@MsgStruct
//	public static final class SingleMsgSprite {
//		@MsgField(Id = 1)
//		public Long spriteId;// 精灵id
//
//		@MsgField(Id = 2)
//		public int type;// 精灵类型 资源, 玩家
//
//		@MsgField(Id = 3)
//		public String name;// 精灵名字
//
//		@MsgField(Id = 4)
//		public int level;// 精灵等级
//
//		@MsgField(Id = 5)
//		public String sovereign;// 主权
//
//		@MsgField(Id = 6)
//		public String union;// 联盟
//
//		@MsgField(Id = 7)
//		public List<MsgSpriteAward> awardList;// 奖励 比如怪物
//
//		@MsgField(Id = 8)
//		public MsgPosition worldLocation;// 精灵坐标
//
//		@MsgField(Id = 9)
//		public MsgSpriteAttachmentInfo attachmentInfo;// 精灵附属信息 , 比如谁在采集,驻防
//														// 以及和我的关系
//
//		@MsgField(Id = 10)
//		public int state;// 精灵状态, 比如战争, 要冒烟
//
//		@MsgField(Id = 11)
//		public int showType;// 显示类型 , 比如资源的石油,稀土,钻石,电力
//	}
//
//	@MsgStruct
//	public static final class MsgSpriteAttachmentInfo {
//		@MsgField(Id = 1)
//		public int type;
//
//		@MsgField(Id = 2)
//		public int relationship;
//	}
//
//	@MsgStruct
//	public static final class MsgSpriteAward {
//		@MsgField(Id = 1)
//		public int itemId;
//
//		@MsgField(Id = 2)
//		public int num;
//	}
//
//	// 资源点的资源信息,支持多个
//	@MsgStruct
//	public static final class MsgSpriteResource {
//		@MsgField(Id = 1)
//		public Long spriteId;
//
//		@MsgField(Id = 2)
//		public List<MsgSpriteResourceChunk> resourceList;// 资源列表
//	}
//
//	@MsgStruct
//	public static final class MsgSpriteResourceChunk {
//		@MsgField(Id = 1)
//		public int type;// 资源类型
//
//		@MsgField(Id = 2)
//		public int curNum;// 剩余数量
//
//		@MsgField(Id = 3)
//		public int maxNum;// 总量
//
//		@MsgField(Id = 4)
//		public Long resID;// 资源id
//
//		@MsgField(Id = 5)
//		public List<MsgResourceCollectLog> collectList;// 采集日志
//	}
//
//	@MsgStruct
//	public static final class MsgResourceCollectLog {
//
//		@MsgField(Id = 1)
//		public int time;
//
//		@MsgField(Id = 2)
//		public String union;
//
//		@MsgField(Id = 3)
//		public String playerName;
//
//		@MsgField(Id = 4)
//		public Long playerRoleId;
//
//		@MsgField(Id = 5)
//		public int leftResNum;
//	}
//
//	// 其他玩家精灵详细信息
//	@MsgStruct
//	public static final class MsgPlayerSpriteDetail {
//		@MsgField(Id = 1)
//		public Long spriteId;
//	}
//
//	// 删除精灵
//	@MsgStruct
//	public static final class MsgRemoveSprite {
//		@MsgField(Id = 1)
//		public Long spriteId;
//
//	}
//
//	// 客户端向server请求当前位置sprite信息 , 坐标为地图的格子坐标
//	public static final class MsgPullSprite {
//		@MsgField(Id = 1)
//		public int tileX;
//
//		public int tileY;
//	}
//
//	// 客户端向server请求精灵信息(资源 , 其他玩家)
//	public static final class MsgPullSpriteInfo {
//		@MsgField(Id = 1)
//		public Long spriteId;
//
//	}
//
//	// 服务器推送当前服行军信息
//	@MsgStruct
//	public static final class MsgMapWalkTimerTask {
//
//		@MsgField(Id = 7)
//		public List<MsgMapWalkTimerTaskChunk> walkList;
//	}
//	
//	@MsgStruct
//	public static final class MsgMapWalkTimerTaskChunk {
//
//		@MsgField(Id = 1)
//		public Long taskId;
//		//
//		@MsgField(Id = 2)
//		public int taskType;
//
//		@MsgField(Id = 3)
//		public MsgPosition targetLocation;
//
//		@MsgField(Id = 4)
//		public MsgPosition sourceLocation;
//
//		@MsgField(Id = 5)
//		public float startTime;
//
//		@MsgField(Id = 6)
//		public float endTime;
//	}
//	
//	// 使用道具
//	@MsgStruct
//	public static final class MsgUseItem {
//		
//		@MsgField(Id = 1)
//		public long id;
//		//
//		@MsgField(Id = 2)
//		public int num;
//		
//		@MsgField(Id = 3)
//		public int itemId;
//		
//	}
//	// 单个道具
//	@MsgStruct
//	public static final class MsgItem {
//		
//		@MsgField(Id = 1)
//		public long id;
//		//
//		@MsgField(Id = 2)
//		public int itemId;
//		
//		@MsgField(Id = 3)
//		public int num;
//		
//		@MsgField(Id = 4)
//		public long startTime;
//		
//	}
//	
//	/**
//	 * 更新道具列表
//	 * @author lin.lin
//	 *
//	 */
//	@MsgStruct
//	public static final class MsgItemList {
//		@MsgField(Id = 1)
//		public List<MsgItem> items;
//	}
//	
//	// 背包
//	@MsgStruct
//	public static final class MsgPlayerBag {
//
//		@MsgField(Id = 1)
//		public int size;
//		//
//		@MsgField(Id = 2)
//		public List<MsgItem> items;
//		
//	}
//	
//	// 单个装备
//	@MsgStruct
//	public static final class MsgEquipment {
//		
//		@MsgField(Id = 1)
//		public long id;
//		//
//		@MsgField(Id = 2)
//		public int equipmentId;
//		
//	}
//	
//	// 装备背包
//	@MsgStruct
//	public static final class MsgPlayerEquipmentBag {
//		//	
//		@MsgField(Id = 1)
//		public List<MsgEquipment> equipments;
//	}
//	
//	// 装备碎片背包
//	@MsgStruct
//	public static final class MsgPlayerEquipmentFragmentBag {
//		//
//		@MsgField(Id = 1)
//		public List<MsgEquipmentFragment> equipmentFragments;
//
//	}
//	
//	// 单个装备碎片
//	@MsgStruct
//	public static final class MsgEquipmentFragment {
//
//		@MsgField(Id = 1)
//		public long id;
//		//
//		@MsgField(Id = 2)
//		public int itemId;
//
//		@MsgField(Id = 3)
//		public int num;
//	}
//
//	/*
//	 * 通用消息包
//	 */
//	@MsgStruct
//	public static final class MsgCommon {
//
//		@MsgField(Id = 1)
//		public int code;
//		//
//		@MsgField(Id = 2)
//		public String value;
//
//	}
//	
//	@MsgStruct
//	public static final class MsgAttribute {
//		
//	}
//	
//	/*
//	 * 
//	 */
//	@MsgStruct
//	public static final class ResAllCountryMessage {
//
//		@MsgField(Id = 1)
//		public int useUid;
//		//
//		@MsgField(Id = 2)
//		public List<CountryBean> countrys = new ArrayList<>();
//		
//		@MsgField(Id = 3)
//		public List<BuildBean> builds = new ArrayList<>();
//
//	}
//	
//	@MsgStruct
//	public static final class CountryBean {
//		@MsgField(Id = 1)
//		public int uid;
//
//		@MsgField(Id = 2)
//		public List<TransformBean> transforms = new ArrayList<>();
//	}
//
//	@MsgStruct
//	public static final class BuildBean {
//		@MsgField(Id = 1)
//		public int uid;
//		
//		@MsgField(Id = 2)
//		public int sid;
//
//		@MsgField(Id = 3)
//		public int level;
//		
//		@MsgField(Id = 4)
//		public int state;
//		
//		@MsgField(Id = 5)
//		public long timerUid;
//	}
//	
//	@MsgStruct
//	public static final class  TransformBean {
//		@MsgField(Id = 1)
//		public int uid;
//		
//		@MsgField(Id = 2)
//		public Vector2Bean vec2s = new Vector2Bean();
//	}
//	
//	@MsgStruct
//	public static final class  Vector2Bean {
//		
//		@MsgField(Id = 1)
//		public int  x;
//		
//		@MsgField(Id = 2)
//		public int y;
//	}
//	
//	
//	/*
//	 * 
//	 */
//	@MsgStruct
//	public static final class ReqCreateBuildMessage {
//
//		@MsgField(Id = 1)
//		public int  uid;
//		
//		@MsgField(Id = 2)
//		public int sid;
//		
//		@MsgField(Id = 3)
//		public int x;
//		
//		@MsgField(Id = 4)
//		public int y;
//		
//		@MsgField(Id = 5)
//		public int createType;
//		
//		@MsgField(Id = 6)
//		public int templateId;
//	}
//	
//	@MsgStruct
//	public static final class ReqRemoveBuildMessage {
//
//		@MsgField(Id = 1)
//		public int  uid;
//		
//		@MsgField(Id = 2)
//		public int sid;
//	}
//	
//	/*
//	 * 
//	 */
//	@MsgStruct
//	public static final class ReqMoveBuildMessage {
//
//		@MsgField(Id = 1)
//		public int  uid;
//		
//		@MsgField(Id = 2)
//		public int x;
//		
//		@MsgField(Id = 3)
//		public int y;
//		
//		@MsgField(Id = 4)
//		public int templateId;
//		
//		@MsgField(Id = 5)
//		public int  sid;
//	}
//	
//	@MsgStruct
//	public static final class ReqCountryHandleMessage {
//
//		@MsgField(Id = 1)
//		public int  templateId;
//		
//		@MsgField(Id = 2)
//		public String type;
//		
//		@MsgField(Id = 3)
//		public int uid;
//		
//	}
//	
//	@MsgStruct
//	public static final class ReqBuildingCollect {
//		@MsgField(Id = 1)
//		public int uid;
//		
//		@MsgField(Id = 2)
//		public int sid;
//		
//	}
//	
//	
//	@MsgStruct
//	public static final class ReqRemoveObstructMessage {
//
//		@MsgField(Id = 1)
//		public int uid;
//		@MsgField(Id = 2)
//		public int sid;
//	}
//	
//	@MsgStruct
//	public static final class ReqLevelUpBuildMessage {
//		@MsgField(Id = 1)
//		public int uid;
//		@MsgField(Id = 2)
//		public int sid;
//		@MsgField(Id = 3)
//		public int createType;
//	}
//	
//	@MsgStruct
//	public static final class ResLevelUpBuildMessage {
//		@MsgField(Id = 1)
//		public int uid;
//		@MsgField(Id = 2)
//		public int level;
//	}
//	
//	
//	@MsgStruct
//	public static final class ResAttrbuteMessage {
//		@MsgField(Id = 1)
//		public List<AttrbuteBean> attrbuteBeans = new ArrayList<>();
//	}
//	
//	@MsgStruct
//	public static final class AttrbuteBean {
//		@MsgField(Id = 1)
//		public int attributeId;
//		@MsgField(Id = 2)
//		public int attributeValue;
//		@MsgField(Id = 3)
//		public List<SystemAttributeBean> systemAttributeBeans = new ArrayList<>();
//	}
//	
//	@MsgStruct
//	public static final class SystemAttributeBean {
//		@MsgField(Id = 1)
//		public int systemId;
//		@MsgField(Id = 2)
//		public double confSubX;
//		@MsgField(Id = 3)
//		public double systemSubValue;
//		@MsgField(Id = 4)
//		public List<SubAttrbuteBean> addxs = new ArrayList<>();
//	}
//	
//	
//	@MsgStruct
//	public static final class SubAttrbuteBean {
//		@MsgField(Id = 1)
//		public int systemId;
//		@MsgField(Id = 2)
//		public double value;
//		@MsgField(Id = 3)
//		public List<Integer> soldierIds = new ArrayList<>();
//	}
//	
//	@MsgStruct
//	public static final class ResAllTechMessage {
//		@MsgField(Id = 1)
//		public List<TechBean> techs = new ArrayList<>();
//	}
//
//	@MsgStruct
//	public static final class TechBean {
//		@MsgField(Id = 1)
//		public int sid;
//		@MsgField(Id = 2)
//		public int level;
//		@MsgField(Id = 3)
//		public long timerUid;
//		@MsgField(Id = 4)
//		public int state;
//	}
//	
//	@MsgStruct
//	public static final class ResLevelUpTechMessage {
//		@MsgField(Id = 1)
//		public int sid;
//		@MsgField(Id = 2)
//		public int level;
//	}
//	
//	@MsgStruct
//	public static final class ReqLevelUpTechMessage {
//		@MsgField(Id = 1)
//		public int sid;
//		@MsgField(Id = 2)
//		public int useType;
//	}
//	
//	@MsgStruct
//	public static final class ResCurrencyMessage {
//		@MsgField(Id = 1)
//		public long resSY;
//
//		@MsgField(Id = 2)
//		public long resXT;
//
//		@MsgField(Id = 3)
//		public long resGC;
//
//		@MsgField(Id = 4)
//		public long resCP;
//
//		@MsgField(Id = 5)
//		public long resZS;
//		
//		@MsgField(Id = 6)
//		public long fightPower;
//	}
//	
//	public static final class ResAllTimerMessage{
//		@MsgField(Id = 1)
//		public List<TimerBean> timers = new ArrayList<>();
//	}
//	
//	public static final class ResTimerMessage{
//		@MsgField(Id = 1)
//		public TimerBean updateTimeBean;
//	}
//	
//	public static final class ResCancelMessage{
//		@MsgField(Id = 1)
//		public long timerUid;
//		
//		@MsgField(Id = 2)
//		public int queueId;
//		
//		@MsgField(Id = 3)
//		public int eType;
//
//	}
//	
//	public static final class  TimerBean{
//		@MsgField(Id = 1)
//		public long timerUid;
//
//		@MsgField(Id = 2)
//		public int endTime;
//		
//		@MsgField(Id = 3)
//		public int eType;
//
//		@MsgField(Id = 4)
//		public int queueId;
//
//		@MsgField(Id = 5)
//		public int uid;
//		
//		@MsgField(Id = 6)
//		public int num;
//		
//		@MsgField(Id = 7)
//		public int startTime;
//	}
//	
//	public static final class  CampOutputTimerBean{
//		@MsgField(Id = 5)
//		public int num;
//	}
//	
//	public static final class ResTimeSystemMessage{
//		@MsgField(Id = 1)
//		public int serverTime1970s;
//	}
//	
//	/**
//	 * 分解装备
//	 * @author kevin.ouyang
//	 *
//	 */
//	@MsgStruct
//	public static final class MsgDecomposeEquipment {
//		
//		@MsgField(Id = 1)
//		public long id;
//	}
//	
//	/**
//	 * 合成装备
//	 * @author kevin.ouyang
//	 *
//	 */
//	@MsgStruct
//	public static final class MsgComposeEquipment {
//		@MsgField(Id = 1)
//		public List<SubComposeEquipment> subComposeEquipments;
//
//		@MsgField(Id = 2)
//		public List<ComposeEquipmentMaterial> materials;
//	}
//
//	@MsgStruct
//	public static final class SubComposeEquipment {
//		@MsgField(Id = 1)
//		public long id;
//
//		@MsgField(Id = 2)
//		public boolean isTarget;
//	}
//
//	@MsgStruct
//	public static final class ComposeEquipmentMaterial {
//		@MsgField(Id = 1)
//		public long id;
//
//		@MsgField(Id = 2)
//		public int num;
//	}
//
//	public static final class ReqCancelTimerTaskMessage {
//		@MsgField(Id = 1)
//		public long timerUid;
//	}
//	
//	public static final class ReqUseSpeedItemMessage{
//		@MsgField(Id = 1)
//		public long timerUid;
//		@MsgField(Id = 2)
//		public long itemUid;
//		@MsgField(Id = 3)
//		public int num;
//		@MsgField(Id = 4)
//		public int itemId;
//	}
//	
//	public static final class ReqDiamondSpeedMessage{
//		@MsgField(Id = 1)
//		public long timerUid;
//	}
//	
//	
//	
//	public static final class ResCampInfoMessage{
//		@MsgField(Id = 1)
//		public CampBean campBean;
//	}
//	
//	public static final class CampBean{
//		@MsgField(Id = 1)
//		public int campType;
//		@MsgField(Id = 2)
//		public List<SoldierBean> soldierBeans = new ArrayList<>();
//		@MsgField(Id = 3)
//		public List<SoldierBean> singleOutputBeans = new ArrayList<>();
//		@MsgField(Id = 4)
//		public List<ArmsFactoryOwnBean> campownBeans = new ArrayList<>();
//	}
//	
//	public static final class ResCampGiveSoldierMessage{
//		@MsgField(Id = 1)
//		public int campType;
//		@MsgField(Id = 2)
//		public List<ArmsFactoryOwnBean> giveSoldierBean = new ArrayList<>();
//	}
//	
//	public static final class ArmsFactoryOwnBean{
//		@MsgField(Id = 1)
//		public int soldierId;
//		@MsgField(Id = 2)
//		public int number;
//		@MsgField(Id = 3)
//		public int collectNum;
//	}
//	
//	public static final class SoldierBean{
//		@MsgField(Id = 1)
//		public int soldierId;
//		@MsgField(Id = 2)
//		public int num;
//		@MsgField(Id = 3)
//		public String name;
//		@MsgField(Id = 4)
//		public List<PeijianBean> peijians = new ArrayList<>();
//	}
//	
//	public static final class PeijianBean{
//		@MsgField(Id = 1)
//		public int peijianId;
//		@MsgField(Id = 2)
//		public int location;
//	}
//	
//	public static final class ResCampOutputMessage{
//		@MsgField(Id = 1)
//		public int soldierId;
//		@MsgField(Id = 2)
//		public int campType;
//		@MsgField(Id = 3)
//		public boolean type;
//		@MsgField(Id = 4)
//		public int num;
//	}
//	
//	public static final class ResCampCreateMessage{
//		@MsgField(Id = 1)
//		public int campType;
//		@MsgField(Id = 2)
//		public boolean type;
//		@MsgField(Id = 3)
//		public SoldierBean soldierBean;
//	}
//	
//	public static final class ReqCampOutputMessage{
//		@MsgField(Id = 1)
//		public int soldierId;
//		@MsgField(Id = 2)
//		public int campType;
//		@MsgField(Id = 3)
//		public int num;
//		@MsgField(Id = 4)
//		public boolean type;
//		@MsgField(Id = 5)
//		public int useType;
//	}
//	
//	public static final class ReqCampCreateMessage{
//		@MsgField(Id = 1)
//		public int soldierId;
//		@MsgField(Id = 2)
//		public int campType;
//		@MsgField(Id = 3)
//		public boolean type;
//		@MsgField(Id = 4)
//		public int useType;
//		@MsgField(Id = 5)
//		public List<PeijianBean> peijians = new ArrayList<>();
//	}
//	
//	@MsgStruct
//	public static final class MsgGM{
//		
//		@MsgField(Id = 1)
//		public String params;
//		
//	}
//
//	// 商店配置
//	@MsgStruct
//	public static final class MsgShopItem {
//
//		@MsgField(Id = 1)
//		public int id;
//		@MsgField(Id = 2)
//		public int shop_number;
//		@MsgField(Id = 3)
//		public List<MsgItem> items;
//		@MsgField(Id = 4)
//		public int name;
//		@MsgField(Id = 5)
//		public int quality;
//		@MsgField(Id = 6)
//		public int description;
//		@MsgField(Id = 7)
//		public int type;
//		@MsgField(Id = 8)
//		public int price;
//		@MsgField(Id = 9)
//		public int tag;
//		@MsgField(Id = 10)
//		public int special_price;
//		@MsgField(Id = 11)
//		public int fast_price;
//		@MsgField(Id = 12)
//		public int ceili;
//		@MsgField(Id = 13)
//		public String icon;
//		@MsgField(Id = 14)
//		public long time;
//
//	}
//
//	@MsgStruct
//	public static final class MsgShopInfo {
//		
//		@MsgField(Id = 1)
//		public List<MsgShopItem> shopItems = new ArrayList<MsgShopItem>();
//
//		// 特卖道具数量
//		@MsgField(Id = 2)
//		public int specialNum;
//		
//	}
//	
//	// 购买商品
//	@MsgStruct
//	public static final class MsgBuyItem {
//		
//		@MsgField(Id = 1)
//		public int id;
//
//		@MsgField(Id = 2)
//		public int num;
//		
//	}
//	
//	@MsgStruct
//	public static final class ReqModSoldierMessage {
//		@MsgField(Id = 1)
//		public int useType;
//		// 特卖开始时间
//		@MsgField(Id = 2)
//		public List<ModFactoryBean> mfb = new ArrayList<>();
//		
//	}
	
	
//	@MsgStruct
//	public static final class ResModSoldierMessage {
//		// 特卖开始时间
//		@MsgField(Id = 1)
//		public List<ModFactoryBean> mfb= new ArrayList<>();
//		
//	}
//	
//	@MsgStruct
//	public static final class ModFactoryBean {
//		@MsgField(Id = 1)
//		public int campType;
//		// 特卖开始时间
//		@MsgField(Id = 2)
//		public List<ModSoldierBean> msb = new ArrayList<>();
//		
//	}
//	
//	@MsgStruct
//	public static final class ModSoldierBean {
//		@MsgField(Id = 1)
//		public int soldierId;
//		// 特卖开始时间
//		@MsgField(Id = 2)
//		public int num;
//	}
//	
//	@MsgStruct
//	public static final class ResTitleMessage {
//		@MsgField(Id = 1)
//		public int languageId;
//		@MsgField(Id = 2)
//		public String utf;
//		@MsgField(Id = 3)
//		public List<String> styles = new ArrayList<String>();
//	}
//	
	
	//------------------------------------------------------
//	@MsgStruct
//	public static final class ResAward {
//		@MsgField(Id = 1)
//		public long id;
//		@MsgField(Id = 2)
//		public int itemId;
//		@MsgField(Id = 3)
//		public int num;
//		@MsgField(Id = 4)
//		public long startTime;
//	}
//	
//	@MsgStruct
//	public static final class ResAwardCenter {
//		@MsgField(Id = 1)
//		public int max;
//		@MsgField(Id = 2)
//		public List<ResAward> awardList;
//	}
//	
//	@MsgStruct
//	public static final class ReqGetAward{
//		@MsgField(Id = 1)
//		public int id;
//	}
	
	
	
}
