import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;

public class LoginWindow {

	public JFrame frame;
	private JTextField usernameField;
	private JPasswordField passwordField;
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	public static String currentUser;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow window = new LoginWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginWindow() {
		initialize();
		conn = javaconnect.ConnecrDb();
	}
	
	public static String getUsername(){
		return currentUser;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel usernameLabel = new JLabel("User Name:");
		usernameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		usernameLabel.setBounds(6, 94, 117, 16);
		frame.getContentPane().add(usernameLabel);
		
		JLabel passwordLabel = new JLabel("Password: ");
		passwordLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		passwordLabel.setBounds(6, 152, 117, 16);
		frame.getContentPane().add(passwordLabel);
		
		JPanel pictureContainer = new JPanel();
		pictureContainer.setBounds(273, 79, 158, 133);
		frame.getContentPane().add(pictureContainer);
		
		usernameField = new JTextField();
		usernameField.setBounds(100, 89, 161, 28);
		frame.getContentPane().add(usernameField);
		usernameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(100, 147, 161, 28);
		frame.getContentPane().add(passwordField);
		//when user clicks on login
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String query = "select username, password from students where username = ?"
						+ "and password = ?";
				try{
					stmt = conn.prepareStatement(query);
					stmt.setString(1, usernameField.getText());
					stmt.setString(2, passwordField.getText());
					rs = stmt.executeQuery();
					//if username and password are correct
					if(rs.next()){
						currentUser = usernameField.getText();
						JOptionPane.showMessageDialog(null, "Successful");
						frame.dispose();
						MainWindow mw = new MainWindow();
						mw.frame.setVisible(true);
					}
					else{
						JOptionPane.showMessageDialog(null, "Username/Password is not correct");
					}
				}
				catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}
				finally{
					try{
						rs.close();
						stmt.close();
					}
					catch(Exception ex2){
						JOptionPane.showMessageDialog(null, ex2);
					}
				}
			}
		});
		loginButton.setBounds(6, 198, 117, 29);
		frame.getContentPane().add(loginButton);
		
		
		JButton registerButton = new JButton("Register");
		
		//if the user clicks on register
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterWindow rw = new RegisterWindow();
				rw.frame.setVisible(true);
			}
		});
		registerButton.setBounds(135, 198, 117, 29);
		frame.getContentPane().add(registerButton);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(6, 0, 138, 22);
		frame.getContentPane().add(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		//when user clicks exit. Exits the program
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
	}
}
