import java.util.*;

public class Node {
	
	
	//Value of Class attribute at this node, if this is a leaf node
	public int valueClassAttribute;
	public int pruneIndexToNode;
	public double entropy;
	
	public Node[] children;
	public ArrayList<ArrayList<Attribute>> dataSubtree;
	
	public Attribute splitRuleAttribute;
	
	
	
	public Node() {
		
		pruneIndexToNode = -1;
		valueClassAttribute = 0;
		entropy = 0.0;

		children = null;
		dataSubtree = new ArrayList<>();
		
		splitRuleAttribute = new Attribute("", 0);
	
	}
	

	@Override
	public Object clone() throws CloneNotSupportedException {
		
		Node cloned = new Node();
		cloned.entropy = this.entropy;
		cloned.valueClassAttribute = this.valueClassAttribute;
		cloned.splitRuleAttribute = new Attribute(this.splitRuleAttribute.name, this.splitRuleAttribute.value);
		cloned.pruneIndexToNode = this.pruneIndexToNode;

		
		// Copying the data 
		for (ArrayList<Attribute> attributes : this.dataSubtree) {
			ArrayList<Attribute> clonedAttributes = new ArrayList<>();
			
			for (Attribute attribute : attributes) {
				clonedAttributes.add(new Attribute(attribute.name, attribute.value));
			}
			
			cloned.dataSubtree.add(clonedAttributes);
		}

		
		
		// Recursively cloning the children for subtree
		if (this.children != null) {
			cloned.children = new Node[2];
			for (int i = 0; i < this.children.length; i++)
				cloned.children[i] = (Node) this.children[i].clone();
		}

		// Return the cloned node
		return cloned;
	}
	
}
