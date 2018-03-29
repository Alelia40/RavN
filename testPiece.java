import junit.framework.TestCase;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class testPiece extends TestCase {
  
  private piece p = new piece(1,1,0);

  public void testGetPlayer() {
    assertEquals(0,p.getPlayer());
  }
  

   public void testMoved() {
     
     assertFalse(p.getMoved());
     
     p.setMoved();
     
     assertTrue(p.getMoved());
     
  }
  
   public void testPositionMethods(){
     assertEquals(1,p.getX());
     assertEquals(1,p.getY());
     
     p.setPosition(2, 2);
    
     assertEquals(2,p.getX());
     assertEquals(2,p.getY());
     
   }
   
}
