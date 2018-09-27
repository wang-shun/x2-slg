<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>兑换码礼包生成</title>
<%@include file="../head.jsp"%>
<link rel="stylesheet" href="/static/css/authority.css" type="text/css"></link>
<STYLE type="text/css">
.gm-user-ul{
	width:100%;
	height:auto;
}
.gm-user-li{
	float: left;
	list-style: none;
	margin-left: 10px;
}
</STYLE>
<script type="text/javascript">
function submitForm(){
	$('#pageForm').form('submit',{
		url:"/operation/redeemcode/save",
		onSubmit:function(){
			//
		},
		success:function(data){
			var data = eval('('+data+')');
			if(data.success){
				$.messager.alert("通知",data.message);
			}
		}
	});
}
function clearForm(){
	$('#pageForm').form('clear');
}

function translate(){
	
}
</script>
</head>
<body>
	<div class="gm-wrap">
		<%@include file="../top.jsp" %>
		<div class="gm-content">
			<%@include file="../left.jsp" %>
			<div class="gm-main">
				<div class="easyui-panel" title="兑换码礼包生成" style="width:100%;max-width:600px;padding:30px 60px;">
					<form method="POST" id="pageForm" class="easyui-form">
						<div style="margin-bottom:20px">
							<select class="easyui-combobox" name="channel" label="渠道选择" style="width:100%">
								<option value="全渠道">全渠道</option>
								<option value="UC">UC</option>
								<option value="360">360</option>
								<option value="拇指玩">拇指玩</option>
							</select>
						</div>
						<div style="margin-bottom:20px">
							<label class="textbox-label textbox-label-before">有效时间</label>
							<span>
								开始: <input class="Wdate" name="startDate" value="${redeemcodePackage.startDate}" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
								结束: <input class="Wdate" name="endDate" value="${redeemcodePackage.endDate}" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
							</span>
						</div>
						<div style="margin-bottom:20px">
							<input class="easyui-textbox" name="packageName" value="${redeemcodePackage.packageName}" style="width:100%" data-options="label:'礼包备注',required:true"/>
						</div>
						<div style="margin-bottom:20px">
							<input class="easyui-textbox" name="num" value="${redeemcodePackage.num}" style="width:100%" data-options="label:'礼包数量',required:true"/>
						</div>
						<div style="margin-bottom:20px">
							<input class="easyui-textbox" name="packageDetail" style="width:100%;height:120px" labelPosition="top" data-options="label:'礼包配置',multiline:true">
						</div>
						<div style="margin-bottom:20px">
							<select class="easyui-combobox" label="礼包内容" style="width:40%">
								<option value="简体中文">简体中文</option>
								<option value="繁体中文">繁体中文</option>
								<option value="英文">英文</option>
							</select>
							<a href="javascript:;" class="easyui-linkbutton" onclick="translate();">翻译</a>
						</div>
						<div style="margin-bottom:20px">
							<input class="easyui-textbox" name="packageTranslate" style="width:100%;height:100px" data-options="multiline:true">
						</div>
					</form>
					<div style="text-align:center;padding:5px 0">
						<a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="submitForm();">保存</a>
						<a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="clearForm();">清除</a>
					</div>
				</div>
			</div>
		</div>
		<%@include file="../foot.jsp" %>
	</div>
</body>
</html>