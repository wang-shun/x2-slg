<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>军团</title>
<%@include file="../head.jsp"%>
<script>
function searchData(){
	var queryParams = $('#dg').datagrid('options').queryParams;
	queryParams.serverArea = $("#serverArea").val();
	$('#dg').datagrid('reload');
	
	var queryParams1 = $('#dg1').datagrid('options').queryParams;
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
	
	var queryParams4 = $('#dg4').datagrid('options').queryParams;
	queryParams4.serverArea = $("#serverArea").val();
	$('#dg4').datagrid('reload');
	
	var queryParams5 = $('#dg5').datagrid('options').queryParams;
	queryParams5.serverArea = $("#serverArea").val();
	$('#dg5').datagrid('reload');
	
	var queryParams6 = $('#dg6').datagrid('options').queryParams;
	queryParams6.startDt = $("#startDt").val();
	queryParams6.endDt = $("#endDt").val();
	queryParams6.serverArea = $("#serverArea").val();
	$('#dg6').datagrid('reload');
	
	var queryParams7 = $('#dg7').datagrid('options').queryParams;
	queryParams7.startDt = $("#startDt").val();
	queryParams7.endDt = $("#endDt").val();
	queryParams7.serverArea = $("#serverArea").val();
	$('#dg7').datagrid('reload');
	
	var queryParams8 = $('#dg8').datagrid('options').queryParams;
	queryParams8.startDt = $("#startDt").val();
	queryParams8.endDt = $("#endDt").val();
	queryParams8.serverArea = $("#serverArea").val();
	$('#dg8').datagrid('reload');
}

function searchData2(){
	var queryParams2 = $('#dg2').datagrid('options').queryParams;
	queryParams2.startDt = $("#startDt2").val();
	queryParams2.endDt = $("#endDt2").val();
	queryParams2.serverArea = $("#serverArea").val();
	$('#dg2').datagrid('reload');
}

function searchData3(){
	var queryParams3 = $('#dg3').datagrid('options').queryParams;
	queryParams3.startDt = $("#startDt3").val();
	queryParams3.endDt = $("#endDt3").val();
	queryParams3.serverArea = $("#serverArea").val();
	$('#dg3').datagrid('reload');
}

function searchData6(){
	var queryParams6 = $('#dg6').datagrid('options').queryParams;
	queryParams6.startDt = $("#startDt6").val();
	queryParams6.endDt = $("#endDt6").val();
	queryParams6.serverArea = $("#serverArea").val();
	$('#dg6').datagrid('reload');
}

function searchData7(){
	var queryParams7 = $('#dg7').datagrid('options').queryParams;
	queryParams7.startDt = $("#startDt7").val();
	queryParams7.endDt = $("#endDt7").val();
	queryParams7.serverArea = $("#serverArea").val();
	$('#dg7').datagrid('reload');
}

