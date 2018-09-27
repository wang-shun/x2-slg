package com.xgame.logic.server.core.language.view.error;

import com.xgame.errorcode.annotation.Description;


/**
 *错误码枚举,每个模块自己单独定义错误码枚举(每个错误码都是由模块号+固定两位错误码)
 *2016-10-12  16:08:30
 *@author ye.yuan
 *
 */
public enum ErrorCodeEnum{

	;
	/**
	 * 通用错误码提示
	 * @author jacky.jiang
	 *
	 */
	public enum E001_LOGIN implements ErrorCodeable {
		
		@Description(desc = "系统错误")
		CODE1(-1),
		
		@Description(desc = "用户为空")
		CODE2(-2),
		
		@Description(desc = "用户名已存在")
		CODE3(-3),
		
		@Description(desc = "用户不存在")
		CODE4(-4),
		
		@Description(desc = "家园在战斗不能使用该功能")
		CODE5(-5),
		
		@Description(desc = "配置表:{},数据:{}不存在")
		CODE6(-6),
		
		@Description(desc = "指定建筑没找到")
		CODE7(-7),
		
		@Description(desc = "家园在战斗不能使用该功能")
		CODE8(-8),
		
		@Description(desc = "兵图纸不存在")
		CODE9(-9),
		
		@Description(desc = "客户端发来数量非法")
		CODE10(-10),
		
		@Description(desc = "资源不足")
		CODE11(-11),
		
		@Description(desc = "道具不足")
		CODE12(-12),
		
		@Description(desc = "没有操作权限")
		CODE13(-13),
		
		@Description(desc = "服务器繁忙")
		CODE14(-14),
		
		@Description(desc = "参数错误")
		CODE15(-15),
		
		@Description(desc = "钻石不足")
		CODE16(-16),
		
		@Description(desc = "道具不能使用")
		CODE17(-17),
		
		@Description(desc = "配置表不存在")
		CODE18(-18),
		
		@Description(desc = "配置表不存在")
		CODE19(-19),
		
		@Description(desc = "联盟不存在")
		CODE20(-20),
		
		@Description(desc = "配置表不一致，无法操作。")
		CODE21(-21),
		
		@Description(desc = "士兵数量不足。")
		CODE22(-22),
		
		@Description(desc = "无效坐标。")
		CODE23(-23),
		
		@Description(desc = "未加入联盟，无法操作。")
		CODE24(-24),
		
		@Description(desc = "时间太短，无法加速。")
		CODE25(-25),
		
		@Description(desc = "该队列不能免费完成")
		CODE26(-26),
		;
		private int type;
		
		
		private E001_LOGIN(int type) {
			this.type = type;
		}
		
		public int get() {
			return type;
		}
	}
	
	/**
	 * 家园错误码
	 * @author jacky.jiang
	 *
	 */
	public enum E100_COUNTRY implements ErrorCodeable {
		
		@Description(desc = "移除建筑队列已满")
		CODE1(-10001),
		
		@Description(desc = "客户端生成的建筑id和服务器不一致")
		CODE2(-10002),
		
		@Description(desc = "建筑配置不存在")
		CODE3(-10003),
		
		@Description(desc = "当前建筑数量{}, 最大建筑数量{}")
		CODE4(-10004),
		
		@Description(desc = "建筑队列已满")
		CODE5(-10005),
		
		@Description(desc = "尝试在该位置放下建筑 失败")
		CODE6(-10006),
		
		@Description(desc = "升级队列已满")
		CODE7(-10007),
		
		@Description(desc = "已是最高等级")
		CODE8(-10008),
		
		@Description(desc = "前置建筑条件不满足")
		CODE9(-10009),
		
		@Description(desc = "升级扣减资源失败")
		CODE10(-10010),
		
		@Description(desc = "升级扣除道具士兵  道具id:{} 道具数量:{}")
		CODE11(-10011),
		
		@Description(desc = "在该点没找到目标建筑")
		CODE12(-10012),
		
		@Description(desc = "非障碍物不能删除")
		CODE13(-10013),
		
