import junit.framework.TestCase;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class testBishop extends testPiece {
  
  /**
   * A test method.
   * (Replace "X" with a name describing the test.  You may write as
   * many "testSomething" methods in this class as you wish, and each
   * one will be called when running JUnit over this class.)
   */
  
  private bishop b = new bishop(0,0,0);
  
  public void testValidMove() {
    
    for( int i = 1; i < 8; i++){
      assertFalse(b.validMove(i,0)); 
    }
    
    for( int i = 1; i < 8; i++){
      assertFalse(b.validMove(0,i)); 
    }
    
    //check diagonally
    for( int i = 1; i < 8; i++){
      assertTrue(b.validMove(i,i)); 
    }
    
    
  }
  
  public void testType(){
    assertEquals("Bishop",b.getType());
  }
  
}