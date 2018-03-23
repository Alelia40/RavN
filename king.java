public class king extends piece{
  
  private Boolean hasMoved;
  
  public king(int x, int y){
    super(x,y);
  }
 
  //method to check if the king has been moved
   public Boolean getMoved(){
    return hasMoved;
  }
  
  //all methods except for move are inherited, move behavior changed
  @Override
  public void move(int x, int y){
    System.out.println("placeholder");
    
  }
  
  
}