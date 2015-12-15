import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import com.mysql.jdbc.Statement;



public class Main extends LogInView {
	static JFrame frame, homeScreenFrame, editProfileFrame;
	static ArrayList<String> userArrayList = new ArrayList<String>();
	
	public static void main(String[] args) {
		frame = new Main();
		frame.setVisible(true);
		
	}
	
	public static void createUserArrayList(){
		
		try {
			
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/dbtest", "root", "123");
			
			Statement st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery("Select username from user;");
			
			while (rs.next()) {
				
				String name = rs.getString("username");
				
				userArrayList.add(name);
				
			}
			
			System.out.println(userArrayList);
			
			st.close();
			conn.close();
			
		} catch (Exception e) {
			
			
		}
		
	}
	
	public Main() {
		createUserArrayList();
	
	}
	
}

class LowercaseFilter extends DocumentFilter {
	
	public void insertString(DocumentFilter.FilterBypass fb, int offset,
		      String text, javax.swing.text.AttributeSet attr) throws BadLocationException {
		    fb.insertString(offset, text.toLowerCase(), attr);
		  
	}
	
	public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
		      String text, javax.swing.text.AttributeSet attr) throws BadLocationException {
		    fb.replace(offset, length, text.toLowerCase(), attr);
		  
	}
	
}
