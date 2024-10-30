/**
 * 
 */
package edu.ncsu.csc216.app_manager.model.application;


import java.util.ArrayList;

import edu.ncsu.csc216.app_manager.model.command.Command;
import edu.ncsu.csc216.app_manager.model.command.Command.CommandValue;
import edu.ncsu.csc216.app_manager.model.command.Command.Resolution;

/**
 * Tracks all application information including the state
 * @author Adith Venkatesh
 */
public class Application {
	
	/**A constant string for New applications.*/
	public static final String A_NEW = "New";
	
	/**A constant string for Old applications.*/
	public static final String A_OLD = "Old";
	
	/**A constant string for Hired applications.*/
	public static final String A_HIRED = "Hired";
	
	/**A constant string for Closed applications.*/
	public static final String CLOSED_NAME = "Closed";
	
	/**A constant string for Interview state applications.*/
	public static final String INTERVIEW_NAME = "Interview";
	
	/**A constant string for Offer state applications.*/
	public static final String OFFER_NAME = "Offer";
	
	/**A constant string for Reference Check state applications.*/
	public static final String REFCHK_NAME = "RefCheck";
	
	/**A constant string for Review state applications.*/
	public static final String REVIEW_NAME = "Review";

	/**A constant string for Review state applications.*/
	public static final String WAITLIST_NAME = "Waitlist";
	
	/**Unique application id for an application.*/
	private int appId;
	
	/**Unique application id for an application.*/
	private AppType appType;
	
	/**Current state of an application id for an application.*/
	private AppState state;
	
	/**Current summary of application*/
	private String summary;
	
	/**User ID of the person reviewing the application*/
	private String reviewer;
	
	/**True when application paperwork is processed. Can only be true for old applications*/
	private boolean processPaperwork;
	
	/**ArrayList of notes*/
	private ArrayList<String> notes;
	
	/**Resolution of the application*/
	private Resolution resolution;
	
	/**Review State instance*/
	private final AppState reviewState = new ReviewState();
	
	/**Interview State instance*/
	private final AppState interviewState = new InterviewState();
	
	/**Waitlist State instance*/
	private final AppState  waitlistState = new WaitlistState();
	
	/**Reference Check State instance*/
	private final AppState refChkState = new RefChkState();
	
	/**Offer State instance*/
	private final AppState offerState = new OfferState();
	
	/**Closed State instance*/
	private final AppState closedState = new ClosedState();
	
	
	
	

	/**
	 * Constructor for Application with only id, apptype, summary and note
	 * @param id application id
	 * @param appType type of application
	 * @param summary summary
	 * @param note note to be added to ArrayList of notes
	 * @throws IllegalArgumentException if appType is null
	 */
	public Application(int id, AppType appType, String summary, String note) {
		setAppId(id);
		
		if(appType == null) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		
		this.appType = appType;
		setSummary(summary);
		notes = new ArrayList<String>();
		this.state = reviewState;
		addNote(note);
		this.reviewer = null;
		this.processPaperwork = false;
		this.resolution = null;
		
	}
	

	/**
	 * Constructor for Application with id, state, appType, summary, reviewer, confirmed, resolution, notes
	 * @param id id of application
	 * @param state application state
	 * @param appType type of application
	 * @param summary summary
	 * @param reviewer user id of person reviewing application
	 * @param processPaperwork boolean for whether a application is confirmed
	 * @param resolution resolution of application
	 * @param notes arraylist of notes
	 */
	public Application(int id, String state, String appType, String summary, String reviewer, boolean processPaperwork, String resolution, ArrayList<String> notes) {
		setAppId(id);
		setState(state);
		setAppType(appType);
		setSummary(summary);
		setReviewer(reviewer);
		setResolution(resolution);
		setProcessPaperwork(processPaperwork);
		setNotes(notes);
		
	}
	
	/**
	 * Interface for states in the Application State Pattern.  All 
	 * concrete application states must implement the AppState interface.
	 * The AppState interface should be a private interface of the 
	 * Application class.
	 * 
	 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu) 
	 * @author Dr. Chandrika Satyavolu (jsatyav@ncsu.edu)
	 */
	private interface AppState {
		
		/**
		 * Update the Application based on the given Command.
		 * @param command Command describing the action that will update the Application's state.
		 * @throws UnsupportedOperationException if the Command is not a valid action or reviewer is empty
		 */
		void updateState(Command command) throws UnsupportedOperationException;
		
