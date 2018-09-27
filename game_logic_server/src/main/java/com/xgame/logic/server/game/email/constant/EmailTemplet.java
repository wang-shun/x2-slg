package com.xgame.logic.server.game.email.constant;




/**
 * 邮件常量
 * @author jacky.jiang
 *
 */
public class EmailTemplet {
	
	/**
	 * 战斗异常邮件
	 */
	public static final int 战斗异常_MAIL_ID = 2;
	
	/**
	 * {}为你发放了军团战事奖励，感谢你一直以来为军团繁荣做出的贡献！再接再厉！\n\n战事奖励
	 */
	public static final int 军团战事奖励_MAIL_ID = 1001;
	
	/**
	 * {}任命你为新的{}
	 */
	public static final int 军团任命_MAIL_ID = 1002;
	
	/**
	 * {}撤销了对你{}的任命
	 */
	public static final int 军团撤销任命_MAIL_ID = 1003;
	
	/**
	 * {}将你调整为{}级军团成员
	 */
	public static final int 军团成员调级_MAIL_ID = 1004;
	
	/**
	 * {}批准了你的入团申请，恭喜你成为了{}的军团成员
	 */
	public static final int 批准入团申请_MAIL_ID = 1005;
	
	
	/**
	 * {}拒绝了你加入{}的申请，别气馁，金子在哪都会发光！
	 */
	public static final int 拒绝入团申请_MAIL_ID = 1006;
	
	/**
	 * {}成为了{}新的军团长！
	 */
	public static final int 设置新军团_MAIL_ID = 1007;
	
	/**
	 * {}将{}的军团长职位转让给了你，恭喜你成为新的军团长
	 */
	public static final int 军团长职位转让_MAIL_ID = 1008;
	
	/**
	 * 您被{}移出了{}，良禽择木，自有留爷处
	 */
	public static final int 移出军团_MAIL_ID = 1009;
	
	/**
	 * {}邀请你加入{}，请及时在军团邀请列表中处理。
	 */
	public static final int 军团邀请_MAIL_ID = 1010;
	
	/**
	 * {}将你加入了{}成为了战队一员。
	 */
	public static final int 加入战队_MAIL_ID = 1011;
	
	/**
	 * {}将你移出了{}。
	 */
	public static final int 移出战队_MAIL_ID = 1012;
	
	/**
	 * 你的部队已返回，累计演习时间{}\n\n演习奖励
	 */
	public static final int 部队演习奖励1_MAIL_ID = 1014;
	
	/**
	 * 你的部队已返回，累计演习时间{}\n\n因演习时间太短未获得奖励。
	 */
	public static final int 部队演习奖励2_MAIL_ID = 1015;
	
	/**
	 * 恭喜你达成了军团事件宝箱积分要求，获得一份奖励。奖励已发放到领奖中心，注意领取\n\n军团事件奖励
	 */
	public static final int 军团事件奖励1_MAIL_ID = 2001;
	
	/**
	 * 恭喜你的军团在军团事件中积分排名达到{}名，获得一份奖励。奖励已发放到领奖中心，注意领取\n\n军团事件排名奖励
	 */
	public static final int 军团事件排名奖励1_MAIL_ID = 2002;
	
	/**
	 * 恭喜你达成了个人事件宝箱积分要求，获得一份奖励。奖励已发放到领奖中心，注意领取\n\n个人事件奖励
	 */
	public static final int 个人事件奖励1_MAIL_ID = 2003;
	
	/**
	 * 恭喜你在个人事件中积分排名达到{}名，获得一份奖励。奖励已发放到领奖中心，注意领取\n\n个人事件排名奖励
	 */
	public static final int 个人事件排名奖励_MAIL_ID = 2004;
	
	/**
	 * 恭喜你达成了荣耀事件宝箱积分要求，获得一份奖励。奖励已发放到领奖中心，注意领取\n\n荣耀事件奖励
	 */
	public static final int 荣耀事件奖励_MAIL_ID = 2005;
	
