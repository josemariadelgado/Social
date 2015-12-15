
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;

public class HomeScreenView extends JFrame{
	static JFrame editProfileFrame;
	JPanel panel;
	static JLabel nameLabel, lastNameLabel, usernameLabel;
	JButton editProfileButton, logOutButton, showAllUsersButton;
	MouseListener openEditProfileView, logOut, openAllUsersView;
	
	public void getUserData() {
		
		String username = LogInView.usernameField.getText();
		
		try {
			
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/dbtest", "root", "123");
			
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("Select * from user where username = '" + username + "';");
			
			if (rs.next()) {
				
				int id = rs.getInt("userId");
				String name = rs.getString("name");
				String lastName = rs.getString("lastName");
				
				System.out.println("\nLogged in as: " + username + "\nid = " + id + "\nName: " + name + "\nLast Name: " + lastName);
				
				nameLabel.setText(name);
				lastNameLabel.setText(lastName);
				usernameLabel.setText(username);
				
			}
			
			st.close();
			conn.close();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
		}
		
	}
	
	public HomeScreenView() {
		
		openEditProfileView = new MouseAdapter() {
			
			public void mouseClicked(MouseEvent arg0) {
				
				String username = usernameLabel.getText();
				
				try {
					
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/dbtest", "root", "123");
					
					Statement st = conn.createStatement();
					ResultSet rs = st.executeQuery("Select * from user where username = '" + username + "';");
					
					if (rs.next()) { 
						
						editProfileFrame = new EditProfileView();
						
						String name = rs.getString("name");
						String lastName = rs.getString("lastName");
						String phoneNumber = rs.getString("phone");
						String address = rs.getString("address");
						
						EditProfileView.usernameField.setText(username);
						EditProfileView.nameField.setText(name);
						EditProfileView.lastNameField.setText(lastName);
						EditProfileView.phoneNumberField.setText(phoneNumber);
						EditProfileView.addressField.setText(address);
						
						editProfileFrame.setTitle("Edit " + "@" + username);
						
						st.close();
						conn.close();
						
					} else {
						
					}
										
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					
				}
				
			}
		};
		
		logOut = new MouseAdapter() {
			
			public void mouseClicked(MouseEvent arg0) {
				
				int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to Log Out?", "Log Out", 2);
				
				if (dialogResult == JOptionPane.YES_OPTION) {
					hide();
					Main.allUsersFrame.setVisible(false);
					JFrame logIn = new LogInView();
				}
			}
		};
		
		openAllUsersView = new MouseAdapter() {
			
			public void mouseClicked(MouseEvent arg0) {
				
				Main.allUsersFrame = new AllUsersView();
				
			}
		};
		
	    setBounds(625, 200, 800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("@" + LogInView.usernameField.getText());
		setVisible(true);
		
		panel = new JPanel();
		panel.setLayout(null);
		add(panel);
		
		nameLabel = new JLabel("Name");
		lastNameLabel = new JLabel("Last Name");
		usernameLabel = new JLabel("username");
		nameLabel.setFont(new Font("Ubuntu", 0, 25));
		nameLabel.setBounds(60, 30, 200, 30);
		lastNameLabel.setBounds(60, 55, 200, 30);
		usernameLabel.setBounds(74, 80, 200, 30);
		JLabel atLabel = new JLabel("@");
		atLabel.setBounds(60, 80, 20, 30);
		panel.add(atLabel);
		panel.add(nameLabel);
		panel.add(lastNameLabel);
		panel.add(usernameLabel);
		
		getUserData();
		
		logOutButton = new JButton("Log Out");
		editProfileButton = new JButton("Edit Profile");
		showAllUsersButton = new JButton("Show All Users");
		logOutButton.setBounds(690, 10, 100, 30);
		editProfileButton.setBounds(60, 110, 100, 30);
		showAllUsersButton.setBounds(60, 145, 125, 30);
		logOutButton.setFocusable(false);
		editProfileButton.setFocusable(false);
		showAllUsersButton.setFocusable(false);
		logOutButton.addMouseListener(logOut);
		editProfileButton.addMouseListener(openEditProfileView);
		showAllUsersButton.addMouseListener(openAllUsersView);
		panel.add(logOutButton);
		panel.add(editProfileButton);
		panel.add(showAllUsersButton);
		
	}
}
