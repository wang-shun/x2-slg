<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>付费率分析</title>
<%@include file="../head.jsp"%>
<script>
function searchData(){
	var queryParams = $('#dg').datagrid('options').queryParams;
	queryParams.startDt = $("#startDt").val();
	queryParams.endDt = $("#endDt").val();
	queryParams.channel = $("#channel").val();
	queryParams.serverArea = $("#serverArea").val();
	$('#dg').datagrid('reload');
	
	var queryParams1 = $('#dg1').datagrid('options').queryParams;
	queryParams1.startDt = $("#startDt").val();
	queryParams1.endDt = $("#endDt").val();
	queryParams1.channel = $("#channel").val();
	queryParams1.serverArea = $("#serverArea").val();
	$('#dg1').datagrid('reload');
	
	var queryParams2 = $('#dg2').datagrid('options').queryParams;
	queryParams2.startDt = $("#startDt").val();
	queryParams2.endDt = $("#endDt").val();
	queryParams2.channel = $("#channel").val();
	queryParams2.serverArea = $("#serverArea").val();
	$('#dg2').datagrid('reload');
	
	var queryParams3 = $('#dg3').datagrid('options').queryParams;
	queryParams3.startDt = $("#startDt").val();
	queryParams3.endDt = $("#endDt").val();
	queryParams3.channel = $("#channel").val();
	queryParams3.serverArea = $("#serverArea").val();
	$('#dg3').datagrid('reload');
}
</script>
</head>
<body>
	<div class="gm-wrap">
		<%@include file="../top.jsp" %>
		<div class="gm-content">
			<%@include file="../left.jsp" %>
			<div class="gm-main">
				<div class="easyui-panel">
					<div id="tb" style="padding:2px 5px;background-color: #F5F5F5">
						<select class="easyui-combobox" name="channel" id="channel" label="渠道选择" labelWidth="60" style="width:140px;">
							<option>1</option>
							<option>2</option>
							<option>3</option>
							<option>4</option>
							<option>5</option>
						</select>
						<select class="easyui-combobox" name="serverArea" id="serverArea" label="服务器选择" labelWidth="70" style="width:140px;">
							<option>1</option>
							<option>2</option>
							<option>3</option>
							<option>4</option>
							<option>5</option>
						</select>
						时间 从: <input class="Wdate" name="startDt" id="startDt" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
						到: <input class="Wdate" name="endDt" id="endDt" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="searchData();">查询</a>
					</div>
					<table class="easyui-datagrid" style="min-height:200px" id="dg"
								data-options="singleSelect:true,striped:true,url:'/playeranalysis/analysis/chargeratejson',fitColumns:true,emptyMsg:'暂无数据',pagination:true">
						<thead>
							<tr>
								<th data-options="field:'dateTime',width:40">日期</th>
								<th data-options="field:'newPlayers',width:40">新增账户</th>
								<th data-options="field:'chargeNum1',width:40">首日付费</th>
								<th data-options="field:'chargeNum2',width:40">2日付费</th>
								<th data-options="field:'chargeNum3',width:40">3日付费</th>
								<th data-options="field:'chargeNum4',width:40">4日付费</th>
								<th data-options="field:'chargeNum5',width:40">5日付费</th>
								<th data-options="field:'chargeNum6',width:40">6日付费</th>
								<th data-options="field:'chargeNum7',width:40">7日付费</th>
								<th data-options="field:'chargeNumFst',width:40">首月付费</th>
								<th data-options="field:'chargeNumSnd',width:40">二月付费</th>
								<th data-options="field:'chargeNumTrd',width:40">三月付费</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" style="min-height:200px" id="dg1"
								data-options="singleSelect:true,striped:true,url:'/playeranalysis/analysis/chargeratejsontime',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'firstChargeTime',width:80">首次付费游戏时间</th>
								<th data-options="field:'playerNum',width:80">付费角色数</th>
								<th data-options="field:'percent',width:80">百分比</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" style="min-height:200px" id="dg2"
								data-options="singleSelect:true,striped:true,url:'/playeranalysis/analysis/chargeratejsongift',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'giftType',width:80">首充礼包选择</th>
								<th data-options="field:'giftId',width:80">礼包ID</th>
								<th data-options="field:'number',width:80">数量</th>
								<th data-options="field:'income',width:80">收入￥</th>
								<th data-options="field:'percent',width:80">数量百分比</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" style="min-height:200px" id="dg3"
								data-options="singleSelect:true,striped:true,url:'/playeranalysis/analysis/chargeratejsonlevel',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'level',width:80">首付行政大楼等级</th>
								<th data-options="field:'playerNum',width:80">付费账户数</th>
								<th data-options="field:'percent',width:80">百分比</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
		<%@include file="../foot.jsp" %>
	</div>
</body>
</html>