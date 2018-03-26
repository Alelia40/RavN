
//import math functions, which will be passed to subclasses for motion behavior
import java.lang.Math;

public class piece{
  
  //field for player, 0 is white, 1 is black
  public int player;
  //position fields for x and y locations
  public int positionX;
  public int positionY;
  //keeps track of type of piece
  public String type;
  
  //constructor for piece, takes position arguments and a player argument
  public piece(int x, int y, int plr){
    
    this.player = plr;
    
    this.positionX = x;
    this.positionY = y;
    
  }
  
  /**
   * method to get the type of piece
   */
  public String getType(){
    return type;
  }
  
  
  /**
   * method to return the player attached to a piece
   */
  public int getPlayer(){
    return player;
  }
  
  //method to get the piece's x position
  public int getX(){
    return positionX;
  }
  
  //method to get the piece's y position
  public int getY(){
    return positionY;
  }
  
  //method to set the piece's position
  public void setPosition(int x, int y){
    this.positionX = x;
    this.positionY = y;
  }
  
  //method to "take" another piece object by replacing it with this piece
  public void take(piece p){
    //save position
    int x = p.getX();
    int y = p.getY();
    
    //if the piece is not a fiendly allow the motion
    if(p.getPlayer() != this.getPlayer()){
      //remove piece
      p.setPosition(-1,-1);
      //place the capturing piece in the new space
      this.setPosition(x,y);
    }
    else{
      //perform some error behavior
      System.out.println("Friendly Fire! Don't attack your own Pieces");
    }
  }
  
  /**
   * method to check if a given move is a valid move for the pieces
   */
  public boolean validMove(int x, int y){
    return false;
    //will be overrided
  }
  
 
  
}