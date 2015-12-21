package ListAllDB;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JList;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;


public class StartPage extends JFrame {

	private JPanel contentPane;
	public static String path;
	public static String namaGraph;
	public static String gFilePath;
	public static String activeDB;
	public static ArrayList<String> dbList;
	public static ArrayList<String> graphList;
	public static GraphIndex gpi;
	public static String dbPath;
	public static GraphTreeIndex graphTreeIndex;
	public static GraphTreeListIndex  graphTreeListIndex;
	public static GraphIndexDesc gIndexDesc;
	public static NVertexEachGraph nVertex;
	public static Double tHold;
	public static String ModeSearching;
	public static String ModeSimilarity;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartPage frame = new StartPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StartPage() {
		setForeground(Color.LIGHT_GRAY);
		dbList = new ArrayList();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 478, 377);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		ImageIcon image = new ImageIcon("C:/Users/jais/Downloads/titan.png");
		contentPane.setLayout(null);
		JLabel lblNewLabel = new JLabel(image);
		lblNewLabel.setBounds(10, 56, 191, 143);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("<html>Simple Graph Database for Graph Visualization <br> and Information Retrieval Analysis</html>");
		lblNewLabel_1.setBounds(10, 210, 191, 98);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Create New Graph DB");
		btnNewButton.setBackground(Color.LIGHT_GRAY);
		btnNewButton.setBounds(222, 56, 191, 90);
		contentPane.add(btnNewButton);
		
		btnNewButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(StartPage.path != null) {
				CreateNewDB frame = new CreateNewDB();
				frame.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(null,"choose DB target first");
				}
			}
		});
		
		JButton btnNewButton_1 = new JButton("Show All Graph DB");
		btnNewButton_1.setBackground(Color.LIGHT_GRAY);
		btnNewButton_1.setBounds(222, 169, 191, 90);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(path != null || path != "") {
					BufferedReader br = null;
					try {
						String dbName;
						String dirPath = path.replace("\\","\\\\");
						System.out.println(dirPath);
						ListAllDB.initListAllDB();
					}catch(Exception ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(null,"There Is No Database In the Current Directory: "+path);
					}
				}
			}
		});
		contentPane.add(btnNewButton_1);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 624, 21);
		contentPane.add(menuBar);
		
		JMenu mnNewMenu = new JMenu("Settings");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Choose DB Target");
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("About");
		mnAbout.add(mntmNewMenuItem_1);
		
		mntmNewMenuItem_1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,"<html>Aplikasi ini dibuat untuk memenuhi <br> persyaratan tugas akhir <br> dengan tujuan agar segera<br> lulus Amien :)<<html>");
			}
		});
		
		mntmNewMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				contentPane.setEnabled(false);
				ChooseTargetDB frame = new ChooseTargetDB();
				frame.setVisible(true);
			}
		});
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
