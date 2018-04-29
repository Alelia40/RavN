import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math;

public class RavN extends JFrame{
  
  private Board chessBoard;
  
  private actionMenu actionBar;
  
  public RavN(){
    this.setSize(1000,1000);
    
    //add in the chessboard
    chessBoard = new Board();
    this.getContentPane().add(chessBoard,BorderLayout.CENTER);
    
    //add the action menu
     actionBar = new actionMenu(); 
     actionBar.getundoButton().addActionListener(new ActionListener() {
          /**
           * What to do when the undo button is pressed
           */
          public void actionPerformed(ActionEvent e){
            chessBoard.unduMoveButton();
          }
     });
     
     actionBar.getforwardButton().addActionListener(new ActionListener() {
          /**
           * What to do when the undo button is pressed
           */
          public void actionPerformed(ActionEvent e){
            chessBoard.forwardButton();
          }
     });
     
     this.getContentPane().add(actionBar, BorderLayout.SOUTH);
     
     this.setVisible(true);
  }
  
  public static void main(String[] args){
    
    RavN game = new RavN();
    
  }
  
  
  
}