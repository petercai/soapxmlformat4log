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
	public void transform(final String source) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException, SAXException, ParserConfigurationException
	{
		String dest = source+".xmlpretty";
		formatLog(source, dest);
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

	protected void formatLog(String source,String dest) throws FileNotFoundException,
			IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException, SAXException, ParserConfigurationException
	{

		File fin = new File(source);
		FileInputStream fis = new FileInputStream(fin);
		BufferedReader in = new BufferedReader(new InputStreamReader(fis));

		FileWriter fstream = new FileWriter(dest,false);
		BufferedWriter out = new BufferedWriter(fstream);

		String sin = null;
		while ((sin = in.readLine()) != null)
		{
			out.write(formatSOAPEnvelope(sin));
			out.newLine();
		}
		// do not forget to close the buffer reader
		in.close();
		// close buffer writer
		out.close();
	}


	

	
}