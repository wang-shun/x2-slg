<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>指挥官</title>
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
	queryParams2.serverArea = $("#serverArea").val();
	$('#dg2').datagrid('reload');
	
	var queryParams3 = $('#dg3').datagrid('options').queryParams;
	queryParams3.serverArea = $("#serverArea").val();
	$('#dg3').datagrid('reload');
	
	var queryParams4 = $('#dg4').datagrid('options').queryParams;
	queryParams4.serverArea = $("#serverArea").val();
	$('#dg4').datagrid('reload');
	
	var queryParams5 = $('#dg5').datagrid('options').queryParams;
	queryParams5.serverArea = $("#serverArea").val();
	$('#dg5').datagrid('reload');
	
	var queryParams6 = $('#dg6').datagrid('options').queryParams;
	queryParams6.serverArea = $("#serverArea").val();
	$('#dg6').datagrid('reload');
}

function searchData1(){
	var queryParams7 = $('#dg7').datagrid('options').queryParams;
	queryParams7.startDt = $("#startDt1").val();
	queryParams7.endDt = $("#endDt1").val();
	queryParams7.serverArea = $("#serverArea1").val();
	$('#dg7').datagrid('reload');
	
	var queryParams8 = $('#dg8').datagrid('options').queryParams;
	queryParams8.startDt = $("#startDt1").val();
	queryParams8.endDt = $("#endDt1").val();
	queryParams8.serverArea = $("#serverArea1").val();
	$('#dg8').datagrid('reload');
	
	var queryParams9 = $('#dg9').datagrid('options').queryParams;
	queryParams9.startDt = $("#startDt1").val();
	queryParams9.endDt = $("#endDt1").val();
	queryParams9.serverArea = $("#serverArea1").val();
	$('#dg9').datagrid('reload');	
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
					<table title="指挥官等级抵达等级平均时间（创角时间到升级时间差）" class="easyui-datagrid" style="min-height:200px" id="dg"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/commanderjsonlevel',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'level',width:40">指挥官等级</th>
								<th data-options="field:'avg',width:40">均值</th>
								<th data-options="field:'min',width:40">最小时间</th>
								<th data-options="field:'max',width:40">最大时间</th>
							</tr>
						</thead>
					</table>
					<table title="指挥官等级人数" class="easyui-datagrid" style="min-height:200px" id="dg1"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/commanderjsonlevelcount',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'level',width:40">指挥官等级</th>
								<th data-options="field:'count',width:40">人数</th>
							</tr>
						</thead>
					</table>
					<table title="指挥官统帅" class="easyui-datagrid" style="min-height:200px" id="dg2"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/commanderjsonleaderlevel',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'level',width:40">统帅等级</th>
								<th data-options="field:'count',width:40">人数</th>
								<th data-options="field:'avg',width:40">平均升级次数</th>
							</tr>
						</thead>
					</table>
					<table title="指挥官徽章达成平均时间" class="easyui-datagrid" style="min-height:200px" id="dg3"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/commanderjsonbadgetime',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'badge',width:40">徽章类型</th>
								<th data-options="field:'star1',width:40">1星</th>
								<th data-options="field:'star2',width:40">2星</th>
								<th data-options="field:'star3',width:40">3星</th>
							</tr>
						</thead>
					</table>
					<table title="指挥官徽章达成人数" class="easyui-datagrid" style="min-height:200px" id="dg4"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/commanderjsonbadgecount',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'badge',width:40">徽章类型</th>
								<th data-options="field:'star1',width:40">1星</th>
								<th data-options="field:'star2',width:40">2星</th>
								<th data-options="field:'star3',width:40">3星</th>
							</tr>
						</thead>
					</table>
					<table title="指挥官徽章设置 时间" class="easyui-datagrid" style="min-height:200px" id="dg5"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/commanderjsonbadgesettime',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'badge',width:40">徽章类型</th>
								<th data-options="field:'star1',width:40">1星</th>
								<th data-options="field:'star2',width:40">2星</th>
								<th data-options="field:'star3',width:40">3星</th>
							</tr>
						</thead>
					</table>
					<table title="指挥官徽章设置人数" class="easyui-datagrid" style="min-height:200px" id="dg6"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/commanderjsonbadgesetcount',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'badge',width:40">徽章类型</th>
								<th data-options="field:'star1',width:40">1星</th>
								<th data-options="field:'star2',width:40">2星</th>
								<th data-options="field:'star3',width:40">3星</th>
							</tr>
						</thead>
					</table>
					<div style="padding:2px 5px;background-color: #F5F5F5">
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
					<table title="每天平均体力消耗 账户" class="easyui-datagrid" style="min-height:200px" id="dg7"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/commanderjsonpowerconsume',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'power',width:40">平均每天体力消耗</th>
								<th data-options="field:'count',width:40">对应账户数量</th>
								<th data-options="field:'percent',width:40">百分比</th>
							</tr>
						</thead>
					</table>
					<table title="平均每天体力购买次数" class="easyui-datagrid" style="min-height:200px" id="dg8"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/commanderjsonpowerbuy',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'buy',width:40">购买次数</th>
								<th data-options="field:'count',width:40">购买账户数量</th>
								<th data-options="field:'percent',width:40">百分比</th>
							</tr>
						</thead>
					</table>
					<table title="体力消耗分类" class="easyui-datagrid" style="min-height:200px" id="dg9"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/commanderjsonpowertype',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'date',width:40">日期</th>
								<th data-options="field:'sum',width:40">体力总消耗</th>
								<th data-options="field:'warevent',width:40">副本</th>
								<th data-options="field:'collect',width:40">资源采集</th>
								<th data-options="field:'attack',width:40">进攻其他玩家基地/扎营/占领</th>
								<th data-options="field:'terrorist',width:40">恐怖分子</th>
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