
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;

import com.mysql.jdbc.PreparedStatement;

public class SignUpView extends JFrame {
	JPanel panel;
	JTextField usernameField, passField, repeatPassField, nameField, lastNameField, phoneNumberField, addressField;
	JLabel usernameLabel, passLabel, repeatPassLabel, nameLabel, lastNameLabel, phoneNumberLabel, addressLabel, errorMessageLabel; 
	JButton signUpButton;
	MouseListener signUp;
	
	public SignUpView() {
		
		signUp = new MouseAdapter() {
			
			public void mouseClicked(MouseEvent arg0) {
				
				String username = usernameField.getText();
				String pass = passField.getText();
				String repeatPass = repeatPassField.getText();
				String name = nameField.getText();
				String lastName = lastNameField.getText();
				String phoneNumber = phoneNumberField.getText();
				String address = addressField.getText();
				
				int userAlreadyExists = 0;
				
				for (String users : Main.userArrayList) {
					
					if (username.equals(users)) {
						
						if (!username.isEmpty() && !pass.isEmpty() && !repeatPass.isEmpty() && !name.isEmpty() && !lastName.isEmpty()) {
						
						errorMessageLabel.setText("'" + username + "' already exists");
						errorMessageLabel.setVisible(true);
						userAlreadyExists ++;
						
						}
						
					}
					
				}
				
				if (userAlreadyExists == 0) {
				
				if (username.isEmpty() || pass.isEmpty() || repeatPass.isEmpty() || name.isEmpty() || lastName.isEmpty()) {
					errorMessageLabel.setText("There are empty fields");
					errorMessageLabel.setVisible(true);
					
				} else if (pass.equals(repeatPass) == false) {
					errorMessageLabel.setText("Password fields do not match");
					errorMessageLabel.setVisible(true);
					
				} else {
				
				try {
					
					Connection conn = MysqlConnection.getInstance();
					Statement st = conn.createStatement();
					
					st.execute("Insert into user (username, pass, name, lastName, phone, address) values " +
							"('" + username + "', '" + pass + "', '" + name + "', '" + lastName + "', '" + phoneNumber + "', '" + address + "');");
					
					Main.userArrayList.add(username);
					System.out.println("User created: " + username);
					System.out.println(Main.userArrayList);
					
					hide();
					
					JOptionPane.showMessageDialog(null, "You have been successfully signed up, now you can log in");
					
				} catch (Exception e) {
					
					JOptionPane.showMessageDialog(null, e.getMessage());
					
				}
				
				}
				
				}	
			}
			
		};
		
		setBounds(800, 200, 400, 550);
		setVisible(true);
		
		panel = new JPanel();
		panel.setLayout(null);
		add(panel);
		
		DocumentFilter dfilter = new LowercaseFilter();
		
		usernameField = new JTextField();
		passField = new JPasswordField();
		repeatPassField = new JPasswordField();
		nameField = new JTextField();
		lastNameField = new JTextField();
		phoneNumberField = new JTextField();
		addressField = new JTextField();
		usernameField.setBounds(100, 80, 200, 27);
		((AbstractDocument) usernameField.getDocument()).setDocumentFilter(dfilter);
		passField.setBounds(100, 135, 200, 27);
		repeatPassField.setBounds(100, 190, 200, 27);
		nameField.setBounds(100, 245, 200, 27);
		lastNameField.setBounds(100, 300, 200, 27);
		phoneNumberField.setBounds(100, 355, 200, 27);
		addressField.setBounds(100, 405, 200, 27);
		panel.add(usernameField);
		panel.add(passField);
		panel.add(repeatPassField);
		panel.add(nameField);
		panel.add(lastNameField);
		panel.add(phoneNumberField);
		panel.add(addressField);
		
		usernameLabel = new JLabel("Username *");
		passLabel = new JLabel("Password *");
		repeatPassLabel = new JLabel("Repeat Password *");
		nameLabel = new JLabel("Name *");
		lastNameLabel = new JLabel("Last Name *");
		phoneNumberLabel = new JLabel("Phone Number");
		addressLabel = new JLabel("Address");
		usernameLabel.setBounds(100, 60, 150, 20);
		passLabel.setBounds(100, 115, 150, 20);
		repeatPassLabel.setBounds(100, 170, 150, 20);
		nameLabel.setBounds(100, 225, 150, 20);
		lastNameLabel.setBounds(100, 280, 150, 20);
		phoneNumberLabel.setBounds(100, 335, 150, 20);
		addressLabel.setBounds(100, 385, 150, 20);
		panel.add(usernameLabel);
		panel.add(passLabel);
		panel.add(repeatPassLabel);
		panel.add(nameLabel);
		panel.add(lastNameLabel);
		panel.add(phoneNumberLabel);
		panel.add(addressLabel);
		
		signUpButton = new JButton("Sign Up!");
		signUpButton.setBounds(150, 475, 100, 30);
		signUpButton.addMouseListener(signUp);
		panel.add(signUpButton);
		
		errorMessageLabel = new JLabel("There are empty fields");
		errorMessageLabel.setBounds(100, 35, 250, 20);
		errorMessageLabel.setForeground(Color.RED);
		errorMessageLabel.setVisible(false);
		panel.add(errorMessageLabel);
		
	}

}
