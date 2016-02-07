package ListAllDB;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class QueryResults extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public static QueryResults dialog;
	private JTable table;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			dialog = new QueryResults();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public QueryResults() {
		setBounds(100, 100, 572, 430);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblQueryResultsl = new JLabel("QUERY RESULTS");
		lblQueryResultsl.setBounds(10, 11, 164, 14);
		contentPanel.add(lblQueryResultsl);
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 34, 536, 314);
			contentPanel.add(scrollPane);
			{
				/*Map<String,Double> map=new HashMap<>();
				 map.put("A",1.00);
				 map.put("B",0.02);
				 map.put("C",0.13); */
				table =new JTable(StartPage.result.size(),2);
				/* int row=0;
				 for(Map.Entry<String,Double> entry: map.entrySet()){
				      table.setValueAt(entry.getKey(),row,0);
				      table.setValueAt(entry.getValue(),row,1);
				      row++;
				 } */
				  
				 for (Entry<String, Double>  ee: StartPage.result.entrySet()) {
					 System.out.println(ee.getKey()+" taraaaa "+ee.getValue());
				 }
				
				  int row = 0;
				  for (Entry<String, Double>  ee: StartPage.result.entrySet()) {
					  if(!ee.getKey().equals("temp.mxe")) {
					   table.setValueAt(ee.getKey(), row, 0);
					   table.setValueAt(ee.getValue(), row, 1);
					   row++;
					  }
				  } 
				 JTableHeader th = table.getTableHeader();
				 TableColumnModel tcm = th.getColumnModel();
				 TableColumn tc = tcm.getColumn(0);
				 TableColumn tc1 = tcm.getColumn(1);
				 tc.setHeaderValue( "Graph Name" );
				 tc1.setHeaderValue( "Similarity Score" );
				 
				 /* table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					
					@Override
					public void valueChanged(ListSelectionEvent e) {
						// TODO Auto-generated method stub
						String graphTarget = table.getValueAt(table.getSelectedRow(), 0).toString();
						String simScore = table.getValueAt(table.getSelectedRow(), 1).toString();
						CompareView.initCompareView(graphTarget,simScore,"tempDistance",graphTarget);
					}
				}); */
				 
				 table.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						String graphTarget = table.getValueAt(table.getSelectedRow(), 0).toString();
						String simScore = table.getValueAt(table.getSelectedRow(), 1).toString();
						CompareView.initCompareView(graphTarget,simScore,"tempDistance",graphTarget);
					}
				});
				 
				 
				 th.repaint();
				 scrollPane.setViewportView(table);
			}
		}
		
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
	
	public QueryResults(final String namaGraphQuery, final String distanceFunction) {
		setBounds(100, 100, 572, 430);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblQueryResultsl = new JLabel("QUERY RESULTS");
		lblQueryResultsl.setBounds(10, 11, 164, 14);
		contentPanel.add(lblQueryResultsl);
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 34, 536, 314);
			contentPanel.add(scrollPane);
			{
				/*Map<String,Double> map=new HashMap<>();
				 map.put("A",1.00);
				 map.put("B",0.02);
				 map.put("C",0.13); */
				table =new JTable(StartPage.result.size(),2);
				/* int row=0;
				 for(Map.Entry<String,Double> entry: map.entrySet()){
				      table.setValueAt(entry.getKey(),row,0);
				      table.setValueAt(entry.getValue(),row,1);
				      row++;
				 } */
				  
				 for (Entry<String, Double>  ee: StartPage.result.entrySet()) {
					 System.out.println(ee.getKey()+" taraaaa "+ee.getValue());
				 }
				
				  int row = 0;
				  for (Entry<String, Double>  ee: StartPage.result.entrySet()) {
					  if(!ee.getKey().equals("temp.mxe")) {
					   table.setValueAt(ee.getKey(), row, 0);
					   table.setValueAt(ee.getValue(), row, 1);
					   row++;
					  }
				  } 
				 JTableHeader th = table.getTableHeader();
				 TableColumnModel tcm = th.getColumnModel();
				 TableColumn tc = tcm.getColumn(0);
				 TableColumn tc1 = tcm.getColumn(1);
				 tc.setHeaderValue( "Graph Name" );
				 tc1.setHeaderValue( "Dissimilarity Measure" );
				 
				 /* table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					
					@Override
					public void valueChanged(ListSelectionEvent e) {
						// TODO Auto-generated method stub
						String graphTarget = table.getValueAt(table.getSelectedRow(), 0).toString();
						String simScore = table.getValueAt(table.getSelectedRow(), 1).toString();
						CompareView.initCompareView(graphTarget,simScore,"tempDistance",graphTarget);
					}
				}); */
				 
				 table.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						String graphTarget = table.getValueAt(table.getSelectedRow(), 0).toString();
						String simScore = table.getValueAt(table.getSelectedRow(), 1).toString();
						CompareView.initCompareView(graphTarget,simScore,distanceFunction,namaGraphQuery);
					}
				});
				 
				 
				 th.repaint();
				 scrollPane.setViewportView(table);
			}
		}
		
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
	
	public static void showQueryResults(){
		try {
			dialog = new QueryResults();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void showQueryResults(final String namaGraphQuery, final String distanceFunction){
		try {
			dialog = new QueryResults(namaGraphQuery,distanceFunction);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
