/**
 * a class that represents a toolbar with fast forward, and undo buttons and a display of whose move it is 
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class actionMenu extends JToolBar{
  
   private JButton undoButton;
   private JButton forwardButton;
   private static JTextArea playerMove;
  
  public actionMenu(){
    
    undoButton = new JButton("Undo Move");  
     forwardButton = new JButton("Fast Forward");
     playerMove = new JTextArea();
     this.add(playerMove);
     playerMove.setText("White's Move");
     this.add(undoButton);
     this.add(forwardButton);
     this.addSeparator();
     this.setBackground(new Color(207,220,227));
    
  }
  
  public JButton getundoButton(){
    return this.undoButton;
  }
  
   public JButton getforwardButton(){
    return this.forwardButton;
  }
  
  public static void setMoveText(String s){
    playerMove.setText(s);
  }
  
}