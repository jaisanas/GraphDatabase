package ListAllDB;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class ModifyDB extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ModifyDB dialog = new ModifyDB("");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ModifyDB(final String dbName) {
		StartPage.activeDB = dbName;
		setBounds(100, 100, 564, 437);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 548, 21);
		contentPanel.add(menuBar);
		
		JMenu mnNewMenu = new JMenu("Menus");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Insert");
		mnNewMenu.add(mntmNewMenuItem);
		
		
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Show All Graph");
		mnNewMenu.add(mntmNewMenuItem_1);
		
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(StartPage.graphList.size() != 0) {
					ShowAllGraph.initListAllGraph();
					System.out.println(StartPage.graphList.size()+"banyak");
				}else {
					JOptionPane.showMessageDialog(null,"Failed to Load Graph in "+dbName);
				}
			}
		});
		
		JMenu mnNewMenu_1 = new JMenu("Update");
		menuBar.add(mnNewMenu_1);
		
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Update All");
		mnNewMenu_1.add(mntmNewMenuItem_2);
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(StartPage.graphList.size() != 0) {
					ShowAllGraph.initListAllGraph();
					System.out.println(StartPage.graphList.size()+"banyak");
				}else {
					JOptionPane.showMessageDialog(null,"Failed to Load Graph in "+dbName);
				}
			}
		});
		
		ImageIcon image = new ImageIcon("C:/Users/jais/Downloads/titan.png");
		JLabel lblNewLabel = new JLabel(image);
		lblNewLabel.setBounds(173, 32, 175, 158);
		contentPanel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(23, 245, 364, 90);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		
		
		JButton btnNewButton = new JButton("Execute");
		btnNewButton.setBounds(402, 245, 136, 36);
		contentPanel.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			 String command = textField.getText();
			 String [] partCommands = command.split(" ");
			 System.out.println(partCommands[0]);
			 if(partCommands[0].toLowerCase().equals("insert") && partCommands[1].toLowerCase().equals("graph")) {
				System.out.println("test");
				System.out.println(partCommands[2]);
				if(partCommands.length == 3) {
					if(StartPage.graphList != null) {
						try{
						
						String dirPath = StartPage.path.replace("\\","\\\\");
						String dbPath = dirPath+"\\"+dbName;
						
						String fileGraphPath = dbPath+"\\"+partCommands[2]+".mxe";
						StartPage.gFilePath = fileGraphPath;
						String fileDescPath = dbPath+"\\"+"jaisGraphDesc.text";
						File file = new File(fileDescPath);
						FileWriter fw = new FileWriter(file.getAbsoluteFile());
						BufferedWriter bw = new BufferedWriter(fw);
						
							File file1 = new File(fileGraphPath);
							if(!file1.exists()) {
								StartPage.graphList.add(partCommands[2]);
								for(int i = 0; i < StartPage.graphList.size(); i++) {
									bw.write(StartPage.graphList.get(i));
									bw.newLine();
								}
								bw.close();
								FileWriter fw1 = new FileWriter(file1.getAbsoluteFile());
								BufferedWriter bw1 = new BufferedWriter(fw1);
								bw1.write("<mxGraphModel><root><mxCell id=\"0\"/><mxCell id=\"1\" parent=\"0\"/></root></mxGraphModel>");
								bw1.close();
								JOptionPane.showMessageDialog(null,"Succeed to create graph "+textField.getText()+" in "+dbPath);
								GraphEditor.initGraphEditor(fileGraphPath);
								textField.setText("");
							}else {
								JOptionPane.showMessageDialog(null,"Failed to create graph (Graph is already exist)"+textField.getText()+" in "+dbPath);
							}
						}
						catch(Exception ex) {
							ex.printStackTrace();
						}
					}else {
						try{
							
							String dirPath = StartPage.path.replace("\\","\\\\");
							String dbPath = dirPath+"\\"+dbName;
							
							String fileGraphPath = dbPath+"\\"+partCommands[2]+".mxe";
							StartPage.gFilePath = fileGraphPath;
							String fileDescPath = dbPath+"\\"+"jaisGraphDesc.text";
							File file = new File(fileDescPath);
							FileWriter fw = new FileWriter(file.getAbsoluteFile());
							BufferedWriter bw = new BufferedWriter(fw);
							
								File file1 = new File(fileGraphPath);
								if(!file1.exists()) {
									StartPage.graphList = new ArrayList(); 
									StartPage.graphList.add(partCommands[2]);
									for(int i = 0; i < StartPage.graphList.size(); i++) {
										bw.write(StartPage.graphList.get(i));
										bw.newLine();
									}
									bw.close();
									FileWriter fw1 = new FileWriter(file1.getAbsoluteFile());
									BufferedWriter bw1 = new BufferedWriter(fw1);
									bw1.write("<mxGraphModel><root><mxCell id=\"0\"/><mxCell id=\"1\" parent=\"0\"/></root></mxGraphModel>");
									bw1.close();
									JOptionPane.showMessageDialog(null,"Succeed to create graph "+textField.getText()+" in "+dbPath);
									GraphEditor.initGraphEditor(fileGraphPath);
									textField.setText("");
								}else {
									JOptionPane.showMessageDialog(null,"Failed to create graph (Graph is already exist)"+textField.getText()+" in "+dbPath);
								}
							}
							catch(Exception ex) {
								ex.printStackTrace();
							}
					}
				}else {
					 JPanel panel = new JPanel();
					 JOptionPane.showMessageDialog(panel, "Undefined syntax query", "Error", JOptionPane.ERROR_MESSAGE);
				}
			 }else if(partCommands.length == 6) {
				 if((partCommands[0].equals("update") || partCommands[0].equals("select") ) && partCommands[1].equals("graph") 
				    && partCommands[2].equals("where") && partCommands[3].equals("name") && partCommands[4].equals("equals")){
					 if(StartPage.graphList == null) {
						 JOptionPane.showMessageDialog(null,dbName+" is empty");
					 } else {
						 String dirPath = StartPage.path.replace("\\","\\\\");
						 String dbPath = dirPath+"\\"+dbName;
						 if(StartPage.graphList.contains(partCommands[5])) {
							 String fileGraphPath = dbPath+"\\"+partCommands[5]+".mxe";
							 System.out.println(fileGraphPath);
							 GraphEditor.initGraphEditor(fileGraphPath);
							 textField.setText("");
						 }else {
							 JOptionPane.showMessageDialog(null,"No such graph"+partCommands[5]+" in "+dbPath);
						 }
					 }
				 } else if(partCommands[0].equals("delete") && partCommands[1].equals("graph") 
						    && partCommands[2].equals("where") && partCommands[3].equals("name") && partCommands[4].equals("equals")) {
					 if(StartPage.graphList == null) {
						JOptionPane.showMessageDialog(null,dbName+" is empty");
					 }else {
						 String dirPath = StartPage.path.replace("\\","\\\\");
						 String dbPath = dirPath+"\\"+dbName;
						 if(StartPage.graphList.contains(partCommands[5])) {
							String fileGraphPath = dbPath+"\\"+partCommands[5]+".mxe";
							File file = new File(fileGraphPath);
							StartPage.graphList.remove(partCommands[5]);
							boolean flag;
							flag = file.delete();
							System.out.println(flag+" "+fileGraphPath);
							if(flag) {
								try {
									String fileDescPath = dbPath+"\\"+"jaisGraphDesc.text";
									File file1 = new File(fileDescPath);
									FileWriter fw = new FileWriter(file.getAbsoluteFile());
									BufferedWriter bw = new BufferedWriter(fw);
									for(int i = 0; i < StartPage.graphList.size(); i++) {
										bw.write(StartPage.graphList.get(i));
										bw.newLine();
									}
									textField.setText("");
									JOptionPane.showMessageDialog(null,"Succeed to delete graph"+partCommands[5]+" in "+dbPath);
								}
								catch(Exception ex) {
									ex.printStackTrace();
									JOptionPane.showMessageDialog(null,"Failed to delete graph"+partCommands[5]+" in "+dbPath);
								}
							} else {
								JOptionPane.showMessageDialog(null,"Failed to delete graph"+partCommands[5]+" in "+dbPath);
							}
						 }else {
							 JOptionPane.showMessageDialog(null,"No such graph"+partCommands[5]+" in "+dbPath);
						 }
					 }
				 }
			 }
			}
		});
		
		mntmNewMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String namaGraph = textField.getText();
				String [] partCommands = namaGraph.split(" ");
				if(partCommands.length == 1) {
					if(StartPage.graphList != null) {
						try{
						
						String dirPath = StartPage.path.replace("\\","\\\\");
						String dbPath = dirPath+"\\"+dbName;
						
						String fileGraphPath = dbPath+"\\"+partCommands[0]+".mxe";
						StartPage.gFilePath = fileGraphPath;
						String fileDescPath = dbPath+"\\"+"jaisGraphDesc.text";
						File file = new File(fileDescPath);
						FileWriter fw = new FileWriter(file.getAbsoluteFile());
						BufferedWriter bw = new BufferedWriter(fw);
						
							File file1 = new File(fileGraphPath);
							if(!file1.exists()) {
								StartPage.graphList.add(partCommands[0]);
								for(int i = 0; i < StartPage.graphList.size(); i++) {
									bw.write(StartPage.graphList.get(i));
									bw.newLine();
								}
								bw.close();
								FileWriter fw1 = new FileWriter(file1.getAbsoluteFile());
								BufferedWriter bw1 = new BufferedWriter(fw1);
								bw1.write("<mxGraphModel><root><mxCell id=\"0\"/><mxCell id=\"1\" parent=\"0\"/></root></mxGraphModel>");
								bw1.close();
								JOptionPane.showMessageDialog(null,"Succeed to create graph "+textField.getText()+" in "+dbPath);
								GraphEditor.initGraphEditor(fileGraphPath);
							}else {
								JOptionPane.showMessageDialog(null,"Failed to create graph (Graph is already exist)"+textField.getText()+" in "+dbPath);
							}
						}
						catch(Exception ex) {
							ex.printStackTrace();
						}
					}else {
						try{
							
							String dirPath = StartPage.path.replace("\\","\\\\");
							String dbPath = dirPath+"\\"+dbName;
							
							String fileGraphPath = dbPath+"\\"+partCommands[0]+".mxe";
							StartPage.gFilePath = fileGraphPath;
							String fileDescPath = dbPath+"\\"+"jaisGraphDesc.text";
							File file = new File(fileDescPath);
							FileWriter fw = new FileWriter(file.getAbsoluteFile());
							BufferedWriter bw = new BufferedWriter(fw);
							
								File file1 = new File(fileGraphPath);
								if(!file1.exists()) {
									StartPage.graphList = new ArrayList(); 
									StartPage.graphList.add(partCommands[0]);
									for(int i = 0; i < StartPage.graphList.size(); i++) {
										bw.write(StartPage.graphList.get(i));
										bw.newLine();
									}
									bw.close();
									FileWriter fw1 = new FileWriter(file1.getAbsoluteFile());
									BufferedWriter bw1 = new BufferedWriter(fw1);
									bw1.write("<mxGraphModel><root><mxCell id=\"0\"/><mxCell id=\"1\" parent=\"0\"/></root></mxGraphModel>");
									bw1.close();
									JOptionPane.showMessageDialog(null,"Succeed to create graph "+textField.getText()+" in "+dbPath);
									GraphEditor.initGraphEditor(fileGraphPath);
								}else {
									JOptionPane.showMessageDialog(null,"Failed to create graph (Graph is already exist)"+textField.getText()+" in "+dbPath);
								}
							}
							catch(Exception ex) {
								ex.printStackTrace();
							}
					}
				}
				else {
					 JPanel panel = new JPanel();
					 JOptionPane.showMessageDialog(panel, "Undefined syntax query", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JLabel lblCurrebtDbIs = new JLabel("Current DB is:");
		lblCurrebtDbIs.setBounds(376, 32, 81, 14);
		contentPanel.add(lblCurrebtDbIs);
		
		JLabel lblNewLabel_1;
		if(dbName == null) {
			lblNewLabel_1 = new JLabel("null");
		}else {
			lblNewLabel_1 = new JLabel(dbName);
		}
		lblNewLabel_1.setBounds(376, 65, 81, 14);
		contentPanel.add(lblNewLabel_1);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
