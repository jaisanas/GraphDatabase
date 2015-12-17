package ListAllDB;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class GraphIndexDesc {
	private HashMap<String, ArrayList<Integer>> gDesc;

	public HashMap<String, ArrayList<Integer>> getgDesc() {
		return gDesc;
	}

	public void setgDesc(HashMap<String, ArrayList<Integer>> gDesc) {
		this.gDesc = gDesc;
	}
	
	public GraphIndexDesc() {
		gDesc = new HashMap<>();
	}
	
	public GraphIndexDesc(HashMap<String, ArrayList<Integer>> gDesc) {
		this.gDesc = gDesc;
	}
	
	public void Print() {
		System.out.println("======================================================");
		System.out.println("============Graph Indexing Description================");
		System.out.println("======================================================");
		for (Entry<String, ArrayList<Integer>> ee : this.gDesc.entrySet()) {
			String key = ee.getKey();
			System.out.print(key);
			ArrayList<Integer> value = ee.getValue();
			System.out.println(" ");
			for(int i = 0; i < value.size(); i++) {
				System.out.print(value.get(i));
				if(i < value.size() -1) System.out.print(",");
			}
			System.out.println();
		}
	}
	
	public void writeToFile() {
		try{
			File file = new File(StartPage.dbPath+"\\"+"jaisGraphIndexList.text");
			
			System.out.println("ngeprint file jaisGraphIndexList");
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			System.out.println("ukuaran graphindexdescription "+this.gDesc.size());
			for (Entry<String, ArrayList<Integer>> ee : this.gDesc.entrySet()) {
				String key = ee.getKey();
				//System.out.print(key);
				System.out.println("aku maluuu");
				bw.write(key);
				bw.write(" ");
				ArrayList<Integer> value = ee.getValue();
				for(int i = 0; i < value.size(); i++) {
					//System.out.print(value.get(i));
					bw.write(Integer.toString(value.get(i)));
					if(i < value.size() -1) bw.write(",");
				}
				//System.out.println();
				bw.newLine();
			}
			
			bw.close();
			
			Print();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
