<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>资源</title>
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
					<div id="tb" style="padding:2px 5px;background-color: #F5F5F5">
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
					<table class="easyui-datagrid" title="资源产出" style="min-height:200px" id="dg"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/resourcesjsonproduce',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th rowspan="2" data-options="field:'date',width:60">日期</th>
								<th colspan="4" data-options="width:160">累计产出</th>
								<th colspan="4" data-options="width:160">野外采集产出</th>
								<th colspan="4" data-options="width:160">基地产出</th>
								<th colspan="4" data-options="width:160">道具使用</th>
								<th colspan="4" data-options="width:160">其他奖励</th>
							</tr>
							<tr>
								<th data-options="field:'produceSteel',width:40">钢材</th>
								<th data-options="field:'produceOil',width:40">石油</th>
								<th data-options="field:'produceRare',width:40">稀土</th>
								<th data-options="field:'produceMoney',width:40">黄金</th>
								<th data-options="field:'wildProduceSteel',width:40">钢材</th>
								<th data-options="field:'wildProduceOil',width:40">石油</th>
								<th data-options="field:'wildProduceRare',width:40">稀土</th>
								<th data-options="field:'wildProduceMoney',width:40">黄金</th>
								<th data-options="field:'buildProduceSteel',width:40">钢材</th>
								<th data-options="field:'buildProduceOil',width:40">石油</th>
								<th data-options="field:'buildProduceRare',width:40">稀土</th>
								<th data-options="field:'buildProduceMoney',width:40">黄金</th>
								<th data-options="field:'propertySteel',width:40">钢材</th>
								<th data-options="field:'propertyOil',width:40">石油</th>
								<th data-options="field:'propertyRare',width:40">稀土</th>
								<th data-options="field:'propertyMoney',width:40">黄金</th>
								<th data-options="field:'otherSteel',width:40">钢材</th>
								<th data-options="field:'otherOil',width:40">石油</th>
								<th data-options="field:'otherRare',width:40">稀土</th>
								<th data-options="field:'otherMoney',width:40">黄金</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" title="资源消耗" style="min-height:200px" id="dg1"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/resourcesjsonconsume',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th rowspan="2" data-options="field:'date',width:60">日期</th>
								<th colspan="4" data-options="width:160">累计消耗</th>
								<th colspan="4" data-options="width:160">基地建设</th>
								<th colspan="4" data-options="width:160">装甲生产</th>
								<th colspan="4" data-options="width:160">科技研究</th>
								<th colspan="4" data-options="width:160">战斗损失</th>
								<th colspan="4" data-options="width:160">其他消耗</th>
							</tr>
							<tr>
								<th data-options="field:'consumeSteel',width:40">钢材</th>
								<th data-options="field:'consumeOil',width:40">石油</th>
								<th data-options="field:'consumeRare',width:40">稀土</th>
								<th data-options="field:'consumeMoney',width:40">黄金</th>
								<th data-options="field:'buildConsumeSteel',width:40">钢材</th>
								<th data-options="field:'buildConsumeOil',width:40">石油</th>
								<th data-options="field:'buildConsumeRare',width:40">稀土</th>
								<th data-options="field:'buildConsumeMoney',width:40">黄金</th>
								<th data-options="field:'equipConsumeSteel',width:40">钢材</th>
								<th data-options="field:'equipConsumeOil',width:40">石油</th>
								<th data-options="field:'equipConsumeRare',width:40">稀土</th>
								<th data-options="field:'equipConsumeMoney',width:40">黄金</th>
								<th data-options="field:'scienceConsumeSteel',width:40">钢材</th>
								<th data-options="field:'scienceConsumeOil',width:40">石油</th>
								<th data-options="field:'scienceConsumeRare',width:40">稀土</th>
								<th data-options="field:'scienceConsumeMoney',width:40">黄金</th>
								<th data-options="field:'fightConsumeSteel',width:40">钢材</th>
								<th data-options="field:'fightConsumeOil',width:40">石油</th>
								<th data-options="field:'fightConsumeRare',width:40">稀土</th>
								<th data-options="field:'fightConsumeMoney',width:40">黄金</th>
								<th data-options="field:'otherConsumeSteel',width:40">钢材</th>
								<th data-options="field:'otherConsumeOil',width:40">石油</th>
								<th data-options="field:'otherConsumeRare',width:40">稀土</th>
								<th data-options="field:'otherConsumeMoney',width:40">黄金</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" title="资源流通" style="min-height:200px" id="dg2"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/resourcesjsoncirculate',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th rowspan="2" data-options="field:'date',width:60">日期</th>
								<th colspan="4" data-options="width:160">流通占比（流通资源/当天总产出）</th>
								<th colspan="4" data-options="width:160">野外掠夺(打基地+炸矿)</th>
								<th colspan="4" data-options="width:160">贸易</th>
								<th colspan="4" data-options="width:160">超时空战斗</th>
							</tr>
							<tr>
								<th data-options="field:'circulateSteel',width:40">钢材</th>
								<th data-options="field:'circulateOil',width:40">石油</th>
								<th data-options="field:'circulateRare',width:40">稀土</th>
								<th data-options="field:'circulateMoney',width:40">黄金</th>
								<th data-options="field:'wildCirculateSteel',width:40">钢材</th>
								<th data-options="field:'wildCirculateConsumeOil',width:40">石油</th>
								<th data-options="field:'wildCirculateConsumeRare',width:40">稀土</th>
								<th data-options="field:'wildCirculateConsumeMoney',width:40">黄金</th>
								<th data-options="field:'tradeCirculateSteel',width:40">钢材</th>
								<th data-options="field:'tradeCirculateOil',width:40">石油</th>
								<th data-options="field:'tradeCirculateRare',width:40">稀土</th>
								<th data-options="field:'tradeCirculateMoney',width:40">黄金</th>
								<th data-options="field:'crossCirculateSteel',width:40">钢材</th>
								<th data-options="field:'crossCirculateOil',width:40">石油</th>
								<th data-options="field:'crossCirculateRare',width:40">稀土</th>
								<th data-options="field:'crossCirculateMoney',width:40">黄金</th>
							</tr>
						</thead>
					</table>
					<table class="easyui-datagrid" title="每日平均采集出征次数" style="min-height:200px" id="dg3"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/resourcesjsoncollect',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'buildLevel',width:40">行政大楼等级</th>
								<th data-options="field:'queue2',width:40">2队列</th>
								<th data-options="field:'queue3',width:40">3队列</th>
								<th data-options="field:'queue4',width:40">4队列</th>
								<th data-options="field:'queue5',width:40">5队列</th>
								<th data-options="field:'queue6',width:40">6队列</th>
								<th data-options="field:'queue7',width:40">7队列</th>
								<th data-options="field:'queue8',width:40">8队列</th>
								<th data-options="field:'queue9',width:40">9队列</th>
								<th data-options="field:'queue10',width:40">10队列</th>
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