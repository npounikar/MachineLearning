import java.nio.channels.SeekableByteChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;


public class TweetKmeansAlgo {

	
	//variable declaration
	double sseValue;
	ArrayList<String> seeds = new ArrayList<String>();
	HashMap<String, String> globalTweets = new HashMap<String, String>();
	HashMap<String, ArrayList<String>> clusters;
	
	
	
// Perform Tweet k mean algorithm :STARTS ----------------------------------------------------------------------------------------------------------------------------------
	
	public void performTweetKmeans(int k, ArrayList<Tweet> tweets) {
		
		boolean stable =false;
		System.out.println("Performing Tweet k -mean Algo :");
		System.out.println("--------------------------------");
		
		//assigning to global tweet
		assignTweetsToGlobal(tweets);
		
		//start finding the distance
		//for k value
		for(int x = 0; x < k; x++) {
			
			//Reached stable condition - no need for further iterations
			if(stable)
				break;
			
			//init clusters
			clusters = new HashMap<String, ArrayList<String>>();
			for(int i = 0; i < seeds.size(); i++) {	
				ArrayList<String> tweetIds = new ArrayList<String>();
				clusters.put(seeds.get(i), tweetIds);
			}
			
			double minDist = Double.MAX_VALUE;
			String minCentId = "";
			
			System.out.println("------------Iteration : "+(x+1)+"---------------");
			for(int i = 0; i < tweets.size(); i++) {
				
				String currTweetId = tweets.get(i).id;
				String currTweetText = tweets.get(i).text;
				
				//checking for min seed for current tweet
				for(int j = 0; j < seeds.size(); j++) {
				
					String centId = seeds.get(j);
					String centText = globalTweets.get(centId);
				
					double distance = getJaccardDistance(centText, currTweetText);
					
					if(distance < minDist) {	
						minDist = distance;
						minCentId = centId;
					}
				}
				
				//update Hashtable cluster - put it in the Arraylist and then hashmap
				clusters.get(minCentId).add(currTweetId);
				

				//intialize the local min vars for next point in the iteration
				minDist = Double.MAX_VALUE;
				minCentId = "";
				
			} // end of every tweets
			
			//printing clusters
			//printClusters(clusters);
			
			//For Next iteration - calculate the average value of x and y for each cluster
			stable = calculateAverageSeeds(clusters);
			
			
		} // end of iterations - x
		sseValue = getSSEValue(clusters);
		System.out.println("SSE : "+sseValue);
	}

// Perform Tweet k mean algorithm :ENDS ----------------------------------------------------------------------------------------------------------------------------------
	
	
	
	
	
// Assign to Global Tweets :STARTS -----------------------------------------------------------------------------------------------------------------------------------------
	
	private void assignTweetsToGlobal(ArrayList<Tweet> tweets) {
		for(int i = 0; i < tweets.size(); i++) {
			globalTweets.put(tweets.get(i).id, tweets.get(i).text);
		}
	}
// Assign to Global Tweets :ENDS -------------------------------------------------------------------------------------------------------------------------------------------
	
	
	

// Calculate Jaccard Distance :STARTS --------------------------------------------------------------------------------------------------------------------------------------
	
		public double getJaccardDistance(String cText, String tText) {
			
			double dist = 0;
			HashSet<String> c = new HashSet<String>();
			HashSet<String> t = new HashSet<String>();
			
			String[] ct = cText.split(" ");
			for(int i = 0; i < ct.length; i++) 
				c.add(ct[i]);
			
			String[] tt = tText.split(" ");
			for(int i = 0; i < tt.length; i++) 
				t.add(tt[i]);
			
			String[] splitCText = c.toString().split(" ");
			String[] splitTText = t.toString().split(" ");
			
			int union = splitCText.length + splitTText.length;
			int intersection = 0;
			
			for(int i = 0; i < splitCText.length; i++) {
				for(int j = 0; j < splitTText.length; j++) {					
					if(splitCText[i].equals(splitTText[j])) {
						intersection++;
						break;
					}
				}
			}
			union = union - intersection;
			dist = (double)(union - intersection)/union;
			
			return dist;
		}
		
// Calculate Jaccard Distance :ENDS ----------------------------------------------------------------------------------------------------------------------------------------
	
		
		
		
		
// Print Clusters : STARTS-----------------------------------------------------------------------------------------------------------------------
				
