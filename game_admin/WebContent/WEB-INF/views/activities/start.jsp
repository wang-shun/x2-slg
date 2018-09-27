<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>运营活动开启设置</title>
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
				<form action="/activities/activity/start" method="POST" id="pageForm">
					<div class="easyui-panel">
						<span style="display: block;font-size: 24px;font-weight: bold;text-align: center;">运营活动开启设置</span>
					</div>
					<div class="easyui-panel">
						<select class="easyui-combobox" name="channel" label="选择时间类型 limitActivity  timeType" labelWidth="180" style="width:400px;">
							<option value="1开服第几天到第几天开启">1开服第几天到第几天开启</option>
         					<option value="2固定时间（自然日）开启">2固定时间（自然日）开启</option>
						</select>
					</div>
					<div class="easyui-panel">
						开服第<input class="easyui-numberbox" value="100" style="width:140px;">天开启
						至
						开服第 <input class="easyui-numberbox" value="100" style="width:140px;">天结束
					</div>
					<div class="easyui-panel">
						<span style="display: block;font-size: 20px;font-weight: bold;">设置该期间活动</span>
						<table class="easyui-datagrid" style="min-height:300px" id="dg"
								data-options="singleSelect:true,striped:true,fitColumns:true,emptyMsg:'暂无数据'">
							<thead>
								<tr>
									<th data-options="field:'activityId',width:80">活动ID</th>
									<th data-options="field:'activityName',width:80">活动名</th>
									<th data-options="field:'activityType',width:80">活动类型</th>
									<th data-options="field:'rule',width:120">特有规则</th>
								</tr>
							</thead>
						</table>
					</div>
					<div class="easyui-panel">
						<span style="display: block;text-align: center;"><a href="javascript:;" class="easyui-linkbutton">保存</a></span>
					</div>
				</form>
			</div>
		</div>
		<%@include file="../foot.jsp" %>
	</div>
</body>
</html>