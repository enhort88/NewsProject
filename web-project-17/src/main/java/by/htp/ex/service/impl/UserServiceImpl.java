package by.htp.ex.service.impl;

import java.text.SimpleDateFormat;

import by.htp.ex.bean.User;
import by.htp.ex.dao.DaoException;
import by.htp.ex.dao.DaoProvider;
import by.htp.ex.dao.IUserDAO;
import by.htp.ex.service.IUserService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.util.validation.ValidationException;
import by.htp.ex.util.validation.impl.UserValidator;
import by.htp.ex.util.validation.impl.UserValidator.UserValidationBuilder;

public class UserServiceImpl implements IUserService {
	
	private static final String GUEST = "guest";
	private static final SimpleDateFormat FORMATTER_BIRTHDAY = new SimpleDateFormat("yyyy-MM-dd");
	private final IUserDAO userDAO = DaoProvider.getInstance().getUserDao();

	@Override
	public String signIn(String login, String password) throws ServiceException, ValidationException {

		UserValidationBuilder userValidationBuilder = new UserValidator.UserValidationBuilder();
		UserValidator validator = userValidationBuilder
				.checkLogin(login)
				.checkPassword(password)
				.checkAll();

		if (!validator.getErrors().isEmpty()) {
			
			throw new ValidationException(validator.errorMessage());
		}

		try {
			if (userDAO.logination(login, password)) {
				return userDAO.getRole(login);
			} else {
				return GUEST;
			}
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean registration(User user) throws ValidationException, ServiceException {
		String dateBrith = FORMATTER_BIRTHDAY.format(user.getDateBirth());
		
		UserValidationBuilder userValidationBuilder = new UserValidator.UserValidationBuilder();
		UserValidator validator = userValidationBuilder
				.checkLogin(user.getLogin())
				.checkPassword(user.getPassword())
				.checkEmail(user.getEmail())
				.checkName(user.getName())
				.checkSurname(user.getSurname())
				.checkBirthday(dateBrith)
				.checkAll();

		if (!validator.getErrors().isEmpty()) {
			throw new ValidationException(validator.errorMessage());
		}

		try {
			return userDAO.registration(user);
			} 
		catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public String getRole(String login) throws ServiceException {
		try {
			return userDAO.getRole(login);
		} catch (DaoException e) {
			throw new ServiceException(e);

		}

	}

	@Override
	public User getUserById(Integer userId) throws ServiceException {
		try {
			return userDAO.getUserById(userId);
		} catch (DaoException e) {
			throw new ServiceException(e);

		}

	}

	@Override
	public int getIdByLogin(String login) throws ServiceException {
		try {
			return userDAO.getIdbyLogin(login);
		} catch (DaoException e) {
			throw new ServiceException(e);

		}

	}
}
