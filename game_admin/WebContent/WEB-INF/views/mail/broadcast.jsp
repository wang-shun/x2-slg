<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>广播</title>
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
				<form action="/operation/mail/savebroadcast" method="POST" id="pageForm">
					<div class="easyui-panel" style="width:100%;min-height:200px;padding: 5px 5px;">
						<span style="display:block">
							<select class="easyui-combobox" name="state" label="选择服务器" labelWidth="70" style="width:180;">
								<option>1</option>
								<option>2</option>
								<option>3</option>
								<option>4</option>
								<option>5</option>
							</select>
						</span>
						<div class="easyui-panel" title="广播内容" style="padding: 5px 5px;">
							<div style="float: left;"><input class="easyui-textbox" name="zh_CN" style="width:300;height:80px;" labelPosition="top" data-options="label:'简体中文',multiline:true"></div>
							<div style="float: left;"><input class="easyui-textbox" name="zh_TW" style="width:300;height:80px;" labelPosition="top" data-options="label:'繁体中文',multiline:true"></div>
							<div style="float: left;"><input class="easyui-textbox" name="en" style="width:300;height:80px;" labelPosition="top" data-options="label:'English',multiline:true"></div>
							<div style="float: left;"><input class="easyui-textbox" name="ja" style="width:300;height:80px;" labelPosition="top" data-options="label:'日本语',multiline:true"></div>
							<div style="float: left;"><input class="easyui-textbox" name="de" style="width:300;height:80px;" labelPosition="top" data-options="label:'德语',multiline:true"></div>
							<div style="float: left;"><input class="easyui-textbox" name="ru" style="width:300;height:80px;" labelPosition="top" data-options="label:'俄语',multiline:true"></div>
							<div style="float: left;"><input class="easyui-textbox" name="vi" style="width:300;height:80px;" labelPosition="top" data-options="label:'越南语',multiline:true"></div>
							<div style="float: left;"><input class="easyui-textbox" name="th" style="width:300;height:80px;" labelPosition="top" data-options="label:'泰语',multiline:true"></div>
						</div>
					</div>
					<div class="easyui-panel" style="width:100%;min-height:40px;text-align: center;padding: 5px 5px;">
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-save">发送</a>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-no">取消</a>
					</div>
				</form>
			</div>
		</div>
		<%@include file="../foot.jsp" %>
	</div>
</body>
</html>