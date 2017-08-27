import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class hashtagcounter {
	private static final String STOPKEYWORD = "stop";
	public static final String outputFileName = "output_file.txt";
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length <= 0){
			System.out.println("Please provide the file");
		}
		BufferedReader bufferedHashTagReader = null;
		BufferedWriter bufferedHashTagWriter = null;
		try {
			FileReader hashTagReader = new FileReader(new File(args[0]));
			bufferedHashTagReader = new BufferedReader(hashTagReader);
			IMaxFibonacciHeap maxHeap = null;
			String lineToBeProcessed = "";
			String lineToWrite = "";
			HashTagCounterManager hashTagCounterManager = new HashTagCounterManager();
			/* reads the input file one line at a time and calls processLine method which does work on the line read.
			   This process continues until the EOF is attained or a stop keyword is read */
			try {
				File outputFile = new File(outputFileName);		
				if(!outputFile.exists()){
					outputFile.createNewFile();
				}
				FileWriter hashTagWriter = new FileWriter(outputFile,true);
				bufferedHashTagWriter = new BufferedWriter(hashTagWriter);
				
				while((lineToBeProcessed = bufferedHashTagReader.readLine()) != null && !lineToBeProcessed.equalsIgnoreCase(STOPKEYWORD)) {
					//maxHeap = hashTagCounterManager.processLine(lineToBeProcessed);
					// The line passed is splitted on the basis of space
					String[] splittedWords = lineToBeProcessed.split(" ");
					// this block is executed only when the line has a hashtag followed by the hash tag frequency
					if(splittedWords.length > 1){
						String hashTag = splittedWords[0].substring(1);
						int frequency = Integer.parseInt(splittedWords[1].trim());
						hashTagCounterManager.processLine(hashTag,frequency);
					}	
					else{
						lineToWrite = hashTagCounterManager.getRemovedNodes(lineToBeProcessed);
						bufferedHashTagWriter.write(lineToWrite);	
						bufferedHashTagWriter.newLine();						
					}
				}
			}catch (IOException e) {				
				e.printStackTrace();
			}   
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally{
			try {
				bufferedHashTagReader.close();
				bufferedHashTagWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
