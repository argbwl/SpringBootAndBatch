package com.ab.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class ConnectionUtil {

	private static String DRIVER_CLASS_NAME;
	private static String USER;
	private static String PASSWORD;
	private static String URL;

	static {
		try (InputStream input = ConnectionUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
			Properties prop = new Properties();
			if (input == null) {
				System.out.println("Sorry, unable to find db.properties");
			} else {
				// load a properties file from class path, inside static method
				prop.load(input);
				// get the property value and set to field
				DRIVER_CLASS_NAME = prop.getProperty("db.driver");
				URL = prop.getProperty("db.url");
				USER = prop.getProperty("db.user");
				PASSWORD = prop.getProperty("db.password");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Bean(name="dmds")
	public static DataSource getDataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName(DRIVER_CLASS_NAME);
		ds.setUsername(USER);
		ds.setPassword(PASSWORD);
		ds.setUrl(URL);
		return ds;
	}
	
	public static Connection getConnection() throws SQLException {
		return getDataSource().getConnection();
	}
	
	public static JdbcTemplate getJdbcTemplate() {
		JdbcTemplate jt=new JdbcTemplate();
		jt.setDataSource(getDataSource());
		return jt;
	}

}
