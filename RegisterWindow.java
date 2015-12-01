import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;

import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class RegisterWindow {

	public JFrame frame;
	private JTextField regusernameField;
	private JPasswordField regpasswordField;
	private JTextField regfirstnameField;
	private JTextField reglastnameField;
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterWindow window = new RegisterWindow();
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
	public RegisterWindow() {
		initialize();
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
		
		JLabel regusernameLabel = new JLabel("User Name: ");
		regusernameLabel.setBounds(6, 57, 76, 39);
		frame.getContentPane().add(regusernameLabel);
		
		JLabel regpasswordLabel = new JLabel("Password: ");
		regpasswordLabel.setBounds(6, 108, 76, 16);
		frame.getContentPane().add(regpasswordLabel);
		
		JLabel regfirstnameLabel = new JLabel("First Name:");
		regfirstnameLabel.setBounds(6, 147, 76, 16);
		frame.getContentPane().add(regfirstnameLabel);
		
		JLabel reglastnameLabel = new JLabel("Last Name:");
		reglastnameLabel.setBounds(6, 186, 76, 16);
		frame.getContentPane().add(reglastnameLabel);
		
		regusernameField = new JTextField();
		regusernameField.setBounds(94, 62, 141, 28);
		frame.getContentPane().add(regusernameField);
		regusernameField.setColumns(10);
		
		regpasswordField = new JPasswordField();
		regpasswordField.setBounds(94, 102, 141, 28);
		frame.getContentPane().add(regpasswordField);
		
		regfirstnameField = new JTextField();
		regfirstnameField.setBounds(94, 141, 141, 28);
		frame.getContentPane().add(regfirstnameField);
		regfirstnameField.setColumns(10);
		
		reglastnameField = new JTextField();
		reglastnameField.setBounds(94, 180, 141, 28);
		frame.getContentPane().add(reglastnameField);
		reglastnameField.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBounds(269, 57, 175, 160);
		frame.getContentPane().add(panel);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 132, 22);
		frame.getContentPane().add(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		//when user clicks the go back button
		JMenuItem mntmLogOut = new JMenuItem("Go Back");
		mntmLogOut.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				LoginWindow lw = new LoginWindow();
				frame.dispose();
				lw.frame.setVisible(true);
			}
		});
		mnFile.add(mntmLogOut);
		
		//when user clicks the exit button
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		
		JButton registerButton = new JButton("Register");
		
		//when the user clicks the register button
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(regusernameField.getText().equals("")||
						regpasswordField.getText().equals("") ||
						regfirstnameField.getText().equals("") ||
						reglastnameField.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Please fill out all the fields");
				}
				else{
					try{
						String query = "Insert into students (username, password, first_name, last_name) values(?,?,?,?)";
						stmt = conn.prepareStatement(query);
						stmt.setString(1, regusernameField.getText());
						stmt.setString(2, regpasswordField.getText());
						stmt.setString(3, regfirstnameField.getText());
						stmt.setString(4, reglastnameField.getText());
						//stmt.setInt(5, 0);
						//stmt.setInt(6, 0);
						stmt.execute();
						JOptionPane.showMessageDialog(null, "Registered successfully!");
						frame.dispose();
						
					}
					catch(Exception ex){
						JOptionPane.showMessageDialog(null, "That username is already taken");
					}
					finally{
						try{
							rs.close();
							stmt.close();
						}
						catch(Exception ex){
							JOptionPane.showMessageDialog(null, ex);
						}
					}
					
				}
			}
		});
		registerButton.setBounds(15, 228, 117, 29);
		frame.getContentPane().add(registerButton);
	}
}