		@Description(desc = "科技建筑不存在")
		CODE14(-10014),
		
		@Description(desc = "你比的销毁装甲功能尚未解锁")
		CODE15(-10015),
		
		@Description(desc = "比崽子你没有兵工厂")
		CODE16(-10016),
		
		@Description(desc = "未建造防守驻地不能使用其功能")
		CODE17(-10017),
		
		@Description(desc = "未修建修理厂")
		CODE18(-10018),
		
		@Description(desc = "没有科技馆")
		CODE19(-10019),
		
		@Description(desc = "兵工厂解锁等级未到达")
		CODE20(-10020),
		@Description(desc = "资源未开放，不能切换为该资源")
		CODE21(-10021),
		;

		private int type;
		
		private E100_COUNTRY(int type) {
			this.type=type;
		}
		
		public int get() {
			return type;
		}
	}
	
	/**
	 * 商城购买
	 * @author jacky.jiang
	 *
	 */
	public enum E120_SHOP implements ErrorCodeable {
		@Description(desc = "达到购买上限，已购买次数{}, 购买上线{}")
		CODE1(-12001),
		
		@Description(desc = "商品不存在")
		CODE2(-12002),
		
		@Description(desc = "参数异常")
		CODE3(-12003),
		
		@Description(desc = "商品已下架")
		CODE4(-12004),
		
		@Description(desc = "购买商品资源不足")
		CODE5(-12005),
		
		@Description(desc = "系统兵种未解锁，无法使用宝箱")
		CODE6(-12006),
		;
		
		private int type;
		
		private E120_SHOP(int type) {
			this.type=type;
		}
		
		public int get() {
			return type;
		}

	}
	
	/**
	 * 指挥官系统错误码
	 * @author ye.yuan
	 *
	 */
	public enum E130_COMMANDER implements ErrorCodeable {
		
		@Description(desc = "名字不能有特殊字符")
		CODE1(-13001),

		@Description(desc = "名字首位不能是空格")
		CODE2(-13002),

		@Description(desc = "道具或钻石不足")
		CODE3(-13003),

		@Description(desc = "已有该形象")
		CODE4(-13004),

		@Description(desc = "装备不存在")
		CODE5(-13005),

		@Description(desc = "没有该装备配置")
		CODE6(-13006),

		@Description(desc = "统帅点>等级*2")
		CODE7(-13007),

		@Description(desc = "统帅点已是最大等级")
		CODE8(-13008),

		@Description(desc = "不能这么使用统帅卡 sb")
		CODE9(-13009),

		@Description(desc = "没有天赋点可用")
		CODE10(-13010),
		
		@Description(desc = "形象不存在")
		CODE11(-13011),
		
		@Description(desc = "查看玩家不存在")
		CODE12(-13012),
		
		@Description(desc = "没有该天赋页")
		CODE13(-13013),
		
		@Description(desc = "已是当前天赋页")
		CODE14(-13014),
		
		@Description(desc = "获得天赋页异常")
		CODE15(-13015),
		
		@Description(desc = "不存在的天赋配置或天赋不是当前页下")
		CODE16(-13016),
		
		@Description(desc = "天赋已点满")
		CODE17(-13017),
		
		@Description(desc = "天赋{}没学")
		CODE18(-13018),
		
		@Description(desc = "天赋配置不存在,或 天赋{}没到{}等级")
		CODE19(-13019),
		
		@Description(desc = "已是满级")
		CODE20(-13020),
		
		@Description(desc = "植入体等级不能>指挥官等级")
		CODE21(-13021),
		
		@Description(desc = "修改名字已存在")
		CODE22(-13022),
		
		@Description(desc = "植入体穿戴等级不足,植入体穿戴所需等级{},当前指挥官等级{}")
		CODE23(-13023),
		;
		
		private int type;
		
		private E130_COMMANDER(int type) {
			this.type=type;
		}
		
		public int get() {
			return type;
		}
	}
	
	public enum E300_EQUIP implements ErrorCodeable {

