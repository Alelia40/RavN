import javax.swing.*;
/**
 * Joe Pratt
 * 
 * creates instances of JButtons that appear as game squares
 */
public class tile extends JButton{
  
 private piece p;

 private int x;
 
 private int y;

  /**
   * creates a tile with no color
   */
  public tile(int x, int y){
    this.x = x;
    this.y = y;
  }
  
  
  public int grabX(){
    return x;
  }
  
  public int grabY(){
    return y;
  }
  

  public piece getPiece(){
    return p;
  }
  
  public void setPiece(piece p){
    this.p = p;
  }
}