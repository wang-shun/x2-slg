<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel">
	<div class="easyui-pagination" data-options="total:${pagination.total}, pageSize: ${pagination.pageSize},pageNumber: ${pagination.pageNumber}"></div>
	<input type="hidden" name="pageNumber" id="pageNumber" value="${pagination.pageNumber}"/> 
	<input type="hidden" name="pageSize" id="pageSize" value="${pagination.pageSize}"/>
	<input type="hidden" name="total" id="total" value="${pagination.total}"/>
	<script>
		$(function() {
			$('.easyui-pagination').pagination({
				onSelectPage : function(pageNumber, pageSize) {
					$("#pageNumber").val(pageNumber);
					$("#pageSize").val(pageSize);
				    $("#pageForm").submit();
				}
			});
		});
	</script>
</div>
