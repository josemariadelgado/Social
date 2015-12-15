
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.swing.*;

import com.mysql.jdbc.Statement;

public class AllUsersView extends JFrame {
	JPanel panel;
	JList allUsersList;
	DefaultListModel allUsersListModel;
	JScrollPane allUsersScrollPane;
	
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
					
					allUsersListModel.addElement(nameLastNameUsername);
				}
				
			}
			
			System.out.println("\nShowing all users:\n" + allUsersListModel);
			
			st.close();
			conn.close();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			
		}
		
	}
	
	
	public AllUsersView() {
		
		setBounds(800, 200, 400, 550);
		setTitle("All Users");
		setVisible(true);
		
		panel = new JPanel();
		panel.setLayout(null);
		add(panel);
		
		loadAllUsers();
		
		allUsersList = new JList(allUsersListModel);
		allUsersList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		allUsersList.setFont(new Font("Ubuntu", 0, 18));
		allUsersScrollPane = new JScrollPane(allUsersList);
		allUsersScrollPane.setBounds(0, 0, 400, 350);
		allUsersScrollPane.setViewportBorder(null);
		panel.add(allUsersScrollPane);
		
		
	}

}
