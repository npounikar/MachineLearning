import java.util.ArrayList;
import java.util.Random;


public class DecisionTree {

	private int nodeNumber;
	public Node treeRootNode;

	EntropyCalculator calc = new EntropyCalculator();
	AccuracyCalculator ac = new AccuracyCalculator();
	
	
	
// == Creating Decision Tree :: START ==============================================================================================================================================
	
	public void buildTree(ArrayList<ArrayList<Attribute>> parsedTrainingAttributes, Node rootNode) {
		
		int index = -1;
		double maxInfoGain = 0.0;
		Attribute newSplitAttr = null;
		ArrayList<ArrayList<Attribute>> leftSet = new ArrayList<>();
		ArrayList<ArrayList<Attribute>> rightSet = new ArrayList<>();
		
		double trainingCurrentEntropy = calc.calculateEntropy(parsedTrainingAttributes);
		rootNode.entropy = trainingCurrentEntropy;
		
		
		
		// Split data in class of 0 as left tree and 1 as right tree
		for (int i = 0; i < parsedTrainingAttributes.get(0).size() - 1; i++) {
		
			ArrayList<ArrayList<Attribute>> leftSub = new ArrayList<>();
			ArrayList<ArrayList<Attribute>> rightSub = new ArrayList<>();
			
			
			// Calculate subtree split on this attribute // 0 to left subset  and 1 to right subset
			for (int j = 0; j < parsedTrainingAttributes.size(); j++) {
				
				ArrayList<Attribute> l = new ArrayList<>();
				l.addAll(parsedTrainingAttributes.get(j));
				System.out.print(parsedTrainingAttributes.get(j).get(i).name +" = "+parsedTrainingAttributes.get(j).get(i).value+ "  ");
				
				if (parsedTrainingAttributes.get(j).get(i).value == 1)
					rightSub.add(l);
				else
					leftSub.add(l);
			}
			System.out.println("  ");
			
			
            // Printing Left and right Subsets
			System.out.println("Left subset : "+ leftSub.size());
			for (int j = 0; j < leftSub.size(); j++) {
				System.out.print(leftSub.get(j).get(i).name +" = "+leftSub.get(j).get(i).value+ "  ");
			}
			System.out.println("  ");
			
			System.out.println("Right subset : "+ rightSub.size());
			for (int j = 0; j < rightSub.size(); j++) {
				System.out.print(rightSub.get(j).get(i).name +" = "+rightSub.get(j).get(i).value+ "  ");
			}
			System.out.println("  ");
			
			
			
			// Calculate sub entropies
			ArrayList<Double> entropiesOfSubset = new ArrayList<>();
			System.out.println("Left Entropy : "+calc.calculateEntropy(leftSub));
			entropiesOfSubset.add(calc.calculateEntropy(leftSub));
			System.out.println("Right Entropy : "+calc.calculateEntropy(rightSub));
			entropiesOfSubset.add(calc.calculateEntropy(rightSub));
		
			
			// Calculate size
			ArrayList<Integer> sizeOfSubset = new ArrayList<>();
			System.out.println("Left Subset Size : "+leftSub.size());
			sizeOfSubset.add(leftSub.size());
			System.out.println("Right Subset Size : "+rightSub.size());
			sizeOfSubset.add(rightSub.size());
						
			
			//Calculate the information Gain
			System.out.println("Entropy of root Node : "+rootNode.entropy);
			double informationGain = calc.calculateInformationGain(rootNode.entropy, sizeOfSubset, entropiesOfSubset, parsedTrainingAttributes.size());
			System.out.println("InfoGain : "+informationGain);
			System.out.println("MaxInfoGain: "+maxInfoGain);
			
			if ((int) (informationGain * 100000000) > (int) (maxInfoGain * 100000000)) {
				
				maxInfoGain = informationGain;
				newSplitAttr = parsedTrainingAttributes.get(0).get(i);
				System.out.println("Name : "+newSplitAttr.name+"   Val :"+newSplitAttr.value);
				
				index = i;
				System.out.println("Index : "+i);
				
				@SuppressWarnings("unchecked")
				ArrayList<ArrayList<Attribute>> copyLeftSub = (ArrayList<ArrayList<Attribute>>) leftSub.clone();
				leftSet = copyLeftSub;
				
				@SuppressWarnings("unchecked")
				ArrayList<ArrayList<Attribute>> copyRightSub = (ArrayList<ArrayList<Attribute>>) rightSub.clone();
				rightSet = copyRightSub;
			}
			System.out.println("");
		}
			
			// Index keeps record of the attribute on which we will be splitting. so, if index is set, we got the attribute on which split will happen
			if (index > -1) {
				
				// Remove the attribute used from further consideration
				System.out.println(index);
				System.out.println("Left :"+leftSet.size());
				System.out.println("right :"+rightSet.size());
				
				for (ArrayList<Attribute> attrLeft : leftSet)
					attrLeft.remove(index);
					
				for (ArrayList<Attribute> attrRight : rightSet) 
					attrRight.remove(index);
					
				System.out.println("LeftAfter :"+leftSet.get(0).size());
				System.out.println("rightAfter :"+rightSet.get(0).size());

				// Set the nodes for the recursive call to the subtree
				Node leftChild = new Node();
				Node rightChild = new Node();
				leftChild.dataSubtree = leftSet;
				rightChild.dataSubtree = rightSet;

				rootNode.children = new Node[2];
				rootNode.children[0] = leftChild;
				rootNode.children[1] = rightChild;
				rootNode.splitRuleAttribute = newSplitAttr;
				rootNode.pruneIndexToNode = ++this.nodeNumber;

				// Recursively call subtrees
				buildTree(leftSet, leftChild);
				buildTree(rightSet, rightChild);
				
			} else {
				// Else, no more splitting is possible for this subtree
				rootNode.valueClassAttribute = parsedTrainingAttributes.get(0).get(parsedTrainingAttributes.get(0).size() - 1).value;
				return;
			}
		
			this.treeRootNode = rootNode;
	}

// == Creating Decision Tree :: ENDS ==============================================================================================================================================	
	
	
	
	


// == PRUNING : STARTS ====================================================================================================================================================================

