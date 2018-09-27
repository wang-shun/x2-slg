<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>存留分析</title>
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
					<div class="easyui-panel">
						
					</div>
					<table class="easyui-datagrid" title="存留" style="min-height:200px" id="dg"
						data-options="singleSelect:true,striped:true,url:'/playeranalysis/analysis/retentionjson',fitColumns:true,emptyMsg:'暂无数据',pagination:true">
						<thead>
							<tr>
								<th data-options="field:'dateTime',width:40">日期</th>
								<th data-options="field:'playerNum',width:40">首日账户</th>
								<th data-options="field:'retention2',width:40">2日存留</th>
								<th data-options="field:'retention3',width:40">3日存留</th>
								<th data-options="field:'retention4',width:40">4日存留</th>
								<th data-options="field:'retention5',width:40">5日存留</th>
								<th data-options="field:'retention6',width:40">6日存留</th>
								<th data-options="field:'retention7',width:40">7日存留</th>
								<th data-options="field:'retention8',width:40">8日存留</th>
								<th data-options="field:'retention9',width:40">9日存留</th>
								<th data-options="field:'retention10',width:40">10日存留</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" title="回流用户" style="min-height:200px" id="dg1"
						data-options="singleSelect:true,striped:true,url:'/playeranalysis/analysis/retentionjsonplayer',fitColumns:true,emptyMsg:'暂无数据',pagination:true">
						<thead>
							<tr>
								<th data-options="field:'dateTime',width:40">日期</th>
								<th data-options="field:'retentionPlayerNum',width:40">回流用户</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" title="新手流失用户-累计游戏时间" style="min-height:200px" id="dg2"
						data-options="singleSelect:true,striped:true,url:'/playeranalysis/analysis/retentionjsonnewleavetime',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'gameTime',width:80">游戏时间</th>
								<th data-options="field:'playerNum',width:80">流失账户</th>
								<th data-options="field:'operate2',width:80">操作</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" title="新手流失用户-累计启动次数" style="min-height:200px" id="dg3"
						data-options="singleSelect:true,striped:true,url:'/playeranalysis/analysis/retentionjsonnewleavestart',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'startNum',width:80">启动次数</th>
								<th data-options="field:'playerNum',width:80">流失账户</th>
								<th data-options="field:'operate3',width:80">操作</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" title="新手流失用户-建筑等级之和" style="min-height:200px" id="dg4"
						data-options="singleSelect:true,striped:true,url:'/playeranalysis/analysis/retentionjsonnewleavelevel',fitColumns:true,emptyMsg:'暂无数据',pagination:true">
						<thead>
							<tr>
								<th data-options="field:'buildLevel',width:80">建筑等级之和</th>
								<th data-options="field:'playerNum',width:80">流失账户</th>
								<th data-options="field:'operate4',width:80">操作</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" title="新手存留用户-累计游戏时间" style="min-height:200px" id="dg5"
						data-options="singleSelect:true,striped:true,url:'/playeranalysis/analysis/retentionjsonnewstaytime',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'gameTime',width:80">游戏时间</th>
								<th data-options="field:'playerNum',width:80">存留账户</th>
								<th data-options="field:'operate5',width:80">操作</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" title="新手存留用户-累计启动次数" style="min-height:200px" id="dg6"
						data-options="singleSelect:true,striped:true,url:'/playeranalysis/analysis/retentionjsonnewstaystart',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'startNum',width:80">启动次数</th>
								<th data-options="field:'playerNum',width:80">存留账户</th>
								<th data-options="field:'operate6',width:80">操作</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" title="新手存留用户-建筑等级之和" style="min-height:200px" id="dg7"
						data-options="singleSelect:true,striped:true,url:'/playeranalysis/analysis/retentionjsonnewstaylevel',fitColumns:true,emptyMsg:'暂无数据',pagination:true">
						<thead>
							<tr>
								<th data-options="field:'buildLevel',width:80">建筑等级之和</th>
								<th data-options="field:'playerNum',width:80">存留账户</th>
								<th data-options="field:'operate7',width:80">操作</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" style="min-height:200px" id="dg8"
						data-options="singleSelect:true,striped:true,url:'/playeranalysis/analysis/retentionjsonlifecycle',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'lifeCycle',width:80">流失用户生命周期</th>
								<th data-options="field:'playerNum',width:80">用户数量</th>
								<th data-options="field:'percent',width:80">百分比</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" style="min-height:200px" id="dg9"
						data-options="singleSelect:true,striped:true,url:'/playeranalysis/analysis/retentionjsonlevel',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'buildLevel',width:80">流失用户行政大楼等级</th>
								<th data-options="field:'playerNum',width:80">用户数量</th>
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