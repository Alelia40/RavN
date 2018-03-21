public class piece{
  
  private int positionX;
  private int positionY;
  
  public piece(int x, int y){
    
    this.positionX = x;
    this.positionY = y;
    
  }
  
  public int getX(){
    return positionX;
  }
  
  public int getY(){
    return positionY;
  }
  
  public void setPosition(int x, int y){
    this.positionX = x;
    this.positionY = y;
  }
  
  public void take(piece p){
    //save position
    int x = p.getX();
    int y = p.getY();
    //remove piece
    p.setPosition(-1,-1);
    //place the capturing piece in the new space
    this.setPosition(x,y);
  }
  
  public void move(int x, int y){
    
  }
                   
  
  
  
  
  
  
  
}