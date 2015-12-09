package ListAllDB;

import java.util.ArrayList;
import java.util.Scanner;

public class BiGraph {
	private ArrayList<BiMap> biMapArr;
	
	public ArrayList<BiMap> getBiMapArr() {
		return biMapArr;
	}

	public void setBiMapArr(ArrayList<BiMap> biMapArr) {
		this.biMapArr = biMapArr;
	}

	public BiGraph() {
	}
	
	public BiGraph(ArrayList<BiMap> biMap) {
		this.biMapArr = biMap;
	}
	
	public BiMap getValue(int i) {
		return biMapArr.get(i);
	}
	
	public void addBiMap(BiMap b) {
		biMapArr.add(b);
	}
	
	public int getSize() {
		return biMapArr.size();
	}
	
	public ArrayList<BiMap> getBiMapEquals(String source) {
		ArrayList<BiMap> tempBiMap = new ArrayList();
		for(int i = 0; i < biMapArr.size(); i++) {
			System.out.println("masuk ke "+i);
			System.out.println("source label "+biMapArr.get(i).getEdgeLabel()+biMapArr.get(i).getSourceLabel()+biMapArr.get(i).getTargetLabel());
			if(biMapArr.get(i).getSourceLabel().equals(source)) {
				System.out.println("ada yang sama "+ i);
				tempBiMap.add(biMapArr.get(i));
			}
		}
		
		return tempBiMap;
	}
	
	public static void main(String [] args) {
		System.out.println("masukkan nilai : ");
		
		Scanner input = new Scanner(System.in);
		String edge,source,target;
		ArrayList<BiMap> arrbm = new ArrayList();
		for(int i = 0; i < 4; i++) {
			edge = input.nextLine();
			source = input.nextLine();
			target = input.nextLine();
			BiMap bm = new BiMap(edge,source,target);
			System.out.println("<"+bm.getEdgeLabel()+"|"+bm.getSourceLabel()+"|"+bm.getTargetLabel()+">");
			arrbm.add(bm);
		}
		BiGraph bg = new BiGraph(arrbm);
		System.out.println("list array yang mengandung source a "+arrbm.size());
		System.out.println("ukuran bg "+bg.getSize());
		ArrayList<BiMap> tempBM = new ArrayList();
		BiMap bb = arrbm.get(0);
		if(bb.getSourceLabel().equals("a")) {
			System.out.println("sama a a");
		}
		tempBM = bg.getBiMapEquals("a");
		System.out.println(tempBM.size());
		BiMap bp;
		for(int i = 0; i < tempBM.size(); i++) {
			bp = tempBM.get(i);
			System.out.println("<"+bp.getEdgeLabel()+"|"+bp.getSourceLabel()+"|"+bp.getTargetLabel()+">");
		}
	}
}