		@Description(desc = "存在没有收取的装备")
		CODE1(-30001),
		
		@Description(desc = "装备材料不存在")
		CODE2(-30002),
		
		@Description(desc = "当前装备不存在")
		CODE3(-30003),
		
		@Description(desc = "合成材料不足")
		CODE4(-30004),
		
		@Description(desc = "材料条件不满足")
		CODE5(-30005),
		
		@Description(desc = "装备材料")
		CODE6(-30006),
		;
		
		private int type;
		
		private E300_EQUIP(int type) {
			this.type=type;
		}
		
		public int get() {
			return type;
		}
	}
	
	public enum E101_CAMP implements ErrorCodeable {
		
		@Description(desc = "建筑还没修好")
		CODE1(-10101),

		@Description(desc = "还有士兵未收取")
		CODE2(-10102),

		@Description(desc = "没找到士兵")
		CODE3(-10103),

		@Description(desc = "正在生产")
		CODE4(-10104),

		@Description(desc = "生产数量过多")
		CODE5(-10105),

		@Description(desc = "没找到组件")
		CODE6(-10106),
		
		@Description(desc = "金钱不足")
		CODE7(-10107),
		
		@Description(desc = "销毁数量不足")
		CODE8(-10108),
		
		@Description(desc = "没有兵待收")
		CODE9(-10109),
		
		@Description(desc = "发送兵数量有误")
		CODE10(-10110),
		
		@Description(desc = "自检名字不合法")
		CODE11(-10111),
		
		@Description(desc = "图纸没有解锁无法生产")
		CODE12(-10112),
		;
		
		private int type;
		
		private E101_CAMP(int type) {
			this.type=type;
		}
		
		public int get() {
			return type;
		}
	}
	
	public enum E1001_CHAT implements ErrorCodeable {
		
		@Description(desc = "聊天目标不能为自己")
		CODE1(-100101),
		
		@Description(desc = "聊天内容超过最大长度{}")
		CODE2(-100102),
		
		@Description(desc = "目标不存在")
		CODE3(-100103),
		
		@Description(desc = "行政大楼等级不足{}")
		CODE4(-100104),
		
		@Description(desc = "发送消息频率太频繁,你稍后再试")
		CODE5(-100105),
		
		@Description(desc = "加入的房间不存在")
		CODE6(-100106),
		
		@Description(desc = "房间密码不正确")
		CODE7(-100107),
		
		@Description(desc = "房间当中玩家不存在")
		CODE8(-100108),
		
		@Description(desc = "房间名称超过最大长度{}")
		CODE9(-100109),
		
		@Description(desc = "在禁入名单当中,不能加入")
		CODE10(-100110),
		
		@Description(desc = "聊天室已解散")
		CODE11(-100111),
		
		@Description(desc = "聊天室达到最大人数上线，无法加入")
		CODE12(-100112),
		
		@Description(desc = "您当前拥有聊天室已达上线")
		CODE13(-100113),
		
		@Description(desc = "申请不存在")
		CODE14(-100114),
		
		@Description(desc = "房间已存在")
		CODE15(-100115),
		
		@Description(desc = "聊天对象为空")
		CODE16(-100116),
		;
		
		private int type;
		
		private E1001_CHAT(int type) {
			this.type=type;
		}
		
		public int get() {
			return type;
		}
	}
	
	/**
	 * 好友
	 * @author jacky.jiang
	 *
	 */
	public enum E1005_FRIEND implements ErrorCodeable {
		
		@Description(desc = "对方已经是你的好友")
		CODE1(-100501),
		
		@Description(desc = "对方已在你的黑名单列表")
		CODE2(-100502),
		
		;
		
		private int type;
		
		private E1005_FRIEND(int type) {
			this.type=type;
		}
		
		public int get() {
			return type;
		}
	}
	
	public enum E132_DEFENS_SOLDIER implements ErrorCodeable {
		
		@Description(desc = "防守驻军数据异常")
		CODE1(-13201),
		
		@Description(desc = "驻防数量配置异常")
		CODE2(-13202),
		
