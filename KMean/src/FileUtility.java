import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;


public class FileUtility {

// ==Read and parse the input file : Starts ====================================================================================================================================
		public ArrayList<Point> parseInputFile(String filepath) throws Exception {
			
			ArrayList<Point> points = new ArrayList<Point>();
			BufferedReader read = null;
			String line = "";
			
		
				// reading headers or attribute names which is present in first line.
				read = new BufferedReader(new FileReader(filepath)); 
				String[] header = read.readLine().split("\t");
				
				//Reading the contents of the file from next line.
				while((line = read.readLine()) != null) {
					String[] values = line.split("\t");

					//parse every value according to its attribute and add to the arraylist
					points.add(new Point(Integer.parseInt(values[0]), Double.parseDouble(values[1]), Double.parseDouble(values[2])));
				}
				read.close();
				
			return points;
		}
		
		
//=Read and parse the input file : ENDS ====================================================================================================================================	
		
		
		
		
		
//=Writes output file : ENDS ===============================================================================================================================================
		public void writeClusters(String outputFile, HashMap<Integer, ArrayList<Point>> clusters, double sse) throws FileNotFoundException {
			
			StringBuilder sb = new StringBuilder();
			sb.append(System.getProperty("line.separator"));
			sb.append("FOR k : ");
			sb.append(clusters.size());
			sb.append(System.getProperty("line.separator"));
			sb.append("------------   ----------------------------------------");
			sb.append(System.getProperty("line.separator"));
			sb.append("<cluster-id>   <List of points ids separated by comma>");
			sb.append(System.getProperty("line.separator"));
			sb.append("------------   ----------------------------------------");
			
			for ( int key : clusters.keySet() )  {
				sb.append(System.getProperty("line.separator"));
				sb.append(key+"\t\t");
				for(int j = 0; j < clusters.get(key).size(); j++) {
					sb.append(clusters.get(key).get(j).id+", ");
				}
			}
			
			sb.append(System.getProperty("line.separator"));
			sb.append(System.getProperty("line.separator"));
			sb.append("SSE  =  "+sse);
			sb.append(System.getProperty("line.separator"));
			sb.append("===============================================================================================================================================================");
			
			String str = sb.toString();
			System.out.println(str);
			//Write in an outputfile
			PrintWriter writer = new PrintWriter(outputFile);
			writer.println(str);
			writer.close();
		}

//=Writes output file : ENDS ==============================================================================================================================================
		
		

		
//=TEST : Print  Input file : STARTS =======================================================================================================================================
		
		public void printSets(ArrayList<Point> attr) {
			
			// test print
			for(int i=0; i<attr.size(); i++) {
					System.out.print(attr.get(i).id +" : "+attr.get(i).x +", "+attr.get(i).y);
					System.out.println(" ");
			}
			System.out.println("===================================");
		}
		
//=TEST : Print  Input file : ENDS =========================================================================================================================================

		
}
