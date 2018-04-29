import junit.framework.TestCase;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class testPawn extends testPiece {
  
  /**
   * A test method.
   * (Replace "X" with a name describing the test.  You may write as
   * many "testSomething" methods in this class as you wish, and each
   * one will be called when running JUnit over this class.)
   */
  
  private pawn p = new pawn(1,7,0);
  private pawn p2 = new pawn(2,2,1);
  
  public void testValidMove() {
    
    //check vertical motion
    
    assertTrue(p.validMove(1,6));
    assertTrue(p.validMove(1,5));
    
    assertTrue(p2.validMove(2,3));
    assertTrue(p2.validMove(2,4));
    
    p.setMoved(p.getMoveCount()+1);
    p2.setMoved(p2.getMoveCount()+1);
    
    assertTrue(p.validMove(1,6));
    assertFalse(p.validMove(1,5));
    
    assertTrue(p2.validMove(2,3));
    assertFalse(p2.validMove(2,4));
    
  }
  
  public void testType(){
    assertEquals("Pawn",p.getType());
  }
  
}