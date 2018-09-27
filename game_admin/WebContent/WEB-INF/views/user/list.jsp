<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>用户列表</title>
<%@include file="../head.jsp"%>
<script>
$(function(){
	$("#dg").datagrid({
		onLoadSuccess:function(data){
			$(".operate1").linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});
			$(".operate2no").linkbutton({text:'禁止登陆',plain:true,iconCls:'icon-no'});
			$(".operate2ok").linkbutton({text:'允许登陆',plain:true,iconCls:'icon-ok'});
			$(".operate3").linkbutton({text:'重置密码',plain:true,iconCls:'icon-reload'});
			$(".operate4no").linkbutton({text:'禁止外网登陆',plain:true,iconCls:'icon-no'});
			$(".operate4ok").linkbutton({text:'允许外网登陆',plain:true,iconCls:'icon-ok'});
		}
	});
})

function searchData(){
	var queryParams = $('#dg').datagrid('options').queryParams;
	queryParams.startDt = $("#startDt").val();
	queryParams.endDt = $("#endDt").val();
	queryParams.username = $("#username").val();
	$('#dg').datagrid('reload');
}

function resetPWD(id){
	$.ajax({
		type:"POST",
		url:"/backadmin/user/resetpassword",
		data:{"id":id},
		success:function(result){
			if(result.success){
				$.messager.alert("通知",result.message);
				$('#dg').datagrid('reload');
			}
		}
	});
}

function updateStatus(id,enabledFlag,networkFlag){
	$.ajax({
		type:"POST",
		url:"/backadmin/user/updatestatus",
		data:{"id":id,"enabledFlag":enabledFlag,"networkFlag":networkFlag},
		success:function(result){
			if(result.success){
				$.messager.alert("通知",result.message);	
				$('#dg').datagrid('reload');
			}
		}
	});
}

function operate1(val,row,index){
	return '<a class="operate1" href="/backadmin/user/edit?id='+row.id+'">编辑</a>';
}

function operate2(val,row,index){
	if(row.enabledFlag == 'Y'){
		return '<a class="operate2no" href="javascrip:;" onclick="updateStatus(\''+row.id+'\',\'N\',\''+row.networkFlag+'\');">禁止登陆</a>';
	}else{
		return '<a class="operate2ok" href="javascrip:;" onclick="updateStatus(\''+row.id+'\',\'Y\',\''+row.networkFlag+'\');">允许登陆</a>';
	}
}

function operate3(val,row,index){
	return '<a class="operate3" href="javascript:;" onclick="resetPWD(\''+row.id+'\');">重置密码</a>';
}

function operate4(val,row,index){
	if(row.networkFlag == 'Y'){
		return '<a class="operate4no" href="javascrip:;" onclick="updateStatus(\''+row.id+'\',\''+row.enabledFlag+'\',\'N\');">禁止外网登陆</a>';
	}else{
		return '<a class="operate4ok" href="javascrip:;" onclick="updateStatus(\''+row.id+'\',\''+row.enabledFlag+'\',\'Y\');">允许外网登陆</a>';
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
				<table class="easyui-datagrid" style="height:800px" id="dg"
							data-options="singleSelect:true,striped:true,url:'/backadmin/user/json',fitColumns:true,pagination:true,toolbar:'#tb'">
					<thead>
						<tr>
							<th data-options="field:'userid',width:80">ID</th>
							<th data-options="field:'username',width:80">管理员帐户</th>
							<th data-options="field:'remark',width:80">备注</th>
							<th data-options="field:'realname',width:80">使用者</th>
							<th data-options="field:'gmname',width:80">GM名</th>
							<th data-options="field:'lastLoginDate',width:140">最后登陆日期</th>
							<th data-options="field:'operate1',width:80,formatter:operate1">操作</th>
							<th data-options="field:'operate2',width:80,formatter:operate2">操作</th>
							<th data-options="field:'operate3',width:80,formatter:operate3">操作</th>
							<th data-options="field:'operate4',width:80,formatter:operate4">操作</th>
						</tr>
					</thead>
				</table>
				<div id="tb" style="padding:2px 5px;">
					时间 从: <input class="Wdate" name="startDt" id="startDt" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
					到: <input class="Wdate" name="endDt" id="endDt" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
					用户名:<input class="easyui-textbox" data-options="prompt:'用户名:'" style="width:110px" name="username" id="username"/>
					<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="searchData();">查询</a>
					<a href="/backadmin/user/edit?id=" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a>
				</div>
			</div>
		</div>
		<%@include file="../foot.jsp" %>
	</div>
</body>
</html>