import java.awt.EventQueue;
import java.awt.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

public class SearchWindow {

	public JFrame frame;
	private JTextField searchtextField;
	Connection conn = null;
	Connection conn2 = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	PreparedStatement stmt2 = null;
	PreparedStatement stmt3 = null;
	ResultSet rs2 = null;
	ResultSet rs3 = null;
	private JTable table;
	private int courseID = 10;
	private String currentUser;
	private JButton btnEnrollInSelected;
	private int prereq;
	private String grade;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchWindow window = new SearchWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//auto-generate grades
	private void generateGrades(){
		Random randomGenerator = new Random();
		//range from 0...100
		int randomInt = randomGenerator.nextInt(101);
		if(randomInt >= 70){
			grade = "A";
		}
		else if(randomInt >= 40){
			grade = "B";
		}
		else if(randomInt >= 20){
			grade = "C";
		}
		else if(randomInt >= 5){
			grade = "D";
		}
		else if(randomInt <= 3){
			grade = "F";
		}
		
	}
	
	public String getGrade(){
		generateGrades();
		return grade;
	}
	/**
	 * Create the application.
	 */
	public SearchWindow() {
		initialize();
		conn = javaconnect.ConnecrDb();
		conn2 = javaconnect.ConnecrDb();
		currentUser = LoginWindow.getUsername();
	}
	
	public void validateEnroll(){
		try{
			int selectedRowIndex = table.getSelectedRow();
			courseID = (int)table.getModel().getValueAt(selectedRowIndex, 0);
			String checkPreReq = "Select PrevCoursesID, Grade from PreviousCourses where "
					+ "Grade <= 'D' AND "
					+ "PrevCoursesID = (Select PreReq from Catalog where ID = '" + courseID + "') AND"
					+ "Username = '" + currentUser + "'";
			System.out.println(checkPreReq);
			stmt = conn.prepareStatement(checkPreReq);
			rs = stmt.executeQuery();
			if(rs.next()){
				btnEnrollInSelected.setEnabled(true);
			}
	}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 629, 489);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		searchtextField = new JTextField();
		searchtextField.setBounds(6, 32, 263, 28);
		frame.getContentPane().add(searchtextField);
		searchtextField.setColumns(10);
		
