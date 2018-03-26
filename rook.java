public class rook extends piece{
  
  private Boolean hasMoved;
  
  public rook(int x, int y, int plr){
    super(x,y, plr);
    hasMoved = false;
    type = "Rook";
  }
  
  //method to check if the rook has been moved
  public boolean getMoved(){
    return hasMoved;
  }
  
  /**
   * Method to identify if the piece has moved
   */
  public void setMoved(){
    this.hasMoved = true;
  }
  
  //helper method to check for valid move by using distance
  @Override
  public boolean validMove(int x, int y){
    int distA = Math.abs(this.getX() - x); 
    int distB = Math.abs(this.getY() - y);
    
    //if the destination is only horizontally distant it is a valid move
    if(distA == 0 && distB > 0)
      return true;
    //if the destination is only vertically distant it is a valid move
    else if(distB ==0 && distA > 0)
      return true;
    else
      return false;
  }
  
}