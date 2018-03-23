public class knight extends piece{
  
  public knight(int x, int y){
    super(x,y);
  }
  
  //all methods except for move are inherited, move behavior changed
  @Override
  public void move(int x, int y){
    System.out.println("placeholder");
    
  }
  
  //method to castle
  public void castle(){
    
  }
  
}