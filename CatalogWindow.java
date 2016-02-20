import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTable;

import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class CatalogWindow {

	public JFrame frame;
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	private JTable table;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CatalogWindow window = new CatalogWindow();
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
	public CatalogWindow() {
		initialize();
		conn = javaconnect.ConnecrDb();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 541, 542);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 37, 519, 466);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnLoadCatalog = new JButton("Load Catalog");
		//if the user clicks on load catalog
		btnLoadCatalog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String query = "Select CourseNumber, CourseName, CourseDsc, PreReq from Catalog";
					stmt = conn.prepareStatement(query);
					rs = stmt.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
		btnLoadCatalog.setBounds(189, 6, 117, 29);
		frame.getContentPane().add(btnLoadCatalog);
		
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
		lblNewLabel.setBounds(0, -20, 541, 540);
		frame.getContentPane().add(lblNewLabel);
	}

}
