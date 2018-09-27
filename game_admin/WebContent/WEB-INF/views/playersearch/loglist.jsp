<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>玩家日志</title>
<%@include file="../head.jsp"%>
<link rel="stylesheet" href="/static/css/authority.css" type="text/css"></link>
<script>
function searchData(){
	var queryParams = $('#dg-player').datagrid('options').queryParams;
	queryParams.startDt = $("#startDt").val();
	queryParams.endDt = $("#endDt").val();
	queryParams.roleId = $("#roleId").val();
	$('#dg-player').datagrid('reload');
	
	var queryParams2 = $('#dg-log').datagrid('options').queryParams;
	queryParams2.startDt = $("#startDt").val();
	queryParams2.endDt = $("#endDt").val();
	queryParams2.roleId = $("#roleId").val();
	$('#dg-log').datagrid('reload');
}
</script>
</head>
<body>
	<div class="gm-wrap">
		<%@include file="../top.jsp" %>
		<div class="gm-content">
			<%@include file="../left.jsp" %>
			<div class="gm-main">
				<table class="easyui-datagrid" style="height:90px" id="dg-player"
							data-options="singleSelect:true,striped:true,url:'/playersearch/player/playerjson',fitColumns:true,emptyMsg:'暂无数据',toolbar:'#tb'">
					<thead>
						<tr>
							<th data-options="field:'roleId',width:140">角色ID</th>
							<th data-options="field:'roleName',width:177">角色名</th>
							<th data-options="field:'serverArea',width:140">服务器</th>
							<th data-options="field:'vipLevel',width:140">VIP</th>
						</tr>
					</thead>
				</table>
				<div id="tb" style="padding:2px 5px;">
					时间 从: <input class="Wdate" name="startDt" id="startDt" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:140px"/>
					到: <input class="Wdate" name="endDt" id="endDt" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:140px"/>
					角色ID:<input class="easyui-textbox" data-options="prompt:'角色ID'" style="width:140px" name="roleId" id="roleId"/>
					<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="searchData();">查询</a>
				</div>
				
				<table class="easyui-datagrid" style="min-height:600px" id="dg-log"
						data-options="singleSelect:true,striped:true,url:'/playersearch/player/logjson',fitColumns:true,pagination:true,emptyMsg:'暂无数据'">
					<thead>
						<tr>
							<th data-options="field:'createTime',width:100">日期</th>
							<th data-options="field:'content',width:497">日志</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
		<%@include file="../foot.jsp" %>
	</div>
</body>
</html>