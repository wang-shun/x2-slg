<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>用户登陆</title>
<link rel="stylesheet" href="/static/css/easyui/easystyle.css" type="text/css"></link>

<script src="/static/js/jquery.min-1.12.4.js" type="text/javascript"></script>
<script src="/static/js/easyui/jquery.easyui.min-1.5.1.js" type="text/javascript"></script>
<script src="/static/js/easyui/easyui-lang-zh_CN.js" type="text/javascript"></script>
<style type="text/css">
.panel{
	position:absolute;
	left:50%;
	top:50%;
	margin:-104px 0 0 -200px;
}
</style>
</head>
<body>
<!-- <table>
	<tr>
		<td align="center">
			<div>
				<form name="login" action="/login" method="post">
					<input type="text" name="username"/>
					<input type="password" name="password"/><br/>
					<input type="submit" value="登录" />
				</form>
			</div>
		</td>
	</tr>
</table> -->
<form id="login" name="login" action="/login" method="post">
	<div class="easyui-panel" style="max-width:400px;padding:30px 60px;margin:0 auto;">
		<div style="margin-bottom:10px">
			<input class="easyui-textbox" name="username" style="width:100%;height:40px;padding:12px" data-options="prompt:'用户名',iconCls:'icon-man',iconWidth:38">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" name="password" type="password" style="width:100%;height:40px;padding:12px" data-options="prompt:'密码',iconCls:'icon-lock',iconWidth:38">
		</div>
		<!-- <div style="margin-bottom:20px">
			<input type="checkbox" checked="checked">
			<span>Remember me</span>
		</div> -->
		<div>
			<a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" style="padding:5px 0px;width:100%;" onclick="$('#login').submit();">
				<span style="font-size:14px;">登录</span>
			</a>
		</div>
	</div>
</form>
</body>
</html>