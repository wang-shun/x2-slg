<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>服务器信息</title>
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
				<table class="easyui-datagrid" style="min-height:600px" id="dg"
							data-options="singleSelect:true,striped:true,url:'/datasearch/datalist/serverinfojson',fitColumns:true,emptyMsg:'暂无数据',pagination:true">
					<thead>
						<tr>
							<th data-options="field:'serverArea',width:80">服务器ID</th>
							<th data-options="field:'serverName',width:80">服务器名</th>
							<th data-options="field:'startDate',width:80">开启时间</th>
							<th data-options="field:'startDays',width:120">开启天数</th>
							<th data-options="field:'playerNum',width:80">服累计用户数量</th>
							<th data-options="field:'chargeTotal',width:140">服累计充值（￥）</th>
							<th data-options="field:'avgCharge',width:140">充值用户均付费值（￥）</th>
							<th data-options="field:'avgChargeAll',width:140">人均付费值（￥）</th>
							<th data-options="field:'operate1',width:140">操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
		<%@include file="../foot.jsp" %>
	</div>
</body>
</html>