<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>菜单列表</title>
<%@include file="../head.jsp"%>
<link rel="stylesheet" href="/static/css/authority.css" type="text/css"></link>
<script type="text/javascript">
var id = '${parentId}';
$(function(){
	$("#left_menu").tree('collapseAll');
	$("#left_menu").tree('expand',$("#left_menu").tree('find','ROOT').target);
	$("#left_menu").tree('expandAll',$("#left_menu").tree('find',id).target);
})
</script>
</head>
<body>
	<div class="gm-wrap">
		<%@include file="../top.jsp" %>
		<div class="gm-content">
			<%@include file="../left.jsp" %>
			<div class="gm-main">
				<form action="/backadmin/authority/save" method="POST" id="pageForm">
					<div class="gm-authority-left">
						<ul id="left_menu" class="easyui-tree" lines="true">
							<li id="ROOT">
								<span><a href="/backadmin/authority/list">ROOT</a></span>
								<ul>
									<c:forEach items="${menus}" var="authority">
										<li id="${authority.id}">
											<span><a href="/backadmin/authority/list?id=${authority.id}">${authority.authorityName }</a></span>
											<c:if test="${not empty authority.child }">
												<ul>
													<c:forEach items="${authority.child }" var="childAuthority">
														<li id="${childAuthority.id}"><span><a href="/backadmin/authority/edit?id=${childAuthority.id}&parentId=${parentId}">${childAuthority.authorityName }</a></span></li>
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
						<table class="easyui-datagrid" style="min-height:600px"
							data-options="rownumbers:true,singleSelect:true,fitColumns:true,toolbar:'#tb'">
							<thead>
								<tr>
									<th data-options="field:'authorityName',width:100">菜单名</th>
									<th data-options="field:'authorityCode',width:100">权限编码</th>
									<th data-options="field:'url',width:150">URL</th>
									<th data-options="field:'itemNo',width:80">排序</th>
									<th data-options="field:'operate',width:120">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${list }" var="authority">
									<tr>
										<td>${authority.authorityName}</td>
										<td>${authority.authorityCode}</td>
										<td>${authority.url}</td>
										<td>${authority.itemNo}</td>
										<td><a href="javascript:$.messager.confirm('确认','是否确认删除？',function(r){if(r){window.location.href='/backadmin/authority/delete?id=${authority.id }&parentId=${parentId}';}});" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除</a><a href="/backadmin/authority/edit?id=${authority.id }&parentId=${parentId}" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">编辑</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div id="tb" style="padding:2px 5px;">
							<a href="/backadmin/authority/edit?id=&parentId=${parentId}" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加菜单</a>
						</div>
					</div>
				</form>
			</div>
		</div>
		<%@include file="../foot.jsp" %>
	</div>
</body>
</html>