<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>封号禁言</title>
<%@include file="../head.jsp"%>
<link rel="stylesheet" href="/static/css/authority.css" type="text/css"></link>
<script>
$(function(){
	$("#dg").datagrid({
		onLoadSuccess:function(data){
			$(".operate1no").linkbutton({text:'禁言',plain:true,iconCls:'icon-no'});
			$(".operate1ok").linkbutton({text:'解禁',plain:true,iconCls:'icon-ok'});
			$(".operate2no").linkbutton({text:'封号',plain:true,iconCls:'icon-no'});
			$(".operate2ok").linkbutton({text:'解禁',plain:true,iconCls:'icon-ok'});
		}
	});
})

function searchData(){
	var queryParams = $('#dg').datagrid('options').queryParams;
	queryParams.roleId = $("#roleId").val();
	queryParams.roleName = $("#roleName").val();
	queryParams.vipLevel = $("#vipLevel").combobox('getValues').join(',');
	$('#dg').datagrid('reload');
}

function forbidChat(id){
	$.ajax({
		type:"POST",
		url:"/cstools/tools/forbidChat",
		data:{"id":id},
		success:function(result){
			if(result.success){
				$.messager.alert("通知",result.message);	
			}
		}
	});
}

function kickOut(id){
	$.ajax({
		type:"POST",
		url:"/cstools/tools/kickout",
		data:{"id":id},
		success:function(result){
			if(result.success){
				$.messager.alert("通知",result.message);	
			}
		}
	});
}

function operate1(val,row,index){
	if(row.exp > 0){
		return '<span>'+row.exp+'</span><a href="javascript:;" class="operate1no" onclick="updateStatus(\''+row.id+'\');">禁言</a>';
	}else{
		return '<span>'+row.exp+'</span><a href="javascript:;" class="operate1ok" onclick="updateStatus(\''+row.id+'\');">解禁</a>';
	}
}

function operate2(val,row,index){
	if(row.kickOut > 1001){
		return '<span>'+row.kickOut+'</span><a href="javascript:;" class="operate2no" onclick="updateStatus(\''+row.id+'\');">封号</a>';
	}else{
		return '<span>'+row.kickOut+'</span><a href="javascript:;" class="operate2ok" onclick="updateStatus(\''+row.id+'\');">解禁</a>';
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
				<table class="easyui-datagrid" style="min-height: 500px;" id="dg"
							data-options="singleSelect:true,striped:true,url:'/cstools/tools/accountjson',fitColumns:true,pagination:true,emptyMsg:'暂无数据',toolbar:'#tb'">
					<thead>
						<tr>
							<th data-options="field:'roleId',width:80">角色ID</th>
							<th data-options="field:'roleName',width:120">角色名</th>
							<th data-options="field:'serverArea',width:80">服务器</th>
							<th data-options="field:'vipLevel',width:80">VIP</th>
							<th data-options="field:'chatFlag',width:80,formatter:operate1">禁止发言</th>
							<th data-options="field:'loginFlag',width:80,formatter:operate2">禁止登陆</th>
							<th data-options="field:'status',width:80">状态</th>
							<th data-options="field:'operator',width:80">操作者帐号</th>
							<th data-options="field:'operateTime',width:140">处理时间</th>
						</tr>
					</thead>
				</table>
				<div id="tb" style="padding:2px 5px;">
					角色ID:<input class="easyui-textbox" data-options="prompt:'角色ID'" style="width:110px" name="roleId" id="roleId"/>
					角色名称:<input class="easyui-textbox" data-options="prompt:'角色名称'" style="width:110px" name="roleName" id="roleName"/>
					VIP:<select class="easyui-combobox" name="server" name="vipLevel" id="vipLevel" data-options="multiple:true" style="width:140px">
							<option value="0">vip0</option>
							<option value="1">vip1</option>
							<option value="2">vip2</option>
							<option value="3">vip3</option>
							<option value="4">vip4</option>
							<option value="5">vip5</option>
							<option value="6">vip6</option>
							<option value="7">vip7</option>
							<option value="8">vip8</option>
							<option value="9">vip9</option>
							<option value="10">vip10</option>
							<option value="11">vip11</option>
							<option value="12">vip12</option>
							<option value="13">vip13</option>
							<option value="14">vip14</option>
							<option value="15">vip15</option>
						</select>
					<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="searchData();">查询</a>
				</div>
			</div>
		</div>
		<%@include file="../foot.jsp" %>
	</div>
</body>
</html>