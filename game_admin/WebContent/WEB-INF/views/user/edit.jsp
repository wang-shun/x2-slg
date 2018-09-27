<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>用户编辑</title>
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
		url:"/backadmin/user/save",
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
$(function(){
	/* var authorities = '${authorities}';
	console.log(authorities);
	$(authorities).each(console.log(authority.id)); */
}); 
</script>
</head>
<body>
	<div class="gm-wrap">
		<%@include file="../top.jsp" %>
		<div class="gm-content">
			<%@include file="../left.jsp" %>
			<div class="gm-main">
				<div class="easyui-panel" title="用户信息" style="width:100%;max-width:600px;padding:30px 60px;">
					<form method="POST" id="pageForm" class="easyui-form">
						<input type="hidden" name="id" value="${user.id}"/>
						<input type="hidden" name="userid" value="${user.userid}"/>
						<input type="hidden" name="password" value="${user.password}"/>
						<input type="hidden" name="enabledFlag" value="${user.enabledFlag}"/>
						<input type="hidden" name="networkFlag" value="${user.networkFlag}"/>
						<input type="hidden" name="lastLoginDate" value="${user.lastLoginDate}"/>
						<input type="hidden" name="createDate" value="${user.createDate}"/>
						<div style="margin-bottom:20px">
							<input class="easyui-textbox" name="username" value="${user.username}" style="width:100%" data-options="label:'用户名',required:true"/>
						</div>
						<div style="margin-bottom:20px">
							<input class="easyui-textbox" name="gmname" value="${user.gmname}" style="width:100%" data-options="label:'GM名',required:true"/>
						</div>
						<div style="margin-bottom:20px">
							<input class="easyui-textbox" name="realname" value="${user.realname}" style="width:100%" data-options="label:'使用者',required:true"/>
						</div>
						<div style="margin-bottom:20px">
							<input class="easyui-textbox" name="remark" value="${user.remark}" style="width:100%" data-options="label:'备注',required:true"/>
						</div>
						<div class="easyui-panel" title="权限信息" style="width:100%;max-width:600px;padding:10px 10px;">
							<ul class="gm-user-ul">
								<c:forEach items="${menus }" var="authority">
									<li class="gm-user-li"><input type="checkbox" value="${authority.id }" name="ids" id="${authority.id }" <c:forEach items="${user.authorities }" var="uauthority"><c:if test="${authority.id eq uauthority.id}">checked</c:if></c:forEach>/>${authority.authorityName }</li>
								</c:forEach>
							</ul>
						</div>
					</form>
					<div style="text-align:center;padding:5px 0">
						<a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="submitForm();">保存</a>
						<a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="clearForm();">清除</a>
						<a href="javascript:history.go(-1);" class="easyui-linkbutton" data-options="iconCls:'icon-back'" >返回</a>
					</div>
				</div>
			</div>
		</div>
		<%@include file="../foot.jsp" %>
	</div>
</body>
</html>