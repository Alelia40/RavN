public class queen extends piece{
  
  public queen (int x, int y, int plr){
    super(x,y, plr);
    type = "Queen";
  }
  
  //helper method to check for valid move by using distance
  @Override
  public boolean validMove(int x, int y){
    int distA = Math.abs(this.getX() - x); 
    int distB = Math.abs(this.getY() - y);
    
    //if the destination has an equal distance in terms of x and y axes, then it is diagonal from the origin and a valid move
    if(distA == distB)
      return true;
    //if the destination is only horizontally distant it is a valid move
    else if(distA == 0 && distB > 0)
      return true;
    //if the destination is only vertically distant it is a valid move
    else if(distB ==0 && distA > 0)
      return true;
    else
      return false;
  }
 
}