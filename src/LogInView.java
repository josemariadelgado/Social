
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;

import com.mysql.jdbc.PreparedStatement;

public class LogInView extends JFrame {
	JPanel panel;
	static JTextField usernameField;
	JPasswordField passField;
	JLabel userNameLabel, passLabel, noAccountLabel, errorMessageLabel;
	JButton logInButton, signUpButton;
	MouseListener logIn, openSignUpView;
	
	public LogInView() {
		
		try {
			
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		    	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		    }
		    
		} catch (Exception e) {
		    
		}
		
		MouseListener logIn = new MouseAdapter() {
			
			public void mouseClicked(MouseEvent arg0) {
				String username = usernameField.getText();
				String pass = passField.getText();
				
				if (username.isEmpty() || pass.isEmpty()) {
					errorMessageLabel.setText("There are empty fields");
					errorMessageLabel.setVisible(true);
					
				} else {
				
				try {
					
					Connection conn = MysqlConnection.getInstance();
					Statement st = conn.createStatement();
					ResultSet rs;
					
					rs = st.executeQuery("Select * from user where userName = '" + username + "' and pass = '" + pass + "';");
					
					if (rs.next()) { 
						
						hide();
						Main.homeScreenFrame = new HomeScreenView();
						
						
					} else {
						errorMessageLabel.setText("Wrong Username or Password");
						errorMessageLabel.setVisible(true);
						
					}
										
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					
				}
				
				}
					
			}
			
		};
		
		openSignUpView = new MouseAdapter() {
			
			public void mouseClicked(MouseEvent arg0) {
				Main.signUpFrame = new SignUpView();
				
			}
		};
		
		setBounds(625, 200, 800, 600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setLayout(null);
		setContentPane(panel);
		
		DocumentFilter dfilter = new LowercaseFilter();
		
		usernameField = new JTextField();
		passField = new JPasswordField();
		usernameField.setBounds(300, 220, 200, 27);
		((AbstractDocument) usernameField.getDocument()).setDocumentFilter(dfilter);
		passField.setBounds(300, 275, 200, 27);
		panel.add(usernameField);
		panel.add(passField);
		
		userNameLabel = new JLabel("Username");
		passLabel = new JLabel("Password");
		errorMessageLabel = new JLabel("There are empty fields");
		noAccountLabel = new JLabel("Don't have an account?");
		userNameLabel.setBounds(300, 200, 200, 20);
		passLabel.setBounds(300, 255, 200, 20);
		errorMessageLabel.setBounds(300, 175, 250, 20);
		errorMessageLabel.setForeground(Color.RED);
		errorMessageLabel.setVisible(false);
		noAccountLabel.setBounds(317, 500, 250, 20);
		panel.add(userNameLabel);
		panel.add(passLabel);
		panel.add(errorMessageLabel);
		panel.add(noAccountLabel);
		
		logInButton = new JButton("Log In");
		signUpButton = new JButton("Sign Up");
		logInButton.setBounds(350, 320, 100, 30);
		logInButton.addMouseListener(logIn);
		signUpButton.setBounds(350, 530, 100, 30);
		signUpButton.addMouseListener(openSignUpView);
		panel.add(logInButton);
		panel.add(signUpButton);
		
		ImageIcon contactImage = new ImageIcon(getClass().getResource("black302.png"));
		JLabel label = new JLabel(contactImage);
		label.setBounds(365, 75, 64, 64);
		panel.add(label);
		
	}

}
