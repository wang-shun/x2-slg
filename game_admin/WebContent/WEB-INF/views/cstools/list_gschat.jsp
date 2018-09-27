<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>聊天-gs</title>
<%@include file="../head.jsp"%>
<link rel="stylesheet" href="/static/css/authority.css" type="text/css"></link>
<style type="text/css">
.panel-float{
	float: left;
}
</style>
<script>
</script>

</head>
<body>
	<div class="gm-wrap">
		<%@include file="../top.jsp" %>
		<div class="gm-content">
			<%@include file="../left.jsp" %>
			<div class="gm-main">
				<form action="/cstools/tools/account" method="POST" id="pageForm">
					<div class="easyui-panel">
						角色ID:<input class="easyui-textbox" data-options="prompt:'角色ID'" style="width:110px" name="playerId" value="${playerId}"/>
						角色名称:<input class="easyui-textbox" data-options="prompt:'角色名称'" style="width:110px" name="playerName" value="${playerName }"/>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="$('#pageForm').submit();">查询</a>
					</div>
					<div class="easyui-panel">
						<c:forEach items="${pagination.list }" var="chat">
							<div style="width:200px;height:300px;border:solid;">
								<span>1</span>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search">打开</a>
								<input class="easyui-textbox" name="remark" data-options="multiline:true">
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search">保存</a>
							</div>
						</c:forEach>
						<div style="width:200px;height:300px;text-align: center;border:solid 1px;border-color: #D4D4D4;float: left;padding: 5px 5px;">
							<span style="display: block;">1</span>
							<span style="display: block;"><a href="javascript:;" class="easyui-linkbutton">打开</a></span>
							<input class="easyui-textbox" name="remark" data-options="multiline:true" style="height:200px">
							<span style="display: block;"><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-save">保存</a></span>
						</div>
						<div style="width:200px;height:300px;text-align: center;border:solid 1px;border-color: #D4D4D4;float: right;padding: 5px 5px;">
							<input class="easyui-textbox" name="remark" data-options="prompt:'输入角色ID'"/>
							<span style="display: block;"><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add">添加</a></span>
						</div>
					</div>
					<%@include file="../page.jsp" %>
				</form>
			</div>
		</div>
		<%@include file="../foot.jsp" %>
	</div>
</body>
</html>