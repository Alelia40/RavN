import javax.swing.*;
import java.awt.*;

public class Board extends JFrame{
  
  private JPanel board;
  
  private tile[][] tiles;
  
  public Board(){
    try {                                               //account for apple's graphics
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    }
    
    catch (Exception e) {
    }
    
    this.board = new JPanel(new GridLayout(8,8));
    this.tiles = new tile[8][8];     //creates as many tiles as there are spaces on the board
    Container c = this.getContentPane();                //formats the JFrame to have the grid of game tiles over the information of who's turn it is 
    c.add(this.board);      
    
    
    for (int index1 = 0 ; index1 < 8 ; index1++){
      for (int index2 = 0 ; index2 < 8 ; index2++){
        tiles[index2][index1] = new tile(true);
      }
    }
    
    this.setVisible(true);
    
 
  }
  
}