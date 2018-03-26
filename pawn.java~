
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
  
  //all methods from piece except for move are inherited, move behavior changed
  @Override
  public void move(int x, int y){
    
    //there are two types of pawn motion, traversal and attack, we need to account for both
    
    if (validMove(x,y) == false){
      System.out.println("This is not a correct move for this piece type");
    }
    else{
      hasMoved =true;
      super.move(x,y);
    }
    
  }
  
  //helper method to check for a valid move based on distances
  private boolean validMove(int x, int y){
    int distA = Math.abs(this.getX() - x); 
    int distB = (this.getY() - y);
    
    //we need to check for valid moves for both white and black pawns
    if( getPlayer() == 1){
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