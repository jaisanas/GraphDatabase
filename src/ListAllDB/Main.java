package ListAllDB;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


//w w  w  .j a  v  a 2  s .com
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Main {
  public static void main(String args[]) {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Object rowData[][] = { { "Row1-Column1", "Row1-Column2", "Row1-Column3" },
        { "Row2-Column1", "Row2-Column2", "Row2-Column3" } };
    Object columnNames[] = { "Column One", "Column Two", "Column Three" };
    final JTable table = new JTable(rowData, columnNames);
    
    
    JScrollPane scrollPane = new JScrollPane(table);

    frame.add(scrollPane, BorderLayout.CENTER);
    frame.setSize(300, 150);
    frame.setVisible(true);
    
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
			 String nilai_0 = table.getValueAt(table.getSelectedRow(), 0).toString();
		        String nilai_1 = table.getValueAt(table.getSelectedRow(), 1).toString();
		        String nilai_2 = table.getValueAt(table.getSelectedRow(), 2).toString();
		        //JOptionPane.showMessageDialog(null,nilai_0+ " alalalal "+nilai_1+" ululululu "+nilai_2);
		        CompareView.initCompareView();
		}
	});
    
    /*ListSelectionModel cellSelectionModel = table.getSelectionModel();
    cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        String selectedData = null;

        String nilai_0 = table.getValueAt(table.getSelectedRow(), 0).toString();
        String nilai_1 = table.getValueAt(table.getSelectedRow(), 1).toString();
        String nilai_2 = table.getValueAt(table.getSelectedRow(), 2).toString();
        //JOptionPane.showMessageDialog(null,nilai_0+ " alalalal "+nilai_1+" ululululu "+nilai_2);
        CompareView.initCompareView();
        //System.out.println("Selected: " + selectedData);
      }

    }); */
    
 
  }
}