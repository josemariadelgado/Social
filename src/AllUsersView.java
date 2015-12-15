
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
	JButton showUserButton;
	MouseListener openShowUserView, enableShowUserButton;
	
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
	
	public AllUsersView() {
		
		openShowUserView = new MouseAdapter() {
			
			public void mouseClicked(MouseEvent arg0) {
				
				Main.showUserFrame = new ShowUserView();
			}
		};
		
		enableShowUserButton = new MouseAdapter() {
			
			public void mouseClicked(MouseEvent arg0) {
				
				showUserButton.setEnabled(true);
				showUserButton.removeMouseListener(openShowUserView);
				showUserButton.addMouseListener(openShowUserView);
				
			}
		};
		
		setBounds(850, 200, 400, 550);
		setTitle("All Users");
		setVisible(true);
		
		panel = new JPanel();
		panel.setLayout(null);
		add(panel);
		
		loadAllUsers();
		
		allUsersList = new JList(allUsersListModel);
		allUsersList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		allUsersList.setFont(new Font("Ubuntu", 0, 18));
		allUsersList.addMouseListener(enableShowUserButton);
		allUsersScrollPane = new JScrollPane(allUsersList);
		allUsersScrollPane.setBounds(0, 0, 400, 350);
		allUsersScrollPane.setViewportBorder(null);
		panel.add(allUsersScrollPane);
		
		showUserButton = new JButton("Show User");
		showUserButton.setBounds(5, 360, 100, 30);
		showUserButton.setFocusable(false);
		showUserButton.setEnabled(false);
		panel.add(showUserButton);
		
	}

}
