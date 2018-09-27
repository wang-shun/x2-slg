<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>运营活动库添加</title>
<%@include file="../head.jsp"%>
<script>
</script>

</head>
<body>
	<div class="gm-wrap">
		<%@include file="../top.jsp" %>
		<div class="gm-content">
			<%@include file="../left.jsp" %>
			<div class="gm-main">
				<form action="/activities/activity/add" method="POST" id="pageForm">
					<div class="easyui-panel">
						<span style="display: block;font-size: 24px;font-weight: bold;text-align: center;">运营活动库添加</span>
						<input class="easyui-textbox" name="serverEvent" style="width:100%;height:120px" labelPosition="top" data-options="label:'活动备注',multiline:true">
						<div class="easyui-panel" title="活动名（标签名，表头名 limitActivity name）" style="padding: 5px 5px;">
							<div style="float: left;"><input class="easyui-textbox" name="zh_CN" style="width:300;" labelPosition="top" data-options="label:'简体中文'"></div>
							<div style="float: left;"><input class="easyui-textbox" name="zh_TW" style="width:300;" labelPosition="top" data-options="label:'繁体中文'"></div>
							<div style="float: left;"><input class="easyui-textbox" name="en" style="width:300;" labelPosition="top" data-options="label:'English'"></div>
							<div style="float: left;"><input class="easyui-textbox" name="ja" style="width:300;" labelPosition="top" data-options="label:'日本语'"></div>
							<div style="float: left;"><input class="easyui-textbox" name="de" style="width:300;" labelPosition="top" data-options="label:'德语'"></div>
							<div style="float: left;"><input class="easyui-textbox" name="ru" style="width:300;" labelPosition="top" data-options="label:'俄语'"></div>
							<div style="float: left;"><input class="easyui-textbox" name="vi" style="width:300;" labelPosition="top" data-options="label:'越南语'"></div>
							<div style="float: left;"><input class="easyui-textbox" name="th" style="width:300;" labelPosition="top" data-options="label:'泰语'"></div>
						</div>
					</div>
					<div class="easyui-panel">
						<select class="easyui-combobox" name="state" label="选择活动类型 limitActivity  table" labelWidth="180" style="width:400px;">
							<option value="排行活动 limitRank">排行活动 limitRank</option>
					        <option value="限购活动 limitShop">限购活动 limitShop</option>
					        <option selected value="福利活动 limitWelfare">福利活动 limitWelfare</option>
						</select>
						<div class="easyui-panel">
							<span style="display: block;font-size: 20px;font-weight: bold;">排名规则</span>
							<div class="easyui-panel" title="面板上方描述 limitActivity desc" style="padding: 5px 5px;">
								<div style="float: left;"><input class="easyui-textbox" name="zh_CN" style="width:300;" labelPosition="top" data-options="label:'简体中文'"></div>
								<div style="float: left;"><input class="easyui-textbox" name="zh_TW" style="width:300;" labelPosition="top" data-options="label:'繁体中文'"></div>
								<div style="float: left;"><input class="easyui-textbox" name="en" style="width:300;" labelPosition="top" data-options="label:'English'"></div>
								<div style="float: left;"><input class="easyui-textbox" name="ja" style="width:300;" labelPosition="top" data-options="label:'日本语'"></div>
								<div style="float: left;"><input class="easyui-textbox" name="de" style="width:300;" labelPosition="top" data-options="label:'德语'"></div>
								<div style="float: left;"><input class="easyui-textbox" name="ru" style="width:300;" labelPosition="top" data-options="label:'俄语'"></div>
								<div style="float: left;"><input class="easyui-textbox" name="vi" style="width:300;" labelPosition="top" data-options="label:'越南语'"></div>
								<div style="float: left;"><input class="easyui-textbox" name="th" style="width:300;" labelPosition="top" data-options="label:'泰语'"></div>
							</div>
							<select class="easyui-combobox" name="state" label="排行类型（军团排行，个人排行）limitRank  belong" labelWidth="180" style="width:400px;">
								<option value="军团">军团</option>
					            <option value="个人">个人</option>
							</select>
							<select class="easyui-combobox" name="state" label="排行类型（任务类型）limitRank type" labelWidth="180" style="width:400px;">
				                <option value="4、生产某级别士兵【V1表示要生产的兵种级别（1~5表示指定等级，为空表示所有等级都行）】【V2列表示需要实际生产的总数量】">4、生产某级别士兵【V1表示要生产的兵种级别（1~5表示指定等级，为空表示所有等级都行）】【V2列表示需要实际生产的总数量】</option>
				                <option value="15、科研提升战力【v2表示提升战斗力的数值】">15、科研提升战力【v2表示提升战斗力的数值】</option>
				                <option value="18、建造提升战力【v2表示提升战斗力的数值】">18、建造提升战力【v2表示提升战斗力的数值】</option>
				                <option value="22、占领领土【v1表示领土原拥有者（1空地2非本军团玩家，为空表示不是同军团的都行）】【v2表示需要成功占领的次数】">22、占领领土【v1表示领土原拥有者（1空地2非本军团玩家，为空表示不是同军团的都行）】【v2表示需要成功占领的次数】</option>
				                <option value="24、野外采集【v1数据表示采集类型（1石油，2稀土，3钢材，4黄金，为空即所有资源都行）】【v2表示完成任务所需量】">24、野外采集【v1数据表示采集类型（1石油，2稀土，3钢材，4黄金，为空即所有资源都行）】【v2表示完成任务所需量】</option>
				                <option value="27、消灭野外怪物【v2列表示需要消灭怪物的具体等级，只有战斗胜利才能判定任务成功】">27、消灭野外怪物【v2列表示需要消灭怪物的具体等级，只有战斗胜利才能判定任务成功】</option>
				                <option value="31、损坏敌方部队【V1表示需要损坏士兵的等级（1~5表示指定等级，为空表示所有等级都行）】【V2列表示累计损坏敌方士兵的数量】">31、损坏敌方部队【V1表示需要损坏士兵的等级（1~5表示指定等级，为空表示所有等级都行）】【V2列表示累计损坏敌方士兵的数量】</option>
				                <option value="41、战争模拟器【V1表示模拟器级别（1普通，2高级，为空所有都行）】【V2列表示在一场中需要胜利的次数】">41、战争模拟器【V1表示模拟器级别（1普通，2高级，为空所有都行）】【V2列表示在一场中需要胜利的次数】</option>
				                <option value="42、军团捐献次数【v1表示捐献类别（1军团，2军团科研）】【v2表示完成任务所需次数】">42、军团捐献次数【v1表示捐献类别（1军团，2军团科研）】【v2表示完成任务所需次数】</option>
				                <option value="49、恐怖分子攻打/击杀【v1表示1攻打2击杀）】【v2表示完成任务需要的次数】">49、恐怖分子攻打/击杀【v1表示1攻打2击杀）】【v2表示完成任务需要的次数】</option>
				                <option value="50、头目入侵积分【v1表示积分类别（1个人2军团）】【v2表示完成任务需要的积分】">50、头目入侵积分【v1表示积分类别（1个人2军团）】【v2表示完成任务需要的积分】</option>
				            </select>
				            <input class="easyui-textbox" name="th" style="width:140px;height:80px;" labelPosition="top" data-options="label:'V1系数limitRank v1'">
				            <input class="easyui-textbox" name="th" style="width:140px;height:80px;" labelPosition="top" data-options="label:'V2系数limitRank v2'">
				            <input class="easyui-textbox" name="th" style="width:140px;height:80px;" labelPosition="top" data-options="label:'排名积分limitRank score'">
						</div>
						<div class="easyui-panel" title="排名奖励">
							<span>名次limitRankRewards rank 最低领奖积分limitRankRewards limit 对应奖励limitRankRewards rewards</span>
							<table class="easyui-datagrid" style="min-height:300px" id="dg"
								data-options="singleSelect:true,striped:true,toolbar:'#tb1'">
							</table>
						</div>
					</div>
				</form>
			</div>
		</div>
		<%@include file="../foot.jsp" %>
	</div>
</body>
</html>