<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>充值查询</title>
<%@include file="../head.jsp"%>
<script>
function searchData(){
	var queryParams1 = $('#dg1').datagrid('options').queryParams;
	queryParams1.startDt = $("#startDt").val();
	queryParams1.endDt = $("#endDt").val();
	$('#dg1').datagrid('reload');
	
	var queryParams2 = $('#dg2').datagrid('options').queryParams;
	queryParams2.startDt = $("#startDt").val();
	queryParams2.endDt = $("#endDt").val();
	$('#dg2').datagrid('reload');
	
	var queryParams3 = $('#dg3').datagrid('options').queryParams;
	queryParams3.startDt = $("#startDt").val();
	queryParams3.endDt = $("#endDt").val();
	$('#dg3').datagrid('reload');
	
	var queryParams4 = $('#dg4').datagrid('options').queryParams;
	queryParams4.startDt = $("#startDt").val();
	queryParams4.endDt = $("#endDt").val();
	$('#dg4').datagrid('reload');
	
	var queryParams5 = $('#dg5').datagrid('options').queryParams;
	queryParams5.startDt = $("#startDt").val();
	queryParams5.endDt = $("#endDt").val();
	$('#dg5').datagrid('reload');
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
					<div class="easyui-panel">
						时间 从: <input class="Wdate" name="startDt" id="startDt" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
						到: <input class="Wdate" name="endDt" id="endDt" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="searchData();">查询</a>
					</div>
					<div title="在线趋势" class="easyui-panel">
						
					</div>
					<div id="tb1" class="easyui-panel">
						<a href="javascript:;" class="easyui-linkbutton" onclick="">新增设备和账户</a>
						<a href="javascript:;" class="easyui-linkbutton" onclick="">活跃玩家</a>
						<a href="javascript:;" class="easyui-linkbutton" onclick="">付费玩家</a>
						<a href="javascript:;" class="easyui-linkbutton" onclick="">收入</a>
					</div>
					<table title="关键指标" class="easyui-datagrid" style="min-height:300px" id="dg1"
							data-options="singleSelect:true,striped:true,url:'/datasearch/datalist/summaryjsonkeytarget',fitColumns:true,emptyMsg:'暂无数据',pagination:true,toolbar:'#tb1'">
						<thead>
							<tr>
								<th data-options="field:'date',width:80">日期</th>
								<th data-options="field:'equip',width:120">设备激活（台）</th>
								<th data-options="field:'player',width:80">新增玩家（账户）</th>
							</tr>
						</thead>
					</table>
					<div id="tb2" class="easyui-panel">
						<a href="javascript:;" class="easyui-linkbutton" onclick="">日付费率</a>
						<a href="javascript:;" class="easyui-linkbutton" onclick="">日ARPU</a>
						<a href="javascript:;" class="easyui-linkbutton" onclick="">日ARPPU</a>
					</div>
					<table title="付费率&ARPU&ARPPU" class="easyui-datagrid" style="min-height:300px" id="dg2"
							data-options="singleSelect:true,striped:true,url:'/datasearch/datalist/summaryjsonconsumerate',fitColumns:true,emptyMsg:'暂无数据',pagination:true,toolbar:'#tb2'">
						<thead>
							<tr>
								<th data-options="field:'date',width:80">日期</th>
								<th data-options="field:'percent',width:120">日付费率</th>
							</tr>
						</thead>
					</table>
					<div id="tb3" class="easyui-panel">
						<a href="javascript:;" class="easyui-linkbutton" onclick="">新增存留用户</a>
						<a href="javascript:;" class="easyui-linkbutton" onclick="">新增存留设备</a>
					</div>
					<table title="存留数据" class="easyui-datagrid" style="min-height:300px" id="dg3"
							data-options="singleSelect:true,striped:true,url:'/datasearch/datalist/summaryjsonretention',fitColumns:true,emptyMsg:'暂无数据',pagination:true,toolbar:'#tb3'">
						<thead>
							<tr>
								<th data-options="field:'date',width:80">日期</th>
								<th data-options="field:'retention1',width:120">次日存留</th>
								<th data-options="field:'retention3',width:120">3日存留</th>
								<th data-options="field:'retention7',width:120">7日存留</th>
								<th data-options="field:'retention15',width:120">15日存留</th>
								<th data-options="field:'retention30',width:120">30日存留</th>
								<th data-options="field:'retention45',width:120">45日存留</th>
								<th data-options="field:'retention60',width:120">60日存留</th>
								<th data-options="field:'retention90',width:120">90日存留</th>
							</tr>
						</thead>
					</table>
					<div id="tb4" class="easyui-panel">
						
					</div>
					<table title="游戏时长&次数" class="easyui-datagrid" style="min-height:300px" id="dg4"
							data-options="singleSelect:true,striped:true,url:'/datasearch/datalist/summaryjsongametime',fitColumns:true,emptyMsg:'暂无数据',pagination:true,toolbar:'#tb4'">
						<thead>
							<tr>
								<th data-options="field:'date',width:80">日期</th>
								<th data-options="field:'avgTime',width:120">平均游戏时长（分钟）</th>
								<th data-options="field:'avgStart',width:120">平均启动次数</th>
							</tr>
						</thead>
					</table>
					<div id="tb5" class="easyui-panel">
						
					</div>
					<table title="新用户价值" class="easyui-datagrid" style="min-height:300px" id="dg5"
							data-options="singleSelect:true,striped:true,url:'/datasearch/datalist/summaryjsonnewvalue',fitColumns:true,emptyMsg:'暂无数据',pagination:true,toolbar:'#tb5'">
						<thead>
							<tr>
								<th data-options="field:'date',width:80">日期</th>
								<th data-options="field:'retention3',width:120">3日价值</th>
								<th data-options="field:'retention7',width:120">7日价值</th>
								<th data-options="field:'retention15',width:120">15日价值</th>
								<th data-options="field:'retention30',width:120">30日价值</th>
								<th data-options="field:'retention45',width:120">45日价值</th>
								<th data-options="field:'retention60',width:120">60日价值</th>
								<th data-options="field:'retention75',width:120">75日价值</th>
								<th data-options="field:'retention90',width:120">90日价值</th>
								<th data-options="field:'retention105',width:120">105日价值</th>
								<th data-options="field:'retention120',width:120">120日价值</th>
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