		/**
		 * Returns the name of the current state as a String.
		 * @return the name of the current state as a String.
		 */
		String getStateName();

	} 
	
	/**
	 * Review state of the Application Manager FSM. 
	 */
	public class ReviewState implements AppState {
		
		
		/**
		 * Updates the state
		 * @param command command to update state
		 * @throws IllegalArgumentException if Standby command is issued on old application
		 * @throws UnsupportedOperationException if an invalid command is issued
		 */
		@Override
		public void updateState(Command command) {
			if(command.getCommand() == CommandValue.ACCEPT) { 
				
				/*if(command.getReviewerId() == null || "".equals(command.getReviewerId())) {
					throw new UnsupportedOperationException("Invalid information.");
				}*/
			
				setReviewer(command.getReviewerId());
				
				state = interviewState;
				appType = AppType.OLD;
				addNote(command.getNote());
			}
			
			else if(command.getCommand() == CommandValue.STANDBY) {
				if(appType == AppType.OLD) {
					throw new UnsupportedOperationException("Invalid information.");
				}
				
				setReviewer(command.getReviewerId());
				resolution = command.getResolution();
				state = waitlistState;
				addNote(command.getNote());
			}
			
			else if(command.getCommand() == CommandValue.REJECT) {
				setReviewer(command.getReviewerId());
				resolution = command.getResolution();
				state = closedState;
				addNote(command.getNote());
			}
			
			else {
				throw new UnsupportedOperationException("Invalid information.");
			}
			
			
			
		}

		/**
		 * Gets the state name
		 * @return state name
		 */
		@Override
		public String getStateName() {
			return REVIEW_NAME;
		}
		
	}
	
	/**
	 * Interview state of the Application Manager FSM.
	 */
	public class InterviewState implements AppState {
		/**
		 * Updates the state
		 * @param command command to update state
		 * @throws UnsupportedOperationException if the reviewerId is null
		 */
		@Override
		public void updateState(Command command){
		
		
			if(command.getCommand() == CommandValue.ACCEPT) {
				if(command.getReviewerId() == null || "".equals(reviewer)) {
					throw new UnsupportedOperationException("Invalid information.");
				}
		
				reviewer = command.getReviewerId();
				state = refChkState; 
				setProcessPaperwork(true);
				addNote(command.getNote());
			}
			
			else if(command.getCommand() == CommandValue.STANDBY) {
				reviewer = command.getReviewerId();
				state = waitlistState;
				setResolution("InterviewCompleted");
				addNote(command.getNote());
			}
			
			else if(command.getCommand() == CommandValue.REJECT) {
				appType = AppType.OLD;
				reviewer = command.getReviewerId();
				state = closedState;
				setResolution("InterviewCompleted");
				addNote(command.getNote());
			}
			
			else {
				throw new UnsupportedOperationException("Invalid information.");
			}
		}

		/**
		 * Gets the state name
		 */
		@Override
		public String getStateName() {
			return INTERVIEW_NAME;
		}
		
	}
	
	/**
	 * Waitlist state of the Application Manager FSM.
	 */
	public class WaitlistState implements AppState {

		/**
		 * Updates the state
		 * @param command command to update state
		 * @throws UnsupportedOperationException if an invalid command is issued
		 */
		@Override
		public void updateState(Command command){
			
			if("InterviewCompleted".equals(getResolution())) {
				setReviewer(command.getReviewerId());
				setProcessPaperwork(true);
				
				if(command.getCommand() == CommandValue.REOPEN){
					resolution = null;
					state = refChkState;
					addNote(command.getNote());
				}
				
				else {
					throw new UnsupportedOperationException("Invalid information.");
				}

			}
			
			
			else if("ReviewCompleted".equals(getResolution()) && "New".equals(getAppType()) && command.getCommand() == CommandValue.REOPEN) {
				resolution = null;
				setAppType(A_OLD);
				state = reviewState;
				addNote(command.getNote()); 
	
			}
			
			else {
				throw new UnsupportedOperationException("Invalid information.");
			}
			
			
		}

		/**
		 * Gets the state name
		 */
		@Override
		public String getStateName() {
			return WAITLIST_NAME;
		}
		
	}
	
	/**
	 * Reference check state of the Application Manager FSM.
	 */
	public class RefChkState implements AppState {

