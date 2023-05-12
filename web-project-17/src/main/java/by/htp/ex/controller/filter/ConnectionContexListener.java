package by.htp.ex.controller.filter;

import by.htp.ex.dao.impl.connection_pool.ConnectionPool;
import by.htp.ex.dao.impl.connection_pool.ConnectionPoolException;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class ConnectionContexListener implements ServletContextListener{
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		try {
			connectionPool.initPoolData();
		} catch (ConnectionPoolException e) {
			throw new RuntimeException("Error with ConnectionPoolData", e);
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce){
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		connectionPool.dispose();
		
	}

}