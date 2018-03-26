import javax.swing.*;
import java.awt.*;

public class Board extends JFrame{
  
  
  private tile[][] tiles; //jbuttons for the UI
  
  public Board(){
    try {                                               //account for apple's graphics
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    }
    
    catch (Exception e) {
    }
    
    Container c = this.getContentPane();                //formats the JFrame to have the grid of game tiles over the information of who's turn it is
    this.setSize(800,800);
    this.tiles = new tile[8][8];     //creates as many tiles as there are spaces on the board 
    c.setLayout(new GridLayout(8,8)); //create a gridlayout on the container     
    
    int count = 1;
    for (int index1 = 0 ; index1 < 8 ; index1++){
      for (int index2 = 0 ; index2 < 8 ; index2++){
        tiles[index2][index1] = new tile();
        if((index1+index2)%2 == 1)
          tiles[index2][index1].setBackground(new Color(176,160,77));
        else
          tiles[index2][index1].setBackground(new Color(90,77,14));
        c.add(tiles[index2][index1]);
      }
    }
      
      
        for(int i = 0; i < 8; i++){       //make white pawns
          tiles[i][1].setPiece(new pawn(i,1,0));
        }      

        for(int i = 0; i < 8; i++){       //make black pawns
          tiles[i][6].setPiece(new pawn(i,6,1));
        }     

        tiles[0][0].setPiece(new rook(0,0,0));             //make rooks
        tiles[7][0].setPiece(new rook(7,0,0));
        tiles[0][7].setPiece(new rook(0,7,1));
        tiles[7][7].setPiece(new rook(7,7,1));
                              
        tiles[1][0].setPiece(new knight(1,0,0));           //make knights
        tiles[6][0].setPiece(new knight(6,0,0));
        tiles[1][7].setPiece(new knight(1,7,1));
        tiles[6][7].setPiece(new knight(6,7,1));
        
        tiles[2][0].setPiece(new bishop(2,0,0));           // make bishops
        tiles[5][0].setPiece(new bishop(5,0,0));
        tiles[2][7].setPiece(new bishop(2,7,1));
        tiles[5][7].setPiece(new bishop(5,7,1));
        
        tiles[3][0].setPiece(new king(3,0,0));             //make kings
        tiles[3][7].setPiece(new king(3,7,1));
        
        tiles[4][0].setPiece(new queen(4,0,0));            //make queens
        tiles[4][7].setPiece(new queen(4,7,1));
        
                              
      this.setVisible(true);
      
  }
  
  /**
   * Method that returns the tiles on the board
   */
  public tile[][] getTiles(){
    return tiles;
  }
  
  /**
   * Moves a piece if the move is valid
   */
  public void move( int x, int y, piece p){
    if(p.validMove(x , y)){            //case of valid move
      getTiles()[p.getX()][p.getY()].setPiece(null);
      getTiles()[x][y].setPiece(p);
      
    }
     
    
  }
  
  
  /**
   * Method that checks if a given tile is occupied by a piece
   */
  public boolean isOccupied(int x, int y){
    if(this.getTiles()[x][y].getPiece() == null){
      return false;
    }
    else{
      return true;
    }
  }
  
}