		@Description(desc = "选中兵数量>该等级上限")
		CODE3(-13203),
		;
		
		private int type;
		
		private E132_DEFENS_SOLDIER(int type) {
			this.type=type;
		}

		@Override
		public int get() {
			return type;
		}
	}
	
	public enum E600200_WEAPON implements ErrorCodeable {
		
		@Description(desc="自己武器名字不合法")
		CODE1(-60001),
		
		@Description(desc="自己武器type1 和 客户端发来的不一致")
		CODE2(-60002),

		@Description(desc="发来的地盘 服务器找不到")
		CODE3(-60003),

		@Description(desc="发来的武器异常")
		CODE4(-60004),
		
		@Description(desc="地盘数多个或没有")
		CODE5(-60005),
		
		@Description(desc="行动部件数量和cao1不匹配")
		CODE6(-60006),
		
		@Description(desc="配件安装不符")
		CODE7(-60007),
		
		@Description(desc="没找到兵工厂")
		CODE8(-60008),
		
		@Description(desc="没指定销毁数量")
		CODE9(-60009),
		
		@Description(desc="销毁装甲队列已满")
		CODE10(-60010),
		
		@Description(desc="没找到要销毁的装甲")
		CODE11(-60011),
		
		@Description(desc="销毁装甲所需消耗不够")
		CODE12(-60012),
		
		@Description(desc="没找到配件配置")
		CODE13(-60013),
		
		@Description(desc="改造队列已满")
		CODE14(-60014),
		
		@Description(desc="改造数量异常")
		CODE15(-60015),
		
		@Description(desc="改造士兵找不到")
		CODE16(-60016),
		
		@Description(desc="有配件不存在")
		CODE17(-60017),
		
		@Description(desc="创建图纸buildIndex不正确")
		CODE18(-60018),
		
		@Description(desc="士兵生产中，无法图纸修改。")
		CODE19(-60019),
		
		@Description(desc="士兵改造中，无法图纸修改。")
		CODE20(-60020),
		
		@Description(desc="士兵修理中，无法图纸修改。")
		CODE21(-60021),
		
		@Description(desc="系统兵种图纸未解锁，不能生产图纸。")
		CODE22(-60022),
		;
		
		private int type;
		
		private E600200_WEAPON(int type) {
			this.type=type;
		}

		@Override
		public int get() {
			return type;
		}
	}
	
	/**
	 * 修理厂
	 * @author jacky.jiang
	 *
	 */
	public enum E105_MOD implements ErrorCodeable {
		
		@Description(desc="发来空的修改数据")
		CODE1(-10501),
		
		@Description(desc="修理队列已满")
		CODE2(-10502),
		
		@Description(desc="发送兵种数量与服务器不符")
		CODE3(-10503),
		
		@Description(desc="没找到配件配置")
		CODE4(-10504),
		
		@Description(desc="修理数量太多")
		CODE5(-10505),
		
		@Description(desc="资源修理不够钱")
		CODE6(-10506),
		
		@Description(desc="错误的使用类型")
		CODE7(-10507),
		
		@Description(desc="没找到兵工厂")
		CODE8(-10508),
		
		@Description(desc="没指定销毁数量")
		CODE9(-10509),
		
		@Description(desc="销毁装甲队列已满")
		CODE10(-10510),
		
		@Description(desc="没找到要销毁的装甲")
		CODE11(-10511),
		
		@Description(desc="销毁装甲所需消耗不够")
		CODE12(-10512),
		
		;
		
		private int type;
		
		private E105_MOD(int type) {
			this.type=type;
		}

		@Override
		public int get() {
			return type;
		}
	}
	
	
	public enum E107_TECH implements ErrorCodeable {
		
		@Description(desc="科技配置表没找到")
		CODE1(-10701),
		
		@Description(desc="科技升级队列已满")
		CODE2(-10702),
		
		@Description(desc="科技已最大等级")
		CODE3(-10703),
		
		@Description(desc="科技前置条件未满足")
		CODE4(-10704),
		
