package ua.lviv.lgs.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtile {
	

	private static String USER_NAME = "root";
	private static String USER_PASSWORD = "4415348692Voxa";
	private static String URL = "jdbc:mysql://localhost/book_ishop?serverTimezone=UTC";
	
	public static Connection openConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		return DriverManager.getConnection(URL,USER_NAME,USER_PASSWORD);
	}

}
