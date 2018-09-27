<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>恐怖份子</title>
<%@include file="../head.jsp"%>
<script>
function searchData(){
	var queryParams = $('#dg').datagrid('options').queryParams;
	queryParams.serverArea = $("#serverArea").val();
	$('#dg').datagrid('reload');
}

function searchData1(){
	var queryParams1 = $('#dg1').datagrid('options').queryParams;
	queryParams1.startDt = $("#startDt1").val();
	queryParams1.endDt = $("#endDt1").val();
	queryParams1.serverArea = $("#serverArea1").val();
	$('#dg1').datagrid('reload');
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
						<span>恐怖份子</span>
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
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/terroristjson',fitColumns:true,emptyMsg:'暂无数据',toolbar:'#tb'">
						<thead>
							<tr>
								<th data-options="field:'terroristLevel',width:40">等级</th>
								<th data-options="field:'firstKill',width:40">首杀时间（创角到首杀时间差）</th>
								<th data-options="field:'firstSoldier',width:40">首杀平均出征部队战力</th>
								<th data-options="field:'buildLevel',width:40">通关平均行政大楼等级</th>
								<th data-options="field:'commanderLevel',width:40">通关平均指挥官等级</th>
								<th data-options="field:'killCount',width:40">累计歼灭次数</th>
							</tr>
						</thead>
					</table>
					<div id="tb1" style="padding:2px 5px;background-color: #F5F5F5">
						<span>恐怖分子击杀次数</span>
						<select class="easyui-combobox" name="serverArea1" id="serverArea1" label="服务器选择" labelWidth="70" style="width:140px;">
							<option>1</option>
							<option>2</option>
							<option>3</option>
							<option>4</option>
							<option>5</option>
						</select>
						时间 从: <input class="Wdate" name="startDt1" id="startDt1" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
						到: <input class="Wdate" name="endDt1" id="endDt1" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="searchData1();">查询</a>
					</div>
					<table class="easyui-datagrid" style="min-height:200px" id="dg1"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/terroristjsoncount',fitColumns:true,emptyMsg:'暂无数据',toolbar:'#tb1'">
						<thead>
							<tr>
								<th data-options="field:'date',width:40">日期</th>
								<th data-options="field:'count',width:40">总击杀次数</th>
								<th data-options="field:'count1',width:40">1_5级</th>
								<th data-options="field:'count6',width:40">6_10级</th>
								<th data-options="field:'count11',width:40">11_15级</th>
								<th data-options="field:'count16',width:40">16_20级</th>
								<th data-options="field:'count21',width:40">21_25级</th>
								<th data-options="field:'count26',width:40">26_30级</th>
								<th data-options="field:'count31',width:40">31_35级</th>
								<th data-options="field:'count36',width:40">36_40级</th>
								<th data-options="field:'count41',width:40">41_45级</th>
								<th data-options="field:'count46',width:40">46_50级</th>
								<th data-options="field:'count51',width:40">51_55级</th>
								<th data-options="field:'count56',width:40">56_60级</th>
								<th data-options="field:'count61',width:40">61_65级</th>
								<th data-options="field:'count66',width:40">66_70级</th>
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