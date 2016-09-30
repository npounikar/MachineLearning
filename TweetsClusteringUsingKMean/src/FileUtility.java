import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;



public class FileUtility {

	
// ==Read and parse the input file : Starts ====================================================================================================================================
	
	public ArrayList<String> parseInputFile(String filepath)  throws Exception {
				
		ArrayList<String> seeds = new ArrayList<String>();
		BufferedReader read = null;
		String line = "";

		//Reading the contents of the file from next line.
		read = new BufferedReader(new FileReader(filepath));
		while((line = read.readLine()) != null) {
			
			//parse every value according to its attribute and add to the arraylist
			if(line.contains(",")) 
				seeds.add(line.substring(0, line.length()-1));
			
			else if(!line.contains(",")) 
				seeds.add(line);
		
		}
		read.close();
		
		return seeds;
	}
			
			
//=Read and parse the input file : ENDS ====================================================================================================================================
	


	
	
	
//=Read and parse the input TWEET file : STARTS ============================================================================================================================

	public ArrayList<Tweet> parseInputJSONFile(String tweetsDataFile) throws FileNotFoundException, IOException  {
		
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		BufferedReader br;
		String line = "";

		br = new BufferedReader(new FileReader(tweetsDataFile));
		while((line = br.readLine())!=null) {
			line = line.replaceAll("[-+.,#:@]","");
			line = line.replace("\\\"", "");
			String[] splittedArray = line.split("\"");
			String text = splittedArray[3];
			String id = splittedArray[18];
			id = id.replace(" ", "");
			tweets.add(new Tweet(id, text));
		}
		br.close();
		return tweets;
	}
	
//=Read and parse the input TWEET file : ENDS ==============================================================================================================================	
	
	
	
	
	
	
	
//=TEST : Print  Input seed file : STARTS =================================================================================================================================
	
	public void printSeeds(ArrayList<String> attr) {
	
		// test print
		System.out.println("===============SEEDS===============");
		for(int i=0; i<attr.size(); i++) 
			System.out.println(attr.get(i));
		
		System.out.println("===================================");
	}
			
//=TEST : Print  Input seed file : ENDS ===================================================================================================================================

	
	
	
//=TEST : Print  Input tweet file : STARTS =================================================================================================================================	
	
	public void printTweets(ArrayList<Tweet> tweets) {
		
		// test print
		System.out.println("===============TWEETS==============");
		for(int i=0; i<tweets.size(); i++) {
			System.out.print(tweets.get(i).id+", "+tweets.get(i).text);
			System.out.println("");
		}
		System.out.println("========================================================================================================================================================================");
		
	}

	
//=TEST : Print  Input tweet file : ENDS ===================================================================================================================================


	
	

//=Write the cluster in output file : STARTS =================================================================================================================================

	public void writeClusters(int k, String outputFile, HashMap<String, ArrayList<String>> clusters, double sse) throws FileNotFoundException {
		
		StringBuilder sb = new StringBuilder();
		sb.append(System.getProperty("line.separator"));
		sb.append("FOR k : "+k);
		sb.append(System.getProperty("line.separator"));
		sb.append("------------------     ----------------------------------------");
		sb.append(System.getProperty("line.separator"));
		sb.append("<cluster-id>            <List of tweets ids separated by comma>");
		sb.append(System.getProperty("line.separator"));
		sb.append("------------------     ----------------------------------------");
		
		Iterator it = clusters.entrySet().iterator();
		while (it.hasNext()) {
		    Map.Entry key = (Map.Entry)it.next();
		    sb.append(System.getProperty("line.separator"));
			sb.append(key.getKey()+" => \t ");
			
			for(int j = 0; j < clusters.get(key.getKey()).size(); j++) {
				sb.append(clusters.get(key.getKey()).get(j)+", ");
			}
			sb.append(System.getProperty("line.separator"));
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
	
//=Write the cluster in output file : STARTS =================================================================================================================================
	
	
	

} // End FileUtility
