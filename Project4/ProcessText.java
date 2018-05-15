import java.io.File;
import java.util.Scanner;

public class ProcessText
{

	public static void main (String[] args) {
		
		if (args.length < 1) {
			System.err.println("Usage: java ProcessText file1 [file2 ...]");
			System.exit(1);
		}
		
		TextStatistics text;
		
		for (int i=0; i<args.length; i++) {
			
			File file = new File(args[i]);
			if(file.exists() && file.isFile()) {
				try {
					text = new TextStatistics(args[i]);
				}
			
				catch(FileNotFoundException e){
					System.err.println("File at argument " + i + " does not exist. Continuing to run.");
				}
	
			}
			
		}
	}
}
