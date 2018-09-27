<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>兑换码记录</title>
<%@include file="../head.jsp"%>
<link rel="stylesheet" href="/static/css/authority.css" type="text/css"></link>
<script>
$(function(){
	$("#dg").datagrid({
		onLoadSuccess:function(data){
			$(".operate1").linkbutton({text:'全部作废',plain:true,iconCls:'icon-no'});
		}
	});
})

function searchData(){
	var queryParams = $('#dg').datagrid('options').queryParams;
	queryParams.startDt = $("#startDt").val();
	queryParams.endDt = $("#endDt").val();
	queryParams.packageName = $("#packageName").val();
	queryParams.channel = $("#channel").val();
	$('#dg').datagrid('reload');
}

function updateStatus(id){
	$.ajax({
		type:"POST",
		url:"/operation/redeemcode/updatestatus",
		data:{"id":id},
		success:function(result){
			if(result.success){
				$.messager.alert("通知",result.message);	
			}
		}
	});
}

function operate1(val,row,index){
	if(row.unusedNum > 0){
		return '<span>'+row.unusedNum+'</span><a href="javascript:;" class="operate1" onclick="updateStatus(\''+row.id+'\');">全部作废</a>';
	}else{
		return '<span>'+row.unusedNum+'</span>';
	}
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
						data-options="singleSelect:true,striped:true,url:'/operation/redeemcode/json',fitColumns:true,pagination:true,toolbar:'#tb'">
					<thead>
						<tr>
							<th data-options="field:'packageName',width:80">礼包备注</th>
							<th data-options="field:'packageDetail',width:160">礼包内容</th>
							<th data-options="field:'num',width:60">生成数量</th>
							<th data-options="field:'unusedNum',width:120,formatter:operate1">未使用数量</th>
							<th data-options="field:'channel',width:80">渠道</th>
							<th data-options="field:'endDate',width:120">有效期</th>
							<th data-options="field:'creator',width:80">操作账号</th>
							<th data-options="field:'createDate',width:120">生成时间</th>
						</tr>
					</thead>
				</table>
				<div id="tb" style="padding:2px 5px;">
					时间 从: <input class="Wdate" name="startDt" id="startDt" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
					到: <input class="Wdate" name="endDt" id="endDt" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px"/>
					礼包备注:<input class="easyui-textbox" data-options="prompt:'礼包备注:'" style="width:110px" name="packageName" id="packageName"/>
					渠道:<input class="easyui-textbox" data-options="prompt:'渠道:'" style="width:110px" name="channel" id="channel"/>
					<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="searchData();">查询</a>
				</div>
			</div>
		</div>
		<%@include file="../foot.jsp" %>
	</div>
</body>
</html>