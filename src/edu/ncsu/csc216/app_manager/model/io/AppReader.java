
package edu.ncsu.csc216.app_manager.model.io;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import edu.ncsu.csc216.app_manager.model.application.Application;

/**
 * Processes files containing applicant information and creates a list of type Application
 * @author Adith Venkatesh
 */
public class AppReader {
	
	/*/**
	 * Constructor for AppReader
	 */
	//public AppReader(){
		
	//}
	
	/**
	 * Reads Application info from a file and makes an ArrayList of applications
	 * @param filename file to be read in
	 * @return an ArrayList of Applications 
	 * @throws IllegalArgumentException if file cannot be found
	 */
	public static ArrayList<Application> readAppsFromFile(String filename){
		try {
			String file = "";
			ArrayList<Application> answer = new ArrayList<Application>();
			Scanner fileScanner = new Scanner(new FileInputStream(filename));
			while(fileScanner.hasNextLine()) { 
				file = file + fileScanner.nextLine() + "\n";
			}
			//print file variable 
			//System.out.println(file);
			
			Scanner lineScanner = new Scanner(file);
			lineScanner.useDelimiter("\\r?\\n?[*]");
			
			while(lineScanner.hasNext()) {
				answer.add(processApp(lineScanner.next()));
			}
			fileScanner.close();
			lineScanner.close();
			for(int i = 0; i < answer.size(); i++) {
				System.out.print("App:" + answer.get(i));
			}
			return answer; 
			
			
		}
		
		catch(FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to load file.");  
		}
		
	}
	
	/**
	 * Processes an application
	 * @param application application to be processed
	 * @return processed Application
	 * @throws IllegalArgumentException if there is an unexpected input in file
	 */
	private static Application processApp(String application) {
		System.out.println(application);
		//application.split("\\r?\\n?[-]");
		try {
			if(application.startsWith("-")) {
				throw new IllegalArgumentException("Invalid information.");
			}
			Application answer = null;
			Scanner input = new Scanner(application);
			ArrayList<String> notes = new ArrayList<String>();
			
			//Uses delimeter to seperate application info and notes
			input.useDelimiter("\r?\n?[-]");
			String appString;
			appString = input.next();
			String noteDisplay = "";
			while(input.hasNext()) {
				noteDisplay = input.next().strip();
				notes.add(noteDisplay);
			}

			Scanner appScanner = new Scanner(appString);
			appScanner.useDelimiter(",");
			int id = appScanner.nextInt();
			String state = appScanner.next();
			String appType = appScanner.next(); 
			String summary = appScanner.next();
			String reviewer = appScanner.next();
			boolean processPaperwork = appScanner.nextBoolean();
			String resolution = ""; 
			if(appScanner.hasNext()) {
				resolution = appScanner.next();
			}
			
			else {
				 resolution = null;
			}
			
			/*//processing notes
			String unprocessedNotes = appScanner.next();
			Scanner noteScanner = new Scanner(unprocessedNotes);
			noteScanner.useDelimiter("\\r?\\n?[-]");
			while(noteScanner.hasNext()) {
				notes.add(noteScanner.next()); 
			}*/
			
			answer = new Application(id, state, appType, summary, reviewer, processPaperwork, resolution, notes);
		
			input.close();
			appScanner.close();
			return answer;

		}
		
		catch(InputMismatchException e) {
			throw new IllegalArgumentException("Unable to load file.");
		}
		
		
		
	}
	
}
