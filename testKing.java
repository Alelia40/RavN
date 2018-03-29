import junit.framework.TestCase;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class testKing extends testPiece {
  
  /**
   * A test method.
   * (Replace "X" with a name describing the test.  You may write as
   * many "testSomething" methods in this class as you wish, and each
   * one will be called when running JUnit over this class.)
   */
  
  private king k = new king(1,5,0);
  
  public void testValidMove() {
    
    //check hporizontal motion
    assertTrue(k.validMove(1,4)); 
    assertTrue(k.validMove(1,6));
    
    assertFalse(k.validMove(1,7)); 
    assertFalse(k.validMove(1,3)); 
    
    //check vertical motion
    assertTrue(k.validMove(2,5));
    assertTrue(k.validMove(0,5));
    
    assertFalse(k.validMove(3,5));
    assertFalse(k.validMove(4,5));
    
    //check diagonal motion
    assertTrue(k.validMove(2,6));
    assertTrue(k.validMove(2,4));
    assertTrue(k.validMove(0,6));
    assertTrue(k.validMove(0,4));
    
    assertFalse(k.validMove(3,7));
    assertFalse(k.validMove(3,5));
    assertFalse(k.validMove(4,8));
    assertFalse(k.validMove(4,4));
    
    
  }
  
  public void testType(){
    assertEquals("King",k.getType());
  }
  
}