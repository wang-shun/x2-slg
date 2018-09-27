<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>配件</title>
<%@include file="../head.jsp"%>
<script>
function searchData(){
	var queryParams = $('#dg').datagrid('options').queryParams;
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
						<span>配件解锁人数</span>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="">人数</a>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="">百分比</a>
					</div>
					<table class="easyui-datagrid" style="min-height:200px" id="dg"
						data-options="singleSelect:true,striped:true,url:'/systemanalysis/analysis/partsjson',fitColumns:true,emptyMsg:'暂无数据',toolbar:'#tb'">
						<thead>
							<tr>
								<th data-options="field:'level',width:40">配件类型</th>
								<th data-options="field:'count0',width:40">0-5个</th>
								<th data-options="field:'count6',width:40">6-10个</th>
								<th data-options="field:'count11',width:40">11-15个</th>
								<th data-options="field:'count16',width:40">16-20个</th>
								<th data-options="field:'count21',width:40">21-25个</th>
								<th data-options="field:'count26',width:40">26-30个</th>
								<th data-options="field:'count31',width:40">31-35个</th>
								<th data-options="field:'count36',width:40">36-40个</th>
								<th data-options="field:'count41',width:40">41-45个</th>
								<th data-options="field:'count46',width:40">46-50个</th>
								<th data-options="field:'count51',width:40">51-55个</th>
								<th data-options="field:'count56',width:40">56-60个</th>
								<th data-options="field:'count61',width:40">61-65个</th>
								<th data-options="field:'count66',width:40">66-70个</th>
								<th data-options="field:'count71',width:40">71-75个</th>
								<th data-options="field:'count76',width:40">76-80个</th>
								<th data-options="field:'count81',width:40">81-85个</th>
								<th data-options="field:'count86',width:40">86-90个</th>
								<th data-options="field:'count91',width:40">91-95个</th>
								<th data-options="field:'count96',width:40">96-100个</th>
								<th data-options="field:'count101',width:40">101-105个</th>
								<th data-options="field:'count106',width:40">106-110个</th>
								<th data-options="field:'count111',width:40">111-115个</th>
								<th data-options="field:'count116',width:40">116-120个</th>
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