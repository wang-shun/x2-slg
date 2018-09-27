<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>运营活动状态</title>
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
				<div class="easyui-panel">
					<div class="easyui-panel">
						<span style="display: block;font-size: 24px;font-weight: bold;text-align: center;">
							运营活动一览
						</span>
						<span style="display: block;">
							<select class="easyui-combobox" name="serverArea" id="serverArea" label="服务器选择" labelWidth="70" style="width:140px;">
								<option>1</option>
								<option>2</option>
								<option>3</option>
								<option>4</option>
								<option>5</option>
							</select>
							时间 从: <input class="Wdate" name="startDt" id="startDt" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
							到: <input class="Wdate" name="endDt" id="endDt" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
							<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="searchData();">查询</a>
						</span>
						<span style="display: block;">
							<a href="javascript:;" class="easyui-linkbutton">保存</a>保存后只是将活动计划存在GM服务器，并不会生效，服务器依然走之前的配置，必须点立即生效后才会替换次日开始的所有活动配置
						</span>
						<span style="display: block;">
							<a href="javascript:;" class="easyui-linkbutton">立即生效</a>次日24:00生效，正在进行或已结束的活动不可编辑，只可通过活动ID在【运营活动库】强制关闭。
						</span>
					</div>
					<table class="easyui-datagrid" title="GM服务器已保存的配置" style="min-height:300px" id="dg1"
								data-options="singleSelect:true,striped:true,fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'activityId',width:80">活动ID</th>
								<th data-options="field:'activityName',width:80">活动名</th>
								<th data-options="field:'activityType',width:80">活动类型</th>
								<th data-options="field:'rule',width:120">特有规则</th>
								<th data-options="field:'remark',width:80">活动备注</th>
								<th data-options="field:'gateOpen',width:80">外网是否开启中</th>
								<th data-options="field:'close',width:80">强制关闭</th>
								<th data-options="field:'updator',width:120">活动内容修改者</th>
								<th data-options="field:'operate1',width:80">操作</th>
								<th data-options="field:'operate2',width:80">操作</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" title="外网正在生效的配置" style="min-height:300px" id="dg1"
							data-options="singleSelect:true,striped:true,fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'activityId',width:80">活动ID</th>
								<th data-options="field:'activityName',width:80">活动名</th>
								<th data-options="field:'activityType',width:80">活动类型</th>
								<th data-options="field:'rule',width:120">特有规则</th>
								<th data-options="field:'remark',width:80">活动备注</th>
								<th data-options="field:'gateOpen',width:80">外网是否开启中</th>
								<th data-options="field:'close',width:80">强制关闭</th>
								<th data-options="field:'updator',width:120">活动内容修改者</th>
								<th data-options="field:'operate1',width:80">操作</th>
								<th data-options="field:'operate2',width:80">操作</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
		<%@include file="../foot.jsp" %>
	</div>
</body>
</html>