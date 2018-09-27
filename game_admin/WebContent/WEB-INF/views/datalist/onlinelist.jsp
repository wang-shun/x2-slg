<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>在线统计</title>
<%@include file="../head.jsp"%>
<script>
function searchData(){
	var queryParams = $('#dg').datagrid('options').queryParams;
	queryParams.startDt = $("#startDt").val();
	queryParams.endDt = $("#endDt").val();
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
				<table class="easyui-datagrid" style="height:800px" id="dg"
							data-options="singleSelect:true,striped:true,url:'/datasearch/datalist/onlinelistjson',fitColumns:true,emptyMsg:'暂无数据',pagination:true,toolbar:'#tb'">
					<thead>
						<tr>
							<th data-options="field:'playerId',width:80">时间</th>
							<th data-options="field:'playerName',width:120">登录账户</th>
							<th data-options="field:'commanderLevel',width:80">最高在线</th>
							<th data-options="field:'buildingLevel',width:80">平均在线</th>
						</tr>
					</thead>
				</table>
				<div id="tb" style="padding:2px 5px;">
					<select class="easyui-combobox" name="serverArea" label="区服选择" id="serverArea" labelWidth="60" style="width:140px;">
						<option>1</option>
						<option>2</option>
						<option>3</option>
						<option>4</option>
						<option>5</option>
					</select>
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