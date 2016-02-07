package ListAllDB;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.SystemColor;

import javax.swing.border.BevelBorder;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CompareView extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private String graphTargetName;
	private String simScore;
	private String distanceMeasure;
	private String graphQueryName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CompareView dialog = new CompareView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void initCompareView(String graphTargetName, String simScore,
		String distanceMeasure, String graphQueryName){
		CompareView dialog = new CompareView(graphTargetName,simScore,distanceMeasure,graphQueryName);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}
	
	public static void initCompareView() {
		CompareView dialog = new CompareView();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}
	
	
	public CompareView(final String graphTargetName, String simScore,
			String distanceFunction,final String graphQueryName) {
		super();
		this.graphTargetName = graphTargetName;
		this.simScore = simScore;
		this.distanceMeasure = distanceMeasure;
		this.graphQueryName = graphQueryName;
		
		setBounds(100, 100, 1109, 663);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 45, 413, 472);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Graph Target File Image "+graphTargetName+".png "+"does not exist");
		lblNewLabel.setBounds(10, 11, 393, 450);
		lblNewLabel.setIcon(new ImageIcon(new ImageIcon(StartPage.dbPath+"\\"+graphTargetName+".png").getImage().getScaledInstance(lblNewLabel.getWidth(), lblNewLabel.getHeight(), Image.SCALE_DEFAULT)));
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(449, 45, 413, 472);
		contentPanel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel label_1 = new JLabel("Graph Target File Image "+graphQueryName+".png "+"does not exist");
		label_1.setBounds(10, 11, 393, 450);
		System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww "+StartPage.dbPath);
		label_1.setIcon(new ImageIcon(new ImageIcon(StartPage.dbPath+"\\"+graphQueryName+".png").getImage().getScaledInstance(label_1.getWidth(), label_1.getHeight(), Image.SCALE_DEFAULT)));
		
		panel_1.add(label_1);
		
		JLabel lblGrafQuery = new JLabel("Graf Target");
		lblGrafQuery.setBounds(182, 21, 110, 14);
		contentPanel.add(lblGrafQuery);
		
		JLabel lblGrafQuery_1 = new JLabel("Graf Query");
		lblGrafQuery_1.setBounds(675, 21, 92, 14);
		contentPanel.add(lblGrafQuery_1);
		
		JLabel lblDistanceMeasure = new JLabel("Distance Measure Type:");
		lblDistanceMeasure.setBounds(872, 47, 177, 14);
		contentPanel.add(lblDistanceMeasure);
		
		JLabel lblDistanceMeasure_1 = new JLabel(distanceFunction);
		lblDistanceMeasure_1.setBounds(872, 72, 117, 14);
		contentPanel.add(lblDistanceMeasure_1);
		
		JLabel lblDistanceScore = new JLabel("Distance Score");
		lblDistanceScore.setBounds(872, 150, 117, 14);
		contentPanel.add(lblDistanceScore);
		
		JLabel label = new JLabel(simScore);
		label.setBounds(872, 175, 46, 14);
		contentPanel.add(label);
		
		JButton btnNewButton = new JButton("Detail Graph Target");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GraphEditor.initGraphEditor(StartPage.dbPath+"\\"+graphTargetName);
			}
		});
		btnNewButton.setBounds(140, 528, 152, 53);
		contentPanel.add(btnNewButton);
		
		JButton button = new JButton("Detail Graph Query");
		button.setBounds(615, 528, 152, 53);
		contentPanel.add(button);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				GraphEditor.initGraphEditor(StartPage.dbPath+"\\"+graphQueryName);
			}
		});
		
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



	/**
	 * Create the dialog.
	 */
	public CompareView() {
		setBounds(100, 100, 1109, 663);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 45, 413, 472);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(10, 11, 393, 450);
		lblNewLabel.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\jais\\Desktop\\proyek\\testDB2\\j14.mxe.png").getImage().getScaledInstance(lblNewLabel.getWidth(), lblNewLabel.getHeight(), Image.SCALE_DEFAULT)));
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(449, 45, 413, 472);
		contentPanel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel label_1 = new JLabel("New label");
		label_1.setBounds(10, 11, 393, 450);
		label_1.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\jais\\Desktop\\proyek\\testDB2\\j14.mxe.png").getImage().getScaledInstance(label_1.getWidth(), label_1.getHeight(), Image.SCALE_DEFAULT)));
		
		panel_1.add(label_1);
		
		JLabel lblGrafQuery = new JLabel("Graf Target");
		lblGrafQuery.setBounds(182, 21, 110, 14);
		contentPanel.add(lblGrafQuery);
		
		JLabel lblGrafQuery_1 = new JLabel("Graf Query");
		lblGrafQuery_1.setBounds(675, 21, 92, 14);
		contentPanel.add(lblGrafQuery_1);
		
		JLabel lblDistanceMeasure = new JLabel("Distance Measure Type:");
		lblDistanceMeasure.setBounds(872, 47, 177, 14);
		contentPanel.add(lblDistanceMeasure);
		
		JLabel lblDistanceMeasure_1 = new JLabel("distance measure");
		lblDistanceMeasure_1.setBounds(872, 72, 117, 14);
		contentPanel.add(lblDistanceMeasure_1);
		
		JLabel lblDistanceScore = new JLabel("Distance Score");
		lblDistanceScore.setBounds(872, 150, 117, 14);
		contentPanel.add(lblDistanceScore);
		
		JLabel label = new JLabel("0");
		label.setBounds(872, 175, 46, 14);
		contentPanel.add(label);
		
		JButton btnNewButton = new JButton("Detail Graph Target");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(140, 528, 152, 53);
		contentPanel.add(btnNewButton);
		
		JButton button = new JButton("Detail Graph Query");
		button.setBounds(615, 528, 152, 53);
		contentPanel.add(button);
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
