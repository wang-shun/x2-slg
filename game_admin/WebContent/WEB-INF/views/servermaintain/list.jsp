<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html>
<head>
<title>充值查询</title>
<%@include file="../head.jsp"%>
<STYLE type="text/css">
span{
	display: block;
}
</STYLE>
<script>
</script>
</head>
<body>
	<div class="gm-wrap">
		<%@include file="../top.jsp" %>
		<div class="gm-content">
			<%@include file="../left.jsp" %>
			<div class="gm-main">
				<div class="easyui-panel" style="width:100%;min-height:80px;padding: 5px 5px;">
					<span>
						<input class="easyui-textbox" name="ipWhiteList" style="width:50%;" labelPosition="top" data-options="label:'新用户默认导入(格式：服务器ID;服务器ID)'"/>
					</span>
					<span>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-save">保存</a>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok">立即生效</a>
					</span>
				</div>
				<div class="easyui-panel" style="width:100%;height:min-200px;padding: 5px 5px;">
					<div class="easyui-panel" title="维护提示">
						<div style="float: left;"><input class="easyui-textbox" name="zh_CN" style="width:300;height:80px;" labelPosition="top" data-options="label:'简体中文',multiline:true"></div>
						<div style="float: left;"><input class="easyui-textbox" name="zh_TW" style="width:300;height:80px;" labelPosition="top" data-options="label:'繁体中文',multiline:true"></div>
						<div style="float: left;"><input class="easyui-textbox" name="en" style="width:300;height:80px;" labelPosition="top" data-options="label:'English',multiline:true"></div>
						<div style="float: left;"><input class="easyui-textbox" name="ja" style="width:300;height:80px;" labelPosition="top" data-options="label:'日本语',multiline:true"></div>
						<div style="float: left;"><input class="easyui-textbox" name="de" style="width:300;height:80px;" labelPosition="top" data-options="label:'德语',multiline:true"></div>
						<div style="float: left;"><input class="easyui-textbox" name="ru" style="width:300;height:80px;" labelPosition="top" data-options="label:'俄语',multiline:true"></div>
						<div style="float: left;"><input class="easyui-textbox" name="vi" style="width:300;height:80px;" labelPosition="top" data-options="label:'越南语',multiline:true"></div>
						<div style="float: left;"><input class="easyui-textbox" name="th" style="width:300;height:80px;" labelPosition="top" data-options="label:'泰语',multiline:true"></div>
					</div>
					<span>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-save">保存</a>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok">立即生效</a>
					</span>
				</div>
				<div class="easyui-panel" style="width:100%;min-height:100px;padding: 5px 5px;">
					<input class="easyui-textbox" name="ipWhiteList" style="width:50%;height:80px" labelPosition="top" data-options="label:'IP白名单(格式：IP地址;IP地址)',multiline:true">
					<span>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-save">保存</a>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok">立即生效</a>
					</span>
				</div>
				<div class="easyui-panel" style="width:100%;min-height:60px;padding: 5px 5px;">
					<span>本地时间：2017/03/05 24:00:00 UTC：2017/03/05 24:00:00</span>
					<span>在线服务器：50    离线服务器：15  启动中服务器：21   待命即开新服剩余：2</span>
					<span>
						<a href="javascript:;" class="easyui-linkbutton">停止全部服务器</a>
						<a href="javascript:;" class="easyui-linkbutton">启动全部服务器</a>
						<a href="javascript:;" class="easyui-linkbutton">打开全部对外网关</a>
					</span>
				</div>
				<table class="easyui-datagrid" style="min-height:300px" id="dg" data-options="singleSelect:true,striped:true,url:'/operation/servermaintain/listjson',fitColumns:true,emptyMsg:'暂无数据'">
					<thead>
						<tr>
							<th data-options="field:'serverArea',width:20">服务器ID</th>
							<th data-options="field:'serverBig',width:20">所属大区</th>
							<th data-options="field:'ip',width:40">服务器IP</th>
							<th data-options="field:'serverStatus',width:60">服务器状态</th>
							<th data-options="field:'playerNum',width:60">累计新用户导入</th>
							<th data-options="field:'operate1',width:60">操作</th>
							<th data-options="field:'gateStatus',width:60">对外网关状态</th>
							<th data-options="field:'operate2',width:60">对外网关操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
		<%@include file="../foot.jsp" %>
	</div>
</body>
</html>