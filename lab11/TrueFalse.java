

public class TrueFalse {
	
	
	public static void main (String[] args) {
		int arrayLength = 0;
		
		try {
			arrayLength = Integer.parseInt(args[0]);
		}
		catch(NumberFormatException nfe){
			System.err.println("Error: must be integer value input");
			System.exit(1);;
		}
		boolean[] flags = new boolean[arrayLength];
		
		for(int i = 0; i < arrayLength; i++) {
			if(i % 2 == 0) {
				flags[i] = true;
			}
			else {
				flags[i] = false;
			}
			i++;
		}
		
		String str = "Length: " + arrayLength + "\nContents: [";
		
		for(int i = 0; i < arrayLength; i++) {
			if(i == 0)
				str += flags[i];
			else
				str += ", " + flags[i];
		}
		
		str += "]\n";
		
		System.out.println(str);
	}

	boolean[] copyArray(boolean[] original) {
		boolean[] copy = original;
		return copy;
	}
	
}
