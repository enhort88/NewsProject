package by.htp.ex.util.validation.impl;

import java.util.ArrayList;
import java.util.List;

import by.htp.ex.util.validation.IValidationBuilder;
import jakarta.servlet.http.HttpSession;

public class SessionValidator {
	

	private static final String ERROR_MESS_2_PART = ". Sorry, try later!";
	private static final String ERROR_MESS_1_PART = "Error! There are problem%s";
	private List<String> errors;

	private SessionValidator(SessionValidationBuilder b) {
		this.errors = b.errors;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public static class SessionValidationBuilder implements IValidationBuilder<SessionValidator> {
		private static final String NOT_ACTIVE = "not active";
		private static final String USER = "user";
		private static final String EDITOR = "editor";
		private static final String MODERATOR = "moderator";
		private static final String ADMIN = "admin";
		private static final String ROLE = "role";
		
		private static final String ACCESS_DENIED = "Access denied for user";
		private static final String ACCESS_DENIED_FOR_THIS_USER = "Access denied for this user: ";
		private static final String NOT_AUTHORIZED = "User not authorized";
		private List<String> errors = new ArrayList<String>();
		
		public SessionValidationBuilder checkAccess(HttpSession session) {
			if (session == null) {
				errors.add(ACCESS_DENIED);
				return this;
			}
			String role =  (String) session.getAttribute(ROLE);

			if (!(ADMIN.equals(role) || MODERATOR.equals(role) || EDITOR.equals(role))) {
				errors.add(ACCESS_DENIED_FOR_THIS_USER +"\""+  role + "\"");
			}
			return this;
		}

		public SessionValidationBuilder checkAuthorization(HttpSession session) {
			if (session == null) {
				errors.add(NOT_AUTHORIZED);
			} else {
				String userStatus = (String) session.getAttribute(USER);
				if (NOT_ACTIVE.equals(userStatus) || userStatus == null) {
					errors.add(NOT_AUTHORIZED);
				}
			}
			return this;
		}

		@Override
		public SessionValidator checkAll() {
			return new SessionValidator(this);
		}
	}

	public String errorMessage() {
		int count = errors.toArray().length;
		StringBuffer message = new StringBuffer();
		String str = ERROR_MESS_1_PART.formatted(count > 1 ? "s: " : ": ");
		message.append(str).append(errors).append(ERROR_MESS_2_PART);
		return message.toString();
	}
}