		JButton btnSearchClassesBy = new JButton("Search Classes by Professor");
		//if user searches by professor, it lists out all the classes being taught by that professor
		btnSearchClassesBy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String query = "Select CourseNumber, CourseName, CourseDsc, PreReq from Catalog where CourseNumber IN "
							+ "(Select CourseNumber from Professor where ProfessorName = '" 
							+ searchtextField.getText() + "' )";
					stmt = conn.prepareStatement(query);
					rs = stmt.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
				}
				catch(Exception ex){
					//ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Professor doesn't exist in the database");
				}
				
			}
		});
		btnSearchClassesBy.setBounds(6, 72, 263, 29);
		frame.getContentPane().add(btnSearchClassesBy);
		
		JButton btnSearchClassesBy_1 = new JButton("Search Classes by Course Number");
		btnSearchClassesBy_1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					String query = "Select CourseNumber, CourseName, CourseDsc, PreReq"
							+ " from Catalog where CourseNumber = " + searchtextField.getText();
					stmt = conn.prepareStatement(query);
					rs = stmt.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
				}
				catch(Exception ex){
					//ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Not a valid course number");
				}
				
			}
		});
		btnSearchClassesBy_1.setBounds(6, 125, 263, 29);
		frame.getContentPane().add(btnSearchClassesBy_1);
		
		JButton btnSearchClassesBy_2 = new JButton("Search Classes by Course Name");
		btnSearchClassesBy_2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					String query = "Select CourseNumber, CourseName, CourseDsc, PreReq"
							+ " from Catalog where CourseName = '" + searchtextField.getText() + "'";
					//System.out.println(query);
					stmt = conn.prepareStatement(query);
					rs = stmt.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
				}
				catch(Exception ex){
					//ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Course doesn't exist in the database");
				}
				
			}
		});
		btnSearchClassesBy_2.setBounds(6, 179, 263, 29);
		frame.getContentPane().add(btnSearchClassesBy_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(294, 39, 313, 405);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnClearResults = new JButton("Clear Results");
		//clear the table
		btnClearResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel tableM = (DefaultTableModel)table.getModel();
				tableM.setRowCount(0);
			}
		});
		btnClearResults.setBounds(292, 6, 117, 29);
		frame.getContentPane().add(btnClearResults);
		
		btnEnrollInSelected = new JButton("Enroll in Selected Course");
		//enroll in a selected course.
		btnEnrollInSelected.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					ArrayList<ResultSet> list = new ArrayList<>();
					int selectedRowIndex = table.getSelectedRow();
					courseID = (int)table.getModel().getValueAt(selectedRowIndex, 0);
					prereq = (int)table.getModel().getValueAt(selectedRowIndex, 3);
					//see if course doesnt already exist and has a grade <= D
					String ifCourseDoesntAlreadyExistInDB = "Select PrevCoursesID from PreviousCourses where Grade <= 'D'"
							+ " AND PrevCoursesID = '" + courseID + "'"
							+ " AND Username = '" + currentUser +"'";
					stmt2 = conn.prepareStatement(ifCourseDoesntAlreadyExistInDB);
					rs2 = stmt2.executeQuery();
					/**String checkPreReq = "Select PrevCoursesID, Grade from PreviousCourses where "
							+ "Grade <= 'D' AND "
							+ "PrevCoursesID IN (Select PreReq from Catalog where CourseNumber = '" + courseID + "') AND"
							+ " Username = '" + currentUser + "'";*/
					String checkPreReq = "SELECT Catalog.PreReq, PreviousCourses.Grade FROM Catalog "
							+ "LEFT OUTER JOIN PreviousCourses ON Catalog.PreReq = PreviousCourses.PrevCoursesID AND "
							+ "PreviousCourses.Grade <='D' AND PreviousCourses.Username = '" + currentUser + "'"
									+ " AND catalog.coursenumber='" + courseID + "'";
					stmt = conn.prepareStatement(checkPreReq);
					rs = stmt.executeQuery();
					String checkQuery = "Select PreReq from Catalog where"
							+ "CourseNumber= '" + courseID + "'"
									+ "AND PreReq IN (Select PrevCourseID from PreviousCourses where PrevCourseID= '"
							+prereq + "'  AND Username = '" + currentUser + "')";
					//stmt3 = conn.prepareStatement(checkQuery);
					//rs3 = stmt3.executeQuery();
					if(rs.next() || prereq == 0)
					{					
						String prevGrade = rs.getString("PreviousCourses.Grade");
						int strPreReq = rs.getInt("Catalog.PreReq");			
						try{
							
							//if the user has already taken the course before with a passing grade
							if(rs2.next()){
								JOptionPane.showMessageDialog(null, "You have already taken this course before");
							}
							else if(prereq != 0 && prevGrade==null){
								JOptionPane.showMessageDialog(null, "You do not meet the necessary pre-requisites");
							}
							else{
								String query = "INSERT INTO CURRENTCOURSES (Username, CurrentCourseID, Grade) values(?,?,?)";
								stmt = conn.prepareStatement(query);
								stmt.setString(1, currentUser);
								stmt.setInt(2, courseID);
								stmt.setString(3, getGrade());
								stmt.execute();
								JOptionPane.showMessageDialog(null, "Enrolled Successfully");
							}
						}
						catch(Exception ex)
						{
							JOptionPane.showMessageDialog(null, "You have already taken this course before");
						}
						
					}
					else{
						JOptionPane.showMessageDialog(null, "You cannot enroll in this course because you do not meet the "
								+ "necessary requirements");
					}
				}
				catch(Exception ex){
					ex.printStackTrace();
					//JOptionPane.showMessageDialog(null, "Either you have already taken this course previously and passed"
						//	+ " or you are current enrolled in this course");
					JOptionPane.showMessageDialog(null, "Label: " + prereq);

				}
			}
		});
		btnEnrollInSelected.setBounds(421, 6, 186, 29);
		frame.getContentPane().add(btnEnrollInSelected);
		
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
		lblNewLabel.setBounds(0, 0, 629, 467);
		frame.getContentPane().add(lblNewLabel);
	}

}
