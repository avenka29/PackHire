package edu.ncsu.csc216.app_manager.model.manager;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;


import edu.ncsu.csc216.app_manager.model.application.Application.AppType;

/**
 * Tests AppManager
 * @author Adith Venkatesh
 */
class AppManagerTest {

	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new FileInputStream(expFile));
				Scanner actScanner = new Scanner(new FileInputStream(actFile));) {

			while (expScanner.hasNextLine() && actScanner.hasNextLine()) {
				String exp = expScanner.nextLine();
				String act = actScanner.nextLine();
				assertEquals(exp, act, "Expected: " + exp + " Actual: " + act);
				// The third argument helps with debugging!
			}
			if (expScanner.hasNextLine()) {
				fail("The expected results expect another line " + expScanner.nextLine());
			}
			if (actScanner.hasNextLine()) {
				fail("The actual results has an extra, unexpected line: " + actScanner.nextLine());
			}

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files."); 
		}
	}
	
	/**Test for AppManager saveAppsToFile()
	 */
	@Test
	public void testSaveAppsToFile(){
		AppManager manager = AppManager.getInstance();
		manager.createNewAppList();
		//ArrayList<Application> expected = AppReader.readAppsFromFile("\"test-files/filereadertest.txt\"");
		
		manager.addAppToList(AppType.NEW, "Application summary", "Note 1");
		Object[][] expected = {{1, "Review", "New", "Application summary"}};
		Object [][] actual = manager.getAppListAsArray();
		for(int i = 0; i < expected.length; i++) {
			for(int j = 0; j < expected[i].length; j++) {
				assertEquals(expected[i][j], actual[i][j]);
			}
		}
		manager.saveAppsToFile("test-files/appmanager_actual.txt");

		checkFiles("test-files/appmanager_expected.txt", "test-files/appmanager_actual.txt");
		
	}
	
	@Test
	public void testGetAppsByType() throws IOException {
		AppManager manager = AppManager.getInstance();
		assertThrows(IllegalArgumentException.class, () -> manager.getAppListAsArrayByAppType(null));
		assertThrows(IllegalArgumentException.class, () -> manager.getAppListAsArrayByAppType("Invalid"));
		
		manager.addAppToList(AppType.NEW, "Application summary", "Note 1");
		Object[][] expected = {{1, "Review", "New", "Application summary"}};
		Object [][] actual = manager.getAppListAsArrayByAppType("New");
		for(int i = 0; i < expected.length; i++) {
			for(int j = 0; j < expected[i].length; j++) {
				assertEquals(expected[i][j], actual[i][j]);
			}
		}
		
		
	}
	


}
