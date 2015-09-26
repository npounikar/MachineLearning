import java.util.ArrayList;


public class AccuracyCalculator {

		double matchCount;
	
	// == Print Accuracy :: START ==================================================================================================================================================

		public double getAccuracy(ArrayList<ArrayList<Attribute>> parsedTestAttributes, Node treeRootNode2) {
			
			double accuracy = 0.0;

			// Check if result is correct or not
			for (ArrayList<Attribute> arrayList : parsedTestAttributes) {
				if (isClassValueMatching(arrayList, treeRootNode2))
					accuracy++;
			}
			this.matchCount = accuracy;
			accuracy =  ((accuracy * 100 )/ parsedTestAttributes.size());
			return accuracy;
		}
		
		
		
		
		private boolean isClassValueMatching(ArrayList<Attribute> dataToMatch, Node head) {
			
			
			// just compare the values in dataToMatch to decision tree, if reached to the leaf
			if (head.children == null) {
				 if (dataToMatch.get(dataToMatch.size() - 1).value == head.valueClassAttribute)
					  return true;
				 else
					 return false;
			} else {
				
				// Computing the value of attribute
				int valAttr = -1;
				for (Attribute attribute : dataToMatch)
					if (attribute.name.equals(head.splitRuleAttribute.name)) {
						valAttr = attribute.value;
						break;
					}

				// Call recursively the child node 
				return isClassValueMatching(dataToMatch, head.children[valAttr]);
			}
		}
		
//		== Print Accuracy :: ENDS ==================================================================================================================================================	


		
}
