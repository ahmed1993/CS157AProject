import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.proteanit.sql.DbUtils;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class TeacherStats {

	public JFrame frame;
	private JTextField textField;
	private JTable table;
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement stmt = null;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeacherStats window = new TeacherStats();
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
	public TeacherStats() {
		initialize();
		conn = javaconnect.ConnecrDb();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 195, 211);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblEnterProfessorsName = new JLabel("Enter Professor's Name:");
		lblEnterProfessorsName.setBackground(Color.BLACK);
		lblEnterProfessorsName.setForeground(Color.WHITE);
		lblEnterProfessorsName.setBounds(26, 29, 174, 16);
		frame.getContentPane().add(lblEnterProfessorsName);
		
		textField = new JTextField();
		textField.setBounds(26, 57, 134, 28);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 136, 128, 47);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnGo = new JButton("Go");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String query = "Select Count(*) as CoursesTeaching from Professor where ProfessorName = '" + textField.getText() + "'";
					stmt = conn.prepareStatement(query);
					rs = stmt.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
				}
				catch(Exception ex){
					//ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Enter a valid professor name");
				}
			}
		});
		btnGo.setBounds(60, 86, 59, 29);
		frame.getContentPane().add(btnGo);
		
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
		lblNewLabel.setBounds(0, -21, 200, 210);
		frame.getContentPane().add(lblNewLabel);
	}
}