		/**
		 * Updates the state
		 * @param command command to update state
		 * @throws UnsupportedOperationException if an invalid command is issued
		 */
		@Override
		public void updateState(Command command){
	
			
			if(command.getCommand() == CommandValue.ACCEPT) {
				setReviewer(command.getReviewerId());
				setProcessPaperwork(true);
				state = offerState;
				addNote(command.getNote());
			}
			
			else if(command.getCommand() == CommandValue.REJECT) {
				state = closedState;
				setResolution("ReferenceCheckCompleted");
				setReviewer(command.getReviewerId());
				addNote(command.getNote());
			}
			
			else {
				throw new UnsupportedOperationException("Invalid information.");
			}
			
			
		}

		/**
		 * Gets the state name
		 */
		@Override
		public String getStateName() {
			return REFCHK_NAME;
		}
		
	}
	
	/**
	 * Offer state of the Application Manager FSM.
	 */
	public class OfferState implements AppState {
		
		/**
		 * Updates the state
		 * @param command command to update state
		 * @throws UnsupportedOperationException if an invalid command is issued
		 */
		@Override
		public void updateState(Command command) {
			
			if(command.getCommand() == CommandValue.ACCEPT) {
				reviewer = command.getReviewerId();
				setProcessPaperwork(true);
				setAppType("Hired");
				state = closedState;
				setResolution("OfferCompleted");
				addNote(command.getNote());
			}
			
			else if(command.getCommand() == CommandValue.REJECT) {
				reviewer = command.getReviewerId();
				resolution = command.getResolution();
				state = closedState;
				addNote(command.getNote());
			}
			
			else {
				throw new UnsupportedOperationException("Invalid information.");
			}
		}

		/**
		 * Gets the state name
		 */
		@Override
		public String getStateName() {
			
			return OFFER_NAME;
		}
		
	}
	
	/**
	 * Closed state of the Application Manager FSM.
	 */
	public class ClosedState implements AppState {

		/**
		 * Updates the state
		 * @param command command to update state
		 * @throws UnsupportedOperationException if an invalid command is issued
		 */
		@Override
		public void updateState(Command command) {

			
			if(command.getCommand() == CommandValue.REOPEN && getAppType().equals(A_NEW)) {
				//if(getAppType().equals(A_NEW)) {
				setAppType(A_OLD);
				state = reviewState;
				resolution = null;
				setReviewer(command.getReviewerId());
				addNote(command.getNote());
				//}
				
				
			}
			
			else if(command.getCommand() == CommandValue.REOPEN && getAppType().equals(A_OLD)) {
				throw new UnsupportedOperationException("Invalid information.");
			}
			
			else {
				throw new UnsupportedOperationException("Invalid information.");
			}
			
			
			
		}

		/**
		 * Gets the state name
		 */
		@Override
		public String getStateName() {
			
			return CLOSED_NAME;
		}
		
	}
	
	/**
	 * Contains application types: can be new, old, or hired
	 * 
	 */
	public enum AppType { 
		/**New Application type*/
		NEW,
		
		/**Old Application type*/
		OLD, 
		
		/**Hired application type*/
		HIRED 
		
	}




	/**
	 * Sets app id
	 * @param appId the appId to set
	 * @throws IllegalArgumentException if application id is less than 1
	 */
	private void setAppId(int appId) { 
		if(appId <= 0) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		
		this.appId = appId;
	}
	
	/**
	 * Sets the state
	 * @param s state to set
	 * @throws IllegalArgumentException if s is empty string or null
	 * @throws IllegalArgumentException if s is not one of the accepted states
	 */
	private void setState(String s) {
		
		boolean check = true;
		if(s == null || "".equals(s)) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		
		if(s.equals(REVIEW_NAME)) {
			this.state = reviewState;
			check = false;
		}
		
		if(s.equals(INTERVIEW_NAME)) {
			this.state = interviewState;
			check = false;
		}
		
		if(s.equals(WAITLIST_NAME)) {
			this.state = waitlistState;
			check = false;
		}
		
		if(s.equals(REFCHK_NAME)) {
			this.state = refChkState;
			check = false;
		}
		
		
		if(s.equals(OFFER_NAME)) {
			this.state = offerState;
			check = false;
		}
		
		if(s.equals(CLOSED_NAME)) {
			this.state = closedState;
			check = false;
		}
		
		if(check){
			throw new IllegalArgumentException("Application cannot be created.");
		}
		
		
		
		
	}
	
