<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>首页</title>
<%@include file="../head.jsp"%>
<!-- <script type="text/javascript">
$(function(){
	$("#combobox").combobox({
		onSelect:function(item){
			$.messager.alert("text",item.text);
		}
	});
});
</script> -->
<style type="text/css">
.panel{
	position:absolute;
	left:50%;
	top:50%;
	margin:-104px 0 0 -200px;
}
</style>
</head>
<body>
	<div class="gm-wrap">
		<%@include file="../top.jsp" %>
		<div class="gm-content">
			<%@include file="../left.jsp" %>
			<div class="gm-main">
				<form action="/index" method="POST" id="pageForm">
				<%-- <div class="easyui-tabs">
					<div title="tab1">
						<div>
							<!-- 支持自定义日期格式、多语言、日期范围限制、自定义事件 -->
							<input class="Wdate" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
						</div>
						<div>
							<select class="easyui-combobox" style="width:200px;" id="combobox">
								<option>1</option>
								<option>2</option>
								<option>3</option>
								<option>4</option>
								<option>5</option>
							</select>
						</div>
						<%@include file="../page.jsp" %>
					</div>
					<div title="tab2">
						<div>
							123123123122312313
						</div>
						<div>
							123123123122312313
						</div>
						<div>
							123123123122312313
						</div>
						<div>
							123123123122312313
						</div>
						<div>
							123123123122312313
						</div>
						<div>
							123123123122312313
						</div>
						<div>
							123123123122312313
						</div>
						<div>
							123123123122312313
						</div>
						<div>
							123123123122312313
						</div>
						<div>
							123123123122312313
						</div>
						<div>
							123123123122312313
						</div>
						<div>
							123123123122312313
						</div>
					</div>
				</div> --%>
					<div class="easyui-panel" style="max-width:400px;padding:30px 60px;margin:0 auto;">
						共和国之辉欢迎你
					</div>
				</form>
			</div>
		</div>
		<%@include file="../foot.jsp" %>
	</div>
</body>
</html>