<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>内部账户列表</title>
<%@include file="../head.jsp"%>
<link rel="stylesheet" href="/static/css/authority.css" type="text/css"></link>
<script type="text/javascript">

</script>
</head>
<body>
	<div class="gm-wrap">
		<%@include file="../top.jsp" %>
		<div class="gm-content">
			<%@include file="../left.jsp" %>
			<div class="gm-main">
				<form action="/operation/inneraccount/list" method="POST" id="pageForm">
					<div class="easyui-panel" style="width:100%;max-width:600px;padding:30px 60px;">
						<div style="margin-bottom:20px;text-align: center;">
							<span style="font-size: 24px;font-weight: bold;">内部账户列表</span>
						</div>
						<div style="margin-bottom:20px">
							<input class="easyui-textbox" name="innerPlayerList" style="width:100%;height:200px" labelPosition="top" data-options="label:'角色ID（不参与任何数据分析和统计）',multiline:true">
						</div>
						<div style="margin-bottom:20px">
							<input class="easyui-textbox" name="innerChargeBag" style="width:100%;height:200px" labelPosition="top" data-options="label:'自充渠道包（不参与任何数据分析和统计）',multiline:true">
						</div>
						<div style="margin-bottom:20px;text-align: center;">
							<a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="submitForm();">保存</a>
							<a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="clearForm();">立即生效</a>
						</div>
					</div>
				</form>
			</div>
		</div>
		<%@include file="../foot.jsp" %>
	</div>
</body>
</html>