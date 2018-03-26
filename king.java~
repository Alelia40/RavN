import java.lang.Math;
public class king extends piece{
  
  private Boolean hasMoved;
  
  public king(int x, int y, int plr){
    super(x,y, plr);
    hasMoved = false;
  }
 
  //method to check if the king has been moved
   public boolean getMoved(){
    return hasMoved;
  }
  
  //all methods from piece except for move are inherited, move behavior changed
  @Override
  public void move(int x, int y){
    hasMoved = true;
    
    //we need something to check for
    
    //if the destination is one space away in any direction then the move is valid
    if(validMove(x,y) == false){
      System.out.println("This is not a correct move for the king");
    }
    else{
    super.move(x,y);
    }
    
  }
  
  //helper method to get the distance of any position from the current position
  private boolean validMove(int x, int y){
    int distA = Math.abs(this.getX() - x); 
    int distB = Math.abs(this.getY() - y);
    
    //as long as both distance fields are less than or equal to 1, any combination would amount to a valid king move
    if(distA > 1 || distB > 1)
      return false;
    else
      return true;
  }
  
  //method to castle
  public void castle(){
    
  }
  
  
}