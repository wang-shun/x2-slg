<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>活跃度</title>
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
					<table title="完成人数（可完成次数=max）" class="easyui-datagrid" style="min-height:200px" id="dg"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/livenessjsonreach',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'date',width:40">日期</th>
								<th data-options="field:'liveness1',width:40">收获资源</th>
								<th data-options="field:'liveness2',width:40">生产轮式战车</th>
								<th data-options="field:'liveness3',width:40">生产旋翼战机</th>
								<th data-options="field:'liveness4',width:40">生产履带战车</th>
								<th data-options="field:'liveness5',width:40">装备生产</th>
								<th data-options="field:'liveness6',width:40">装备合成</th>
								<th data-options="field:'liveness7',width:40">材料生产</th>
								<th data-options="field:'liveness8',width:40">材料合成</th>
								<th data-options="field:'liveness9',width:40">科研升级</th>
								<th data-options="field:'liveness10',width:40">建筑升级</th>
								<th data-options="field:'liveness11',width:40">使用资源道具</th>
								<th data-options="field:'liveness12',width:40">在线活跃</th>
								<th data-options="field:'liveness13',width:40">活跃大师</th>
								<th data-options="field:'liveness14',width:40">军团捐献</th>
								<th data-options="field:'liveness15',width:40">科研捐献</th>
								<th data-options="field:'liveness16',width:40">刷新商店</th>
								<th data-options="field:'liveness17',width:40">采集钢材</th>
								<th data-options="field:'liveness18',width:40">采集石油</th>
								<th data-options="field:'liveness19',width:40">采集稀土</th>
								<th data-options="field:'liveness20',width:40">采集黄金</th>
								<th data-options="field:'liveness21',width:40">正义之战</th>
								<th data-options="field:'liveness22',width:40">个人事件</th>
								<th data-options="field:'liveness23',width:40">日常任务</th>
								<th data-options="field:'liveness24',width:40">军团任务</th>
								<th data-options="field:'liveness25',width:40">军团援建</th>
								<th data-options="field:'liveness26',width:40">占领领土</th>
							</tr>
						</thead>
					</table>
					<table title="奖励领取人数" class="easyui-datagrid" style="min-height:200px" id="dg1"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/livenessjsonaward',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'date',width:40">日期</th>
								<th data-options="field:'award1',width:40">积分奖励1</th>
								<th data-options="field:'award2',width:40">积分奖励2</th>
								<th data-options="field:'award3',width:40">积分奖励3</th>
								<th data-options="field:'award4',width:40">积分奖励4</th>
								<th data-options="field:'award5',width:40">积分奖励5</th>
								<th data-options="field:'award6',width:40">积分奖励6</th>
								<th data-options="field:'award7',width:40">积分奖励7</th>
								<th data-options="field:'award8',width:40">积分奖励8</th>
								<th data-options="field:'award9',width:40">积分奖励9</th>
								<th data-options="field:'award10',width:40">积分奖励10</th>
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