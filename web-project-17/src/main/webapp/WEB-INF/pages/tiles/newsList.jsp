<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/WEB-INF/pages/tiles/locale.jsp"%>


<div class="body-title">
	<a href="controller?command=go_to_news_list"> <c:out value="${newslist_news}" />&rarr; </a> <c:out value="${newslist_newslist}" />
</div>

<form action="controller" method="post">
	<c:forEach var="news" items="${requestScope.news}">
		<div class="single-news-wrapper">
			<div class="single-news-header-wrapper">
			
				<div align="left" class="news-title">
					<c:out value="${news.title}" />
				</div>
				
				<div align="left" class="news-brief">
					<c:out value="${news.briefNews}" />
				</div>
				
				<div align="center" class="news-content">
					<c:out value="${news.content}" />
				</div>
				
				<div align="right" class="news-date">
					<c:out value="${news.newsDate}" />
				</div>
				
				<div class="news-link-to-wrapper">
					<div class="link-position">
						<c:if test="${sessionScope.role eq 'admin'}">
						      <a href="controller?command=go_to_edit_news&id=${news.idNews}"> <c:out value="${newslist_edit}" /> </a> 
						</c:if>
						
						<a href="controller?command=go_to_view_news&id=${news.idNews}"> <c:out value="${newslist_view}" /> </a> 
   					    
   					    <c:if test="${sessionScope.role eq 'admin'}">
   					         <input type="checkbox" name="idNews" value="${news.idNews }" />
   					    </c:if>
					</div>
				</div>

			</div>
		</div>

	</c:forEach>
	
	<div style="position: absolute; right: 10px; margin-top: 20px">
		<c:if test="${sessionScope.role eq 'admin'}">
   			<input type="hidden" name="command" value="do_delete_news" />
            <input type="submit" value=<c:out value="${newslist_del}" /> />
   		</c:if>
	</div>

	<!-- 	<logic:notEmpty name="newsForm" property="newsList">
		<div class="delete-button-position">
			<html:submit>
				<bean:message key="locale.newslink.deletebutton" />
			</html:submit>
		</div>
	</logic:notEmpty> -->

	<div class="no-news">
		<c:if test="${requestScope.news eq null}">
        <c:out value="${newslist_nonews}" /> </a>
	</c:if>
	</div>
</form>

<form name="page-number" action="controller" method="get" >
	<input type="hidden" name="command" value="go_to_news_list" />
	<input type="hidden" name="pageNumber" value="${requestScope.pageNumber}">
	<input type="hidden" name="newsCount" value="${requestScope.newsCount}">
	<input type="hidden" name="newsCountAll" value="${sessionScope.newsCountAll}">
	
	<div class="news-number">
	<c:out value="${newslist_pick}" /> 
	<a href="controller?command=go_to_news_list&newsCount=5"> <b><font size="3">5</font></b></a> /
	<a href= "controller?command=go_to_news_list&newsCount=10"> <b><font size="3">10</font></b></a> / 
	<a href= "controller?command=go_to_news_list&newsCountAll=1"> <b><font size="5" >&#8734; </font></b></a>  </a>
	<%-- <c:out value="${newslist_all}" />--%>
	</div>
</form>

<div class="newslist-pagination">
	<c:if test="${not (sessionScope.newsCountAll eq 1) }">
	<c:if test="${not (requestScope.pageNumber eq 1) }">
		<a href="controller?command=go_to_news_list&newsCount=${requestScope.newsCount}&pageNumber=${requestScope.pageNumber-1}"> <b><font size="3" color="0a9fd9">&#9668;</font></b> </a> &nbsp;
	</c:if>

	<c:if test="${not (requestScope.pageNumber eq 1) }" >
		&nbsp;<a href="controller?command=go_to_news_list&newsCount=${requestScope.newsCount}&pageNumber=1" ><b><font size="3" >1</font></b></a>
	</c:if>
	
	<c:if test="${not ((requestScope.pageNumber eq 1) ||  (requestScope.pageNumber eq 2)) }" >
		<a href="controller?command=go_to_news_list&newsCount=${requestScope.newsCount}&pageNumber=${requestScope.pageNumber-1}" >${requestScope.pageNumber-1}</a>
	</c:if>
	
	 <a > <b><font size="5" color="0a9fd9">${requestScope.pageNumberView}</font></b>  </a> 
	 
	 <c:if test="${not ((requestScope.pageNumber eq requestScope.totalAmmountPages) ||  (requestScope.pageNumber eq requestScope.totalAmmountPages-1 )) }" >
		<a href="controller?command=go_to_news_list&newsCount=${requestScope.newsCount}&pageNumber=${requestScope.pageNumber+1}" >${requestScope.pageNumber+1}</a>
	</c:if>
	
	<c:if test="${not (requestScope.pageNumber eq requestScope.totalAmmountPages)}">
		&nbsp;<a href="controller?command=go_to_news_list&newsCount=${requestScope.newsCount}&pageNumber=${requestScope.totalAmmountPages}"><b><font size="3" >${requestScope.totalAmmountPages}</font></b></a>
	</c:if>


	<c:if test="${not (requestScope.pageNumber eq requestScope.totalAmmountPages) }">
		&nbsp; <a href="controller?command=go_to_news_list&newsCount=${requestScope.newsCount}&pageNumber=${requestScope.pageNumber+1}"> <b><font size="3" color="0a9fd9">&#9658;</font></b> </a>
	</c:if>
	</c:if>
</div>