		@Description(desc="建筑前置条件未满足")
		CODE5(-10705),
		
		@Description(desc="资源扣除失败")
		CODE6(-10706),
		
		@Description(desc="错误的使用类型")
		CODE7(-10707),
		;
		
		private int type;
		
		private E107_TECH(int type) {
			this.type=type;
		}

		@Override
		public int get() {
			return type;
		}
	}
	
	public enum E410_MAIL implements ErrorCodeable {
		
		@Description(desc="玩家找不到")
		CODE1(-41001),
		
		@Description(desc="发送人不能是自己")
		CODE2(-41002),
		
		@Description(desc="标记成功")
		CODE3(-41003),
		
		@Description(desc="穿戴成功")
		CODE4(-41004),
		
		@Description(desc="查看其他指挥官信息成功")
		CODE5(-41005),
		
		@Description(desc="天赋页使用成功")
		CODE6(-41006),
		
		@Description(desc="你在他黑名单列表里,无法操作")
		CODE7(-41007),
		
		@Description(desc="他在你黑名单列表里,无法操作")
		CODE8(-41008),
		
		@Description(desc="玩家邮件不存在")
		CODE9(-41009),
		
		@Description(desc="页签不存在")
		CODE10(-41010),
		
		
		
		;
		
		private int type;
		
		private E410_MAIL(int type) {
			this.type=type;
		}
		
		public int get() {
			return type;
		}
	}
	
	
	public enum E200_AWARD_CENTER implements ErrorCodeable {
		
		@Description(desc="发来奖励位置错误")
		CODE1(-20001),
		;
		
		private int type;
		
		private E200_AWARD_CENTER(int type) {
			this.type=type;
		}
		
		public int get() {
			return type;
		}
	}
	
	/**
	 * 战斗请求
	 * @author jacky.jiang
	 *
	 */
	public enum E500_WAR implements ErrorCodeable {
		
		@Description(desc="超过当前可出征士兵最大上线{}")
		CODE1(-50001),
		
		@Description(desc="燃油数量不足，不能出征")
		CODE2(-50002),
		
		;
		private int type;
		
		private E500_WAR(int type) {
			this.type=type;
		}

		@Override
		public int get() {
			return type;
		}
	}
	
	
	/**
	 * 战斗请求
	 * @author jacky.jiang
	 *
	 */
	public enum E121_WORLD implements ErrorCodeable {
		
		@Description(desc="出征队列达到上限:{}")
		CODE1(-12101),
		
		@Description(desc="燃油数量不足，不能出征")
		CODE2(-12102),
		
		@Description(desc="出征士兵超上限{}")
		CODE3(-12103),
		
		@Description(desc="士兵数量不足，无法出征")
		CODE4(-12104),
		
		@Description(desc="目的地坐标不存在")
		CODE5(-12105),
		
		@Description(desc="行军队列不存在")
		CODE6(-12106),
		
		@Description(desc="资源石油不足,不能侦查")
		CODE7(-12107),
		
		@Description(desc="超过出兵数量上线{}, 无法出征.")
		CODE8(-12108),
		
		@Description(desc="已有部队前往, 无法出征.")
		CODE9(-12109),
		
		@Description(desc="迁往的坐标点不存在")
		CODE10(-12110),
		
		@Description(desc="雷达不存在，不能侦查")
		CODE11(-12111),
		
		@Description(desc="障碍点信息不可查看")
		CODE12(-12112),
		
		@Description(desc="已有部队前往目的地，请等待部队返回")
		CODE13(-12113),
		
		@Description(desc="联盟不存在,不能占领")
		CODE14(-12114),
		
		@Description(desc="周围没有联盟领地,不能占领")
		CODE15(-12115),
		
		@Description(desc="有部队正在返回，不能牵城")
		CODE16(-12116),
		
		@Description(desc="目标类型与当前行军类型不符,无法出征")
		CODE17(-12117),
		
		@Description(desc="出征类型不存在。")
		CODE18(-12118),
		
		@Description(desc="不是战斗类型的出征，不能派兵。")
		CODE19(-12119),
		
