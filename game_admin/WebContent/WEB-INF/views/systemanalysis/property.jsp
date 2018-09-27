<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>道具</title>
<%@include file="../head.jsp"%>
<script>
$(function(){
	$("#dg").datagrid({
		onLoadSuccess:function(data){
			$(".operate1").linkbutton({text:'查看',plain:true,iconCls:'icon-more'});
		}
	});
})

function searchData(){
	var queryParams = $('#dg').datagrid('options').queryParams;
	queryParams.startDt = $("#startDt").val();
	queryParams.endDt = $("#endDt").val();
	queryParams.serverArea = $("#serverArea").val();
	$('#dg').datagrid('reload');
}

function operate1(val,row,index){
	return '<a class="operate1" href="javascript:;" onclick="">查看</a>';
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
					<table class="easyui-datagrid" style="min-height:200px" id="dg"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/propertyjson',fitColumns:true,emptyMsg:'暂无数据',pagination:true">
						<thead>
							<tr>
								<th data-options="field:'propertyName',width:40">道具名</th>
								<th data-options="field:'diamondBuy',width:40">钻石购买</th>
								<th data-options="field:'chargeGive',width:40">充值赠送</th>
								<th data-options="field:'gameProduce',width:40">游戏产出</th>
								<th data-options="field:'consumeNum',width:40">消耗数量</th>
								<th data-options="field:'operate1',width:40,fomatter:operate1">详情</th>
							</tr>
						</thead>
					</table>
				</div>
				<div class="easyui-dialog" style="width:600px;height:300px;" data-options="title:'详情',modal:true,closed:true" id="dd">
				</div>
			</div>
		</div>
		<%@include file="../foot.jsp" %>
	</div>
</body>
</html>