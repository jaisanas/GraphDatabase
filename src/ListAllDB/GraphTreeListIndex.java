package ListAllDB;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

public class GraphTreeListIndex {
	private HashMap<String,GraphData> gTreeIndex;
	
	public GraphTreeListIndex(String namaGraph, GraphData dt) {
		if(this.gTreeIndex == null) {
			this.gTreeIndex = new HashMap<String,GraphData>();
			this.gTreeIndex.put(namaGraph, dt);
		} else {
			this.gTreeIndex.put(namaGraph, dt);
		}
		
	
	}
	
	public GraphTreeListIndex() {
		this.gTreeIndex = new HashMap<String,GraphData>();
		System.out.println(this.gTreeIndex == null);
	}

	public HashMap<String, GraphData> getgTreeIndex() {
		return gTreeIndex;
	}

	public void setgTreeIndex(HashMap<String, GraphData> gTreeIndex) {
		this.gTreeIndex = gTreeIndex;
	}
	
	public void putData(String nama) {
		
	}
	
	public boolean compare(ArrayList<String> a1, ArrayList<String> a2) {
		boolean flag = true;
		if(a1.size() != a2.size()) {
			flag = false;
		}else{
			for(int i = 0; i < a1.size(); i++) {
				if(!a2.contains(a1.get(i))) flag=false;
			}
		}
		
		System.out.println("flag "+flag);
		return flag;
	}
	
	public boolean cekMirip(GraphData data1, GraphData data2) {
		boolean cek = false;
		System.out.println("*****awal cek mirip ");
		for (Entry<String, ArrayList<ArrayList<String>>> ee : data1.getGraph().entrySet()) {
			String key = ee.getKey();
			System.out.println("key data1 "+key);
			System.out.println("value data1 "+ee.getValue());
			System.out.println("emboh ora eroh "+data2.getGraph().get(key));
			if(data2.getGraph().get(key) != null) {
				System.out.println(data2.getGraph().get(key).get(0));
				cek = true;
			}
		}
		
		for (Entry<String, ArrayList<ArrayList<String>>> ee : data2.getGraph().entrySet()) {
			System.out.println("key data2 "+ee.getKey());
			System.out.println("value data 2 "+ee.getValue());
		}
		System.out.println("*****akhir cek mirip ");
		return cek;
	}
	
	public void Print(){
		//print isi tree
		System.out.println("===============================================");
		System.out.println("============Graph Tree Indexing================");
		System.out.println("===============================================");
		HashMap<String,ArrayList<String>> indexTree = StartPage.graphTreeIndex.getIndexTree();
		if(indexTree != null) {
			for (Entry<String, ArrayList<String>> ee : indexTree.entrySet()) {
				String key = ee.getKey();
				ArrayList<String> value = ee.getValue();
				System.out.print(key);
				System.out.print(" ");
				for(int i = 0; i < value.size(); i++) {
					System.out.print(value.get(i));
					if(i < value.size() - 1) {
						System.out.print(",");
					}
				}
				System.out.println();
			}
		}
		
		//print isi tree list index
		System.out.println("====================================================");
		System.out.println("============Graph Tree List Indexing================");
		System.out.println("====================================================");
		if(this.gTreeIndex != null) {
			for (Entry<String,GraphData> ee : this.gTreeIndex.entrySet()) {
				String key = ee.getKey();
				System.out.print(key);
				System.out.println();
				HashMap<String, ArrayList<ArrayList<String>>> isiGraphData = ee.getValue().getGraph();
				for (Entry<String, ArrayList<ArrayList<String>>> eek : isiGraphData.entrySet()) {
					String keyIsi = eek.getKey();
					System.out.print(keyIsi);
					System.out.print(" ");
					ArrayList<ArrayList<String>> valueIsi = eek.getValue();
					for(int i = 0; i < valueIsi.size(); i++) {
						for(int j = 0; j <valueIsi.get(i).size(); j++) {
							System.out.print(valueIsi.get(i).get(j));
							if(j < valueIsi.get(i).size() -1) {
								System.out.print(",");
							}
						}
						if(i < valueIsi.size() -1 ) {
							System.out.print(" ");
						}
					}
					System.out.println();
				}
				System.out.println();
				System.out.println("<==================>");
				System.out.println();
			}
		}
		
	}
	
