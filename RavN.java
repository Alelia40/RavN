import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math;

public class RavN extends JFrame{
  
  private Board chessBoard;
  
  private JToolBar actionMenu;
  
  public RavN(){
    this.setSize(1000,1000);
    
    //add in the chessboard
    chessBoard = new Board();
    this.getContentPane().add(chessBoard,BorderLayout.CENTER);
    
    //add the action menu
    actionMenu = new JToolBar();
    
     actionMenu.setRollover(true);  
     JButton undoButton = new JButton("Undo Move");  
     actionMenu.add(undoButton);
     undoButton.addActionListener(new ActionListener() {
          /**
           * What to do when the undo button is pressed
           */
          public void actionPerformed(ActionEvent e){
            chessBoard.unduMoveButton();
          }
     });
     
     actionMenu.addSeparator();
     actionMenu.setBackground(new Color(207,220,227));
     
     this.getContentPane().add(actionMenu, BorderLayout.SOUTH);
     
     this.setVisible(true);
  }
  
  public static void main(String[] args){
    
    RavN game = new RavN();
    
  }
  
  
  
}