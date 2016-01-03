package ListAllDB;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;

import javax.swing.Action;

public class QueryResults extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private final Action action = new SwingAction();
	public static QueryResults dialog = new QueryResults();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
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
		setBounds(100, 100, 572, 463);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		/*HashMap<String,Integer> map = new HashMap<>();
		map.put("A",1);
		map.put("B", 2);
		map.put("C", 3);
		int row = 0;*/
		Object rowData[][] = { { "Row1-Column1", "Row1-Column2", "Row1-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"},
                { "Row2-Column1000", "Row2-Column2", "Row2-Column3"} };
		Object columnNames[] = { "Column One", "Column Two", "Column Three"};
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 47, 536, 334);
			contentPanel.add(scrollPane);
			table = new JTable(rowData,columnNames);
			scrollPane.setViewportView(table);
		}
		
		JLabel lblNewLabel = new JLabel("Query Result");
		lblNewLabel.setBounds(10, 11, 226, 14);
		contentPanel.add(lblNewLabel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setAction(action);
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
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "OK");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			dialog.dispose();
		}
	}
}
