<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>充值查询</title>
<%@include file="../head.jsp"%>
<script>
function showReceives(){
	var roleIds = $('#roleIds').val()
	if(roleIds==""){
		$.messager.alert("通知","请填写收件人");	
	}else{
		$.ajax({
			type:"POST",
			url:"/operation/mail/showreceives",
			data:{"roleIds":roleIds},
			success:function(result){
				if(result.success){
					$('#dd').dialog({
						content:result.message
					})
					$('#dd').dialog('open')
				}
			}
		});
	}
}
</script>
</head>
<body>
	<div class="gm-wrap">
		<%@include file="../top.jsp" %>
		<div class="gm-content">
			<%@include file="../left.jsp" %>
			<div class="gm-main">
				<form action="/operation/mail/sendmail" method="POST" id="pageForm">
					<div class="easyui-panel" style="width:100%;min-height:200px;padding: 5px 5px;">
						<span style="display: block;font-size: 24px;font-weight:bold;">收件人（二选一）</span>
						<span style="display:block">
							<select class="easyui-combobox" name="state" label="选择服务器" labelWidth="70" style="width:180;">
								<option>1</option>
								<option>2</option>
								<option>3</option>
								<option>4</option>
								<option>5</option>
							</select>
						</span>
						<span>
							<input class="easyui-textbox" name="roleIds" id="roleIds" data-options="label:'角色ID',labelWidth:70,prompt:'格式： 角色ID;角色ID...例如：99999999;8000000;500000;600000'" style="width:400px">
							<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="showReceives();">查看收件人</a>
						</span>
						<div class="easyui-panel" title="邮件主题" style="padding: 5px 5px;">
							<div style="float: left;"><input class="easyui-textbox" name="zh_CN" style="width:300;" labelPosition="top" data-options="label:'简体中文'"></div>
							<div style="float: left;"><input class="easyui-textbox" name="zh_TW" style="width:300;" labelPosition="top" data-options="label:'繁体中文'"></div>
							<div style="float: left;"><input class="easyui-textbox" name="en" style="width:300;" labelPosition="top" data-options="label:'English'"></div>
							<div style="float: left;"><input class="easyui-textbox" name="ja" style="width:300;" labelPosition="top" data-options="label:'日本语'"></div>
							<div style="float: left;"><input class="easyui-textbox" name="de" style="width:300;" labelPosition="top" data-options="label:'德语'"></div>
							<div style="float: left;"><input class="easyui-textbox" name="ru" style="width:300;" labelPosition="top" data-options="label:'俄语'"></div>
							<div style="float: left;"><input class="easyui-textbox" name="vi" style="width:300;" labelPosition="top" data-options="label:'越南语'"></div>
							<div style="float: left;"><input class="easyui-textbox" name="th" style="width:300;" labelPosition="top" data-options="label:'泰语'"></div>
						</div>
						<div class="easyui-panel" title="邮件内容" style="padding: 5px 5px;">
							<div style="float: left;"><input class="easyui-textbox" name="zh_CN" style="width:300;height:80px;" labelPosition="top" data-options="label:'简体中文',multiline:true"></div>
							<div style="float: left;"><input class="easyui-textbox" name="zh_TW" style="width:300;height:80px;" labelPosition="top" data-options="label:'繁体中文',multiline:true"></div>
							<div style="float: left;"><input class="easyui-textbox" name="en" style="width:300;height:80px;" labelPosition="top" data-options="label:'English',multiline:true"></div>
							<div style="float: left;"><input class="easyui-textbox" name="ja" style="width:300;height:80px;" labelPosition="top" data-options="label:'日本语',multiline:true"></div>
							<div style="float: left;"><input class="easyui-textbox" name="de" style="width:300;height:80px;" labelPosition="top" data-options="label:'德语',multiline:true"></div>
							<div style="float: left;"><input class="easyui-textbox" name="ru" style="width:300;height:80px;" labelPosition="top" data-options="label:'俄语',multiline:true"></div>
							<div style="float: left;"><input class="easyui-textbox" name="vi" style="width:300;height:80px;" labelPosition="top" data-options="label:'越南语',multiline:true"></div>
							<div style="float: left;"><input class="easyui-textbox" name="th" style="width:300;height:80px;" labelPosition="top" data-options="label:'泰语',multiline:true"></div>
						</div>
						<div class="easyui-panel" title="领奖中心标题" style="padding: 5px 5px;">
							<div style="float: left;"><input class="easyui-textbox" name="zh_CN" style="width:300;" labelPosition="top" data-options="label:'简体中文'"></div>
							<div style="float: left;"><input class="easyui-textbox" name="zh_TW" style="width:300;" labelPosition="top" data-options="label:'繁体中文'"></div>
							<div style="float: left;"><input class="easyui-textbox" name="en" style="width:300;" labelPosition="top" data-options="label:'English'"></div>
							<div style="float: left;"><input class="easyui-textbox" name="ja" style="width:300;" labelPosition="top" data-options="label:'日本语'"></div>
							<div style="float: left;"><input class="easyui-textbox" name="de" style="width:300;" labelPosition="top" data-options="label:'德语'"></div>
							<div style="float: left;"><input class="easyui-textbox" name="ru" style="width:300;" labelPosition="top" data-options="label:'俄语'"></div>
							<div style="float: left;"><input class="easyui-textbox" name="vi" style="width:300;" labelPosition="top" data-options="label:'越南语'"></div>
							<div style="float: left;"><input class="easyui-textbox" name="th" style="width:300;" labelPosition="top" data-options="label:'泰语'"></div>
						</div>
						<div class="easyui-panel" style="padding: 5px 5px;">
							<div style="width:45%;float: left">
								<input class="easyui-textbox" name="packageDetail" style="width:100%;height:125px" labelPosition="top" data-options="label:'奖励',multiline:true">
							</div>
							<div style="width:45%;float: right">
								<select class="easyui-combobox" label="奖励内容" labelWidth="70" style="width:200">
									<option value="简体中文">简体中文</option>
									<option value="繁体中文">繁体中文</option>
									<option value="英文">英文</option>
								</select>
								<a href="javascript:;" class="easyui-linkbutton" onclick="translate();">翻译</a>
								<input class="easyui-textbox" name="packageTranslate" style="width:100%;height:100px" data-options="multiline:true">
							</div>
						</div>
					</div>
					<div class="easyui-panel" style="width:100%;min-height:40px;text-align: center;padding: 5px 5px;">
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-save">发送</a>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-no">取消</a>
					</div>
				</form>
				<div class="easyui-dialog" style="width:600px;height:300px;" data-options="title:'收件人列表',modal:true,closed:true" id="dd">
				</div>
			</div>
		</div>
		<%@include file="../foot.jsp" %>
	</div>
</body>
</html>