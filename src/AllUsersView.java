
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.swing.*;

import com.mysql.jdbc.Statement;

public class AllUsersView extends JFrame {
	JPanel panel;
	static JList allUsersList;
	DefaultListModel allUsersListModel;
	JScrollPane allUsersScrollPane;
	JLabel nameLabel, lastNameLabel, usernameLabel, phoneNumberLabel, addressLabel;
	MouseListener showUser;
	
	public void loadAllUsers() {
		
		try {
			
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/dbtest", "root", "123"); 
			Statement st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery("Select * from user;");
			
			allUsersListModel = new DefaultListModel();
			
			while (rs.next()) {
				
				String name = rs.getString("name");
				String lastName = rs.getString("lastName");
				String username = rs.getString("username");
				String nameLastNameUsername = name + " " + lastName + "  @" + username;
				
				if (!username.equals(HomeScreenView.usernameLabel.getText())) {
					
					allUsersListModel.addElement(username);
					
				}
				
			}
			
			System.out.println("\nShowing all users:\n" + allUsersListModel);
			
			st.close();
			conn.close();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			
		}
		
	}
	
	public void getUserData() {
		
		String username = (String) AllUsersView.allUsersList.getSelectedValue();
		
		try {
			
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/dbtest", "root", "123");
			
			Statement st = (Statement) conn.createStatement();
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
	
	public AllUsersView() {
		
		showUser = new MouseAdapter() {
			
			public void mouseClicked(MouseEvent arg0) {
				
				getUserData();
				Main.allUsersFrame.setSize(750, 350);
				Main.allUsersFrame.setTitle("All Users - @" + allUsersList.getSelectedValue());
				
			}
		};
		
		setBounds(650, 250, 400, 350);
		setTitle("All Users");
		setVisible(true);
		
		panel = new JPanel();
		panel.setLayout(null);
		add(panel);
		
		loadAllUsers();
		
		allUsersList = new JList(allUsersListModel);
		allUsersList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		allUsersList.setFont(new Font("Ubuntu", 0, 18));
		allUsersList.addMouseListener(showUser);
		allUsersScrollPane = new JScrollPane(allUsersList);
		allUsersScrollPane.setBounds(0, 0, 400, 350);
		allUsersScrollPane.setViewportBorder(null);
		panel.add(allUsersScrollPane);
		
		nameLabel = new JLabel();
		lastNameLabel = new JLabel();
		usernameLabel = new JLabel();
		phoneNumberLabel = new JLabel();
		addressLabel = new JLabel();
		nameLabel.setFont(new Font("Ubuntu", 0, 25));
		nameLabel.setBounds(440, 20, 200, 30);
		lastNameLabel.setBounds(440, 45, 200, 30);
		usernameLabel.setBounds(454, 70, 200, 30);
		phoneNumberLabel.setBounds(440, 95, 200, 30);
		addressLabel.setBounds(440, 120, 200, 30);
		JLabel atLabel = new JLabel("@");
		atLabel.setBounds(440, 70, 20, 30);
		panel.add(atLabel);
		panel.add(nameLabel);
		panel.add(lastNameLabel);
		panel.add(usernameLabel);
		panel.add(phoneNumberLabel);
		panel.add(addressLabel);
		
	}

}
