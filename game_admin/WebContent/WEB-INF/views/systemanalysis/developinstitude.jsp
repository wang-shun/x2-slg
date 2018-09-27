<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>勘探开发院</title>
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
				<div class="easyui-panel">
					<div style="padding:2px 5px;background-color: #F5F5F5">
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
					</div>
					<div class="easyui-panel">
					
					</div>
					<table title="勘探开发院  资源收取次数对应 人数" class="easyui-datagrid" style="min-height:200px" id="dg"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/developinstitudejson',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'date',width:40">日期</th>
								<th data-options="field:'collect1',width:40">收取1次</th>
								<th data-options="field:'collect2',width:40">收取2次</th>
								<th data-options="field:'collect3',width:40">收取3次</th>
								<th data-options="field:'collect4',width:40">收取4次</th>
								<th data-options="field:'collect5',width:40">收取5次</th>
								<th data-options="field:'collect6',width:40">收取6次</th>
								<th data-options="field:'collect7',width:40">收取7次</th>
								<th data-options="field:'collect8',width:40">收取8次</th>
								<th data-options="field:'collect9',width:40">收取9次</th>
								<th data-options="field:'collect10',width:40">收取10次</th>
								<th data-options="field:'collect11',width:40">收取11次</th>
								<th data-options="field:'collect12',width:40">收取12次</th>
								<th data-options="field:'collect13',width:40">收取13次</th>
								<th data-options="field:'collect14',width:40">收取14次</th>
								<th data-options="field:'collect15',width:40">收取15次</th>
								<th data-options="field:'collect16',width:40">收取16次</th>
								<th data-options="field:'collect17',width:40">收取17次</th>
								<th data-options="field:'collect18',width:40">收取18次</th>
								<th data-options="field:'collect19',width:40">收取19次</th>
								<th data-options="field:'collect20',width:40">收取20次</th>
								<th data-options="field:'collect21',width:40">收取21次</th>
								<th data-options="field:'collect22',width:40">收取22次</th>
								<th data-options="field:'collect23',width:40">收取23次</th>
								<th data-options="field:'collect24',width:40">收取≥24次</th>
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