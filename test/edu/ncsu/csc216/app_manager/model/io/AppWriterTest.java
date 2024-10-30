package edu.ncsu.csc216.app_manager.model.io;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


import edu.ncsu.csc216.app_manager.model.application.Application;

/**
 * Tests AppWriter
 * @author Adith Venkatesh
 */
class AppWriterTest {
	
	/**
	 * Helper method to compare two files for the same contents (Note: This method was made by Teaching Staff)
	 * @param expFile expected output
	 * @param actFile actual output
	 * @return whether two files are equal or not
	 */
	private boolean checkFiles(String expFile, String actFile) { 
		boolean answer = false;
		try (Scanner expScanner = new Scanner(new FileInputStream(expFile));
				Scanner actScanner = new Scanner(new FileInputStream(actFile));) {

			while (expScanner.hasNextLine() && actScanner.hasNextLine()) {
				String exp = expScanner.nextLine();
				String act = actScanner.nextLine();
				assertEquals(exp, act, "Expected: " + exp + " Actual: " + act);
				if(exp.equals(act)) {
					answer = true;
				}
				// The third argument helps with debugging!
			}
			if (expScanner.hasNextLine()) {
				fail("The expected results expect another line " + expScanner.nextLine());
				answer = false;
			}
			if (actScanner.hasNextLine()) {
				fail("The actual results has an extra, unexpected line: " + actScanner.nextLine());
				answer = false;
			}
			
			expScanner.close();
			actScanner.close();
			
			return answer;
		} catch (IOException e) {
			return false;
			//fail("Error reading files."); 
		}
	}

	/**
	 * tests app writing impelmentation
	 */
	@Test
	public void testWriteAppsToFile() throws IOException {
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("[Review] Note 1\n");
		
		ArrayList<Application> appList = new ArrayList<Application>();

		appList.add(new Application(15, "Closed", "Old", "Application summary", "reviewer", true, "OfferCompleted", notes));
		AppWriter.writeAppsToFile("test-files/actual.txt", appList);
		assertTrue(checkFiles("test-files/filereadertest.txt", "test-files/actual.txt"));
		
		
	}

}
