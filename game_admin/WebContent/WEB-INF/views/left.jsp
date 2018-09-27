<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<div class="gm-menu">
	<ul id="main_menu" class="easyui-tree">
		<c:forEach items="${_menulist}" var="authority">
			<sec:authorize access="hasRole('ADMIN') or hasRole('${authority.authorityCode }')">
				<li>
					<c:if test="${authority.authorityCode eq 'ROLE_INDEX'}"><span><a href="${authority.url}">${authority.authorityName }</a></span></c:if>
					<c:if test="${authority.authorityCode ne 'ROLE_INDEX'}"><span>${authority.authorityName }</span></c:if>
					<c:if test="${not empty authority.child }">
						<ul>
							<c:forEach items="${authority.child }" var="childAuthority">
								<li><span><a href="${childAuthority.url }">${childAuthority.authorityName }</a></span></li>
							</c:forEach>
						</ul>
					</c:if>
				</li>
			</sec:authorize>
		</c:forEach>
	</ul>
</div>