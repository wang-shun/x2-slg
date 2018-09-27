<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>鲸鱼用户预警</title>
<%@include file="../head.jsp"%>
<script>
$(function(){
	$("#dg").datagrid({
		onLoadSuccess:function(data){
			$(".operate1").linkbutton({text:'邮件',plain:true,iconCls:'icon-search'});
			$(".operate2").linkbutton({text:'已关注 删除此记录',plain:true,iconCls:'icon-search'});
		}
	});
})


function operate1(val,row,index){
	return '<a href="javascript:;" class="operate1" onclick="">邮件</a>';
}
function operate2(val,row,index){
	return '<a href="javascript:;" class="operate2" onclick="">已关注 删除此记录</a>';
}
</script>
</head>
<body>
	<div class="gm-wrap">
		<%@include file="../top.jsp" %>
		<div class="gm-content">
			<%@include file="../left.jsp" %>
			<div class="gm-main">
				<form action="/rechargeuser/player/warning" method="POST" id="pageForm">
					<div class="easyui-panel" style="padding: 5px 5px;">
						<span style="display: block;font-size: 24px;font-weight:bold;">
							提醒设置
						</span>
					</div>
					<div class="easyui-panel" style="padding: 5px 5px;">
						<span style="display: block;">
							新账户单位时间<input class="easyui-numberspinner" style="width:100px"/>
							小时之内充值额度￥≥<input class="easyui-numberspinner" style="width:100px"/>
						</span>
						<span style="display: block;">
							<a href="javascript:;" class="easyui-linkbutton">保存</a>
							<a href="javascript:;" class="easyui-linkbutton">立即生效</a>
						</span>
					</div>
					<div class="easyui-panel" style="padding: 5px 5px;">
						<input class="easyui-textbox" name="packageDetail" style="width:500px;min-height:120px" labelPosition="top" data-options="label:'老账户累计充值额度达到以下额度提醒 ￥',multiline:true">
						<span style="display: block;">
							<a href="javascript:;" class="easyui-linkbutton">保存</a>
							<a href="javascript:;" class="easyui-linkbutton">立即生效</a>
						</span>
					</div>
					<div class="easyui-panel">
						<table class="easyui-datagrid" style="min-height:300px" id="dg"
								data-options="singleSelect:true,striped:true,url:'/rechargeuser/player/warningjson',fitColumns:true,emptyMsg:'暂无数据',pagination:true">
							<thead>
								<tr>
									<th data-options="field:'dateTime',width:80">预警时间</th>
									<th data-options="field:'roleId',width:80">账户</th>
									<th data-options="field:'roleName',width:80">名字</th>
									<th data-options="field:'serverArea',width:120">服务器</th>
									<th data-options="field:'content',width:120">提醒内容</th>
									<th data-options="field:'status',width:120">状态</th>
									<th data-options="field:'operate1',width:120,formatter:operate1">操作</th>
									<th data-options="field:'operate2',width:120,formatter:operate2">操作</th>
								</tr>
							</thead>
						</table>
					</div>
				</form>
			</div>
		</div>
		<%@include file="../foot.jsp" %>
	</div>
</body>
</html>