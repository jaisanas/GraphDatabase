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
	
	public static ArrayList<Integer> getElementSama(ArrayList<Integer> e1, ArrayList<Integer> e2) {
		ArrayList<Integer> temp = new ArrayList<>();
		for(int i = 0; i < e1.size(); i++) {
			if(e2.contains(e1.get(i))) {
				temp.add(e1.get(i));
			}
		}
		
		return temp;
	}
	
	public static Double normalize(Double currValue, Double MaxValue, Double MinValue) {
		Double normal = (Double) (currValue - MinValue)/(MaxValue - MinValue);
		
		return normal;
	}
	
	public static void selectGraph(String namaGraph, String distanceFunction, String vectorSpaceModel, String thold) {
		Double tMagnitude = Double.parseDouble(thold);
		System.out.println("nama graf "+namaGraph);
		System.out.println("ukuran gIndexdesc "+StartPage.gIndexDesc.getgDesc().size());
		System.out.println("apakah ada "+ StartPage.gIndexDesc.getgDesc().get(namaGraph));
		ArrayList<Integer> tempPosition = StartPage.gIndexDesc.getgDesc().get(namaGraph);
		ArrayList<ArrayList<String>> temp = new ArrayList<>();
		//isi temp adalah kandidat yang masih mengandung nama graph query <nama graph, nama kandidat graf1, nama kandidat graf2, ....>
		for(int i = 0; i < tempPosition.size(); i++) {
			if(StartPage.gpi.valueIndex.get(tempPosition.get(i)).getNamaGraph().contains(namaGraph)) {
				temp.add(StartPage.gpi.valueIndex.get(tempPosition.get(i)).getNamaGraph());
			}
		}
		
		System.out.println("ukuran temp ialah "+temp.size());
		
		HashMap<String,ArrayList<Integer>> tempVValue = new HashMap<>();
		
		//cari tempVValue untuk dijadikan kandidat dengan menghapus nama graph query;
		for(int i = 0; i < temp.size(); i++) {
			ArrayList<String> tempEachTemp = temp.get(i);
			for(int j = 0; j < tempEachTemp.size(); j++) {
				if(!tempEachTemp.get(j).equals(namaGraph)) {
					ArrayList<Integer> nilaiTemp = getElementSama(StartPage.gIndexDesc.getgDesc().get(namaGraph), StartPage.gIndexDesc.getgDesc().get(tempEachTemp.get(j)));
					if (tempVValue.get(tempEachTemp.get(j)) == null) {
						tempVValue.put(tempEachTemp.get(j), nilaiTemp);
					}
				}
			}
		}
		
		
		for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {
			System.out.println(ee.getKey()+" "+ee.getValue());
		}
		
		/*System.out.println(" print similarity Euclidean Distance ");
		for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {
			System.out.println(ee.getKey()+" "+eqluideanDistance(StartPage.gIndexDesc.getgDesc().get(namaGraph),StartPage.gIndexDesc.getgDesc().get(ee.getKey()), tempVValue.get(ee.getKey())));
			
		} */
		
		HashMap<String,Double> result = new HashMap<>();
		ArrayList<String> candidat = new ArrayList<>();
		ArrayList<Double> candidatValue = new ArrayList<>();
		
		if(distanceFunction.equals("euclidean")) {
			if(vectorSpaceModel.equals("v1.0")) {
				System.out.println("================================================= euclidean v1.0");
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {
					
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					//System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					Double nilai = euclideanDistance(graphInput,graphTarget,graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}else if(vectorSpaceModel.equals("v1.1")) {
				System.out.println("================================================= euclidean v1.1");
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {
					
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					//System.out.println(ee.getKey()+" "+euclideanDistanceV11(graphInput,graphTarget,graphTemp));
					Double nilai = euclideanDistanceV11(graphInput,graphTarget,graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(),nilai);
				}
			} else if(vectorSpaceModel.equals("v2.0")) {
				System.out.println("================================================= euclidean v2.0");
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {
					ArrayList<Integer> graphInput = null;
					graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					ArrayList<Integer> graphTarget = null;
					graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					ArrayList<Integer> graphTemp = null;
					graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					}
					Double nilai = euclideanDistanceV20(graphInput,graphTarget,graphTemp);
					//System.out.println(ee.getKey()+" "+euclideanDistanceV20(graphInput,graphTarget,graphTemp));
					if(nilai <= tMagnitude) result.put(ee.getKey(),nilai);
			  }
		   }else {
			   	System.out.println("========================================= euclidean V2.1");
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {
					ArrayList<Integer> graphInput = null;
					graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					ArrayList<Integer> graphTarget = null;
					graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					ArrayList<Integer> graphTemp = null;
					graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					}
					
					Double nilai = euclideanDistanceV21(graphInput,graphTarget,graphTemp);
					
					//System.out.println(ee.getKey()+" "+euclideanDistanceV21(graphInput,graphTarget,graphTemp));
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
		   }
		} else if(distanceFunction.equals("city_block")) {
			if(vectorSpaceModel.equals("v1.0")){
				System.out.println("========================================= city block V1.0");
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					//System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = cityBlock(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}else if(vectorSpaceModel.equals("v1.1")) {
				System.out.println("========================================= city block V1.1");
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					//System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					Double nilai = cityBlockV11(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}else if(vectorSpaceModel.equals("v2.0")) {
				System.out.println("========================================= city block V2.0");
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					//System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					System.out.println(ee.getKey());
					Double nilai = cityBlockV20(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}else {
				System.out.println("========================================= city block V2.1");
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					//System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					Double nilai = cityBlockV21(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}
		}else if(distanceFunction.equals("chebyshev")){
			if(vectorSpaceModel.equals("v1.0")) {
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					//System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = chebyshev(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}else if(vectorSpaceModel.equals("v1.1")) {
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					//System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = chebyshevV11(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}else if(vectorSpaceModel.equals("v2.0")) {
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					//System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = chebyshevV20(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}else if(vectorSpaceModel.equals("v2.1")) {
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					//System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = chebyshevV21(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}
		}else if(distanceFunction.equals("sorensen")) {
			if(vectorSpaceModel.equals("v1.0")) {
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					//System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					System.out.println(ee.getKey());
					Double nilai = sorensen(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}else if(vectorSpaceModel.equals("v1.1")){
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = sorensenV11(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}else if(vectorSpaceModel.equals("v2.0")){
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = sorensenV20(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}else if(vectorSpaceModel.equals("v2.1")){
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = sorensenV21(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}
		}else if (distanceFunction.equals("soergel")){
			if(vectorSpaceModel.equals("v1.0")) {
			for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
				ArrayList<Integer> graphInput = new ArrayList<>();
				for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
					graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
				}
				
				ArrayList<Integer> graphTarget = new ArrayList<>();
				for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
					graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
				}
				
				ArrayList<Integer> graphTemp =  new ArrayList<>();
				for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
					graphTemp.add(tempVValue.get(ee.getKey()).get(i));
				} 
				System.out.println(ee.getKey());
				//candidat.add(ee.getKey());
				//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
				//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
				
				Double nilai = soergel(graphInput, graphTarget, graphTemp);
				if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
			}
		  }else if(vectorSpaceModel.equals("v1.1")){
			  System.out.println("======================================= soregel v1.1");
			  for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = soergelV11(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				} 
		  }else if(vectorSpaceModel.equals("v2.0")) {
			  System.out.println("======================================= soregel v2.0");
			  for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = soergelV20(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				} 
		  }else {
			  System.out.println("======================================= soregel v2.1");
			  for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = soergelV21(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				} 
		  }
		}else if(distanceFunction.equals("lorentzian")) {
			if(vectorSpaceModel.equals("v1.0")) {
				System.out.println("======================================= lorentzian v1.0");
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = lorentzian(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}else if(vectorSpaceModel.equals("v1.1")) {
				System.out.println("======================================= lorentzian v1.1");
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = lorentzianV11(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}else if(vectorSpaceModel.equals("v2.0")) {
				System.out.println("====================================== lorentzian v2.0");
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = lorentzianV20(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}else {
				System.out.println("======================================= lorentzian v2.1");
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = lorentzianV21(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}
		}else if(distanceFunction.equals("motyka")) {
			if(vectorSpaceModel.equals("v1.0")) {
				System.out.println("======================================= motyka v1.0");
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = motyka(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}else if(vectorSpaceModel.equals("v1.1")) {
				System.out.println("======================================= motyka v1.1");
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = motykaV11(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}else if(vectorSpaceModel.equals("v2.0")) {
				System.out.println("======================================= motyka v2.0");
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = motykaV20(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}else if(vectorSpaceModel.equals("v2.1")){
				System.out.println("======================================= motyka v2.1");
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = motykaV21(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}
		}else if(distanceFunction.equals("tanimoto")) {
			if(vectorSpaceModel.equals("v1.0")){
				System.out.println("======================================= tanimoto v1.0");
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = tanimoto(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}else if(vectorSpaceModel.equals("v1.1")){
				System.out.println("======================================= tanimoto v1.1");
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = tanimotoV11(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}else if(vectorSpaceModel.equals("v2.0")) {
				System.out.println("======================================= tanimoto v2.0");
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = tanimotoV20(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}else if(vectorSpaceModel.equals("v2.1")){
				System.out.println("======================================= tanimoto v2.1");
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = tanimotoV21(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}
		}else if(distanceFunction.equals("cosine")) {
			if(vectorSpaceModel.equals("v1.0")) {
				System.out.println("======================================= cosine v1.0");
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = cosine(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}else if(vectorSpaceModel.equals("v1.1")){
				System.out.println("======================================= cosine v1.1");
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = cosineV11(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}else if(vectorSpaceModel.equals("v2.0")) {
				System.out.println("======================================= cosine v2.0");
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = cosineV20(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}else {
				System.out.println("======================================= cosine v2.1");
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = cosineV21(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}
		}else if(distanceFunction.equals("jaccard")) {
			if(vectorSpaceModel.equals("v1.0")){
				System.out.println("======================================= jaccard v1.0");
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = jaccard(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}else if(vectorSpaceModel.equals("v1.1")) {
				System.out.println("======================================= jaccard v1.1");
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = jaccardV11(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}else if(vectorSpaceModel.equals("v2.0")) {
				System.out.println("======================================= jaccard v2.0");
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = jaccardV20(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}else if(vectorSpaceModel.equals("v2.1")){
				System.out.println("======================================= jaccard v2.1");
				for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {	
					ArrayList<Integer> graphInput = new ArrayList<>();
					for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
						graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
					}
					
					ArrayList<Integer> graphTarget = new ArrayList<>();
					for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
						graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
					}
					
					ArrayList<Integer> graphTemp =  new ArrayList<>();
					for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
						graphTemp.add(tempVValue.get(ee.getKey()).get(i));
					} 
					System.out.println(ee.getKey());
					//candidat.add(ee.getKey());
					//candidatValue.add(euclideanDistance(graphInput,graphTarget,graphTemp));
					//System.out.println(euclideanDistance(graphInput,graphTarget,graphTemp));
					
					Double nilai = jaccardV21(graphInput, graphTarget, graphTemp);
					if(nilai <= tMagnitude) result.put(ee.getKey(), nilai);
				}
			}
		}
		
		StartPage.result = result;
		QueryResults.showQueryResults(namaGraph,distanceFunction); 
		
		/* System.out.println("print similarity Euclidean Distance v1_0");
		for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {
			
			ArrayList<Integer> graphInput = new ArrayList<>();
			for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
				graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
			}
			
			ArrayList<Integer> graphTarget = new ArrayList<>();
			for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
				graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
			}
			
			
			
			ArrayList<Integer> graphTemp =  new ArrayList<>();
			for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
				graphTemp.add(tempVValue.get(ee.getKey()).get(i));
			} 
			System.out.println(ee.getKey()+" "+euclideanDistance(graphInput,graphTarget,graphTemp));
			
			//result.put(ee.getKey(),eqluideanDistanceV11(graphInput,graphTarget,graphTemp));
		}
		
		System.out.println("print similarity Euclidean Distance v1_1");
		for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {
			
			ArrayList<Integer> graphInput = new ArrayList<>();
			for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
				graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
			}
			
			ArrayList<Integer> graphTarget = new ArrayList<>();
			for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
				graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
			}
			
			
			
			ArrayList<Integer> graphTemp =  new ArrayList<>();
			for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
				graphTemp.add(tempVValue.get(ee.getKey()).get(i));
			} 
			System.out.println(ee.getKey()+" "+euclideanDistanceV11(graphInput,graphTarget,graphTemp));
			
			//result.put(ee.getKey(),eqluideanDistanceV11(graphInput,graphTarget,graphTemp));
		} 
		
		ArrayList<Double> data = new ArrayList<>();
		System.out.println("print similarity Euclidean Distance v2_0");
		for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {
			ArrayList<Integer> graphInput = null;
			graphInput = new ArrayList<>();
			for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
				graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
			}
			ArrayList<Integer> graphTarget = null;
			graphTarget = new ArrayList<>();
			for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
				graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
			}
			ArrayList<Integer> graphTemp = null;
			graphTemp =  new ArrayList<>();
			for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
				graphTemp.add(tempVValue.get(ee.getKey()).get(i));
			}
			Double nilai = euclideanDistanceV20(graphInput,graphTarget,graphTemp);
			//System.out.println(ee.getKey()+" "+nilai);
			///Double nilai = euclideanDistanceV20(graphInput,graphTarget,graphTemp);
			//data.add(0.77777777777777777777777777777);
			//System.out.println("nilai "+nilai);
			result.put(ee.getKey(),nilai);
		}
		StartPage.result = result;
		QueryResults.showQueryResults(namaGraph,distanceFunction);
		
		/*System.out.println("print similarity Euclidean Distance v2_1");
		for (Entry<String, ArrayList<Integer>> ee : tempVValue.entrySet()) {
			ArrayList<Integer> graphInput = null;
			graphInput = new ArrayList<>();
			for(int i = 0; i < StartPage.gIndexDesc.getgDesc().get(namaGraph).size(); i++) {
				graphInput.add(StartPage.gIndexDesc.getgDesc().get(namaGraph).get(i));
			}
			ArrayList<Integer> graphTarget = null;
			graphTarget = new ArrayList<>();
			for(int i =0; i < StartPage.gIndexDesc.getgDesc().get(ee.getKey()).size(); i++) {
				graphTarget.add(StartPage.gIndexDesc.getgDesc().get(ee.getKey()).get(i));
			}
			ArrayList<Integer> graphTemp = null;
			graphTemp =  new ArrayList<>();
			for(int i = 0; i < tempVValue.get(ee.getKey()).size(); i++) {
				graphTemp.add(tempVValue.get(ee.getKey()).get(i));
			}
			
			System.out.println(ee.getKey()+" "+euclideanDistanceV21(graphInput,graphTarget,graphTemp));
			//result.put(ee.getKey(),euclideanDistanceV21(graphInput,graphTarget,graphTemp));
		} */
	
	}
	
	/*=======================================Pemodelan Vektor Versi v1.0=============================================================*/
	//Euclidean Distance V1.0
	public static Double euclideanDistance(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		
		int tempSize;
		if(graphTarget.size() >= graphInput.size()) {
			tempSize = graphTarget.size();
		}else {
			tempSize = graphInput.size();
		}
		
		Integer [] input = new Integer[tempSize];
		Integer [] target = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		
		System.out.println("ukuran graph Temp "+graphTemp.size());
		for(int i = 0; i < graphTemp.size(); i++) {
			input[i] = graphTemp.get(i);
			target[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		
		
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <target.length; i++) {
				target[i] = null;
			}
			for(int i = graphTemp.size(); i < input.length; i++) {
				int y = i - graphTemp.size();
				input[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <input.length; i++) {
				input[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <target.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				target[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <target.length; i++) {
				target[i] = null;
			}
			for(int i = graphTemp.size(); i < input.length; i++) {
				input[i] = null;
			}
		}
		
		for(int i = 0; i < input.length; i++) {
			if(input[i] != null) {
				input[i] = 1;
			}else {
				input[i] = 0;
			}
		}
		
		for(int i = 0; i < target.length; i++) {
			if(target[i] != null) {
				target[i] = 1;
			}else {
				target[i] = 0;
			}
		}
		
		
		Double score = (double) Math.abs(input[0] - target[0]) * Math.abs(input[0] - target[0]);
		for(int i =1; i < input.length; i++) {
			score = score + (Math.abs(input[i] - target[i]) *Math.abs(input[i] - target[i]));
		}
		
		
		Double MinValue = 0.0;
		int tempMaxValue = 0;
		for(int i = 0; i < input.length; i++) {
			tempMaxValue++;
		}
		
		Double MaxValue = Math.sqrt(tempMaxValue);
		
		Double finalScore = normalize(score,MaxValue,MinValue);
		//System.out.println("final Score "+finalScore);
		return finalScore;
	}
	
	//Cosine Similarity V1.0
	public static Double cosineSimilarity(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		score = (getPembilang(graphInput, graphTarget, graphTemp))*(getPenyebut(graphInput, graphTarget, graphTemp));
		Double currValue = 1.0 - score;
		Double finalScore = currValue;
		return finalScore;
	}
	public static Double getPenyebut(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int tempSize;
		if(graphTarget.size() >= graphInput.size()) {
			tempSize = graphTarget.size();
		}else {
			tempSize = graphInput.size();
		}
		
		Integer [] input = new Integer[tempSize];
		Integer [] target = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			input[i] = graphTemp.get(i);
			target[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		
		
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <target.length; i++) {
				target[i] = null;
			}
			for(int i = graphTemp.size(); i < input.length; i++) {
				int y = i - graphTemp.size();
				input[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <input.length; i++) {
				input[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <target.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				target[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <target.length; i++) {
				target[i] = null;
			}
			for(int i = graphTemp.size(); i < input.length; i++) {
				input[i] = null;
			}
		}
		
		for(int i = 0; i < input.length; i++) {
			if(input[i] != null) {
				input[i] = 1;
			}else {
				input[i] = 0;
			}
		}
		
		for(int i = 0; i < target.length; i++) {
			if(target[i] != null) {
				target[i] = 1;
			}else {
				target[i] = 0;
			}
		}
		
		int value = input[0]*target[0];
		for(int i = 1; i < input.length; i++) {
			value = value + input[i]*target[i];
		}
		Double tempValue = (double) value;
		score = tempValue;
		return score;
	}
	public static Double getPembilang(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int tempSize;
		if(graphTarget.size() >= graphInput.size()) {
			tempSize = graphTarget.size();
		}else {
			tempSize = graphInput.size();
		}
		
		Integer [] input = new Integer[tempSize];
		Integer [] target = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			input[i] = graphTemp.get(i);
			target[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		
		
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <target.length; i++) {
				target[i] = null;
			}
			for(int i = graphTemp.size(); i < input.length; i++) {
				int y = i - graphTemp.size();
				input[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <input.length; i++) {
				input[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <target.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				target[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <target.length; i++) {
				target[i] = null;
			}
			for(int i = graphTemp.size(); i < input.length; i++) {
				input[i] = null;
			}
		}
		
		for(int i = 0; i < input.length; i++) {
			if(input[i] != null) {
				input[i] = 1;
			}else {
				input[i] = 0;
			}
		}
		
		for(int i = 0; i < target.length; i++) {
			if(target[i] != null) {
				target[i] = 1;
			}else {
				target[i] = 0;
			}
		}
		
		int value = input[0]*target[0];
		for(int i = 1; i < input.length; i++) {
			value = value + input[i]*target[i];
		}
		Double tempValue = (double) value;
		score = tempValue;
		return score;
	}
	
	//city block similarity
	public static Double cityBlock(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp){
		Double score;
		int tempSize;
		if(graphTarget.size() >= graphInput.size()) {
			tempSize = graphTarget.size();
		}else {
			tempSize = graphInput.size();
		}
		
		Integer [] input = new Integer[tempSize];
		Integer [] target = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			input[i] = graphTemp.get(i);
			target[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		
		
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <target.length; i++) {
				target[i] = null;
			}
			for(int i = graphTemp.size(); i < input.length; i++) {
				int y = i - graphTemp.size();
				input[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <input.length; i++) {
				input[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <target.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				target[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <target.length; i++) {
				target[i] = null;
			}
			for(int i = graphTemp.size(); i < input.length; i++) {
				input[i] = null;
			}
		}
		
		for(int i = 0; i < input.length; i++) {
			if(input[i] != null) {
				input[i] = 1;
			}else {
				input[i] = 0;
			}
		}
		
		for(int i = 0; i < target.length; i++) {
			if(target[i] != null) {
				target[i] = 1;
			}else {
				target[i] = 0;
			}
		}
		
		int value = Math.abs(input[0] - target[0]);
		for(int i = 1; i < input.length; i++) {
			value = value + Math.abs(input[i] - target[i]);
		}
		Double MinValue = 0.0;
		int tempMaxValue = 0;
		for(int i = 0; i < input.length; i++) {
			tempMaxValue++;
		}
		
		double MaxValue = (double) tempMaxValue;
		
		Double tempValue = (double) value;
		score = normalize(tempValue, MaxValue, MinValue);
		return score;
	}
	//chebyshev
	public static Double chebyshev(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp){
		Double score;
		int tempSize;
		if(graphTarget.size() >= graphInput.size()) {
			tempSize = graphTarget.size();
		}else {
			tempSize = graphInput.size();
		}
		
		Integer [] input = new Integer[tempSize];
		Integer [] target = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			input[i] = graphTemp.get(i);
			target[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <target.length; i++) {
				target[i] = null;
			}
			for(int i = graphTemp.size(); i < input.length; i++) {
				int y = i - graphTemp.size();
				input[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <input.length; i++) {
				input[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <target.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				target[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <target.length; i++) {
				target[i] = null;
			}
			for(int i = graphTemp.size(); i < input.length; i++) {
				input = null;
			}
		}
		
		for(int i = 0; i < input.length; i++) {
			if(input[i] != null) {
				input[i] = 1;
			}else {
				input[i] = 0;
			}
		}
		
		for(int i = 0; i < target.length; i++) {
			if(target[i] != null) {
				target[i] = 1;
			}else {
				target[i] = 0;
			}
		}
		
		int value = Math.abs(input[0] - target[0]);
		for(int i = 1; i < input.length; i++) {
			if(value < Math.abs(input[i] -target[i])){
				value = Math.abs(input[i] -target[i]);
			}
		}
		
		Double MinValue = 0.0;
		Double MaxValue = 1.0;
		
		Double tempValue = (double) value;
		score = normalize(tempValue, MaxValue, MinValue);
		return score;
	}
	
	//sorensen distance
	public static Double sorensen(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp){
		Double score;
		int tempSize;
		if(graphTarget.size() >= graphInput.size()) {
			tempSize = graphTarget.size();
		}else {
			tempSize = graphInput.size();
		}
		
		Integer [] input = new Integer[tempSize];
		Integer [] target = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			input[i] = graphTemp.get(i);
			target[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <target.length; i++) {
				target[i] = null;
			}
			for(int i = graphTemp.size(); i < input.length; i++) {
				int y = i - graphTemp.size();
				input[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <input.length; i++) {
				input[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <target.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				target[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <target.length; i++) {
				target[i] = null;
			}
			for(int i = graphTemp.size(); i < input.length; i++) {
				input = null;
			}
		}
		
		for(int i = 0; i < input.length; i++) {
			if(input[i] != null) {
				input[i] = 1;
			}else {
				input[i] = 0;
			}
		}
		
		for(int i = 0; i < target.length; i++) {
			if(target[i] != null) {
				target[i] = 1;
			}else {
				target[i] = 0;
			}
		}
		
		printArray(input);
		printArray(target);
		
		Double pembilang = (double) Math.abs(input[0] - target[0]);
		for(int i = 1; i < input.length; i++) {
			pembilang = pembilang + Math.abs(input[i] -target[i]);
		}
		System.out.println("pembilang "+pembilang);
		Double penyebut = (double) input[0] + target[0];
		for(int i = 1; i < input.length; i++) {
			penyebut = penyebut + (input[i] + target[i]);
		}
		System.out.println("penyebut "+penyebut);
		
		double tempValue = (double) (pembilang/penyebut);
		System.out.println("tempValue "+(double) pembilang/penyebut);
		score = tempValue;
		return score;
	}
	
	//gower distance
	public static Double gower(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp){
		Double score;
		int tempSize;
		if(graphTarget.size() >= graphInput.size()) {
			tempSize = graphTarget.size();
		}else {
			tempSize = graphInput.size();
		}
		
		Integer [] input = new Integer[tempSize];
		Integer [] target = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			input[i] = graphTemp.get(i);
			target[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <target.length; i++) {
				target[i] = null;
			}
			for(int i = graphTemp.size(); i < input.length; i++) {
				int y = i - graphTemp.size();
				input[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <input.length; i++) {
				input[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <target.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				target[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <target.length; i++) {
				target[i] = null;
			}
			for(int i = graphTemp.size(); i < input.length; i++) {
				input = null;
			}
		}
		
		for(int i = 0; i < input.length; i++) {
			if(input[i] != null) {
				input[i] = 1;
			}else {
				input[i] = 0;
			}
		}
		
		for(int i = 0; i < target.length; i++) {
			if(target[i] != null) {
				target[i] = 1;
			}else {
				input[i] = 0;
			}
		}
		
		int pembilang = Math.abs(input[0] - target[0]);
		for(int i = 1; i < input.length; i++) {
			pembilang = pembilang + Math.abs(input[i] -target[i]);
		}
		int penyebut = input.length;
		
		Double tempValue = (double) (pembilang/penyebut);
		score = tempValue;
		return score;
	}
	
	//soergel diatnce
	public static Double soergel(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp){
		Double score;
		int tempSize;
		if(graphTarget.size() >= graphInput.size()) {
			tempSize = graphTarget.size();
		}else {
			tempSize = graphInput.size();
		}
		
		Integer [] input = new Integer[tempSize];
		Integer [] target = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			input[i] = graphTemp.get(i);
			target[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <target.length; i++) {
				target[i] = null;
			}
			for(int i = graphTemp.size(); i < input.length; i++) {
				int y = i - graphTemp.size();
				input[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <input.length; i++) {
				input[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <target.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				target[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <target.length; i++) {
				target[i] = null;
			}
			for(int i = graphTemp.size(); i < input.length; i++) {
				input = null;
			}
		}
		
		for(int i = 0; i < input.length; i++) {
			if(input[i] != null) {
				input[i] = 1;
			}else {
				input[i] = 0;
			}
		}
		
		for(int i = 0; i < target.length; i++) {
			if(target[i] != null) {
				target[i] = 1;
			}else {
				target[i] = 0;
			}
		}
		printArray(input);
		printArray(target);
		Double pembilang = (double) Math.abs(input[0] - target[0]);
		for(int i = 1; i < input.length; i++) {
			pembilang = pembilang + Math.abs(input[i] -target[i]);
		}
		Double penyebut;
		if(input[0] >= target[0]) {
			penyebut = (double) input[0];
		}else {
			penyebut = (double) target[0];
		}
		for(int i = 1; i < input.length; i++) {
			if(input[i] >= target[i]) {
				penyebut = penyebut + input[i];
			}else {
				penyebut = penyebut + target[i];
			}
		}
		Double tempValue = (double) (pembilang/penyebut);
		score = tempValue;
		return score;
	}
	
	//kulczynki diatnce
		public static Double kulczynki(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp){
			Double score;
			int tempSize;
			if(graphTarget.size() >= graphInput.size()) {
				tempSize = graphTarget.size();
			}else {
				tempSize = graphInput.size();
			}
			
			Integer [] input = new Integer[tempSize];
			Integer [] target = new Integer[tempSize];
			//isi value input ataupun target dari graphTemp
			for(int i = 0; i < graphTemp.size(); i++) {
				input[i] = graphTemp.get(i);
				target[i] = graphTemp.get(i);
			}
			
			//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
			for(int i = 0; i < graphInput.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphInput.get(i) == graphTemp.get(j)) {
						graphInput.remove(i);
					}
				}
			}
			for(int i = 0; i < graphTarget.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphTarget.get(i) == graphTemp.get(j)) {
						graphTarget.remove(i);
					}
				}
			}
			if(graphInput.size() > graphTarget.size()) {
				//System.out.println("graphInput.size() >= graphTarget.size()");
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					int y = i - graphTemp.size();
					input[i] = graphInput.get(y);
				}
			}else if(graphInput.size() < graphTarget.size()) {
				//System.out.println("graphInput.size() < graphTarget.size()");
				for(int i = graphTemp.size(); i <input.length; i++) {
					input[i] = null;
				}
				//System.out.println("ddddd "+targetV2_0.length);
				for(int i = graphTemp.size(); i <target.length; i++) {
					int y = i - graphTemp.size();
					//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
					target[i] = graphTarget.get(y);
				}
			}else {
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					input = null;
				}
			}
			
			for(int i = 0; i < input.length; i++) {
				if(input[i] != null) {
					input[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			for(int i = 0; i < target.length; i++) {
				if(target[i] != null) {
					target[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			int pembilang = Math.abs(input[0] - target[0]);
			for(int i = 0; i < input.length; i++) {
				pembilang = pembilang + Math.abs(input[i] -target[i]);
			}
			int penyebut;
			if(input[0] >= target[0]) {
				penyebut = target[0];
			}else {
				penyebut = input[0];
			}
			for(int i = 1; i < input.length; i++) {
				if(input[i] >= target[i]) {
					penyebut = penyebut + target[i];
				}else {
					penyebut = penyebut + input[i];
				}
			}
			Double tempValue = (double) (pembilang/penyebut);
			score = tempValue;
			return score;
		}
	
		//Canberra diatnce
		public static Double canberra(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp){
			Double score;
			int tempSize;
			if(graphTarget.size() >= graphInput.size()) {
				tempSize = graphTarget.size();
			}else {
				tempSize = graphInput.size();
			}
			
			Integer [] input = new Integer[tempSize];
			Integer [] target = new Integer[tempSize];
			//isi value input ataupun target dari graphTemp
			for(int i = 0; i < graphTemp.size(); i++) {
				input[i] = graphTemp.get(i);
				target[i] = graphTemp.get(i);
			}
			
			//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
			for(int i = 0; i < graphInput.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphInput.get(i) == graphTemp.get(j)) {
						graphInput.remove(i);
					}
				}
			}
			for(int i = 0; i < graphTarget.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphTarget.get(i) == graphTemp.get(j)) {
						graphTarget.remove(i);
					}
				}
			}
			if(graphInput.size() > graphTarget.size()) {
				//System.out.println("graphInput.size() >= graphTarget.size()");
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					int y = i - graphTemp.size();
					input[i] = graphInput.get(y);
				}
			}else if(graphInput.size() < graphTarget.size()) {
				//System.out.println("graphInput.size() < graphTarget.size()");
				for(int i = graphTemp.size(); i <input.length; i++) {
					input[i] = null;
				}
				//System.out.println("ddddd "+targetV2_0.length);
				for(int i = graphTemp.size(); i <target.length; i++) {
					int y = i - graphTemp.size();
					//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
					target[i] = graphTarget.get(y);
				}
			}else {
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					input = null;
				}
			}
			
			for(int i = 0; i < input.length; i++) {
				if(input[i] != null) {
					input[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			for(int i = 0; i < target.length; i++) {
				if(target[i] != null) {
					target[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			int pembilang = Math.abs(input[0] - target[0]);
			int penyebut  = input[0] + target[0];
			double value = (double) pembilang/penyebut;
			for(int i = 1; i < input.length; i++) {
				value = value + ((double) ((Math.abs(input[i]-target[i])/(input[i]+target[i]))));
			}
			Double tempValue = value;
			score = tempValue;
			return score;
		}		
		
	
		//lorentzian distance
		public static Double lorentzian(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp){
			Double score;
			int tempSize;
			if(graphTarget.size() >= graphInput.size()) {
				tempSize = graphTarget.size();
			}else {
				tempSize = graphInput.size();
			}
			
			Integer [] input = new Integer[tempSize];
			Integer [] target = new Integer[tempSize];
			//isi value input ataupun target dari graphTemp
			for(int i = 0; i < graphTemp.size(); i++) {
				input[i] = graphTemp.get(i);
				target[i] = graphTemp.get(i);
			}
			
			//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
			for(int i = 0; i < graphInput.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphInput.get(i) == graphTemp.get(j)) {
						graphInput.remove(i);
					}
				}
			}
			for(int i = 0; i < graphTarget.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphTarget.get(i) == graphTemp.get(j)) {
						graphTarget.remove(i);
					}
				}
			}
			if(graphInput.size() > graphTarget.size()) {
				//System.out.println("graphInput.size() >= graphTarget.size()");
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					int y = i - graphTemp.size();
					input[i] = graphInput.get(y);
				}
			}else if(graphInput.size() < graphTarget.size()) {
				//System.out.println("graphInput.size() < graphTarget.size()");
				for(int i = graphTemp.size(); i <input.length; i++) {
					input[i] = null;
				}
				//System.out.println("ddddd "+targetV2_0.length);
				for(int i = graphTemp.size(); i <target.length; i++) {
					int y = i - graphTemp.size();
					//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
					target[i] = graphTarget.get(y);
				}
			}else {
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					input = null;
				}
			}
			
			for(int i = 0; i < input.length; i++) {
				if(input[i] != null) {
					input[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			for(int i = 0; i < target.length; i++) {
				if(target[i] != null) {
					target[i] = 1;
				}else {
					target[i] = 0;
				}
			}
			printArray(input);
			printArray(target);
			
			double value = Math.log(1 + ((double) Math.abs(input[0] - target[0])));
			for(int i = 0; i < input.length; i++) {
				value = value + Math.log(1 + ((double) Math.abs(input[i] - target[i])));
			}
			
			double MinValue = 0.0;
			double MaxValue = input.length * Math.log(2);
			
			Double tempValue = value;
			score = normalize(tempValue, MaxValue, MinValue);
			return score;
		}		
		
	//11. Intersection
	public static Double intersection(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp){
			Double score;
			int tempSize;
			if(graphTarget.size() >= graphInput.size()) {
				tempSize = graphTarget.size();
			}else {
				tempSize = graphInput.size();
			}
			
			Integer [] input = new Integer[tempSize];
			Integer [] target = new Integer[tempSize];
			//isi value input ataupun target dari graphTemp
			for(int i = 0; i < graphTemp.size(); i++) {
				input[i] = graphTemp.get(i);
				target[i] = graphTemp.get(i);
			}
			
			//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
			for(int i = 0; i < graphInput.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphInput.get(i) == graphTemp.get(j)) {
						graphInput.remove(i);
					}
				}
			}
			for(int i = 0; i < graphTarget.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphTarget.get(i) == graphTemp.get(j)) {
						graphTarget.remove(i);
					}
				}
			}
			if(graphInput.size() > graphTarget.size()) {
				//System.out.println("graphInput.size() >= graphTarget.size()");
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					int y = i - graphTemp.size();
					input[i] = graphInput.get(y);
				}
			}else if(graphInput.size() < graphTarget.size()) {
				//System.out.println("graphInput.size() < graphTarget.size()");
				for(int i = graphTemp.size(); i <input.length; i++) {
					input[i] = null;
				}
				//System.out.println("ddddd "+targetV2_0.length);
				for(int i = graphTemp.size(); i <target.length; i++) {
					int y = i - graphTemp.size();
					//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
					target[i] = graphTarget.get(y);
				}
			}else {
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					input = null;
				}
			}
			
			for(int i = 0; i < input.length; i++) {
				if(input[i] != null) {
					input[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			for(int i = 0; i < target.length; i++) {
				if(target[i] != null) {
					target[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			double value = (double) Math.abs(input[0] - target[0]);
			for(int i = 1; i < input.length; i++) {
				value = value + (double) Math.abs(input[i] - target[i]);
			}
			Double tempValue = value;
			score = tempValue;
			return score;
		}
		
		
	//12. Wave Hedges
		public static Double waveHedges(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp){
			Double score;
			int tempSize;
			if(graphTarget.size() >= graphInput.size()) {
				tempSize = graphTarget.size();
			}else {
				tempSize = graphInput.size();
			}
			
			Integer [] input = new Integer[tempSize];
			Integer [] target = new Integer[tempSize];
			//isi value input ataupun target dari graphTemp
			for(int i = 0; i < graphTemp.size(); i++) {
				input[i] = graphTemp.get(i);
				target[i] = graphTemp.get(i);
			}
			
			//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
			for(int i = 0; i < graphInput.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphInput.get(i) == graphTemp.get(j)) {
						graphInput.remove(i);
					}
				}
			}
			for(int i = 0; i < graphTarget.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphTarget.get(i) == graphTemp.get(j)) {
						graphTarget.remove(i);
					}
				}
			}
			if(graphInput.size() > graphTarget.size()) {
				//System.out.println("graphInput.size() >= graphTarget.size()");
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					int y = i - graphTemp.size();
					input[i] = graphInput.get(y);
				}
			}else if(graphInput.size() < graphTarget.size()) {
				//System.out.println("graphInput.size() < graphTarget.size()");
				for(int i = graphTemp.size(); i <input.length; i++) {
					input[i] = null;
				}
				//System.out.println("ddddd "+targetV2_0.length);
				for(int i = graphTemp.size(); i <target.length; i++) {
					int y = i - graphTemp.size();
					//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
					target[i] = graphTarget.get(y);
				}
			}else {
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					input = null;
				}
			}
			
			for(int i = 0; i < input.length; i++) {
				if(input[i] != null) {
					input[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			for(int i = 0; i < target.length; i++) {
				if(target[i] != null) {
					target[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			
			double value;
			
			if(input[0] > target[0]) {
				value = (double) (Math.abs(input[0] - target[0])/input[0]);
			}else {
				value = (double) (Math.abs(input[0] - target[0])/target[0]);
			}
			for(int i = 1; i < input.length; i++) {
				if(input[i] > target[i]) {
					value = value + (double) (Math.abs(input[i] - target[i])/input[i]);
				}else {
					value = value + (double) (Math.abs(input[i] - target[i])/target[i]);
				}
			}
			Double tempValue = value;
			score = tempValue;
			return score;
		}
	//13. czekanowski
		public static Double czekanowski(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp){
			Double score;
			int tempSize;
			if(graphTarget.size() >= graphInput.size()) {
				tempSize = graphTarget.size();
			}else {
				tempSize = graphInput.size();
			}
			
			Integer [] input = new Integer[tempSize];
			Integer [] target = new Integer[tempSize];
			//isi value input ataupun target dari graphTemp
			for(int i = 0; i < graphTemp.size(); i++) {
				input[i] = graphTemp.get(i);
				target[i] = graphTemp.get(i);
			}
			
			//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
			for(int i = 0; i < graphInput.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphInput.get(i) == graphTemp.get(j)) {
						graphInput.remove(i);
					}
				}
			}
			for(int i = 0; i < graphTarget.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphTarget.get(i) == graphTemp.get(j)) {
						graphTarget.remove(i);
					}
				}
			}
			if(graphInput.size() > graphTarget.size()) {
				//System.out.println("graphInput.size() >= graphTarget.size()");
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					int y = i - graphTemp.size();
					input[i] = graphInput.get(y);
				}
			}else if(graphInput.size() < graphTarget.size()) {
				//System.out.println("graphInput.size() < graphTarget.size()");
				for(int i = graphTemp.size(); i <input.length; i++) {
					input[i] = null;
				}
				//System.out.println("ddddd "+targetV2_0.length);
				for(int i = graphTemp.size(); i <target.length; i++) {
					int y = i - graphTemp.size();
					//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
					target[i] = graphTarget.get(y);
				}
			}else {
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					input = null;
				}
			}
			
			for(int i = 0; i < input.length; i++) {
				if(input[i] != null) {
					input[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			for(int i = 0; i < target.length; i++) {
				if(target[i] != null) {
					target[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			int pembilang = Math.abs(input[0] - target[0]);
			for(int i = 1; i < input.length; i++) {
				pembilang = pembilang + Math.abs(input[i] -target[i]);
			}
			int penyebut = input[0] + target[0];
			for(int i = 1; i < input.length; i++) {
				penyebut = penyebut + (input[i] + target[i]);
			}
			Double tempValue = (double) (pembilang/penyebut);
			score = tempValue;
			return score;
		}
	//14. Motyka
		public static Double motyka(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp){
			Double score;
			int tempSize;
			if(graphTarget.size() >= graphInput.size()) {
				tempSize = graphTarget.size();
			}else {
				tempSize = graphInput.size();
			}
			
			Integer [] input = new Integer[tempSize];
			Integer [] target = new Integer[tempSize];
			//isi value input ataupun target dari graphTemp
			for(int i = 0; i < graphTemp.size(); i++) {
				input[i] = graphTemp.get(i);
				target[i] = graphTemp.get(i);
			}
			
			//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
			for(int i = 0; i < graphInput.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphInput.get(i) == graphTemp.get(j)) {
						graphInput.remove(i);
					}
				}
			}
			for(int i = 0; i < graphTarget.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphTarget.get(i) == graphTemp.get(j)) {
						graphTarget.remove(i);
					}
				}
			}
			if(graphInput.size() > graphTarget.size()) {
				//System.out.println("graphInput.size() >= graphTarget.size()");
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					int y = i - graphTemp.size();
					input[i] = graphInput.get(y);
				}
			}else if(graphInput.size() < graphTarget.size()) {
				//System.out.println("graphInput.size() < graphTarget.size()");
				for(int i = graphTemp.size(); i <input.length; i++) {
					input[i] = null;
				}
				//System.out.println("ddddd "+targetV2_0.length);
				for(int i = graphTemp.size(); i <target.length; i++) {
					int y = i - graphTemp.size();
					//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
					target[i] = graphTarget.get(y);
				}
			}else {
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					input = null;
				}
			}
			
			for(int i = 0; i < input.length; i++) {
				if(input[i] != null) {
					input[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			for(int i = 0; i < target.length; i++) {
				if(target[i] != null) {
					target[i] = 1;
				}else {
					target[i] = 0;
				}
			}
			
			printArray(input);
			printArray(target);
			
			double pembilang;
			if(input[0] >= target [0]) {
				pembilang = (double) input[0];
			}else {
				pembilang = (double)target[0];
			}
			for(int i = 1; i < input.length; i++) {
				if(input[i] >= target[i]) {
					pembilang = pembilang + input[i];
				}else {
					pembilang = pembilang + target[i];
				}
			}
			double penyebut = (double) input[0] + target[0];
			for(int i = 1; i < input.length; i++) {
				penyebut = penyebut + (input[i] + target[i]);
			}
			Double MinValue = 0.5;
			Double MaxValue = 1.0;
			Double tempValue = (double) (pembilang/penyebut);
			score = normalize(tempValue, MaxValue, MinValue);
			return score;
		}

	//15. kulczyki_s
		public static Double kulczynki_s(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp){
			Double score;
			int tempSize;
			if(graphTarget.size() >= graphInput.size()) {
				tempSize = graphTarget.size();
			}else {
				tempSize = graphInput.size();
			}
			
			Integer [] input = new Integer[tempSize];
			Integer [] target = new Integer[tempSize];
			//isi value input ataupun target dari graphTemp
			for(int i = 0; i < graphTemp.size(); i++) {
				input[i] = graphTemp.get(i);
				target[i] = graphTemp.get(i);
			}
			
			//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
			for(int i = 0; i < graphInput.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphInput.get(i) == graphTemp.get(j)) {
						graphInput.remove(i);
					}
				}
			}
			for(int i = 0; i < graphTarget.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphTarget.get(i) == graphTemp.get(j)) {
						graphTarget.remove(i);
					}
				}
			}
			if(graphInput.size() > graphTarget.size()) {
				//System.out.println("graphInput.size() >= graphTarget.size()");
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					int y = i - graphTemp.size();
					input[i] = graphInput.get(y);
				}
			}else if(graphInput.size() < graphTarget.size()) {
				//System.out.println("graphInput.size() < graphTarget.size()");
				for(int i = graphTemp.size(); i <input.length; i++) {
					input[i] = null;
				}
				//System.out.println("ddddd "+targetV2_0.length);
				for(int i = graphTemp.size(); i <target.length; i++) {
					int y = i - graphTemp.size();
					//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
					target[i] = graphTarget.get(y);
				}
			}else {
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					input = null;
				}
			}
			
			for(int i = 0; i < input.length; i++) {
				if(input[i] != null) {
					input[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			for(int i = 0; i < target.length; i++) {
				if(target[i] != null) {
					target[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			int pembilang;
			if(input[0] >= target[0]) {
				pembilang = input[0];
			}else {
				pembilang = target[0];
			}
			for(int i = 1; i < input.length; i++) {
				if(input[i] >= target[i]) {
					pembilang = pembilang +input[i];
				}else{
					pembilang = pembilang + target[i];
				}
			}
			int penyebut = Math.abs(input[0] - target[0]);
			for(int i = 1; i < input.length; i++) {
				penyebut = penyebut + Math.abs(input[i] - target[i]);
			}
			Double tempValue = (double) (pembilang/penyebut);
			score = tempValue;
			return score;
		}
	//16. ruzicka
		public static Double ruzicka(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp){
			Double score;
			int tempSize;
			if(graphTarget.size() >= graphInput.size()) {
				tempSize = graphTarget.size();
			}else {
				tempSize = graphInput.size();
			}
			
			Integer [] input = new Integer[tempSize];
			Integer [] target = new Integer[tempSize];
			//isi value input ataupun target dari graphTemp
			for(int i = 0; i < graphTemp.size(); i++) {
				input[i] = graphTemp.get(i);
				target[i] = graphTemp.get(i);
			}
			
			//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
			for(int i = 0; i < graphInput.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphInput.get(i) == graphTemp.get(j)) {
						graphInput.remove(i);
					}
				}
			}
			for(int i = 0; i < graphTarget.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphTarget.get(i) == graphTemp.get(j)) {
						graphTarget.remove(i);
					}
				}
			}
			if(graphInput.size() > graphTarget.size()) {
				//System.out.println("graphInput.size() >= graphTarget.size()");
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					int y = i - graphTemp.size();
					input[i] = graphInput.get(y);
				}
			}else if(graphInput.size() < graphTarget.size()) {
				//System.out.println("graphInput.size() < graphTarget.size()");
				for(int i = graphTemp.size(); i <input.length; i++) {
					input[i] = null;
				}
				//System.out.println("ddddd "+targetV2_0.length);
				for(int i = graphTemp.size(); i <target.length; i++) {
					int y = i - graphTemp.size();
					//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
					target[i] = graphTarget.get(y);
				}
			}else {
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					input = null;
				}
			}
			
			for(int i = 0; i < input.length; i++) {
				if(input[i] != null) {
					input[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			for(int i = 0; i < target.length; i++) {
				if(target[i] != null) {
					target[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			int pembilang;
			if(input[0] >= target[0]) {
				pembilang = target[0];
			}else{
				pembilang = input[0];
			}
			for(int i = 1; i < input.length; i++) {
				if(input[i] >= target[i]) {
					pembilang = pembilang + target[i];
				}else {
					pembilang = pembilang +input[i];
				}
			}
			int penyebut;
			if(input[0] >= target[0]) {
				penyebut = input[0];
			}else {
				penyebut = target[0];
			}
			for(int i = 1; i < input.length; i++) {
				if(input[i] >= target[i]) {
					penyebut = penyebut + input[i];
				}else {
					penyebut = penyebut + target[i];
				}
			}
			Double tempValue = (double) (pembilang/penyebut);
			score = tempValue;
			return score;
		}
	//17. tanimoto
		public static Double tanimoto(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp){
			Double score;
			int tempSize;
			if(graphTarget.size() >= graphInput.size()) {
				tempSize = graphTarget.size();
			}else {
				tempSize = graphInput.size();
			}
			
			Integer [] input = new Integer[tempSize];
			Integer [] target = new Integer[tempSize];
			//isi value input ataupun target dari graphTemp
			for(int i = 0; i < graphTemp.size(); i++) {
				input[i] = graphTemp.get(i);
				target[i] = graphTemp.get(i);
			}
			
			//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
			for(int i = 0; i < graphInput.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphInput.get(i) == graphTemp.get(j)) {
						graphInput.remove(i);
					}
				}
			}
			for(int i = 0; i < graphTarget.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphTarget.get(i) == graphTemp.get(j)) {
						graphTarget.remove(i);
					}
				}
			}
			if(graphInput.size() > graphTarget.size()) {
				//System.out.println("graphInput.size() >= graphTarget.size()");
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					int y = i - graphTemp.size();
					input[i] = graphInput.get(y);
				}
			}else if(graphInput.size() < graphTarget.size()) {
				//System.out.println("graphInput.size() < graphTarget.size()");
				for(int i = graphTemp.size(); i <input.length; i++) {
					input[i] = null;
				}
				//System.out.println("ddddd "+targetV2_0.length);
				for(int i = graphTemp.size(); i <target.length; i++) {
					int y = i - graphTemp.size();
					//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
					target[i] = graphTarget.get(y);
				}
			}else {
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					input = null;
				}
			}
			
			for(int i = 0; i < input.length; i++) {
				if(input[i] != null) {
					input[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			for(int i = 0; i < target.length; i++) {
				if(target[i] != null) {
					target[i] = 1;
				}else {
					target[i] = 0;
				}
			}
			
			printArray(input);
			printArray(target);
			
			double pembilang;
			pembilang = (double) Math.max(input[0], target[0]) - Math.min(input[0], target[0]);
			for(int i = 1; i < input.length; i++) {
				pembilang = pembilang + (Math.max(input[i], target[i]) - Math.min(input[i],target[i]));
			}
			double penyebut = (double) Math.max(input[0], target[0]);
			for(int i = 1; i < input.length; i++) {
				penyebut = penyebut + Math.max(input[i],target[i]);
			}
			Double tempValue = (double) (pembilang/penyebut);
			score = tempValue;
			return score;
		}

	//18. inner product
		public static Double innerProduct(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp){
			Double score;
			int tempSize;
			if(graphTarget.size() >= graphInput.size()) {
				tempSize = graphTarget.size();
			}else {
				tempSize = graphInput.size();
			}
			
			Integer [] input = new Integer[tempSize];
			Integer [] target = new Integer[tempSize];
			//isi value input ataupun target dari graphTemp
			for(int i = 0; i < graphTemp.size(); i++) {
				input[i] = graphTemp.get(i);
				target[i] = graphTemp.get(i);
			}
			
			//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
			for(int i = 0; i < graphInput.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphInput.get(i) == graphTemp.get(j)) {
						graphInput.remove(i);
					}
				}
			}
			for(int i = 0; i < graphTarget.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphTarget.get(i) == graphTemp.get(j)) {
						graphTarget.remove(i);
					}
				}
			}
			if(graphInput.size() > graphTarget.size()) {
				//System.out.println("graphInput.size() >= graphTarget.size()");
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					int y = i - graphTemp.size();
					input[i] = graphInput.get(y);
				}
			}else if(graphInput.size() < graphTarget.size()) {
				//System.out.println("graphInput.size() < graphTarget.size()");
				for(int i = graphTemp.size(); i <input.length; i++) {
					input[i] = null;
				}
				//System.out.println("ddddd "+targetV2_0.length);
				for(int i = graphTemp.size(); i <target.length; i++) {
					int y = i - graphTemp.size();
					//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
					target[i] = graphTarget.get(y);
				}
			}else {
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					input = null;
				}
			}
			
			for(int i = 0; i < input.length; i++) {
				if(input[i] != null) {
					input[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			for(int i = 0; i < target.length; i++) {
				if(target[i] != null) {
					target[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			int pembilang = input[0]*target[0];
			for(int i = 1; i < input.length; i++) {
				pembilang = pembilang + input[i]*target[i];
			}
			
			Double tempValue = (double) pembilang;
			score = tempValue;
			return score;
		}
	//19. Harmonic Mean
		public static Double harmonicMean(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp){
			Double score;
			int tempSize;
			if(graphTarget.size() >= graphInput.size()) {
				tempSize = graphTarget.size();
			}else {
				tempSize = graphInput.size();
			}
			
			Integer [] input = new Integer[tempSize];
			Integer [] target = new Integer[tempSize];
			//isi value input ataupun target dari graphTemp
			for(int i = 0; i < graphTemp.size(); i++) {
				input[i] = graphTemp.get(i);
				target[i] = graphTemp.get(i);
			}
			
			//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
			for(int i = 0; i < graphInput.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphInput.get(i) == graphTemp.get(j)) {
						graphInput.remove(i);
					}
				}
			}
			for(int i = 0; i < graphTarget.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphTarget.get(i) == graphTemp.get(j)) {
						graphTarget.remove(i);
					}
				}
			}
			if(graphInput.size() > graphTarget.size()) {
				//System.out.println("graphInput.size() >= graphTarget.size()");
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					int y = i - graphTemp.size();
					input[i] = graphInput.get(y);
				}
			}else if(graphInput.size() < graphTarget.size()) {
				//System.out.println("graphInput.size() < graphTarget.size()");
				for(int i = graphTemp.size(); i <input.length; i++) {
					input[i] = null;
				}
				//System.out.println("ddddd "+targetV2_0.length);
				for(int i = graphTemp.size(); i <target.length; i++) {
					int y = i - graphTemp.size();
					//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
					target[i] = graphTarget.get(y);
				}
			}else {
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					input = null;
				}
			}
			
			for(int i = 0; i < input.length; i++) {
				if(input[i] != null) {
					input[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			for(int i = 0; i < target.length; i++) {
				if(target[i] != null) {
					target[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			double pembilang = input[0]*target[0]/(input[0] +target[0]);
			for(int i = 1; i < input.length; i++) {
				pembilang = pembilang + (input[i]*target[i])/(input[0] + target[0]);
			}
			
			Double tempValue = (double) 2*pembilang;
			score = tempValue;
			return score;
		}
	//20.Cosine
		public static Double cosine(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp){
			Double score;
			int tempSize;
			if(graphTarget.size() >= graphInput.size()) {
				tempSize = graphTarget.size();
			}else {
				tempSize = graphInput.size();
			}
			
			Integer [] input = new Integer[tempSize];
			Integer [] target = new Integer[tempSize];
			//isi value input ataupun target dari graphTemp
			for(int i = 0; i < graphTemp.size(); i++) {
				input[i] = graphTemp.get(i);
				target[i] = graphTemp.get(i);
			}
			
			//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
			for(int i = 0; i < graphInput.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphInput.get(i) == graphTemp.get(j)) {
						graphInput.remove(i);
					}
				}
			}
			for(int i = 0; i < graphTarget.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphTarget.get(i) == graphTemp.get(j)) {
						graphTarget.remove(i);
					}
				}
			}
			if(graphInput.size() > graphTarget.size()) {
				//System.out.println("graphInput.size() >= graphTarget.size()");
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					int y = i - graphTemp.size();
					input[i] = graphInput.get(y);
				}
			}else if(graphInput.size() < graphTarget.size()) {
				//System.out.println("graphInput.size() < graphTarget.size()");
				for(int i = graphTemp.size(); i <input.length; i++) {
					input[i] = null;
				}
				//System.out.println("ddddd "+targetV2_0.length);
				for(int i = graphTemp.size(); i <target.length; i++) {
					int y = i - graphTemp.size();
					//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
					target[i] = graphTarget.get(y);
				}
			}else {
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					input = null;
				}
			}
			
			for(int i = 0; i < input.length; i++) {
				if(input[i] != null) {
					input[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			for(int i = 0; i < target.length; i++) {
				if(target[i] != null) {
					target[i] = 1;
				}else {
					target[i] = 0;
				}
			}
			
			printArray(input);
			printArray(target);
			
			double pembilang = (double)input[0]*target[0];
			for(int i = 1; i < input.length; i++) {
				pembilang = pembilang + input[i]*target[i];
			}
			double p1 = (double) input[0]*input[0];
			for(int i = 1; i < input.length; i++) {
				p1 = p1 + input[i]*input[i];
			}
			double p2 = (double) target[0]*target[0];
			for(int i = 1; i < input.length; i++) {
				p2 = p2 + target[i]*target[i];
			}
			Double tempValue = (double) pembilang/(Math.sqrt(p1)*Math.sqrt(p2));
			score = 1.0 - tempValue;
			return score;
		}
	//21. kumar hassebrook
		public static Double kumarHassebrook(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp){
			Double score;
			int tempSize;
			if(graphTarget.size() >= graphInput.size()) {
				tempSize = graphTarget.size();
			}else {
				tempSize = graphInput.size();
			}
			
			Integer [] input = new Integer[tempSize];
			Integer [] target = new Integer[tempSize];
			//isi value input ataupun target dari graphTemp
			for(int i = 0; i < graphTemp.size(); i++) {
				input[i] = graphTemp.get(i);
				target[i] = graphTemp.get(i);
			}
			
			//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
			for(int i = 0; i < graphInput.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphInput.get(i) == graphTemp.get(j)) {
						graphInput.remove(i);
					}
				}
			}
			for(int i = 0; i < graphTarget.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphTarget.get(i) == graphTemp.get(j)) {
						graphTarget.remove(i);
					}
				}
			}
			if(graphInput.size() > graphTarget.size()) {
				//System.out.println("graphInput.size() >= graphTarget.size()");
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					int y = i - graphTemp.size();
					input[i] = graphInput.get(y);
				}
			}else if(graphInput.size() < graphTarget.size()) {
				//System.out.println("graphInput.size() < graphTarget.size()");
				for(int i = graphTemp.size(); i <input.length; i++) {
					input[i] = null;
				}
				//System.out.println("ddddd "+targetV2_0.length);
				for(int i = graphTemp.size(); i <target.length; i++) {
					int y = i - graphTemp.size();
					//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
					target[i] = graphTarget.get(y);
				}
			}else {
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					input = null;
				}
			}
			
			for(int i = 0; i < input.length; i++) {
				if(input[i] != null) {
					input[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			for(int i = 0; i < target.length; i++) {
				if(target[i] != null) {
					target[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			int pembilang = input[0]*target[0];
			for(int i = 1; i < input.length; i++) {
				pembilang = pembilang + input[i]*target[i];
			}
			
			int penyebut;
			int p1 = input[0] * input[0];
			int p2 = target[0] * target[0];
			int p3 = input[0]*target[0];
			penyebut = p1 + p2  - p3;
			for(int i = 1; i < input.length; i++) {
				p1 = p1 + input[i]*input[i];
				p2 = p2 + target[i]*target[i];
				p3 = p3 + input[i]*target[i];
				penyebut = penyebut + (p1 + p2 -p3);
			}
			Double tempValue = (double) (pembilang/penyebut);
			score = tempValue;
			return score;
		}
	//22. jaccard 
		public static Double jaccard(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp){
			Double score;
			int tempSize;
			if(graphTarget.size() >= graphInput.size()) {
				tempSize = graphTarget.size();
			}else {
				tempSize = graphInput.size();
			}
			
			Integer [] input = new Integer[tempSize];
			Integer [] target = new Integer[tempSize];
			//isi value input ataupun target dari graphTemp
			for(int i = 0; i < graphTemp.size(); i++) {
				input[i] = graphTemp.get(i);
				target[i] = graphTemp.get(i);
			}
			
			//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
			for(int i = 0; i < graphInput.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphInput.get(i) == graphTemp.get(j)) {
						graphInput.remove(i);
					}
				}
			}
			for(int i = 0; i < graphTarget.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphTarget.get(i) == graphTemp.get(j)) {
						graphTarget.remove(i);
					}
				}
			}
			if(graphInput.size() > graphTarget.size()) {
				//System.out.println("graphInput.size() >= graphTarget.size()");
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					int y = i - graphTemp.size();
					input[i] = graphInput.get(y);
				}
			}else if(graphInput.size() < graphTarget.size()) {
				//System.out.println("graphInput.size() < graphTarget.size()");
				for(int i = graphTemp.size(); i <input.length; i++) {
					input[i] = null;
				}
				//System.out.println("ddddd "+targetV2_0.length);
				for(int i = graphTemp.size(); i <target.length; i++) {
					int y = i - graphTemp.size();
					//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
					target[i] = graphTarget.get(y);
				}
			}else {
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					input[i] = null;
				}
			}
			
			for(int i = 0; i < input.length; i++) {
				if(input[i] != null) {
					input[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			for(int i = 0; i < target.length; i++) {
				if(target[i] != null) {
					target[i] = 1;
				}else {
					target[i] = 0;
				}
			}
			
			printArray(input);
			printArray(target);
			
			double pembilang =(double) (input[0]-target[0])*(input[0] - target[0]);
			for(int i = 1; i < input.length; i++) {
				pembilang = pembilang + ((input[i] - target[i])*(input[i] - target[i]));
			}
			
			
			double p1 = (double) (input[0] * input[0]);
			
			for(int i =1; i < input.length; i++) {
				p1 = p1 + (input[i]*target[i]);
			}
			
			double p2 = (double) target[0] * target[0];
			for(int i = 1; i < input.length; i++) {
				p2 = p2 + (target[i] * target[i]);
			}
			
			double p3 = (double) input[0] * target[0];
			for(int i = 1; i < input.length; i++) {
				p3 = p3 + (input[i] * target[i]);
			}
			
			
			Double tempValue = (double) (pembilang/(p1 + p2 - p3));
			score = tempValue;
			return score;
		}
	//23. dice
		public static Double dice(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp){
			Double score;
			int tempSize;
			if(graphTarget.size() >= graphInput.size()) {
				tempSize = graphTarget.size();
			}else {
				tempSize = graphInput.size();
			}
			
			Integer [] input = new Integer[tempSize];
			Integer [] target = new Integer[tempSize];
			//isi value input ataupun target dari graphTemp
			for(int i = 0; i < graphTemp.size(); i++) {
				input[i] = graphTemp.get(i);
				target[i] = graphTemp.get(i);
			}
			
			//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
			for(int i = 0; i < graphInput.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphInput.get(i) == graphTemp.get(j)) {
						graphInput.remove(i);
					}
				}
			}
			for(int i = 0; i < graphTarget.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphTarget.get(i) == graphTemp.get(j)) {
						graphTarget.remove(i);
					}
				}
			}
			if(graphInput.size() > graphTarget.size()) {
				//System.out.println("graphInput.size() >= graphTarget.size()");
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					int y = i - graphTemp.size();
					input[i] = graphInput.get(y);
				}
			}else if(graphInput.size() < graphTarget.size()) {
				//System.out.println("graphInput.size() < graphTarget.size()");
				for(int i = graphTemp.size(); i <input.length; i++) {
					input[i] = null;
				}
				//System.out.println("ddddd "+targetV2_0.length);
				for(int i = graphTemp.size(); i <target.length; i++) {
					int y = i - graphTemp.size();
					//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
					target[i] = graphTarget.get(y);
				}
			}else {
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					input = null;
				}
			}
			
			for(int i = 0; i < input.length; i++) {
				if(input[i] != null) {
					input[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			for(int i = 0; i < target.length; i++) {
				if(target[i] != null) {
					target[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			int pembilang = (input[0]-target[0])*(input[0] - target[0]);
			for(int i = 1; i < input.length; i++) {
				pembilang = pembilang + ((input[i] - target[i])*(input[i] - target[i]));
			}
			
			int penyebut;
			int p1 = input[0] * input[0];
			int p2 = target[0] * target[0];
			int p3 = input[0] * target[0];
			penyebut = p1 + p2;
			for(int i = 1; i < input.length; i++) {
				p1 = p1 + input[i]*input[i];
				p2 = p2 + target[i]*target[i];
				p3 = p3 + input[i]*target[i];
				penyebut = penyebut + (p1 + p2);
			}
			Double tempValue = (double) (pembilang/penyebut);
			score = tempValue;
			return score;
		}
	//24. fidelity
		public static Double fidelity(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp){
			Double score;
			int tempSize;
			if(graphTarget.size() >= graphInput.size()) {
				tempSize = graphTarget.size();
			}else {
				tempSize = graphInput.size();
			}
			
			Integer [] input = new Integer[tempSize];
			Integer [] target = new Integer[tempSize];
			//isi value input ataupun target dari graphTemp
			for(int i = 0; i < graphTemp.size(); i++) {
				input[i] = graphTemp.get(i);
				target[i] = graphTemp.get(i);
			}
			
			//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
			for(int i = 0; i < graphInput.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphInput.get(i) == graphTemp.get(j)) {
						graphInput.remove(i);
					}
				}
			}
			for(int i = 0; i < graphTarget.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphTarget.get(i) == graphTemp.get(j)) {
						graphTarget.remove(i);
					}
				}
			}
			if(graphInput.size() > graphTarget.size()) {
				//System.out.println("graphInput.size() >= graphTarget.size()");
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					int y = i - graphTemp.size();
					input[i] = graphInput.get(y);
				}
			}else if(graphInput.size() < graphTarget.size()) {
				//System.out.println("graphInput.size() < graphTarget.size()");
				for(int i = graphTemp.size(); i <input.length; i++) {
					input[i] = null;
				}
				//System.out.println("ddddd "+targetV2_0.length);
				for(int i = graphTemp.size(); i <target.length; i++) {
					int y = i - graphTemp.size();
					//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
					target[i] = graphTarget.get(y);
				}
			}else {
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					input = null;
				}
			}
			
			for(int i = 0; i < input.length; i++) {
				if(input[i] != null) {
					input[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			for(int i = 0; i < target.length; i++) {
				if(target[i] != null) {
					target[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			double pembilang = Math.sqrt(input[0]*target[0]);
			for(int i = 1; i < input.length; i++) {
				pembilang = pembilang + Math.sqrt(input[i]*target[i]);
			}
			Double tempValue = (double) pembilang;
			score = tempValue;
			return score;
		}
	//25. bhattacharyya
		public static Double bhattacharyya(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp){
			Double score;
			int tempSize;
			if(graphTarget.size() >= graphInput.size()) {
				tempSize = graphTarget.size();
			}else {
				tempSize = graphInput.size();
			}
			
			Integer [] input = new Integer[tempSize];
			Integer [] target = new Integer[tempSize];
			//isi value input ataupun target dari graphTemp
			for(int i = 0; i < graphTemp.size(); i++) {
				input[i] = graphTemp.get(i);
				target[i] = graphTemp.get(i);
			}
			
			//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
			for(int i = 0; i < graphInput.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphInput.get(i) == graphTemp.get(j)) {
						graphInput.remove(i);
					}
				}
			}
			for(int i = 0; i < graphTarget.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphTarget.get(i) == graphTemp.get(j)) {
						graphTarget.remove(i);
					}
				}
			}
			if(graphInput.size() > graphTarget.size()) {
				//System.out.println("graphInput.size() >= graphTarget.size()");
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					int y = i - graphTemp.size();
					input[i] = graphInput.get(y);
				}
			}else if(graphInput.size() < graphTarget.size()) {
				//System.out.println("graphInput.size() < graphTarget.size()");
				for(int i = graphTemp.size(); i <input.length; i++) {
					input[i] = null;
				}
				//System.out.println("ddddd "+targetV2_0.length);
				for(int i = graphTemp.size(); i <target.length; i++) {
					int y = i - graphTemp.size();
					//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
					target[i] = graphTarget.get(y);
				}
			}else {
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					input = null;
				}
			}
			
			for(int i = 0; i < input.length; i++) {
				if(input[i] != null) {
					input[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			for(int i = 0; i < target.length; i++) {
				if(target[i] != null) {
					target[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			double pembilang = Math.sqrt(input[0]*target[0]);
			for(int i = 1; i < input.length; i++) {
				pembilang = pembilang + Math.sqrt(input[i]*target[i]);
			}
			Double tempValue = (double) -1 * Math.log(pembilang);
			score = tempValue;
			return score;
		}
	//26. hellinger
		public static Double hellinger(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp){
			Double score;
			int tempSize;
			if(graphTarget.size() >= graphInput.size()) {
				tempSize = graphTarget.size();
			}else {
				tempSize = graphInput.size();
			}
			
			Integer [] input = new Integer[tempSize];
			Integer [] target = new Integer[tempSize];
			//isi value input ataupun target dari graphTemp
			for(int i = 0; i < graphTemp.size(); i++) {
				input[i] = graphTemp.get(i);
				target[i] = graphTemp.get(i);
			}
			
			//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
			for(int i = 0; i < graphInput.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphInput.get(i) == graphTemp.get(j)) {
						graphInput.remove(i);
					}
				}
			}
			for(int i = 0; i < graphTarget.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphTarget.get(i) == graphTemp.get(j)) {
						graphTarget.remove(i);
					}
				}
			}
			if(graphInput.size() > graphTarget.size()) {
				//System.out.println("graphInput.size() >= graphTarget.size()");
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					int y = i - graphTemp.size();
					input[i] = graphInput.get(y);
				}
			}else if(graphInput.size() < graphTarget.size()) {
				//System.out.println("graphInput.size() < graphTarget.size()");
				for(int i = graphTemp.size(); i <input.length; i++) {
					input[i] = null;
				}
				//System.out.println("ddddd "+targetV2_0.length);
				for(int i = graphTemp.size(); i <target.length; i++) {
					int y = i - graphTemp.size();
					//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
					target[i] = graphTarget.get(y);
				}
			}else {
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					input = null;
				}
			}
			
			for(int i = 0; i < input.length; i++) {
				if(input[i] != null) {
					input[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			for(int i = 0; i < target.length; i++) {
				if(target[i] != null) {
					target[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			double pembilang = Math.sqrt(input[0]*target[0]);
			for(int i = 1; i < input.length; i++) {
				pembilang = pembilang + Math.sqrt(input[i]*target[i]);
			}
			Double tempValue = (double) 2*(Math.sqrt(1 - pembilang));
			score = tempValue;
			return score;
		}
	//27. matusita
		public static Double matusita(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp){
			Double score;
			int tempSize;
			if(graphTarget.size() >= graphInput.size()) {
				tempSize = graphTarget.size();
			}else {
				tempSize = graphInput.size();
			}
			
			Integer [] input = new Integer[tempSize];
			Integer [] target = new Integer[tempSize];
			//isi value input ataupun target dari graphTemp
			for(int i = 0; i < graphTemp.size(); i++) {
				input[i] = graphTemp.get(i);
				target[i] = graphTemp.get(i);
			}
			
			//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
			for(int i = 0; i < graphInput.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphInput.get(i) == graphTemp.get(j)) {
						graphInput.remove(i);
					}
				}
			}
			for(int i = 0; i < graphTarget.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphTarget.get(i) == graphTemp.get(j)) {
						graphTarget.remove(i);
					}
				}
			}
			if(graphInput.size() > graphTarget.size()) {
				//System.out.println("graphInput.size() >= graphTarget.size()");
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					int y = i - graphTemp.size();
					input[i] = graphInput.get(y);
				}
			}else if(graphInput.size() < graphTarget.size()) {
				//System.out.println("graphInput.size() < graphTarget.size()");
				for(int i = graphTemp.size(); i <input.length; i++) {
					input[i] = null;
				}
				//System.out.println("ddddd "+targetV2_0.length);
				for(int i = graphTemp.size(); i <target.length; i++) {
					int y = i - graphTemp.size();
					//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
					target[i] = graphTarget.get(y);
				}
			}else {
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					input = null;
				}
			}
			
			for(int i = 0; i < input.length; i++) {
				if(input[i] != null) {
					input[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			for(int i = 0; i < target.length; i++) {
				if(target[i] != null) {
					target[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			double pembilang = Math.sqrt(input[0]*target[0]);
			for(int i = 1; i < input.length; i++) {
				pembilang = pembilang + Math.sqrt(input[i]*target[i]);
			}
			Double tempValue = (double) Math.sqrt(2 - 2*pembilang);
			score = tempValue;
			return score;
		}
	//28. squared chord 
		public static Double squaredChord(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp){
			Double score;
			int tempSize;
			if(graphTarget.size() >= graphInput.size()) {
				tempSize = graphTarget.size();
			}else {
				tempSize = graphInput.size();
			}
			
			Integer [] input = new Integer[tempSize];
			Integer [] target = new Integer[tempSize];
			//isi value input ataupun target dari graphTemp
			for(int i = 0; i < graphTemp.size(); i++) {
				input[i] = graphTemp.get(i);
				target[i] = graphTemp.get(i);
			}
			
			//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
			for(int i = 0; i < graphInput.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphInput.get(i) == graphTemp.get(j)) {
						graphInput.remove(i);
					}
				}
			}
			for(int i = 0; i < graphTarget.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphTarget.get(i) == graphTemp.get(j)) {
						graphTarget.remove(i);
					}
				}
			}
			if(graphInput.size() > graphTarget.size()) {
				//System.out.println("graphInput.size() >= graphTarget.size()");
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					int y = i - graphTemp.size();
					input[i] = graphInput.get(y);
				}
			}else if(graphInput.size() < graphTarget.size()) {
				//System.out.println("graphInput.size() < graphTarget.size()");
				for(int i = graphTemp.size(); i <input.length; i++) {
					input[i] = null;
				}
				//System.out.println("ddddd "+targetV2_0.length);
				for(int i = graphTemp.size(); i <target.length; i++) {
					int y = i - graphTemp.size();
					//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
					target[i] = graphTarget.get(y);
				}
			}else {
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					input = null;
				}
			}
			
			for(int i = 0; i < input.length; i++) {
				if(input[i] != null) {
					input[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			for(int i = 0; i < target.length; i++) {
				if(target[i] != null) {
					target[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			double pembilang = Math.sqrt(input[0]*target[0]);
			for(int i = 1; i < input.length; i++) {
				pembilang = pembilang + Math.sqrt(input[i]*target[i]);
			}
			Double tempValue = (double) (2*pembilang) - 1;
			score = tempValue;
			return score;
		}
	//29. squared euclidean 
		public static Double squaredEuclidean(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp){
			Double score;
			int tempSize;
			if(graphTarget.size() >= graphInput.size()) {
				tempSize = graphTarget.size();
			}else {
				tempSize = graphInput.size();
			}
			
			Integer [] input = new Integer[tempSize];
			Integer [] target = new Integer[tempSize];
			//isi value input ataupun target dari graphTemp
			for(int i = 0; i < graphTemp.size(); i++) {
				input[i] = graphTemp.get(i);
				target[i] = graphTemp.get(i);
			}
			
			//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
			for(int i = 0; i < graphInput.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphInput.get(i) == graphTemp.get(j)) {
						graphInput.remove(i);
					}
				}
			}
			for(int i = 0; i < graphTarget.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphTarget.get(i) == graphTemp.get(j)) {
						graphTarget.remove(i);
					}
				}
			}
			if(graphInput.size() > graphTarget.size()) {
				//System.out.println("graphInput.size() >= graphTarget.size()");
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					int y = i - graphTemp.size();
					input[i] = graphInput.get(y);
				}
			}else if(graphInput.size() < graphTarget.size()) {
				//System.out.println("graphInput.size() < graphTarget.size()");
				for(int i = graphTemp.size(); i <input.length; i++) {
					input[i] = null;
				}
				//System.out.println("ddddd "+targetV2_0.length);
				for(int i = graphTemp.size(); i <target.length; i++) {
					int y = i - graphTemp.size();
					//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
					target[i] = graphTarget.get(y);
				}
			}else {
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					input = null;
				}
			}
			
			for(int i = 0; i < input.length; i++) {
				if(input[i] != null) {
					input[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			for(int i = 0; i < target.length; i++) {
				if(target[i] != null) {
					target[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			double pembilang = (input[0] - target[0])*(input[0] - target[0]);
			for(int i = 1; i < input.length; i++) {
				pembilang = pembilang + (input[i] -target[i])*(input[i] - target[i]);
			}
			Double tempValue = (double) pembilang;
			score = tempValue;
			return score;
		}
	//30. pearson x2
		public static Double pearsonX2(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp){
			Double score;
			int tempSize;
			if(graphTarget.size() >= graphInput.size()) {
				tempSize = graphTarget.size();
			}else {
				tempSize = graphInput.size();
			}
			
			Integer [] input = new Integer[tempSize];
			Integer [] target = new Integer[tempSize];
			//isi value input ataupun target dari graphTemp
			for(int i = 0; i < graphTemp.size(); i++) {
				input[i] = graphTemp.get(i);
				target[i] = graphTemp.get(i);
			}
			
			//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
			for(int i = 0; i < graphInput.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphInput.get(i) == graphTemp.get(j)) {
						graphInput.remove(i);
					}
				}
			}
			for(int i = 0; i < graphTarget.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphTarget.get(i) == graphTemp.get(j)) {
						graphTarget.remove(i);
					}
				}
			}
			if(graphInput.size() > graphTarget.size()) {
				//System.out.println("graphInput.size() >= graphTarget.size()");
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					int y = i - graphTemp.size();
					input[i] = graphInput.get(y);
				}
			}else if(graphInput.size() < graphTarget.size()) {
				//System.out.println("graphInput.size() < graphTarget.size()");
				for(int i = graphTemp.size(); i <input.length; i++) {
					input[i] = null;
				}
				//System.out.println("ddddd "+targetV2_0.length);
				for(int i = graphTemp.size(); i <target.length; i++) {
					int y = i - graphTemp.size();
					//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
					target[i] = graphTarget.get(y);
				}
			}else {
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					input = null;
				}
			}
			
			for(int i = 0; i < input.length; i++) {
				if(input[i] != null) {
					input[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			for(int i = 0; i < target.length; i++) {
				if(target[i] != null) {
					target[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			double pembilang = (input[0] - target[0])*(input[0] - target[0])/target[0];
			for(int i = 1; i < input.length; i++) {
				pembilang = pembilang + (input[i] -target[i])*(input[i] - target[i])/target[i];
			}
			Double tempValue = (double) pembilang;
			score = tempValue;
			return score;
		}
	//31. avg
		public static Double avg(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp){
			Double score;
			int tempSize;
			if(graphTarget.size() >= graphInput.size()) {
				tempSize = graphTarget.size();
			}else {
				tempSize = graphInput.size();
			}
			
			Integer [] input = new Integer[tempSize];
			Integer [] target = new Integer[tempSize];
			//isi value input ataupun target dari graphTemp
			for(int i = 0; i < graphTemp.size(); i++) {
				input[i] = graphTemp.get(i);
				target[i] = graphTemp.get(i);
			}
			
			//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
			for(int i = 0; i < graphInput.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphInput.get(i) == graphTemp.get(j)) {
						graphInput.remove(i);
					}
				}
			}
			for(int i = 0; i < graphTarget.size(); i++) {
				for(int j = 0; j < graphTemp.size(); j++) {
					if(graphTarget.get(i) == graphTemp.get(j)) {
						graphTarget.remove(i);
					}
				}
			}
			if(graphInput.size() > graphTarget.size()) {
				//System.out.println("graphInput.size() >= graphTarget.size()");
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					int y = i - graphTemp.size();
					input[i] = graphInput.get(y);
				}
			}else if(graphInput.size() < graphTarget.size()) {
				//System.out.println("graphInput.size() < graphTarget.size()");
				for(int i = graphTemp.size(); i <input.length; i++) {
					input[i] = null;
				}
				//System.out.println("ddddd "+targetV2_0.length);
				for(int i = graphTemp.size(); i <target.length; i++) {
					int y = i - graphTemp.size();
					//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
					target[i] = graphTarget.get(y);
				}
			}else {
				for(int i = graphTemp.size(); i <target.length; i++) {
					target[i] = null;
				}
				for(int i = graphTemp.size(); i < input.length; i++) {
					input = null;
				}
			}
			
			for(int i = 0; i < input.length; i++) {
				if(input[i] != null) {
					input[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			for(int i = 0; i < target.length; i++) {
				if(target[i] != null) {
					target[i] = 1;
				}else {
					input[i] = 0;
				}
			}
			
			double suku1 = Math.abs(input[0] - target[0]);
			for(int i = 1; i < input.length; i++) {
				suku1 = suku1 + Math.abs(input[i] - target[i]);
			}
			
			double suku2 = Math.abs(input[0] - target[0]);
			for(int i = 1; i < input.length; i++) {
				if(suku2 < Math.abs(input[i] - target[i])){
					suku2 = Math.abs(input[i] - target[i]);
				}
			}
			Double tempValue = (double) (suku1 + suku2)/2;
			score = tempValue;
			return score;
		}
	/*===========================================Pemodelan Vektor Versi V1.1=========================================================*/
	//1. Euclidean Distance V1.1
	public static Double euclideanDistanceV11(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				inputV1_1[i] = 1;
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				targetV1_1[i] = 0;
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		int value = (inputV1_1[0] - targetV1_1[0])*(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			value = value + ((inputV1_1[i] - targetV1_1[i])*(inputV1_1[i] - targetV1_1[i]));
		}
		
		double MinValue = 0.0;
		double MaxValue = Math.sqrt(inputV1_1.length); 
		
		Double tempValue = (double) Math.sqrt(value);
		score = normalize(tempValue, MaxValue, MinValue);
		return score;
	
	}
	
	//2. cityblock
	public static Double cityBlockV11(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				inputV1_1[i] = 1;
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				targetV1_1[i] = 0;
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		int value = Math.abs(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			value = value + Math.abs(inputV1_1[i] - targetV1_1[i]);
		}
		
		double MinValue = 0.0;
		double MaxValue = (double) inputV1_1.length;
		
		
		Double tempValue = (double) value;
		score = normalize(tempValue, MaxValue, MinValue);
		return score;
	}
	
	//4. chebyshev
	public static Double chebyshevV11(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				inputV1_1[i] = 1;
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				targetV1_1[i] = 0;
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		int value = Math.abs(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			if(value < Math.abs(inputV1_1[i] - targetV1_1[i]))
				value = Math.abs(inputV1_1[i] - targetV1_1[i]);
		}
		
		double MinValue = 0.0;
		double MaxValue = 1.0;
		
		Double tempValue = (double) value;
		score = normalize(tempValue, MaxValue, MinValue);
		return score;
	}
	
	//5. soergel
	public static Double soergelV11(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				inputV1_1[i] = 1;
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				targetV1_1[i] = 0;
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		Double pembilang= (double) Math.abs(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			pembilang = pembilang + Math.abs(inputV1_1[i] - targetV1_1[i]);
		}
		Double penyebut = (double) Math.max(inputV1_1[0], targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			penyebut = penyebut + Math.max(inputV1_1[i], targetV1_1[i]);
		}
		Double tempValue = (double) pembilang/penyebut;
		score = tempValue;
		return score;
	}
	
	//6. gower
	public static Double gowerV11(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				inputV1_1[i] = 1;
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				targetV1_1[i] = 0;
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		int value = Math.abs(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			value = value + Math.abs(inputV1_1[i] - targetV1_1[i]);
		}
		Double tempValue = (double) value/inputV1_1.length;
		score = tempValue;
		return score;
	}

	//7. sorensen
	public static Double sorensenV11(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				inputV1_1[i] = 1;
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				targetV1_1[i] = 0;
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		Double pembilang= (double)Math.abs(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			pembilang = pembilang + Math.abs(inputV1_1[i] - targetV1_1[i]);
		}
		Double penyebut = (double) inputV1_1[0] + targetV1_1[0];
		for(int i = 1; i < inputV1_1.length; i++) {
			penyebut = penyebut + (inputV1_1[i] + targetV1_1[i]);
		}
		Double tempValue = (double) pembilang/penyebut;
		score = tempValue;
		return score;
	}
	
	//8.kulczynki
	public static Double kulczynkiV11(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				inputV1_1[i] = 1;
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				targetV1_1[i] = 0;
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		int pembilang= Math.abs(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			pembilang = pembilang + Math.abs(inputV1_1[i] - targetV1_1[i]);
		}
		int penyebut = Math.min(inputV1_1[0], targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			penyebut = penyebut + Math.min(inputV1_1[i], targetV1_1[i]);
		}
		Double tempValue = (double) pembilang/penyebut;
		score = tempValue;
		return score;
	}
	
	//10. lorentzian 
	public static Double lorentzianV11(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				inputV1_1[i] = 1;
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				targetV1_1[i] = 0;
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		double pembilang= Math.log(1 + Math.abs(inputV1_1[0] - targetV1_1[0]));
		for(int i = 1; i < inputV1_1.length; i++) {
			pembilang = pembilang + Math.log(1 + Math.abs(inputV1_1[i] - targetV1_1[i]));
		}
		
		double MinValue = 0.0;
		double MaxValue = inputV1_1.length*Math.log(2);
		
		Double tempValue = (double) pembilang;
		score = normalize(tempValue, MaxValue, MinValue);
		return score;
	}
	
	//11. intersection
	public static Double intersectionV11(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				inputV1_1[i] = 1;
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				targetV1_1[i] = 0;
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		int pembilang= Math.abs(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			pembilang = pembilang + Math.abs(inputV1_1[i] - targetV1_1[i]);
		}
		
		Double tempValue = (double) pembilang/2;
		score = tempValue;
		return score;
	}
	
	//13. czekanowski == sorensen
	
	//14. Motyka
	public static Double motykaV11(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				inputV1_1[i] = 1;
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				targetV1_1[i] = 0;
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		double pembilang= (double) Math.abs(inputV1_1[0] + targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			pembilang = pembilang + Math.abs(inputV1_1[i] + targetV1_1[i]);
		}
		
		System.out.println("pembilang "+pembilang);
		double penyebut = (double) Math.max(inputV1_1[0], targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			penyebut = penyebut + Math.max(inputV1_1[i], targetV1_1[i]);
		}
		
		double MinValue = 0.5;
		double MaxValue = 1.0;
		
		Double tempValue = (double) penyebut/pembilang;
		score = normalize(tempValue, MaxValue, MinValue);
		System.out.println("score "+score);
		return score;
	}
	
	//15. kulczynki s
	public static Double kulczynki_sV11(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				inputV1_1[i] = 1;
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				targetV1_1[i] = 0;
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		int pembilang= Math.abs(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			pembilang = pembilang + Math.abs(inputV1_1[i] - targetV1_1[i]);
		}
		int penyebut = Math.min(inputV1_1[0], targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			penyebut = penyebut + Math.min(inputV1_1[i], targetV1_1[i]);
		}
		Double tempValue = (double) penyebut/pembilang;
		score = tempValue;
		return score;
	}
	
	//16. ruzicka
	public static Double ruzickaV11(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				inputV1_1[i] = 1;
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				targetV1_1[i] = 0;
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		int pembilang= Math.min(inputV1_1[0], targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			pembilang = pembilang + Math.min(inputV1_1[i], targetV1_1[i]);
		}
		int penyebut = Math.max(inputV1_1[0], targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			penyebut = penyebut + Math.max(inputV1_1[i], targetV1_1[i]);
		}
		Double tempValue = (double) pembilang/penyebut;
		score = tempValue;
		return score;
	}
	
	//17. tanimoto
	public static Double tanimotoV11(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				inputV1_1[i] = 1;
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				targetV1_1[i] = 0;
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		
		double pembilang= (double) Math.max(inputV1_1[0], targetV1_1[0]) - Math.min(inputV1_1[0], targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			pembilang = pembilang +(Math.max(inputV1_1[i], targetV1_1[i]) - Math.min(inputV1_1[i], targetV1_1[i]));
		}
		double penyebut = (double) Math.max(inputV1_1[0], targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			penyebut = penyebut + Math.max(inputV1_1[i], targetV1_1[i]);
		}
		
		double MinValue = 0.0;
		double MaxValue = 1.0;
		
		Double tempValue = (double) pembilang/penyebut;
		score = normalize(tempValue, MaxValue, MinValue);
		return score;
	}
	
	//18. inner product
	public static Double innerProductV11(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				inputV1_1[i] = 1;
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				targetV1_1[i] = 0;
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		int pembilang= inputV1_1[0]*targetV1_1[0];
		for(int i = 1; i < inputV1_1.length; i++) {
			pembilang = pembilang + inputV1_1[i]*targetV1_1[i];
		}
		
		Double tempValue = (double) pembilang;
		score = tempValue;
		return score;
	}
	
	//20. cosine 
	public static Double cosineV11(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				inputV1_1[i] = 1;
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				targetV1_1[i] = 0;
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		int pembilang= inputV1_1[0]*targetV1_1[0];
		for(int i = 1; i < inputV1_1.length; i++) {
			pembilang = pembilang + inputV1_1[i]*targetV1_1[i];
		}
		
		int p1 = inputV1_1[0]*inputV1_1[0];
		for(int i = 1; i < inputV1_1.length; i++) {
			p1 = p1 + (inputV1_1[i]*inputV1_1[i]);
		}
		
		int p2 = targetV1_1[0]*targetV1_1[0];
		for(int i = 1; i < inputV1_1.length; i++) {
			p2 = p2 + (targetV1_1[i] * targetV1_1[i]);
		}
		
		Double tempValue = (double) pembilang/(Math.sqrt(p1)* Math.sqrt(p2));
		score = 1 - tempValue;
		return score;
	}
	
	//21. kumar hassebrook
	public static Double kumarHassebrookV11(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				inputV1_1[i] = 1;
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				targetV1_1[i] = 0;
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		int p1 = inputV1_1[0] * targetV1_1[0];
		for(int i =1; i < inputV1_1.length; i++) {
			p1 = p1 + inputV1_1[i]* targetV1_1[i];
		}
		
		int p2 = inputV1_1[0]*inputV1_1[0];
		for(int i = 1; i < inputV1_1.length; i++) {
			p2 = p2 + inputV1_1[i]*inputV1_1[i];
		}
		
		int p3 = targetV1_1[0]*targetV1_1[0];
		for(int i = 1; i < inputV1_1.length; i++) {
			p3 = p3 + (targetV1_1[i] * targetV1_1[i]);
		}
		
		Double tempValue = (double) p1/(p2 + p3 -p1);
		score = tempValue;
		return score;
	}
	
	//22. jaccard
	public static Double jaccardV11(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				inputV1_1[i] = 1;
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				targetV1_1[i] = 0;
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		
		int pembilang = (inputV1_1[0] -targetV1_1[0])*(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			pembilang = pembilang + ((inputV1_1[i] - targetV1_1[i])*(inputV1_1[i] - targetV1_1[i]));
		}
		
		int p1 = inputV1_1[0] * targetV1_1[0];
		for(int i =1; i < inputV1_1.length; i++) {
			p1 = p1 + inputV1_1[i]* targetV1_1[i];
		}
		
		int p2 = inputV1_1[0]*inputV1_1[0];
		for(int i = 1; i < inputV1_1.length; i++) {
			p2 = p2 + inputV1_1[i]*inputV1_1[i];
		}
		
		int p3 = targetV1_1[0]*targetV1_1[0];
		for(int i = 1; i < inputV1_1.length; i++) {
			p3 = p3 + (targetV1_1[i] * targetV1_1[i]);
		}
		
		Double tempValue = (double) pembilang/(p2 + p3 -p1);
		score = tempValue;
		return score;
	}
	
	//23. dice
	public static Double diceV11(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				inputV1_1[i] = 1;
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				targetV1_1[i] = 0;
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		
		int pembilang = (inputV1_1[0] -targetV1_1[0])*(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			pembilang = pembilang + ((inputV1_1[i] - targetV1_1[i])*(inputV1_1[i] - targetV1_1[i]));
		}
		
		int p1 = inputV1_1[0] * targetV1_1[0];
		for(int i =1; i < inputV1_1.length; i++) {
			p1 = p1 + inputV1_1[i]* targetV1_1[i];
		}
		
		int p2 = inputV1_1[0]*inputV1_1[0];
		for(int i = 1; i < inputV1_1.length; i++) {
			p2 = p2 + inputV1_1[i]*inputV1_1[i];
		}
		
		int p3 = targetV1_1[0]*targetV1_1[0];
		for(int i = 1; i < inputV1_1.length; i++) {
			p3 = p3 + (targetV1_1[i] * targetV1_1[i]);
		}
		
		Double tempValue = (double) pembilang/(p2 + p3);
		score = tempValue;
		return score;
	}

	//24. fidelity
	public static Double fidelityV11(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				inputV1_1[i] = 1;
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				targetV1_1[i] = 0;
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		
		int pembilang = (inputV1_1[0] -targetV1_1[0])*(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			pembilang = pembilang + ((inputV1_1[i] - targetV1_1[i])*(inputV1_1[i] - targetV1_1[i]));
		}
		
		double p1 = Math.sqrt(inputV1_1[0] * targetV1_1[0]);
		for(int i =1; i < inputV1_1.length; i++) {
			p1 = p1 + Math.sqrt(inputV1_1[i]* targetV1_1[i]);
		}
		
		int p2 = inputV1_1[0]*inputV1_1[0];
		for(int i = 1; i < inputV1_1.length; i++) {
			p2 = p2 + inputV1_1[i]*inputV1_1[i];
		}
		
		int p3 = targetV1_1[0]*targetV1_1[0];
		for(int i = 1; i < inputV1_1.length; i++) {
			p3 = p3 + (targetV1_1[i] * targetV1_1[i]);
		}
		
		Double tempValue = (double) p1;
		score = tempValue;
		return score;
	}
	
	//25. bhattacharyya
	public static Double bhattacharyyaV11(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				inputV1_1[i] = 1;
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				targetV1_1[i] = 0;
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		
		int pembilang = (inputV1_1[0] -targetV1_1[0])*(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			pembilang = pembilang + ((inputV1_1[i] - targetV1_1[i])*(inputV1_1[i] - targetV1_1[i]));
		}
		
		double p1 = Math.sqrt(inputV1_1[0] * targetV1_1[0]);
		for(int i =1; i < inputV1_1.length; i++) {
			p1 = p1 + Math.sqrt(inputV1_1[i]* targetV1_1[i]);
		}
		
		
		Double tempValue = (double) -1 * Math.log(p1);
		score = tempValue;
		return score;
	}
	
	//26. hellinger
	public static Double hellingerV11(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				inputV1_1[i] = 1;
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				targetV1_1[i] = 0;
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		
		int pembilang = (inputV1_1[0] -targetV1_1[0])*(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			pembilang = pembilang + ((inputV1_1[i] - targetV1_1[i])*(inputV1_1[i] - targetV1_1[i]));
		}
		
		double p1 = Math.sqrt(inputV1_1[0] * targetV1_1[0]);
		for(int i =1; i < inputV1_1.length; i++) {
			p1 = p1 + Math.sqrt(inputV1_1[i]* targetV1_1[i]);
		}
		
		
		Double tempValue = (double) 2*Math.sqrt(1 - p1);
		score = tempValue;
		return score;
	}
	
	//28. matusita
	public static Double matusitaV11(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				inputV1_1[i] = 1;
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				targetV1_1[i] = 0;
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		
		int pembilang = (inputV1_1[0] -targetV1_1[0])*(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			pembilang = pembilang + ((inputV1_1[i] - targetV1_1[i])*(inputV1_1[i] - targetV1_1[i]));
		}
		
		double p1 = Math.sqrt(inputV1_1[0] * targetV1_1[0]);
		for(int i =1; i < inputV1_1.length; i++) {
			p1 = p1 + Math.sqrt(inputV1_1[i]* targetV1_1[i]);
		}
		
		
		Double tempValue = (double) Math.sqrt(2 - 2 *p1);
		score = tempValue;
		return score;
	}
	
	//29. squared root
	public static Double squaredRootV11(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				inputV1_1[i] = 1;
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				targetV1_1[i] = 0;
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		
		int pembilang = (inputV1_1[0] -targetV1_1[0])*(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			pembilang = pembilang + ((inputV1_1[i] - targetV1_1[i])*(inputV1_1[i] - targetV1_1[i]));
		}
		
		double p1 = Math.sqrt(inputV1_1[0] * targetV1_1[0]);
		for(int i =1; i < inputV1_1.length; i++) {
			p1 = p1 + Math.sqrt(inputV1_1[i]* targetV1_1[i]);
		}
		
		
		Double tempValue = (double) 2*p1 -1 ;
		score = tempValue;
		return score;
	}
	
	//30. squared euclidean
	public static Double squaredEuclideanV11(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				inputV1_1[i] = 1;
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				targetV1_1[i] = 0;
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		
		int pembilang = (inputV1_1[0] -targetV1_1[0])*(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			pembilang = pembilang + ((inputV1_1[i] - targetV1_1[i])*(inputV1_1[i] - targetV1_1[i]));
		}
		
		Double tempValue = (double) pembilang;
		score = tempValue;
		return score;
	}
	
	//45. avg
	public static Double avgV11(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		//isi value input ataupun target dari graphTemp
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		//hapus value yang sama di graphInput atau graphTarget terhadap graphTemp
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				inputV1_1[i] = 1;
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				targetV1_1[i] = 0;
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		
		int suku1 = Math.abs(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			suku1 = suku1 + Math.abs(inputV1_1[i] - targetV1_1[i]);
		}
		
		int suku2 = Math.abs(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			if(suku2 < Math.abs(inputV1_1[i] - targetV1_1[i])) {
				suku2 = Math.abs(inputV1_1[i] - targetV1_1[i]);
			}
		}
		
		Double tempValue = (double) (suku1+suku2)/2;
		score = tempValue;
		return score;
	}
	
	/*===========================================Pemodelan Vektor Versi V2.0=========================================================*/
	//1. Euclidean Distance V2.0
	public static Double euclideanDistanceV20(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int tempSize;
		System.out.println("graph input size "+graphInput.size());
		System.out.println("graph target size "+graphTarget.size());
		if(graphInput.size() >= graphTarget.size()) {
			tempSize = graphInput.size();
		}else {
			tempSize = graphTarget.size();
		}
		Integer[] inputV2_0 = new Integer[tempSize];
		Integer[] targetV2_0 = new Integer[tempSize]; 
		System.out.println("graphTemp "+graphTemp.size());
		System.out.println("inputV2_0 "+tempSize);
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV2_0[i] = graphTemp.get(i);
			targetV2_0[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		//System.out.println("isi graph target "+graphTarget.size());
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				int y = i - graphTemp.size();
				inputV2_0[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				targetV2_0[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
		}
		
		for(int i = 0; i < inputV2_0.length; i++) {
			if(inputV2_0[i] != null) {
				int temp = inputV2_0[i];
				inputV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		
		for(int i = 0; i < targetV2_0.length; i++) {
			if(targetV2_0[i] != null) {
				int temp = targetV2_0[i];
				targetV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				targetV2_0[i] = 0;
			}
		}
		printArray(inputV2_0);
		printArray(targetV2_0);
		int value = Math.abs(inputV2_0[0] - targetV2_0[0]) * Math.abs(inputV2_0[0] - targetV2_0[0]);
		for(int i = 1; i < inputV2_0.length; i++) {
			value = value + Math.abs(inputV2_0[i] - targetV2_0[i]) * Math.abs(inputV2_0[i] - targetV2_0[i]);
		}
		
		double MinValue = 0.0;
		double MaxValue = 0.0;
		if(inputV2_0[0] == targetV2_0[0]) {
			MaxValue = (inputV2_0[0])*(inputV2_0[0]);
		}else if(inputV2_0[0] > targetV2_0[0]) {
			MaxValue = inputV2_0[0]*inputV2_0[0];
		}else {
			MaxValue = targetV2_0[0]*targetV2_0[0];
		}		
		
		for(int i = 1; i < inputV2_0.length; i++) {
			if(inputV2_0[i] == targetV2_0[i]) {
				MaxValue = MaxValue + (inputV2_0[i]*inputV2_0[i]);
			}else if(inputV2_0[i] > targetV2_0[i]) {
				MaxValue = MaxValue + (inputV2_0[i]*inputV2_0[i]);
			}else {
				MaxValue = MaxValue + (targetV2_0[i]*targetV2_0[i]);
			}
		}
		
		double finalMaxValue = Math.sqrt(MaxValue);
		
		Double tempValue = (double) Math.sqrt(value);
		score = normalize(tempValue, finalMaxValue, MinValue);
		return score;
	}
	
	//2. city block
	public static Double cityBlockV20(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int tempSize;
		if(graphInput.size() >= graphTarget.size()) {
			tempSize = graphInput.size();
		}else {
			tempSize = graphTarget.size();
		}
		Integer[] inputV2_0 = new Integer[tempSize];
		Integer[] targetV2_0 = new Integer[tempSize]; 
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV2_0[i] = graphTemp.get(i);
			targetV2_0[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		//System.out.println("isi graph target "+graphTarget.size());
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				int y = i - graphTemp.size();
				inputV2_0[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				targetV2_0[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
		}
		
		for(int i = 0; i < inputV2_0.length; i++) {
			if(inputV2_0[i] != null) {
				int temp = inputV2_0[i];
				inputV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		
		for(int i = 0; i < targetV2_0.length; i++) {
			if(targetV2_0[i] != null) {
				int temp = targetV2_0[i];
				targetV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				targetV2_0[i] = 0;
			}
		}
		printArray(inputV2_0);
		printArray(targetV2_0);
		int value = Math.abs(inputV2_0[0] - targetV2_0[0]);
		for(int i = 1; i < inputV2_0.length; i++) {
			value = value + Math.abs(inputV2_0[i] - targetV2_0[i]);
		}
		
		double MinValue = 0.0;
		double MaxValue = 0.0;
		if(inputV2_0[0] > targetV2_0[0]) {
			MaxValue = inputV2_0[0];
		}else if(inputV2_0[0] < targetV2_0[0]) {
			MaxValue = targetV2_0[0];
		}else {
			MaxValue = inputV2_0[0];
		}
		
		
		for(int i = 1; i < inputV2_0.length; i++) {
			if(inputV2_0[i] > targetV2_0[i]) {
				MaxValue = MaxValue + inputV2_0[i];
			}else if (inputV2_0[i] < targetV2_0[i]) {
				MaxValue = MaxValue +targetV2_0[i];
			}else {
				MaxValue = MaxValue + targetV2_0[i];
			}
		}
		
		double finalMaxValue = MaxValue;
		
		Double tempValue = (double) value;
		score = normalize(tempValue, finalMaxValue, MinValue);
		return score;
	}
	
	//4. chebyshev
	public static Double chebyshevV20(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int tempSize;
		if(graphInput.size() >= graphTarget.size()) {
			tempSize = graphInput.size();
		}else {
			tempSize = graphTarget.size();
		}
		Integer[] inputV2_0 = new Integer[tempSize];
		Integer[] targetV2_0 = new Integer[tempSize]; 
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV2_0[i] = graphTemp.get(i);
			targetV2_0[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		//System.out.println("isi graph target "+graphTarget.size());
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				int y = i - graphTemp.size();
				inputV2_0[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				targetV2_0[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
		}
		
		for(int i = 0; i < inputV2_0.length; i++) {
			if(inputV2_0[i] != null) {
				int temp = inputV2_0[i];
				inputV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		
		for(int i = 0; i < targetV2_0.length; i++) {
			if(targetV2_0[i] != null) {
				int temp = targetV2_0[i];
				targetV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				targetV2_0[i] = 0;
			}
		}
		printArray(inputV2_0);
		printArray(targetV2_0);
		int value = Math.abs(inputV2_0[0] - targetV2_0[0]);
		for(int i = 1; i < inputV2_0.length; i++) {
			if(value <  Math.abs(inputV2_0[i] - targetV2_0[i]))
				value =  Math.abs(inputV2_0[i] - targetV2_0[i]);
		}
		
		double MinValue = 0.0;
		double MaxValue = 0.0;
		
		if(inputV2_0[0] > targetV2_0[0]) {
			MaxValue = inputV2_0[0];
		}else if(targetV2_0[0] > inputV2_0[0]) {
			MaxValue = targetV2_0[0];
		}else {
			MaxValue = inputV2_0[0];
		}
		
		for(int i = 1; i < inputV2_0.length; i++) {
			if(inputV2_0[i] > targetV2_0[i]) {
				if(MaxValue < inputV2_0[i]) MaxValue = inputV2_0[i]; 
			}else{
				if(MaxValue < targetV2_0[i]) MaxValue = targetV2_0[i];
			}
		}
		
		double finalMaxValue = MaxValue;
		
		Double tempValue = (double) value;
		score = normalize(tempValue, finalMaxValue, MinValue);
		return score;
	}
	
	//5. sorensen
	public static Double sorensenV20(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int tempSize;
		if(graphInput.size() >= graphTarget.size()) {
			tempSize = graphInput.size();
		}else {
			tempSize = graphTarget.size();
		}
		Integer[] inputV2_0 = new Integer[tempSize];
		Integer[] targetV2_0 = new Integer[tempSize]; 
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV2_0[i] = graphTemp.get(i);
			targetV2_0[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		//System.out.println("isi graph target "+graphTarget.size());
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				int y = i - graphTemp.size();
				inputV2_0[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				targetV2_0[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
		}
		
		for(int i = 0; i < inputV2_0.length; i++) {
			if(inputV2_0[i] != null) {
				int temp = inputV2_0[i];
				inputV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		
		for(int i = 0; i < targetV2_0.length; i++) {
			if(targetV2_0[i] != null) {
				int temp = targetV2_0[i];
				targetV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				targetV2_0[i] = 0;
			}
		}
		printArray(inputV2_0);
		printArray(targetV2_0);
		Double pembilang = (double) Math.abs(inputV2_0[0] - targetV2_0[0]);
		for(int i = 1; i < inputV2_0.length; i++) {
			pembilang = pembilang + Math.abs(inputV2_0[i] - targetV2_0[i]);
		}
		
		System.out.println("pembilang "+pembilang);
		
		Double penyebut =(double) inputV2_0[0] + targetV2_0[0];
		for(int i = 1; i < inputV2_0.length; i++) {
			penyebut = penyebut + (inputV2_0[i] + targetV2_0[i]);
		}
		System.out.println("penyebut "+penyebut);
		
		Double tempValue = (double) pembilang/penyebut;
		score = tempValue;
		return score;
	}
	
	//6. gower
	public static Double gowerV20(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int tempSize;
		if(graphInput.size() >= graphTarget.size()) {
			tempSize = graphInput.size();
		}else {
			tempSize = graphTarget.size();
		}
		Integer[] inputV2_0 = new Integer[tempSize];
		Integer[] targetV2_0 = new Integer[tempSize]; 
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV2_0[i] = graphTemp.get(i);
			targetV2_0[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		//System.out.println("isi graph target "+graphTarget.size());
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				int y = i - graphTemp.size();
				inputV2_0[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				targetV2_0[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
		}
		
		for(int i = 0; i < inputV2_0.length; i++) {
			if(inputV2_0[i] != null) {
				int temp = inputV2_0[i];
				inputV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		
		for(int i = 0; i < targetV2_0.length; i++) {
			if(targetV2_0[i] != null) {
				int temp = targetV2_0[i];
				targetV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		printArray(inputV2_0);
		printArray(targetV2_0);
		int pembilang = Math.abs(inputV2_0[0] - targetV2_0[0]);
		for(int i = 1; i < inputV2_0.length; i++) {
			pembilang = pembilang + Math.abs(inputV2_0[i] - targetV2_0[i]);
		}
		
		
		Double tempValue = (double) pembilang/inputV2_0.length;
		score = tempValue;
		return score;
	}
	
	//7. soergel
	public static Double soergelV20(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int tempSize;
		if(graphInput.size() >= graphTarget.size()) {
			tempSize = graphInput.size();
		}else {
			tempSize = graphTarget.size();
		}
		Integer[] inputV2_0 = new Integer[tempSize];
		Integer[] targetV2_0 = new Integer[tempSize]; 
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV2_0[i] = graphTemp.get(i);
			targetV2_0[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		//System.out.println("isi graph target "+graphTarget.size());
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				int y = i - graphTemp.size();
				inputV2_0[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				targetV2_0[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
		}
		
		for(int i = 0; i < inputV2_0.length; i++) {
			if(inputV2_0[i] != null) {
				int temp = inputV2_0[i];
				inputV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		
		for(int i = 0; i < targetV2_0.length; i++) {
			if(targetV2_0[i] != null) {
				int temp = targetV2_0[i];
				targetV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				targetV2_0[i] = 0;
			}
		}
		printArray(inputV2_0);
		printArray(targetV2_0);
		double pembilang = (double) Math.abs(inputV2_0[0] - targetV2_0[0]);
		for(int i = 1; i < inputV2_0.length; i++) {
			pembilang = pembilang + Math.abs(inputV2_0[i] - targetV2_0[i]);
		}
		
		double penyebut = (double) Math.max(inputV2_0[0], targetV2_0[0]);
		for(int i = 1; i < inputV2_0.length; i++) {
			penyebut = penyebut + Math.max(inputV2_0[i], targetV2_0[i]);
		}
		
		Double tempValue = (double) pembilang/penyebut;
		score = tempValue;
		return score;
	}
	
	//8. kulczynki
	public static Double kulczynkiV20(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int tempSize;
		if(graphInput.size() >= graphTarget.size()) {
			tempSize = graphInput.size();
		}else {
			tempSize = graphTarget.size();
		}
		Integer[] inputV2_0 = new Integer[tempSize];
		Integer[] targetV2_0 = new Integer[tempSize]; 
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV2_0[i] = graphTemp.get(i);
			targetV2_0[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		//System.out.println("isi graph target "+graphTarget.size());
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				int y = i - graphTemp.size();
				inputV2_0[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				targetV2_0[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
		}
		
		for(int i = 0; i < inputV2_0.length; i++) {
			if(inputV2_0[i] != null) {
				int temp = inputV2_0[i];
				inputV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		
		for(int i = 0; i < targetV2_0.length; i++) {
			if(targetV2_0[i] != null) {
				int temp = targetV2_0[i];
				targetV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		printArray(inputV2_0);
		printArray(targetV2_0);
		int pembilang = Math.abs(inputV2_0[0] - targetV2_0[0]);
		for(int i = 1; i < inputV2_0.length; i++) {
			pembilang = pembilang + Math.abs(inputV2_0[i] - targetV2_0[i]);
		}
		
		int penyebut = Math.min(inputV2_0[0], targetV2_0[0]);
		for(int i = 1; i < inputV2_0.length; i++) {
			penyebut = penyebut + Math.min(inputV2_0[i], targetV2_0[i]);
		}
		
		Double tempValue = (double) pembilang/penyebut;
		score = tempValue;
		return score;
	}
	
	//10. lorentzian
	public static Double lorentzianV20(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int tempSize;
		if(graphInput.size() >= graphTarget.size()) {
			tempSize = graphInput.size();
		}else {
			tempSize = graphTarget.size();
		}
		Integer[] inputV2_0 = new Integer[tempSize];
		Integer[] targetV2_0 = new Integer[tempSize]; 
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV2_0[i] = graphTemp.get(i);
			targetV2_0[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		//System.out.println("isi graph target "+graphTarget.size());
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				int y = i - graphTemp.size();
				inputV2_0[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				targetV2_0[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
		}
		
		for(int i = 0; i < inputV2_0.length; i++) {
			if(inputV2_0[i] != null) {
				int temp = inputV2_0[i];
				inputV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		
		for(int i = 0; i < targetV2_0.length; i++) {
			if(targetV2_0[i] != null) {
				int temp = targetV2_0[i];
				targetV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				targetV2_0[i] = 0;
			}
		}
		printArray(inputV2_0);
		printArray(targetV2_0);
		double value = Math.log(1 + Math.abs(inputV2_0[0] - targetV2_0[0]));
		for(int i = 1; i < inputV2_0.length; i++) {
			value = value +  Math.log(1 + Math.abs(inputV2_0[i] - targetV2_0[i]));
		}
		
		System.out.println("value "+value);
		
		double MinValue = 0.0;
		double MaxValue = 0.0;
		if(inputV2_0[0] > targetV2_0[0]) {
			MaxValue = Math.log(1 + inputV2_0[0]);
		}else {
			MaxValue = Math.log(1 + targetV2_0[0]);
		}
		
		for(int i = 1; i < inputV2_0.length; i++) {
			if(inputV2_0[i] > targetV2_0[i]) {
				MaxValue = MaxValue + Math.log(1 + inputV2_0[i]);
			}else{
				MaxValue = MaxValue + Math.log(1 + targetV2_0[i]);
			}
		}
		
		System.out.println("MaxValue "+MaxValue);
		
		double finalMaxValue = MaxValue;
		
		Double tempValue = (double) value;
		score = normalize(tempValue, finalMaxValue, MinValue);
		return score;
	}
	
	//11. intersection
	public static Double intersectionV20(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int tempSize;
		if(graphInput.size() >= graphTarget.size()) {
			tempSize = graphInput.size();
		}else {
			tempSize = graphTarget.size();
		}
		Integer[] inputV2_0 = new Integer[tempSize];
		Integer[] targetV2_0 = new Integer[tempSize]; 
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV2_0[i] = graphTemp.get(i);
			targetV2_0[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		//System.out.println("isi graph target "+graphTarget.size());
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				int y = i - graphTemp.size();
				inputV2_0[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				targetV2_0[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
		}
		
		for(int i = 0; i < inputV2_0.length; i++) {
			if(inputV2_0[i] != null) {
				int temp = inputV2_0[i];
				inputV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		
		for(int i = 0; i < targetV2_0.length; i++) {
			if(targetV2_0[i] != null) {
				int temp = targetV2_0[i];
				targetV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		printArray(inputV2_0);
		printArray(targetV2_0);
		int value = Math.abs(inputV2_0[0] - targetV2_0[0]);
		for(int i = 1; i < inputV2_0.length; i++) {
			value = value + Math.abs(inputV2_0[i] - targetV2_0[i]);
		}
		Double tempValue = (double) value/2;
		score = tempValue;
		return score;
	}
	
	//13. czekanowski = sorensen
	
	//14. motyka
	public static Double motykaV20(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int tempSize;
		if(graphInput.size() >= graphTarget.size()) {
			tempSize = graphInput.size();
		}else {
			tempSize = graphTarget.size();
		}
		Integer[] inputV2_0 = new Integer[tempSize];
		Integer[] targetV2_0 = new Integer[tempSize]; 
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV2_0[i] = graphTemp.get(i);
			targetV2_0[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		//System.out.println("isi graph target "+graphTarget.size());
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				int y = i - graphTemp.size();
				inputV2_0[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				targetV2_0[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
		}
		
		for(int i = 0; i < inputV2_0.length; i++) {
			if(inputV2_0[i] != null) {
				int temp = inputV2_0[i];
				inputV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		
		for(int i = 0; i < targetV2_0.length; i++) {
			if(targetV2_0[i] != null) {
				int temp = targetV2_0[i];
				targetV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				targetV2_0[i] = 0;
			}
		}
		printArray(inputV2_0);
		printArray(targetV2_0);
		double pembilang = (double) Math.max(inputV2_0[0], targetV2_0[0]);
		for(int i = 1; i < inputV2_0.length; i++) {
			pembilang = pembilang + Math.max(inputV2_0[i], targetV2_0[i]);
		}
		
		
		
		double penyebut = (double) inputV2_0[0] + targetV2_0[0];
		for(int i = 1; i < inputV2_0.length; i++) {
			penyebut = penyebut + (inputV2_0[i] + targetV2_0[i]);
		}
		
		double MinValue = 0.5;
		double MaxValue = 1.0;
		
		
		Double tempValue = (double) pembilang/penyebut;
		score = normalize(tempValue, MaxValue, MinValue);
		return score;
	}
	
	//15. kulczynki s
	public static Double kulczynki_sV20(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int tempSize;
		if(graphInput.size() >= graphTarget.size()) {
			tempSize = graphInput.size();
		}else {
			tempSize = graphTarget.size();
		}
		Integer[] inputV2_0 = new Integer[tempSize];
		Integer[] targetV2_0 = new Integer[tempSize]; 
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV2_0[i] = graphTemp.get(i);
			targetV2_0[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		//System.out.println("isi graph target "+graphTarget.size());
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				int y = i - graphTemp.size();
				inputV2_0[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				targetV2_0[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
		}
		
		for(int i = 0; i < inputV2_0.length; i++) {
			if(inputV2_0[i] != null) {
				int temp = inputV2_0[i];
				inputV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		
		for(int i = 0; i < targetV2_0.length; i++) {
			if(targetV2_0[i] != null) {
				int temp = targetV2_0[i];
				targetV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		printArray(inputV2_0);
		printArray(targetV2_0);
		int pembilang = Math.min(inputV2_0[0], targetV2_0[0]);
		for(int i = 1; i < inputV2_0.length; i++) {
			pembilang = pembilang + Math.min(inputV2_0[i], targetV2_0[i]);
		}
		
		int penyebut = inputV2_0[0] + targetV2_0[0];
		for(int i = 1; i < inputV2_0.length; i++) {
			penyebut = penyebut + (inputV2_0[i] + targetV2_0[i]);
		}
		Double tempValue = (double) pembilang/penyebut;
		score = tempValue;
		return score;
	}
	
	//16. ruzicka
	public static Double ruzickaV20(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int tempSize;
		if(graphInput.size() >= graphTarget.size()) {
			tempSize = graphInput.size();
		}else {
			tempSize = graphTarget.size();
		}
		Integer[] inputV2_0 = new Integer[tempSize];
		Integer[] targetV2_0 = new Integer[tempSize]; 
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV2_0[i] = graphTemp.get(i);
			targetV2_0[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		//System.out.println("isi graph target "+graphTarget.size());
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				int y = i - graphTemp.size();
				inputV2_0[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				targetV2_0[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
		}
		
		for(int i = 0; i < inputV2_0.length; i++) {
			if(inputV2_0[i] != null) {
				int temp = inputV2_0[i];
				inputV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		
		for(int i = 0; i < targetV2_0.length; i++) {
			if(targetV2_0[i] != null) {
				int temp = targetV2_0[i];
				targetV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		printArray(inputV2_0);
		printArray(targetV2_0);
		int pembilang = Math.min(inputV2_0[0], targetV2_0[0]);
		for(int i = 1; i < inputV2_0.length; i++) {
			pembilang = pembilang + Math.min(inputV2_0[i], targetV2_0[i]);
		}
		
		int penyebut = Math.max(inputV2_0[0], targetV2_0[0]);
		for(int i = 1; i < inputV2_0.length; i++) {
			penyebut = penyebut + Math.max(inputV2_0[i], targetV2_0[i]);
		}
		Double tempValue = (double) pembilang/penyebut;
		score = tempValue;
		return score;
	}
	
	//17. tanimoto
	public static Double tanimotoV20(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int tempSize;
		if(graphInput.size() >= graphTarget.size()) {
			tempSize = graphInput.size();
		}else {
			tempSize = graphTarget.size();
		}
		Integer[] inputV2_0 = new Integer[tempSize];
		Integer[] targetV2_0 = new Integer[tempSize]; 
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV2_0[i] = graphTemp.get(i);
			targetV2_0[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		//System.out.println("isi graph target "+graphTarget.size());
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				int y = i - graphTemp.size();
				inputV2_0[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				targetV2_0[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
		}
		
		for(int i = 0; i < inputV2_0.length; i++) {
			if(inputV2_0[i] != null) {
				int temp = inputV2_0[i];
				inputV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		
		for(int i = 0; i < targetV2_0.length; i++) {
			if(targetV2_0[i] != null) {
				int temp = targetV2_0[i];
				targetV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		printArray(inputV2_0);
		printArray(targetV2_0);
		double pembilang = (double) Math.max(inputV2_0[0], targetV2_0[0])- Math.min(inputV2_0[0], targetV2_0[0]);
		for(int i = 1; i < inputV2_0.length; i++) {
			pembilang = pembilang + (Math.max(inputV2_0[i], targetV2_0[i]) - Math.min(inputV2_0[i], targetV2_0[i]));
		}
		
		double penyebut = (double) Math.max(inputV2_0[0], targetV2_0[0]);
		for(int i = 1; i < inputV2_0.length; i++) {
			penyebut = penyebut + Math.max(inputV2_0[i], targetV2_0[i]);
		}
		Double tempValue = (double) pembilang/penyebut;
		score = tempValue;
		return score;
	}
	
	//18.inner product
	public static Double innerProductV20(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int tempSize;
		if(graphInput.size() >= graphTarget.size()) {
			tempSize = graphInput.size();
		}else {
			tempSize = graphTarget.size();
		}
		Integer[] inputV2_0 = new Integer[tempSize];
		Integer[] targetV2_0 = new Integer[tempSize]; 
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV2_0[i] = graphTemp.get(i);
			targetV2_0[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		//System.out.println("isi graph target "+graphTarget.size());
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				int y = i - graphTemp.size();
				inputV2_0[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				targetV2_0[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
		}
		
		for(int i = 0; i < inputV2_0.length; i++) {
			if(inputV2_0[i] != null) {
				int temp = inputV2_0[i];
				inputV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		
		for(int i = 0; i < targetV2_0.length; i++) {
			if(targetV2_0[i] != null) {
				int temp = targetV2_0[i];
				targetV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		printArray(inputV2_0);
		printArray(targetV2_0);
		int value = inputV2_0[0]*targetV2_0[0];
		for(int i = 1; i < inputV2_0.length; i++) {
			value = value +(inputV2_0[i]*targetV2_0[i]);
		}
		Double tempValue = (double) value;
		score = tempValue;
		return score;
	}
	
	//20. cosine
	public static Double cosineV20(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int tempSize;
		if(graphInput.size() >= graphTarget.size()) {
			tempSize = graphInput.size();
		}else {
			tempSize = graphTarget.size();
		}
		Integer[] inputV2_0 = new Integer[tempSize];
		Integer[] targetV2_0 = new Integer[tempSize]; 
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV2_0[i] = graphTemp.get(i);
			targetV2_0[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		//System.out.println("isi graph target "+graphTarget.size());
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				int y = i - graphTemp.size();
				inputV2_0[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				targetV2_0[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
		}
		
		for(int i = 0; i < inputV2_0.length; i++) {
			if(inputV2_0[i] != null) {
				int temp = inputV2_0[i];
				inputV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		
		for(int i = 0; i < targetV2_0.length; i++) {
			if(targetV2_0[i] != null) {
				int temp = targetV2_0[i];
				targetV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				targetV2_0[i] = 0;
			}
		}
		printArray(inputV2_0);
		printArray(targetV2_0);
		int p1 = inputV2_0[0] * targetV2_0[0];
		for(int i = 1; i < inputV2_0.length; i++) {
			p1 = p1 + (inputV2_0[i] * targetV2_0[i]);
		}
		
		int p2 = inputV2_0[0]*inputV2_0[0];
		for(int i = 1; i < targetV2_0.length; i++) {
			p2 = p2 + (inputV2_0[i] * inputV2_0[i]);
		}
		
		int p3 = targetV2_0[0]*targetV2_0[0];
		for(int i = 1; i < inputV2_0.length; i++) {
			p3 = p3 + (targetV2_0[i]* targetV2_0[i]);
		}
		Double tempValue = (double) p1/(Math.sqrt(p2) * Math.sqrt(p3));
		score = 1 - tempValue;
		return score;
	}
	
	//21. kumar hassebrook
	public static Double kumarHassebrookV20(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int tempSize;
		if(graphInput.size() >= graphTarget.size()) {
			tempSize = graphInput.size();
		}else {
			tempSize = graphTarget.size();
		}
		Integer[] inputV2_0 = new Integer[tempSize];
		Integer[] targetV2_0 = new Integer[tempSize]; 
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV2_0[i] = graphTemp.get(i);
			targetV2_0[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		//System.out.println("isi graph target "+graphTarget.size());
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				int y = i - graphTemp.size();
				inputV2_0[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				targetV2_0[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
		}
		
		for(int i = 0; i < inputV2_0.length; i++) {
			if(inputV2_0[i] != null) {
				int temp = inputV2_0[i];
				inputV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		
		for(int i = 0; i < targetV2_0.length; i++) {
			if(targetV2_0[i] != null) {
				int temp = targetV2_0[i];
				targetV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		printArray(inputV2_0);
		printArray(targetV2_0);
		int p1 = inputV2_0[0] * targetV2_0[0];
		for(int i = 1; i < inputV2_0.length; i++) {
			p1 = p1 + (inputV2_0[0] * targetV2_0[0]);
		}
		
		int p2 = inputV2_0[0]*inputV2_0[0];
		for(int i = 1; i < targetV2_0.length; i++) {
			p2 = p2 + (inputV2_0[i] * inputV2_0[i]);
		}
		
		int p3 = targetV2_0[0]*targetV2_0[0];
		for(int i = 1; i < inputV2_0.length; i++) {
			p3 = p3 + (targetV2_0[i]* targetV2_0[i]);
		}
		Double tempValue = (double) p1/(p2 + p3 - p1);
		score = tempValue;
		return score;
	}
	
	//22. jaccard
	public static Double jaccardV20(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int tempSize;
		if(graphInput.size() >= graphTarget.size()) {
			tempSize = graphInput.size();
		}else {
			tempSize = graphTarget.size();
		}
		Integer[] inputV2_0 = new Integer[tempSize];
		Integer[] targetV2_0 = new Integer[tempSize]; 
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV2_0[i] = graphTemp.get(i);
			targetV2_0[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		//System.out.println("isi graph target "+graphTarget.size());
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				int y = i - graphTemp.size();
				inputV2_0[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				targetV2_0[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
		}
		
		for(int i = 0; i < inputV2_0.length; i++) {
			if(inputV2_0[i] != null) {
				int temp = inputV2_0[i];
				inputV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		
		for(int i = 0; i < targetV2_0.length; i++) {
			if(targetV2_0[i] != null) {
				int temp = targetV2_0[i];
				targetV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				targetV2_0[i] = 0;
			}
		}
		printArray(inputV2_0);
		printArray(targetV2_0);
		
		double pembilang = (double) (inputV2_0[0] - targetV2_0[0])*(inputV2_0[0] - targetV2_0[0]);
		for(int i = 1; i < inputV2_0.length; i++) {
			pembilang = pembilang +( (inputV2_0[i] - targetV2_0[i])*(inputV2_0[i] - targetV2_0[i]));
		}
		
		System.out.println("pembilang "+pembilang);
		
		double p1 = (double) inputV2_0[0] * targetV2_0[0];
		for(int i = 1; i < inputV2_0.length; i++) {
			p1 = p1 + (inputV2_0[i] * targetV2_0[i]);
		}
		
		System.out.println("p1 "+p1);
		
		double p2 = (double) inputV2_0[0]*inputV2_0[0];
		for(int i = 1; i < targetV2_0.length; i++) {
			p2 = p2 + (inputV2_0[i] * inputV2_0[i]);
		}
		
		System.out.println("p2 "+p2);
		
		double p3 = (double) targetV2_0[0]*targetV2_0[0];
		for(int i = 1; i < inputV2_0.length; i++) {
			p3 = p3 + (targetV2_0[i]* targetV2_0[i]);
		}
		
		System.out.println("p3 "+p3);
		
		Double tempValue = (double) pembilang/(p2 + p3 - p1);
		score = tempValue;
		return score;
	}
	
	//23.dice
	public static Double diceV20(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int tempSize;
		if(graphInput.size() >= graphTarget.size()) {
			tempSize = graphInput.size();
		}else {
			tempSize = graphTarget.size();
		}
		Integer[] inputV2_0 = new Integer[tempSize];
		Integer[] targetV2_0 = new Integer[tempSize]; 
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV2_0[i] = graphTemp.get(i);
			targetV2_0[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		//System.out.println("isi graph target "+graphTarget.size());
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				int y = i - graphTemp.size();
				inputV2_0[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				targetV2_0[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
		}
		
		for(int i = 0; i < inputV2_0.length; i++) {
			if(inputV2_0[i] != null) {
				int temp = inputV2_0[i];
				inputV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		
		for(int i = 0; i < targetV2_0.length; i++) {
			if(targetV2_0[i] != null) {
				int temp = targetV2_0[i];
				targetV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		printArray(inputV2_0);
		printArray(targetV2_0);
		
		int pembilang = (inputV2_0[0] - targetV2_0[0])*(inputV2_0[0] - targetV2_0[0]);
		for(int i = 1; i < inputV2_0.length; i++) {
			pembilang = pembilang +( (inputV2_0[i] - targetV2_0[i])*(inputV2_0[i] - targetV2_0[i]));
		}
		
		int p2 = inputV2_0[0]*inputV2_0[0];
		for(int i = 1; i < targetV2_0.length; i++) {
			p2 = p2 + (inputV2_0[i] * inputV2_0[i]);
		}
		
		int p3 = targetV2_0[0]*targetV2_0[0];
		for(int i = 1; i < inputV2_0.length; i++) {
			p3 = p3 + (targetV2_0[i]* targetV2_0[i]);
		}
		Double tempValue = (double) pembilang/(p2 + p3);
		score = tempValue;
		return score;
	}
	
	//24. fidelity
	public static Double fidelityV20(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int tempSize;
		if(graphInput.size() >= graphTarget.size()) {
			tempSize = graphInput.size();
		}else {
			tempSize = graphTarget.size();
		}
		Integer[] inputV2_0 = new Integer[tempSize];
		Integer[] targetV2_0 = new Integer[tempSize]; 
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV2_0[i] = graphTemp.get(i);
			targetV2_0[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		//System.out.println("isi graph target "+graphTarget.size());
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				int y = i - graphTemp.size();
				inputV2_0[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				targetV2_0[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
		}
		
		for(int i = 0; i < inputV2_0.length; i++) {
			if(inputV2_0[i] != null) {
				int temp = inputV2_0[i];
				inputV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		
		for(int i = 0; i < targetV2_0.length; i++) {
			if(targetV2_0[i] != null) {
				int temp = targetV2_0[i];
				targetV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		printArray(inputV2_0);
		printArray(targetV2_0);
		
		
		double p1 = Math.sqrt(inputV2_0[0] * targetV2_0[0]);
		for(int i = 1; i < inputV2_0.length; i++) {
			p1 = p1 + Math.sqrt(inputV2_0[0] * targetV2_0[0]);
		}
		
		Double tempValue = (double) p1;
		score = tempValue;
		return score;
	}
	
	//25. bhattacharyya
	public static Double bhattacharyyaV20(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int tempSize;
		if(graphInput.size() >= graphTarget.size()) {
			tempSize = graphInput.size();
		}else {
			tempSize = graphTarget.size();
		}
		Integer[] inputV2_0 = new Integer[tempSize];
		Integer[] targetV2_0 = new Integer[tempSize]; 
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV2_0[i] = graphTemp.get(i);
			targetV2_0[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		//System.out.println("isi graph target "+graphTarget.size());
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				int y = i - graphTemp.size();
				inputV2_0[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				targetV2_0[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
		}
		
		for(int i = 0; i < inputV2_0.length; i++) {
			if(inputV2_0[i] != null) {
				int temp = inputV2_0[i];
				inputV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		
		for(int i = 0; i < targetV2_0.length; i++) {
			if(targetV2_0[i] != null) {
				int temp = targetV2_0[i];
				targetV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		printArray(inputV2_0);
		printArray(targetV2_0);
		
		
		double p1 = Math.sqrt(inputV2_0[0] * targetV2_0[0]);
		for(int i = 1; i < inputV2_0.length; i++) {
			p1 = p1 + Math.sqrt(inputV2_0[0] * targetV2_0[0]);
		}
		
		Double tempValue = (double) -1 * Math.log(p1);
		score = tempValue;
		return score;
	}
	
	//26. hellinger
	public static Double hellingerV20(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int tempSize;
		if(graphInput.size() >= graphTarget.size()) {
			tempSize = graphInput.size();
		}else {
			tempSize = graphTarget.size();
		}
		Integer[] inputV2_0 = new Integer[tempSize];
		Integer[] targetV2_0 = new Integer[tempSize]; 
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV2_0[i] = graphTemp.get(i);
			targetV2_0[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		//System.out.println("isi graph target "+graphTarget.size());
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				int y = i - graphTemp.size();
				inputV2_0[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				targetV2_0[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
		}
		
		for(int i = 0; i < inputV2_0.length; i++) {
			if(inputV2_0[i] != null) {
				int temp = inputV2_0[i];
				inputV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		
		for(int i = 0; i < targetV2_0.length; i++) {
			if(targetV2_0[i] != null) {
				int temp = targetV2_0[i];
				targetV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		printArray(inputV2_0);
		printArray(targetV2_0);
		
		
		double p1 = Math.sqrt(inputV2_0[0] * targetV2_0[0]);
		for(int i = 1; i < inputV2_0.length; i++) {
			p1 = p1 + Math.sqrt(inputV2_0[0] * targetV2_0[0]);
		}
		
		Double tempValue = (double) 2*Math.sqrt(1 - p1);
		score = tempValue;
		return score;
	}
	
	//27. matusita
	public static Double matusitaV20(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int tempSize;
		if(graphInput.size() >= graphTarget.size()) {
			tempSize = graphInput.size();
		}else {
			tempSize = graphTarget.size();
		}
		Integer[] inputV2_0 = new Integer[tempSize];
		Integer[] targetV2_0 = new Integer[tempSize]; 
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV2_0[i] = graphTemp.get(i);
			targetV2_0[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		//System.out.println("isi graph target "+graphTarget.size());
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				int y = i - graphTemp.size();
				inputV2_0[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				targetV2_0[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
		}
		
		for(int i = 0; i < inputV2_0.length; i++) {
			if(inputV2_0[i] != null) {
				int temp = inputV2_0[i];
				inputV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		
		for(int i = 0; i < targetV2_0.length; i++) {
			if(targetV2_0[i] != null) {
				int temp = targetV2_0[i];
				targetV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		printArray(inputV2_0);
		printArray(targetV2_0);
		
		
		double p1 = Math.sqrt(inputV2_0[0] * targetV2_0[0]);
		for(int i = 1; i < inputV2_0.length; i++) {
			p1 = p1 + Math.sqrt(inputV2_0[0] * targetV2_0[0]);
		}
		
		Double tempValue = (double) Math.sqrt(2 - 2*p1);
		score = tempValue;
		return score;
	}
	
	//28. squared chord
	public static Double squaredChordV20(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int tempSize;
		if(graphInput.size() >= graphTarget.size()) {
			tempSize = graphInput.size();
		}else {
			tempSize = graphTarget.size();
		}
		Integer[] inputV2_0 = new Integer[tempSize];
		Integer[] targetV2_0 = new Integer[tempSize]; 
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV2_0[i] = graphTemp.get(i);
			targetV2_0[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		//System.out.println("isi graph target "+graphTarget.size());
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				int y = i - graphTemp.size();
				inputV2_0[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				targetV2_0[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
		}
		
		for(int i = 0; i < inputV2_0.length; i++) {
			if(inputV2_0[i] != null) {
				int temp = inputV2_0[i];
				inputV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		
		for(int i = 0; i < targetV2_0.length; i++) {
			if(targetV2_0[i] != null) {
				int temp = targetV2_0[i];
				targetV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		printArray(inputV2_0);
		printArray(targetV2_0);
		
		
		double p1 = Math.sqrt(inputV2_0[0] * targetV2_0[0]);
		for(int i = 1; i < inputV2_0.length; i++) {
			p1 = p1 + Math.sqrt(inputV2_0[0] * targetV2_0[0]);
		}
		
		Double tempValue = (double) 2*p1 - 1;
		score = tempValue;
		return score;
	}
	
	//29. squared euclidean
	public static Double squaredEuclideanV20(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int tempSize;
		if(graphInput.size() >= graphTarget.size()) {
			tempSize = graphInput.size();
		}else {
			tempSize = graphTarget.size();
		}
		Integer[] inputV2_0 = new Integer[tempSize];
		Integer[] targetV2_0 = new Integer[tempSize]; 
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV2_0[i] = graphTemp.get(i);
			targetV2_0[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		//System.out.println("isi graph target "+graphTarget.size());
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				int y = i - graphTemp.size();
				inputV2_0[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				targetV2_0[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
		}
		
		for(int i = 0; i < inputV2_0.length; i++) {
			if(inputV2_0[i] != null) {
				int temp = inputV2_0[i];
				inputV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		
		for(int i = 0; i < targetV2_0.length; i++) {
			if(targetV2_0[i] != null) {
				int temp = targetV2_0[i];
				targetV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		printArray(inputV2_0);
		printArray(targetV2_0);
		
		
		double p1 = (inputV2_0[0] - targetV2_0[0])*(inputV2_0[0] - targetV2_0[0]);
		for(int i = 1; i < inputV2_0.length; i++) {
			p1 = p1 + ( (inputV2_0[0] - targetV2_0[0])*(inputV2_0[0] - targetV2_0[0]));
		}
		
		Double tempValue = (double) p1;
		score = tempValue;
		return score;
	}
	
	//45. avg
	public static Double avgV20(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int tempSize;
		if(graphInput.size() >= graphTarget.size()) {
			tempSize = graphInput.size();
		}else {
			tempSize = graphTarget.size();
		}
		Integer[] inputV2_0 = new Integer[tempSize];
		Integer[] targetV2_0 = new Integer[tempSize]; 
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV2_0[i] = graphTemp.get(i);
			targetV2_0[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		//System.out.println("isi graph target "+graphTarget.size());
		if(graphInput.size() > graphTarget.size()) {
			//System.out.println("graphInput.size() >= graphTarget.size()");
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				int y = i - graphTemp.size();
				inputV2_0[i] = graphInput.get(y);
			}
		}else if(graphInput.size() < graphTarget.size()) {
			//System.out.println("graphInput.size() < graphTarget.size()");
			for(int i = graphTemp.size(); i <inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
			//System.out.println("ddddd "+targetV2_0.length);
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				int y = i - graphTemp.size();
				//System.out.println("y "+y+" "+"isi graphTarget "+graphTarget.get(y));
				targetV2_0[i] = graphTarget.get(y);
			}
		}else {
			for(int i = graphTemp.size(); i <targetV2_0.length; i++) {
				targetV2_0[i] = null;
			}
			for(int i = graphTemp.size(); i < inputV2_0.length; i++) {
				inputV2_0[i] = null;
			}
		}
		
		for(int i = 0; i < inputV2_0.length; i++) {
			if(inputV2_0[i] != null) {
				int temp = inputV2_0[i];
				inputV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		
		for(int i = 0; i < targetV2_0.length; i++) {
			if(targetV2_0[i] != null) {
				int temp = targetV2_0[i];
				targetV2_0[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else {
				inputV2_0[i] = 0;
			}
		}
		printArray(inputV2_0);
		printArray(targetV2_0);
		
		
		double p1 = Math.abs(inputV2_0[0] - targetV2_0[0]);
		for(int i = 1; i < inputV2_0.length; i++) {
			p1 = p1 + Math.abs(inputV2_0[i] - targetV2_0[i]);
		}
		
		double p2 = Math.abs(inputV2_0[0] - targetV2_0[0]);
		for(int i = 1; i < targetV2_0.length; i++) {
			if(p2 < Math.abs(inputV2_0[i] - targetV2_0[i])) p2 = Math.abs(inputV2_0[i] - targetV2_0[i]);
		}
		Double tempValue = (double) (p1 + p2)/2;
		score = tempValue;
		return score;
	}
	
	/*===========================================Pemodelan Vektor Versi V2.1=========================================================*/
	//Euclidean Distance V2.1
	public static Double euclideanDistanceV21(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
	
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				int temp = inputV1_1[i];
				inputV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] != null) {
				int temp = targetV1_1[i];
				targetV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else{
				targetV1_1[i] = 0;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		int value = Math.abs(inputV1_1[0] - targetV1_1[0]) * Math.abs(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			value = value + Math.abs(inputV1_1[i] - targetV1_1[i]) * Math.abs(inputV1_1[i] - targetV1_1[i]);
		}
		
		double MinValue = 0.0;
		double MaxValue = 0.0;
		
		if(inputV1_1[0] > targetV1_1[0]) {
			MaxValue = inputV1_1[0] * inputV1_1[0];
		}else {
			MaxValue = targetV1_1[0] * targetV1_1[0];
		}
		
		for(int i = 1; i < inputV1_1.length; i++) {
			if(inputV1_1[i] > targetV1_1[i]) {
				MaxValue = MaxValue + (inputV1_1[i] * inputV1_1[i]);
			}else {
				MaxValue = MaxValue + (targetV1_1[i] * targetV1_1[i]);
			}
		}
		
		double finalMaxValue = Math.sqrt(MaxValue);
		
		Double tempValue = (double) Math.sqrt(value);
		score = normalize(tempValue, finalMaxValue, MinValue);
		return score;
	}
	
	//city block
	public static Double cityBlockV21(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
	
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				int temp = inputV1_1[i];
				inputV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] != null) {
				int temp = targetV1_1[i];
				targetV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else{
				targetV1_1[i] = 0;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		int value = Math.abs(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			value = value + Math.abs(inputV1_1[i] - targetV1_1[i]);
		}
		System.out.println("inputV1_1 "+inputV1_1.length);
		System.out.println("targetV1_1 "+targetV1_1.length);
		double MinValue = 0.0;
		double MaxValue = 0.0;
		if(inputV1_1[0] > targetV1_1[0]) {
			MaxValue = inputV1_1[0];
		}else {
			MaxValue = targetV1_1[0];
		}
		
		for(int i = 1; i < inputV1_1.length; i++) {
			if(inputV1_1[i] > targetV1_1[i]) {
				MaxValue = MaxValue + inputV1_1[i]; 
			}else {
				MaxValue = MaxValue + targetV1_1[i];
			}
		}
		
		double finalMaxValue = MaxValue;
		
		Double tempValue = (double) value;
		score = normalize(tempValue, finalMaxValue, MinValue);
		return score;
	}
	
	//4. chebyshev
	public static Double chebyshevV21(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
	
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				int temp = inputV1_1[i];
				inputV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] != null) {
				int temp = targetV1_1[i];
				targetV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else{
				targetV1_1[i] = 0;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		int value = Math.abs(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			if(value < Math.abs(inputV1_1[i] - targetV1_1[i]) )
			value = Math.abs(inputV1_1[i] - targetV1_1[i]);
		}
		
		double MinValue = 0.0;
		double MaxValue = 0.0;
		
		if(inputV1_1[0] > targetV1_1[0]) {
			MaxValue = inputV1_1[0];
		}else {
			MaxValue = targetV1_1[0];
		}
		
		for(int i = 1; i < inputV1_1.length; i++) {
			if(inputV1_1[i] > targetV1_1[i]) {
				if(MaxValue < inputV1_1[i]) {
					MaxValue = inputV1_1[i];
				}
			}else {
				if(MaxValue < targetV1_1[i]) {
					MaxValue = targetV1_1[i];
				}
			}
		}
		
		
		Double tempValue = (double) value;
		score = normalize(tempValue, MaxValue, MinValue);
		return score;
	}
	
	//5. sorensen
	public static Double sorensenV21(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
	
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				int temp = inputV1_1[i];
				inputV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] != null) {
				int temp = targetV1_1[i];
				targetV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else{
				targetV1_1[i] = 0;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		Double value = (double) Math.abs(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			value = value + Math.abs(inputV1_1[i] - targetV1_1[i]);
		}
		System.out.println("pembilang "+value);
		
		Double penyebut = (double) inputV1_1[0] + targetV1_1[0];
		for(int i = 1; i < inputV1_1.length; i++) {
			penyebut = penyebut + (inputV1_1[i] + targetV1_1[i]);
		}
		
		System.out.println("penyebut "+penyebut);
		
		Double tempValue = (double) value/penyebut;
		score = tempValue;
		return score;
	}
	
	//6. gower
	public static Double gowerV21(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
	
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				int temp = inputV1_1[i];
				inputV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				int temp = targetV1_1[i];
				targetV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		int value = Math.abs(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			value = value + Math.abs(inputV1_1[i] - targetV1_1[i]);
		}
		
		Double tempValue = (double) value/inputV1_1.length;
		score = tempValue;
		return score;
	}
	
	//7. soergel
	public static Double soergelV21(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
	
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				int temp = inputV1_1[i];
				inputV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] != null) {
				int temp = targetV1_1[i];
				targetV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else{
				targetV1_1[i] = 0;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		int value = Math.abs(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			value = value + Math.abs(inputV1_1[i] - targetV1_1[i]);
		}
		
		int penyebut = Math.max(inputV1_1[0],targetV1_1[0]);
		for(int i =1; i < inputV1_1.length; i++) {
			penyebut = penyebut + Math.max(inputV1_1[0],targetV1_1[i]);
		}
		Double tempValue = (double) value/penyebut;
		score = tempValue;
		return score;
	}
	
	//8. kulczynki
	public static Double kulczynkiV21(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
	
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				int temp = inputV1_1[i];
				inputV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				int temp = targetV1_1[i];
				targetV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		int value = Math.abs(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			value = value + Math.abs(inputV1_1[i] - targetV1_1[i]);
		}
		
		int penyebut = Math.min(inputV1_1[0],targetV1_1[0]);
		for(int i =1; i < inputV1_1.length; i++) {
			penyebut = penyebut + Math.min(inputV1_1[0],targetV1_1[i]);
		}
		Double tempValue = (double) value/penyebut;
		score = tempValue;
		return score;
	}
	
	//10. lorentzian
	public static Double lorentzianV21(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
	
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				int temp = inputV1_1[i];
				inputV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] != null) {
				int temp = targetV1_1[i];
				targetV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else{
				targetV1_1[i] = 0;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		double value = Math.log(1 + Math.abs(inputV1_1[0] - targetV1_1[0]));
		for(int i = 1; i < inputV1_1.length; i++) {
			value = value + Math.log(1 + Math.abs(inputV1_1[i] - targetV1_1[i]));
		}
		
		double MinValue = 0.0;
		double MaxValue = 0.0;
		
		if(inputV1_1[0] > targetV1_1[0]) {
			MaxValue = Math.log(1 + inputV1_1[0]);
		}else {
			MaxValue = Math.log(1 + targetV1_1[0]);
		}
		
		for(int i = 1; i < inputV1_1.length; i++) {
			if(inputV1_1[i] > targetV1_1[i]) {
				MaxValue = MaxValue + Math.log(1 + inputV1_1[i]);
			}else{
				MaxValue = MaxValue + Math.log(1 + targetV1_1[i]);
			}
		}
		
		double finalMaxValue = MaxValue;
		
		Double tempValue = (double) value;
		score = normalize(tempValue, finalMaxValue, MinValue);
		return score;
	}
	
	//11. intersection
	public static Double intersectionV21(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
	
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				int temp = inputV1_1[i];
				inputV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				int temp = targetV1_1[i];
				targetV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		int value = Math.abs(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			value = value + Math.abs(inputV1_1[i] - targetV1_1[i]);
		}
		
		Double tempValue = (double) value/2;
		score = tempValue;
		return score;
	}
	
	//13. czekanowski = sorensen
	
	//14. motyka
	public static Double motykaV21(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
	
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				int temp = inputV1_1[i];
				inputV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] != null) {
				int temp = targetV1_1[i];
				targetV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else{
				targetV1_1[i] = 0;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		double value = (double) Math.max(inputV1_1[0],targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			value = value + Math.max(inputV1_1[i],targetV1_1[i]);
		}
		
		double penyebut = (double) inputV1_1[0] + targetV1_1[0];
		for(int i =1; i < inputV1_1.length; i++) {
			penyebut = penyebut + (inputV1_1[i] + targetV1_1[i]);
		}
		
		double MinValue = 0.5;
		double MaxValue = 1.0;
		
		Double tempValue = (double) value/penyebut;
		score = normalize(tempValue, MaxValue, MinValue);
		return score;
	}
	
	//15. kulczynki
	public static Double kulczynki_sV21(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
	
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				int temp = inputV1_1[i];
				inputV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				int temp = targetV1_1[i];
				targetV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		int value = Math.min(inputV1_1[0],targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			value = value + Math.min(inputV1_1[i],targetV1_1[i]);
		}
		
		int penyebut = Math.abs(inputV1_1[0]-targetV1_1[0]);
		for(int i =1; i < inputV1_1.length; i++) {
			penyebut = penyebut + Math.abs(inputV1_1[0] - targetV1_1[i]);
		}
		Double tempValue = (double) value/penyebut;
		score = tempValue;
		return score;
	}
	
	//16. ruzicka
	public static Double ruzickaV21(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				int temp = inputV1_1[i];
				inputV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				int temp = targetV1_1[i];
				targetV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		int value = Math.min(inputV1_1[0],targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			value = value + Math.min(inputV1_1[i],targetV1_1[i]);
		}
		
		int penyebut = Math.max(inputV1_1[0],targetV1_1[0]);
		for(int i =1; i < inputV1_1.length; i++) {
			penyebut = penyebut + Math.max(inputV1_1[0],targetV1_1[i]);
		}
		Double tempValue = (double) value/penyebut;
		score = tempValue;
		return score;
	}
	
	//17. tanimoto
	public static Double tanimotoV21(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a-c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				int temp = inputV1_1[i];
				inputV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] != null) {
				int temp = targetV1_1[i];
				targetV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else{
				targetV1_1[i] = 0;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		double value = (double) Math.max(inputV1_1[0],targetV1_1[0]) - Math.min(inputV1_1[0], targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			value = value + (Math.max(inputV1_1[i], targetV1_1[i]) - Math.min(inputV1_1[i], targetV1_1[i]));
		}
		
		double penyebut =(double) Math.max(inputV1_1[0],targetV1_1[0]);
		for(int i =1; i < inputV1_1.length; i++) {
			penyebut = penyebut + Math.max(inputV1_1[0],targetV1_1[i]);
		}
		Double tempValue = (double) value/penyebut;
		score = tempValue;
		return score;
	}
	
	//18. inner product
	public static Double innerProductV21(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
	
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				int temp = inputV1_1[i];
				inputV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				int temp = targetV1_1[i];
				targetV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		int p1 = inputV1_1[0]*targetV1_1[0];
		for(int i = 1; i < inputV1_1.length; i++) {
			p1 = p1 + inputV1_1[i]*targetV1_1[i];
		}
		Double tempValue = (double) p1;
		score = tempValue;
		return score;
	}
	
	//20. cosine
	public static Double cosineV21(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
	
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				int temp = inputV1_1[i];
				inputV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] != null) {
				int temp = targetV1_1[i];
				targetV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else{
				targetV1_1[i] = 0;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		int p1 = inputV1_1[0]*targetV1_1[0];
		for(int i = 1; i < inputV1_1.length; i++) {
			p1 = p1 + inputV1_1[i]*targetV1_1[i];
		}
		
		int p2 = inputV1_1[0] * inputV1_1[0];
		for(int i = 1; i < inputV1_1.length; i++) {
			p2 = p2 + inputV1_1[i]*inputV1_1[i];
		}
		
		int p3 = targetV1_1[0] * targetV1_1[0];
		for(int i = 1; i < inputV1_1.length; i++) {
			p3 = p3 + (targetV1_1[i] * targetV1_1[i]);
		}
		
		Double tempValue = (double) p1/(Math.sqrt(p2) * Math.sqrt(p3));
		score = 1 - tempValue;
		return score;
	}
	
	//21. kumar hassebrook
	public static Double kumarHassebrookV21(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				int temp = inputV1_1[i];
				inputV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				int temp = targetV1_1[i];
				targetV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		int p1 = inputV1_1[0]*targetV1_1[0];
		for(int i = 1; i < inputV1_1.length; i++) {
			p1 = p1 + inputV1_1[i]*targetV1_1[i];
		}
		
		int p2 = inputV1_1[0] * inputV1_1[0];
		for(int i = 1; i < inputV1_1.length; i++) {
			p2 = p2 + inputV1_1[i]*inputV1_1[i];
		}
		
		int p3 = targetV1_1[0] * targetV1_1[0];
		for(int i = 1; i < inputV1_1.length; i++) {
			p3 = p3 + (targetV1_1[i] * targetV1_1[i]);
		}
		
		Double tempValue = (double) p1/(p2 + p3 - p1);
		score = tempValue;
		return score;
	}
	
	//22. jaccard
	public static Double jaccardV21(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				int temp = inputV1_1[i];
				inputV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] != null) {
				int temp = targetV1_1[i];
				targetV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else{
				targetV1_1[i] = 0;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		double p1 = (double) (inputV1_1[0] - targetV1_1[0])*(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			p1 = p1 + (inputV1_1[i] - targetV1_1[i])*(inputV1_1[i] - targetV1_1[i]);
		}
		
		double p2 = (double) inputV1_1[0] * inputV1_1[0];
		for(int i = 1; i < inputV1_1.length; i++) {
			p2 = p2 + inputV1_1[i]*inputV1_1[i];
		}
		
		double p3 = (double) targetV1_1[0] * targetV1_1[0];
		for(int i = 1; i < inputV1_1.length; i++) {
			p3 = p3 + (targetV1_1[i] * targetV1_1[i]);
		}
		
		double p4 = (double) inputV1_1[0]*targetV1_1[0];
		for(int i = 1; i < inputV1_1.length; i++) {
			p4 = p4 + (inputV1_1[i] * targetV1_1[i]);
		}
		
		Double tempValue = (double) p1/(p2 + p3 -p4);
		score = tempValue;
		return score;
	}
	
	//23. dice
	public static Double diceV21(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				int temp = inputV1_1[i];
				inputV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				int temp = targetV1_1[i];
				targetV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		int p1 = (inputV1_1[0]-targetV1_1[0])*(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			p1 = p1 + (inputV1_1[i]-targetV1_1[i])*(inputV1_1[i] - targetV1_1[i]);
		}
		
		int p2 = inputV1_1[0] * inputV1_1[0];
		for(int i = 1; i < inputV1_1.length; i++) {
			p2 = p2 + inputV1_1[i]*inputV1_1[i];
		}
		
		int p3 = targetV1_1[0] * targetV1_1[0];
		for(int i = 1; i < inputV1_1.length; i++) {
			p3 = p3 + (targetV1_1[i] * targetV1_1[i]);
		}
		
		Double tempValue = (double) p1/(p2 + p3);
		score = tempValue;
		return score;
	}
	
	//24. fidelity
	public static Double fidelityV21(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				int temp = inputV1_1[i];
				inputV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				int temp = targetV1_1[i];
				targetV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		
		double pembilang = Math.sqrt(inputV1_1[0] *targetV1_1[0]);
		for(int i =1; i< inputV1_1.length; i++) {
			pembilang = pembilang + Math.sqrt(inputV1_1[i] *targetV1_1[i]);
		}
		
		Double tempValue = (double) pembilang;
		score = tempValue;
		return score;
	}
	
	//25. bhattacharyya
	public static Double bhattacharyyaV21(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				int temp = inputV1_1[i];
				inputV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				int temp = targetV1_1[i];
				targetV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		
		double pembilang = Math.sqrt(inputV1_1[0] *targetV1_1[0]);
		for(int i =1; i< inputV1_1.length; i++) {
			pembilang = pembilang + Math.sqrt(inputV1_1[i] *targetV1_1[i]);
		}
		
		Double tempValue = (double) -1 * Math.log(pembilang);
		score = tempValue;
		return score;
	}
	
	//26. hellinger
	public static Double hellingerV21(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				int temp = inputV1_1[i];
				inputV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				int temp = targetV1_1[i];
				targetV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		
		double pembilang = Math.sqrt(inputV1_1[0] *targetV1_1[0]);
		for(int i =1; i< inputV1_1.length; i++) {
			pembilang = pembilang + Math.sqrt(inputV1_1[i] *targetV1_1[i]);
		}
		
		Double tempValue = (double) 2*Math.sqrt(1 - pembilang);
		score = tempValue;
		return score;
	}
	
	//27. matusita
	public static Double matusitaV21(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				int temp = inputV1_1[i];
				inputV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				int temp = targetV1_1[i];
				targetV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		
		double pembilang = Math.sqrt(inputV1_1[0] *targetV1_1[0]);
		for(int i =1; i< inputV1_1.length; i++) {
			pembilang = pembilang + Math.sqrt(inputV1_1[i] *targetV1_1[i]);
		}
		
		Double tempValue = (double) Math.sqrt(2 - 2*pembilang);
		score = tempValue;
		return score;
	}
	
	//28. squared chord
	public static Double squaredChordV21(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				int temp = inputV1_1[i];
				inputV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				int temp = targetV1_1[i];
				targetV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		
		double pembilang = Math.sqrt(inputV1_1[0] *targetV1_1[0]);
		for(int i =1; i< inputV1_1.length; i++) {
			pembilang = pembilang + Math.sqrt(inputV1_1[i] *targetV1_1[i]);
		}
		
		Double tempValue = (double) 2 *pembilang -1;
		score = tempValue;
		return score;
	}

	//29. squared euclidean
	public static Double squaredEuclideanV21(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				int temp = inputV1_1[i];
				inputV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				int temp = targetV1_1[i];
				targetV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		
		double pembilang = (inputV1_1[0] -targetV1_1[0]) * (inputV1_1[0] - targetV1_1[0]);
		for(int i =1; i< inputV1_1.length; i++) {
			pembilang = pembilang + ((inputV1_1[i] -targetV1_1[i]) * (inputV1_1[i] - targetV1_1[i]));
		}
		
		Double tempValue = (double) pembilang;
		score = tempValue;
		return score;
	}
	
	//45. avg
	public static Double avgV21(ArrayList<Integer> graphInput, ArrayList<Integer> graphTarget, ArrayList<Integer> graphTemp) {
		Double score;
		int a = graphInput.size();
		int b = graphTarget.size();
		int c = graphTemp.size();
		int tempSize = c + (a -c) + (b-c);
		
		Integer [] inputV1_1 = new Integer[tempSize];
		Integer [] targetV1_1 = new Integer[tempSize];
		for(int i = 0; i < graphTemp.size(); i++) {
			inputV1_1[i] = graphTemp.get(i);
			targetV1_1[i] = graphTemp.get(i);
		}
		
		for(int i = 0; i < graphInput.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphInput.get(i) == graphTemp.get(j)) {
					graphInput.remove(i);
				}
			}
		}
		
		for(int i = 0; i < graphTarget.size(); i++) {
			for(int j = 0; j < graphTemp.size(); j++) {
				if(graphTarget.get(i) == graphTemp.get(j)) {
					graphTarget.remove(i);
				}
			}
		}
		
		for(int i =graphTemp.size()+graphTarget.size() ; i < inputV1_1.length; i++) {
			int y = i - (graphTemp.size()+graphTarget.size());
			inputV1_1[i] = graphInput.get(y);
		}
		
		for(int i =graphTemp.size() ; i < targetV1_1.length; i++) {
			int y = i - graphTemp.size();
			if(i < (graphTemp.size() + graphTarget.size())) {
				targetV1_1[i] = graphTarget.get(y);
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		for(int i = 0; i < inputV1_1.length; i++) {
			if(inputV1_1[i] == null) {
				inputV1_1[i] = 0;
			}else {
				int temp = inputV1_1[i];
				inputV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}
		}
		for(int i = 0; i < targetV1_1.length; i++) {
			if(targetV1_1[i] == null) {
				int temp = targetV1_1[i];
				targetV1_1[i] = StartPage.gpi.getKeyIndex().get(temp).getKeyGraphValue().size();
			}else{
				targetV1_1[i] = 1;
			}
		}
		printArray(inputV1_1);
		printArray(targetV1_1);
		
		double p1 = Math.abs(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			p1 = p1 + Math.abs(inputV1_1[i] - targetV1_1[i]);
		}
		
		double p2 = Math.abs(inputV1_1[0] - targetV1_1[0]);
		for(int i = 1; i < inputV1_1.length; i++) {
			if(p2 < Math.abs(inputV1_1[i] -targetV1_1[i])) {
				p2 = Math.abs(inputV1_1[i] - targetV1_1[i]);
			}
		}
		
		Double tempValue = (double) (p1 + p2)/2;
		score = tempValue;
		return score;
	}
	
	//print isi array
	public static void printArray(Integer [] arr) {
		System.out.print("[");
		for(int i = 0; i < arr.length; i++) {
			System.out.print(arr[i]+" "+",");
		}
		System.out.print("]");
	}
}
