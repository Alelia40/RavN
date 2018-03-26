public class bishop extends piece{
  
  public bishop(int x, int y, int plr){
    super(x,y,plr);
  }
  
  //all methods from piece except for move are inherited, move behavior changed
  @Override
  public void move(int x, int y){
    
    if(validMove(x,y) == false){
      System.out.println("This is not a correct move for this piece type");
    }
    else{
       super.move(x,y);
    }
    
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