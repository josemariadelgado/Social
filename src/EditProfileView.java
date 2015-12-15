
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;

import com.mysql.jdbc.Statement;

public class EditProfileView extends JFrame {
	JPanel panel;
	static JTextField usernameField, nameField, lastNameField, phoneNumberField, addressField;
	JPasswordField currentPassField, newPassField, repeatNewPassField;
	JLabel usernameLabel, nameLabel, lastNameLabel, phoneNumberLabel, addressLabel, currentPassLabel, newPassLabel, repeatNewPassLabel, errorMessageLabel;
	JButton saveChangesButton, deleteAccountButton;
	MouseListener saveChanges, deleteAccount;
	
	
	public EditProfileView() {
		
		saveChanges = new MouseAdapter() {
			
			public void mouseClicked(MouseEvent arg0) {
				
				String username = usernameField.getText();
				String name = nameField.getText();
				String lastName = lastNameField.getText();
				String phoneNumber = phoneNumberField.getText();
				String address = addressField.getText();
				String currentPass = currentPassField.getText();
				String newPass = newPassField.getText();
				String repeatNewPass = repeatNewPassField.getText();
				String getPass = "";
				
				int userAlreadyExists = 0; 
				
				for (String users : Main.userArrayList) {
					
					if (!username.equals(HomeScreenView.usernameLabel.getText())) {
						
						if (username.equals(users)) {
							
							if (!name.isEmpty() && !lastName.isEmpty() && !username.isEmpty()) {
								
								errorMessageLabel.setText("'" + username + "' already exists");
								errorMessageLabel.setVisible(true);
								userAlreadyExists ++;
						
						}
						
					}
						
					}
					
				}
				
				if (userAlreadyExists == 0) {
				
				if (username.isEmpty() || name.isEmpty() || lastName.isEmpty()) {
					errorMessageLabel.setText("There are empty fields");
					errorMessageLabel.setVisible(true);
					
				} else if (currentPass.isEmpty() && newPass.isEmpty() && repeatNewPass.isEmpty()){
					
					try {
						
						Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/dbtest", "root", "123");
						Statement st = (Statement) conn.createStatement();
						
						st.execute("Update user set username = '" + username + "', name = '" + name + "', lastName = '" 
						+ lastName + "', phone = '" + phoneNumber + "', address = '" + address + "' where username = '" + HomeScreenView.usernameLabel.getText() + "';");
						
						hide();
						
						Main.userArrayList.removeAll(Main.userArrayList);
						Main.createUserArrayList();
						
						ResultSet rs = st.executeQuery("Select * from user where username = '" + username + "';");
						
						if (rs.next()) {
							
							String homeScreenName = rs.getString("name");
							String homeScreenLastName = rs.getString("lastName");
							String homeScreenUsername = rs.getString("username");
							
							HomeScreenView.nameLabel.setText(homeScreenName);
							HomeScreenView.lastNameLabel.setText(homeScreenLastName);
							HomeScreenView.usernameLabel.setText(homeScreenUsername);
							
							Main.homeScreenFrame.setTitle("@" + username);
												
						}
						
						st.close();
						conn.close();
						
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
						
					}
					
				} else if (!currentPass.isEmpty() && !newPass.isEmpty() && !repeatNewPass.isEmpty()) {
					
					
					try {
						
						Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/dbtest", "root", "123");
						Statement st = (Statement) conn.createStatement();
						
						ResultSet rs = st.executeQuery("Select pass from user where username = '" + HomeScreenView.usernameLabel.getText() + "';");
						
						if (rs.next()) {
							
							getPass = rs.getString("pass");
							
						}
						
						if (currentPass.equals(getPass) && !newPass.isEmpty() && !repeatNewPass.isEmpty() && newPass.equals(repeatNewPass)) {
							
							try {
								
								st.execute("Update user set username = '" + username + "', name = '" + name + "', lastName = '" 
						        + lastName + "', phone = '" + phoneNumber + "', address = '" + address + "', pass = '" + newPass + "' where username = '" + 
						        HomeScreenView.usernameLabel.getText() + "';");
								
								hide();
								
								Main.userArrayList.removeAll(Main.userArrayList);
								Main.createUserArrayList();
								
								ResultSet rs1 = st.executeQuery("Select * from user where username = '" + username + "';");
								
								if (rs1.next()) {
									
									String homeScreenName = rs1.getString("name");
									String homeScreenLastName = rs1.getString("lastName");
									String homeScreenUSername = rs1.getString("username");
									
									HomeScreenView.nameLabel.setText(homeScreenName);
									HomeScreenView.lastNameLabel.setText(homeScreenLastName);
									HomeScreenView.usernameLabel.setText(homeScreenUSername);
									
									Main.homeScreenFrame.setTitle("@" + username);
								
								}
								
								st.close();
								conn.close();
								
							} catch (Exception e) {
								
							}
							
						} else if (!currentPass.equals(getPass)) {
							errorMessageLabel.setText("'Current Password' field does not match with your current password");
							errorMessageLabel.setVisible(true);
							
						} else if (!newPass.equals(repeatNewPass)) {
							errorMessageLabel.setText("'New Password' fields do not match");
							errorMessageLabel.setVisible(true);
							
						}
						
						st.close();
						conn.close();
						
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
						
					}
					
				} else if (currentPass.isEmpty() || newPass.isEmpty() || repeatNewPass.isEmpty()) {
					errorMessageLabel.setText("There are empty fields");
					errorMessageLabel.setVisible(true);
					
				}
				
				} 	
			}
		};
		
		deleteAccount = new MouseAdapter() {
			
			public void mouseClicked(MouseEvent arg0) {
				
				String username = HomeScreenView.usernameLabel.getText();
				
				int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete your account?", "Delete Account", 2);
				
				if (dialogResult == JOptionPane.YES_OPTION) {
					
					try {
					
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/dbtest", "root", "123");
					Statement st = (Statement) conn.createStatement();
					
					st.execute("Delete from user where username = '" + username + "';");
					
					hide();
					Main.homeScreenFrame.setVisible(false);
					Main.frame = new LogInView();
					
					Main.userArrayList.removeAll(Main.userArrayList);
					Main.createUserArrayList();
					
					st.close();
					conn.close();
					
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
						
					}	
				}	
			}
		};
		
		setBounds(650, 250, 700, 450);
		setVisible(true);
		
		panel = new JPanel();
		panel.setLayout(null);
		add(panel);
		
		DocumentFilter dfilter = new LowercaseFilter();
		
		usernameField = new JTextField();
		currentPassField = new JPasswordField();
		newPassField = new JPasswordField();
		repeatNewPassField = new JPasswordField();
		nameField = new JTextField();
		lastNameField = new JTextField();
		phoneNumberField = new JTextField();
		addressField = new JTextField();
		usernameField.setBounds(100, 80, 200, 27);
		((AbstractDocument) usernameField.getDocument()).setDocumentFilter(dfilter);
		currentPassField.setBounds(400, 80, 200, 27);
		newPassField.setBounds(400, 135, 200, 27);
		repeatNewPassField.setBounds(400, 190, 200, 27);
		nameField.setBounds(100, 135, 200, 27);
		lastNameField.setBounds(100, 190, 200, 27);
		phoneNumberField.setBounds(100, 245, 200, 27);
		addressField.setBounds(100, 300, 200, 27);
		panel.add(usernameField);
		panel.add(currentPassField);
		panel.add(newPassField);
		panel.add(repeatNewPassField);
		panel.add(nameField);
		panel.add(lastNameField);
		panel.add(phoneNumberField);
		panel.add(addressField);
		
		usernameLabel = new JLabel("Username *");
		currentPassLabel = new JLabel("Current Password");
		newPassLabel = new JLabel("New Password");
		repeatNewPassLabel = new JLabel("Repeat New Password");
		nameLabel = new JLabel("Name *");
		lastNameLabel = new JLabel("Last Name *");
		phoneNumberLabel = new JLabel("Phone Number");
		addressLabel = new JLabel("Address");
		usernameLabel.setBounds(100, 60, 150, 20);
		currentPassLabel.setBounds(400, 60, 150, 20);
		newPassLabel.setBounds(400, 115, 150, 20);
		repeatNewPassLabel.setBounds(400, 170, 200, 20);
		nameLabel.setBounds(100, 115, 150, 20);
		lastNameLabel.setBounds(100, 170, 150, 20);
		phoneNumberLabel.setBounds(100, 225, 150, 20);
		addressLabel.setBounds(100, 280, 150, 20);
		panel.add(usernameLabel);
		panel.add(currentPassLabel);
		panel.add(newPassLabel);
		panel.add(repeatNewPassLabel);
		panel.add(nameLabel);
		panel.add(lastNameLabel);
		panel.add(phoneNumberLabel);
		panel.add(addressLabel);
		
		saveChangesButton = new JButton("Save Changes");
		saveChangesButton.setBounds(275, 370, 150, 30);
		saveChangesButton.addMouseListener(saveChanges);
		panel.add(saveChangesButton);
		
		errorMessageLabel = new JLabel("There are empty fields");
		errorMessageLabel.setBounds(100, 35, 500, 20);
		errorMessageLabel.setForeground(Color.RED);
		errorMessageLabel.setVisible(false);
		panel.add(errorMessageLabel);
		
		deleteAccountButton = new JButton("Delete my account");
		deleteAccountButton.setBounds(520, 370, 160, 30);
		deleteAccountButton.setForeground(Color.RED);
		deleteAccountButton.addMouseListener(deleteAccount);
		panel.add(deleteAccountButton);
		
	}

}