	public void performPostPruning(int L, int K, ArrayList<ArrayList<Attribute>> dataForPrune) throws CloneNotSupportedException {
		
		// Assign the tree as best
		Node bestProbableNode = (Node) treeRootNode.clone();
		
		for (int i = 0; i < L; i++) {
			// Copy root to rootcopy
			Node rootCopy = (Node) treeRootNode.clone();

			// Generate a random number between 1 and K
			Random random = new Random(nodeNumber);
			int M = random.nextInt(K);
			for (int j = 0; j < M; j++) {
				int r1 = random.nextInt();
				searchRemoveNode(rootCopy, r1);
			}

			// Set rootcopy to best if its accuracy is better
			if (ac.getAccuracy(dataForPrune, bestProbableNode) < ac.getAccuracy(dataForPrune, rootCopy))
				bestProbableNode = (Node) rootCopy.clone();
		}

		// Set root as the best tree formed
		treeRootNode = (Node) bestProbableNode.clone();
	}

	
	private void searchRemoveNode(Node root, int index) {
		
		// If we found the root with the index
		if (root.pruneIndexToNode == index) {
			
			// Decide which is the majority class, and make it a leaf node
			if (root.children[0].dataSubtree.size() > root.children[1].dataSubtree.size()) {
				
				int[] valCount = new int[2];
				for (ArrayList<Attribute> attribute : root.children[0].dataSubtree)
					valCount[attribute.get(attribute.size() - 1).value]++;

				if( valCount[0] > valCount[1] )
					root.children[0].valueClassAttribute = 0;
				else 
					root.children[0].valueClassAttribute = 1;
				
				root.children[0].children = null;
				root.children[0].pruneIndexToNode = -1;
				
			} else {
				int[] count = new int[2];
				for (ArrayList<Attribute> attribute : root.children[1].dataSubtree)
					count[attribute.get(attribute.size() - 1).value]++;

				if (count[0] > count[1])
					root.children[1].valueClassAttribute = 0;
				else
					root.children[1].valueClassAttribute = 1;
				
				root.children[1].children = null;
				root.children[1].pruneIndexToNode = -1;
			}
			
		} else if (root.children != null) {
			
			searchRemoveNode(root.children[0], index);
			searchRemoveNode(root.children[1], index);
		}
	}
	
// PRUNING : ENDS ====================================================================================================================================================================






	// == Print Decision Tree :: START ================================================================================================================================================
	
	public String printTree(Node root, int level) {
		
		// Initialize string builder and append tabs according to the level of the node
		StringBuilder sb1 = new StringBuilder();

		// for all subtrees
		for (int i = 0; i < root.children.length; i++) {
			for (int j = 0; j < level; j++)
				sb1.append("| ");

			// Print subtree if present, or just class attribute value as the leaf node
			sb1.append(root.splitRuleAttribute.name + " = " + i + " :");
			if (root.children[i].children != null)
				sb1.append("\n" + printTree(root.children[i], level + 1));
			else
				sb1.append(" " + root.children[i].valueClassAttribute + "\n");
		}

		return sb1.toString();
	}
	
	
	public String toString() {
		return printTree(this.treeRootNode, 0);
	}
	
// == Print Decision Tree :: ENDS ==============================================================================================================================================




}
