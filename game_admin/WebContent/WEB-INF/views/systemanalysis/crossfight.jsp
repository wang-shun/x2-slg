<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>超时空战斗</title>
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
					<table class="easyui-datagrid" style="min-height:200px" id="dg"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/crossfightjson',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'date',width:40">日期</th>
								<th data-options="field:'fightCount',width:40">战斗次数</th>
								<th data-options="field:'successCount',width:40">胜利次数</th>
								<th data-options="field:'failCount',width:40">失败次数</th>
								<th data-options="field:'fight1',width:40">战斗1次</th>
								<th data-options="field:'fight2',width:40">战斗2次</th>
								<th data-options="field:'fight3',width:40">战斗3次</th>
								<th data-options="field:'fight4',width:40">战斗4次</th>
								<th data-options="field:'fight5',width:40">战斗5次</th>
								<th data-options="field:'fight6',width:40">战斗6次</th>
								<th data-options="field:'fight7',width:40">战斗7次</th>
								<th data-options="field:'fight8',width:40">战斗8次</th>
								<th data-options="field:'fight9',width:40">战斗9次</th>
								<th data-options="field:'fight10',width:40">战斗10次</th>
								<th data-options="field:'fight11',width:40">战斗11次</th>
								<th data-options="field:'fight12',width:40">战斗12次</th>
								<th data-options="field:'fight13',width:40">战斗13次</th>
								<th data-options="field:'fight14',width:40">战斗14次</th>
								<th data-options="field:'fight15',width:40">战斗15次</th>
								<th data-options="field:'fight16',width:40">战斗16次</th>
								<th data-options="field:'fight17',width:40">战斗17次</th>
								<th data-options="field:'fight18',width:40">战斗18次</th>
								<th data-options="field:'fight19',width:40">战斗19次</th>
								<th data-options="field:'fight20',width:40">战斗20次</th>
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