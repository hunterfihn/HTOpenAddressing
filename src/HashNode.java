public class HashNode {
	public String Key;
	public String Value;
	public int Code;
	public NodeState State = NodeState.empty_since_start; //state: 0 empty since start, 1 empty after delete, 2 regular 
	
	public HashNode(String key, String value, int code) {
		this.Key = key;
		this.Value = value;
		this.Code = code; 
		this.State = NodeState.regular; 
	}
	
	public static HashNode emptySinceStartNode(int code) {
		HashNode value = new HashNode("", "", code);
		value.State = NodeState.empty_since_start;
		return value;
	}
	
	public static HashNode emptyAfterDeleteNode(int code) {
		HashNode value = new HashNode("", "", code);
		value.State = NodeState.empty_after_delete; 
		return value;
	}
}

