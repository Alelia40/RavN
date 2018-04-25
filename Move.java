import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Move{
  
  private piece pieceLastMoved;
  
  private int pieceLastMovedFromX;
  
  private int pieceLastMovedFromY;
  
  private piece pieceTakenLastTurn;
  
  private Icon takenPieceIcon;
  
  private Icon movedPieceIcon;
  
  public void setPieceLastMoved(piece pieceLastMoved){
    this.pieceLastMoved = pieceLastMoved;
  }
  
  public void setPieceLastMovedFromX(int x){
    this.pieceLastMovedFromX =  x;
  }
  
  public void setPieceLastMovedFromY(int y){
    this.pieceLastMovedFromY = y;
  }
  
  public void setPieceTakenLastTurn(piece pieceTakenLastTurn){
    this.pieceTakenLastTurn = pieceTakenLastTurn;
  }
  
  public void setTakenPieceIcon(Icon takenPieceIcon){
    this.takenPieceIcon = takenPieceIcon;
  }
  
  public void setMovedPieceIcon(Icon movedPieceIcon){
    this.movedPieceIcon = movedPieceIcon;
  }
  
  public piece getPieceLastMoved(){
    return pieceLastMoved;
  }
  
  public int getPieceLastMovedFromX(){
    return pieceLastMovedFromX;
  }
  
  public int getPieceLastMovedFromY(){
    return pieceLastMovedFromY;
  }
  
  public piece getPieceTakenLastTurn(){
    return pieceTakenLastTurn;
  }
  
  public Icon getTakenPieceIcon(){
    return takenPieceIcon;
  }
  
  public Icon getMovedPieceIcon(){
    return movedPieceIcon;
  }
  
}