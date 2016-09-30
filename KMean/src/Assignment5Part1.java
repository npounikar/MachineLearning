import java.util.ArrayList;


public class Assignment5Part1 {

	public static void main(String[] args) {
		
		System.out.println("Solution to HomeWork5 -- Machine learning");
		System.out.println("Implementation of k-means Algorithm : ");
		System.out.println("==========================================");
		
		//variable declaration -------------------------------------------------------------------------------------------------------------------------------------------
		// Parse arguments from command line
		int k = Integer.parseInt(args[0]);
		String inputFile = args[1];
		String outputFile = args[2];
		//String inputFile = "C:\\Liquid\\UTD\\FALL2015\\ML\\Assignments\\A5\\test_data.txt";
		//String outputFile = "C:\\Liquid\\UTD\\FALL2015\\ML\\Assignments\\A5\\out_data.txt";
		//int k = 12;
		
		
		//Read and parse the input file ----------------------------------------------------------------------------------------------------------------------------------
		ArrayList<Point> points = new ArrayList<Point>();
		FileUtility fu = new FileUtility();
		try {
			points = fu.parseInputFile(inputFile);
			//fu.printSets(points);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//Perform k-means ------------------------------------------------------------------------------------------------------------------------------------------------
		KmeansAlgo kmean = new KmeansAlgo();
		kmean.performKmeans(k, points);
		
		

		//Write the output file ----------------------------------------------------------------------------------------------------------------------------------
		try {
			fu.writeClusters(outputFile, kmean.clusters, kmean.sseValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
