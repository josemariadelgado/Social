package com.jose.social.mysql;

import java.sql.*;

import javax.swing.JOptionPane;

public class MysqlConnection {
	public static Connection conn;
	
	public static Connection getInstance() {
		
		try {
			
			if (conn == null) {
				
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost/dbtest", "root", "123");
				System.out.println("\nMysql connection succes!");
				
			}
			
		} catch (SQLException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			
		} 
		
		return conn;
		
	}

}
