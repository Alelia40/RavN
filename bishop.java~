public class bishop extends piece{
  
  public bishop(int x, int y, int plr){
    super(x,y,plr);
  }
  
  //helper method to get the distance of any position from the current position
  @Override
  public boolean validMove(int x, int y){
    int distA = Math.abs(this.getX() - x); 
    int distB = Math.abs(this.getY() - y);
    
    //if the destination has an equal distance in terms of x and y axes from the origin, then it is diagonal and a valid move
    if(distA == distB)
      return true;
    else
      return false;
  }
}