<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>装备材料</title>
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
					<table title="植入体材料（装备材料）生产队列" class="easyui-datagrid" style="min-height:200px" id="dg"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/equipmaterialjson',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'queue',width:40">生产队列</th>
								<th data-options="field:'playerNum',width:40">人数</th>
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
					<table title="植入体材料（装备材料）每天累计生产时间" class="easyui-datagrid" style="min-height:200px" id="dg1"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/equipmaterialjsontime',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'date',width:40">日期</th>
								<th data-options="field:'clock0',width:40">0_1h</th>
								<th data-options="field:'clock1',width:40">1.01_2h</th>
								<th data-options="field:'clock2',width:40">2.01_3h</th>
								<th data-options="field:'clock3',width:40">3.01_4h</th>
								<th data-options="field:'clock4',width:40">4.01_5h</th>
								<th data-options="field:'clock5',width:40">5.01_6h</th>
								<th data-options="field:'clock6',width:40">6.01_7h</th>
								<th data-options="field:'clock7',width:40">7.01_8h</th>
								<th data-options="field:'clock8',width:40">8.01_9h</th>
								<th data-options="field:'clock9',width:40">9.01_10h</th>
								<th data-options="field:'clock10',width:40">10.01_11h</th>
								<th data-options="field:'clock11',width:40">11.01_12h</th>
								<th data-options="field:'clock12',width:40">12.01_13h</th>
								<th data-options="field:'clock13',width:40">13.01_14h</th>
								<th data-options="field:'clock14',width:40">14.01_15h</th>
								<th data-options="field:'clock15',width:40">15.01_16h</th>
								<th data-options="field:'clock16',width:40">16.01_17h</th>
								<th data-options="field:'clock17',width:40">17.01_18h</th>
								<th data-options="field:'clock18',width:40">18.01_19h</th>
								<th data-options="field:'clock19',width:40">19.01_20h</th>
								<th data-options="field:'clock20',width:40">20.01_21h</th>
								<th data-options="field:'clock21',width:40">21.01_22h</th>
								<th data-options="field:'clock22',width:40">22.01_23h</th>
								<th data-options="field:'clock23',width:40">23.01_24h</th>
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