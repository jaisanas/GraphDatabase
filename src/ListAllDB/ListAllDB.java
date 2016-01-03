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
import java.util.Arrays;
import java.util.HashMap;

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

import ListAllDB.GraphIndex.KeyGraphIndex;
import ListAllDB.GraphIndex.ValueGraphIndex;

public class ListAllDB extends JPanel {

public static JFrame frame = new JFrame("List All Databases");

  JList list;

  public ListAllDB() {
    setLayout(new BorderLayout());
    
    list = new JList(StartPage.dbList.toArray());
    JButton button = new JButton("execute");
    JScrollPane pane = new JScrollPane(list);
    System.out.println("isi dari jlist");
    for(int i = 0; i < list.getModel().getSize(); i++) {
    	//System.out.println(list.getModel().getElementAt(i));
    }
    
    System.out.println("isi dari dblist");
    for(int i = 0; i < StartPage.dbList.size();i++) {
    	//System.out.println(StartPage.dbList.get(i));
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
    JFrame frame = new JFrame("DB list");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setContentPane(new ListAllDB());
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
		String dbPath = dirPath+"\\"+element;
		StartPage.dbPath = dbPath;
		String filePath = dbPath+"\\"+"jaisGraphDesc.text";
		System.out.println(filePath);	
		File file = new File(filePath);
		
		//create file index
		String dirPathIndex = StartPage.path.replace("\\","\\\\");
		String dbPathIndex = dirPathIndex+"\\"+element;
		String filePathIndex = dbPath+"\\"+"jaisGraphIndex.text";
		String filePathIndex2 = dbPath+"\\"+"jaisGraphIndexList.text";
		System.out.println(filePathIndex);
		File file1 = new File(filePathIndex);
		File file1List = new File(filePathIndex2);
		
		String dirTreeIndex = StartPage.path.replace("\\","\\\\");
		String dbTreeIndex = dirPathIndex+"\\"+element;
		String fileTreeIndex = dbPath+"\\"+"jaisGraphTreeIndex.text";
		String fileTreeListIndex = dbPath+"\\"+"jaisGraphTreeListIndex.text";
		System.out.println(fileTreeIndex);
		File fileTree = new File(fileTreeIndex);
		File fileTreeList = new File(fileTreeListIndex);
		
		//create file for indexing path 
		String dirPathIndexPath = StartPage.path.replace("\\","\\\\");
		String dbPathIndexPath = dirPathIndexPath+"\\"+element;
		String filePathIndexPath = dbPathIndexPath+"\\"+"jaisGraphPathIndex.text";
		File file2 = new File(filePathIndexPath);
		
		//create file for indexing metadata
		String fileMetaDataPath = dbPathIndexPath+"\\"+"jaisGraphMetadataPathIndex.text";
		File file3 = new File(fileMetaDataPath);
		
		if (!file.exists() && !file1.exists() && !file1List.exists() && !fileTree.exists() && !fileTreeList.exists()) {
			System.out.println("capek");
			file.createNewFile();
			file1.createNewFile();
			file1List.createNewFile();
			fileTree.createNewFile();
			fileTreeList.createNewFile();
		}else {
			StartPage.graphList = new ArrayList();
			br = new BufferedReader(new FileReader(filePath));
			String graphPath;
			StartPage.graphList.clear();
			while((graphPath = br.readLine())!= null) {
				System.out.println(graphPath);
				StartPage.graphList.add(graphPath);
			}
			
			ModifyDB dialog = new ModifyDB(element);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			
			StartPage.gpi = new GraphIndex();
			br = new BufferedReader(new FileReader(filePathIndex));
			StartPage.gpi.destroy();
			String temp = br.readLine();
			
			ArrayList<String> graphName;
			ArrayList<KeyGraphIndex> keyIndex = new ArrayList<>();
			ArrayList<ValueGraphIndex> valueIndex = new ArrayList<>();
			BiMap tempBM = new BiMap();
			while(temp != null) {
				ArrayList<BiMap> bm = new ArrayList<>();
				graphName = new ArrayList<String>();
				if (temp.equals("[")) {
					temp = br.readLine();
					while(!temp.equals("]")) {
						String [] tempOfTemp = temp.split(" ");
						tempBM = new BiMap(tempOfTemp[0], tempOfTemp[1], tempOfTemp[2]);
						System.out.println("tempBM "+tempBM.getString());
						bm.add(tempBM);
						temp = br.readLine();
					}
				}
				
				KeyGraphIndex k = new KeyGraphIndex(bm);
				bm = null;
				System.out.println("isi KeyGraphIndex ");
				for(int o = 0; o < k.getKeyGraphValue().size(); o++) {
					System.out.println(k.getKeyGraphValue().get(o).getString());
				}
				keyIndex.add(k);
				System.out.println("print isi keyindex");
				for(int it = 0; it < keyIndex.size(); it++) {
					for(int j = 0; j < keyIndex.get(it).getKeyGraphValue().size(); j++) {
						System.out.println(keyIndex.get(it).getKeyGraphValue().get(j).getString());
					}
				}
				if(temp.equals("]")) {
					temp = br.readLine();
				}
				
				if(temp.equals("{")) {
					temp = br.readLine();
					while(!temp.equals("}")) {
						graphName.add(temp);
						System.out.println("temp "+temp);
						temp = br.readLine();
					}
				}
				
				ValueGraphIndex v = new ValueGraphIndex(graphName);
				graphName = null;
				valueIndex.add(v);
				System.out.println("print isi value index");
				/*for(int s = 0; s < valueIndex.size(); s++) {
					for(int r = 0; r < valueIndex.get(s).getNamaGraph().size(); r++) {
						System.out.println(valueIndex.get(s).getNamaGraph().get(r));
					}
				}*/
				//graphName = null;
				if(temp.equals("}")) {
					temp = br.readLine();
				}
			}
			
			StartPage.gpi = new GraphIndex(keyIndex, valueIndex);
			System.out.println("ngeprint");
			StartPage.gpi.print();
			
			//baca file index graph list 
			BufferedReader brIList = new BufferedReader(new FileReader(filePathIndex2));
			String graphDesc = brIList.readLine();
			HashMap<String,ArrayList<Integer>> gDesc = new HashMap<>();
			while(graphDesc != null) {
				String [] tokenGraph = graphDesc.split(" ");
				String key = tokenGraph[0];
				String [] positionList = tokenGraph[1].split(",");
				ArrayList<Integer> tempInt = new ArrayList<>();
				for(int iiter = 0; iiter < positionList.length; iiter++) {
					tempInt.add(Integer.parseInt(positionList[iiter]));
				}
				gDesc.put(key, tempInt);
				graphDesc = brIList.readLine();
			}
			brIList.close();
			StartPage.gIndexDesc = null;
			StartPage.gIndexDesc = new GraphIndexDesc(gDesc);
			//baca graphTreeIndex
			StartPage.graphTreeIndex = null;
			StartPage.graphTreeIndex = new GraphTreeIndex();
			String hashBar;
			BufferedReader brTree = new BufferedReader(new FileReader(fileTreeIndex));
			while((hashBar = brTree.readLine()) != null) {
				String [] tokenBar = hashBar.split(" ");
				String [] childBar = tokenBar[1].split(",");
				ArrayList<String> listChild = new ArrayList<>(Arrays.asList(childBar));
				StartPage.graphTreeIndex.getIndexTree().put(tokenBar[0], listChild);
				listChild = null;
			}
			brTree.close();
			//baca graphTreeListIndex
			StartPage.graphTreeListIndex = null;
			StartPage.graphTreeListIndex = new GraphTreeListIndex();
			String gdt;
			BufferedReader brList = new BufferedReader(new FileReader(fileTreeListIndex));
			gdt = brList.readLine();
			while(gdt != null) {
				String [] tempList = gdt.split(" ");
				GraphData dt;
				
				HashMap<String, ArrayList<ArrayList<String>>> graph = new HashMap<>();
				if(tempList.length == 1) {
					StartPage.graphTreeListIndex.getgTreeIndex().put(tempList[0], new GraphData());
					gdt = brList.readLine();
					String [] barLine = gdt.split(" ");
					boolean flag = true;
					while(barLine.length > 1) {
						ArrayList<ArrayList<String>> edgeList = new ArrayList<>();
						String [] edgeListEachVertex = barLine[1].split("-");
						for(int iter = 0; iter < edgeListEachVertex.length; iter++) {
							System.out.println("isi edge iter "+edgeListEachVertex[iter]);
							String [] edgeArrayComma = edgeListEachVertex[iter].split(",");
							ArrayList<String> tempEdgeList = new ArrayList<>(Arrays.asList(edgeArrayComma));
							System.out.println(tempEdgeList);
							System.out.println("anjing "+edgeList.size());
							edgeList.add(tempEdgeList);
						}
						graph.put(barLine[0], edgeList);
						gdt = null;barLine = null; edgeList = null;
						gdt = brList.readLine();
						System.out.println("why except "+gdt);
						if(gdt != null) {
							barLine = gdt.split(" ");
						}else {
							break;
						}
					}
					dt = new GraphData(graph);
					StartPage.graphTreeListIndex.getgTreeIndex().put(tempList[0],dt);
					tempList = null;dt=null;graph=null;
				}
				
			}
			brList.close();
			
			StartPage.graphTreeListIndex.Print(StartPage.graphTreeIndex);
		 }
		 frame.setVisible(false);
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	
      }
    }
  }
  
  public static void initIndex() {
	  try {
	    	BufferedReader br = null;
			String filePath = StartPage.dbPath+"\\"+"jaisGraphDesc.text";
			System.out.println(filePath);	
			File file = new File(filePath);
			
			//create file index
			String filePathIndex = StartPage.dbPath+"\\"+"jaisGraphIndex.text";
			String filePathIndex2 = StartPage.dbPath+"\\"+"jaisGraphIndexList.text";
			System.out.println(filePathIndex);
			File file1 = new File(filePathIndex);
			File file1List = new File(filePathIndex2);
			
			String fileTreeIndex = StartPage.dbPath+"\\"+"jaisGraphTreeIndex.text";
			String fileTreeListIndex = StartPage.dbPath+"\\"+"jaisGraphTreeListIndex.text";
			System.out.println(fileTreeIndex);
			File fileTree = new File(fileTreeIndex);
			File fileTreeList = new File(fileTreeListIndex);
			
			String filePathIndexPath = StartPage.dbPath+"\\"+"jaisGraphPathIndex.text";
			File file2 = new File(filePathIndexPath);
			
			//create file for indexing metadata
			String fileMetaDataPath = StartPage.dbPath+"\\"+"jaisGraphMetadataPathIndex.text";
			File file3 = new File(fileMetaDataPath);
			
			if (!file.exists() && !file1.exists() && !file1List.exists() && !fileTree.exists() && !fileTreeList.exists()) {
				System.out.println("capek");
				file.createNewFile();
				file1.createNewFile();
				file1List.createNewFile();
				fileTree.createNewFile();
				fileTreeList.createNewFile();
			}else {
				StartPage.graphList = new ArrayList();
				br = new BufferedReader(new FileReader(filePath));
				String graphPath;
				StartPage.graphList.clear();
				while((graphPath = br.readLine())!= null) {
					System.out.println(graphPath);
					StartPage.graphList.add(graphPath);
				}
				
				StartPage.gpi = new GraphIndex();
				br = new BufferedReader(new FileReader(filePathIndex));
				StartPage.gpi.destroy();
				String temp = br.readLine();
				
				ArrayList<String> graphName;
				ArrayList<KeyGraphIndex> keyIndex = new ArrayList<>();
				ArrayList<ValueGraphIndex> valueIndex = new ArrayList<>();
				BiMap tempBM = new BiMap();
				while(temp != null) {
					ArrayList<BiMap> bm = new ArrayList<>();
					graphName = new ArrayList<String>();
					if (temp.equals("[")) {
						temp = br.readLine();
						while(!temp.equals("]")) {
							String [] tempOfTemp = temp.split(" ");
							tempBM = new BiMap(tempOfTemp[0], tempOfTemp[1], tempOfTemp[2]);
							System.out.println("tempBM "+tempBM.getString());
							bm.add(tempBM);
							temp = br.readLine();
						}
					}
					
					KeyGraphIndex k = new KeyGraphIndex(bm);
					bm = null;
					System.out.println("isi KeyGraphIndex ");
					for(int o = 0; o < k.getKeyGraphValue().size(); o++) {
						System.out.println(k.getKeyGraphValue().get(o).getString());
					}
					keyIndex.add(k);
					System.out.println("print isi keyindex");
					for(int it = 0; it < keyIndex.size(); it++) {
						for(int j = 0; j < keyIndex.get(it).getKeyGraphValue().size(); j++) {
							System.out.println(keyIndex.get(it).getKeyGraphValue().get(j).getString());
						}
					}
					if(temp.equals("]")) {
						temp = br.readLine();
					}
					
					if(temp.equals("{")) {
						temp = br.readLine();
						while(!temp.equals("}")) {
							graphName.add(temp);
							System.out.println("temp "+temp);
							temp = br.readLine();
						}
					}
					
					ValueGraphIndex v = new ValueGraphIndex(graphName);
					graphName = null;
					valueIndex.add(v);
					System.out.println("print isi value index");
					/*for(int s = 0; s < valueIndex.size(); s++) {
						for(int r = 0; r < valueIndex.get(s).getNamaGraph().size(); r++) {
							System.out.println(valueIndex.get(s).getNamaGraph().get(r));
						}
					}*/
					//graphName = null;
					if(temp.equals("}")) {
						temp = br.readLine();
					}
				}
				
				StartPage.gpi = new GraphIndex(keyIndex, valueIndex);
				System.out.println("ngeprint");
				StartPage.gpi.print();
				
				//baca file index graph list 
				//baca file index graph list 
				BufferedReader brIList = new BufferedReader(new FileReader(filePathIndex2));
				String graphDesc = brIList.readLine();
				HashMap<String,ArrayList<Integer>> gDesc = new HashMap<>();
				while(graphDesc != null) {
					String [] tokenGraph = graphDesc.split(" ");
					String key = tokenGraph[0];
					String [] positionList = tokenGraph[1].split(",");
					ArrayList<Integer> tempInt = new ArrayList<>();
					for(int iiter = 0; iiter < positionList.length; iiter++) {
						System.out.println("mati mati mati "+positionList[iiter]);
						System.out.println(Integer.parseInt(positionList[iiter]));
						tempInt.add(Integer.parseInt(positionList[iiter]));
					}
					gDesc.put(key, tempInt);
					graphDesc = brIList.readLine();
				}
				brIList.close();
				StartPage.gIndexDesc = null;
				StartPage.gIndexDesc = new GraphIndexDesc(gDesc);
				
				//baca graphTreeIndex
				StartPage.graphTreeIndex = null;
				StartPage.graphTreeIndex = new GraphTreeIndex();
				String hashBar;
				BufferedReader brTree = new BufferedReader(new FileReader(fileTreeIndex));
				while((hashBar = brTree.readLine()) != null) {
					String [] tokenBar = hashBar.split(" ");
					String [] childBar = tokenBar[1].split(",");
					ArrayList<String> listChild = new ArrayList<>(Arrays.asList(childBar));
					StartPage.graphTreeIndex.getIndexTree().put(tokenBar[0], listChild);
					listChild = null;
				}
				brTree.close();
				//baca graphTreeListIndex
				StartPage.graphTreeListIndex = null;
				StartPage.graphTreeListIndex = new GraphTreeListIndex();
				String gdt;
				BufferedReader brList = new BufferedReader(new FileReader(fileTreeListIndex));
				gdt = brList.readLine();
				while(gdt != null) {
					String [] tempList = gdt.split(" ");
					GraphData dt;
					
					HashMap<String, ArrayList<ArrayList<String>>> graph = new HashMap<>();
					if(tempList.length == 1) {
						StartPage.graphTreeListIndex.getgTreeIndex().put(tempList[0], new GraphData());
						gdt = brList.readLine();
						String [] barLine = gdt.split(" ");
						boolean flag = true;
						while(barLine.length > 1) {
							ArrayList<ArrayList<String>> edgeList = new ArrayList<>();
							String [] edgeListEachVertex = barLine[1].split("-");
							for(int iter = 0; iter < edgeListEachVertex.length; iter++) {
								System.out.println("isi edge iter "+edgeListEachVertex[iter]);
								String [] edgeArrayComma = edgeListEachVertex[iter].split(",");
								ArrayList<String> tempEdgeList = new ArrayList<>(Arrays.asList(edgeArrayComma));
								edgeList.add(tempEdgeList);
							}
							graph.put(barLine[0], edgeList);
							gdt = null;barLine = null; edgeList = null;
							gdt = brList.readLine();
							if(gdt != null) {
								barLine = gdt.split(" ");
							}else {
								break;
							}
						}
						dt = new GraphData(graph);
						StartPage.graphTreeListIndex.getgTreeIndex().put(tempList[0],dt);
						tempList = null;dt=null;graph=null;
					}
					
				}
				brList.close();
				StartPage.graphTreeListIndex.Print(StartPage.graphTreeIndex);
			 }
			 
	    	}catch(Exception ex) {
	    		ex.printStackTrace();
	    	}
  	
  }
  
  public static void initListAllDB() {
	  	frame.setSize(260, 200);
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.setContentPane(new ListAllDB());
	    frame.pack();
	    frame.setVisible(true);
  }
}