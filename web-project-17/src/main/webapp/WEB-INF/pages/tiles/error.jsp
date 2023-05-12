<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/WEB-INF/pages/tiles/locale.jsp"%>
<link rel="stylesheet" href="styles/newsStyle.css">
<div class="error" >
<h1 align="center"> <c:out value="${error_name}" /></h1>
<br >
<div align="center">
<c:if test="${not (sessionScope.errorMessage eq null)}">
	<font color="red" size="+1"> <c:out value="${errorMessage}" />
	</font>
</c:if>
<br >
<c:if test="${not (sessionScope.errorValidationMessage eq null)}">
	<font color="red" size="+1"> <c:out value="${errorValidationMessage}" />
	</font>
</c:if>
</div>
<br>
<br >
<div align="center">
<form action="controller" method="get">
	<input type="hidden" name="command" value="go_to_base_page" /> <input
		type="submit" value=<c:out value="${error_back}" /> />
</form>
</div>

</div>