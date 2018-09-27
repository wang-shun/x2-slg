<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>菜单编辑</title>
<%@include file="../head.jsp"%>
<link rel="stylesheet" href="/static/css/authority.css" type="text/css"></link>
<script type="text/javascript">
var parentId = '${parentId}'
var id = '${authority.id}'
$(function(){
	$("#left_menu").tree('collapseAll');
	$("#left_menu").tree('expand',$("#left_menu").tree('find','ROOT').target);
	$("#left_menu").tree('expandAll',$("#left_menu").tree('find',parentId).target);
	if(null == id){
		$("#left_menu").tree('select',$("#left_menu").tree('find',parentId).target);
	}else{
		$("#left_menu").tree('select',$("#left_menu").tree('find',id).target);
	}
})
function submitForm(){
	$('#pageForm').form('submit',{
		url:"/backadmin/authority/save",
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
</script>
</head>
<body>
	<div class="gm-wrap">
		<%@include file="../top.jsp" %>
		<div class="gm-content">
			<%@include file="../left.jsp" %>
			<div class="gm-main">
				<div class="gm-authority-left">
					<ul id="left_menu" class="easyui-tree">
						<li id="ROOT">
							<span><a href="/backadmin/authority/list">ROOT</a></span>
							<ul>
								<c:forEach items="${menus}" var="authority">
									<li id="${authority.id}">
										<span><a href="/backadmin/authority/list?id=${authority.id}">${authority.authorityName }</a></span>
										<c:if test="${not empty authority.child }">
											<ul>
												<c:forEach items="${authority.child }" var="childAuthority">
													<li id="${childAuthority.id}"><span><a href="/backadmin/authority/edit?id=${childAuthority.id}&parentId=${authority.id}">${childAuthority.authorityName }</a></span></li>
												</c:forEach>
											</ul>
										</c:if>
									</li>
								</c:forEach>
							</ul>
						</li>
					</ul>
				</div>
				<div class="gm-authority-right">
					<div class="easyui-panel" style="width:100%;max-width:600px;padding:30px 60px;">
						<form method="POST" id="pageForm" class="easyui-form">
							<input type="hidden" name="id" id="id" value="${authority.id}"/>
							<input type="hidden" name="parent.id" id="parent.id" value="${parentId}"/>
							<div style="margin-bottom:20px">
								<input class="easyui-textbox" name="authorityName" value="${authority.authorityName}" style="width:100%" data-options="label:'菜单名',required:true"/>
							</div>
							<div style="margin-bottom:20px">
								<input class="easyui-textbox" name="authorityCode" value="${authority.authorityCode}" style="width:100%" data-options="label:'权限编码',required:true"/>
							</div>
							<div style="margin-bottom:20px">
								<input class="easyui-textbox" name="url" value="${authority.url}" style="width:100%" data-options="label:'URL',required:true"/>
							</div>
							<div style="margin-bottom:20px">
								<input class="easyui-textbox" name="itemNo" value="${authority.itemNo}" style="width:100%" data-options="label:'排序',required:true"/>
							</div>
						</form>
						<div style="text-align:center;padding:5px 0">
							<a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="submitForm();">保存</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@include file="../foot.jsp" %>
	</div>
</body>
</html>