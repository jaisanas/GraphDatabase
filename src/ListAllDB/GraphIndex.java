package ListAllDB;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class GraphIndex {
	private ArrayList<KeyGraphIndex> keyIndex;
	private ArrayList<ValueGraphIndex> valueIndex;
	private int counter;
	
	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public GraphIndex(ArrayList<KeyGraphIndex> k, ArrayList<ValueGraphIndex> v) {
		this.keyIndex = k;
		this.valueIndex = v;
	}
	
	public ArrayList<KeyGraphIndex> getKeyIndex() {
		return keyIndex;
	}

	public void setKeyIndex(ArrayList<KeyGraphIndex> keyIndex) {
		this.keyIndex = keyIndex;
	}

	public ArrayList<ValueGraphIndex> getValueIndex() {
		return valueIndex;
	}

	public void setValueIndex(ArrayList<ValueGraphIndex> valueIndex) {
		this.valueIndex = valueIndex;
	}
	
	public GraphIndex() {
		
	}
	
	public boolean contains(ArrayList<BiMap> lbm, BiMap bm) {
		int index = -9;
		for(int i = 0; i < lbm.size(); i++) {
			if(lbm.get(i).equalsBM(bm)) index = i;
		}
		
		if(index >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isEqualsListBM(ArrayList<BiMap> b1, ArrayList<BiMap> b2) {
		boolean flag = true;
		if(b1.size() != b2.size()) {
			flag = false;
		} else {
			for(int i = 0; i < b1.size(); i++) {
				if(!contains(b1,b2.get(i))) flag = false;
			}
		}
		
		return flag;
	}
	
	public void setNewValue(HashMap<String,ArrayList<BiMap>> hm,String namaGraph) {
		System.out.println("set new value");
		System.out.println("ukuran key index"+this.keyIndex.size());
		if(this.keyIndex.size() == 0) {
			System.out.println("test coba");
			initValue(hm,namaGraph);
		}else {
			ArrayList<Integer> arrInteger = new ArrayList<>();
			
			for(int i = 0; i < this.keyIndex.size(); i++) {
				if(hm.get(this.keyIndex.get(i).getFirstBM().getSourceLabel()) != null) {
					System.out.println("masuk josh "+hm.get(this.keyIndex.get(i).getFirstBM().getSourceLabel()));
					ArrayList<BiMap> tempBm = new ArrayList();
					tempBm = hm.get(this.keyIndex.get(i).getFirstBM().getSourceLabel());
					System.out.println("sama gak sih "+isEqualsListBM(tempBm, this.keyIndex.get(i).getKeyGraphValue()));
					if(isEqualsListBM(tempBm, this.keyIndex.get(i).getKeyGraphValue())) {
						this.valueIndex.get(i).setNew(namaGraph);
						System.out.println("remove "+this.keyIndex.get(i).getFirstBM().getSourceLabel());
						hm.remove(this.keyIndex.get(i).getFirstBM().getSourceLabel());
						arrInteger.add(i);
					}
				}
			}
			
			int iterate = this.keyIndex.size();
			ArrayList<BiMap> tempBm = new ArrayList();
			for (Entry<String, ArrayList<BiMap>> ee : hm.entrySet()) {
			    String key = ee.getKey();
			    ArrayList<BiMap> values = ee.getValue();
			    // TODO: Do something.
			    tempBm = values;
			    KeyGraphIndex kgi = new KeyGraphIndex(tempBm);
			    this.keyIndex.add(kgi);
				ValueGraphIndex vgi = new ValueGraphIndex(namaGraph);
				this.valueIndex.add(vgi);
				arrInteger.add(iterate);
				iterate++;
			}
			StartPage.gIndexDesc.getgDesc().put(namaGraph, arrInteger);
		}
	}
	
	public void setNewFeature(BiMap bm,String namaGraph, boolean isEndIndex) {
		if (keyIndex == null) {
			init(bm,namaGraph);
		}else {
			System.out.println("key index tidak null");
			HashMap<String,ArrayList<BiMap>> hm = new HashMap<String, ArrayList<BiMap>>();
			boolean flag = false;
			int index = -999;
			int counter = 0;
			ArrayList<BiMap> bp = new ArrayList();
			for(int i = 0; i < keyIndex.size(); i++) {
				if(this.keyIndex.get(i).getFirstBM().equalsSource(bm)) {
					System.out.println("masuk keyIndex sama dengan "+i);
					flag = true;
					index = i;
					if(this.valueIndex.get(index).getNamaGraph().get(0) != namaGraph) {
						String sourceKey = bm.getSourceLabel();
						if (hm.get(sourceKey) == null ) {
							hm.put(sourceKey, new ArrayList<BiMap>());
							hm.get(sourceKey).add(bm);
						}else {
							hm.get(sourceKey).add(bm);
						}
						bp.add(bm);
						counter++;
					}
				} 
			}
			
			if(flag && this.valueIndex.get(index).getNamaGraph().get(0) == namaGraph) {
				this.keyIndex.get(index).setNew(bm);
				this.valueIndex.get(index).setNew(namaGraph);
			} else if(flag && isEndIndex){
				for(int i = 0; i <keyIndex.size(); i++) {
					BiMap tempBM = this.keyIndex.get(i).getFirstBM();
					if(hm.get(tempBM.getSourceLabel()) != null) {
						ArrayList<BiMap> tempValue = hm.get(tempBM.getSourceLabel());
						if(tempValue.size() == this.keyIndex.get(i).getKeyGraphValue().size()) {
							this.valueIndex.get(i).setNew(namaGraph);
						}else {
							KeyGraphIndex kgi = new KeyGraphIndex(bm);
							this.keyIndex.add(kgi);
							ValueGraphIndex vgi = new ValueGraphIndex(namaGraph);
							this.valueIndex.add(vgi);
						}
					}
				}
			}else {
				KeyGraphIndex kgi = new KeyGraphIndex(bm);
				this.keyIndex.add(kgi);
				ValueGraphIndex vgi = new ValueGraphIndex(namaGraph);
				this.valueIndex.add(vgi);
			}
		}
	}
	
	public void initValue(HashMap<String, ArrayList<BiMap>> hm,String namaGraph ) {
		int i = 0;
		ArrayList<Integer> arrayIntger = new ArrayList<>();
		for (Entry<String, ArrayList<BiMap>> ee : hm.entrySet()) {
		    String key = ee.getKey();
		    ArrayList<BiMap> values = ee.getValue();
		    // TODO: Do something.
		    ArrayList<String> name = new ArrayList<>();
		    name.add(namaGraph);
		    KeyGraphIndex k = new KeyGraphIndex(values);
		    ValueGraphIndex v = new ValueGraphIndex(name);
		    this.keyIndex.add(k);
		    this.valueIndex.add(v);
		    arrayIntger.add(i);
		    i++;
		}
		
		StartPage.gIndexDesc.getgDesc().put(namaGraph, arrayIntger);
		
	}
	
	public void destroy() {
		if(keyIndex != null && valueIndex != null) {
			this.keyIndex.clear();
			this.valueIndex.clear();
		}
	}
	
	public void print() {
		System.out.println("==========================================");
		System.out.println("============Graph Indexing================");
		System.out.println("==========================================");
		for(int i = 0; i < keyIndex.size(); i++) {
			for(int j = 0; j < keyIndex.get(i).getKeyGraphValue().size(); j++) {
				System.out.println(keyIndex.get(i).getKeyGraphValue().get(j).getString());
				
			}
			System.out.println("<==>");
			for(int k = 0; k < this.valueIndex.get(i).getNamaGraph().size(); k++) {
				System.out.println(this.valueIndex.get(i).getNamaGraph().get(k));
				
			}
			System.out.println();
		}
	}
	
	public void writeToFile(String filePath) {
		try{
			File file = new File(filePath);
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for(int i = 0; i < keyIndex.size(); i++) {
				bw.write("[");
				bw.newLine();
				for(int j = 0; j < keyIndex.get(i).getKeyGraphValue().size(); j++) {
					BiMap bm = keyIndex.get(i).getKeyGraphValue().get(j);
					bw.write(bm.getEdgeLabel()+" "+bm.getSourceLabel()+" "+bm.getTargetLabel());
					bw.newLine();
				}
				bw.write("]");
				bw.newLine();
				bw.write("{");
				bw.newLine();
				for(int k = 0; k < valueIndex.get(i).getNamaGraph().size(); k++) {
					String temp = valueIndex.get(i).getNamaGraph().get(k);
					bw.write(temp);
					bw.newLine();
				}
				bw.write("}");
				bw.newLine();
			}
			bw.close();
			StartPage.gIndexDesc.writeToFile();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void init(BiMap bm, String namaGraph) {
		keyIndex = new ArrayList();
		valueIndex = new ArrayList();
		ArrayList<BiMap> temp = new ArrayList();
		ArrayList<String> tempName = new ArrayList<>();
		temp.add(bm);
		tempName.add(namaGraph);
		KeyGraphIndex ind = new KeyGraphIndex(temp);
		ValueGraphIndex vind = new ValueGraphIndex(tempName);
		this.keyIndex.add(ind);
		this.valueIndex.add(vind);
	}
	
	public static class KeyGraphIndex{
		private ArrayList<BiMap> keyGraphValue;
		
		public KeyGraphIndex(){
			this.keyGraphValue = new ArrayList();
		}
		
		public ArrayList<BiMap> getKeyGraphValue() {
			return keyGraphValue;
		}
		public void setKeyGraphValue(ArrayList<BiMap> keyGraphValue) {
			this.keyGraphValue = keyGraphValue;
		}
		
		public KeyGraphIndex(ArrayList<BiMap> bm) {
			this.keyGraphValue = bm;
		}
		
		public KeyGraphIndex(BiMap bm) {
			this.keyGraphValue = new ArrayList();
			this.keyGraphValue.add(bm);
		}
		
		public boolean isExist(BiMap bm) {
			boolean flag = false;
			for(int i = 0; i < keyGraphValue.size(); i++) {
				if(bm.equalsBM(keyGraphValue.get(i))) {
					flag = true;
				}
			}
			
			return flag;
		}
		
		public void destroy() {
			
		}
		
		public boolean equalsIndexValue(KeyGraphIndex kgi) {
			boolean flag = true;
			if(this.keyGraphValue.size() == kgi.keyGraphValue.size()) {
				for(int i = 0; i < this.keyGraphValue.size(); i++) {
					BiMap tempCompare = this.keyGraphValue.get(i);
					for(int j = 0; j < kgi.keyGraphValue.size(); j++) {
						boolean flag1 = tempCompare.equalsBM(kgi.keyGraphValue.get(i));
						flag = flag && flag1;
					}
				}
			}else {
				flag = false;
			}
			
			return flag;
		}
		
		public BiMap getFirstBM() {
			return keyGraphValue.get(0);
		}
		
		public void setNew(BiMap bm) {
			boolean flag = true;
			for(int i = 0; i < keyGraphValue.size(); i++) {
				if(this.keyGraphValue.get(i).equalsBM(bm)) {
					System.out.println("is true "+this.keyGraphValue.get(i).equalsBM(bm)+" "+i);
					flag = false;
				}
			}
			
			if(flag) {
				this.keyGraphValue.add(bm);
			}
		}
		
		public void setAja(BiMap b) {
			System.out.println("key "+b.getString());
			this.keyGraphValue.add(b);
		}
	}
	
	public static class ValueGraphIndex {
		private ArrayList<String> namaGraph;
		
		public ArrayList<String> getNamaGraph() {
			return namaGraph;
		}
		public void setNamaGraph(ArrayList<String> namaGraph) {
			this.namaGraph = namaGraph;
		}
		
		public ValueGraphIndex(ArrayList<String> namaGraph) {
			this.namaGraph = namaGraph;
		}
		
		public void setNew(String Graph) {
			
			if(!namaGraph.contains(Graph)) {
				System.out.println("masuk setNew namaGraph");
				this.namaGraph.add(Graph);
			}
		}
		
		public ValueGraphIndex(String namaGraph) {
			this.namaGraph = new ArrayList<>();
			this.namaGraph.add(namaGraph);
		}
	}
	
	public static void selectGraph(String namaGraph, String distanceFunction) {
		System.out.println("apakah ada "+ StartPage.gIndexDesc.getgDesc().get(namaGraph).size());
		ArrayList<Integer> tempPosition = StartPage.gIndexDesc.getgDesc().get(namaGraph);
		ArrayList<ArrayList<String>> temp = new ArrayList<>();
		for(int i = 0; i < tempPosition.size(); i++) {
			if(StartPage.gpi.valueIndex.get(tempPosition.get(i)).getNamaGraph().contains(namaGraph)) {
				temp.add(StartPage.gpi.valueIndex.get(tempPosition.get(i)).getNamaGraph());
			}
		}
		
		System.out.println("ukuran temp ialah "+temp.size());
		
		HashMap<String,ArrayList<Integer>> tempVValue = new HashMap<>();
		
		for(int i = 0; i < temp.size(); i++) {
			ArrayList<String> lala = temp.get(i);
			for(int j = 0; j < lala.size(); j++) {
				if(!lala.get(j).equals(namaGraph)) {
					for(int k = 0; k < temp.size(); k++) {
						if(temp.contains(lala.get(j))) {
								if (tempVValue.get(lala.get(j)) == null) {
									System.out.println("masuk sini suliiit di tenggorokan ");
									tempVValue.put(lala.get(j), new ArrayList<Integer>());
								}
								if (tempVValue.get(lala.get(j)) != null) {
									System.out.println("masuk sini lega di tenggorokan ");
									tempVValue.get(lala.get(j)).add(k);
								}
						}
					}
				}
			}
		}
		for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {
			System.out.println(" "+ee.getKey());
		}
		System.out.println(" print similarity ");
		for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {
			System.out.println(ee.getValue()+" "+eqluideanDistance(StartPage.gIndexDesc.getgDesc().get(namaGraph),StartPage.gIndexDesc.getgDesc().get(ee.getKey()), tempVValue.get(ee.getKey())));
		}
	}
	
	public static Double eqluideanDistance(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		if(graphInput.size() < graphTarget.size()) {
			score = Math.sqrt(graphTarget.size() - graphTemp.size());
		}else {
			score = Math.sqrt(graphInput.size() - graphTemp.size());
		}
		return score;
	}
}