		@Description(desc="贸易资源超过负载上线{}")
		CODE20(-12120),
		
		@Description(desc="不能侦查同盟玩家")
		CODE21(-12121),
		
		@Description(desc="目的地有自己驻扎的部队，不能出征。")
		CODE22(-12122),
		
		@Description(desc="不能攻打同盟玩家")
		CODE23(-12123),
		
		@Description(desc="目的地是同盟玩家不可攻打")
		CODE24(-12124),
		
		@Description(desc="非出征返回状态队列，无法加速")
		CODE25(-12125),
		
		@Description(desc="不能撤回非自己的部队")
		CODE26(-12126),
		
		@Description(desc="非出征状态队列不能撤回")
		CODE27(-12127),
		
		@Description(desc="目的地数据异常")
		CODE28(-12128),
		
		@Description(desc="目的地不是空地，不能迁城")
		CODE29(-12129),
		
		@Description(desc="非pvp活动期间，非本军团主权领地不能迁城")
		CODE30(-12130),
		
		@Description(desc="不满足新手传送条件，不能进行传送")
		CODE31(-12131),
		
		@Description(desc="非军团领土，不能进行军团传送")
		CODE32(-12132),
		
		@Description(desc="发起集结进攻不能向自己派遣部队")
		CODE33(-12133),
		
		@Description(desc="不再集结等待中，不能解散集结队伍")
		CODE34(-12134),
		
		@Description(desc="出征队列不存在，无法解散")
		CODE35(-12135),
		
		@Description(desc="行军队列不在集结等待状态不能解散")
		CODE36(-12136),
		
		@Description(desc="玩家开启了保护罩，无法攻打")
		CODE37(-12137),
		
		@Description(desc="有部队在外面出征，不能使用防御类道具")
		CODE38(-12138),
		
		@Description(desc="战斗中不能撤军")
		CODE39(-12139),
		
		@Description(desc="贸易建筑不存在")
		CODE40(-12140),
		
		@Description(desc="没有外事联络处，不能驻防")
		CODE41(-12141),
		
		@Description(desc="目的地有自己的性机能不能迁城")
		CODE42(-12142),
		
		@Description(desc="恐怖分子已逃跑")
		CODE43(-12143),
		
		@Description(desc="先击杀上一个等级恐怖分子")
		CODE44(-12144),
		
		@Description(desc="通缉奖励已领取过")
		CODE45(-12145),
		
		@Description(desc="未击杀该等级恐怖分子,不能领取奖励")
		CODE46(-12146),
		
		@Description(desc="恐怖分子不存在")
		CODE47(-12147),
		;
		private int type;
		
		private E121_WORLD(int type) {
			this.type=type;
		}

		@Override
		public int get() {
			return type;
		}
	}
	
	/**
	 * 关系错误码
	 * @author ye.yuan
	 *
	 */
	public enum E301_RANDER implements ErrorCodeable {
		
		@Description(desc="有人侦查或被侦查了 但没找到你这个等级的雷达相关配置")
		CODE1(-30101),
		
		@Description(desc="找不到被攻击玩家")
		CODE2(-30102),
		
		@Description(desc="主动侦查玩家没有雷达建筑")
		CODE3(-30103),
		;
		private int type;
		
		private E301_RANDER(int type) {
			this.type=type;
		}

		@Override
		public int get() {
			return type;
		}
	}
	
	/**
	 * 好友
	 * @author ye.yuan
	 *
	 */
	public enum E1006_RELATION implements ErrorCodeable {
		
		@Description(desc="对方已经是你的好友")
		CODE1(-100601),
		
		@Description(desc="对方好友不存在")
		CODE2(-100602),
		
		@Description(desc="对方好友不存在")
		CODE3(-100603),
		
		@Description(desc="好友在黑名单当中")
		CODE4(-100604),
		
		;
		private int type;
		
		private E1006_RELATION(int type) {
			this.type=type;
		}
		
		@Override
		public int get() {
			return type;
		}
	}
	
