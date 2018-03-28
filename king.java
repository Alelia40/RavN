import java.lang.Math;
public class king extends piece{
  

  
  public king(int x, int y, int plr){
    super(x,y, plr);
    
    type = "King";
  }
 

  
  //helper method to get the distance of any position from the current position
  @Override
  public boolean validMove(int x, int y){
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