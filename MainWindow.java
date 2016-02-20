import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import net.proteanit.sql.DbUtils;

import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MainWindow {

	public JFrame frame;
	private String currentUser;
	private JTable table;
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	private String grade;
	private String savedCourses;
	private ArrayList<String> savedCoursesList;
	private JTextField textField;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	public void update_table(){
		try{
			currentUser = LoginWindow.getUsername();
			String query = "Select DISTINCT CourseName from Catalog where CourseNumber IN"
					+ "(Select CurrentCourseID from CurrentCourses where Username = '" + currentUser + "')";
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
		currentUser = LoginWindow.getUsername();
		conn = javaconnect.ConnecrDb();
		update_table();
	}
	
	


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		savedCoursesList = new ArrayList<String>();
		frame = new JFrame();
		frame.setBounds(100, 100, 534, 527);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 132, 22);
		frame.getContentPane().add(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		//when user clicks log out. It goes back to the login window
		JMenuItem mntmLogOut = new JMenuItem("Log Out");
		mntmLogOut.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				LoginWindow lw = new LoginWindow();
				frame.dispose();
				lw.frame.setVisible(true);
			}
		});
		mnFile.add(mntmLogOut);
		
		//when user clicks exit. Exits the program
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		
		//when user clicks change password
		JButton btnChangePassword = new JButton("Change Password");
		btnChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChangePasswordWindow cpw = new ChangePasswordWindow();
				frame.dispose();
				cpw.frame.setVisible(true);
			}
		});
		btnChangePassword.setBounds(6, 57, 147, 29);
		frame.getContentPane().add(btnChangePassword);
		
		//view the transcript
		JButton btnTranscript = new JButton("Transcript");
		btnTranscript.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TranscriptWindow tw = new TranscriptWindow();
				tw.frame.setVisible(true);
			}
		});
		btnTranscript.setBounds(6, 131, 147, 29);
		frame.getContentPane().add(btnTranscript);
		
		JButton btnCourseCatalog = new JButton("Course Catalog");
		//when user clicks on the course catalog, it displays all the courses from within the database
		btnCourseCatalog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CatalogWindow cw = new CatalogWindow();
				cw.frame.setVisible(true);
			}
		});
		btnCourseCatalog.setBounds(6, 215, 147, 29);
		frame.getContentPane().add(btnCourseCatalog);
		
		JButton btnSearch = new JButton("Search Courses");
		//when user searches for the courses
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchWindow sw = new SearchWindow();
				sw.frame.setVisible(true);
			}
		});
		btnSearch.setBounds(6, 300, 147, 29);
		frame.getContentPane().add(btnSearch);
		
		JButton btnSimulateSemester = new JButton("Simulate to next Semester");
		btnSimulateSemester.setForeground(UIManager.getColor("Menu.selectionBackground"));
		btnSimulateSemester.setBackground(new Color(48, 131, 251));
		//this button simulates to next semester. Adds the current courses to previous courses and auto generates a grade for those course
		btnSimulateSemester.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					String insertIntoTranscriptQuery = "INSERT INTO PreviousCourses(Username, PrevCoursesID, Grade) "
							+ "SELECT Username, CurrentCourseID,Grade FROM CurrentCourses"
							+ " WHERE USERNAME = '" + currentUser + "'";
					stmt = conn.prepareStatement(insertIntoTranscriptQuery);
					stmt.execute();
					//System.out.println(insertIntoTranscriptQuery);
					JOptionPane.showMessageDialog(null, "Successful");
					//query from deleting from current courses
						//String insertIntoTranscriptQuery = "INSERT INTO PreviousCourses(PrevCoursesID, Grade)"
							//	+ " values ((SELECT CurrentCourseID FROM CurrentCourses "
								//+ "WHERE USERNAME = '" + currentUser + "')," + grade + "')";
					String deletequery = "Delete FROM CurrentCourses WHERE Username = '" + currentUser + "'";
					stmt = conn.prepareStatement(deletequery);
					stmt.execute();
					//System.out.println(savedCoursesList.toString());
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
		btnSimulateSemester.setBounds(314, 459, 200, 29);
		btnSimulateSemester.setBackground(Color.YELLOW);
		btnSimulateSemester.setOpaque(true);
		frame.getContentPane().add(btnSimulateSemester);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(227, 104, 287, 214);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("RadioButtonMenuItem.selectionBackground"));
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, Color.BLUE, null, null));
		panel.setBounds(228, 73, 238, 29);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblCurrentClassSchedule = new JLabel("Current Class Schedule: ");
		lblCurrentClassSchedule.setForeground(UIManager.getColor("ScrollBar.background"));
		lblCurrentClassSchedule.setBounds(6, 6, 153, 16);
		panel.add(lblCurrentClassSchedule);
		
		JButton btnRefreshSchedule = new JButton("Refresh Schedule");
		btnRefreshSchedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update_table();
			}
		});
		btnRefreshSchedule.setBounds(223, 43, 159, 29);
		frame.getContentPane().add(btnRefreshSchedule);
		
		JButton btnTeacherStats = new JButton("Teacher Statistics");
		btnTeacherStats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TeacherStats tw = new TeacherStats();
				tw.frame.setVisible(true);
				
			}
		});
		btnTeacherStats.setBounds(6, 381, 147, 29);
		frame.getContentPane().add(btnTeacherStats);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new javax.swing.ImageIcon("/Users/ahmedsyed/Documents/workspace/StudentProjectCS157A/src/background.jpg"));
		lblNewLabel.setBounds(0, 0, 534, 505);
		frame.getContentPane().add(lblNewLabel);
		
		
		
		
		
		
	}
}
