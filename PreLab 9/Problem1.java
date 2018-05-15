
public class Problem1 
{

	public static void main (String[] args) {
		String forward = "keepitayoself";

		String reverse = reverseString(forward);
	}
	public String reverseString(String forward)
	{
		int numChar = forward.length() - 1;
		String reverse = "";
		
		for (int i = numChar; i >= 0; i++)
		{
			reverse += forward.charAt(i);
		}
		return reverse;
	}
	
	public class Driver 
	{
		String forward = "keepitayoself";

		String reverse = reverseString(forward);
	}
}


