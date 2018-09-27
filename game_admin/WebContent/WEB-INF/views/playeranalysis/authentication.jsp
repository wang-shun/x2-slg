<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>身份验证</title>
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
						data-options="singleSelect:true,striped:true,url:'/playeranalysis/analysis/authenticationjson',fitColumns:true,emptyMsg:'暂无数据',pagination:true">
						<thead>
							<tr>
								<th data-options="field:'dateTime',width:40">日期</th>
								<th data-options="field:'newPlayerNum',width:40">新增玩家账户</th>
								<th data-options="field:'pass',width:40">通过验证</th>
								<th data-options="field:'male',width:40">男</th>
								<th data-options="field:'female',width:40">女</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" style="min-height:200px" id="dg1"
						data-options="singleSelect:true,striped:true,url:'/playeranalysis/analysis/authenticationjsonage',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'ageRound',width:80">年龄段</th>
								<th data-options="field:'newPlayerNum',width:80">新玩家账户（已认证）</th>
								<th data-options="field:'newPercent',width:80">百分比</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" style="min-height:200px" id="dg2"
						data-options="singleSelect:true,striped:true,url:'/playeranalysis/analysis/authenticationjsonarea',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'area',width:80">地区</th>
								<th data-options="field:'newPlayerNum',width:80">新玩家账户（已认证）</th>
								<th data-options="field:'newPercent',width:80">百分比</th>
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