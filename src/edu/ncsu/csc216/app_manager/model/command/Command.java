
package edu.ncsu.csc216.app_manager.model.command;

/**
 * Has information on user commands that would lead to a transition in state
 * @author Adith Venkatesh
 */
public class Command {
	
	

	/**A constant string for the Review Completed resolution.*/
	public static final String R_REVCOMPLETED = "ReviewCompleted";
	
	/**A constant string for the “Interview Completed resolution.*/
	public static final String R_INTCOMPLETED = "InterviewCompleted";
	
	/**A constant string for the Reference Check Completed resolution.*/
	public static final String R_REFCHKCOMPLETED = "ReferenceCheckCompleted";
	
	/**A constant string for the Offer Completed resolution.*/
	public static final String R_OFFERCOMPLETED = "OfferCompleted";
	
	/**Unity id of reviewer*/
	private String reviewerId;
	
	/**Note field*/
	private String note;
	
	/**Represents what a user can do with an application*/
	private CommandValue c;
	
	/**Represents how an application can be resolved*/
	private Resolution resolution; 
	
	/**
	 * Constructor for Command
	 * @param c CommandValue for command
	 * @param reviewerId reviewer id for command
	 * @param resolution resolution for command
	 * @param note note for command
	 * @throws IllegalArgumentException if there is no CommandValue
	 * @throws IllegalArgumentException if CommandValue is Accept but there is no reviewerId
	 * @throws IllegalArgumentException if CommandValue is Standby or Reject and there is no resolution
	 * @throws IllegalArgumentException if note is empty or null
	 */
	public Command(CommandValue c, String reviewerId, Resolution resolution, String note ) {
		if(c == null) {
			throw new IllegalArgumentException("Invalid information.");
		}
		this.c = c; 
		
		if( c == CommandValue.ACCEPT && (reviewerId == null || "".equals(reviewerId))){
			throw new IllegalArgumentException("Invalid information.");
		} 
		this.reviewerId = reviewerId;
		
		if((c == CommandValue.STANDBY || c == CommandValue.REJECT) && resolution == null) {
			throw new IllegalArgumentException("Invalid information.");

		}
		this.resolution = resolution;
		
		if(note == null || "".equals(note)){
			throw new IllegalArgumentException("Invalid information.");
		}
		this.note = note;
		
	}

	
	/**
	 * Ways that a user can interact with an application
	 * ACCEPT a
	 */
	public enum CommandValue { 
		/**ACCEPT command*/
		ACCEPT,
		
		/**REJECT command*/
		REJECT, 
		
		/**STANDBY command*/
		STANDBY, 
		
		/**REOPEN command*/
		REOPEN;
		
		
	}

	
	/**
	 * Ways that an application can be resolved
	 */
	public enum Resolution { 
		
		/**Review completed resolution*/
		REVCOMPLETED, 
		
		/**Interview completed resolution*/
		INTCOMPLETED, 
		
		/**Reference check completed resolution*/
		REFCHKCOMPLETED, 
		
		/**Offer completed resolution*/
		OFFERCOMPLETED;
		

	}

	/**
	 * Gets reviewer id
	 * @return the reviewerID
	 */
	public CommandValue getCommand() {
		return c;
	}
	
	
	/**
	 * Gets reviewer id
	 * @return the reviewerID
	 */
	public String getReviewerId() {
		return reviewerId;
	}


	/**
	 * Gets note field
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	
	/**
	 * Gets the Resolution
	 * @return the resolution
	 */
	public Resolution getResolution() {
		return resolution;
	}

	

}
