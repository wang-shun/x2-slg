<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>充值查询</title>
<%@include file="../head.jsp"%>
<link rel="stylesheet" href="/static/css/authority.css" type="text/css"></link>
<script>
function searchData(){
	var queryParams = $('#dg').datagrid('options').queryParams;
	queryParams.roleId = $("#roleId").val();
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
				<table class="easyui-datagrid" style="min-height:600px" id="dg"
							data-options="singleSelect:true,striped:true,url:'/operation/chargesearch/listjson',fitColumns:true,pagination:true,emptyMsg:'暂无数据',toolbar:'#tb'">
					<thead>
						<tr>
							<th data-options="field:'chargeTime',width:80">时间</th>
							<th data-options="field:'chargeMoney',width:80">充值金额</th>
							<th data-options="field:'roleId',width:80">角色ID</th>
							<th data-options="field:'roleName',width:120">角色名</th>
							<th data-options="field:'serverArea',width:80">服务器</th>
							<th data-options="field:'deserveDiamond',width:140">应发钻石</th>
						</tr>
					</thead>
				</table>
				<div id="tb" style="padding:2px 5px;">
					角色ID:<input class="easyui-textbox" data-options="prompt:'角色ID'" style="width:140px" name="roleId" id="roleId"/>
					<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="searchData();">查询</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>