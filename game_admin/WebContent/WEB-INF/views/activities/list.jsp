<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>查看活动库</title>
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
							运营活动库
						</span>
					</div>
					<table class="easyui-datagrid" style="min-height:300px" id="dg"
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