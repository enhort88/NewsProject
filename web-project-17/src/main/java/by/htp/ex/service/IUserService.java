package by.htp.ex.service;

import by.htp.ex.bean.User;
import by.htp.ex.util.validation.ValidationException;


public interface IUserService {

	String signIn(String login, String password) throws ServiceException, ValidationException; 							

	boolean registration(User user) throws ServiceException, ValidationException;										
	
	public String getRole(String login) throws ServiceException;
	
	User getUserById (Integer userId) throws ServiceException;

	int getIdByLogin(String login) throws ServiceException;

	
}
