/***********************************************
 * Copyright (c) 2013 Peter Cai                *
 * All rights reserved.                        *
 ***********************************************/
package cai.peter.launch;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import cai.peter.util.TransformLogFile;

public class Launcher
{
	public static void main(String[] args)
	{
		Options options = new Options();
		Option option = new Option("f",true, "File name or Folder to format");
		option.setRequired(true);
		options.addOption(option);
		CommandLineParser parser = new BasicParser();
		try
		{
			CommandLine cl = parser.parse(options, args);
			String filename = cl.getOptionValue("f");

			new TransformLogFile().transform(filename);

		}
		catch (ParseException e)
		{
			help(options);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	static void help(Options options)
	{
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("java -jar logf.jar -f <arg>", options);
	}
}
