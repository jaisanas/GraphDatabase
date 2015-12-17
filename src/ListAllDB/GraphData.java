package ListAllDB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

public class GraphData {
	private HashMap<String,ArrayList<ArrayList<String>>> graph;

	public HashMap<String,ArrayList<ArrayList<String>>> getGraph() {
		return graph;
	}

	public void setGraph(HashMap<String,ArrayList<ArrayList<String>>> graph) {
		this.graph = graph;
	}
	
	public void put(String key,ArrayList<ArrayList<String>> graph) {
		this.graph.put(key, graph);
	}
	
	public GraphData() {
		this.graph = new HashMap<>();
	}
	
	public GraphData(HashMap<String,ArrayList<ArrayList<String>>> graph) {
		this.graph = graph;
	}
	
	public void printEachKey(ArrayList<ArrayList<String>> value) {
		for(int i = 0; i < value.size(); i++) {
			for(int j = 0; j < value.get(i).size(); j++) {
				System.out.println("edge "+value.get(i).get(j));
			}
		}
	} 
	
	public GraphData(HashMap<String,ArrayList<ArrayList<String>>> hm,String namaGraph) {
		String [] keyTemp = new String[hm.size()];
		int i = 0;
		for (Entry<String, ArrayList<ArrayList<String>>> ee : hm.entrySet()) {
			   String key = ee.getKey();
			   System.out.println("key "+key);
			   keyTemp[i] = key;
			   System.out.println(keyTemp[i]);
			   i++;
			}
		System.out.println("isi keytemp");
		for(int j = 0; j < keyTemp.length; j++) {
			System.out.println(keyTemp[j]);
		}
		Arrays.sort(keyTemp);
		for(int j = 0; j < keyTemp.length; j++) {
			System.out.println(keyTemp[j]);
		}
		this.graph = null;
		this.graph = new HashMap<String,ArrayList<ArrayList<String>>>();
		for(int j = 0; j < hm.size(); j++) {
			ArrayList<ArrayList<String>> tempList = hm.get(keyTemp[j]);
			this.graph.put(keyTemp[j], tempList);
		}
		
		for (Entry<String, ArrayList<ArrayList<String>>> ee : this.graph.entrySet()) {
			   String key = ee.getKey();
			   System.out.println("key "+key);
			   ArrayList<ArrayList<String>> value = ee.getValue();
			   printEachKey(value);
			}
		
	}
}
