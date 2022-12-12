

enum NodeState{
	empty_since_start, //0
	empty_after_delete, //1
	regular; //2
}

public class OA {
	public HashNode[] buckets;
	public int Size;
	public int Count;
	public int resizecount =0;
	private final float Threashold = 0.7f;
	private final int initialSize = 10;
	
	public OA() 
	{
		this.Size = initialSize;
		this.buckets = new HashNode[initialSize];
		for(int i = 0; i < buckets.length; i++) 
		{
			buckets[i] = HashNode.emptySinceStartNode(i);
		}
	}
	
	public int GetHash(String key) {
		int hashValue = key.hashCode();
		return hashValue % this.Size;
	}
	
	//add
	public void add(String key, String value) throws Exception {
		int hashCode = this.GetHash(key);
		HashNode newNode = new HashNode(key, value, hashCode);
		HashNode bucket = this.buckets[hashCode];
		if (bucket.State == NodeState.empty_since_start || bucket.State == NodeState.empty_after_delete) 
		{
			this.buckets[hashCode] = newNode;
			this.Count++;
			//System.out.println("added at index: " + hashCode);
			//this.buckets[hashCode].State = NodeState.regular;
			//System.out.println(newNode + " bucket state " + bucket.State);
				Resize();
			
		} 
		else 
		{
			int nextkey = hashCode;
			System.out.println("checking: " + nextkey);
			do {
				nextkey = (nextkey + 1) % this.Size;
				bucket = this.buckets[nextkey];
				//System.out.println("checking: " + nextkey);
				} while (bucket.State == NodeState.regular && nextkey != hashCode);
			if (nextkey != hashCode) {
				this.buckets[nextkey] = newNode;
				this.Count++;
				//System.out.println("added at buckets[" + nextkey + "]");
				Resize();
			} else {
				throw new Exception("The hash table is full.");
			}
		}
	}
	
	
	//get
	public HashNode Get(String key) {
		//get hash code
		int hashCode = this.GetHash(key);
		//look up on the array
		HashNode curr = this.buckets[hashCode];
		int nextKey = hashCode;
		if (curr.State == NodeState.empty_since_start) 
		{
			return null;
		} 
		else if (curr.State == NodeState.empty_after_delete || !curr.Key.equals(key)) 
		{
			//we need to check the next opening address
			nextKey = (nextKey + 1) % this.Size;
			curr = this.buckets[nextKey];
			while (curr.State != NodeState.empty_since_start && nextKey != hashCode) 
			{
				if (curr.State == NodeState.empty_after_delete || !curr.Key.equals(key)) 
				{
					nextKey = (nextKey + 1) % this.Size;
					curr = this.buckets[nextKey];
				} else 
				{
					return curr;
				}
			}
			return null;
		} else 
		{
			return curr;
		}
		//check the key and the key on the bucket
		//if not, look at the next position
		//keep on searching until we searched all the positions or
		//until we meet empty-since-start
	}
	
	
	//remove
	public void Remove(String key) {
		int hashCode = this.GetHash(key);
		int nextkey = hashCode;
		HashNode curr = this.buckets[nextkey];
		if (curr.State == NodeState.empty_since_start) {
			return;
		} else if (curr.State == NodeState.regular &&
				curr.Key.equals(key)) {
			this.buckets[nextkey] = HashNode.emptyAfterDeleteNode(nextkey);
			return;
		} else {
			nextkey = (nextkey + 1) % this.Size;
			curr = this.buckets[nextkey];
			while (curr.State == NodeState.empty_after_delete ||
					(curr.State == NodeState.regular && curr.Key
					!= key) || nextkey == hashCode){
				nextkey = (nextkey + 1) % this.Size;
				curr = this.buckets[nextkey];
			}
			if (nextkey == hashCode) {
				return;
			} else if (curr.State == NodeState.empty_since_start) {
				return;
			} else {
				this.buckets[nextkey] = HashNode.emptyAfterDeleteNode(nextkey);
				return;
			}
		}
	}
	
	
	//resize
	public void Resize() throws Exception 
	{
		if (!this.IsChunky()) 
		{
			return;
		}
		HashNode[] originalBuckets = this.buckets;
		this.Count = 0;
		this.Size = this.Size * 2;
		this.buckets = new HashNode[this.Size];
		for(int i = 0; i < this.Size; i++) 
		{
			this.buckets[i] = HashNode.emptySinceStartNode(i);
		}
		for(int i = 0; i < originalBuckets.length; i++) 
		{
			HashNode curr = originalBuckets[i];
			if (curr.State == NodeState.regular) 
			{
				this.add(curr.Key, curr.Value);
			} 
		}
		resizecount++;
		System.out.println("Resized---------------------------------");
		return;
	}
	
	
	//check the capacity
	private boolean IsChunky() {
		return (float)this.Count / (float)this.Size >= this.Threashold;
	}
}