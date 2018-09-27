<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>联系客服</title>
<%@include file="../head.jsp"%>
<link rel="stylesheet" href="/static/css/authority.css" type="text/css"></link>
<script>
function operate1(val,row,index){
	if(row.unusedNum > 0){
		return '<span>'+row.unusedNum+'</span><a href="javascript:;" class="operate1" onclick="updateStatus(\''+row.id+'\');">全部作废</a>';
	}else{
		return '<span>'+row.unusedNum+'</span>';
	}
}

function operate2(val,row,index){
	if(row.unusedNum > 0){
		return '<span>'+row.unusedNum+'</span><a href="javascript:;" class="operate1" onclick="updateStatus(\''+row.id+'\');">全部作废</a>';
	}else{
		return '<span>'+row.unusedNum+'</span>';
	}
}

function operate3(val,row,index){
	if(row.unusedNum > 0){
		return '<span>'+row.unusedNum+'</span><a href="javascript:;" class="operate1" onclick="updateStatus(\''+row.id+'\');">全部作废</a>';
	}else{
		return '<span>'+row.unusedNum+'</span>';
	}
}

function operate4(val,row,index){
	if(row.unusedNum > 0){
		return '<span>'+row.unusedNum+'</span><a href="javascript:;" class="operate1" onclick="updateStatus(\''+row.id+'\');">全部作废</a>';
	}else{
		return '<span>'+row.unusedNum+'</span>';
	}
}

function operate5(val,row,index){
	if(row.unusedNum > 0){
		return '<span>'+row.unusedNum+'</span><a href="javascript:;" class="operate1" onclick="updateStatus(\''+row.id+'\');">全部作废</a>';
	}else{
		return '<span>'+row.unusedNum+'</span>';
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
				<div class="easyui-tabs" style="min-height:600px">
					<div title="未回复">
						<table class="easyui-datagrid" style="height:600px" id="dg1"
									data-options="singleSelect:true,striped:true,url:'/cstools/tools/unreplyjson',fitColumns:true,pagination:true,emptyMsg:'暂无数据',toolbar:'#tb1'">
							<thead>
								<tr>
									<th data-options="field:'content',width:140">内容</th>
									<th data-options="field:'playerName',width:120">角色名</th>
									<th data-options="field:'server',width:80">服务器</th>
									<th data-options="field:'playerId',width:80">角色ID</th>
									<th data-options="field:'vip',width:80">VIP</th>
									<th data-options="field:'operate1',width:80,formatter:operate1">处理</th>
									<th data-options="field:'operate2',width:80,formatter:operate2">处理</th>
									<th data-options="field:'language',width:80">语言版本</th>
									<th data-options="field:'updateTime',width:80">提交时间</th>
								</tr>
							</thead>
						</table>
						<div id="tb1" style="padding:2px 5px;">
							角色ID:<input class="easyui-textbox" data-options="prompt:'角色ID'" style="width:110px" name="playerId" value="${playerId}"/>
							角色名称:<input class="easyui-textbox" data-options="prompt:'角色名称'" style="width:110px" name="playerName" value="${playerName }"/>
							时间 从: <input class="Wdate" name="startDt" value="${startDt}" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
							到: <input class="Wdate" name="endDt" value="${endDt}" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
							vip:<input class="easyui-textbox" data-options="prompt:'vip'" style="width:110px" name="vip" value="${vip }"/>
							语言版本:<input class="easyui-textbox" data-options="prompt:'语言版本'" style="width:110px" name="language" value="${language }"/>
							<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="$('#pageForm').submit();">查询</a>
						</div>
					</div>
					<div title="骚扰">
						<table class="easyui-datagrid" style="height:600px" id="dg2"
									data-options="singleSelect:true,striped:true,url:'/cstools/tools/harassjson',fitColumns:true,pagination:true,emptyMsg:'暂无数据',toolbar:'#tb2'">
							<thead>
								<tr>
									<th data-options="field:'content',width:140">内容</th>
									<th data-options="field:'playerName',width:120">角色名</th>
									<th data-options="field:'server',width:80">服务器</th>
									<th data-options="field:'playerId',width:80">角色ID</th>
									<th data-options="field:'vip',width:80">VIP</th>
									<th data-options="field:'operate3',width:80,formatter:operate3">处理</th>
									<th data-options="field:'operate4',width:80,formatter:operate4">处理</th>
									<th data-options="field:'language',width:80">语言版本</th>
									<th data-options="field:'updateTime',width:80">提交时间</th>
								</tr>
							</thead>
						</table>
						<div id="tb2" style="padding:2px 5px;">
							角色ID:<input class="easyui-textbox" data-options="prompt:'角色ID'" style="width:110px" name="playerId" value="${playerId}"/>
							角色名称:<input class="easyui-textbox" data-options="prompt:'角色名称'" style="width:110px" name="playerName" value="${playerName }"/>
							时间 从: <input class="Wdate" name="startDt" value="${startDt}" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
							到: <input class="Wdate" name="endDt" value="${endDt}" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
							vip:<input class="easyui-textbox" data-options="prompt:'vip'" style="width:110px" name="vip" value="${vip }"/>
							语言版本:<input class="easyui-textbox" data-options="prompt:'语言版本'" style="width:110px" name="language" value="${language }"/>
							<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="$('#pageForm').submit();">查询</a>
						</div>
					</div>
					<div title="已回复">
						<table class="easyui-datagrid" style="height:600px" id="dg3"
									data-options="singleSelect:true,striped:true,url:'/cstools/tools/replyjson',fitColumns:true,pagination:true,emptyMsg:'暂无数据',toolbar:'#tb3'">
							<thead>
								<tr>
									<th data-options="field:'content',width:140">内容</th>
									<th data-options="field:'player',width:120">角色</th>
									<th data-options="field:'vip',width:80">VIP</th>
									<th data-options="field:'relayFlag',width:80">回复账号</th>
									<th data-options="field:'harassFlag',width:80">回复时间</th>
									<th data-options="field:'language',width:80">语言版本</th>
									<th data-options="field:'updateTime',width:80">提交时间</th>
									<th data-options="field:'operate5',width:80,formatter:operate5">处理</th>
								</tr>
							</thead>
						</table>
						<div id="tb3" style="padding:2px 5px;">
							角色ID:<input class="easyui-textbox" data-options="prompt:'角色ID'" style="width:110px" name="playerId" value="${playerId}"/>
							角色名称:<input class="easyui-textbox" data-options="prompt:'角色名称'" style="width:110px" name="playerName" value="${playerName }"/>
							时间 从: <input class="Wdate" name="startDt" value="${startDt}" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
							到: <input class="Wdate" name="endDt" value="${endDt}" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
							vip:<input class="easyui-textbox" data-options="prompt:'vip'" style="width:110px" name="vip" value="${vip }"/>
							语言版本:<input class="easyui-textbox" data-options="prompt:'语言版本'" style="width:110px" name="language" value="${language }"/>
							<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="$('#pageForm').submit();">查询</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@include file="../foot.jsp" %>
	</div>
</body>
</html>