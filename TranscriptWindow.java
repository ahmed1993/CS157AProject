import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class TranscriptWindow {

	public JFrame frame;
	Connection conn = null;
	PreparedStatement stmt = null;
	PreparedStatement stmt2 = null;
	ResultSet rs = null;
	ResultSet rs2 = null;
	private String currentUser;
	private JTable table;
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TranscriptWindow window = new TranscriptWindow();
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
	public TranscriptWindow() {
		initialize();
		conn = javaconnect.ConnecrDb();
		currentUser = LoginWindow.getUsername();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 580);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(37, 514, 380, -425);
		frame.getContentPane().add(scrollPane);
		
		JButton btnLoadTranscript = new JButton("Load Transcript");
		//when user clicks the load transcript button, it loads their previous courses from the prev course table
		btnLoadTranscript.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					//String query = "Select CourseName, Grade from Catalog, PreviousCourses where ID in("
						//	+ "Select PrevCoursesID from PreviousCourses where Username = '" + currentUser + "')";
					String query = "Select DISTINCT CourseName from Catalog where CourseNumber in"
							+ "(Select PrevCoursesID from PreviousCourses where Username = '" + currentUser + "')";
					String query2 = "Select Grade from PreviousCourses where Username = '" + currentUser + "'";
					stmt = conn.prepareStatement(query);
					stmt2 = conn.prepareStatement(query2);
					rs = stmt.executeQuery();
					rs2 = stmt2.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					table_1.setModel(DbUtils.resultSetToTableModel(rs2));
					
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
		btnLoadTranscript.setBounds(0, 60, 160, 29);
		frame.getContentPane().add(btnLoadTranscript);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 106, 138, 429);
		frame.getContentPane().add(scrollPane_1);
		
		table = new JTable();
		scrollPane_1.setViewportView(table);
		
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
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(160, 106, 138, 429);
		frame.getContentPane().add(scrollPane_2);
		
		table_1 = new JTable();
		scrollPane_2.setViewportView(table_1);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new javax.swing.ImageIcon("/Users/ahmedsyed/Documents/workspace/StudentProjectCS157A/src/background.jpg"));

		lblNewLabel.setBounds(0, 0, 450, 558);
		frame.getContentPane().add(lblNewLabel);
	}
}
