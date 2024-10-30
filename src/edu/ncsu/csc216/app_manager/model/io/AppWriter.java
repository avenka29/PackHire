package edu.ncsu.csc216.app_manager.model.io;

import java.io.File;
import java.io.PrintStream;
import java.util.List;

import edu.ncsu.csc216.app_manager.model.application.Application;

/**
 * Writes Application information to a file
 * @author Adith Venkatesh
 */
public class AppWriter {


	
	/**
	 * Writes application info to a file from a list
	 * @param fileName name of file to be written into
	 * @param appList Application list to be written into a file
	 */
	public static void writeAppsToFile(String fileName, List<Application> appList) {

		try{
			PrintStream fileWriter = new PrintStream(new File(fileName));
			for (int i = 0; i < appList.size(); i++) {
				System.out.println(appList.get(i).toString());
				fileWriter.print(appList.get(i).toString());
			}

			fileWriter.close(); 
		}
		
		catch(Exception e){
			throw new IllegalArgumentException("Unable to save file.");
		}
					

				

	}
	
	
}
