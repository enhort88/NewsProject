package by.htp.ex.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import by.htp.ex.bean.News;
import by.htp.ex.bean.User;
import by.htp.ex.dao.DaoException;
import by.htp.ex.dao.NewsDAOException;
import by.htp.ex.dao.impl.connection_pool.ConnectionPool;
import by.htp.ex.dao.impl.connection_pool.ConnectionPoolException;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.impl.NewsServiceImpl;
import by.htp.ex.service.impl.UserServiceImpl;
import by.htp.ex.util.validation.ValidationException;
import jakarta.servlet.ServletException;

public class TestDao {
	private static final SimpleDateFormat FORMATTER_BIRTHDAY = new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String[] args) throws ServletException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		try {
			connectionPool.initPoolData();
		} catch (ConnectionPoolException e1) {
			e1.printStackTrace();
		}


		UserServiceImpl u = new UserServiceImpl();
		NewsServiceImpl s =  new NewsServiceImpl();

		try {

			
			Integer newsId = 17;
			String title = "Hello5";
			String briefNews = "Brief5";
			String content = "CONTENT3";
			String newsDate = "01-01-2023";
			Integer userId = 1;

			
			String login = "user5";
			String password = "User2!";
			String email = "enhort@mail.ru";
			String name = "Artem";
			String surname = "Ponikarov";
			String adress = "Kazan";
			String tel = "79172000000";
			String date = "1988-02-02";
			String gender = "male";
			
			
			Date dateBirth = null;
			try {
				dateBirth = FORMATTER_BIRTHDAY.parse(date);
			} catch (Exception e2) {
				throw new ServletException("Error! Problems with Date Birth parsing", e2);
			}
			
			News newS = new News(newsId, title, briefNews, content, newsDate, userId);
			
			User user = new User(login,password,email);
			
			user.setName(name);
			user.setSurname(surname);
			user.setAdress(adress);
			user.setTelnumber(tel);
			user.setDateBirth(dateBirth);
			user.setGender(gender);

			for (int i = 0; i < 3; i++) {
				System.out.println(s.list().get(i));
			}
			System.out.println(s.list().size());
//			System.out.println(u.registration(user) + " TEST DAO");
			

		} catch (/*ValidationException*/ /* | ServiceException */  ServiceException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
