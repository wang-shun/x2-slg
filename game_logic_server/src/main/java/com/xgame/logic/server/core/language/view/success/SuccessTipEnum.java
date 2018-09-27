package com.xgame.logic.server.core.language.view.success;

import com.xgame.errorcode.annotation.Description;


/**
 *错误码枚举,每个模块自己单独定义错误码枚举
 *2016-10-12  16:08:30
 *@author ye.yuan
 *
 */
public enum SuccessTipEnum {

	;
	/**
	 * 家园错误码
	 * @author jacky.jiang
	 *
	 */
	public enum E109_ITEM implements SuccessTip {
		
		@Description(desc="道具使用成功")
		CODE1(10901),
		
		@Description(desc="道具加速成功")
		CODE2(10902),
		
		@Description(desc="购买成功")
		CODE3(10903),
		;
		private int type;
		
		private E109_ITEM(int type) {
			this.type=type;
		}
		
		public int get() {
			return type;
		}
		
	}
	
	public enum E101_CAMP implements SuccessTip {
		
		@Description(desc="收取兵成功")
		CODE1(10101),
		
		@Description(desc="士兵销毁成功")
		CODE2(10102),
		
		@Description(desc="修改士兵成功")
		CODE3(10103),
		
		@Description(desc="开始生产")
		CODE4(10104),
		
		@Description(desc="生产成功")
		CODE5(10105),
		;

		private int type;
		
		private E101_CAMP(int type) {
			this.type=type;
		}
		
		public int get() {
			return type;
		}
		
	}
	
	public enum E600_WEAPON implements SuccessTip {
		
		@Description(desc="改造图纸成功")
		CODE1(60001),
		
		@Description(desc="装甲销毁完成")
		CODE2(60002),
		
		@Description(desc="改造兵成功")
		CODE3(60003),
		;

		private int type;
		
		private E600_WEAPON(int type) {
			this.type=type;
		}
		
		public int get() {
			return type;
		}
		
	}
	
	public enum E100_COUNTRY implements SuccessTip {
		
		@Description(desc="创建成功")
		CODE1(10001),
		
		@Description(desc="建造完成")
		CODE2(10002),
		
		@Description(desc="建筑开始升级")
		CODE3(10003),
		
		@Description(desc="移动建筑成功")
		CODE4(10004),
		
		@Description(desc="移除建筑开始")
		CODE6(10005),
		;

		private int type;
		
		private E100_COUNTRY(int type) {
			this.type=type;
		}
		
		public int get() {
			return type;
		}
		
	}
	
	public enum E132_DEFENS implements SuccessTip {
		
		@Description(desc="成功设置自动防御")
		CODE1(13201),
		
		@Description(desc="成功设置驻防部队")
		CODE2(13202),
		
		@Description(desc="卸载驻防部队成功")
		CODE3(13203),
		;
		
		private int type;
		
		private E132_DEFENS(int type) {
			this.type=type;
		}
		
		public int get() {
			return type;
		}
			
	}
	
	public enum E107_TECH implements SuccessTip {
		
		@Description(desc="升级科技开始")
		CODE1(10701),
		;
		
		private int type;
		
		private E107_TECH(int type) {
			this.type=type;
		}
		
		public int get() {
			return type;
		}
			
	}
	
	public enum E105_MOD implements SuccessTip {
		
		@Description(desc="开始修理")
		CODE1(10501),
		;
		
		private int type;
		
		private E105_MOD(int type) {
			this.type=type;
		}
		
		public int get() {
			return type;
		}
	}
	
	/**
	 * 装备
	 * @author jacky.jiang
	 *
	 */
	public enum E300_EQUIP implements SuccessTip {
		
		@Description(desc="植入体锻造开始")
		CODE1(30001),
		
		@Description(desc="植入体合成成功")
		CODE2(30002),
		
		@Description(desc="材料合成成功")
		CODE3(30003),
		
		@Description(desc="植入体锻造成功")
		CODE4(30004),
		;
		
		private int type;
		
