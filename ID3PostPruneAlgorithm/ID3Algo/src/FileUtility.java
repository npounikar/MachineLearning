import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


public class FileUtility {
	
	
	// read and parse csv file
	public ArrayList<ArrayList<Attribute>> parseCSVFile(String filepath) {
		
		ArrayList<ArrayList<Attribute>> attributes = new ArrayList<>();
		BufferedReader read = null;
		String line = "";
		
		
		try {
			
			// reading first line for headers or attribute names.
			read = new BufferedReader(new FileReader(filepath)); 
			String[] header = read.readLine().split(",");
			
			//Now, read the contents of the file from next line.
			while((line = read.readLine()) != null) {
				
				String[] values = line.split(",");
				
				//parse every value according to its attribute and assign it and add to the list
				ArrayList<Attribute> rows = new ArrayList<>();
				for(int i=0; i<values.length; i++) {
					rows.add(new Attribute(header[i], Integer.parseInt(values[i])));
				}
				attributes.add(rows);
			}
			
			
		} catch (Exception e) {
			
		} finally {
			if(read != null) {
				try {
					read.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return attributes;
	}
	
	
	//Print read CSV file
	public void printSets(ArrayList<ArrayList<Attribute>> attr) {
		// test print
		Iterator<ArrayList<Attribute>> itr = attr.iterator();
		while(itr.hasNext()) {
			ArrayList<Attribute> element  = (ArrayList<Attribute>) itr.next();

			for(int i=0; i<element.size(); i++) {
				System.out.print(element.get(i).name +" = "+element.get(i).value +"   ");
			}
			System.out.println(" ");
		}
		System.out.println("===============================================================================================================================================================================================");
	}
	
	

}
