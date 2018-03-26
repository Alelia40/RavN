
public class pawn extends piece{
  
  private boolean hasMoved;
  
  public pawn(int x, int y, int plr){
    super(x,y,plr);
    hasMoved = false;
  }
  
  //method to check if the pawn has been moved
   public boolean getMoved(){
    return hasMoved;
  }
  
   public void setMoved(){
     this.hasMoved = true;
   }
  
  
  //method to check for a valid traaversal move for the pawn
  @Override
  public boolean validMove(int x, int y){
    int distA = Math.abs(this.getX() - x); 
    int distB = (this.getY() - y);
    
    //we need to check for valid moves for both white and black pawns
    if( getPlayer() == 0){
      //if the space is only minus one space away vertically then this is a correct traversal move
      if(distB == -1 && distA ==0 )
        return true;
      //allow for a double jump forward on first pawn move
      else if( getMoved() == false && distB == -2 && distA ==0)
        return true;
      else
        return false;
    }
    else{
      //if the space is only plus one space away vertically then this is a correct traversal move
      if(distB == 1 && distA ==0 )
        return true;
      //allow for a double jump forward on first pawn move
      else if( getMoved() == false && distB == 2 && distA ==0)
        return true;
      else
        return false;
    }
    
  }
  
  
  
  
  
  
}