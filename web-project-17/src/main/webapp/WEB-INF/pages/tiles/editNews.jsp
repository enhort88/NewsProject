<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/WEB-INF/pages/tiles/locale.jsp"%>

<div class="body-title">
	<a href="controller?command=go_to_news_list"><c:out value="${newslist_news}" />&rarr; </a> <c:out value="${edit}" />
</div>

<div class="add-table-margin">
	<form action="controller" method="post">
		<table class="news_text_format">
			<tr>
				<td class="space_around_title_text"><c:out value="${title}" /></td>
				<td class="space_around_view_text"><div class="word-breaker">
						<input type="text" name="news_title"
							value="<c:out value="${requestScope.news.title }"/>" />
					</div></td>
			</tr>
			<tr style="display:none">
				<td class="space_around_title_text"><c:out value="${date}" /></td>
				<td class="space_around_view_text"><div class="word-breaker">
						<input type="text" name="news_date"
							value="01-01-2023"<c:out value="${requestScope.news.newsDate }"/>" />
					</div></td>
			</tr>
			<tr>
				<td class="space_around_title_text"><c:out value="${brief}" /></td>
				<td class="space_around_view_text"><div class="word-breaker">
						<input type="text" name="news_brief"
							value="<c:out value="${requestScope.news.briefNews }"/>" />
					</div></td>
			</tr>
			<tr>
				<td class="space_around_title_text"><c:out value="${content}" /></td>
				<td class="space_around_view_text"><div class="word-breaker">
						<textarea name="news_content" rows="10" cols="50"><c:out
								value="${requestScope.news.content }" /></textarea>
					</div></td>
			</tr>

		</table>
</div>


<c:if test="${sessionScope.role eq 'admin'}">
	<div>
		<form>
			<input type="hidden" name="command" value="do_edit_news" /> <input
				type="hidden" name="idNews" value="${news.idNews}" /> <input
				type="submit" value=<c:out value="${edit_save}" /> />
		</form>
	</div>

	<div align="right">
		<form action="controller">
			<input type="hidden" name="command" value="go_to_news_list" /> <input
				type="hidden" name="idNews" value="${news.idNews}" /> <input
				type="submit" value=<c:out value="${view_cancel}" /> />
		</form>

	</div>
</c:if>