<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/WEB-INF/pages/tiles/locale.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>

<div class="wrapper">
	<div class="newstitle">
		<c:out value="${header_name}" />
	</div>

	<div class="local-link">
		<div align="right">
			<form action="controller" method="post"
				style="display: inline-block;">
				<input type="hidden" name="command" value="do_locale"> <input
					type="hidden" name="local" value="en"> <input type="submit"
					value="${en_button}">
			</form>
			<form action="controller" method="post"
				style="display: inline-block;">
				<input type="hidden" name="command" value="do_locale"> <input
					type="hidden" name="local" value="ru"> <input type="submit"
					value="${ru_button}">
			</form>


		</div>
		<div> </div>
		<c:if test="${not (sessionScope.user eq 'active')}">
			<div align="right">
				<form action="controller" method="post">
					<input type="hidden" name="command" value="do_sign_in" />
					<c:out value="${header_login}" />
					: <input type="text" name="login" value="enhort88" /><br />
					<c:out value="${header_password}" />
					: <input type="password" name="password" value="Fqrs95f45!" /><br />

						
						<c:if test="${not ((sessionScope.AuthenticationMessage eq null))}">
							<font color="red">
							<c:set var="AuthenticationMessage" value="${sessionScope.AuthenticationMessage}" />
							<fmt:message bundle="${loc}" key="${AuthenticationMessage}"	var="AuthenticationMessage" />
							<c:out value="${AuthenticationMessage}" />
						</font>
						</c:if>
						
					<a href="controller?command=go_to_registration_page"
						style="display: inline-block;"> <c:out
							value="${header_registration}" />
					</a> <input type="submit" value="${header_signin}" /><br />
				</form>
			</div>

		</c:if>

		<c:if test="${sessionScope.user eq 'active'}">
			<div align="right">
				<form action="controller" method="post">
					<input type="hidden" name="command" value="do_sign_out" /> <input
						type="submit" value="${header_signout}" /><br />
				</form>
			</div>
		</c:if>
	</div>
</div>