	/**
	 * 好友
	 * @author ye.yuan
	 *
	 */
	public enum E1007_ALLIANCE implements ErrorCodeable {
		
		@Description(desc="没有搜索到联盟")
		CODE1(-100701),
		
		@Description(desc="联盟申请已经存在")
		CODE2(-100702),
		
		@Description(desc="你已申请太多军团了，可在申请列表中撤销申请")
		CODE3(-100703),
		
		@Description(desc="已在邀请列表当中")
		CODE4(-100704),
		
		@Description(desc="已经加入联盟不能发送申请")
		CODE5(-100705),
		
		@Description(desc="联盟名称超过长度限制")
		CODE6(-100706),
		
		@Description(desc="联盟名称已存在")
		CODE7(-100707),
		
		@Description(desc="联盟简称已存在")
		CODE8(-100708),
		
		@Description(desc="建筑条件未满足，不能创建军团")
		CODE9(-100709),
		
		@Description(desc="资源不足，不能创建军团")
		CODE10(-100710),
		
		@Description(desc="您已加入军团，不能再创建军团")
		CODE11(-100711),
		
		@Description(desc="军团已达人数上限，不能再申请加入")
		CODE12(-100712),
		
		@Description(desc="您已经拥有军团无法再次加入")
		CODE13(-100713),
		
		@Description(desc="联盟资金不足")
		CODE14(-100714),
		
		@Description(desc="权限不足，无法操作.")
		CODE15(-100715),
		
		@Description(desc="不在联盟当中，不能操作。")
		CODE16(-100716),
		
		@Description(desc="达到今日最大捐献次数")
		CODE17(-100717),
		
		@Description(desc="资源不足，无法捐献")
		CODE18(-100718),
		
		@Description(desc="战队不存在")
		CODE19(-100719),
		
		@Description(desc="距离上一次编辑战队信息小于7天")
		CODE20(-100720),
		
		@Description(desc="不是战队队长，不能编辑战队信息")
		CODE21(-100721),
		
		@Description(desc="战队人数超过上线，不能添加成员")
		CODE22(-100722),
		
		@Description(desc="该玩家已在战队中")
		CODE23(-100723),
		
		@Description(desc="玩家不在战队中")
		CODE24(-100724),
		
		@Description(desc="发送公告cd中")
		CODE25(-100725),
		
		@Description(desc="联盟申请不存在")
		CODE26(-100726),
		
		@Description(desc="联盟援建异常")
		CODE27(-100727),
		
		@Description(desc="联盟还有其他成员，军团长不能推出军团，请先转让军团长")
		CODE28(-100728),
		
		@Description(desc="不是队长，不能解散队伍")
		CODE29(-100729),
		
		@Description(desc="队伍异常，无法遣返")
		CODE30(-100730),
		
		@Description(desc="联盟邮件达到今日最大次数")
		CODE31(-100731),
		
		@Description(desc="该玩家已有军团。")
		CODE32(-100732),
		
		@Description(desc="联盟名称不能提交。")
		CODE33(-100733),

		@Description(desc="当前任务已经发送援建，无法再次援建")
		CODE34(-100734),
		
		@Description(desc="联盟名称与现有名称相同，不能修改")
		CODE35(-100735),
		
		@Description(desc="联盟简称与现有简称相同，不能修改")
		CODE36(-100736),
		
		@Description(desc="联盟简称近一月内修改过，不能修改")
		CODE37(-100737),
		
		@Description(desc="联盟旗帜近一月内修改过，不能修改")
		CODE38(-100738),
		
		@Description(desc="联盟简称必须为3个字符")
		CODE39(-100739),
		
		@Description(desc="联盟头衔不能重复")
		CODE40(-100740),
		
		@Description(desc="官员头衔只能分配给R5玩家")
		CODE41(-100741),
		
		@Description(desc="队长只能分配给R4以上的玩家")
		CODE42(-100742),
		;
		private int type;
		
		private E1007_ALLIANCE(int type) {
			this.type=type;
		}
		
		@Override
		public int get() {
			return type;
		}
	}
	