	private void printClusters(HashMap<String, ArrayList<String>> clusters) {
		
		Iterator it = clusters.entrySet().iterator();
		while (it.hasNext()) {
		    Map.Entry key = (Map.Entry)it.next();
			System.out.println("Centroid Id : "+key.getKey()+" => ");
			
			for(int j = 0; j < clusters.get(key.getKey()).size(); j++) {
				System.out.print(clusters.get(key.getKey()).get(j)+", ");
			}
			System.out.println("");System.out.println("");
		}
	}
				
// Print Clusters : ENDS-----------------------------------------------------------------------------------------------------------------------		
	
	
	
	
// Average Centroid : STARTS-----------------------------------------------------------------------------------------------------------------------

	private boolean calculateAverageSeeds(HashMap<String, ArrayList<String>> clusters) {
	
		String[] tmpSeeds = new String[seeds.size()];
		double minSum;
		boolean stable = false;
		String minSeedId = "";
		double sum;
		int count = 0;
		
		Iterator it = clusters.entrySet().iterator();
		while (it.hasNext()) {
		    Map.Entry key = (Map.Entry)it.next();
		    
		    minSum = Double.MAX_VALUE;
			for(int i = 0; i < clusters.get(key.getKey()).size(); i++) {
				sum = 0;
				for(int j = 0; j < clusters.get(key.getKey()).size(); j++) {
					
//					System.out.println("i : "+clusters.get(key.getKey()).get(i));
//					System.out.println("i : "+globalTweets.get(clusters.get(key.getKey()).get(i)));
//					System.out.println("j : "+clusters.get(key.getKey()).get(j));
//					System.out.println("j : "+globalTweets.get(clusters.get(key.getKey()).get(j)));
					
					double distance = getJaccardDistance(globalTweets.get(clusters.get(key.getKey()).get(i)), globalTweets.get(clusters.get(key.getKey()).get(j)));
					sum = sum + distance;
				}
				
				if(sum < minSum) {
					minSum = sum;
					minSeedId = clusters.get(key.getKey()).get(i);
				}
			}
			
			//put new seeds values in the temp seeds array
			if(count < tmpSeeds.length) {
				tmpSeeds[count] = minSeedId;
				count++;
			}
			
			//init the minimum counters
			minSeedId = "";
			
			//System.out.println("");
		}
		
		
		
		//Check if all the entries in the seeds are same or not for all the instance of the seed in the old arraylist to the new array
		int seedsSame = 0;
		for(int i = 0 ; i < seeds.size(); i++){
			for(int j = 0 ; j < tmpSeeds.length; j++){
				if(tmpSeeds[j].equals(seeds.get(i))) {
					seedsSame++;
				}
			}
		}
	
		
		//Check if stable or not : If stable - update the seeds arraylist
		if(seedsSame == 25)
			stable = true;
		else {
			stable = false;
			for(int j = 0 ; j < tmpSeeds.length; j++){
				seeds.set(j, tmpSeeds[j]);
			}
		}
		
	
		return stable;
	}

// Average Centroid : ENDS-----------------------------------------------------------------------------------------------------------------------

			

	
// SSE : STARTS-----------------------------------------------------------------------------------------------------------------------

		private double getSSEValue(HashMap<String, ArrayList<String>> clust) {
						
			double sse = 0, distance = 0;
			int len = 0;
						
			//For each centroid
			Iterator it = clust.entrySet().iterator();
			while (it.hasNext()) {
			    Map.Entry key = (Map.Entry)it.next();
			    
				for(int j = 0; j < clust.get(key.getKey()).size(); j++) {
				
					distance = getJaccardDistance(globalTweets.get(key.getKey()), globalTweets.get(clust.get(key.getKey()).get(j)));
					distance = distance * distance;
					sse = sse + distance;
					
				}
				
			}
			
			return sse;
		}

// SSE : ENDS-----------------------------------------------------------------------------------------------------------------------

				
	
	
} // END of class TweetKMeanAlgo
