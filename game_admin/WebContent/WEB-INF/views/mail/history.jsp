<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>充值查询</title>
<%@include file="../head.jsp"%>
<script>
</script>
</head>
<body>
	<div class="gm-wrap">
		<%@include file="../top.jsp" %>
		<div class="gm-content">
			<%@include file="../left.jsp" %>
			<div class="gm-main">
				<table class="easyui-datagrid" style="height:800px" id="dg"
							data-options="singleSelect:true,striped:true,url:'/operation/mail/historyjson',fitColumns:true,pagination:true,toolbar:'#tb'">
					<thead>
						<tr>
							<th data-options="field:'receiver',width:80">收件人</th>
							<th data-options="field:'title',width:80">邮件主题</th>
							<th data-options="field:'content',width:80">邮件内容</th>
							<th data-options="field:'awardTitle',width:120">领奖中心标题</th>
							<th data-options="field:'translate',width:80">奖励翻译</th>
							<th data-options="field:'operator',width:140">操作者</th>
							<th data-options="field:'operateDate',width:140">操作事件</th>
						</tr>
					</thead>
				</table>
				<div id="tb" style="padding:2px 5px;">
					<select class="easyui-combobox" name="language" label="语言选择" labelWidth="60" style="width:220">
						<option>1</option>
						<option>2</option>
						<option>3</option>
						<option>4</option>
						<option>5</option>
					</select>
					<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="$('#pageForm').submit();">查询</a>
				</div>
			</div>
		</div>
		<%@include file="../foot.jsp" %>
	</div>
</body>
</html>