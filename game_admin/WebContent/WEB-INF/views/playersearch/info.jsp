<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>玩家信息</title>
<%@include file="../head.jsp"%>
<link rel="stylesheet" href="/static/css/authority.css" type="text/css"></link>
<script>
var currency = JSON.parse('${currency}');
var itemTable = JSON.parse('${itemTable}');
var equipmentMap = JSON.parse('${equipmentMap}');
var soldiers = JSON.parse('${soldiers}');
var rewards = JSON.parse('${rewards}');
var activeItems = JSON.parse('${activeItems}');
var commander = JSON.parse('${commander}');
var techs = JSON.parse('${techs}');
$(function(){
	console.log(currency)
	console.log(itemTable)
	console.log(equipmentMap)
	console.log(soldiers)
	console.log(rewards)
	console.log(activeItems)
	console.log(commander)
	console.log(techs)
	$("#currency").datagrid({
		data:currency
	})
	$("#itemTable").datagrid({
		data:itemTable
	})
	$("#equipmentMap").datagrid({
		data:equipmentMap
	})
	$("#soldiers").datagrid({
		data:soldiers
	})
	$("#rewards").datagrid({
		data:rewards
	})
	$("#activeItems").datagrid({
		data:activeItems
	})
	$("#commander").datagrid({
		data:commander
	})
	$("#techs").datagrid({
		data:techs
	})
})
</script>

</head>
<body>
	<div class="gm-wrap">
		<%@include file="../top.jsp" %>
		<div class="gm-content">
			<%@include file="../left.jsp" %>
			<div class="gm-main">
				<form action="/playersearch/player/info" method="POST" id="pageForm">
					<div id="tb" style="padding:2px 5px;">
						角色ID:<input class="easyui-textbox" data-options="prompt:'角色ID'" style="width:140px" name="roleId" value="${roleId}"/>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="$('#pageForm').submit();">查询</a>
					</div>
					<table class="easyui-datagrid" style="height:90px" id="dg-player"
							data-options="singleSelect:true,striped:true,fitColumns:true,emptyMsg:'暂无数据',toolbar:'#tb'">
						<thead>
							<tr>
								<th data-options="field:'roleId',width:190">角色ID</th>
								<th data-options="field:'roleName',width:217">角色名</th>
								<th data-options="field:'serverArea',width:190">服务器</th>
							</tr>
						</thead>
						<tbody>
							<td>${player.roleId }</td>
							<td>${player.roleName }</td>
							<td>${player.serverArea }</td>
						</tbody>
					</table>
					<div class="easyui-panel">
						<div class="easyui-panel" title="货币" style="padding: 5px 5px;">
							<table class="easyui-datagrid" style="min-height:200px" id="currency"
							data-options="singleSelect:true,striped:true,fitColumns:true,emptyMsg:'暂无数据'">
								<thead>
									<tr>
										<th data-options="field:'f1',width:280">货币类型</th>
										<th data-options="field:'f2',width:273">数量</th>
									</tr>
								</thead>
							</table>
						</div>
						<div class="easyui-panel" title="背包" style="padding: 5px 5px;">
							<table title="道具" class="easyui-datagrid" style="min-height:200px" id="itemTable"
							data-options="singleSelect:true,striped:true,fitColumns:true,emptyMsg:'暂无数据'">
								<thead>
									<tr>
										<th data-options="field:'itemId',width:80">道具ID</th>
										<th data-options="field:'num',width:80">数量</th>
										<th data-options="field:'startTime',width:80">获得时间</th>
									</tr>
								</thead>
							</table>
							<table title="装备" class="easyui-datagrid" style="min-height:200px" id="equipmentMap"
							data-options="singleSelect:true,striped:true,fitColumns:true,emptyMsg:'暂无数据'">
								<thead>
									<tr>
										<th data-options="field:'equipmentSequenceId',width:80">唯一ID</th>
										<th data-options="field:'equipmentId',width:80">模板ID</th>
										<th data-options="field:'isEquiped',width:80">装备是否被穿戴</th>
									</tr>
								</thead>
							</table>
						</div>
						<div class="easyui-panel" title="士兵" style="padding: 5px 5px;">
							<table class="easyui-datagrid" style="min-height:200px" id="soldiers"
							data-options="singleSelect:true,striped:true,fitColumns:true,emptyMsg:'暂无数据'">
								<thead>
									<tr>
										<th data-options="field:'soldierId',width:80">士兵Id</th>
										<th data-options="field:'isSystem',width:80">是否系统兵种</th>
										<th data-options="field:'type',width:80">类型</th>
										<th data-options="field:'num',width:80">数量</th>
										<th data-options="field:'name',width:80">兵种名称</th>
										<th data-options="field:'level',width:80">士兵等级</th>
										<th data-options="field:'vector',width:80">坐标</th>
									</tr>
								</thead>
							</table>
						</div>
						<div class="easyui-panel" title="活跃" style="padding: 5px 5px;">
							<table title="已激活宝箱" class="easyui-datagrid" style="min-height:200px" id="rewards"
							data-options="singleSelect:true,striped:true,fitColumns:true,emptyMsg:'暂无数据'">
								<thead>
									<tr>
										<th data-options="field:'id',width:80">宝箱Id</th>
										<th data-options="field:'status',width:80">状态</th>
									</tr>
								</thead>
							</table>
							<table title="活跃完成状态" class="easyui-datagrid" style="min-height:200px" id="activeItems"
							data-options="singleSelect:true,striped:true,fitColumns:true,emptyMsg:'暂无数据'">
								<thead>
									<tr>
										<th data-options="field:'id',width:80">id</th>
										<th data-options="field:'status',width:80">状态</th>
									</tr>
								</thead>
							</table>
						</div>
						<div class="easyui-panel" title="指挥官" style="padding: 5px 5px;">
							<table class="easyui-datagrid" style="min-height:200px" id="commander"
							data-options="singleSelect:true,striped:true,fitColumns:true,emptyMsg:'暂无数据'">
								<thead>
									<tr>
										<th data-options="field:'name',width:80">名称</th>
										<th data-options="field:'level',width:80">等级</th>
										<th data-options="field:'exp',width:80">经验</th>
										<th data-options="field:'cPoint',width:80">统帅点</th>
										<th data-options="field:'energy',width:80">体力</th>
										<th data-options="field:'style',width:80">形象id</th>
										<th data-options="field:'talentPoints',width:80">已学天赋点</th>
										<th data-options="field:'useTalentPage',width:80">使用天赋页</th>
									</tr>
								</thead>
							</table>
						</div>
						<div class="easyui-panel" title="科研" style="padding: 5px 5px;">
							<table class="easyui-datagrid" style="min-height:200px" id="techs"
							data-options="singleSelect:true,striped:true,fitColumns:true,emptyMsg:'暂无数据'">
								<thead>
									<tr>
										<th data-options="field:'id',width:80">id</th>
										<th data-options="field:'level',width:80">等级</th>
										<th data-options="field:'state',width:80">状态</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
				</form>
			</div>
		</div>
		<%@include file="../foot.jsp" %>
	</div>
</body>
</html>