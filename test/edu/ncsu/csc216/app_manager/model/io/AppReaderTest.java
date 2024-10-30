
package edu.ncsu.csc216.app_manager.model.io;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


import edu.ncsu.csc216.app_manager.model.application.Application;




/**
 * Tests AppReader
 * @author Adith Venkatesh
 */
class AppReaderTest { 


	
	/** File reader test  */
	private final String fileTest = "test-files/actualAppRecords.txt";
	
	
	
	/**
	 * Tests if file does not exist
	 */
	@Test
	public void invalidFileName() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> AppReader.readAppsFromFile("doesnotexist"));
		assertEquals("Unable to load file.", e.getMessage());
	} 

	/**
	 * Tests valid file read
	 */
	@Test
	public void validTestFile() {
		
		//Creates expected notes array
		ArrayList<String> notes = new ArrayList<String>(); 
		notes.add("[Review] Note 1");
		notes.add("[Waitlist] Note 2");
		
		//Creates list of expected applications
		ArrayList<Application> expected = new ArrayList<Application>();

		expected.add(new Application(14, "Waitlist", "New", "Application summary", null, false, "ReviewCompleted", notes));
		
		ArrayList<Application> actual = AppReader.readAppsFromFile(fileTest);
		for(int i = 0; i < actual.size(); i++) {
			Application predict = expected.get(i);
			Application result = actual.get(i); 
			assertAll(
					() -> assertEquals(predict.getAppId(), result.getAppId()),
					() -> assertEquals(predict.getStateName(), result.getStateName()),
					() -> assertEquals(predict.getAppType(), result.getAppType()),
					() -> assertEquals(predict.getSummary(), result.getSummary()),
					() -> assertEquals(predict.getReviewer(), result.getReviewer()),
					() -> assertFalse(result.isProcessed()), 
					() -> assertEquals(predict.getResolution(), result.getResolution()),
					() -> assertEquals(predict.getNotesString(), result.getNotesString())
			);

		}
		
		
	}
	
	/**
	 * Tests if an id is negative and the exception is thrown
	 */
	@Test
	public void testNegativeId() {
		assertThrows(IllegalArgumentException.class, () -> AppReader.readAppsFromFile("test-files/negativeId.txt"));
	}


}
