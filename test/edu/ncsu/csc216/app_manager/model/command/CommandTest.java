
package edu.ncsu.csc216.app_manager.model.command;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.app_manager.model.command.Command.CommandValue;
import edu.ncsu.csc216.app_manager.model.command.Command.Resolution;

 
/**
 * Tests Command
 * @author Adith Venkatesh
 */
class CommandTest {

	/**Default test*/
	@Test
	public void testCommandConstructor() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> new Command(null, "avenka29", null, "note" ));
		assertEquals("Invalid information.", e.getMessage());
		
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> new Command(CommandValue.ACCEPT, null, null, "note" ));
		assertEquals("Invalid information.", e1.getMessage());
		
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> new Command(CommandValue.ACCEPT, "", null, "note" ));
		assertEquals("Invalid information.", e2.getMessage());
		
		Exception e3 = assertThrows(IllegalArgumentException.class, () -> new Command(CommandValue.STANDBY, "avenka29", null, "note" ));
		assertEquals("Invalid information.", e3.getMessage());
		
		Exception e4 = assertThrows(IllegalArgumentException.class, () -> new Command(CommandValue.REJECT, "avenka29", null, "note" ));
		assertEquals("Invalid information.", e4.getMessage());
		
		/*Exception e5 = assertThrows(IllegalArgumentException.class, () -> new Command(CommandValue.STANDBY, "avenka29", Resolution.INTCOMPLETED, null ));
		assertEquals("Invalid information.", e.getMessage());*/
		
		Exception e6 = assertThrows(IllegalArgumentException.class, () -> new Command(CommandValue.STANDBY, "avenka29", Resolution.INTCOMPLETED, "" ));
		assertEquals("Invalid information.", e6.getMessage());
		
		
		//Test setters and getters if needed
	}
	
	@Test
	public void testGetters() {
		Command test = new Command(CommandValue.ACCEPT, "avenka29", null, "note" );
		Command test2 = new Command(CommandValue.ACCEPT, "avenka29", Resolution.INTCOMPLETED, "note" );
		
		//test command value
		assertEquals(CommandValue.ACCEPT, test.getCommand());
		
		//test get reviewer
		assertEquals("avenka29", test.getReviewerId());
		
		//test resolution
		assertEquals(Resolution.INTCOMPLETED, test2.getResolution());
		
		//test note
		assertEquals("note", test.getNote());

	}

}
