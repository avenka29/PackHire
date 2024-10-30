/**
 * 
 */
package edu.ncsu.csc216.app_manager.model.manager;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc216.app_manager.model.application.Application;
import edu.ncsu.csc216.app_manager.model.application.Application.AppType;
import edu.ncsu.csc216.app_manager.model.command.Command;

/**
 * Maintains a list of Applications
 * @author Adith Venkatesh
 */
public class AppList {

	/**Used to determine application id*/
	private int counter;
	
	/**ArrayList of applications*/
	private ArrayList<Application> appList;
	
	/**
	 * Constructor for AppList
	 */
	public AppList() {
		appList = new ArrayList<Application>();
		counter = 0;
	}
	
	/**
	 * Adds an application
	 * @param appType type of application
	 * @param summary summary of application
	 * @param note application note
	 * @return integer
	 */
	public int addApp(AppType appType, String summary, String note) {
		counter++;
		Application app = new Application(counter, appType, summary, note);
		appList.add(app);
		return app.getAppId();
	}
	
	/**
	 * Adds multiple applications
	 * @param apps list of applications to be added
	 */
	public void addApps(ArrayList<Application> apps) {
		appList = new ArrayList<Application>();
		for(int i = 0; i < apps.size(); i++) {
			addApp(apps.get(i));
		}
		
		counter = appList.getLast().getAppId() + 1;
		
		System.out.println("FINAL APP RESULT");
		for(int i = 0; i < appList.size(); i++ ) {
			System.out.println(appList.get(i).toString());
		}
		System.out.println("FINAL APP RESULT");

		
	}
	
	/**
	 * Checks for duplicates and adds in sorted order
	 * @param app application to be added
	 */
	private void addApp(Application app) {		
		//Checks for duplicates
		if(appList.size() == 0) {
			appList.add(app);
		}
		
		else {
			boolean shouldAdd = true;
			for(int i = 0; i < appList.size(); i++) {
				//System.out.println("Run B");  
				if(appList.get(i).getAppId() == app.getAppId()) {
					System.out.println("Run");
					shouldAdd = false;
					break;
				}
				
			}
			
			//Finding right index
			if(shouldAdd) {
			boolean added = true;
			for(int i = 0; i < appList.size(); i++) {	
				if(appList.get(i).getAppId() > app.getAppId()) {
					//Adds application before the larger id value
					System.out.println(appList.get(i));
					appList.add(i, app);
					added = false;
					break;
				}
				

			}
			
			if(added) {
				appList.add(app);
			}
		}

		} 
		
		
		
		
	}
	
	/**
	 * Gets application list
	 * @return application list
	 */
	public List<Application> getApps() {
		return appList;
	}
	
	/**
	 * Gets all applications of a specific type
	 * @param type type of application
	 * @return list of applications
	 */
	public ArrayList<Application> getAppsByType(String type) {
		if(type == null) {
			throw new IllegalArgumentException("Invalid type.");
		}
		ArrayList<Application> answer = new ArrayList<Application>();
		for(int i = 0; i < appList.size(); i++) {
			if(appList.get(i).getAppType().equals(type)) {
				answer.add(appList.get(i));
			}
		}
		
		System.out.println("APP TYPE START");
		for(int i = 0; i < answer.size(); i++) {
			System.out.println(answer.get(i));
		}
		
		System.out.println("APP TYPE START");

		return answer;
	}
	
	/**
	 * Gets an application by id
	 * @param id id of application to get
	 * @return Application with corresponding id
	 */
	public Application getAppById(int id) {
		
		for(int i = 0; i < appList.size(); i++) {
			if(appList.get(i).getAppId() == id) {
				return appList.get(i); 
			}
		}
		
		
		
		return null;
	}
	
	/**
	 * Executes a command
	 * @param id id of app
	 * @param command command to be executed 
	 */
	public void executeCommand(int id, Command command) {
		Application app = getAppById(id);
		
		if(app == null) {
			return;
		}
		getAppById(id).update(command); 
		
	}
	
	/**
	 * Deletes an app based on id
	 * @param id id of application
	 */
	public void deleteAppById(int id) {
		
		for(int i = 0; i < appList.size(); i++) {
			if(appList.get(i).getAppId() == id) {
				appList.remove(i);
			}
		}
		
	}
	
	
}
