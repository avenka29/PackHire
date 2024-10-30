/**
 * 
 */
package edu.ncsu.csc216.app_manager.model.manager;

import java.util.ArrayList;

import edu.ncsu.csc216.app_manager.model.application.Application;
import edu.ncsu.csc216.app_manager.model.application.Application.AppType;
import edu.ncsu.csc216.app_manager.model.command.Command;
import edu.ncsu.csc216.app_manager.model.io.AppReader;
import edu.ncsu.csc216.app_manager.model.io.AppWriter;

/**
 * Maintains Application List and handles input from GUI
 * @author Adith Venkatesh
 */
public class AppManager {
	
	/**Contains AppManager instance*/
	private static AppManager instance;
	
	/**Contains AppList instance*/
	private AppList appList;
	
	/**
	 * Constructor for app manager
	 */
	public AppManager() {
		appList = new AppList();
	}
	/**
	 * Gets AppManager  
	 * @return AppManager
	 */
	public static AppManager getInstance() {
		if (instance == null) {
			instance = new AppManager();
		}
		return instance;
	}


	/**
	 * Saves applications to a file
	 * @param fileName file to be saved into
	 */
	public void saveAppsToFile(String fileName){
		AppWriter.writeAppsToFile(fileName, appList.getApps()); 
		
	}

	/**
	 * Loads applications from a file
	 * @param fileName name of the file
	 */
	public void loadAppsFromFile(String fileName) {
		appList.addApps(AppReader.readAppsFromFile(fileName));
		
	}
	
	
	/**
	 * Updates the global reference to the older app list to a new app list
	 */
	public void createNewAppList() {
		appList = new AppList();
		
	}

	/**
	 * Returns the application list as a 2d array
	 * @return a 2D array of Objects
	 */
	public Object[][] getAppListAsArray() {
		Object[][] answer = new Object[appList.getApps().size()][4];
		for(int i = 0; i < appList.getApps().size(); i++) {
			answer[i][0] = appList.getApps().get(i).getAppId();
			answer[i][1] = appList.getApps().get(i).getStateName();
			answer[i][2] = appList.getApps().get(i).getAppType();
			answer[i][3] = appList.getApps().get(i).getSummary();
		}
		return answer;
	}

	/**
	 * Returns the application list as a 2d array based on Application type
	 * @param appType type of object
	 * @return a 2D array of Objects
	 */
	public Object[][] getAppListAsArrayByAppType(String appType) {
	
		if(appType == null) {
			throw new IllegalArgumentException();
		}
		
		if(!"New".equals(appType) && !"Old".equals(appType) && !"Hired".equals(appType)) {
			throw new IllegalArgumentException();
		}
		
		ArrayList<Application> appByType = appList.getAppsByType(appType);

		Object[][] answer = new Object[appByType.size()][4];
		for(int i = 0; i < appByType.size(); i++) {
			answer[i][0] = appByType.get(i).getAppId();
			answer[i][1] = appByType.get(i).getStateName();
			answer[i][2] = appByType.get(i).getAppType();
			answer[i][3] = appByType.get(i).getSummary();
		}
		return answer;
	}

	
	/**
	 * Gets an Application by id
	 * @param appId id of application to get
	 * @return Application of corresponding id
	 */
	public Application getAppById(int appId) {
		
		return appList.getAppById(appId);
	}

	
	/**
	 * Executes a command
	 * @param id id of app
	 * @param command command to be executed
	 */
	public void executeCommand(int id, Command command) {
		getAppById(id).update(command);
	}
	
	/**
	 * Deletes an app based on id
	 * @param id id of application
	 */
	public void deleteAppById(int id) {
		appList.deleteAppById(id);
	}
	
	
	/**
	 * Adds application to Application List
	 * @param type type of application
	 * @param summary summary
	 * @param note note
	 */
	public void addAppToList(AppType type, String summary, String note) {
		appList.addApp(type, summary, note);
		
	}








}
