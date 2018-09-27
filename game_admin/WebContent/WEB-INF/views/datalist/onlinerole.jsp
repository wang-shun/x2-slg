<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>在线角色</title>
<%@include file="../head.jsp"%>
<script>
function searchData(){
	var queryParams = $('#dg').datagrid('options').queryParams;
	queryParams.roleId = $("#roleId").val();
	queryParams.roleName = $("#roleName").val();
	queryParams.serverArea = $("#serverArea").val();
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
				<table class="easyui-datagrid" style="height:800px" id="dg"
							data-options="singleSelect:true,striped:true,url:'/datasearch/datalist/onlinerolejson',fitColumns:true,emptyMsg:'暂无数据',pagination:true,toolbar:'#tb'">
					<thead>
						<tr>
							<th data-options="field:'roleId',width:80">角色ID</th>
							<th data-options="field:'roleName',width:120">角色名</th>
							<th data-options="field:'commanderLevel',width:80">指挥官等级</th>
							<th data-options="field:'level',width:80">行政大楼等级</th>
							<th data-options="field:'vipLevel',width:80">VIP</th>
							<th data-options="field:'lastLoginDate',width:80">最后登录时间</th>
							<th data-options="field:'operate1',width:80">操作</th>
							<th data-options="field:'operate2',width:80">操作</th>
							<th data-options="field:'operate3',width:80">操作</th>
							<th data-options="field:'operate4',width:80">操作</th>
						</tr>
					</thead>
				</table>
				<div id="tb" style="padding:2px 5px;">
					<select class="easyui-combobox" name="serverArea" id="serverArea" label="选择服务器" labelWidth="70" style="width:140px;">
						<option>1</option>
						<option>2</option>
						<option>3</option>
						<option>4</option>
						<option>5</option>
					</select>
					角色ID:<input class="easyui-textbox" data-options="prompt:'角色ID'" style="width:110px" name="roleId" id="roleId"/>
					角色名称:<input class="easyui-textbox" data-options="prompt:'角色名称'" style="width:110px" name="roleName" id="roleName"/>
					<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="searchData();">查询</a>
				</div>
			</div>
		</div>
		<%@include file="../foot.jsp" %>
	</div>
</body>
</html>