	/**
	 * Sets app type
	 * @param type the appType to set
	 * @throws IllegalArgumentException if appType is empty string or null
	 */
	private void setAppType(String type) { 
		boolean check = true;
		if(type == null || "".equals(type)) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		
		if("New".equals(type) && (state == interviewState || state == refChkState || state == offerState)) {
			throw new IllegalArgumentException("Application cannot be created.");

		}
		 
		if(type.equals(A_NEW)) {
			check = false;
			this.appType = AppType.NEW;
			
		}
		 
		if(type.equals(A_OLD)) {
			check = false;
			this.appType = AppType.OLD;
			
		}
		
		if(type.equals(A_HIRED)) {
			check = false;
			this.appType = AppType.HIRED;
			
		}
		
		if(check) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
	}
	
	/**
	 * Sets summary
	 * @param summary the summary to set
	 */
	private void setSummary(String summary) {
		if(summary == null || "".equals(summary)){
			throw new IllegalArgumentException("Application cannot be created.");
		}
		this.summary = summary;
	}
	
	/**
	 * Sets the reviewer
	 * @param reviewer the reviewer to set
	 * @throws IllegalArgumentException if state is Interview,Offer, or RefCheck and there is no reviewer
	 * @throws IllegalArgumentException if there is a reviewer in Review state
	 */
	public void setReviewer(String reviewer) {
		if( (reviewer == null || "".equals(reviewer)) 
				&& (getStateName().equals(INTERVIEW_NAME) || getStateName().equals(OFFER_NAME) || getStateName().equals(REFCHK_NAME)) ) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		
		
		/*if(getStateName().equals(REVIEW_NAME) && !(reviewer == null || ("").equals(reviewer)) ){
			throw new IllegalArgumentException("Application cannot be created.");

		}*/
		if("".equals(reviewer)) {
			this.reviewer = null;
		}
		
		else {
			this.reviewer = reviewer;
		}
		
	}
	
	/**
	 * Sets processPaperwork
	 * @param processPaperwork the processPaperwork to set
	 */
	public void setProcessPaperwork(boolean processPaperwork) {
		if( state == waitlistState && !processPaperwork && resolution == Resolution.INTCOMPLETED) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		
		if( state == reviewState && !processPaperwork && resolution == Resolution.REVCOMPLETED) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		
		if( (state == refChkState || state == offerState) && !processPaperwork) {
			throw new IllegalArgumentException("Application cannot be created.");

		}
		
		if(processPaperwork && state == interviewState) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		
		if(state == reviewState && processPaperwork) {
			throw new IllegalArgumentException("Application cannot be created.");

		}
		
		if("New".equals(getAppType()) && processPaperwork) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		 
		this.processPaperwork = processPaperwork;
	}

	 
	/**
	 * Sets the reviewer
	 * @param resolution resolution of the application
	 * @throws IllegalArgumentException if there is no resolution in the Waitlist or Closed state
	 */
	public void setResolution(String resolution) {
		//try {
			if( (this.state instanceof WaitlistState || this.state instanceof ClosedState) && (resolution == null || "".equals(resolution)))  {
				throw new IllegalArgumentException("Application cannot be created.");
			}
			
			if(resolution == null) {
				this.resolution = null;
			} 
			
			else {
				if(resolution.equals(Command.R_REVCOMPLETED)) {
					this.resolution = Resolution.REVCOMPLETED;
				}
				
				if(resolution.equals(Command.R_INTCOMPLETED)) {
					this.resolution = Resolution.INTCOMPLETED;
				}
				
				if(resolution.equals(Command.R_REFCHKCOMPLETED)) { 
					this.resolution = Resolution.REFCHKCOMPLETED;
				}
				
				if(resolution.equals(Command.R_OFFERCOMPLETED)) {
					this.resolution = Resolution.OFFERCOMPLETED;
				}
				
				if(state == reviewState && resolution.equals(Command.R_REVCOMPLETED)) {
					throw new IllegalArgumentException("Application cannot be created.");

				}
				
				if(state == interviewState && (resolution.equals(Command.R_INTCOMPLETED) || resolution.equals(Command.R_REVCOMPLETED)) ) {
					throw new IllegalArgumentException("Application cannot be created.");

				}
				
				if(state == offerState && resolution.equals(Command.R_OFFERCOMPLETED)) {
					throw new IllegalArgumentException("Application cannot be created.");

				}
				
				if(state == refChkState &&  resolution.equals(Command.R_REFCHKCOMPLETED)) {
					throw new IllegalArgumentException("Application cannot be created.");

				}
				
				if(state == waitlistState && !(resolution.equals(Command.R_REVCOMPLETED) || resolution.equals(Command.R_INTCOMPLETED))) {
					throw new IllegalArgumentException("Application cannot be created.");
				}
				
				if(resolution.equals(Command.R_INTCOMPLETED) && "New".equals(getAppType())) {
					throw new IllegalArgumentException("Application cannot be created.");

				}
			
			
			}
		//}
		
		/*catch(NullPointerException e) {
			//Do nothing 
		}*/
		
	}
	

