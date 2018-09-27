<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>漏单查询</title>
<%@include file="../head.jsp"%>
<link rel="stylesheet" href="/static/css/authority.css" type="text/css"></link>
<script>
$(function(){
	$("#dg").datagrid({
		onLoadSuccess:function(data){
			$(".operate1").linkbutton({text:'查看充值记录',plain:true,iconCls:'icon-search'});
			$(".operate2").linkbutton({text:'已处理 删除此记录',plain:true,iconCls:'icon-no'});
		}
	});
})

function operate1(val,row,index){
	return '<a class="operate1" href="javascrip:;">查看充值记录</a>';
}

function operate2(val,row,index){
	return '<a class="operate2" href="javascrip:;">已处理 删除此记录</a>';
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
							data-options="singleSelect:true,striped:true,url:'/operation/chargesearch/mislistjson',fitColumns:true,pagination:true,emptyMsg:'暂无数据'">
					<thead>
						<tr>
							<th data-options="field:'chargeTime',width:80">时间</th>
							<th data-options="field:'chargeMoney',width:80">充值金额</th>
							<th data-options="field:'roleId',width:80">角色ID</th>
							<th data-options="field:'roleName',width:120">角色名</th>
							<th data-options="field:'serverArea',width:80">服务器</th>
							<th data-options="field:'deserveDiamond',width:140">应发钻石</th>
							<th data-options="field:'operate1',width:160,formatter:operate1">操作</th>
							<th data-options="field:'operate2',width:160,formatter:operate2">操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
		<%@include file="../foot.jsp" %>
	</div>
</body>
</html>