import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class ChangePasswordWindow {

	public JFrame frame;
	private JTextField newPasswordtext;
	private JTextField confirmPasswordtext;
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	private String currentUser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangePasswordWindow window = new ChangePasswordWindow();
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
	public ChangePasswordWindow() {
		initialize();
		currentUser = LoginWindow.getUsername();
		conn = javaconnect.ConnecrDb();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewPassword = new JLabel("New Password: ");
		lblNewPassword.setBounds(6, 63, 124, 16);
		frame.getContentPane().add(lblNewPassword);
		
		JLabel lblConfirmPassword = new JLabel("Confirm Password:");
		lblConfirmPassword.setBounds(6, 153, 124, 16);
		frame.getContentPane().add(lblConfirmPassword);
		
		newPasswordtext = new JTextField();
		newPasswordtext.setColumns(10);
		newPasswordtext.setBounds(130, 57, 134, 28);
		frame.getContentPane().add(newPasswordtext);
		
		confirmPasswordtext = new JTextField();
		confirmPasswordtext.setColumns(10);
		confirmPasswordtext.setBounds(130, 147, 134, 28);
		frame.getContentPane().add(confirmPasswordtext);
		
		JButton btnChangePassword = new JButton("Change Password");
		//when user clicks change password
		btnChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(	newPasswordtext.getText().equals("") ||
						confirmPasswordtext.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Please fill out all the fields");
				}
				//if the new passwords entered don't match
				else if(!(newPasswordtext.getText().equals(confirmPasswordtext.getText()))){
					JOptionPane.showMessageDialog(null, "Your passwords dont match");
				}
				else{
					try{
						String query = "UPDATE students "
								+ "set password ='" + confirmPasswordtext.getText() + 
								"' where username = '" + currentUser + "' "; 
						stmt = conn.prepareStatement(query);
						stmt.execute();
						JOptionPane.showMessageDialog(null,"Password changed successfully");
						LoginWindow lw = new LoginWindow();
						frame.dispose();
						lw.frame.setVisible(true);
					}
					catch(SQLException ex){
						JOptionPane.showMessageDialog(null,ex.getErrorCode());
					}
				}
			}
		});
		btnChangePassword.setBounds(6, 215, 142, 29);
		frame.getContentPane().add(btnChangePassword);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(6, 0, 138, 22);
		frame.getContentPane().add(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		//when user clicks exit. Exits the program
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame.dispose();
			}
		});
		mnFile.add(mntmExit);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new javax.swing.ImageIcon("/Users/ahmedsyed/Documents/workspace/StudentProjectCS157A/src/background.jpg"));
		lblNewLabel.setBounds(0, 0, 450, 278);
		frame.getContentPane().add(lblNewLabel);
	}
}
