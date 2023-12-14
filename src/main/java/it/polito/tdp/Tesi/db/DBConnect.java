package it.polito.tdp.Tesi.db;


import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBConnect {

	
	static private HikariDataSource ds = null;
	static private String url = "jdbc:mariadb://localhost:3306/boat";
	
	public static Connection getConnection() {
		
		if(ds==null) {
			ds = new HikariDataSource();
			ds.setJdbcUrl(url);
			ds.setUsername("root");
			ds.setPassword("rootroot");
		}
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}




