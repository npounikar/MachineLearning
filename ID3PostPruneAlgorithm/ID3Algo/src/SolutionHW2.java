import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;


public class SolutionHW2 {

	static ArrayList<ArrayList<Attribute>> parsedTrainingAttributes = new ArrayList<>();
	static ArrayList<ArrayList<Attribute>> parsedValidationAttributes = new ArrayList<>();
	static ArrayList<ArrayList<Attribute>> parsedTestAttributes = new ArrayList<>();
	
	
	public static void main(String args[]) {
		
		System.out.println("Solution to HomeWork2 -- Machine learning");
		System.out.println("Implementation of ID3 Algorithm : ");
		System.out.println("==========================================");
		
		
		// Parse arguments
		int L = Integer.parseInt(args[0]);
	    int K = Integer.parseInt(args[1]);
	    String trainingset = args[2];
	    String validationset = args[3];
	    String testset = args[4];
	    boolean toPrint = args[5].equalsIgnoreCase("yes");
		
		
//------------------------------------------------------------READING CSV FILE ------------------------------------------------------------------------------//	
		
		FileUtility fu1 = new FileUtility();
		parsedTrainingAttributes = fu1.parseCSVFile(trainingset);
		parsedValidationAttributes = fu1.parseCSVFile(validationset);
		parsedTestAttributes = fu1.parseCSVFile(testset);
		
		System.out.println("Read Successfull..........");
		System.out.println();
		
		
//------------------------------------------------------------PRINTING CSV FILE ------------------------------------------------------------------------------//				
		// Test print
		fu1.printSets(parsedTrainingAttributes);
		
		
//------------------------------------ Build tree using information gain and print its accuracy over test set ------------------------------------------------//
		DecisionTree dtree = new DecisionTree();
		dtree.buildTree(parsedTrainingAttributes, new Node());
		
		
//------------------------------------------------------------PRINTING-------------------------------------------------------------------------------------------------------//			
		
		System.out.println("----------------------------------------------------------------------------------");
		AccuracyCalculator ac1 = new AccuracyCalculator();
		
		// Printing the decision tree and accuracy before pruning 
		if (toPrint) {
			
			System.out.println("Decision tree before pruning: ");
			System.out.println("");
			System.out.println(dtree);
			System.out.println("Accuracy of decision tree before pruning : "+ ac1.getAccuracy(parsedTestAttributes, dtree.treeRootNode)+"%");
			System.out.println("Total Matched classes : "+(int)ac1.matchCount);
			System.out.println("----------------------------------------------------------------------------------");
			System.out.println("");
		
			// Perform Post pruning the tree
			try {
				dtree.performPostPruning(L, K, parsedValidationAttributes);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		
			System.out.println("Decision tree after pruning: ");
			System.out.println("");
			System.out.println(dtree);
			System.out.println("Accuracy of decision tree after pruning: "+ ac1.getAccuracy(parsedTestAttributes, dtree.treeRootNode)+"%");
			System.out.println("Total Matched classes : "+(int)ac1.matchCount);
			System.out.println("----------------------------------------------------------------------------------");
			
		} else {
	
			System.out.println("Accuracy of decision tree before pruning : "+ ac1.getAccuracy(parsedTestAttributes, dtree.treeRootNode)+"%");
			System.out.println("Total Matched classes : "+(int)ac1.matchCount);
			System.out.println("");
		
			// Perform Post pruning the tree
			try {
				dtree.performPostPruning(L, K, parsedValidationAttributes);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			
			System.out.println("Accuracy of decision tree after pruning: "+ ac1.getAccuracy(parsedTestAttributes, dtree.treeRootNode)+"%");
			System.out.println("Total Matched classes : "+(int)ac1.matchCount);
			System.out.println("----------------------------------------------------------------------------------");
			
		}
	}
	
}