	/**
	 * 恭喜你在军团事件中积分排名达到{}名，获得一份奖励。奖励已发放到领奖中心，注意领取\n\n荣耀事件排名奖励
	 */
	public static final int 荣耀事件排名奖励_MAIL_ID = 2006;
	
	
	//-----------------------------野外行军类邮件------------------------------/

	/**
	 * 侦查失败，部队抵达时，该坐标上已不是出发时刻的资源点或采集部队
	 */
	public static final int 侦查失败资源_MAIL_ID = 3001;
	
	/**
	 * 进攻失败，部队抵达时，该坐标上已不是出发时刻的资源点或采集部队
	 */
	public static final int 进攻失败资源_MAIL_ID = 3002;
	
	/**
	 * 侦查失败，部队抵达时，该坐标上已不是出发时刻的玩家基地
	 */
	public static final int 侦查失败基地_MAIL_ID = 3003;
	
	/**
	 * 进攻失败，部队抵达时，该坐标上已不是出发时刻的玩家基地
	 */
	public static final int 进攻失败基地_MAIL_ID = 3004;
	
	/**
	 * 集结进攻失败，部队抵达时，该坐标上已不是出发时刻的玩家基地
	 */
	public static final int 集结进攻失败基地_MAIL_ID = 3005;
	
	/**
	 * 驻防失败，部队抵达时，该坐标上已不是出发时刻的玩家基地
	 */
	public static final int 驻防失败基地_MAIL_ID = 3006;
	
	/**
	 * 贸易失败，部队抵达时，该坐标上已不是出发时刻的玩家基地。资源将会自动带回。
	 */
	public static final int 贸易失败基地_MAIL_ID = 3007;
	
	/**
	 * 侦查失败，部队抵达时，该坐标上已不是出发时刻的玩家占领
	 */
	public static final int 侦查失败占领_MAIL_ID = 3008;
	
	/**
	 * 进攻失败，部队抵达时，该坐标上已不是出发时刻的玩家占领
	 */
	public static final int 进攻失败占领_MAIL_ID = 3009;
	
	/**
	 * 侦查失败，部队抵达时，该坐标上已不是出发时刻的玩家扎营
	 */
	public static final int 侦查失败扎营_MAIL_ID = 3010;
	
	/**
	 * 进攻失败，部队抵达时，该坐标上已不是出发时刻的玩家扎营
	 */
	public static final int 进攻失败扎营_MAIL_ID = 3011;
	
	/**
	 * 侦查失败，部队抵达时，该坐标上已不是出发时刻的恐怖分子或恐怖分子已逃跑
	 */
	public static final int 侦查报告恐怖分子_MAIL_ID = 3012;
	
	/**
	 * 进攻失败，部队抵达时，该坐标上已不是出发时刻的恐怖分子或恐怖分子已被击杀。
	 */
	public static final int 进攻失败恐怖分子_MAIL_ID = 3013;
	
	/**
	 *演习失败，部队抵达时，该坐标上已无演习基地或不是本军团演习基地。
	 */
	public static final int 演习失败_MAIL_ID = 3014;
	
	/**
	 *采集失败，部队抵达时，该坐标上已无军团超级矿或不是本军团的超级矿。
	 */
	public static final int 采集失败_MAIL_ID = 3015;
	
	/**
	 *侦查失败，部队抵达时，【执政军团争夺战】已结束，【世界政府】已进入保护状态
	 */
	public static final int 侦查失败_世界政府_MAIL_ID = 3016;
	
	/**
	 *进攻失败，部队抵达时，【执政军团争夺战】已结束，【世界政府】已进入保护状态
	 */
	public static final int 进攻失败_世界政府_MAIL_ID = 3017;
	
	/**
	 *进攻失败，部队抵达时，【执政军团争夺战】已结束，【世界政府】已进入保护状态
	 */
	public static final int 集结进攻失败_世界政府_MAIL_ID = 3018;
	
	/**
	 *防御失败，部队抵达时，【执政军团争夺战】已结束，【世界政府】已进入保护状态
	 */
	public static final int 防御失败_世界政府_MAIL_ID = 3019;
	