	public void Print(GraphTreeIndex tree){
		//print isi tree
		System.out.println("===============================================");
		System.out.println("============Graph Tree Indexing================");
		System.out.println("===============================================");
		HashMap<String,ArrayList<String>> indexTree = tree.getIndexTree();
		if(indexTree != null) {
			for (Entry<String, ArrayList<String>> ee : indexTree.entrySet()) {
				String key = ee.getKey();
				ArrayList<String> value = ee.getValue();
				System.out.print(key);
				System.out.print(" ");
				for(int i = 0; i < value.size(); i++) {
					System.out.print(value.get(i));
					if(i < value.size() - 1) {
						System.out.print(",");
					}
				}
				System.out.println();
			}
		}
		
		//print isi tree list index
		System.out.println("====================================================");
		System.out.println("============Graph Tree List Indexing================");
		System.out.println("====================================================");
		if(this.gTreeIndex != null) {
			for (Entry<String,GraphData> ee : this.gTreeIndex.entrySet()) {
				String key = ee.getKey();
				System.out.print(key);
				System.out.println();
				HashMap<String, ArrayList<ArrayList<String>>> isiGraphData = ee.getValue().getGraph();
				for (Entry<String, ArrayList<ArrayList<String>>> eek : isiGraphData.entrySet()) {
					String keyIsi = eek.getKey();
					System.out.print(keyIsi);
					System.out.print(" ");
					ArrayList<ArrayList<String>> valueIsi = eek.getValue();
					for(int i = 0; i < valueIsi.size(); i++) {
						for(int j = 0; j <valueIsi.get(i).size(); j++) {
							System.out.print(valueIsi.get(i).get(j));
							if(j < valueIsi.get(i).size() -1) {
								System.out.print(",");
							}
						}
						if(i < valueIsi.size() -1 ) {
							System.out.print("$");
						}
					}
					System.out.println();
				}
				System.out.println();
				System.out.println("<==================>");
				System.out.println();
			}
		}
		
	}
	
