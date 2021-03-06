import junit.framework.TestCase;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class testKnight extends testPiece {
  
  /**
   * A test method.
   * (Replace "X" with a name describing the test.  You may write as
   * many "testSomething" methods in this class as you wish, and each
   * one will be called when running JUnit over this class.)
   */
  
  private knight k = new knight(1,0,0);
  
  public void testValidMove() {
    
    //check all spaces
    
    for(int i = 0; i < 8; i ++){
      for( int j = 0; j < 8; j++){
        
        if( i==2 && j ==2)
          assertTrue(k.validMove(i,j));
        else if(i==0 && j ==2)
          assertTrue(k.validMove(i,j));
        else if( i==3 && j == 1)
          assertTrue(k.validMove(i,j));
        else
          assertFalse(k.validMove(i,j));
        
        
      }
    }
    
    
  }
  
  public void testType(){
    assertEquals("Knight",k.getType());
  }
  
}