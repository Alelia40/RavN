import javax.swing.*;
import java.awt.*;

public class Board extends JFrame{
  
  
  private tile[][] tiles; //jbuttons for the UI
  
  private piece[][] pieceArr = new piece[8][8]; //representation of the pieces on the board
  
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
      
      this.setVisible(true);
      
      
    }
  }
}