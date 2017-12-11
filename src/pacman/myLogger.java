package pacman;
import java.io.*;

public class myLogger 
{
	private String fileName;
	private boolean append;
	private FileWriter fw;
	private PrintWriter pw;
	
	public myLogger()
	{
		//Initialize variables
		this.fileName = "logger.txt";
		this.append = true;
	}
	
	public void write_to_logger(String message) throws IOException
	{
		fw = new FileWriter(fileName, append);
		pw = new PrintWriter(fw);
		
		pw.printf("%s\n", message);
		
		fw.close();
		pw.close();
	}
}
