public class knight extends piece{
  
  public knight(int x, int y, int plr){
    super(x,y,plr);
    type = "Knight";
  }
  
  //method to get the validity of any position from the current position
  @Override
  public boolean validMove(int x, int y){
    int distA = Math.abs(this.getX() - x); 
    int distB = Math.abs(this.getY() - y);
    
    //as long as one distance field is equal to 2 and another is equal to 1, this is a valid knight move
    if(distA == 1 && distB == 2)
      return true;
    else if(distA ==2 && distB ==1)
      return true;
    else 
      return false;
  }
  
}