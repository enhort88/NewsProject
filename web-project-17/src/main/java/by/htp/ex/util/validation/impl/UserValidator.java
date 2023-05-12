package by.htp.ex.util.validation.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.htp.ex.util.validation.IValidationBuilder;

public class UserValidator {

	private static final String ERROR_MESS_2_PART = ". Troubles with REGEX rules!";
	private static final String ERROR_MESS_1_PART = "Error! There are problems in field%s";
	
	private List<String> errors;

	private UserValidator(UserValidationBuilder e) {
		this.errors = e.errors;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	@Override
	public int hashCode() {
		return Objects.hash(errors);
	}

	public static class UserValidationBuilder implements IValidationBuilder<UserValidator> {
		private static final String LOGIN = "Login";
		private static final String PASSWORD = "Password";
		private static final String EMAIL = "Email";
		private static final String NAME = "Name";
		private static final String SURNAME = "Surname";
		private static final String BIRTHDAY_DATE = "Birthday date";
		
		private List<String> errors = new ArrayList<String>();
		
		private static final String REGEX_FOR_LOGIN = "^[a-zA-Z][a-zA-Z0-9-_\\.]{3,18}$";
//		private static final String REGEX_FOR_PASSWORD = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^\\da-zA-Z]).{4,}$\r\n";
		private static final String REGEX_FOR_PASSWORD = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$";
		private static final String REGEX_FOR_EMAIL = "^[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9]+)*(\\.[a-zA-Z]{2,})$";
//		private static final String REGEX_FOR_EMAIL = "^[^@\\\\s]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}$\r\n";
//		private static final String REGEX_FOR_EMAIL = "/^\\S+@\\S+\\.\\S+$/";
		private static final String REGEX_FOR_NAME = "^[a-zA-Z]{1,12}$";
		private static final String REGEX_FOR_SURMANE = "^[a-zA-Z]{1,20}$";
		private static final String REGEX_FOR_BIRTHDAY = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";

		public UserValidationBuilder checkLogin(String login) {
			Matcher loginMatcher = Pattern.compile(REGEX_FOR_LOGIN).matcher(login);
			if (!loginMatcher.matches()) {
				errors.add(LOGIN);
			}
			return this;
		}

		public UserValidationBuilder checkPassword(String password) {

			Matcher passwordMatcher = Pattern.compile(REGEX_FOR_PASSWORD).matcher(password);
			if (!passwordMatcher.matches()) {
				errors.add(PASSWORD);
			}
			return this;
		}

		public UserValidationBuilder checkEmail(String email) {
			Matcher emailMatcher = Pattern.compile(REGEX_FOR_EMAIL).matcher(email);
			if (!emailMatcher.matches()) {
				errors.add(EMAIL);
			}
			return this;
		}

		public UserValidationBuilder checkName(String name) {
			Matcher nameMatcher = Pattern.compile(REGEX_FOR_NAME).matcher(name);
			if (!nameMatcher.matches()) {
				errors.add(NAME);
			}
			return this;
		}

		public UserValidationBuilder checkSurname(String surname) {
			Matcher surnameMatcher = Pattern.compile(REGEX_FOR_SURMANE).matcher(surname);
			if (!surnameMatcher.matches()) {
				errors.add(SURNAME);
			}
			return this;
		}

		public UserValidationBuilder checkBirthday(String birthday) {
			Matcher birthdayMatcher = Pattern.compile(REGEX_FOR_BIRTHDAY).matcher(birthday);
			if (!birthdayMatcher.matches()) {
				errors.add(BIRTHDAY_DATE);
			}
			return this;
		}

		@Override
		public UserValidator checkAll() {
			return new UserValidator(this);
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
