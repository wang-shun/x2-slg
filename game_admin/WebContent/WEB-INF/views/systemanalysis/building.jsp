<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>建筑</title>
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
					<table title="建筑等级达成人数(矿车和围墙取平均值)" class="easyui-datagrid" style="min-height:200px" id="dg"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/buildingjsonlevel',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'buildingId',width:40">建筑id</th>
								<th data-options="field:'name',width:40">名字</th>
								<th data-options="field:'count1',width:40">1级人数</th>
								<th data-options="field:'count2',width:40">2级人数</th>
								<th data-options="field:'count3',width:40">3级人数</th>
								<th data-options="field:'count4',width:40">4级人数</th>
								<th data-options="field:'count5',width:40">5级人数</th>
								<th data-options="field:'count6',width:40">6级人数</th>
								<th data-options="field:'count7',width:40">7级人数</th>
								<th data-options="field:'count8',width:40">8级人数</th>
								<th data-options="field:'count9',width:40">9级人数</th>
								<th data-options="field:'count10',width:40">10级人数</th>
								<th data-options="field:'count11',width:40">11级人数</th>
								<th data-options="field:'count12',width:40">12级人数</th>
								<th data-options="field:'count13',width:40">13级人数</th>
								<th data-options="field:'count14',width:40">14级人数</th>
								<th data-options="field:'count15',width:40">15级人数</th>
								<th data-options="field:'count16',width:40">16级人数</th>
								<th data-options="field:'count17',width:40">17级人数</th>
								<th data-options="field:'count18',width:40">18级人数</th>
								<th data-options="field:'count19',width:40">19级人数</th>
								<th data-options="field:'count20',width:40">20级人数</th>
								<th data-options="field:'count21',width:40">21级人数</th>
								<th data-options="field:'count22',width:40">22级人数</th>
								<th data-options="field:'count23',width:40">23级人数</th>
								<th data-options="field:'count24',width:40">24级人数</th>
								<th data-options="field:'count25',width:40">25级人数</th>
								<th data-options="field:'count26',width:40">26级人数</th>
								<th data-options="field:'count27',width:40">27级人数</th>
								<th data-options="field:'count28',width:40">28级人数</th>
								<th data-options="field:'count29',width:40">29级人数</th>
								<th data-options="field:'count30',width:40">30级人数</th>
							</tr>
						</thead>
					</table>
					<table title="行政大楼抵达等级平均时间（创角时间到升级时间差）" class="easyui-datagrid" style="min-height:200px" id="dg1"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/buildingjsontime',fitColumns:true,emptyMsg:'暂无数据',toolbar:'#tb'">
						<thead>
							<tr>
								<th data-options="field:'level',width:40">行政大楼等级</th>
								<th data-options="field:'avg',width:40">均值</th>
								<th data-options="field:'min',width:40">最小值</th>
								<th data-options="field:'max',width:40">最大值</th>
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