import junit.framework.TestCase;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class testRook extends testPiece {
  
  /**
   * A test method.
   * (Replace "X" with a name describing the test.  You may write as
   * many "testSomething" methods in this class as you wish, and each
   * one will be called when running JUnit over this class.)
   */
  
  private rook r = new rook(0,0,0);
  
  public void testValidMove() {
    
    for( int i = 1; i < 8; i++){
      assertTrue(r.validMove(i,0)); 
    }
    
    for( int i = 1; i < 8; i++){
      assertTrue(r.validMove(0,i)); 
    }
    
    //check diagonally
    for( int i = 1; i < 8; i++){
      assertFalse(r.validMove(i,i)); 
    }
    
    
  }
  
  public void testType(){
    assertEquals("Rook",r.getType());
  }
  
}
