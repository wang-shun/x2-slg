<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>收入数据</title>
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
	
	var queryParams4 = $('#dg4').datagrid('options').queryParams;
	queryParams4.startDt = $("#startDt").val();
	queryParams4.endDt = $("#endDt").val();
	queryParams4.channel = $("#channel").val();
	queryParams4.serverArea = $("#serverArea").val();
	$('#dg4').datagrid('reload');
	
	var queryParams5 = $('#dg5').datagrid('options').queryParams;
	queryParams5.startDt = $("#startDt").val();
	queryParams5.endDt = $("#endDt").val();
	queryParams5.channel = $("#channel").val();
	queryParams5.serverArea = $("#serverArea").val();
	$('#dg5').datagrid('reload');
	
	var queryParams6 = $('#dg6').datagrid('options').queryParams;
	queryParams6.startDt = $("#startDt").val();
	queryParams6.endDt = $("#endDt").val();
	queryParams6.channel = $("#channel").val();
	queryParams6.serverArea = $("#serverArea").val();
	$('#dg6').datagrid('reload');
	
	var queryParams7 = $('#dg7').datagrid('options').queryParams;
	queryParams7.startDt = $("#startDt").val();
	queryParams7.endDt = $("#endDt").val();
	queryParams7.channel = $("#channel").val();
	queryParams7.serverArea = $("#serverArea").val();
	$('#dg7').datagrid('reload');
	
	var queryParams8 = $('#dg8').datagrid('options').queryParams;
	queryParams8.startDt = $("#startDt").val();
	queryParams8.endDt = $("#endDt").val();
	queryParams8.channel = $("#channel").val();
	queryParams8.serverArea = $("#serverArea").val();
	$('#dg8').datagrid('reload');
	
	var queryParams9 = $('#dg9').datagrid('options').queryParams;
	queryParams9.startDt = $("#startDt").val();
	queryParams9.endDt = $("#endDt").val();
	queryParams9.channel = $("#channel").val();
	queryParams9.serverArea = $("#serverArea").val();
	$('#dg9').datagrid('reload');
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
						data-options="singleSelect:true,striped:true,url:'/incomeanalysis/analysis/incomelistjson',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'channel',width:40">渠道收入</th>
								<th data-options="field:'income',width:40">付费金额￥</th>
								<th data-options="field:'percent',width:40">百分比</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" style="min-height:200px" id="dg1"
						data-options="singleSelect:true,striped:true,url:'/incomeanalysis/analysis/incomelistjsonday',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'dateTime',width:80">日期</th>
								<th data-options="field:'income',width:80">收入金额</th>
								<th data-options="field:'chargeCount',width:80">充值次数</th>
								<th data-options="field:'accountNum',width:80">充值账户</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" style="min-height:200px" id="dg2"
						data-options="singleSelect:true,striped:true,url:'/incomeanalysis/analysis/incomelistjsonbuild',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'level',width:80">付费行政大楼等级</th>
								<th data-options="field:'chargeNum',width:80">付费金额￥</th>
								<th data-options="field:'chargeCount',width:80">付费次数</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" style="min-height:200px" id="dg3"
						data-options="singleSelect:true,striped:true,url:'/incomeanalysis/analysis/incomelistjsoncommander',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'level',width:80">付费指挥官等级</th>
								<th data-options="field:'chargeNum',width:80">付费金额￥</th>
								<th data-options="field:'chargeCount',width:80">付费次数</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" style="min-height:200px" id="dg4"
						data-options="singleSelect:true,striped:true,url:'/incomeanalysis/analysis/incomelistjsonweek',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'count',width:80">每周充值次数</th>
								<th data-options="field:'accountNum',width:80">付费账户</th>
								<th data-options="field:'percent',width:80">百分比</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" style="min-height:200px" id="dg5"
						data-options="singleSelect:true,striped:true,url:'/incomeanalysis/analysis/incomelistjsonmonth',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'count',width:80">每月充值次数</th>
								<th data-options="field:'accountNum',width:80">付费账户</th>
								<th data-options="field:'percent',width:80">百分比</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" style="min-height:200px" id="dg6"
						data-options="singleSelect:true,striped:true,url:'/incomeanalysis/analysis/incomelistjsonweeksum',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'sum',width:80">每周充值额度</th>
								<th data-options="field:'accountNum',width:80">付费账户</th>
								<th data-options="field:'percent',width:80">百分比</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" style="min-height:200px" id="dg7"
						data-options="singleSelect:true,striped:true,url:'/incomeanalysis/analysis/incomelistjsonmonthsum',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'sum',width:80">每月充值额度</th>
								<th data-options="field:'accountNum',width:80">付费账户</th>
								<th data-options="field:'percent',width:80">百分比</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" style="min-height:200px" id="dg8"
						data-options="singleSelect:true,striped:true,url:'/incomeanalysis/analysis/incomelistjsontimeround',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'timeRound',width:80">充值间隔</th>
								<th data-options="field:'accountNum',width:80">付费账户</th>
								<th data-options="field:'percent',width:80">百分比</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" style="min-height:200px" id="dg9"
						data-options="singleSelect:true,striped:true,url:'/incomeanalysis/analysis/incomelistjsongiftround',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'giftRound',width:80">充值分档</th>
								<th data-options="field:'count',width:80">数量</th>
								<th data-options="field:'income',width:80">收入￥</th>
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