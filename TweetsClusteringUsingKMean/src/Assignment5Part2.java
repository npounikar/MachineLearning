import java.util.ArrayList;


public class Assignment5Part2 {

	public static void main(String[] args) {
		
		System.out.println("Solution to HomeWork5 -- Machine learning");
		System.out.println("Implementation of tweets-k-means Algorithm : ");
		System.out.println("==========================================");
		
		//variable declaration -------------------------------------------------------------------------------------------------------------------------------------------
		// Parse arguments from command line
		int k = Integer.parseInt(args[0]);
		String initialSeedsFile = args[1];
		String tweetsDataFile = args[2];
		String outputFile = args[3];
		
//		int k = 25;
//		String initialSeedsFile = "C:\\Liquid\\UTD\\FALL2015\\ML\\Assignments\\A5\\Pounikar_nxp142430\\part2\\Dataset\\InitialSeeds.txt";
//		String tweetsDataFile = "C:\\Liquid\\UTD\\FALL2015\\ML\\Assignments\\A5\\Pounikar_nxp142430\\part2\\Dataset\\Tweets.json";
//		String outputFile = "C:\\Liquid\\UTD\\FALL2015\\ML\\Assignments\\A5\\Pounikar_nxp142430\\part2\\Dataset\\out_data.txt";
		FileUtility fu = new FileUtility();
		
		
		//Read and parse the input seed text file ----------------------------------------------------------------------------------------------------------------------------------
		ArrayList<String> seeds = new ArrayList<String>();
		try {
			seeds = fu.parseInputFile(initialSeedsFile);
			//fu.printSeeds(seeds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//Read and parse the input tweets json file ----------------------------------------------------------------------------------------------------------------------------------
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		try {
			tweets = fu.parseInputJSONFile(tweetsDataFile);
			//fu.printTweets(tweets);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		
		//Perform k-means ------------------------------------------------------------------------------------------------------------------------------------------------
		TweetKmeansAlgo tweetkmean = new TweetKmeansAlgo();
		tweetkmean.seeds = seeds;
		tweetkmean.performTweetKmeans(k, tweets);
		//System.out.println(tweetkmean.getJaccardDistance("this is my kingdom", "is this me"));
				
				
		//Write the output file ----------------------------------------------------------------------------------------------------------------------------------
		try {
			fu.writeClusters(k, outputFile, tweetkmean.clusters, tweetkmean.sseValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	} //End Main

} //End Class
