<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>转化率</title>
<%@include file="../head.jsp"%>
<script>
function searchData(){
	var queryParams = $('#dg').datagrid('options').queryParams;
	queryParams.startDt = $("#startDt").val();
	queryParams.endDt = $("#endDt").val();
	queryParams.channel = $("#channel").val();
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
				<table class="easyui-datagrid" style="height:800px" id="dg"
							data-options="singleSelect:true,striped:true,url:'/datasearch/datalist/conversionratejson',fitColumns:true,emptyMsg:'暂无数据',pagination:true,toolbar:'#tb'">
					<thead>
						<tr>
							<th data-options="field:'date',width:80">日期</th>
							<th data-options="field:'downloadNum',width:120">游戏包下载量</th>
							<th data-options="field:'installNum',width:80">游戏安装量</th>
							<th data-options="field:'loginNum',width:80">注册登录量</th>
							<th data-options="field:'installRate',width:80">安装转化率</th>
							<th data-options="field:'registerRate',width:80">注册转化率</th>
						</tr>
					</thead>
				</table>
				<div id="tb" style="padding:2px 5px;">
					<select class="easyui-combobox" name="channel" label="渠道选择" id="channel" labelWidth="60" style="width:140px;">
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
			</div>
		</div>
		<%@include file="../foot.jsp" %>
	</div>
</body>
</html>