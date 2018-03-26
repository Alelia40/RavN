import javax.swing.*;
/**
 * Joe Pratt
 * 
 * creates instances of JButtons that appear as game squares
 */
public class tile extends JButton{
  
 private piece p;


  /**
   * creates a tile with no color
   */
  public tile(){
    
  }

  public piece getPiece(){
    return p;
  }
  
  public void setPiece(piece p){
    this.p = p;
  }
}