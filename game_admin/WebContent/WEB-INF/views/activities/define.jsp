<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>事件系统设定</title>
<%@include file="../head.jsp"%>
<script>
</script>
</head>
<body>
	<div class="gm-wrap">
		<%@include file="../top.jsp" %>
		<div class="gm-content">
			<%@include file="../left.jsp" %>
			<div class="gm-main">
				<form action="/activities/activity/define" method="POST" id="pageForm">
					<div class="easyui-panel">
						<span style="display: block;font-size: 24px;font-weight: bold;text-align: center;">
							事件系统设定
						</span>
					</div>
					<div class="easyui-panel">
						<div style="width:28%;float: left;">
							<input class="easyui-textbox" name="serverEvent" style="width:100%;min-height:400px" labelPosition="top" data-options="label:'serverEvent.txt',multiline:true">
						</div>
						<div style="width:70%;float: right;">
							<input class="easyui-textbox" name="serverEventTeam" style="width:100%;min-height:400px" labelPosition="top" data-options="label:'serverEventTeam.txt',multiline:true">
						</div>
						<span style="display: block;text-align: center;">
							<a href="javascript:;" class="easyui-linkbutton">确认修改（下次事件开始生效）</a>
						</span>
					</div>
				</form>
			</div>
		</div>
		<%@include file="../foot.jsp" %>
	</div>
</body>
</html>