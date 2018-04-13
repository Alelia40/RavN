import junit.framework.TestCase;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class testBoard extends TestCase {
  
  /**
   * A test method for checked getters and setters
   */
  public void testChecked() {
    Board x = new Board();
    
    assertFalse(x.getwhiteChecked());
    assertFalse(x.getblackChecked());
    
    x.setwhiteChecked(true);
    x.setblackChecked(true);
    
    assertTrue(x.getwhiteChecked());
    assertTrue(x.getblackChecked());
  }
  
  public void testgetTile(){
    Board x = new Board();
    
    tile t = x.getTiles()[0][0];
    assertNotNull(t);
    
  }
  
}
