<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>事件</title>
<%@include file="../head.jsp"%>
<script>
function searchData(){
	var queryParams = $('#dg').datagrid('options').queryParams;
	queryParams.startDt = $("#startDt").val();
	queryParams.endDt = $("#endDt").val();
	queryParams.serverArea = $("#serverArea").val();
	$('#dg').datagrid('reload');
	
	var queryParams1 = $('#dg1').datagrid('options').queryParams;
	queryParams1.startDt = $("#startDt").val();
	queryParams1.endDt = $("#endDt").val();
	queryParams1.serverArea = $("#serverArea").val();
	$('#dg1').datagrid('reload');
	
	var queryParams2 = $('#dg2').datagrid('options').queryParams;
	queryParams2.startDt = $("#startDt").val();
	queryParams2.endDt = $("#endDt").val();
	queryParams2.serverArea = $("#serverArea").val();
	$('#dg2').datagrid('reload');
	
	var queryParams3 = $('#dg3').datagrid('options').queryParams;
	queryParams3.startDt = $("#startDt").val();
	queryParams3.endDt = $("#endDt").val();
	queryParams3.serverArea = $("#serverArea").val();
	$('#dg3').datagrid('reload');
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
					<div id="tb" style="padding:2px 5px;background-color: #F5F5F5">
						<span>个人事件达成率</span>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="">数量</a>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="">百分比</a>
					</div>
					<table class="easyui-datagrid" style="min-height:200px" id="dg"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/eventjsonplayer',fitColumns:true,emptyMsg:'暂无数据',toolbar:'#tb'">
						<thead>
							<tr>
								<th rowspan="2" data-options="field:'date',width:40">日期</th>
								<th rowspan="2" data-options="field:'unReach',width:40">未达成</th>
								<th colspan="4" data-options="field:'capacity',width:40">战斗力提升</th>
								<th colspan="4" data-options="field:'science',width:40">科研战斗力提升</th>
								<th colspan="4" data-options="field:'build',width:40">基地建设</th>
								<th colspan="4" data-options="field:'army',width:40">部队生产</th>
								<th colspan="4" data-options="field:'resources',width:40">资源采集</th>
								<th colspan="4" data-options="field:'simulator',width:40">战争模拟器</th>
								<th colspan="4" data-options="field:'pvp',width:40">PVP</th>
							</tr>
							<tr>
								<th data-options="field:'capacityFst',width:40">第1段</th>
								<th data-options="field:'capacitySnd',width:40">第2段</th>
								<th data-options="field:'capacityTrd',width:40">第3段</th>
								<th data-options="field:'capacityUn',width:40">未达成</th>
								<th data-options="field:'scienceFst',width:40">第1段</th>
								<th data-options="field:'scienceSnd',width:40">第2段</th>
								<th data-options="field:'scienceTrd',width:40">第3段</th>
								<th data-options="field:'scienceUn',width:40">未达成</th>
								<th data-options="field:'buildFst',width:40">第1段</th>
								<th data-options="field:'buildSnd',width:40">第2段</th>
								<th data-options="field:'buildTrd',width:40">第3段</th>
								<th data-options="field:'buildUn',width:40">未达成</th>
								<th data-options="field:'armyFst',width:40">第1段</th>
								<th data-options="field:'armySnd',width:40">第2段</th>
								<th data-options="field:'armyTrd',width:40">第3段</th>
								<th data-options="field:'armyUn',width:40">未达成</th>
								<th data-options="field:'resourcesFst',width:40">第1段</th>
								<th data-options="field:'resourcesSnd',width:40">第2段</th>
								<th data-options="field:'resourcesTrd',width:40">第3段</th>
								<th data-options="field:'resourcesUn',width:40">未达成</th>
								<th data-options="field:'simulatorFst',width:40">第1段</th>
								<th data-options="field:'simulatorSnd',width:40">第2段</th>
								<th data-options="field:'simulatorTrd',width:40">第3段</th>
								<th data-options="field:'simulatorUn',width:40">未达成</th>
								<th data-options="field:'pvpFst',width:40">第1段</th>
								<th data-options="field:'pvpSnd',width:40">第2段</th>
								<th data-options="field:'pvpTrd',width:40">第3段</th>
								<th data-options="field:'pvpUn',width:40">未达成</th>
							</tr>
						</thead>
					</table>
					<div id="tb1" style="padding:2px 5px;background-color: #F5F5F5">
						<span>荣耀事件达成率</span>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="">数量</a>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="">百分比</a>
					</div>
					<table class="easyui-datagrid" style="min-height:200px" id="dg1"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/eventjsonhonour',fitColumns:true,emptyMsg:'暂无数据',toolbar:'#tb1'">
						<thead>
							<tr>
								<th rowspan="2" data-options="field:'date',width:40">日期</th>
								<th rowspan="2" data-options="field:'unReach',width:40">未达成</th>
								<th colspan="4" data-options="field:'capacity',width:40">战斗力提升</th>
								<th colspan="4" data-options="field:'science',width:40">科研战斗力提升</th>
								<th colspan="4" data-options="field:'build',width:40">基地建设</th>
								<th colspan="4" data-options="field:'army',width:40">部队生产</th>
								<th colspan="4" data-options="field:'resources',width:40">资源采集</th>
								<th colspan="4" data-options="field:'simulator',width:40">战争模拟器</th>
								<th colspan="4" data-options="field:'pvp',width:40">PVP</th>
							</tr>
							<tr>
								<th data-options="field:'capacityFst',width:40">第1段</th>
								<th data-options="field:'capacitySnd',width:40">第2段</th>
								<th data-options="field:'capacityTrd',width:40">第3段</th>
								<th data-options="field:'capacityUn',width:40">未达成</th>
								<th data-options="field:'scienceFst',width:40">第1段</th>
								<th data-options="field:'scienceSnd',width:40">第2段</th>
								<th data-options="field:'scienceTrd',width:40">第3段</th>
								<th data-options="field:'scienceUn',width:40">未达成</th>
								<th data-options="field:'buildFst',width:40">第1段</th>
								<th data-options="field:'buildSnd',width:40">第2段</th>
								<th data-options="field:'buildTrd',width:40">第3段</th>
								<th data-options="field:'buildUn',width:40">未达成</th>
								<th data-options="field:'armyFst',width:40">第1段</th>
								<th data-options="field:'armySnd',width:40">第2段</th>
								<th data-options="field:'armyTrd',width:40">第3段</th>
								<th data-options="field:'armyUn',width:40">未达成</th>
								<th data-options="field:'resourcesFst',width:40">第1段</th>
								<th data-options="field:'resourcesSnd',width:40">第2段</th>
								<th data-options="field:'resourcesTrd',width:40">第3段</th>
								<th data-options="field:'resourcesUn',width:40">未达成</th>
								<th data-options="field:'simulatorFst',width:40">第1段</th>
								<th data-options="field:'simulatorSnd',width:40">第2段</th>
								<th data-options="field:'simulatorTrd',width:40">第3段</th>
								<th data-options="field:'simulatorUn',width:40">未达成</th>
								<th data-options="field:'pvpFst',width:40">第1段</th>
								<th data-options="field:'pvpSnd',width:40">第2段</th>
								<th data-options="field:'pvpTrd',width:40">第3段</th>
								<th data-options="field:'pvpUn',width:40">未达成</th>
							</tr>
						</thead>
					</table>
					<div id="tb2" style="padding:2px 5px;background-color: #F5F5F5">
						<span>军团事件达成率</span>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="">数量</a>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="">百分比</a>
					</div>
					<table class="easyui-datagrid" style="min-height:200px" id="dg2"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/eventjsongroup',fitColumns:true,emptyMsg:'暂无数据',toolbar:'#tb2'">
						<thead>
							<tr>
								<th rowspan="2" data-options="field:'date',width:40">日期</th>
								<th colspan="4" data-options="field:'capacity',width:40">军团战斗力提升</th>
								<th colspan="4" data-options="field:'science',width:40">领土争夺</th>
							</tr>
							<tr>
								<th data-options="field:'capacityUn',width:40">未达成</th>
								<th data-options="field:'capacityFst',width:40">第1段</th>
								<th data-options="field:'capacitySnd',width:40">第2段</th>
								<th data-options="field:'capacityTrd',width:40">第3段</th>
								<th data-options="field:'terroristUn',width:40">未达成</th>
								<th data-options="field:'terroristFst',width:40">第1段</th>
								<th data-options="field:'terroristSnd',width:40">第2段</th>
								<th data-options="field:'terroristTrd',width:40">第3段</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" title="时间段对应事件达成次数" style="min-height:200px" id="dg3"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/eventjsontime',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'startTime',width:40">时段</th>
								<th data-options="field:'endTime',width:40">时段</th>
								<th data-options="field:'reachCount',width:40">事件达成次数</th>
								<th data-options="field:'percent',width:40">百分比</th>
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