function searchData8(){
	var queryParams8 = $('#dg8').datagrid('options').queryParams;
	queryParams8.startDt = $("#startDt8").val();
	queryParams8.endDt = $("#endDt8").val();
	queryParams8.serverArea = $("#serverArea").val();
	$('#dg8').datagrid('reload');
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
					<div id="tb" style="padding:2px 5px;background-color: #F5F5F5">
						<span>军团等级&科研大楼</span>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="showLevelDetail();">查看科技等级详情</a>
					</div>
					<table class="easyui-datagrid" style="min-height:200px" id="dg"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/groupjsonlevel',fitColumns:true,emptyMsg:'暂无数据',toolbar:'#tb'">
						<thead>
							<tr>
								<th rowspan="2" data-options="field:'level',width:60">等级</th>
								<th colspan="3" data-options="width:160">军团等级抵达等级平均时间（军团创建时间到升级时间差）</th>
								<th colspan="3" data-options="width:160">科研大楼抵达等级平均时间（军团创建时间到升级时间差）</th>
							</tr>
							<tr>
								<th data-options="field:'avgGroup',width:40">均值</th>
								<th data-options="field:'minGroup',width:40">最小值</th>
								<th data-options="field:'maxGroup',width:40">最大值</th>
								<th data-options="field:'avgScience',width:40">均值</th>
								<th data-options="field:'minScience',width:40">最小值</th>
								<th data-options="field:'maxScience',width:40">最大值</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" title="军团资金日获得军团数量" style="min-height:200px" id="dg1"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/groupjsonmoney',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'money',width:40">资金数</th>
								<th data-options="field:'groupNum',width:40">军团数量</th>
								<th data-options="field:'percent',width:40">百分比</th>
							</tr>
						</thead>
					</table>
					<div id="tb2" style="padding:2px 5px;background-color: #F5F5F5">
						<span>军团捐献&科技捐献账户数量</span>
						时间 从: <input class="Wdate" name="startDt2" id="startDt2" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
						到: <input class="Wdate" name="endDt2" id="endDt2" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="searchData2();">查询</a>
					</div>
					<table class="easyui-datagrid" style="min-height:200px" id="dg2"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/groupjsondonate',fitColumns:true,emptyMsg:'暂无数据',toolbar:'#tb2'">
						<thead>
							<tr>
								<th rowspan="2" data-options="field:'date',width:60">每天捐献次数</th>
								<th colspan="5" data-options="width:160">军团等级捐献</th>
								<th colspan="5" data-options="width:160">军团科技捐献</th>
							</tr>
							<tr>
								<th data-options="field:'levelSteel',width:40">钢材</th>
								<th data-options="field:'levelOil',width:40">石油</th>
								<th data-options="field:'levelRare',width:40">稀土</th>
								<th data-options="field:'levelMoney',width:40">黄金</th>
								<th data-options="field:'levelDiamond',width:40">钻石</th>
								<th data-options="field:'scienceSteel',width:40">钢材</th>
								<th data-options="field:'scienceOil',width:40">石油</th>
								<th data-options="field:'scienceRare',width:40">稀土</th>
								<th data-options="field:'scienceMoney',width:40">黄金</th>
								<th data-options="field:'scienceDiamond',width:40">钻石</th>
							</tr>
						</thead>
					</table>
					<div id="tb3" style="padding:2px 5px;background-color: #F5F5F5">
						<span>平均每日援建次数</span>
						时间 从: <input class="Wdate" name="startDt3" id="startDt3" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
						到: <input class="Wdate" name="endDt3" id="endDt3" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="searchData3();">查询</a>
					</div>
					<table class="easyui-datagrid" style="min-height:200px" id="dg3"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/resourcesjsonhelp',fitColumns:true,emptyMsg:'暂无数据',toolbar:'#tb3'">
						<thead>
							<tr>
								<th data-options="field:'helpCount',width:40">平均每日援建次数</th>
								<th data-options="field:'playerNum',width:40">账户数量</th>
								<th data-options="field:'percent',width:40">百分比</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" title="军团宝箱等级" style="min-height:200px" id="dg4"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/resourcesjsonbox',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'boxLevel',width:40">军团宝箱等级</th>
								<th data-options="field:'groupNum',width:40">军团数量</th>
								<th data-options="field:'percent',width:40">百分比</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" title="军团活跃等级" style="min-height:200px" id="dg5"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/resourcesjsonactive',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'activeLevel',width:40">军团活跃等级</th>
								<th data-options="field:'groupNum',width:40">军团数量</th>
								<th data-options="field:'percent',width:40">百分比</th>
							</tr>
						</thead>
					</table>
					<div id="tb6" style="padding:2px 5px;background-color: #F5F5F5">
						<span>军团超级矿每周平均次数 军团数量</span>
						时间 从: <input class="Wdate" name="startDt6" id="startDt6" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
						到: <input class="Wdate" name="endDt6" id="endDt6" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="searchData6();">查询</a>
					</div>
					<table class="easyui-datagrid" style="min-height:200px" id="dg6"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/resourcesjsonsuper',fitColumns:true,emptyMsg:'暂无数据',toolbar:'#tb6'">
						<thead>
							<tr>
								<th data-options="field:'weekCount',width:40">每周次数</th>
								<th data-options="field:'superSteel',width:40">超级钢铁</th>
								<th data-options="field:'superOil',width:40">超级石油</th>
								<th data-options="field:'superRare',width:40">超级稀土</th>
								<th data-options="field:'superMoney',width:40">超级黄金</th>
							</tr>
						</thead>
					</table>
					<div id="tb7" style="padding:2px 5px;background-color: #F5F5F5">
						<span>平均每天累计演习时间对应账户数量</span>
						时间 从: <input class="Wdate" name="startDt7" id="startDt7" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
						到: <input class="Wdate" name="endDt7" id="endDt7" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="searchData7();">查询</a>
					</div>
					<table class="easyui-datagrid" style="min-height:200px" id="dg7"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/resourcesjsonexercise',fitColumns:true,emptyMsg:'暂无数据',toolbar:'#tb7'">
						<thead>
							<tr>
								<th data-options="field:'exerciseCount',width:40">平均每天累计参与实践</th>
								<th data-options="field:'playerNum',width:40">账户数量</th>
								<th data-options="field:'percent',width:40">百分比</th>
							</tr>
						</thead>
					</table>
					<div id="tb8" style="padding:2px 5px;background-color: #F5F5F5">
						<span>军事演习参与率</span>
						时间 从: <input class="Wdate" name="startDt8" id="startDt8" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
						到: <input class="Wdate" name="endDt8" id="endDt8" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="searchData8();">查询</a>
					</div>
					<table class="easyui-datagrid" style="min-height:200px" id="dg8"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/resourcesjsonexerciserate',fitColumns:true,emptyMsg:'暂无数据',toolbar:'#tb8'">
						<thead>
							<tr>
								<th data-options="field:'date',width:40">日期</th>
								<th data-options="field:'noGroup',width:40">无联盟活跃账户</th>
								<th data-options="field:'inGroup',width:40">有联盟活跃账户</th>
								<th data-options="field:'joinPlayer',width:40">参与军团演习账户</th>
								<th data-options="field:'percent',width:40">军团成员参与比（参与军团演习账户/有军团账户）</th>
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