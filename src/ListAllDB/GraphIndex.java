package ListAllDB;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
	
	public void setNewValue(HashMap<String,ArrayList<BiMap>> hm,String namaGraph) {
		System.out.println("set new value");
		System.out.println("ukuran key index"+this.keyIndex.size());
		if(this.keyIndex.size() == 0) {
			System.out.println("test coba");
			initValue(hm,namaGraph);
		}else {
			for(int i = 0; i < this.keyIndex.size(); i++) {
				if(hm.get(this.keyIndex.get(i).getFirstBM().getSourceLabel()) != null) {
					ArrayList<BiMap> tempBm = new ArrayList();
					tempBm = hm.get(this.keyIndex.get(i).getFirstBM().getSourceLabel());
					if(tempBm.size() == this.keyIndex.get(i).getKeyGraphValue().size()) {
						this.valueIndex.get(i).setNew(namaGraph);
						hm.remove(this.keyIndex.get(i).getFirstBM().getSourceLabel());
					}
				}
			}
			
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
			}
			
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
		}
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
}
