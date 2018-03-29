import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math;

public class Board extends JFrame{
  
  
  private tile[][] tiles; //jbuttons for the UI
  
  private tile init;  //initial button pressed with piece
  
  private int whoseMove = 0;
  
  private boolean whiteChecked = false;
  private boolean blackChecked = false;
  
  public static void main(String[] args){
    Board b = new Board();
  }
  
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
        tiles[index2][index1] = new tile(index2,index1);
        tiles[index2][index1].addActionListener(new ActionListener() {
          /**
           * What to do when a button is pressed
           */
          public void actionPerformed(ActionEvent e){
            tile t = (tile)e.getSource();        //this is the tile that was pressed
            boolean isMoved = false;             //Serves as a flag so you don't move a piece and then reassign it as init
            if(getInit() != null && t.getPiece() == null){ //Case: Move to open space
              move(t.grabX(),t.grabY(),getInit().getPiece()); //moves a piece
              isMoved = true;
              setInit(null);                                //forgets the piece that was moved so another can be chosen
            } 
            else if(getInit() != null && t.getPiece().getPlayer() != getInit().getPiece().getPlayer()){   //Case: Move onto enemy piece
              move(t.grabX(),t.grabY(),getInit().getPiece());
              isMoved = true;
              setInit(null);
            }
            if(t.getPiece() != null && getInit() == null && isMoved == false){  //Case: Select Piece to move
              setInit(t);                                //sets the piece to move
            }
          }
        });
        if((index1+index2)%2 == 1)
          tiles[index2][index1].setBackground(new Color(90,77,14));
        else
          tiles[index2][index1].setBackground(new Color(176,160,77));
        c.add(tiles[index2][index1]);
      }
    }
    
    
    for(int i = 0; i < 8; i++){                   //make black pawns
      tiles[i][1].setPiece(new pawn(i,1,1));
      tiles[i][1].setText("Black Pawn");
    }      
    
    for(int i = 0; i < 8; i++){                     //make white pawns
      tiles[i][6].setPiece(new pawn(i,6,0));
      tiles[i][6].setText("White Pawn");
    }     
    
    tiles[0][0].setPiece(new rook(0,0,1));         //make rooks
    tiles[7][0].setPiece(new rook(7,0,1));
    tiles[0][7].setPiece(new rook(0,7,0));
    tiles[7][7].setPiece(new rook(7,7,0));
    
    tiles[0][0].setText("Black Rook");
    tiles[7][0].setText("Black Rook");
    tiles[0][7].setText("White Rook");
    tiles[7][7].setText("White Rook");
    
    
    tiles[1][0].setPiece(new knight(1,0,1));             //make knights
    tiles[6][0].setPiece(new knight(6,0,1));
    tiles[1][7].setPiece(new knight(1,7,0));
    tiles[6][7].setPiece(new knight(6,7,0));
    
    tiles[1][0].setText("Black Knight");
    tiles[6][0].setText("Black Knight");
    tiles[1][7].setText("White Knight");
    tiles[6][7].setText("White Knight");
    
    tiles[2][0].setPiece(new bishop(2,0,1));            // make bishops
    tiles[5][0].setPiece(new bishop(5,0,1));
    tiles[2][7].setPiece(new bishop(2,7,0));
    tiles[5][7].setPiece(new bishop(5,7,0));
    
    tiles[2][0].setText("Black Bishop");
    tiles[5][0].setText("Black Bishop");
    tiles[2][7].setText("White Bishop");
    tiles[5][7].setText("White Bishop");
    
    tiles[4][0].setPiece(new king(4,0,1));             //make kings
    tiles[4][7].setPiece(new king(4,7,0));
    
    tiles[4][0].setText("Black King");
    tiles[4][7].setText("White King");
    
    
    
    tiles[3][0].setPiece(new queen(3,0,1));            //make queens
    tiles[3][7].setPiece(new queen(3,7,0));
    
    tiles[3][7].setText("White Queen");
    tiles[3][0].setText("Black Queen");
    
    this.setVisible(true);
    
    
  }
  
  /**
   * Method that returns the tiles on the board
   */
  public tile[][] getTiles(){
    return tiles;
  }
  
  public tile getInit(){
    return init;
  }
  
  public void setInit(tile t){
    init = t;
  }
  
  
  /**
   * Moves a piece if the move is valid
   */
  public void move( int x, int y, piece p){
    
    if(p.validMove(x , y) && p.type == "Knight" && p.getPlayer() == getWhoseMove()){            //case of valid move
      getTiles()[p.getX()][p.getY()].setPiece(null);        //sets origional square piece to null
      getTiles()[x][y].setPiece(p);                         //sets new square piece to the piece which moved
      getTiles()[x][y].setText(getTiles()[p.getX()][p.getY()].getText());  //sets the text on the new square to the text of the old square
      getTiles()[p.getX()][p.getY()].setText("");                          //sets the text of the old square to null
      p.setPosition(x , y);                                 //the piece now knows its own position
      p.setMoved();
      setWhoseMove((getWhoseMove() +1) % 2);
      lookForCheck();
    }
    
    
    if(p.validMove(x , y) && p.type == "King" && p.getPlayer() == getWhoseMove()){            //case of valid move
      getTiles()[p.getX()][p.getY()].setPiece(null);        //sets origional square piece to null
      getTiles()[x][y].setPiece(p);                         //sets new square piece to the piece which moved
      getTiles()[x][y].setText(getTiles()[p.getX()][p.getY()].getText());  //sets the text on the new square to the text of the old square
      getTiles()[p.getX()][p.getY()].setText("");                          //sets the text of the old square to null
      p.setPosition(x , y);                                 //the piece now knows its own position
      p.setMoved();
      setWhoseMove((getWhoseMove() +1) % 2);
      lookForCheck();
    }else if(Math.abs(x - p.getX()) == 2 && p.type == "King" && p.getPlayer() == getWhoseMove()){

      castle((king)p , 0 , 0);
      castle((king)p , 0 , 7);
      castle((king)p , 7 , 0);
      castle((king)p , 7 , 7);
      lookForCheck();
    }
    
    
    
    
    if(x == p.getX()){
      if(p.validMove(x , y) && p.type == "Pawn" && !isOccupied(x , y) && clearPath( x , y , p ) && p.getPlayer() == getWhoseMove()){            //case of valid move
        getTiles()[p.getX()][p.getY()].setPiece(null);        //sets origional square piece to null
        getTiles()[x][y].setPiece(p);                         //sets new square piece to the piece which moved
        getTiles()[x][y].setText(getTiles()[p.getX()][p.getY()].getText());  //sets the text on the new square to the text of the old square
        getTiles()[p.getX()][p.getY()].setText("");                          //sets the text of the old square to null
        p.setPosition(x , y);                                 //the piece now knows its own position
        p.setMoved();
        setWhoseMove((getWhoseMove() +1) % 2);
        lookForCheck();
      }
    }
    else if(Math.abs(x - p.getX()) == 1 && isOccupied(x , y)){
      if(p.validMove(x , y) && p.type == "Pawn" && p.getPlayer() == getWhoseMove()){            //case of valid move
        getTiles()[p.getX()][p.getY()].setPiece(null);        //sets origional square piece to null
        getTiles()[x][y].setPiece(p);                         //sets new square piece to the piece which moved
        getTiles()[x][y].setText(getTiles()[p.getX()][p.getY()].getText());  //sets the text on the new square to the text of the old square
        getTiles()[p.getX()][p.getY()].setText("");                          //sets the text of the old square to null
        p.setPosition(x , y);                                 //the piece now knows its own position
        p.setMoved();
        setWhoseMove((getWhoseMove() +1) % 2);
        lookForCheck();
      }
    }
    
    
    if(p.validMove(x , y) && p.type == "Rook" && clearPath( x , y , p ) && p.getPlayer() == getWhoseMove()){            //case of valid move
      getTiles()[p.getX()][p.getY()].setPiece(null);        //sets origional square piece to null
      getTiles()[x][y].setPiece(p);                         //sets new square piece to the piece which moved
      getTiles()[x][y].setText(getTiles()[p.getX()][p.getY()].getText());  //sets the text on the new square to the text of the old square
      getTiles()[p.getX()][p.getY()].setText("");                          //sets the text of the old square to null
      p.setPosition(x , y);                                 //the piece now knows its own position
      p.setMoved();
      setWhoseMove((getWhoseMove() +1) % 2);
      lookForCheck();
    }
    
    
    if(p.validMove(x , y) && p.type == "Bishop" && clearPath( x , y , p ) && p.getPlayer() == getWhoseMove()){            //case of valid move
      getTiles()[p.getX()][p.getY()].setPiece(null);        //sets origional square piece to null
      getTiles()[x][y].setPiece(p);                         //sets new square piece to the piece which moved
      getTiles()[x][y].setText(getTiles()[p.getX()][p.getY()].getText());  //sets the text on the new square to the text of the old square
      getTiles()[p.getX()][p.getY()].setText("");                          //sets the text of the old square to null
      p.setPosition(x , y);                                 //the piece now knows its own position
      p.setMoved();
      setWhoseMove((getWhoseMove() +1) % 2);
      lookForCheck();
    }
    
    
    if(p.validMove(x , y) && p.type == "Queen" && clearPath( x , y , p )&& p.getPlayer() == getWhoseMove()){            //case of valid move
      getTiles()[p.getX()][p.getY()].setPiece(null);        //sets origional square piece to null
      getTiles()[x][y].setPiece(p);                         //sets new square piece to the piece which moved
      getTiles()[x][y].setText(getTiles()[p.getX()][p.getY()].getText());  //sets the text on the new square to the text of the old square
      getTiles()[p.getX()][p.getY()].setText("");                          //sets the text of the old square to null
      p.setPosition(x , y);                                 //the piece now knows its own position
      p.setMoved();
      setWhoseMove((getWhoseMove() +1) % 2);
      lookForCheck();
    }
    
    if(this.whiteChecked == true){
    JOptionPane.showMessageDialog(new JFrame(), "White is in Check", "Dialog",
        JOptionPane.ERROR_MESSAGE);
    }
    if(this.blackChecked == true){
       JOptionPane.showMessageDialog(new JFrame(), "Black is in Check", "Dialog",
        JOptionPane.ERROR_MESSAGE);
    }
    
  }
  
  
  
  
  
  
  /**
   * Method that checks if there is a clear path between the start location and end location
   */
  public boolean clearPath(int x, int y, piece p){
    int initX = p.getX();
    int initY = p.getY();
    if(Math.abs(y - initY) == Math.abs(x - initX) ){
      if(x > initX && y > initY){
        for(int i = 1; i < x - initX; i++){
          if(isOccupied(initX + i , initY + i)){
            return false;
          }
        }
      }
      if(x > initX && y < initY){
        for(int i = 1; i < x - initX; i++){
          if(isOccupied(initX + i , initY - i)){
            return false;
          }
        }
      }
      if(x < initX && y > initY){
        for(int i = 1; i < y - initY; i++){
          if(isOccupied(initX - i , initY + i)){
            return false;
          }
        }
      }
      if(x < initX && y < initY){
        for(int i = 1; i < initX - x; i++){
          if(isOccupied(initX - i , initY - i)){
            return false;
          }
        }
      }
      
    }
    else if(x - initX != 0){
      if(x > initX){
        for(int i = initX + 1 ; i < x ; i++){
          if(isOccupied(i , y)){
            return false;
          }
        }
      }
      if(x < initX){
        for(int i = x + 1 ; i < initX ; i++){
          if(isOccupied(i , y)){
            return false;
          }
        }
      }
    }
    else if(y - initY != 0){
      if(y > initY){
        for(int i = initY + 1 ; i < y ; i++){
          if(isOccupied(x , i)){
            return false;
          }
        }
      }
      if(y < initY){
        for(int i = y + 1 ; i < initY ; i++){
          if(isOccupied(x , i)){
            return false;
          }
        }
      }
    }
    return true;
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
  
  /**
   * Method that checks for a legal catle move
   */
  public boolean legalCastle(rook r, king k){
    
    
    int rookX = r.getX();
    int rookY = r.getY();
    
    
    if(r.getPlayer() != k.getPlayer()){
      return false;
    }
    else if(r.getMoved() == true){
      return false;
    }
    else if(k.getMoved() == true){
      return false;
    }
    else if(clearPath(rookX,rookY,k) == false){
      return false;
    }
    else{
      return true;
    }
    
  }
  
  
  /** 
   * method that performs the castle
   */
  public void castle(king k, int rookX, int rookY){
    
    rook r = (rook)getTiles()[rookX][rookY].getPiece();
    
    if(legalCastle(r,k)){
      
      if(rookX ==0 && rookY ==0){
        move(3,0,r);
        getTiles()[k.getX()][k.getY()].setPiece(null);        //sets origional square piece to null
        getTiles()[2][0].setPiece(k);                         //sets new square piece to the piece which moved
        getTiles()[2][0].setText(getTiles()[k.getX()][k.getY()].getText());  //sets the text on the new square to the text of the old square
        getTiles()[k.getX()][k.getY()].setText("");                          //sets the text of the old square to null
        k.setPosition(2 , 0);                                 //the piece now knows its own position
        k.setMoved();
        setWhoseMove((getWhoseMove() +1) % 2);
      }
      else if(rookX == 7 && rookY == 0){
        move(5,0,r);
        getTiles()[k.getX()][k.getY()].setPiece(null);        //sets origional square piece to null
        getTiles()[6][0].setPiece(k);                         //sets new square piece to the piece which moved
        getTiles()[6][0].setText(getTiles()[k.getX()][k.getY()].getText());  //sets the text on the new square to the text of the old square
        getTiles()[k.getX()][k.getY()].setText("");                          //sets the text of the old square to null
        k.setPosition(6 , 0);                                 //the piece now knows its own position
        k.setMoved();
        setWhoseMove((getWhoseMove() +1) % 2);
      }
      else if(rookX == 0 && rookY ==7){
        move(3,7,r);
        getTiles()[k.getX()][k.getY()].setPiece(null);        //sets origional square piece to null
        getTiles()[2][7].setPiece(k);                         //sets new square piece to the piece which moved
        getTiles()[2][7].setText(getTiles()[k.getX()][k.getY()].getText());  //sets the text on the new square to the text of the old square
        getTiles()[k.getX()][k.getY()].setText("");                          //sets the text of the old square to null
        k.setPosition(2 , 7);                                 //the piece now knows its own position
        k.setMoved();
        setWhoseMove((getWhoseMove() +1) % 2);
      }
      else if(rookX == 7 && rookY ==7){
        move(5,7,r);
        getTiles()[k.getX()][k.getY()].setPiece(null);        //sets origional square piece to null
        getTiles()[6][7].setPiece(k);                         //sets new square piece to the piece which moved
        getTiles()[6][7].setText(getTiles()[k.getX()][k.getY()].getText());  //sets the text on the new square to the text of the old square
        getTiles()[k.getX()][k.getY()].setText("");                          //sets the text of the old square to null
        k.setPosition(6 , 7);                                 //the piece now knows its own position
        k.setMoved();
        setWhoseMove((getWhoseMove() +1) % 2);
      }
      
      
    }
    
  }
  
  /**
   * look for a player's king
   */
  public piece findKing(int player){
    
    piece king = null;//variable for king
    
    if (player != 1 && player !=0){
      return null;
    }
    else{
      
      tile[][] board = getTiles();
      
      for(int i = 0; i < 8; i++){
        for(int j = 0; j < 8; j++){
          
          if(board[i][j].getPiece() != null){
            if(board[i][j].getPiece().getType().equals("King") == true && board[i][j].getPiece().getPlayer() == player){
              king = board[i][j].getPiece();
            }

          }
        }
      }
    }
    
    return king;
    
  }
  
  /**
   * method looking for check
   */
  public void lookForCheck(){
    
    //find the king of the currently defending player
    int opp; //opposition player
    piece k; //king variable
    if(getWhoseMove() == 0){
      opp = 0;
      k = findKing(opp);
    }
    else{
      opp = 1;
      k = findKing(opp);
    }
    
    //location of king
    int kingX = k.getX();
    int kingY = k.getY();
    
    
    tile[][] board = getTiles();
    
    if(opp == 0){
        this.whiteChecked = false;
    }
    else{
        this.blackChecked = false;
    }
    
    System.out.println(blackChecked);
    
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        
        if(board[i][j].getPiece() != null && board[i][j].getPiece().getPlayer() != opp){ //if we find a friendly piece check if it can attack the king next turn
          if(board[i][j].getPiece().validMove(kingX,kingY) == true && clearPath(kingX, kingY,board[i][j].getPiece()) && board[i][j].getPiece().getType() != "Knight"){
            
            if(opp == 0){
              this.whiteChecked = true;
            }
            else{
              this.blackChecked = true;
            }
       
          }
          else if(board[i][j].getPiece().validMove(kingX,kingY) == true && board[i][j].getPiece().getType() == "Knight") {
            
            if(opp == 0){
              this.whiteChecked = true;
            }
            else{
              this.blackChecked = true;
            }
            
          }
          
        }
        
      }
    }
    
  }
  
  public boolean getwhiteChecked(){
    return this.whiteChecked;
  }
  
  public boolean getblackChecked(){
    return this.blackChecked;
  }
  
  
  /**
   * getter setters for whoseMove
   */
  public int getWhoseMove(){
    return whoseMove;
  }
  
  public void setWhoseMove(int wm){
    whoseMove = wm;
  }
  
  
  
}