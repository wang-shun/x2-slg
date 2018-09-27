<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>部队剩余</title>
<%@include file="../head.jsp"%>
<script>
function searchData(){
	var queryParams = $('#dg').datagrid('options').queryParams;
	queryParams.serverArea = $("#serverArea").val();
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
				<div class="easyui-panel">
					<div id="tb" style="padding:2px 5px;background-color: #F5F5F5">
						<select class="easyui-combobox" name="serverArea" id="serverArea" label="服务器选择" labelWidth="70" style="width:140px;">
							<option>1</option>
							<option>2</option>
							<option>3</option>
							<option>4</option>
							<option>5</option>
						</select>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="searchData();">查询</a>
					</div>
					<table class="easyui-datagrid" style="min-height:200px" id="dg"
						data-options="singleSelect:true,striped:true,url:'/consumptionanalysis/analysis/armyjson',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'armyType',width:40">部队大兵种</th>
								<th data-options="field:'armyLevel',width:40">兵种等级</th>
								<th data-options="field:'freeSoldier',width:40">闲兵数量</th>
								<th data-options="field:'injurySoldier',width:40">伤兵数量</th>
								<th data-options="field:'avgFreeSoldier',width:40">闲兵人均</th>
								<th data-options="field:'avgInjurySoldier',width:40">伤兵人均</th>
								<th data-options="field:'trend',width:40">趋势</th>
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