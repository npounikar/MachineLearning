import java.util.ArrayList;
import java.util.HashMap;


public class KmeansAlgo {

	
	// variable declarations 
	double sseValue = 0;
	ArrayList<Point> randomCentroids = new ArrayList<Point>();
	HashMap<Integer, ArrayList<Point>> clusters = new HashMap<Integer, ArrayList<Point>>();
			
			
	
// Perform K-mean algo : STARTS ----------------------------------------------------------------------------------------------------------------------------------------------
	
	public void performKmeans(int k, ArrayList<Point> points) {
		
		double distance;
		
		// Random Centroids -------------------------------------------------------------------------------------------------------------------------------------
		RandomNumberGenerator rnd = new RandomNumberGenerator();
		randomCentroids = rnd.getRandomPointsId(k, points);

		
		// Update the distance array of every point to every centroid points ------------------------------------------------------------------------------------
		//find the minimum distance of a point from every centroid and put it in the cluster of that particular centroid ----------------------------------------
		for(int x = 0 ; x < 25; x++) {
			
			//init clusters
			initClusters(clusters, k);
			
			double minDist = Double.MAX_VALUE;
			int minCentId = 0, i, j;
			
			System.out.println("------------Iteration : "+(x+1)+"---------------");
			for( i = 0; i < points.size(); i++) {

				//System.out.print("POINT : "+points.get(i).id +" => "+points.get(i).x+", "+points.get(i).y);
				//System.out.println("");

				for( j = 0; j < randomCentroids.size(); j++) {

					//System.out.print("CENTROID : "+randomCentroids.get(j).id+" => "+randomCentroids.get(j).x+", "+randomCentroids.get(j).y);
					//System.out.print("CENTROID : "+j+" => "+randomCentroids.get(j).x+", "+randomCentroids.get(j).y);
					//System.out.println();

					distance = getEucledianDistance(points.get(i).x, points.get(i).y, randomCentroids.get(j).x, randomCentroids.get(j).y);
					//System.out.println(j+", "+distance+" for "+randomCentroids.get(j).id);
					//System.out.println(j+", "+distance+" for "+j);

					if(distance < minDist) {	
						minDist = distance;
						minCentId = j;
					}

				} // End of every centroids
				
				//System.out.println(points.get(i).id +" closest from "+minCentId +" with "+minDist);
				//System.out.println("-----------------------------------------");

				//put it in the Arraylist and then hashmap
				clusters.get(minCentId).add(points.get(i));


				//intialize the local min vars for next point in the iteration
				minDist = Double.MAX_VALUE;
				minCentId = 0;

			} // End of every points	

			//print hashmap clusters that has the infor about centroid and clusters
			printClusters(clusters);
			
			//For Next iteration - calculate the average value of x and y for each cluster
			boolean stable = calculateAverageCentroid(clusters);
			
			//Reached stable condition - no need for further iterations
			if(stable)
				break;
			
		}// End of iterations - x	
		sseValue = getSSEValue(randomCentroids, clusters);
	}

// Perform K-mean algo : ENDS ----------------------------------------------------------------------------------------------------------------------------------------------
	
	
	
	
	
	
	
// Get Eucledian distance of a point from centroid : STARTS-----------------------------------------------------------------------------------------------------------------
	

	private double getEucledianDistance(double x1, double y1, double x2, double y2) {
		
		double xDiff = x2 - x1;
		double yDiff = y2 - y1;
		double xDiffSqr = xDiff * xDiff;
		double yDiffSqr = yDiff * yDiff;
		double total = xDiffSqr + yDiffSqr;
		double distance = Math.sqrt(total);
		return distance;
		
	}
	
// Get Eucledian distance of a point from centroid : ENDS------------------------------------------------------------------------------------------------------------------
		
	
	
	
	
	
// Init Clusters : STARTS-----------------------------------------------------------------------------------------------------------------------
	
		private void initClusters(HashMap<Integer, ArrayList<Point>> clusters, int k) {
				
			//Initialization of Cluster datastructure
			for(int i = 0; i < k; i++) {	
				ArrayList<Point> clusterPoints = new ArrayList<Point>();
				clusters.put(i, clusterPoints);
			}
			
		}
			
// Init Clusters : ENDS-----------------------------------------------------------------------------------------------------------------------	
	
	

	
		
	
// Print Clusters : STARTS-----------------------------------------------------------------------------------------------------------------------
		
		private void printClusters(HashMap<Integer, ArrayList<Point>> clusters) {
			
			for ( int key : clusters.keySet() )  {
				System.out.println("Key : "+key);
				for(int j = 0; j < clusters.get(key).size(); j++) {
					System.out.print(clusters.get(key).get(j).id+", ");
				}
				System.out.println();
			}
			
		}
		
// Print Clusters : ENDS-----------------------------------------------------------------------------------------------------------------------		
		
		
		

		
		
// Average Centroid : STARTS-----------------------------------------------------------------------------------------------------------------------

		private boolean calculateAverageCentroid(HashMap<Integer, ArrayList<Point>> clusters) {
			
			boolean stable = false;
			double sumX, sumY, avgX, avgY;
			int len = 0;
			
			//For each centroid
			for ( int key : clusters.keySet() )  {
				
				len = clusters.get(key).size();
				sumX = 0;
				sumY = 0;
				avgX = 0;
				avgY = 0;
				
				for(int j = 0; j < len; j++) {
					sumX = sumX + clusters.get(key).get(j).x;
					sumY = sumY + clusters.get(key).get(j).y;
				}
				
				 avgX = (sumX)/len;
				 avgY = (sumY)/len;
	
				//Stablizing condition
				if(randomCentroids.get(key).x == avgX && randomCentroids.get(key).y == avgY && !stable) {
					stable = true;
				} else {
					stable = false;
				}
				
				System.out.println("len of Cluster : "+len);
				System.out.println("-- OLD --");
				System.out.println("X : "+randomCentroids.get(key).x);
				System.out.println("Y : "+randomCentroids.get(key).y);
				System.out.println("");
				
				randomCentroids.get(key).x = avgX;
				randomCentroids.get(key).y = avgY;
				
				System.out.println("--NEW--");				
				System.out.println("X : "+randomCentroids.get(key).x);
				System.out.println("Y : "+randomCentroids.get(key).y);
				System.out.println("=======================");
				
			}
			return stable;
		}

// Average Centroid : ENDS-----------------------------------------------------------------------------------------------------------------------

		
		
		
// SSE : STARTS-----------------------------------------------------------------------------------------------------------------------

	private double getSSEValue(ArrayList<Point> centroids, HashMap<Integer, ArrayList<Point>> clust) {
					
		double sse = 0, distance, centX = 0, centY = 0;
		int len = 0;
					
		//For each centroid
		for ( int key : clust.keySet() )  {
			len = clust.get(key).size();
			centX = centroids.get(key).x;
			centY = centroids.get(key).y;
			distance = 0;
			
			for(int j = 0; j < len; j++) {
					distance = getEucledianDistance(centX, centY, clusters.get(key).get(j).x, clusters.get(key).get(j).y);
					distance = distance * distance;
					sse = sse + distance;
			}				
		}
		
		return sse;
	}

// SSE : ENDS-----------------------------------------------------------------------------------------------------------------------


}