	/**
	 * 任务
	 * @author zehong.he
	 *
	 */
	public enum E1008_TASK implements ErrorCodeable {
		
		@Description(desc="任务不存在")
		CODE1(-100801),
		
		@Description(desc="任务未完成")
		CODE2(-100802),

		@Description(desc="任务奖励已领取")
		CODE3(-100803),

		@Description(desc="宝箱奖励已领取")
		CODE4(-100804),
		
		@Description(desc="任务已开启")
		CODE5(-100805),
		
		@Description(desc="砖石不足")
		CODE6(-100806),
		
		@Description(desc="勋章未点亮")
		CODE7(-100807),
		
		@Description(desc="任务未开启")
		CODE8(-100808),
		
		@Description(desc="任务已完成")
		CODE9(-100809),
		
		@Description(desc="同一时间只能开启一个任务")
		CODE10(-1008010),
		
		;
		
		private int type;
		
		private E1008_TASK(int type) {
			this.type=type;
		}
		
		@Override
		public int get() {
			return type;
		}
	}
	
	/**
	 * 副本
	 * @author zehong.he
	 *
	 */
	public enum E1009_COPY implements ErrorCodeable {
		
		@Description(desc="副本未解锁")
		CODE1(-100901),
		
		@Description(desc="副本不存在")
		CODE2(-100902),
		
		@Description(desc="体力不足")
		CODE3(-100903),

		@Description(desc="宝箱不存在")
		CODE4(-100904),
		
		@Description(desc="星星不足，宝箱未解锁")
		CODE5(-100905),
		
		@Description(desc="宝箱奖励已领取")
		CODE6(-100906),
		
		@Description(desc="星级不足，无法扫荡")
		CODE7(-100907),
		
		
		;
		
		private int type;
		
		private E1009_COPY(int type) {
			this.type=type;
		}
		
		@Override
		public int get() {
			return type;
		}
	}
	
	/**
	 * 联盟扩展（建筑福利）
	 * @author zehong.he
	 *
	 */
	public enum E1100_ALLIANCEEXT implements ErrorCodeable {
		
		@Description(desc="当前点不可用")
		CODE1(-110001),

		@Description(desc="联盟等级未达到解锁要求，不能创建联盟建筑")
		CODE2(-110002),
		
		@Description(desc="非联盟领地，不能创建联盟建筑")
		CODE3(-110003),
		
		@Description(desc="已存在该类型建筑")
		CODE4(-110004),
		
		@Description(desc="该建筑非本联盟建筑")
		CODE5(-110005),
		
		@Description(desc="该建筑不存在")
		CODE6(-110006),
		
		@Description(desc="联盟扩展信息不存在")
		CODE7(-110007),
		
		@Description(desc="已到最大等级，不能升级")
		CODE8(-110008),
		
		@Description(desc="科技大厅不存在")
		CODE9(-110009),
		
		@Description(desc="科技大厅未达到解锁等级")
		CODE10(-110010),
		
		@Description(desc="当前科技已满，请升级科研大厅")
		CODE11(-110011),
		
		@Description(desc="已有部队在演习中")
		CODE12(-110012),
		
		@Description(desc="演习队列不存在")
		CODE13(-110013),
		
		@Description(desc="当日加入军团不可领取活跃奖励")
		CODE14(-110014),
		
		@Description(desc="宝箱不能重复开启")
		CODE15(-110015),
		
		@Description(desc="宝箱不存在")
		CODE16(-110016),
		
		@Description(desc="奖励不能分配给非军团玩家")
		CODE17(-110017),
		
		@Description(desc="奖励数量不足")
		CODE18(-110018),
		
		@Description(desc="已达到购买上限")
		CODE19(-110019),
		
		@Description(desc="贡献不够")
		CODE20(-110020),
		
		@Description(desc="道具不存在")
		CODE21(-110021),
		;
		
		private int type;
		
		private E1100_ALLIANCEEXT(int type) {
			this.type=type;
		}
		
		@Override
		public int get() {
			return type;
		}
	}
}
