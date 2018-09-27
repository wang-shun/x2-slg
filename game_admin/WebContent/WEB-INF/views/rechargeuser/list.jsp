<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>充值用户列表</title>
<%@include file="../head.jsp"%>
<script>
$(function(){
	$("#dg").datagrid({
		onLoadSuccess:function(data){
			$(".operate1").linkbutton({text:'查看充值记录',plain:true,iconCls:'icon-search'});
		}
	});
})

function searchData(){
	var queryParams = $('#dg').datagrid('options').queryParams;
	queryParams.roleId = $("#roleId").val();
	queryParams.channel = $("#channel").val();
	queryParams.serverArea = $("#serverArea").val();
	$('#dg').datagrid('reload');
}

function operate1(val,row,index){
	return '<a href="javascript:;" class="operate1" onclick="updateStatus(\''+row.id+'\');">查看充值记录</a>';
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
						角色ID:<input class="easyui-textbox" data-options="prompt:'角色ID'" style="width:110px" name="roleId" id="roleId"/>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="searchData();">查询</a>
					</div>
					<table class="easyui-datagrid" style="min-height:200px" id="dg"
						data-options="singleSelect:true,striped:true,url:'/rechargeuser/player/listjson',fitColumns:true,emptyMsg:'暂无数据',pagination:true">
						<thead>
							<tr>
								<th data-options="field:'roleId',width:40">用户ID</th>
								<th data-options="field:'roleName',width:40">用户名</th>
								<th data-options="field:'serverArea',width:40">服务器</th>
								<th data-options="field:'chargeSum',width:40">累计充值额度￥</th>
								<th data-options="field:'channel',width:40">渠道</th>
								<th data-options="field:'capacity',width:40">当前战力</th>
								<th data-options="field:'lastLoginTime',width:40">最后登录时间</th>
								<th data-options="field:'lastChargeTime',width:40">最后充值时间</th>
								<th data-options="field:'operate1',width:40,formatter:operate1">操作</th>
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