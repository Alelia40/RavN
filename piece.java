public class Piece{
  
  private int positionX;
  private int positionY;
  
  public int getX(){
    return positionX;
  }
  
  public int getY(){
    return positionY;
  }
  
  public int setPosition(int x, int y){
    this.positionX = x;
    this.positionY = y;
  }
  
  public void take(Piece p){
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