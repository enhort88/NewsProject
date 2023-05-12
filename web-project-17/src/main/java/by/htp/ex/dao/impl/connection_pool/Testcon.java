
package by.htp.ex.dao.impl.connection_pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Testcon {

	public static void main(String[] args) {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		try {
			connectionPool.initPoolData();
		} catch (ConnectionPoolException e1) {
			e1.printStackTrace();
		}

		try (Connection connection = connectionPool.takeConnection()) {

	//		PreparedStatement st = connection.prepareStatement("SELECT * FROM users");
			PreparedStatement st = connection.prepareStatement("SELECT * FROM users WHERE login = \"enhort8\"");
			System.out.println("Database connected!");

			/*
			 * String sql =
			 * "INSERT INTO users (login,password,registration_date,roles_id) VALUES (?,?,?,?)"
			 * ; PreparedStatement ps = connection.prepareStatement(sql); ps.setString(1,
			 * "testCON"); ps.setString(2, "testCON"); ps.setString(3,
			 * "2023-03-03 00:00:00"); ps.setInt(4, 1); ps.executeUpdate();
			 */	 

			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getInt(1) + " - " + rs.getString(2) + " - " + rs.getString(3) + " - "
						+ rs.getString(4) + " - " + rs.getInt(5));
			}

		} catch (SQLException | ConnectionPoolException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
}
