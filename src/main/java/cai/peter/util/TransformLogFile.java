package cai.peter.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class TransformLogFile
{
	public void transform(final String filename, boolean isBackup) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException, SAXException, ParserConfigurationException
	{
		if( isBackup )
		{
			String dest = filename+".xmlpretty";
			formatLog(new File(filename), new File(dest));
			
		}
		else
		{
			File dest = new File(filename);
			
			for(int i=1;i<Integer.MAX_VALUE;i++)
			{
				File source = new File(String.format("%s.bak%d", filename,i));
				if( !source.exists())
				{
					dest.renameTo(source);
					formatLog(source, dest);
					break;
				}
			}
			
		}
		
			
//		String tmp = source + ".___";
//		File tmpFile = new File(tmp);
//		if( tmpFile.exists())
//			tmpFile.delete();
//		new File(source).renameTo(tmpFile);
//		new File(dest).renameTo(new  File(source));
	}

	boolean isChanged = false;
	String formatSOAPEnvelope(String input ) throws ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException, SAXException, IOException, ParserConfigurationException
	{
			Pattern regex = Pattern.compile("<.*?Envelope.*?Envelope>");
			Matcher regexMatcher = regex.matcher(input);
			if (regexMatcher.find()) {
				int mstart = regexMatcher.start();
				int mend = regexMatcher.end();
				
				return input.substring(0,mstart)
						+System.lineSeparator()
						+XmlFormatter.format(input.substring(mstart,mend))
						+System.lineSeparator()
						+formatSOAPEnvelope(input.substring(mend));
			} 
			
			return input;
		
	}

	protected void formatLog(File source,File dest) throws FileNotFoundException,
			IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException, SAXException, ParserConfigurationException
	{

		BufferedReader fileBufReader = new BufferedReader(new InputStreamReader(new FileInputStream(source)));
		BufferedWriter fileBufWriter = new BufferedWriter(new FileWriter(dest,false));

		String aLine = null;
		while ((aLine = fileBufReader.readLine()) != null)
		{
			fileBufWriter.write(formatSOAPEnvelope(aLine));
			fileBufWriter.newLine();
		}
		fileBufReader.close();
		fileBufWriter.close();
	}


	

	
}