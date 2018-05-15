import java.io.File;

public class TextStatistics implements TextStatisticsInterface
{

	public TextStatistics(String string) {
		
	
		File file = new File(string);
	}
	
	@Override
	public int getCharCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWordCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLineCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int[] getLetterCount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getWordLengthCount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getAverageWordLength() {
		// TODO Auto-generated method stub
		return 0;
	}

}
