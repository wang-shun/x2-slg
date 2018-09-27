<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>部队</title>
<%@include file="../head.jsp"%>
<script>
function searchData(){
	var queryParams = $('#dg').datagrid('options').queryParams;
	queryParams.serverArea = $("#serverArea").val();
	$('#dg').datagrid('reload');
	
	var queryParams1 = $('#dg1').datagrid('options').queryParams;
	queryParams1.serverArea = $("#serverArea").val();
	$('#dg1').datagrid('reload');
}

function searchData2(){
	var queryParams2 = $('#dg2').datagrid('options').queryParams;
	queryParams2.startDt = $("#startDt2").val();
	queryParams2.endDt = $("#endDt2").val();
	queryParams2.serverArea = $("#serverArea2").val();
	$('#dg2').datagrid('reload');
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
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="searchData();">查询</a>
					</div>
					<table title="系统兵种" class="easyui-datagrid" style="min-height:200px" id="dg"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/armyjsonsystem',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'armyType',width:40">部队</th>
								<th data-options="field:'produce',width:40">生产部队数量</th>
								<th data-options="field:'fight',width:40">战损部队数量</th>
								<th data-options="field:'destory',width:40">销毁部队数量</th>
								<th data-options="field:'other',width:40">其他部队消耗</th>
								<th data-options="field:'owner',width:40">拥有部队数量</th>
							</tr>
						</thead>
					</table>
					<table title="蓝图兵种" class="easyui-datagrid" style="min-height:200px" id="dg1"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/armyjsonblue',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'armyType',width:40">部队</th>
								<th data-options="field:'produce',width:40">生产部队数量</th>
								<th data-options="field:'fight',width:40">战损部队数量</th>
								<th data-options="field:'destory',width:40">销毁部队数量</th>
								<th data-options="field:'other',width:40">其他部队消耗</th>
								<th data-options="field:'owner',width:40">拥有部队数量</th>
							</tr>
						</thead>
					</table>
					<div style="padding:2px 5px;background-color: #F5F5F5">
						<select class="easyui-combobox" name="serverArea2" id="serverArea2" label="服务器选择" labelWidth="70" style="width:140px;">
							<option>1</option>
							<option>2</option>
							<option>3</option>
							<option>4</option>
							<option>5</option>
						</select>
						时间 从: <input class="Wdate" name="startDt2" id="startDt2" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
						到: <input class="Wdate" name="endDt2" id="endDt2" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="searchData2();">查询</a>
					</div>
					<table title="蓝图修改次数" class="easyui-datagrid" style="min-height:200px" id="dg2"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/armyjsonbluechange',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'date',width:40">日期</th>
								<th data-options="field:'t1',width:40">T1蓝图修改次数</th>
								<th data-options="field:'t2',width:40">T2蓝图修改次数</th>
								<th data-options="field:'t3',width:40">T3蓝图修改次数</th>
								<th data-options="field:'t4',width:40">T4蓝图修改次数</th>
								<th data-options="field:'t5',width:40">T5蓝图修改次数</th>
								<th data-options="field:'s1',width:40">S1蓝图修改次数</th>
								<th data-options="field:'s2',width:40">S2蓝图修改次数</th>
								<th data-options="field:'s3',width:40">S3蓝图修改次数</th>
								<th data-options="field:'s4',width:40">S4蓝图修改次数</th>
								<th data-options="field:'s5',width:40">S5蓝图修改次数</th>
								<th data-options="field:'f1',width:40">F1蓝图修改次数</th>
								<th data-options="field:'f2',width:40">F2蓝图修改次数</th>
								<th data-options="field:'f3',width:40">F3蓝图修改次数</th>
								<th data-options="field:'f4',width:40">F4蓝图修改次数</th>
								<th data-options="field:'f5',width:40">F5蓝图修改次数</th>
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