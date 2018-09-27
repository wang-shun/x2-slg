<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>运营商信息</title>
<%@include file="../head.jsp"%>
<script>
function searchData(){
	var queryParams = $('#dg').datagrid('options').queryParams;
	queryParams.startDt = $("#startDt").val();
	queryParams.endDt = $("#endDt").val();
	$('#dg').datagrid('reload');
}
</script>
</head>
<body>
	<div class="gm-wrap">
		<%@include file="../top.jsp" %>
		<div class="gm-content">
			<%@include file="../left.jsp" %>
			<div class="gm-main">
				<table class="easyui-datagrid" style="min-height:600px" id="dg"
							data-options="singleSelect:true,striped:true,url:'/datasearch/datalist/operationlistjson',fitColumns:true,emptyMsg:'暂无数据',toolbar:'#tb'">
					<thead>
						<tr>
							<th data-options="field:'operationName',width:80">运营商名称</th>
							<th data-options="field:'serverTotal',width:80">开服总数量</th>
							<th data-options="field:'firstServerDate',width:80">首服开启时间</th>
							<th data-options="field:'lastServerDate',width:120">末服开启时间</th>
							<th data-options="field:'chargeTotal',width:80">充值总金额（￥）</th>
							<th data-options="field:'chargePlayerNum',width:140">充值总人数</th>
							<th data-options="field:'installNum',width:140">总装机量</th>
							<th data-options="field:'registerPlayerNum',width:140">总注册用户数</th>
							<th data-options="field:'avgCharge',width:140">充值用户均付费值（￥）</th>
							<th data-options="field:'avgChargeAll',width:140">人均付费值（￥）</th>
						</tr>
					</thead>
				</table>
				<div id="tb" style="padding:2px 5px;">
					时间 从: <input class="Wdate" name="startDt" id="startDt" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
					到: <input class="Wdate" name="endDt" id="endDt" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
					<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="searchData();">查询</a>
				</div>
			</div>
		</div>
		<%@include file="../foot.jsp" %>
	</div>
</body>
</html>