	/**
	 * Sets the notes arraylist
	 * @param notes the notes to set
	 */
	public void setNotes(ArrayList<String> notes) {
		if(notes == null) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if(notes.isEmpty()) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		this.notes = notes;
	}

	
	
	/**
	 * Gets app id
	 * @return the appId
	 */
	public int getAppId() {
		return appId;
	}
	
	
	/**
	 * Gives state of the application
	 * @return the state
	 */
	public String getStateName() {
		
		if(state instanceof ReviewState) {
			return REVIEW_NAME;
		}
		
		if(state instanceof InterviewState) {
			return INTERVIEW_NAME;
		}
		
		if(state instanceof WaitlistState) {
			return WAITLIST_NAME;
		}
		
		if(state instanceof RefChkState) {
			return REFCHK_NAME;
		}
		
		if(state instanceof OfferState) {
			return OFFER_NAME;
		}
		
		if(state instanceof ClosedState) {
			return CLOSED_NAME;
		}
		
		
		return null;
	}

	

	/**
	 * Gets AppType
	 * @return the appType
	 */
	public String getAppType() {
		
		if(appType == AppType.NEW) {
			return A_NEW;
		}
		
		if(appType == AppType.OLD) {
			return A_OLD;
		}
		
		if(appType == AppType.HIRED) {
			return A_HIRED;
		}
		
		return null;
	}


	/**
	 * Gets summary
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * Gets reviewer
	 * @return the reviewer
	 */
	public String getReviewer() {
		return reviewer;
	}

	/**
	 * Getter for processedPaperwork
	 * @return boolean value
	 */
	public boolean isProcessed() {
		return processPaperwork;
	}

	/**
	 * Gets resolution
	 * @return the reviewer
	 */
	public String getResolution() {
		if(resolution == Resolution.INTCOMPLETED) {
			return Command.R_INTCOMPLETED; 
		}
		
		if(resolution == Resolution.OFFERCOMPLETED) {
			return Command.R_OFFERCOMPLETED;
		}
		
		if(resolution == Resolution.REFCHKCOMPLETED) {
			return Command.R_REFCHKCOMPLETED;
		}
		
		if(resolution == Resolution.REVCOMPLETED) {
			return Command.R_REVCOMPLETED;
		}
		
		return null;
	}


	/**
	 * gets arraylist of notes
	 * @return the notes
	 */
	public ArrayList<String> getNotes() {
		return notes;
	}
	
	/**
	 * gets notes
	 * @return string of notes
	 */
	public String getNotesString() {
		String answer = "";
		for(int i = 0; i < notes.size(); i++) {
			//System.out.println("Notes value: " + notes.get(i));
			//System.out.println("answer before: " + answer);
			answer = answer + "-" + notes.get(i) + "\n";
			//System.out.println("answer after: " + answer);
		}
		return answer;
	}


	/**
	 * Gets information about application in file reader format
	 * @return String with all valid information
	 */
	@Override
	public String toString() {
		/*return "Application [appId=" + appId + ", state=" + state + ", summary=" + summary + ", reviewer=" + reviewer
				+ ", processPaperwork=" + processPaperwork + ", notes=" + notes + ", resolution=" + resolution
				+ ", appType=" + appType + "]";*/
		
		
		String answer = "";
		String tempResolution = "";
		//make temp value and assign to empty string
		if(getResolution() != null) {
			tempResolution = getResolution();
		}
		answer = answer + "*" + getAppId() + "," + getStateName() + "," + getAppType() + "," + getSummary() + "," + getReviewer() + "," + isProcessed() + "," + tempResolution + "\n" + getNotesString();
		return answer;
	}
	
	/**
	 * adds a note to the notes 
	 * @param note note to be added
	 */
	public void addNote(String note) {
		String answer = ""; 
		if(note == null || "".equals(note)) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		
		answer = "[" + getStateName() + "] " + note; 
		notes.add(answer);
	}
	
	/**
	 * Updates the command
	 * @param command command to change to
	 */
	public void update(Command command) throws UnsupportedOperationException{
		state.updateState(command);
	}
	
	

}
