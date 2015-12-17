package com.jose.social;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import com.jose.social.mysql.MysqlConnection;
import com.jose.social.views.LogInView;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class Main extends LogInView {
	public static JFrame frame, homeScreenFrame, signUpFrame, editProfileFrame, allUsersFrame;
	public static ArrayList<String> userArrayList = new ArrayList<String>();
	
	public static void main(String[] args) {
		frame = new Main();
		frame.setVisible(true);
		
	}
	
	public Main() {
		createUserArrayList();
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			
		    @Override
		    public void run() {
		    	
		    	try {
		    		
		    		if (!MysqlConnection.conn.isClosed()) {
		    			
		    			MysqlConnection.conn.close();
		    			
		    			if (MysqlConnection.conn.isClosed()) {
		    				
		    				System.out.println("\nMysql connection closed");
		    			}
					
		    		}
					
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					
				}
		        
		    }
		    
		});
		
	}

	public static void createUserArrayList() {
		
		try {
			
			Connection conn = MysqlConnection.getInstance();
			Statement st = (Statement) conn.createStatement();
			ResultSet rs;
			
			rs = st.executeQuery("Select username from user;");
			
			while (rs.next()) {
				
				String name = rs.getString("username");
				
				userArrayList.add(name);
				
			}
			
			System.out.println(userArrayList);
		
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			
		}
		
	}
	
}
