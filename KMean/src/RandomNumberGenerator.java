import java.util.*;


public class RandomNumberGenerator {

	
	public ArrayList<Point> getRandomPointsId(int k, ArrayList<Point> points) {
		
		Point p;
		ArrayList<Point> randomPoints = new ArrayList<Point>();
		
		Random random = new Random();
		int[] cent = new int[k];

		//getting random Ids------------------------------------------------------------------------------------------------------------------------------------
	    Set<Integer> set = new HashSet<Integer>(k);
	    while(set.size()< k) 
	    	while (set.add(random.nextInt(points.size())) != true);
	    
	    int i = 0;
	    for (Integer val : set)
	    	cent[i++] = val;
	    
	    //getting the value of points using random ids----------------------------------------------------------------------------------------------------------
	    for(i = 0; i < cent.length; i++) {
			System.out.println(cent[i] +" : "+points.get(cent[i]-1).id +" = "+points.get(cent[i]-1).x+", "+points.get(cent[i]-1).y);
			//p = new Point(points.get(cent[i]-1).id, points.get(cent[i]-1).x, points.get(cent[i]-1).y);
			p = new Point(i, points.get(cent[i]-1).x, points.get(cent[i]-1).y);
			randomPoints.add(p);	
		}
	    
		return randomPoints;
	}
	
}