	public void writeToFile(String filePathTree,String filePathList) {
		try{
			File file = new File(filePathTree);
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			//tulis graph tree index
			HashMap<String,ArrayList<String>> indexTree = StartPage.graphTreeIndex.getIndexTree();
			if(indexTree != null) {
				for (Entry<String, ArrayList<String>> ee : indexTree.entrySet()) {
					String key = ee.getKey();
					ArrayList<String> value = ee.getValue();
					bw.write(key);
					bw.write(" ");
					for(int i = 0; i < value.size(); i++) {
						bw.write(value.get(i));
						if(i < value.size() - 1) {
							bw.write(",");
						}
					}
					bw.newLine();
				}
			}
			bw.close();
			//tulis graph tree list index
			File fileList = new File(filePathList);
			FileWriter fwList = new FileWriter(fileList.getAbsoluteFile());
			BufferedWriter bwList = new BufferedWriter(fwList);
			if(this.gTreeIndex != null) {
				for (Entry<String,GraphData> ee : this.gTreeIndex.entrySet()) {
					String key = ee.getKey();
					bwList.write(key);
					bwList.newLine();
					HashMap<String, ArrayList<ArrayList<String>>> isiGraphData = ee.getValue().getGraph();
					for (Entry<String, ArrayList<ArrayList<String>>> eek : isiGraphData.entrySet()) {
						String keyIsi = eek.getKey();
						bwList.write(keyIsi);
						bwList.write(" ");
						ArrayList<ArrayList<String>> valueIsi = eek.getValue();
						for(int i = 0; i < valueIsi.size(); i++) {
							for(int j = 0; j <valueIsi.get(i).size(); j++) {
								bwList.write(valueIsi.get(i).get(j));
								if(j < valueIsi.get(i).size() -1) {
									bwList.write(",");
								}
							}
							if(i < valueIsi.size() -1 ) {
								bwList.write("-");
							}
						}
						bwList.newLine();
					}
				}
			}
			
			bwList.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void writeToFile(GraphTreeIndex tree, String filePathTree,String filePathList) {
		try{
			File file = new File(filePathTree);
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			//tulis graph tree index
			HashMap<String,ArrayList<String>> indexTree = tree.getIndexTree();
			if(indexTree != null) {
				for (Entry<String, ArrayList<String>> ee : indexTree.entrySet()) {
					String key = ee.getKey();
					ArrayList<String> value = ee.getValue();
					bw.write(key);
					bw.write(" ");
					for(int i = 0; i < value.size(); i++) {
						bw.write(value.get(i));
						if(i < value.size() - 1) {
							bw.write(",");
						}
					}
					bw.newLine();
				}
			}
			bw.close();
			//tulis graph tree list index
			File fileList = new File(filePathList);
			FileWriter fwList = new FileWriter(fileList.getAbsoluteFile());
			BufferedWriter bwList = new BufferedWriter(fwList);
			if(this.gTreeIndex != null) {
				for (Entry<String,GraphData> ee : this.gTreeIndex.entrySet()) {
					String key = ee.getKey();
					bwList.write(key);
					bwList.newLine();
					HashMap<String, ArrayList<ArrayList<String>>> isiGraphData = ee.getValue().getGraph();
					for (Entry<String, ArrayList<ArrayList<String>>> eek : isiGraphData.entrySet()) {
						String keyIsi = eek.getKey();
						bwList.write(keyIsi);
						bwList.write(" ");
						ArrayList<ArrayList<String>> valueIsi = eek.getValue();
						for(int i = 0; i < valueIsi.size(); i++) {
							for(int j = 0; j <valueIsi.get(i).size(); j++) {
								bwList.write(valueIsi.get(i).get(j));
								if(j < valueIsi.get(i).size() -1) {
									bwList.write(",");
								}
							}
							if(i < valueIsi.size() -1 ) {
								bwList.write("$");
							}
						}
						bwList.newLine();
					}
				}
			}
			
			bwList.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public GraphTreeIndex union(String namaGraph, GraphData dt, GraphTreeIndex tree) {
		GraphTreeIndex tempTree = null;
		System.out.println(this.gTreeIndex == null);
		if(this.gTreeIndex.size() == 0) {
			System.out.println("*******awal this.gTreeIndex.size()== 0*******");
			this.gTreeIndex = new HashMap<>();
			this.gTreeIndex.put("root_0", dt);
			this.gTreeIndex.put("root_1", dt);
			this.gTreeIndex.put(namaGraph, dt);
			tree = new GraphTreeIndex();
			tree.init(namaGraph);
			System.out.println(tree.getIndexTree().size());
			tempTree = tree;
			for (Entry<String, ArrayList<String>> ee : tree.getIndexTree().entrySet()) {
				System.out.println(ee.getKey());
				System.out.println(ee.getValue());
			}
			Print(tree);
			System.out.println("********akhir this.gTreeIndex.size()== 0*******");
		}else {
			System.out.println("********awal this.gTreeIndex.size() != 0*******");
			GraphData tempRoot0 = this.gTreeIndex.get("root_0");

			System.out.println("kok bisa masuk sini ");
			//Union dt dengan root0
			System.out.println("awal print root_1x");
			GraphData tempRoot1x = new GraphData();
			tempRoot1x = this.gTreeIndex.get("root_1");
			for (Entry<String, ArrayList<ArrayList<String>>> ee : tempRoot1x.getGraph().entrySet()) {
				System.out.println(ee.getKey());
				System.out.println(ee.getValue());
			}
			System.out.println("akhir print root_1x");
			Print(tree);
			for (Entry<String, ArrayList<ArrayList<String>>> ee : dt.getGraph().entrySet()) {
				  String key = ee.getKey();
				  ArrayList<String> value = ee.getValue().get(0);
				  ArrayList<ArrayList<String>> tempSisi = null;
				  tempSisi = new ArrayList<>();
				  tempSisi.add(value);
				  System.out.println();
				  if(this.gTreeIndex.get("root_0").getGraph().get(key) == null) {
					 //tempRoot0.put(key, tempSisi);
					  this.gTreeIndex.get("root_0").put(key, tempSisi);
				  }else {
					  boolean flag = false;
					  System.out.println(this.gTreeIndex.get("root_0").getGraph().get(key));
					  for(int bla = 0; bla < this.gTreeIndex.get("root_0").getGraph().get(key).size(); bla++) {
						  if(compare(this.gTreeIndex.get("root_0").getGraph().get(key).get(bla),value)) flag = true; 
					  }
					  
					  if(!flag) {
						  this.gTreeIndex.get("root_0").getGraph().get(key).add(value);
					  }
				  }
			}
			/*ArrayList<ArrayList<String>> gila = new ArrayList<>();
			ArrayList<String> dumb = new ArrayList<>();
			dumb.add("hahaha");
			gila.add(dumb);
			tempRoot0.put("ad",gila);
			HashMap<String, ArrayList<ArrayList<String>>> p = new HashMap<>();
			p.put("ad", gila);
			this.gTreeIndex.put("root_0", new GraphData(p));*/
			Print(tree);
			tempTree = tree;
			System.out.println("awal print root_1");
			GraphData tempRoot1 = new GraphData();
			tempRoot1 = this.gTreeIndex.get("root_1");
			for (Entry<String, ArrayList<ArrayList<String>>> ee : tempRoot1.getGraph().entrySet()) {
				System.out.println(ee.getKey());
				System.out.println(ee.getValue());
			}
			System.out.println("akhir print root_1");
			ArrayList<String> child = tree.getIndexTree().get("root_0");
			String [] tempChild = new String[child.size()];
			tempChild = child.toArray(tempChild);
			Arrays.sort(tempChild);
			for(int i = 0; i < tempChild.length; i++) {
				System.out.println("tempChild ke "+tempChild[i]);
			}
			
			System.out.println("kok mirip sih "+cekMirip(this.gTreeIndex.get("root_1"), dt));
			
			ArrayList<Integer> indexMiripVertex = new ArrayList<>();
			for(int i = 0; i < child.size(); i++) {
				if(cekMirip(this.gTreeIndex.get(child.get(i)),dt)) {
					System.out.println("index mirip "+i);
					indexMiripVertex.add(i);
				}
			}
			System.out.println("ukuran indexMiripVertex "+indexMiripVertex.size());
			if(indexMiripVertex.size() == 0) {
				System.out.println("tidak ada yang mirip");
				String gName = tempChild[tempChild.length -1];
				String [] tokens = gName.split("_");
				int foo = Integer.parseInt(tokens[1]);
				System.out.println("gName "+gName);
				int indexRoot = foo+1;
				String gNameRoot = tokens[0]+"_"+indexRoot;
				ArrayList<String> childRoot = new ArrayList<>();
				childRoot.add(namaGraph);
				child.add(gNameRoot);
				tree.getIndexTree().put("root_0", child);
				tree.getIndexTree().put(gNameRoot, childRoot);
				this.gTreeIndex.put(gNameRoot, dt);
				this.gTreeIndex.put(namaGraph, dt);
			}else {
				for(int i = 0; i < indexMiripVertex.size(); i++) {
					int tempIndex = indexMiripVertex.get(i);
					String namaRoot = child.get(tempIndex);
					GraphData tempX = this.gTreeIndex.get(namaRoot);
					for (Entry<String, ArrayList<ArrayList<String>>> ee : dt.getGraph().entrySet()) {
						   String keyX = ee.getKey();
						   ArrayList<ArrayList<String>> valueX = ee.getValue();
						   ArrayList<String> tempEdgesDtX = dt.getGraph().get(keyX).get(0);
						   if(tempX.getGraph().get(keyX) == null) {
							   tempX.getGraph().put(keyX, valueX);
						   } else {
							   boolean isExist = false;
							   ArrayList<ArrayList<String>> isiArrayTempX = tempX.getGraph().get(keyX);
							   for(int iter = 0; iter < isiArrayTempX.size(); iter++) {
								   if(compare(isiArrayTempX.get(iter), tempEdgesDtX)) isExist = true;
							   }
							   if(!isExist) {
								   tempX.getGraph().get(keyX).add(tempEdgesDtX);
							   }
						 }
					}
					
					this.gTreeIndex.put(namaRoot,tempX);
					this.gTreeIndex.put(namaGraph, dt);
					tree.getIndexTree().get(namaRoot).add(namaGraph);
				}
				
			}
			
			tempTree = tree;
			System.out.println("*********akhir this.gTreeIndex.size()!= 0********"); 
		}	
		
		return tempTree;
	}
	
	public void PrintResult(HashMap<String,Double> hm) {
		System.out.println("====================================================");
		System.out.println("============Graph BTree Index result================");
		System.out.println("====================================================");
		if(hm == null) {
			System.out.println("kosong");
		}else {
			for (Entry<String,Double> ee : hm.entrySet()) {
				System.out.println(ee.getKey()+" "+ee.getValue());
			}
		}
	}
	
	public HashMap<String,Double> Search(GraphData query,Double thold) {
		HashMap<String,Double> listCandidate = new HashMap<>();
		GraphData tempRoot0 = this.gTreeIndex.get("root_0");
		if(!isNeverhappened(query, tempRoot0)) {
			listCandidate = null;
		}else {
				if(thold < cariScore(query, tempRoot0)) {
					listCandidate = null;
				} else {
					ArrayList<String> anakRoot0 = StartPage.graphTreeIndex.getIndexTree().get("root_0");
					for(int i = 0; i < anakRoot0.size(); i++) {
						if(thold >= cariScore(query,this.gTreeIndex.get(anakRoot0.get(i)))) {
							ArrayList<String> anakRootI = StartPage.graphTreeIndex.getIndexTree().get(anakRoot0.get(i));
							for(int j = 0; j < anakRootI.size(); j++) {
							     if (thold >= cariScore(query, this.gTreeIndex.get(anakRootI.get(j))))
									listCandidate.put(anakRootI.get(j), cariScore(query, this.gTreeIndex.get(anakRootI.get(j))));
						    }
						}
					}
			  }
		}
		
		return listCandidate;
	}
	
	public Double cariScore(GraphData query,GraphData target) {
		Double scoreDistance = 0.00;
		HashMap<String, ArrayList<ArrayList<String>>> queryMap = query.getGraph();
		HashMap<String,ArrayList<ArrayList<String>>> targetMap = target.getGraph();
		if(queryMap.size() >= targetMap.size()) {
			for (Entry<String, ArrayList<ArrayList<String>>> ee : queryMap.entrySet()) {
				String key = ee.getKey();
				ArrayList<ArrayList<String>> value = ee.getValue();
				if(targetMap.get(key)==null) {
					scoreDistance++;
				}else {
					if(!isContain(targetMap.get(key), value.get(0))) {
						scoreDistance = scoreDistance + 0.50;
					} 
				}
			}
		}else {
			for (Entry<String, ArrayList<ArrayList<String>>> ee : targetMap.entrySet()) {
				String key = ee.getKey();
				
				if(queryMap.get(key)==null) {
					scoreDistance++;
				}else {
					ArrayList<ArrayList<String>> value = queryMap.get(key);
					if(!isContain(targetMap.get(key), value.get(0))) {
						scoreDistance = scoreDistance + 0.50;
					} 
				}
			}
		}
		
		return scoreDistance;
	}
	
	public HashMap<String,Double> searchByModification(GraphData query, Double thold) {
		HashMap<String,Double> finaList = new HashMap<>();
		//cari candidate anak
		ArrayList<String> childList = StartPage.graphTreeIndex.getIndexTree().get("root_0");
		ArrayList<String> temp = new ArrayList<>();
		temp.add(childList.get(0));
		int j = 0;
		for(int i = 0; i < childList.size(); i++) {
			if(i != j) {
				if(cariScoreModification(query, this.gTreeIndex.get(childList.get(i))) == cariScoreModification(query, this.gTreeIndex.get(temp.get(j)))) {
					temp.add(childList.get(i));
					j++;
				} else if (cariScoreModification(query, this.gTreeIndex.get(childList.get(i))) > cariScoreModification(query, this.gTreeIndex.get(temp.get(j)))) {
					temp = null;
					temp = new ArrayList<>();
					j = 0;
					temp.add(childList.get(i));
				}
			}
		}
		
		GraphData target = this.gTreeIndex.get("root_0");
		if(cariScoreModification(query, target) >= 0.50) {
			for(int i = 0; i < temp.size(); i++) {
				ArrayList<String> tempX = new ArrayList<>();
				tempX = StartPage.graphTreeIndex.getIndexTree().get(temp.get(i));
				for(int k = 0; k < tempX.size(); k++) {
					GraphData targetX = this.gTreeIndex.get(tempX.get(k));
					if(cariScore(query, targetX) <= thold) {
						finaList.put(tempX.get(j), cariScore(query, targetX));
					}
				}
			}
		}else {
			finaList = null;
		}
		
		return finaList;
	}
	
	public boolean isNeverhappened(GraphData query, GraphData target) {
		boolean flag = false;
		HashMap<String, ArrayList<ArrayList<String>>> queryMap = query.getGraph();
		HashMap<String,ArrayList<ArrayList<String>>> targetMap = target.getGraph();
		for (Entry<String, ArrayList<ArrayList<String>>> ee : queryMap.entrySet()) {
			String key = ee.getKey();
			ArrayList<ArrayList<String>> value = ee.getValue();
			if(targetMap.get(key)!=null) {
				flag = true;
			}
		}
		
		return flag;

	}
	
	public boolean isContain(ArrayList<ArrayList<String>> a1, ArrayList<String> a2) {
		boolean flag = false;
		for(int i = 0; i < a1.size(); i++) {
			if(compare(a1.get(i), a2)) {
				flag = true;
			}
		}
		
		return flag;
	}
	
	
	
	public Double cariScoreModification(GraphData query,GraphData target) {
		Double doubleScore = 0.00;
		HashMap<String, ArrayList<ArrayList<String>>> queryMap = query.getGraph();
		HashMap<String,ArrayList<ArrayList<String>>> targetMap = target.getGraph();
		for (Entry<String, ArrayList<ArrayList<String>>> ee : queryMap.entrySet()) {
			String key = ee.getKey();
			ArrayList<ArrayList<String>> value = ee.getValue();
			if(targetMap.get(key) != null) {
				if(isContain(targetMap.get(key), value.get(0))) {
					doubleScore = doubleScore++;
				}else {
					doubleScore = doubleScore + 0.50;
				  }
				}
			}
		return doubleScore;
	}
	
	public ArrayList<String> getParentCandidate(String tempRoot0, GraphData query) {
		ArrayList<String> temp = new ArrayList<>();
		ArrayList<String> childList = StartPage.graphTreeIndex.getIndexTree().get("root_0");
		if(childList.size() != 0) temp.add(childList.get(0));
		int j = 0;
		for(int i = 0; i < childList.size(); i++) {
			if(i != j) {
				if(cariScoreModification(query, this.gTreeIndex.get(childList.get(i))) == cariScoreModification(query, this.gTreeIndex.get(temp.get(j)))) {
					temp.add(childList.get(i));
					j++;
				} else if (cariScoreModification(query, this.gTreeIndex.get(childList.get(i))) > cariScoreModification(query, this.gTreeIndex.get(temp.get(j)))) {
					temp = null;
					temp = new ArrayList<>();
					j = 0;
					temp.add(childList.get(i));
				}
			}
		}
		
		return temp;
	}	
	public void union(String namaGraph, GraphData dt ) {
		System.out.println(this.gTreeIndex == null);
		if(this.gTreeIndex.size() == 0) {
			System.out.println("*******awal this.gTreeIndex.size()== 0*******");
			this.gTreeIndex = new HashMap<>();
			this.gTreeIndex.put("root_0", dt);
			this.gTreeIndex.put("root_1", dt);
			this.gTreeIndex.put(namaGraph, dt);
			StartPage.graphTreeIndex.init(namaGraph);
			System.out.println(StartPage.graphTreeIndex.getIndexTree().size());
			for (Entry<String, ArrayList<String>> ee : StartPage.graphTreeIndex.getIndexTree().entrySet()) {
				System.out.println(ee.getKey());
				System.out.println(ee.getValue());
			}
			Print(StartPage.graphTreeIndex);
			System.out.println("********akhir this.gTreeIndex.size()== 0*******");
		}else {
			System.out.println("********awal this.gTreeIndex.size() != 0*******");
			GraphData tempRoot0 = this.gTreeIndex.get("root_0");

			System.out.println("kok bisa masuk sini ");
			//Union dt dengan root0
			System.out.println("awal print root_1x");
			GraphData tempRoot1x = new GraphData();
			tempRoot1x = this.gTreeIndex.get("root_1");
			for (Entry<String, ArrayList<ArrayList<String>>> ee : tempRoot1x.getGraph().entrySet()) {
				System.out.println(ee.getKey());
				System.out.println(ee.getValue());
			}
			System.out.println("akhir print root_1x");
			Print(StartPage.graphTreeIndex);
			for (Entry<String, ArrayList<ArrayList<String>>> ee : dt.getGraph().entrySet()) {
				  String key = ee.getKey();
				  ArrayList<String> value = ee.getValue().get(0);
				  ArrayList<ArrayList<String>> tempSisi = null;
				  tempSisi = new ArrayList<>();
				  tempSisi.add(value);
				  System.out.println();
				  if(this.gTreeIndex.get("root_0").getGraph().get(key) == null) {
					 //tempRoot0.put(key, tempSisi);
					  this.gTreeIndex.get("root_0").put(key, tempSisi);
				  }else {
					  boolean flag = false;
					  System.out.println(this.gTreeIndex.get("root_0").getGraph().get(key));
					  System.out.println("ukuran nya em "+this.gTreeIndex.get("root_0").getGraph().get(key).size());
					  for(int bla = 0; bla < this.gTreeIndex.get("root_0").getGraph().get(key).size(); bla++) {
						  for(int bro = 0; bro < this.gTreeIndex.get("root_0").getGraph().get(key).get(bla).size(); bro++) {
							  System.out.println("aduh biyung "+this.gTreeIndex.get("root_0").getGraph().get(key).get(bla).get(bro));
						  }
						  System.out.println("bla ke "+bla+" "+compare(this.gTreeIndex.get("root_0").getGraph().get(key).get(bla),value));
						  if(compare(this.gTreeIndex.get("root_0").getGraph().get(key).get(bla),value)) flag = true; 
					  }
					  
					  if(!flag) {
						  this.gTreeIndex.get("root_0").getGraph().get(key).add(value);
					  }
				  }
			}
			
			/*ArrayList<ArrayList<String>> gila = new ArrayList<>();
			ArrayList<String> dumb = new ArrayList<>();
			dumb.add("hahaha");
			gila.add(dumb);
			tempRoot0.put("ad",gila);
			HashMap<String, ArrayList<ArrayList<String>>> p = new HashMap<>();
			p.put("ad", gila);
			this.gTreeIndex.put("root_0", new GraphData(p));*/
			Print(StartPage.graphTreeIndex);
			System.out.println("awal print root_1");
			GraphData tempRoot1 = new GraphData();
			tempRoot1 = this.gTreeIndex.get("root_1");
			for (Entry<String, ArrayList<ArrayList<String>>> ee : tempRoot1.getGraph().entrySet()) {
				System.out.println(ee.getKey());
				System.out.println(ee.getValue());
			}
			System.out.println("akhir print root_1");
			ArrayList<String> child = StartPage.graphTreeIndex.getIndexTree().get("root_0");
			String [] tempChild = new String[child.size()];
			tempChild = child.toArray(tempChild);
			Arrays.sort(tempChild);
			for(int i = 0; i < tempChild.length; i++) {
				System.out.println("tempChild ke "+tempChild[i]);
			}
			
			System.out.println("kok mirip sih "+cekMirip(this.gTreeIndex.get("root_1"), dt));
			
			ArrayList<Integer> indexMiripVertex = new ArrayList<>();
			for(int i = 0; i < child.size(); i++) {
				if(cekMirip(this.gTreeIndex.get(child.get(i)),dt)) {
					System.out.println("index mirip "+i);
					indexMiripVertex.add(i);
				}
			}
			System.out.println("ukuran indexMiripVertex "+indexMiripVertex.size());
			if(indexMiripVertex.size() == 0) {
				System.out.println("tidak ada yang mirip");
				String gName = tempChild[tempChild.length -1];
				String [] tokens = gName.split("_");
				int foo = Integer.parseInt(tokens[1]);
				System.out.println("gName "+gName);
				int indexRoot = foo+1;
				String gNameRoot = tokens[0]+"_"+indexRoot;
				ArrayList<String> childRoot = new ArrayList<>();
				childRoot.add(namaGraph);
				child.add(gNameRoot);
				StartPage.graphTreeIndex.getIndexTree().put("root_0", child);
				StartPage.graphTreeIndex.getIndexTree().put(gNameRoot, childRoot);
				this.gTreeIndex.put(gNameRoot, dt);
				this.gTreeIndex.put(namaGraph, dt);
			}else {
				for(int i = 0; i < indexMiripVertex.size(); i++) {
					int tempIndex = indexMiripVertex.get(i);
					String namaRoot = child.get(tempIndex);
					GraphData tempX = this.gTreeIndex.get(namaRoot);
					for (Entry<String, ArrayList<ArrayList<String>>> ee : dt.getGraph().entrySet()) {
						   String keyX = ee.getKey();
						   ArrayList<ArrayList<String>> valueX = ee.getValue();
						   ArrayList<String> tempEdgesDtX = dt.getGraph().get(keyX).get(0);
						   if(tempX.getGraph().get(keyX) == null) {
							   tempX.getGraph().put(keyX, valueX);
						   } else {
							   boolean isExist = false;
							   ArrayList<ArrayList<String>> isiArrayTempX = tempX.getGraph().get(keyX);
							   for(int iter = 0; iter < isiArrayTempX.size(); iter++) {
								   if(compare(isiArrayTempX.get(iter), tempEdgesDtX)) isExist = true;
							   }
							   if(!isExist) {
								   tempX.getGraph().get(keyX).add(tempEdgesDtX);
							   }
						 }
					}
					
					this.gTreeIndex.put(namaRoot,tempX);
					this.gTreeIndex.put(namaGraph, dt);
					StartPage.graphTreeIndex.getIndexTree().get(namaRoot).add(namaGraph);
				}
				
			}
			
			System.out.println("*********akhir this.gTreeIndex.size()!= 0********"); 
		}	
		
	}
}
