package edu.ncsu.csc216.app_manager.model.manager;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import edu.ncsu.csc216.app_manager.model.application.Application;
import edu.ncsu.csc216.app_manager.model.application.Application.AppType;
//import edu.ncsu.csc216.app_manager.model.command.Command;
//import edu.ncsu.csc216.app_manager.model.command.Command.CommandValue;
//import edu.ncsu.csc216.app_manager.model.command.Command.Resolution;
import edu.ncsu.csc216.app_manager.model.command.Command;
import edu.ncsu.csc216.app_manager.model.command.Command.CommandValue;

/**
 * Tests AppList
 * @author Adith Venkatesh
 */
class AppListTest {
	
	/*@Before
	public ArrayList<Application> setUp() {
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("a");
		notes.add("b");
		Application app1 = new Application(14, "Waitlist", "New", "Application Summary", "", false, "ReviewCompleted", notes);
		Application app2 = new Application(1, "Review", "New", "Application Summary", "", false, "", notes);
		Application app3 = new Application(2, "Waitlist", "New", "Application Summary", "", false, "ReviewCompleted", notes);

		ArrayList<Application> apps = new ArrayList<Application>();
		apps.add(app1);
		apps.add(app2);
		apps.add(app3);
		
		return apps;
	}*/
	
	/**Default test*/
	@Test
	public void testAddApps() {
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("a");  
		notes.add("b");
		Application app2 = new Application(1, "Review", "New", "Application Summary", "", false, "", notes);
		Application app3 = new Application(2, "Waitlist", "New", "Application Summary", "", false, "ReviewCompleted", notes);
		Application app4 = new Application(2, "Waitlist", "New", "Application Summary", "", false, "ReviewCompleted", notes);


		ArrayList<Application> apps = new ArrayList<Application>();
		
		apps.add(app2);	
		apps.add(app3);
		//Successful add
		AppList appList = new AppList();
		appList.addApps(apps);
		assertEquals(apps, appList.getApps()); 
		
		apps.add(app4);
		
		ArrayList<Application> expected = new ArrayList<Application>();
		expected.add(new Application(1, "Review", "New", "Application Summary", "", false, "", notes));
		expected.add(new Application(2, "Waitlist", "New", "Application Summary", "", false, "ReviewCompleted", notes));


		
		
		
	}
	
	
	@Test
	public void testGetAppById() {
		AppList appList = new AppList();
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("a");
		notes.add("b");
		Application app1 = new Application(14, "Waitlist", "New", "Application Summary", "", false, "ReviewCompleted", notes);
		Application app2 = new Application(1, "Review", "New", "Application Summary", "", false, "", notes);
		Application app3 = new Application(2, "Waitlist", "New", "Application Summary", "", false, "ReviewCompleted", notes);
		
		ArrayList<Application> apps = new ArrayList<Application>();
		apps.add(app1);
		apps.add(app2);
		apps.add(app3);
		appList.addApps(apps);
		
		assertEquals(app1, appList.getAppById(14));
	}
	
	@Test
	public void testgetAppsByType() {
		AppList appList = new AppList();
		assertThrows(IllegalArgumentException.class, () -> appList.getAppsByType(null));
	}
	
	
	@Test
	public void testExecuteCommand() {
		AppList appList = new AppList();
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("a");
		notes.add("b");
		//Command command1 = new Command(CommandValue.ACCEPT, "reviewer", Resolution.OFFERCOMPLETED, "c");
		Application app1 = new Application(14, "Waitlist", "New", "Application Summary", "", false, "ReviewCompleted", notes);
		
		ArrayList<Application> expected = new ArrayList<Application>();
		expected.add(app1);
		
		appList.addApps(expected);
		
		ArrayList<Application> actual = appList.getAppsByType("New");
		for(int i = 0; i < expected.size(); i++) {
			assertEquals(expected.get(i), actual.get(i));
		}
		
	}
	
	/**
	 * Tests if add app returns the App Id of an newly added application
	 */
	@Test
	public void testAddApp() {
		AppList appList = new AppList();
		assertEquals(1, appList.addApp(AppType.NEW, "Summary", "Note"));
	}
	
	/**
	 * Tests if getAppById returns null if not foud in appList
	 */
	@Test 
	public void testGetAppByIdNull() {
		AppList appList = new AppList();
		appList.addApp(AppType.NEW, "Summary", "Note");
		assertNull(appList.getAppById(24));
	}
	
	/**
	 * Tests if executeCommand does not throw a NullPointerException if no App is found by getAppID
	 */
	@Test
	public void executeDoesNotThrow() {
		AppList appList = new AppList();
		appList.addApp(AppType.NEW, "Summary", "Note");
		Command command1 = new Command(CommandValue.ACCEPT, "reviewer", null, "Note 1");
		assertDoesNotThrow( () -> appList.executeCommand(24, command1));
	}
	

	

}
