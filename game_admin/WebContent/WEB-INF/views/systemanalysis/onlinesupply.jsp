<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>在线补给</title>
<%@include file="../head.jsp"%>
<script>
function searchData(){
	var queryParams = $('#dg').datagrid('options').queryParams;
	queryParams.startDt = $("#startDt").val();
	queryParams.endDt = $("#endDt").val();
	queryParams.serverArea = $("#serverArea").val();
	$('#dg').datagrid('reload');
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
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/onlinesupplyjson',fitColumns:true,emptyMsg:'暂无数据'">
						<thead>
							<tr>
								<th data-options="field:'date',width:80">日期</th>
								<th data-options="field:'stage1count',width:80">第1段奖励领取人数</th>
								<th data-options="field:'stage2count',width:80">第2段奖励领取人数</th>
								<th data-options="field:'stage3count',width:80">第3段奖励领取人数</th>
								<th data-options="field:'stage4count',width:80">第4段奖励领取人数</th>
								<th data-options="field:'stage5count',width:80">第5段奖励领取人数</th>
								<th data-options="field:'stage6count',width:80">第6段奖励领取人数</th>
								<th data-options="field:'stage7count',width:80">第7段奖励领取人数</th>
								<th data-options="field:'stage8count',width:80">第8段奖励领取人数</th>
								<th data-options="field:'stage9count',width:80">第9段奖励领取人数</th>
								<th data-options="field:'stage10count',width:80">第10段奖励领取人数</th>
								<th data-options="field:'stage11count',width:80">第11段奖励领取人数</th>
								<th data-options="field:'stage12count',width:80">第12段奖励领取人数</th>
								<th data-options="field:'stage13count',width:80">第13段奖励领取人数</th>
								<th data-options="field:'stage14count',width:80">第14段奖励领取人数</th>
								<th data-options="field:'stage15count',width:80">第15段奖励领取人数</th>
								<th data-options="field:'stage16count',width:80">第16段奖励领取人数</th>
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