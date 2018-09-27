<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>用户日志列表</title>
<%@include file="../head.jsp"%>
<link rel="stylesheet" href="/static/css/authority.css" type="text/css"></link>
<script>
$(function(){
	$("#dg").datagrid({
		url:"/backadmin/userlog/json",
		columns:[[
			{field:'userName',title:'操作人',width:80},
			{field:'createDate',title:'操作时间',width:140},
			{field:'ip',title:'IP',width:140},
			{field:'actionDetail',title:'操作',width:140},
		]],
		fitColumns:true,
		pagination:true,
		pageList:[10,20,50,100],
		pageSize:20,
		pageNumber:1,
		toolbar:'#tb',
	});
})

function searchData(){
	var queryParams = $('#dg').datagrid('options').queryParams;
	queryParams.startDt = $("#startDt").val();
	queryParams.endDt = $("#endDt").val();
	queryParams.realName = $("#realName").val();
	queryParams.actionDetail = $("#actionDetail").val();
	$('#dg').datagrid('reload');
}
</script>
</head>
<body>
	<div class="gm-wrap">
		<%@include file="../top.jsp" %>
		<div class="gm-content">
			<%@include file="../left.jsp" %>
			<div class="gm-main">
				<table id="dg"></table>
				<div id="tb" style="padding:2px 5px;">
					时间 从: <input class="Wdate" name="startDt" id="startDt" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
					到: <input class="Wdate" name="endDt" id="endDt" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
					用户名:<input class="easyui-textbox" data-options="prompt:'用户名:'" style="width:110px" name="realName" id="realName"/>
					操作:<input class="easyui-textbox" data-options="prompt:'操作:'" style="width:110px" name="actionDetail" id="actionDetail"/>
					<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="searchData();">查询</a>
				</div>
			</div>
		</div>
		<%@include file="../foot.jsp" %>
	</div>
</body>
</html>