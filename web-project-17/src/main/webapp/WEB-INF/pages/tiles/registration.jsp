<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/WEB-INF/pages/tiles/locale.jsp"%>

<div class="wrapper">
	<div class="body-title">
		<a href="controller?command=go_to_registration_page">Registration
		</a>
	</div>

	<div class="add-table-margin">
		<form name="form" action="controller" method="post"
			onsubmit="return validateRegistration()">
			<div class="form_wrap">
				<div class="input_grp">
					<td class="space_around_title_text"><c:out value="${reg_login}" /></td>
					<div class="input_wrap">
						<label for="login"><c:out value="${login}" /></label> <input
							type="text" id="login" name="login" value="user">
					</div><br>
					<td class="space_around_title_text"><c:out value="${reg_password}" /></td>
					<div class="input_wrap">
						<label for="password"><c:out value="${password}" /></label> <input
							type="password" id="password" name="password" value="Fqrs15f15!!">
					</div>
					<td class="space_around_title_text"><c:out value="${reg_repPassword}" /></td>
					<div class="input_wrap">
						<label for="repeatPassword"><c:out	value="${repeat_password}" /></label> <input 
						type="password"	id="repeatPassword" name="repeatPassword" value="Fqrs15f15!!">
					</div><br>
					<td class="space_around_title_text"><c:out	value="${reg_email}" /></td>
					<div class="input_wrap">
						<label for="email"><c:out value="${email}" /></label> <input
							type="email" id="email" name="email" value="enhort@mail.ru">
					</div><br>
					<td class="space_around_title_text"><c:out	value="${reg_name}" /></td>
					<div class="input_wrap">
						<label for="name"><c:out value="${name}" /></label> <input
							type="text" id="name" name="name" value="Artem">
					</div><br>

					<td class="space_around_title_text"><c:out	value="${reg_surname}" /></td>
					<div class="input_wrap">
						<label for="surname"><c:out value="${surname}" /></label> <input
							type="text" id="surname" name="surname" value="Ponikarov">
					</div><br>

					<div class="input_wrap">
						<td class="space_around_title_text"><c:out	value="${reg_address}" /></td> <label
							for="adress"><c:out value="${adress}" /></label> <input
							type="text" id="adress" name="adress" value="Kazan">
					</div><br>
					<div class="input_wrap">
						<td class="space_around_title_text"><c:out	value="${reg_tel}" /></td> <label
							for="tel"><c:out value="${tel}" /></label> <input type="text"
							id="tel" name="tel" value="79172000000">
					</div><br>
					<div class="input_wrap">
						<td class="space_around_title_text"><c:out	value="${reg_birth}" /></td> <label
							for="birthday"><c:out value="${birthday}" /></label> <input
							type="date" id="birthday" name="birthday" value="1988-02-02">
					</div><br>
					<form>
						<input type="radio" id="gender-male" name="gender" value="male">
						<label for="gender-male"><c:out	value="${reg_male}" /></label><br> <input type="radio"
							id="gender-female" name="gender" value="female"> <label
							for="gender-female"><c:out	value="${reg_femail}" /></label><br>

					</form>
				</div>
				<br>
				<div class="input_wrap">
					<input type="hidden" name="command" value="do_registration" /> <input
						type="submit" value=" &#10004; " class="submit_btn">
				</div>
				 
				<div align="right">
					<form action="controller" method="get">
						<input type="hidden" name="command" value="go_to_base_page" /> <input
							type="submit" value=<c:out value="${reg_back}" /> />
				</div>
		</form>
	</div>
	</form>
</div>
</div>