	/**
	 *防御失败，部队抵达时，【执政军团争夺战】已结束，【世界政府】已进入保护状态
	 */
	public static final int 集结防御失败_世界政府_MAIL_ID = 3020;
	
	
	
	//---------------------------侦查报告------------------------------
	
	/**
	 * 基地侦察报告
	 */
	public static final int 基地侦察报告_MAIL_ID = 50000;
	
	/**
	 * 基地被侦察报告
	 */
	public static final int 基地被侦察报告_MAIL_ID = 50001;
	
	/**
	 * 资源点侦察报告
	 */
	public static final int 资源点侦察报告_MAIL_ID = 50002;
	
	/**
	 * 资源点被侦察报告
	 */
	public static final int 资源点被侦察报告_MAIL_ID = 50003;
	
	/**
	 * 营地侦察报告
	 */
	public static final int 营地侦察报告_MAIL_ID = 50004;
	
	/**
	 * 营地被侦察报告
	 */
	public static final int 营地被侦察报告_MAIL_ID = 50005;
	
	/**
	 * 领地侦察报告
	 */
	public static final int 领地侦察报告_MAIL_ID = 50006;
	
	/**
	 * 领地被侦察报告
	 */
	public static final int 领地被侦察报告_MAIL_ID = 50007;
	
	/**
	 * 怪物侦察报告
	 */
	public static final int 怪物侦察报告_MAIL_ID = 50008;
	
	/**
	 * 侦查保护报告
	 */
	public static final int 侦查保护报告_MAIL_ID = 50009;
	
	
	//---------------------------进攻报告------------------------------
	
	/**
	 * 基地进攻报告
	 */
	public static final int 基地进攻报告_MAIL_ID = 60000;
	
	/**
	 * 基地防守报告
	 */
	public static final int 基地防守报告_MAIL_ID = 60001;
	
	/**
	 * 资源点进攻报告
	 */
	public static final int 资源点进攻报告_MAIL_ID = 60002;
	
	/**
	 * 资源点防守报告
	 */
	public static final int 资源点防守报告_MAIL_ID = 60003;
	
	/**
	 * 营地进攻报告
	 */
	public static final int 营地进攻报告_MAIL_ID = 60005;
	
	/**
	 * 营地防守报告
	 */
	public static final int 营地防守报告_MAIL_ID = 60006;
	
	/**
	 * 怪物进攻报告胜利
	 */
	public static final int 怪物进攻报告胜利_MAIL_ID = 60007;
	
	/**
	 * 怪物进攻报告失败
	 */
	public static final int 怪物进攻报告失败_MAIL_ID = 60008;
	
	/**
	 * 基地集结进攻胜利报告
	 */
	public static final int 基地集结进攻报告_MAIL_ID = 60009;
	
	/**
	 * 基地集结防守胜利报告
	 */
	public static final int 基地集结防守报告_MAIL_ID = 60010;
	
	/**
	 * 怪物集结进攻报告
	 */
	public static final int 怪物集结进攻报告_MAIL_ID = 60011;
	
	/**
	 * 怪物集结防守报告
	 */
	public static final int 怪物集结防守报告_MAIL_ID = 60012;
	
	/**
	 * 采集报告
	 */
	public static final int 采集报告_MAIL_ID = 70000;
	
	/**
	 * 占领成功
	 */
	public static final int 占领成功_MAIL_ID = 70001;
	
	/**
	 * 驻防报告
	 */
	public static final int 驻防报告_MAIL_ID = 70002;
	
	/**
	 * 撤防报告
	 */
	public static final int 撤防报告_MAIL_ID = 70003;
	
	/**
	 * 贸易报告
	 */
	public static final int 贸易报告_MAIL_ID = 70004;
	
	/**
	 * 防守失败
	 */
	public static final int 防守失败_MAIL_ID = 70005;
	
	/**
	 * 防守胜利
	 */
	public static final int 防守胜利_MAIL_ID = 70006;
	
}
