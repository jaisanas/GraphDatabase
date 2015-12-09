package ListAllDB;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;


public class CreateNewDB extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateNewDB frame = new CreateNewDB();
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
	public CreateNewDB() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 563, 433);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\jais\\Downloads\\titan.png"));
		lblNewLabel.setBounds(147, 11, 258, 205);
		contentPane.add(lblNewLabel);
		
		JLabel lblDbName = new JLabel("DB Name  :");
		lblDbName.setBounds(35, 230, 69, 14);
		contentPane.add(lblDbName);
		
		textField = new JTextField();
		textField.setBounds(114, 227, 385, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Creates");
		btnNewButton.setBounds(212, 284, 169, 69);
		contentPane.add(btnNewButton);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.out.println(textField.getText());
				String dirPath = StartPage.path.replace("\\","\\\\");
				String filePath = dirPath+"\\"+"jaisDB.text";
				File dirPathDB = new File(dirPath+"\\"+textField.getText());
				if(StartPage.path != null)
				{
				 
				 /*if(StartPage.dbList != null) {*/
					 //jika dblist tidak null
					 if(!dirPathDB.exists()) 
					  {
							dirPathDB.mkdir();
						try{
							File file = new File(filePath);
							if (!file.exists()) {
							file.createNewFile();
							}
						
						FileWriter fw = new FileWriter(file.getAbsoluteFile());
						BufferedWriter bw = new BufferedWriter(fw);
						StartPage.dbList.add(textField.getText());
						for(int i = 0; i < StartPage.dbList.size(); i++) {
							bw.write(StartPage.dbList.get(i));
							bw.newLine();
						}
						
						bw.close();
						JOptionPane.showMessageDialog(null,"Succeed to create database "+textField.getText()+" in "+StartPage.path);
						}catch(Exception ex) {
							ex.printStackTrace();
						}
					
					 }
					 else 
					 {
						 JOptionPane.showMessageDialog(null,"Failed to create database "+textField.getText()+" in "+StartPage.path);
					 } 
				 /*}else {
					 //jika dblist null
					 
				 }*/
				 
				}
				else 
				{
			      JOptionPane.showMessageDialog(null,"You must choose db target");
				}
				dispose();
			}
		});
	}

}
