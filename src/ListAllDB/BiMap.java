package ListAllDB;

public class BiMap {
	private String edgeLabel;
	private String sourceLabel;
	private String targetLabel;
	public BiMap(String x,String y, String z) {
		this.edgeLabel = x;
		this.sourceLabel = y;
		this.targetLabel = z;
	}
	
	public BiMap() {
		
	}
	
	public void destroy(){
		this.edgeLabel = null;
		this.sourceLabel = null;
		this.edgeLabel = null;
	}
	
	public void setValue(String edge, String source, String target) {
		this.edgeLabel = edge;
		this.sourceLabel = source;
		this.targetLabel = target;
	}
	public String getEdgeLabel() {
		return edgeLabel;
	}
	public void setEdgeLabel(String edgeLabel) {
		this.edgeLabel = edgeLabel;
	}
	public String getSourceLabel() {
		return sourceLabel;
	}
	public void setSourceLabel(String sourceLabel) {
		this.sourceLabel = sourceLabel;
	}
	public String getTargetLabel() {
		return targetLabel;
	}
	public void setTargetLabel(String targetLabel) {
		this.targetLabel = targetLabel;
	}
	
	public String getEdgeEquals(String source) {
		if(sourceLabel == source) {
			return edgeLabel;
		}else {
			return null;
		}
	}
	
	public String getTargetLabel(String source) {
		if(sourceLabel == source) {
			return targetLabel;
		}else {
			return null;
		}
	}
	
	public boolean equalsBM(BiMap bm) {
		return ((this.edgeLabel.equals(bm.edgeLabel)) 
				&& (this.sourceLabel.equals(bm.sourceLabel)) && (this.targetLabel.equals(bm.targetLabel)));
	}
	
	public boolean equalsSource(BiMap bm) {
		return (this.sourceLabel.equals(bm.getSourceLabel()));
	}
	
	public String getString(){
		return this.edgeLabel+"|"+this.sourceLabel+"|"+this.targetLabel;
	}
}
