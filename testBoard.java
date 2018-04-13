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
  
  Board x = new Board();
  
  public void testChecked() {
    
    assertFalse(x.getwhiteChecked());
    assertFalse(x.getblackChecked());
    
    x.setwhiteChecked(true);
    x.setblackChecked(true);
    
    assertTrue(x.getwhiteChecked());
    assertTrue(x.getblackChecked());
  }
  
  public void testgetTile(){
    
    tile t = x.getTiles()[0][0];
    assertNotNull(t);
    
  }
  
  public void testisOccupied(){
    
    for( int j = 0; j < 8; j ++){
      for( int i = 0; i < 8; i++){
        
        if( j == 0)
          assertTrue(x.isOccupied(i,j));
        else if (j ==1)
          assertTrue(x.isOccupied(i,j));
        else if (j ==6)
          assertTrue(x.isOccupied(i,j));
        else if (j ==7)
          assertTrue(x.isOccupied(i,j));
        else
          assertFalse(x.isOccupied(i,j));
      }
    }
    
  }
  
  public void testLegalCastle(){
    
    //at start none should be legal
    
    tile p[][] = x.getTiles();
    
    assertFalse(x.legalCastle((rook)p[0][0].getPiece(),(king) p[4][0].getPiece()));
    assertFalse(x.legalCastle((rook)p[0][0].getPiece(),(king) p[4][7].getPiece()));
    
    assertFalse(x.legalCastle((rook)p[0][7].getPiece(),(king) p[4][0].getPiece()));
    assertFalse(x.legalCastle((rook)p[0][7].getPiece(),(king) p[4][7].getPiece()));
    
    assertFalse(x.legalCastle((rook)p[7][7].getPiece(),(king) p[4][0].getPiece()));
    assertFalse(x.legalCastle((rook)p[7][7].getPiece(),(king) p[4][7].getPiece()));
    
    assertFalse(x.legalCastle((rook)p[7][0].getPiece(),(king) p[4][0].getPiece()));
    assertFalse(x.legalCastle((rook)p[7][0].getPiece(),(king) p[4][7].getPiece()));
    
    //it's implausible to do the rest in JUnit, we can perform this in functional testing
    
  }
  
  
  
}
