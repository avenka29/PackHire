
package edu.ncsu.csc216.app_manager.model.application;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import edu.ncsu.csc216.app_manager.model.application.Application.AppType;
import edu.ncsu.csc216.app_manager.model.command.Command;
import edu.ncsu.csc216.app_manager.model.command.Command.CommandValue;
import edu.ncsu.csc216.app_manager.model.command.Command.Resolution;


/**
 * Tests Application
 * @author Adith Venkatesh
 */
class ApplicationTest { 

	/**Tests application constructor**/
	@Test
	public void testApplicationConstructor() {
		
		//app id of 0
		Exception e = assertThrows(IllegalArgumentException.class, () -> new Application(0, AppType.NEW, "summary", "note"));
		assertEquals("Application cannot be created.", e.getMessage());
		
		//null app type
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> new Application(14, null, "summary", "note"));
		assertEquals("Application cannot be created.", e1.getMessage());

		//empty app type
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> new Application(14, null, "summary", "note"));
		assertEquals("Application cannot be created.", e2.getMessage());

		//null summary
		Exception e3 = assertThrows(IllegalArgumentException.class, () -> new Application(14, AppType.NEW, null, "note"));
		assertEquals("Application cannot be created.", e3.getMessage());

		//empty summary
		Exception e4 = assertThrows(IllegalArgumentException.class, () -> new Application(14, AppType.NEW, "", "note"));
		assertEquals("Application cannot be created.", e4.getMessage());

		//null note
		Exception e5 = assertThrows(IllegalArgumentException.class, () -> new Application(0, AppType.NEW, "summary", null));
		assertEquals("Application cannot be created.", e5.getMessage());

		//empty note
		Exception e6 = assertThrows(IllegalArgumentException.class, () -> new Application(14, AppType.NEW, null, ""));
		assertEquals("Application cannot be created.", e6.getMessage());
		
	}
	
	/**
	 * tests setAppType
	 */
	@Test
	public void testSetAppType() {
		//NEW AppType
		Application test = new Application(14, AppType.OLD, "summary", "note");
		assertEquals("Old", test.getAppType());
		
		//OLD AppType
		Application test1 = new Application(14, AppType.OLD, "summary", "note");
		assertEquals("Old", test1.getAppType());
		
		//HIRED AppType
		Application test2 = new Application(14, AppType.HIRED, "summary", "note");
		assertEquals("Hired", test2.getAppType());
		
		//Empty AppType
		Exception e = assertThrows(IllegalArgumentException.class, () -> new Application(14, null, "summary", "note"));
		assertEquals("Application cannot be created.", e.getMessage());
		
		//Null AppType
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> new Application(14, null, "summary", "note"));
		assertEquals("Application cannot be created.", e1.getMessage());
		
		
	}
	
	/*public void testSetNotes() {
		Application test = new Application(14, "NEW", "summary", "note");
		assertEquals("note", )
	}*/
	
	/**  
	 * Tests getAppId method
	 */
	@Test
	public void testGetAppId() {
		Application test = new Application(14, AppType.NEW, "summary", "note");
		assertEquals(14, test.getAppId());
		
		Exception e = assertThrows(IllegalArgumentException.class, () -> new Application(-1, AppType.NEW, "summary", "note"));
		assertEquals("Application cannot be created.", e.getMessage());
		
		

	}
	 
	@Test
	public void testApplicationConstructorLong() {
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("a");
		notes.add("b");
		Application test = new Application(14, "Waitlist", "New", "Application Summary", "", false, "ReviewCompleted", notes);
		
		//Test id
		assertEquals(14, test.getAppId());
		
		//Test state
		assertEquals("Waitlist", test.getStateName());
		
		//Test appType
		assertEquals("New", test.getAppType());
		
		//test summary
		assertEquals("Application Summary", test.getSummary());
		
		//test processPaperwork
		assertFalse(test.isProcessed());
		
		//test Resolution
		assertEquals("ReviewCompleted", test.getResolution());
		
		ArrayList<String> testNotes = new ArrayList<String>();
		testNotes.add("a");
		testNotes.add("b");
		
		//test notes
		assertEquals(testNotes, test.getNotes());

	}
	
	/**
	 * Test setState through constructor
	 */
	@Test
	public void testSetState() {
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("a");
		notes.add("b");
		
		//Null state
		Exception e = assertThrows(IllegalArgumentException.class, () -> new Application(14, null, "New", "Application Summary", "", false, "ReviewCompleted", notes));
		assertEquals("Application cannot be created.", e.getMessage());
		
		//Empty state
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> new Application(14, "", "New", "Application Summary", "", false, "ReviewCompleted", notes));
		assertEquals("Application cannot be created.", e1.getMessage());
		
		//Interview state
		Application test2 = new Application(3, "Interview", "Old", "Application Summary", "reviewer", false, "", notes);
		assertEquals("Interview", test2.getStateName());
		assertEquals("reviewer", test2.getReviewer());
		
		//RefCheck state
		Application test3 = new Application(7, "RefCheck", "Old", "Application summary", "reviewer", true, "", notes);
		assertEquals("RefCheck", test3.getStateName());
		
		//Offer state
		Application test4 = new Application(16, "Offer", "Old", "Application summary", "reviewer", true, "", notes);
		assertEquals("Offer", test4.getStateName());

		//Closed state
		Application test5 = new Application(15, "Closed", "Old", "Application summary", "reviewer", true, "OfferCompleted", notes);
		assertEquals("Closed", test5.getStateName());
		
		//Closed state
		Application test7 = new Application(15, "Review", "Old", "Application summary", "reviewer", false, "OfferCompleted", notes);
		assertEquals("Review", test7.getStateName());

		//If state is not one of the listed ones
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> new Application(15, "FailTest", "Old", "Application summary", "reviewer", true, "OfferCompleted", notes));
		assertEquals("Application cannot be created.", e2.getMessage());
		
		Application test6 = new Application(14, AppType.NEW, "summary", "note");
		assertEquals("Review", test6.getStateName());
	}
	
	@Test
	public void testSetReviewer() {
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("a");
		notes.add("b");

		//Empty reviewer in Interview state
		Exception e = assertThrows(IllegalArgumentException.class, () -> new Application(3, "Interview", "Old", "Application Summary", "", false, "", notes));
		assertEquals("Application cannot be created.", e.getMessage());

		//Null reviewer in Interview state
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> new Application(3, "Interview", "Old", "Application Summary", null, false, "", notes));
		assertEquals("Application cannot be created.", e1.getMessage());
		
		//Empty reviewer in Offer state
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> new Application(3, "Offer", "Old", "Application Summary", "", false, "", notes));
		assertEquals("Application cannot be created.", e2.getMessage());

		//Null reviewer in Offer state
		Exception e3 = assertThrows(IllegalArgumentException.class, () -> new Application(3, "Offer", "Old", "Application Summary", null, false, "", notes));
		assertEquals("Application cannot be created.", e3.getMessage());
		
		//Empty reviewer in ReferenceCheck state
		Exception e4 = assertThrows(IllegalArgumentException.class, () -> new Application(3, "RefCheck", "Old", "Application Summary", "", false, "", notes));
		assertEquals("Application cannot be created.", e4.getMessage());

		//Null reviewer in ReferenceCheck state
		Exception e5 = assertThrows(IllegalArgumentException.class, () -> new Application(3, "RefCheck", "Old", "Application Summary", null, false, "", notes));
		assertEquals("Application cannot be created.", e5.getMessage());
		
		
		
	}
	
	@Test
	public void testSetResolution() {
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("a");
		notes.add("b");
		Application test = new Application(3, "Interview", "Old", "Application Summary", "reviewer", false, "", notes);
		
		//Null pointer exception is caught and nothing is thrown;
		assertDoesNotThrow(() -> test.setResolution(null));
		
		//Null resolution in Waitlist state
		Exception e = assertThrows(IllegalArgumentException.class, () -> new Application(3, "Waitlist", "Old", "Application Summary", null, false, "", notes));
		assertEquals("Application cannot be created.", e.getMessage());
		
		//Empty resolution in Waitlist state
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> new Application(3, "Waitlist", "Old", "Application Summary", "", false, "", notes));
		assertEquals("Application cannot be created.", e1.getMessage());
		
		//Null resolution in Closed state
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> new Application(3, "Closed", "Old", "Application Summary", null, false, "", notes));
		assertEquals("Application cannot be created.", e2.getMessage());
		
		//Empty resolution in Closed state
		Exception e3 = assertThrows(IllegalArgumentException.class, () -> new Application(3, "Closed", "Old", "Application Summary", "", false, "", notes));
		assertEquals("Application cannot be created.", e3.getMessage());
		
		//Test InterviewCompleted resolution
		test.setResolution("OfferCompleted");
		assertEquals("OfferCompleted", test.getResolution());
		
		//Test ReferenceCheckCompleted resolution
		test.setResolution("ReferenceCheckCompleted");
		assertEquals("ReferenceCheckCompleted", test.getResolution());
	}
	
	@Test
	public void testSetSummary() {
		//Null summary
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("a");
		notes.add("b");
		
		//Null summary
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> new Application(3, "Interview", "Old", null, "reviewer", false, "", notes));
		assertEquals("Application cannot be created.", e1.getMessage());
		
		//Blank summary
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> new Application(3, "Interview", "Old", "", "reviewer", false, "", notes));
		assertEquals("Application cannot be created.", e2.getMessage());
	}
	 
	@Test
	public void testGetNotesString() {
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("a");
		notes.add("b");
		//System.out.println(notes.get(0));
		//System.out.println(notes.get(1));
		Application test = new Application(3, "Interview", "Old", "Application Summary", "reviewer", false, "", notes);
		String compare = "-a\n-b\n";
		//System.out.println(compare);
		//System.out.println(test.getNotesString());
		assertEquals(compare, test.getNotesString());
	}
	
	/**
	 * Tests valid and invalid transitions in the reivew state
	 * @throws UnsupportedOperationException if there is an invalid transition
	 */
	@Test
	public void testReviewState() throws UnsupportedOperationException {
		
		//Testing if a new application is automatically in reivew state
		Application app1 = new Application(1, AppType.NEW, "App summary", "note");
		assertEquals("Review", app1.getStateName());
		
		//Testing if app1 can be moved into interview state
		Command command1 = new Command(CommandValue.ACCEPT, "reviewer", null, "Note 1");
		app1.update(command1);
		assertAll(
			() -> assertEquals(1, app1.getAppId()),
			() -> assertEquals("Interview", app1.getStateName()),
			() -> assertEquals("Old", app1.getAppType()),
			() -> assertEquals("App summary", app1.getSummary()),
			() -> assertEquals("reviewer", app1.getReviewer()), 
			() -> assertFalse(app1.isProcessed()), 
			() -> assertEquals(null, app1.getResolution()),
			() -> assertEquals("-[Review] note\n-[Interview] Note 1\n", app1.getNotesString())
		);
				
		
		
		//New Application
		Application app2 = new Application(1, AppType.NEW, "App summary", "note");
		
		//Testing if app1 can be moved into WaitlistState
		Command command2 = new Command(CommandValue.STANDBY, null, Resolution.REVCOMPLETED, "Note 1");
		app2.update(command2);
		assertAll(
			() -> assertEquals(1, app2.getAppId()),
			() -> assertEquals("Waitlist", app2.getStateName()),
			() -> assertEquals("New", app2.getAppType()),
			() -> assertEquals("App summary", app2.getSummary()),
			() -> assertEquals(null, app2.getReviewer()),
			() -> assertFalse(app2.isProcessed()), 
			() -> assertEquals("ReviewCompleted", app2.getResolution()),
			() -> assertEquals("-[Review] note\n-[Waitlist] Note 1\n", app2.getNotesString())
		);
		
		//New Application
		Application app3 = new Application(1, AppType.NEW, "App summary", "note");
		
		//Testing if app1 can be moved into Closed
		Command command3 = new Command(CommandValue.REJECT, null, Resolution.REVCOMPLETED, "Note 1");
		app3.update(command3);
		assertAll(
			() -> assertEquals(1, app3.getAppId()),
			() -> assertEquals("Closed", app3.getStateName()),
			() -> assertEquals("New", app3.getAppType()),
			() -> assertEquals("App summary", app3.getSummary()),
			() -> assertEquals(null, app3.getReviewer()),
			() -> assertFalse(app3.isProcessed()), 
			() -> assertEquals("ReviewCompleted", app3.getResolution()),
			() -> assertEquals("-[Review] note\n-[Closed] Note 1\n", app3.getNotesString())
		);
		
	}
	
	
	
	/**
	 * Tests valid and invalid transitions in the Waitlist state
	 * @throws UnsupportedOperationException if there is an invalid transition
	 */
	@Test
	public void testInterviewState() throws UnsupportedOperationException { 
		
		//Getting application to interview state
		Application app1 = new Application(1, AppType.NEW, "App summary", "note");
		
		Command command1 = new Command(CommandValue.ACCEPT, "reviewer", null, "Note 1");
		app1.update(command1);
		
		//Testing transition to Reference Check state
		Command command2 = new Command(CommandValue.ACCEPT, "reviewer", null, "Note 2");
		app1.update(command2);
		assertAll(
				() -> assertEquals(1, app1.getAppId()),
				() -> assertEquals("RefCheck", app1.getStateName()),
				() -> assertEquals("Old", app1.getAppType()),
				() -> assertEquals("App summary", app1.getSummary()),
				() -> assertEquals("reviewer", app1.getReviewer()),
				() -> assertTrue(app1.isProcessed()), 
				() -> assertEquals(null, app1.getResolution()),
				() -> assertEquals("-[Review] note\n-[Interview] Note 1\n-[RefCheck] Note 2\n", app1.getNotesString())
	); 
		
		//Getting application to interview state
		Application app2 = new Application(1, AppType.NEW, "App summary", "note");
		app2.update(command1); 

		//Triggers transition to Waitlist state
		Command command3 = new Command(CommandValue.STANDBY, "reviewer", Resolution.OFFERCOMPLETED, "Note 2");
		app2.update(command3);
		assertAll(
			() -> assertEquals(1, app2.getAppId()),
			() -> assertEquals("Waitlist", app2.getStateName()),
			() -> assertEquals("Old", app2.getAppType()),
			() -> assertEquals("App summary", app1.getSummary()),
			() -> assertEquals("reviewer", app2.getReviewer()),
			() -> assertFalse(app2.isProcessed()), 
			() -> assertEquals("InterviewCompleted", app2.getResolution()),
			() -> assertEquals("-[Review] note\n-[Interview] Note 1\n-[Waitlist] Note 2\n", app2.getNotesString())
	);

		//Triggers transition t-
		Command command4 = new Command(CommandValue.REJECT, "reviewer", Resolution.INTCOMPLETED, "Note 3");
		//Getting application to interview state
		Application app3 = new Application(1, AppType.NEW, "App summary", "note");
		app3.update(command1);
		app3.update(command4);
		assertAll(
			() -> assertEquals(1, app3.getAppId()),
			() -> assertEquals("Closed", app3.getStateName()),
			() -> assertEquals("Old", app3.getAppType()),
			() -> assertEquals("App summary", app3.getSummary()),
			() -> assertEquals("reviewer", app3.getReviewer()), 
			() -> assertFalse(app3.isProcessed()), 
			() -> assertEquals("InterviewCompleted", app3.getResolution()),
			() -> assertEquals("-[Review] note\n-[Interview] Note 1\n-[Closed] Note 3\n", app3.getNotesString())
	);

	



	
	}
	
	@Test
	public void testWaitlistState() {
		//New Application
		Application app1 = new Application(1, AppType.NEW, "App summary", "note");
				
		//Testing if app1 can be moved into WaitlistState
		Command command1 = new Command(CommandValue.STANDBY, "", Resolution.REVCOMPLETED, "Note 1");
		app1.update(command1);
		
		Command command2 = new Command(CommandValue.REOPEN, "", null, "Note 2");
		app1.update(command2);
		assertAll(
				() -> assertEquals("Review", app1.getStateName()),
				() -> assertEquals("Old", app1.getAppType())
		);
		
		
		//New Application
		Application app2 = new Application(1, AppType.NEW, "App summary", "note");
		Command command4 = new Command(CommandValue.ACCEPT, "reviewer", null, "Note 1");
		app2.update(command4);	
		
		//Testing if app1 can be moved into WaitlistState
		Command command3 = new Command(CommandValue.STANDBY, "reviewer", Resolution.INTCOMPLETED, "Note 2");
		app2.update(command3);
		
		Command command5 = new Command(CommandValue.REOPEN, "reviewer", null, "Note 3");
		app2.update(command5);
		assertAll(
				() -> assertEquals("RefCheck", app2.getStateName()),
				() -> assertEquals("Old", app2.getAppType()),
				() -> assertEquals("reviewer", app2.getReviewer()), 
				() -> assertTrue(app2.isProcessed()), 
				() -> assertNull(null)
		); 

		
		
		
	}
	
	@Test
	public void testRefCheckState() {
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("a");
		notes.add("b");
		Application app1 = new Application(7, "RefCheck", "Old", "Application summary", "reviewer", true, "", notes );
		
		Command command1 = new Command(CommandValue.ACCEPT, "reviewer", null, "c");
		app1.update(command1);
		
		assertAll(
				() -> assertEquals("Offer", app1.getStateName()),
				() -> assertEquals("Old", app1.getAppType()),
				() -> assertEquals("reviewer", app1.getReviewer()), 
				() -> assertTrue(app1.isProcessed()), 
				() -> assertNull(null)
		); 
		
		Application app2 = new Application(7, "RefCheck", "Old", "Application summary", "reviewer", true, "", notes );
		Command command2 = new Command(CommandValue.REJECT, null, Resolution.REFCHKCOMPLETED, "d" );
		app2.update(command2);
		assertAll(
				() -> assertEquals("Closed", app2.getStateName()),
				() -> assertEquals("Old", app2.getAppType()),
				() -> assertEquals(null, app2.getReviewer()), 
				() -> assertTrue(app2.isProcessed()), 
				() -> assertEquals("ReferenceCheckCompleted", app2.getResolution())
		); 
	}
	
	@Test
	public void testOfferState() {
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("a");
		notes.add("b");
		Application app1 = new Application(16, "Offer", "Old", "Application summary", "reviewer", true, null, notes);
		Command command1 = new Command(CommandValue.ACCEPT, "reviewer", Resolution.OFFERCOMPLETED, "c");
		app1.update(command1);
		
		assertAll(
				() -> assertEquals("Closed", app1.getStateName()),
				() -> assertEquals("Hired", app1.getAppType()),
				() -> assertEquals("reviewer", app1.getReviewer()),  
				() -> assertTrue(app1.isProcessed()), 
				() -> assertEquals("OfferCompleted", app1.getResolution())
		);
		
		Application app2 = new Application(16, "Offer", "Old", "Application summary", "reviewer", true, null, notes);
		Command command2 = new Command(CommandValue.REOPEN, "reviewer", null, "d");
		
		assertThrows(UnsupportedOperationException.class, () -> app2.update(command2)); 
		
		Application app3 = new Application(16, "Offer", "Old", "Application summary", "reviewer", true, null, notes);
		Command command3 = new Command(CommandValue.REJECT, "reviewer", Resolution.OFFERCOMPLETED, "e");
		app3.update(command3);
		assertAll(
				() -> assertEquals("Closed", app3.getStateName()),
				() -> assertEquals("Old", app3.getAppType()),
				() -> assertEquals("reviewer", app3.getReviewer()),  
				() -> assertTrue(app3.isProcessed()), 
				() -> assertEquals("OfferCompleted", app3.getResolution())
		);
		
	}
	
	@Test
	public void testClosedState() {
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("a");
		notes.add("b");
		
		//New Application
		Application app3 = new Application(1, AppType.NEW, "App summary", "note");
				
		//Testing if app1 can be moved into Closed
		Command command3 = new Command(CommandValue.REJECT, null, Resolution.REVCOMPLETED, "Note 1");
		app3.update(command3);
		assertAll(
			() -> assertEquals(1, app3.getAppId()),
			() -> assertEquals("Closed", app3.getStateName()),
			() -> assertEquals("New", app3.getAppType()),
			() -> assertEquals("App summary", app3.getSummary()),
			() -> assertEquals(null, app3.getReviewer()),
			() -> assertFalse(app3.isProcessed()), 
			() -> assertEquals("ReviewCompleted", app3.getResolution())
		);
		
		Command command4 = new Command(CommandValue.REOPEN, "", null, "Note 3" );
		app3.update(command4);
		assertAll(
				() -> assertEquals(1, app3.getAppId()),
				() -> assertEquals("Review", app3.getStateName()),
				() -> assertEquals("Old", app3.getAppType()),
				() -> assertEquals(null, app3.getReviewer()),
				() -> assertFalse(app3.isProcessed()), 
				() -> assertEquals(null, app3.getResolution())
			); 
			
		
		Application app4 = new Application(4, "Closed", "Old", "Application summary", null, true, "OfferCompleted", notes);
		
		Command command1 = new Command(CommandValue.ACCEPT, "reviewer", Resolution.OFFERCOMPLETED, "c");

		assertThrows(UnsupportedOperationException.class, () -> app4.update(command4));
		assertThrows(UnsupportedOperationException.class, () -> app4.update(command1));
	}
	
	@Test
	public void testUpdateInterviewToWaitlist() {
		Application app1 = new Application(1, AppType.NEW, "App summary", "note");
		Command command1 = new Command(CommandValue.ACCEPT, "reviewer", null, "Note 1" );
		app1.update(command1);
		
		Command command2 = new Command(CommandValue.STANDBY, "reviewer", Resolution.INTCOMPLETED, "Note 2");
		app1.update(command2);
		
		assertEquals("Waitlist", app1.getStateName());
	} 
	 
}
