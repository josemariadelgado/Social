
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;

public class ShowUserView extends JFrame{
	JPanel panel;
	JLabel nameLabel, lastNameLabel, usernameLabel, phoneNumberLabel, addressLabel;
	
	public void getUserData() {
		
		String username = (String) AllUsersView.allUsersList.getSelectedValue();
		
		try {
			
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/dbtest", "root", "123");
			
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("Select * from user where username = '" + username + "';");
			
			if (rs.next()) {
				
				int id = rs.getInt("userId");
				String name = rs.getString("name");
				String lastName = rs.getString("lastName");
				String phoneNumber = rs.getString("phone");
				String address = rs.getString("address");
				
				nameLabel.setText(name);
				lastNameLabel.setText(lastName);
				usernameLabel.setText(username);
				phoneNumberLabel.setText(phoneNumber);
				addressLabel.setText(address);
				
			}
			
			st.close();
			conn.close();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			
		}
		
	}
	
	public ShowUserView() {
		
		setBounds(850, 200, 300, 300);
		setVisible(true);
		
		panel = new JPanel();
		panel.setLayout(null);
		add(panel);
		
		nameLabel = new JLabel("Name");
		lastNameLabel = new JLabel("Last Name");
		usernameLabel = new JLabel("username");
		phoneNumberLabel = new JLabel("012345678");
		addressLabel = new JLabel("Address");
		nameLabel.setFont(new Font("Ubuntu", 0, 25));
		nameLabel.setBounds(40, 20, 200, 30);
		lastNameLabel.setBounds(40, 45, 200, 30);
		usernameLabel.setBounds(54, 70, 200, 30);
		phoneNumberLabel.setBounds(40, 95, 200, 30);
		addressLabel.setBounds(40, 120, 200, 30);
		JLabel atLabel = new JLabel("@");
		atLabel.setBounds(40, 70, 20, 30);
		panel.add(atLabel);
		panel.add(nameLabel);
		panel.add(lastNameLabel);
		panel.add(usernameLabel);
		panel.add(phoneNumberLabel);
		panel.add(addressLabel);
		
		getUserData();
		
		
	}

}
