package ListAllDB;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JLabel;
import javax.swing.JEditorPane;


public class ChooseTargetDB extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	//public static JButton btnNewButton_1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChooseTargetDB frame = new ChooseTargetDB();
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
	public ChooseTargetDB() {
		//final JFrame f = new JFrame();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 547, 419);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(106, 193, 248, 22);
		contentPane.add(textField);
		textField.setColumns(10);
		JLabel lblTargetDb = new JLabel("Target DB :");
		lblTargetDb.setBounds(10, 197, 115, 14);
		contentPane.add(lblTargetDb);
		
		final JButton btnNewButton = new JButton("choose target");
		btnNewButton.setBounds(366, 188, 128, 33);
		contentPane.add(btnNewButton);
		
		btnNewButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
			    //chooser.setDialogTitle(choosertitle);
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    //
			    // disable the "All files" option.
			    //
			    chooser.setAcceptAllFileFilterUsed(false);
			    //    
			    if (chooser.showOpenDialog(btnNewButton) == JFileChooser.APPROVE_OPTION) { 
			      System.out.println("getCurrentDirectory(): " 
			         +  chooser.getCurrentDirectory());
			      
			      System.out.println("getSelectedFile() : " 
			         +  chooser.getSelectedFile());
			      textField.setText(chooser.getSelectedFile().toString());
			      StartPage.path = chooser.getSelectedFile().toString();
			      }
			    else {
			      System.out.println("No Selection ");
			      }
			}
		});
		
		JButton btnNewButton_1 = new JButton("Apply");
		btnNewButton_1.setBounds(171, 248, 162, 50);
		contentPane.add(btnNewButton_1);
		
		ImageIcon image = new ImageIcon("C:/Users/jais/Downloads/titan.png");
		JLabel lblNewLabel = new JLabel(image);
		lblNewLabel.setBounds(132, 11, 222, 146);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("<html>Panel Window for <br>Database Creations</html>");
		lblNewLabel_1.setBounds(366, 11, 155, 122);
		contentPane.add(lblNewLabel_1);
		
		btnNewButton_1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(StartPage.path != null || StartPage.path != "") {
					StartPage.dbList.clear();
					System.out.println("masuk halaman choose target");
					BufferedReader br = null;
					try {
						System.out.println("masuk halaman try choose target");
						String dbName;
						String dirPath = StartPage.path.replace("\\","\\\\");
						String filePath = dirPath+"\\"+"jaisDB.text";
						br = new BufferedReader(new FileReader(filePath));
						
						System.out.println("ukuran dbList = "+StartPage.dbList.size());
						while((dbName = br.readLine())!= null) {
							System.out.println(dbName);
							StartPage.dbList.add(dbName);
						}
					}catch(Exception ex) {
						//ex.printStackTrace();
						JOptionPane.showMessageDialog(null,"There No Database In the Current Directory: "+StartPage.path);
					}
				}
				dispose();
			}
		});
	}
	
}
