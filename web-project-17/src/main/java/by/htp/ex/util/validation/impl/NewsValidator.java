package by.htp.ex.util.validation.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.htp.ex.util.validation.IValidationBuilder;

public class NewsValidator {

	private static final String ERROR_MESS_2_PART = ". Troubles with REGEX rules!";
	private static final String ERROR_MESS_1_PART = "Error! There are problems in field%s";
	private List<String> errors;

	private NewsValidator(NewsValidationBuilder e) {
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

	public static class NewsValidationBuilder implements IValidationBuilder<NewsValidator> {
		private static final String TITLE = "Title";
		private static final String BRIEF = "Brief";
		private static final String CONTENT = "Content";
		private static final String DATE = "Date";

		private List<String> errors = new ArrayList<String>();

		private static final String REGEX_FOR_TITLE = "^[a-zA-Z0-9- ]{1,25}$";
		private static final String REGEX_FOR_BRIEF = "^[a-zA-Z0-9- ]{1,80}$";
		private static final String REGEX_FOR_CONTENT = "^[a-zA-Z][a-zA-Z0-9-_\\. ]{1,200}$";
		private static final String REGEX_FOR_DATE = "(0?[1-9]|[12]\\d|30|31)[^\\w\\d\\r\\n:](0?[1-9]|1[0-2])[^\\w\\d\\r\\n:](\\d{4}|\\d{2})";

		public NewsValidationBuilder checkTitle(String title) {
			Matcher titleMatcher = Pattern.compile(REGEX_FOR_TITLE).matcher(title);
			if (!titleMatcher.matches()) {
				errors.add(TITLE);
			}
			return this;
		}

		public NewsValidationBuilder checkBrief(String brief) {
			Matcher briefMatcher = Pattern.compile(REGEX_FOR_BRIEF).matcher(brief);
			if (!briefMatcher.matches()) {
				errors.add(BRIEF);
			}
			return this;
		}

		public NewsValidationBuilder checkContent(String content) {
			Matcher contentMatcher = Pattern.compile(REGEX_FOR_CONTENT).matcher(content);
			if (!contentMatcher.matches()) {
				errors.add(CONTENT);
			}
			return this;
		}

		public NewsValidationBuilder checkDate(String date) {
			Matcher dateMatcher = Pattern.compile(REGEX_FOR_DATE).matcher(date);
			if (!dateMatcher.matches()) {
				errors.add(DATE);
			}
			return this;
		}

		@Override
		public NewsValidator checkAll() {
			return new NewsValidator(this);
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
