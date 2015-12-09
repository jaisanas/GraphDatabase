package ListAllDB;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ShowAllGraph extends JPanel {

 

  JList list;

  public ShowAllGraph() {
    setLayout(new BorderLayout());

    list = new JList(StartPage.graphList.toArray());
    JButton button = new JButton("execute");
    JScrollPane pane = new JScrollPane(list);
    System.out.println("isi dari jlist");
    for(int i = 0; i < list.getModel().getSize(); i++) {
    	//System.out.println(list.getModel().getElementAt(i));
    }
    
    System.out.println("isi dari dblist");
    for(int i = 0; i < StartPage.graphList.size();i++) {
    	System.out.println(StartPage.graphList.get(i));
    }
    DefaultListSelectionModel m = new DefaultListSelectionModel();
    m.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    m.setLeadAnchorNotificationEnabled(false);
    list.setSelectionModel(m);

    list.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        //System.out.println(e.toString());
    	 /* int x = list.getSelectedIndex();
    	  System.out.println(x);*/
      }
    });
    button.addActionListener(new PrintListener());

    add(pane, BorderLayout.NORTH);
    add(button, BorderLayout.SOUTH);
  }

  public static void main(String s[]) {
    JFrame frame = new JFrame("Show All Graph");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setContentPane(new ShowAllGraph());
    frame.pack();
    frame.setVisible(true);
  }
  
  
  // An inner class to respond to clicks on the Print button
  class PrintListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      int selected[] = list.getSelectedIndices();
      System.out.println("Selected Elements:  ");

      for (int i = 0; i < selected.length; i++) {
    	try {
    	BufferedReader br = null;
        String element = (String) list.getModel().getElementAt(selected[i]);
        System.out.println(selected[i]);
        System.out.println("  " + element);
        String dirPath = StartPage.path.replace("\\","\\\\");
		String graphPath = dirPath+"\\"+StartPage.activeDB+"\\"+element+".mxe";
			StartPage.gFilePath = graphPath;
			GraphEditor.initGraphEditor(StartPage.gFilePath);
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	
      }
    }
  }
  
  public static void initListAllGraph() {
	  	JFrame frame = new JFrame("List All Graph");
	  	frame.setSize(260, 200);
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.setContentPane(new ShowAllGraph());
	    frame.pack();
	    frame.setVisible(true);
  }
}
