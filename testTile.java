import junit.framework.TestCase;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class testTile extends TestCase {
  
  /**
   * A test method for piece values in the tile
   */
  public void testPieceVal() {
    tile t = new tile(0,0);
    
    //should have no piece to start
    assertNull(t.getPiece());
    
    rook r = new rook(0,0,1);
    t.setPiece(r);
    assertNotNull(t.getPiece());
    assertSame(t.getPiece(), r);
    
  }
  
  public void testLocation(){
    tile t = new tile(0,0);
    
    assertEquals(0, t.grabX());
    assertEquals(0, t.grabY());
  }
}
