package ListAllDB;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class NVertexEachGraph {
	private HashMap<String, Integer> hm;

	public HashMap<String, Integer> getHm() {
		return hm;
	}

	public void setHm(HashMap<String, Integer> hm) {
		this.hm = hm;
	}
	
	public NVertexEachGraph() {
		hm = new HashMap<>();
	}
	
	public NVertexEachGraph(HashMap<String, Integer> hm) {
		this.hm = hm;
	}
	
	public void writeToFile(String filePath) {
		try{
			File file = new File(filePath);
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for (Entry<String, Integer> ee : this.hm.entrySet()) {
				bw.write(ee.getKey());
				bw.write(" ");
				bw.write(ee.getValue());
			}
			bw.newLine();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void Print() {
		System.out.println("==========================================");
		System.out.println("============NVertexEachIndex==============");
		System.out.println("==========================================");
		for (Entry<String, Integer> ee : this.hm.entrySet()) {
			System.out.println(ee.getKey()+" "+ee.getValue());
		}
	}
}