		private E300_EQUIP(int type) {
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
	public enum E120_SHOP implements SuccessTip {
		
		@Description(desc="商城购买")
		CODE1(12001),
		;
		
		private int type;
		
		private E120_SHOP(int type) {
			this.type=type;
		}
		
		public int get() {
			return type;
		}
	}
	
	public enum E130_COMMANDER implements SuccessTip {
		
		@Description(desc="使用统帅卡成功")
		CODE1(13001),
		
		@Description(desc="改名成功")
		CODE2(13002),
		
		@Description(desc="改形象成功")
		CODE3(13003),
		
		@Description(desc="穿戴成功")
		CODE4(13004),
		
		@Description(desc="查看其他指挥官信息成功")
		CODE5(13005),
		
		@Description(desc="天赋页使用成功")
		CODE6(13006),
		
		@Description(desc="天赋页切换成功")
		CODE7(13007),
		
		@Description(desc="天赋加添成功")
		CODE8(13008),
		
		@Description(desc="卸载装备成功")
		CODE9(13009),
		
		@Description(desc="重置成功")
		CODE10(13010),
		;
		
		private int type;
		
		private E130_COMMANDER(int type) {
			this.type=type;
		}
		
		public int get() {
			return type;
		}
	}
	
	public enum E410_MAIL implements SuccessTip {
		
		@Description(desc="发送全服邮件成功")
		CODE1(41001),
		
		@Description(desc="删除成功")
		CODE2(41002),
		
		@Description(desc="标记成功")
		CODE3(41003),
		
		@Description(desc="发送成功")
		CODE4(41004),

		;
		
		private int type;
		
		private E410_MAIL(int type) {
			this.type=type;
		}
		
		public int get() {
			return type;
		}
	}
	
	public enum E200_AWARD_CENTER implements SuccessTip {
		
		@Description(desc="领取成功")
		CODE1(20001),
		
		;
		
		private int type;
		
		private E200_AWARD_CENTER(int type) {
			this.type=type;
		}
		
		public int get() {
			return type;
		}
	}
	
	public enum E500_WAR implements SuccessTip {
		
		@Description(desc="查找玩家成功.")
		CODE1(50001),
		
		
		@Description(desc="请求攻打成功.")
		CODE2(50002),
		;
		
		private int type;
		
		private E500_WAR(int type) {
			this.type=type;
		}
		
		public int get() {
			return type;
		}
	}
	
	public enum E121_WORLD implements SuccessTip {
		
		@Description(desc="查找世界地图成功.")
		CODE1(12101),
		
		@Description(desc="攻打出征成功.")
		CODE2(12102),
		
		@Description(desc="侦查出征成功.")
		CODE3(12103),
		
		@Description(desc="查看出征部队信息.")
		CODE4(12104),
		
		@Description(desc="采集出征成功.")
		CODE5(12105),
		
		@Description(desc="出征成功.")
		CODE6(12106),
		;
		
		private int type;
		
		private E121_WORLD(int type) {
			this.type=type;
		}
		
		public int get() {
			return type;
		}
	}
	
	
	public enum E1006_RELATION implements SuccessTip {
		
		@Description(desc="添加好友成功.")
		CODE1(100601),
		;
		
		private int type;
		
		private E1006_RELATION(int type) {
			this.type=type;
		}
		
		public int get() {
			return type;
		}
	}
	
	/**
	 * 联盟
	 * @author jacky.jiang
	 *
	 */
	public enum E1007_ALLIANCE implements SuccessTip {
		
		@Description(desc="联盟搜索成功")
		CODE1(100701),
		
		@Description(desc="联盟申请成功")
		CODE2(100702),
		
		@Description(desc="军团创建成功，努力将军团发扬光大吧")
		CODE3(100703),
		
		@Description(desc="处理军团申请成功")
		CODE4(100704),
		
		@Description(desc="获取邀请和申请信息成功")
		CODE5(100705),
		
		@Description(desc="取消申请成功")
		CODE6(100706),
		
		@Description(desc="联盟升级成功，联盟升级到{}级")
		CODE7(100707),
		
		@Description(desc="捐献成功，军团资金+{}，军团贡献+{}")
		CODE8(100708),
		
		@Description(desc="处理军团申请成功")
		CODE9(100709),
		
		@Description(desc="军团申请成功")
		CODE10(100710),
		
		@Description(desc="编辑战队成功")
		CODE11(100711),
		
		@Description(desc="添加成员成功")
		CODE12(100712),
		
		@Description(desc="剔除战队成员")
		CODE13(100713),
		
		@Description(desc="转换军团长")
		CODE14(100714),
		
		@Description(desc="编辑头衔")
		CODE15(100715),
		
		@Description(desc="编辑联盟信息")
		CODE16(100716),
		
		@Description(desc="发送联盟帮助")
		CODE17(100717),
		
		@Description(desc="联盟帮助")
		CODE18(100718),
		
		@Description(desc="撤职成功")
		CODE19(100719),
		
		@Description(desc="编辑权限成功")
		CODE20(100720),
		
		@Description(desc="开启自动审批")
		CODE21(100721),
		
		@Description(desc="军团邮件发送成功，今日剩余{}次")
		CODE22(100722),
		
		@Description(desc="处理军团邀请成功")
		CODE23(100723),
		
		@Description(desc="已拒绝加入")
		CODE24(100724),
		
		@Description(desc="取消自动审批")
		CODE25(100725),
		
		@Description(desc="捐献成功，科技经验+{}，军团贡献+{}")
		CODE26(100726),
		;
		
		private int type;
		
		private E1007_ALLIANCE(int type) {
			this.type=type;
		}
		
		public int get() {
			return type;
		}
	}
	
}
