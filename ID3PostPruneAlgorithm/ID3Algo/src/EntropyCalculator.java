import java.util.ArrayList;


public class EntropyCalculator {
	
// == Computes Entropy :: START =====================================================================================================================================================
	
		public double calculateEntropy(ArrayList<ArrayList<Attribute>> trainData) {
			
			int base = 2;
			double[] valCount = new double[2];
			
			for (ArrayList<Attribute> attr : trainData) {
				if(attr.get(attr.size() - 1).value == 1)
					valCount[valCount.length-1]++;
				else 
					valCount[valCount.length-valCount.length]++;
			}
			
			//Calculating the Entropy 
			double entropy = 0;
			for (int j = 0; j < valCount.length; j++) {
				if ((int)valCount[j] != 0) {
					double n = (Math.log(valCount[j])/Math.log(base));
					double d = (Math.log(trainData.size())/Math.log(base)); 
					entropy -= ((valCount[j] * (n - d)) / trainData.size());
				}
			}
			return entropy;
		}

// == Computes Entropy :: ENDS =====================================================================================================================================================
		
		
		
// == Computes Information Gain :: START=============================================================================================================================================	
			
			public double calculateInformationGain(double trainingCurrentEntropy, ArrayList<Integer> subsetSizes, ArrayList<Double> subEntropies, double n) {
				
				// Compute gain as root entropy
				double infoGain = trainingCurrentEntropy;
				for (int j = 0; j < subEntropies.size(); j++) {
					infoGain -= (subsetSizes.get(j) / n) * subEntropies.get(j);
//					System.out.println("subsetSizes.get(j) : "+subsetSizes.get(j));
//					System.out.println("n :"+n);
//					System.out.println("subsetSizes.get(j)/n : "+(double)subsetSizes.get(j)/n);
//					System.out.println("subSetVarianceImpurities.get(j) : "+subSetVarianceImpurities.get(j));
//					System.out.println("(subsetSizes.get(j) / n) *subSetVarianceImpurities.get(j) : "+((double)subsetSizes.get(j) / n) *subSetVarianceImpurities.get(j));
				}
				
				return infoGain;
			}

// == Computes Information Gain :: ENDS==============================================================================================================================================
			

}
