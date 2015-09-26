

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class FileUtility {



	public void writeFile(int round) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("output.txt", "UTF-8");
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		writer.println("Round : "+round);
		writer.println("");
		writer.println("Strategy     No. of Games    Ending Balance ");
		writer.close();
		
		
	}
	
	
	
	public void writeToFile(int strategy, int currentBalance, int i) {
		
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("output.txt");
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} 
		
		writer.println(strategy+"             "+i+"               $"+currentBalance);
		writer.close();
		
	}
	
	

}
