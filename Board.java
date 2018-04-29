import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math;

public class Board extends JPanel{
  
  private Move[] moveList = new Move[300];
  
  private int mv = 0;
  
  private boolean isCheckMate = false;
  
  private piece pieceLastMoved;
  
  private int pieceLastMovedFromX;
  
  private int pieceLastMovedFromY;
  
  private piece pieceTakenLastTurn;
  
  private Icon takenPieceIcon;
  
  private Icon movedPieceIcon;
  
  private tile[][] tiles; //jbuttons for the UI
  
  private tile init;  //initial button pressed with piece
  
  private int whoseMove = 0;      //whites turn to start
  
  private boolean whiteChecked = false;
  private boolean blackChecked = false;
  
  
  public Board(){
    try {                                               //account for apple's graphics
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    }
    
    catch (Exception e) {
    }
    for(int i = 0; i < 300; i++){
      moveList[i] = new Move();
    }
    //Container c = this.getContentPane();                //formats the JPanel to have the grid of game tiles over the information of who's turn it is
    this.setSize(800,800);
    this.tiles = new tile[8][8];     //creates as many tiles as there are spaces on the board 
    this.setLayout(new GridLayout(8,8)); //create a gridlayout on the container     
    
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
        this.add(tiles[index2][index1]);
      }
    }
    
    
    for(int i = 0; i < 8; i++){                   //make black pawns
      tiles[i][1].setPiece(new pawn(i,1,1));
      tiles[i][1].setIcon(new ImageIcon("Chess Icons/blackPawn.png"));
    }      
    
    for(int i = 0; i < 8; i++){                     //make white pawns
      tiles[i][6].setPiece(new pawn(i,6,0));
      tiles[i][6].setIcon(new ImageIcon("Chess Icons/whitePawn.png"));
    }     
    
    tiles[0][0].setPiece(new rook(0,0,1));         //make rooks
    tiles[7][0].setPiece(new rook(7,0,1));
    tiles[0][7].setPiece(new rook(0,7,0));
    tiles[7][7].setPiece(new rook(7,7,0));
    
    tiles[0][0].setIcon(new ImageIcon("Chess Icons/blackRook.png"));
    tiles[7][0].setIcon(new ImageIcon("Chess Icons/blackRook.png"));
    tiles[0][7].setIcon(new ImageIcon("Chess Icons/whiteRook.png"));
    tiles[7][7].setIcon(new ImageIcon("Chess Icons/whiteRook.png"));
    
    
    tiles[1][0].setPiece(new knight(1,0,1));             //make knights
    tiles[6][0].setPiece(new knight(6,0,1));
    tiles[1][7].setPiece(new knight(1,7,0));
    tiles[6][7].setPiece(new knight(6,7,0));
    
    tiles[1][0].setIcon(new ImageIcon("Chess Icons/blackKnight.png"));
    tiles[6][0].setIcon(new ImageIcon("Chess Icons/blackKnight.png"));
    tiles[1][7].setIcon(new ImageIcon("Chess Icons/whiteKnight.png"));
    tiles[6][7].setIcon(new ImageIcon("Chess Icons/whiteKnight.png"));
    
    
    tiles[2][0].setPiece(new bishop(2,0,1));            // make bishops
    tiles[5][0].setPiece(new bishop(5,0,1));
    tiles[2][7].setPiece(new bishop(2,7,0));
    tiles[5][7].setPiece(new bishop(5,7,0));
    
    tiles[2][0].setIcon(new ImageIcon("Chess Icons/blackBishop.png"));
    tiles[5][0].setIcon(new ImageIcon("Chess Icons/blackBishop.png"));
    tiles[2][7].setIcon(new ImageIcon("Chess Icons/whiteBishop.png"));
    tiles[5][7].setIcon(new ImageIcon("Chess Icons/whiteBishop.png"));
    
    
    
    tiles[4][0].setPiece(new king(4,0,1));             //make kings
    tiles[4][7].setPiece(new king(4,7,0));
    
    tiles[4][0].setIcon(new ImageIcon("Chess Icons/blackKing.png"));
    tiles[4][7].setIcon(new ImageIcon("Chess Icons/whiteKing.png"));
    
    
    
    tiles[3][0].setPiece(new queen(3,0,1));            //make queens
    tiles[3][7].setPiece(new queen(3,7,0));
    
    tiles[3][0].setIcon(new ImageIcon("Chess Icons/blackQueen.png"));
    tiles[3][7].setIcon(new ImageIcon("Chess Icons/whiteQueen.png"));
    
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
   * Method that undoes the last move
   */
  public void unduMove(){
    if(pieceLastMoved != null && this.isCheckMate == false){
      
      int originalX = pieceLastMoved.getX();
      int originalY= pieceLastMoved.getY();
      
      getTiles()[pieceLastMoved.getX()][pieceLastMoved.getY()].setPiece(pieceTakenLastTurn);        //sets origional square piece to the piece taken last time
      getTiles()[pieceLastMovedFromX][pieceLastMovedFromY].setPiece(pieceLastMoved);                         //sets new square piece to the piece which moved
      
      getTiles()[pieceLastMovedFromX][pieceLastMovedFromY].setIcon(getTiles()[pieceLastMoved.getX()][pieceLastMoved.getY()].getIcon());  //sets the icon on the new square to the text of the old square
      getTiles()[originalX][originalY].setIcon(takenPieceIcon);
      
      //updates the location info for the pieces
      if(pieceTakenLastTurn != null){
        pieceTakenLastTurn.setPosition(originalX,originalY);
      }
      pieceLastMoved.setPosition(pieceLastMovedFromX, pieceLastMovedFromY);
      
      pieceLastMoved.setMoved(pieceLastMoved.getMoveCount() -1);
      
      //set the piece last moved to null
      this.pieceLastMoved = null;
      
      
      
      setWhoseMove((getWhoseMove() +1) % 2);
      if(this.getWhoseMove() ==0){
        actionMenu.setMoveText("White's Move");
      }
      else{
        actionMenu.setMoveText("Black's Move");
      }    //switch player move back
    }
  }
  
  //decided to cut this method of accessing the move linkedlist in favor of a one move rewind until bugs can be worked out
  /**
   * Method that undoes the last move and switches the player turn
   */
  public void unduMoveButton(){
    
    System.out.println(mv);
    if(mv > 0){  // go back to the last move that was made
      mv--;
    }
    if(moveList[mv].getPieceLastMoved() != null){
      
      int originalX = moveList[mv].getPieceLastMoved().getX();
      int originalY= moveList[mv].getPieceLastMoved().getY();
      
      
      getTiles()[originalX][originalY].setPiece(moveList[mv].getPieceTakenLastTurn());        //sets origional square piece to the piece taken last time
      getTiles()[moveList[mv].getPieceLastMovedFromX()][moveList[mv].getPieceLastMovedFromY()].setPiece(moveList[mv].getPieceLastMoved());                         //sets new square piece to the piece which moved
      
      
      getTiles()[moveList[mv].getPieceLastMovedFromX()][moveList[mv].getPieceLastMovedFromY()].setIcon(getTiles()[originalX][originalY].getIcon());  //sets the icon on the new square to the text of the old square
      getTiles()[originalX][originalY].setIcon(takenPieceIcon);
      
      if(moveList[mv].getPieceTakenLastTurn() != null){
        moveList[mv].getPieceTakenLastTurn().setPosition(originalX,originalY);
      }
      moveList[mv].getPieceLastMoved().setPosition(moveList[mv].getPieceLastMovedFromX(), moveList[mv].getPieceLastMovedFromY());
      
      
      setWhoseMove((getWhoseMove() +1) % 2);
      if(this.getWhoseMove() ==0){
        actionMenu.setMoveText("White's Move");
      }
      else{
        actionMenu.setMoveText("Black's Move");
      }    //switch player move back
    }
  }
  
  
  /**
   * Method that undoes the last move and switches the player turn
   */
  public void forwardButton(){
    
    
    
  }
  
  
  /**
   * helper method to promote the pawn at the end
   */
  public void promotePawn(piece p){
    if(p.getType() == "Pawn"){
      int pieceX = p.getX();
      int pieceY = p.getY();
      
      int plr = p.getPlayer();
      
      getTiles()[pieceX][pieceY].setPiece(new queen(pieceX,pieceY,plr));
      
      if(plr == 1){
        getTiles()[pieceX][pieceY].setIcon(new ImageIcon("Chess Icons/blackQueen.png"));
      }
      else if(plr == 0){
        getTiles()[pieceX][pieceY].setIcon(new ImageIcon("Chess Icons/whiteQueen.png"));
      }
      
    }
  }
  
  /**
   * Moves a piece if the move is valid (for checkmate only)
   */
  private void move( int x, int y, piece p){
    
    if(this.isCheckMate ==false){
      
      //first store the piece that is about to be taken
      pieceTakenLastTurn = null;
      if(tiles[x][y].getPiece() != null && tiles[x][y].getPiece().getPlayer() != getWhoseMove()){
        pieceTakenLastTurn = tiles[x][y].getPiece(); 
        moveList[mv].setPieceTakenLastTurn(tiles[x][y].getPiece());
      }
      
      if(tiles[x][y].getPiece() == null || (tiles[x][y].getPiece() != null && tiles[x][y].getPiece().getPlayer() != getWhoseMove())){
        
        if(p.validMove(x , y) && p.type == "Knight" && p.getPlayer() == getWhoseMove()){            //case of valid move
          getTiles()[p.getX()][p.getY()].setPiece(null);        //sets origional square piece to null
          getTiles()[x][y].setPiece(p);                         //sets new square piece to the piece which moved
          
          takenPieceIcon = getTiles()[x][y].getIcon();//save icon of taken piece
          moveList[mv].setTakenPieceIcon(getTiles()[x][y].getIcon());
          
          getTiles()[x][y].setIcon(getTiles()[p.getX()][p.getY()].getIcon());  //sets the icon on the new square to the text of the old square
          moveList[mv].setMovedPieceIcon(getTiles()[p.getX()][p.getY()].getIcon());
          getTiles()[p.getX()][p.getY()].setIcon(null);                          //sets the icon of the old square to null
          
          
          lookForCheck(getWhoseMove());
          if((getWhoseMove() == 0 && this.whiteChecked == true) || (getWhoseMove() == 1 && this.blackChecked == true)){      //Make sure the move doesn't put the player in check
            getTiles()[p.getX()][p.getY()].setPiece(p);        
            getTiles()[x][y].setPiece(null);
            
            getTiles()[p.getX()][p.getY()].setIcon(getTiles()[x][y].getIcon()); 
            getTiles()[x][y].setIcon(takenPieceIcon);
            
            
            this.blackChecked = false;
            this.whiteChecked = false;
          }else{
            this.pieceLastMoved = p;
            moveList[mv].setPieceLastMoved(p);
            this.pieceLastMovedFromX = p.getX();
            moveList[mv].setPieceLastMovedFromX(p.getX());
            this.pieceLastMovedFromY = p.getY();
            moveList[mv].setPieceLastMovedFromY(p.getY());
            p.setPosition(x , y);         //the piece now knows its own position
            p.setMoved(p.getMoveCount()+1);
            setWhoseMove((getWhoseMove() +1) % 2);
            if(this.getWhoseMove() ==0){
              actionMenu.setMoveText("White's Move");
            }
            else{
              actionMenu.setMoveText("Black's Move");
            }
            lookForCheck(getWhoseMove());
            mv++;
          }
        }
        
        
        if(p.validMove(x , y) && p.type == "King" && p.getPlayer() == getWhoseMove()){            //case of valid move
          piece takenPiece = getTiles()[x][y].getPiece(); //save piece to be taken
          getTiles()[p.getX()][p.getY()].setPiece(null);        //sets origional square piece to null
          getTiles()[x][y].setPiece(p);                         //sets new square piece to the piece which moved
          
          takenPieceIcon = getTiles()[x][y].getIcon();//save icon of taken piece
          moveList[mv].setTakenPieceIcon(getTiles()[x][y].getIcon());
          
          getTiles()[x][y].setIcon(getTiles()[p.getX()][p.getY()].getIcon());  //sets the icon on the new square to the text of the old square
          moveList[mv].setMovedPieceIcon(getTiles()[p.getX()][p.getY()].getIcon());
          getTiles()[p.getX()][p.getY()].setIcon(null);                          //sets the icon of the old square to null
          
          
          int origionalX = p.getX();                                          //remember origional coordinates for piece
          int origionalY = p.getY();
          this.pieceLastMovedFromX = origionalX;
          this.pieceLastMovedFromY = origionalY;
          p.setPosition(x , y);                                               //bc of implementation of lookForCheck we have to update the kings postion
          
          lookForCheck(getWhoseMove());
          if((getWhoseMove() == 0 && this.whiteChecked == true) || (getWhoseMove() == 1 && this.blackChecked == true)){ //Make sure the move doesn't put the player in check
            getTiles()[origionalX][origionalY].setPiece(p);        
            getTiles()[x][y].setPiece(takenPiece);
            
            getTiles()[origionalX][origionalY].setIcon(getTiles()[x][y].getIcon()); 
            getTiles()[x][y].setIcon(takenPieceIcon);
            
            
            this.blackChecked = false;
            this.whiteChecked = false;
            p.setPosition(origionalX,origionalY);
          }else{
            this.pieceLastMoved = p;
            moveList[mv].setPieceLastMoved(p);
            moveList[mv].setPieceLastMovedFromX(origionalX);
            moveList[mv].setPieceLastMovedFromY(origionalY);
            p.setPosition(x , y);                                 //the piece now knows its own position
            p.setMoved(p.getMoveCount()+1);
            setWhoseMove((getWhoseMove() +1) % 2);
            if(this.getWhoseMove() ==0){
              actionMenu.setMoveText("White's Move");
            }
            else{
              actionMenu.setMoveText("Black's Move");
            }
            lookForCheck(getWhoseMove());
            mv++;
          }
        }else if(Math.abs(x - p.getX()) == 2 && p.type == "King" && p.getPlayer() == getWhoseMove()){
          
          castle((king)p , 0 , 0);
          castle((king)p , 0 , 7);
          castle((king)p , 7 , 0);
          castle((king)p , 7 , 7);
          lookForCheck(getWhoseMove());
        }
        
        
        
        
        if(x == p.getX()){
          if(p.validMove(x , y) && p.type == "Pawn" && !isOccupied(x , y) && clearPath( x , y , p ) && p.getPlayer() == getWhoseMove()){            //case of valid move
            getTiles()[p.getX()][p.getY()].setPiece(null);        //sets origional square piece to null
            getTiles()[x][y].setPiece(p);                         //sets new square piece to the piece which moved
            
            takenPieceIcon = getTiles()[x][y].getIcon();//save icon of taken piece
            moveList[mv].setTakenPieceIcon(getTiles()[x][y].getIcon());
            
            getTiles()[x][y].setIcon(getTiles()[p.getX()][p.getY()].getIcon());  //sets the icon on the new square to the text of the old square
            moveList[mv].setMovedPieceIcon(getTiles()[p.getX()][p.getY()].getIcon());
            
            getTiles()[p.getX()][p.getY()].setIcon(null);                          //sets the icon of the old square to null
            
            
            lookForCheck(getWhoseMove());
            if((getWhoseMove() == 0 && this.whiteChecked == true) || (getWhoseMove() == 1 && this.blackChecked == true)){   //Make sure the move doesn't put the player in check
              getTiles()[p.getX()][p.getY()].setPiece(p);        
              getTiles()[x][y].setPiece(null);
              
              getTiles()[p.getX()][p.getY()].setIcon(getTiles()[x][y].getIcon()); 
              getTiles()[x][y].setIcon(takenPieceIcon);
              
              
              this.blackChecked = false;
              this.whiteChecked = false;
            }else{
              this.pieceLastMoved = p;
              moveList[mv].setPieceLastMoved(p);
              this.pieceLastMovedFromX = p.getX();
              moveList[mv].setPieceLastMovedFromX(p.getX());
              this.pieceLastMovedFromY = p.getY();
              moveList[mv].setPieceLastMovedFromY(p.getY());
              p.setPosition(x , y);                                 //the piece now knows its own position
              
              if(p.getY()==7 || p.getY() ==0){
                this.promotePawn(p);
              }
              
              p.setMoved(p.getMoveCount()+1);
              setWhoseMove((getWhoseMove() +1) % 2);
              if(this.getWhoseMove() ==0){
                actionMenu.setMoveText("White's Move");
              }
              else{
                actionMenu.setMoveText("Black's Move");
              }
              lookForCheck(getWhoseMove());
              mv++;
            }
          }
        }
        else if(Math.abs(x - p.getX()) == 1 && isOccupied(x , y)){
          if(p.validMove(x , y) && p.type == "Pawn" && p.getPlayer() == getWhoseMove()){            //case of valid move
            getTiles()[p.getX()][p.getY()].setPiece(null);        //sets origional square piece to null
            getTiles()[x][y].setPiece(p);                         //sets new square piece to the piece which moved
            
            
            takenPieceIcon = getTiles()[x][y].getIcon();//save icon of taken piece
            moveList[mv].setTakenPieceIcon(getTiles()[x][y].getIcon());
            
            getTiles()[x][y].setIcon(getTiles()[p.getX()][p.getY()].getIcon());  //sets the icon on the new square to the text of the old square
            moveList[mv].setMovedPieceIcon(getTiles()[p.getX()][p.getY()].getIcon());
            getTiles()[p.getX()][p.getY()].setIcon(null);                          //sets the icon of the old square to null
            
            
            lookForCheck(getWhoseMove());
            if((getWhoseMove() == 0 && this.whiteChecked == true) || (getWhoseMove() == 1 && this.blackChecked == true)){   //Make sure the move doesn't put the player in check
              getTiles()[p.getX()][p.getY()].setPiece(p);        
              getTiles()[x][y].setPiece(null);
              
              getTiles()[p.getX()][p.getY()].setIcon(getTiles()[x][y].getIcon()); 
              getTiles()[x][y].setIcon(takenPieceIcon);
              
              this.blackChecked = false;
              this.whiteChecked = false;
            }else{
              this.pieceLastMoved = p;
              moveList[mv].setPieceLastMoved(p);
              this.pieceLastMovedFromX = p.getX();
              moveList[mv].setPieceLastMovedFromX(p.getX());
              this.pieceLastMovedFromY = p.getY();
              moveList[mv].setPieceLastMovedFromY(p.getY());
              p.setPosition(x , y);                                 //the piece now knows its own position
              
              if(p.getY()==7 || p.getY() ==0){
                this.promotePawn(p);
              }
              
              p.setMoved(p.getMoveCount() + 1);
              setWhoseMove((getWhoseMove() +1) % 2);
              if(this.getWhoseMove() ==0){
                actionMenu.setMoveText("White's Move");
              }
              else{
                actionMenu.setMoveText("Black's Move");
              }
              lookForCheck(getWhoseMove());
              mv++;
            }
          }
        }
        
        
        if(p.validMove(x , y) && p.type == "Rook" && clearPath( x , y , p ) && p.getPlayer() == getWhoseMove()){            //case of valid move
          getTiles()[p.getX()][p.getY()].setPiece(null);        //sets origional square piece to null
          getTiles()[x][y].setPiece(p);                         //sets new square piece to the piece which moved
          
          takenPieceIcon = getTiles()[x][y].getIcon();//save icon of taken piece
          
          getTiles()[x][y].setIcon(getTiles()[p.getX()][p.getY()].getIcon());  //sets the icon on the new square to the text of the old square
          moveList[mv].setMovedPieceIcon(getTiles()[p.getX()][p.getY()].getIcon());
          getTiles()[p.getX()][p.getY()].setIcon(null);                          //sets the icon of the old square to null
          
          
          lookForCheck(getWhoseMove());
          if((getWhoseMove() == 0 && this.whiteChecked == true) || (getWhoseMove() == 1 && this.blackChecked == true)){   //Make sure the move doesn't put the player in check
            getTiles()[p.getX()][p.getY()].setPiece(p);        
            getTiles()[x][y].setPiece(null);
            
            getTiles()[p.getX()][p.getY()].setIcon(getTiles()[x][y].getIcon()); 
            getTiles()[x][y].setIcon(takenPieceIcon);
            
            
            this.blackChecked = false;
            this.whiteChecked = false;
          }else{
            this.pieceLastMoved = p;
            moveList[mv].setPieceLastMoved(p);
            this.pieceLastMovedFromX = p.getX();
            moveList[mv].setPieceLastMovedFromX(p.getX());
            this.pieceLastMovedFromY = p.getY();
            moveList[mv].setPieceLastMovedFromY(p.getY());
            p.setPosition(x , y);                                 //the piece now knows its own position
            p.setMoved(p.getMoveCount()+1);
            setWhoseMove((getWhoseMove() +1) % 2);
            if(this.getWhoseMove() ==0){
              actionMenu.setMoveText("White's Move");
            }
            else{
              actionMenu.setMoveText("Black's Move");
            }
            lookForCheck(getWhoseMove());
            mv++;
          }
        }
        
        
        if(p.validMove(x , y) && p.type == "Bishop" && clearPath( x , y , p ) && p.getPlayer() == getWhoseMove()){            //case of valid move
          getTiles()[p.getX()][p.getY()].setPiece(null);        //sets origional square piece to null
          getTiles()[x][y].setPiece(p);                         //sets new square piece to the piece which moved
          
          takenPieceIcon = getTiles()[x][y].getIcon();//save icon of taken piece
          moveList[mv].setTakenPieceIcon(getTiles()[x][y].getIcon());
          
          getTiles()[x][y].setIcon(getTiles()[p.getX()][p.getY()].getIcon());  //sets the icon on the new square to the text of the old square
          moveList[mv].setMovedPieceIcon(getTiles()[p.getX()][p.getY()].getIcon());
          getTiles()[p.getX()][p.getY()].setIcon(null);                          //sets the icon of the old square to null
          
          lookForCheck(getWhoseMove());
          if((getWhoseMove() == 0 && this.whiteChecked == true) || (getWhoseMove() == 1 && this.blackChecked == true)){   //Make sure the move doesn't put the player in check
            getTiles()[p.getX()][p.getY()].setPiece(p);        
            getTiles()[x][y].setPiece(null);
            
            getTiles()[p.getX()][p.getY()].setIcon(getTiles()[x][y].getIcon()); 
            getTiles()[x][y].setIcon(takenPieceIcon);
            
            this.blackChecked = false;
            this.whiteChecked = false;
          }else{
            this.pieceLastMoved = p;
            moveList[mv].setPieceLastMoved(p);
            this.pieceLastMovedFromX = p.getX();
            moveList[mv].setPieceLastMovedFromX(p.getX());
            this.pieceLastMovedFromY = p.getY();
            moveList[mv].setPieceLastMovedFromY(p.getY());
            p.setPosition(x , y);                                 //the piece now knows its own position
            p.setMoved(p.getMoveCount()+1);
            setWhoseMove((getWhoseMove() +1) % 2);
            if(this.getWhoseMove() ==0){
              actionMenu.setMoveText("White's Move");
            }
            else{
              actionMenu.setMoveText("Black's Move");
            }
            lookForCheck(getWhoseMove());
            mv++;
          }
        }
        
        
        if(p.validMove(x , y) && p.type == "Queen" && clearPath( x , y , p )&& p.getPlayer() == getWhoseMove()){            //case of valid move
          getTiles()[p.getX()][p.getY()].setPiece(null);        //sets origional square piece to null
          getTiles()[x][y].setPiece(p);                         //sets new square piece to the piece which moved
          
          takenPieceIcon = getTiles()[x][y].getIcon();//save icon of taken piece
          moveList[mv].setTakenPieceIcon(getTiles()[x][y].getIcon());
          
          getTiles()[x][y].setIcon(getTiles()[p.getX()][p.getY()].getIcon());  //sets the icon on the new square to the text of the old square
          moveList[mv].setMovedPieceIcon(getTiles()[p.getX()][p.getY()].getIcon());
          getTiles()[p.getX()][p.getY()].setIcon(null);                          //sets the icon of the old square to null
          
          
          lookForCheck(getWhoseMove());
          if((getWhoseMove() == 0 && this.whiteChecked == true) || (getWhoseMove() == 1 && this.blackChecked == true)){    //Make sure the move doesn't put the player in check
            getTiles()[p.getX()][p.getY()].setPiece(p);        
            getTiles()[x][y].setPiece(null);
            
            getTiles()[p.getX()][p.getY()].setIcon(getTiles()[x][y].getIcon()); 
            getTiles()[x][y].setIcon(takenPieceIcon);
            
            
            this.blackChecked = false;
            this.whiteChecked = false;
          }else{
            this.pieceLastMoved = p;
            moveList[mv].setPieceLastMoved(p);
            this.pieceLastMovedFromX = p.getX();
            moveList[mv].setPieceLastMovedFromX(p.getX());
            this.pieceLastMovedFromY = p.getY();
            moveList[mv].setPieceLastMovedFromY(p.getY());
            p.setPosition(x , y);                                 //the piece now knows its own position
            p.setMoved(p.getMoveCount()+1);
            setWhoseMove((getWhoseMove() +1) % 2);
            if(this.getWhoseMove() ==0){
              actionMenu.setMoveText("White's Move");
            }
            else{
              actionMenu.setMoveText("Black's Move");
            }
            lookForCheck(getWhoseMove());
            mv++;
          }
        }
        
        if(this.whiteChecked == true){
          
          if(lookForCheckmate(0) == true){
            //end the game
            this.isCheckMate = true;
            JOptionPane.showMessageDialog(new JFrame(), "White is in Checkmate, Black Wins!", "Dialog",
                                          JOptionPane.ERROR_MESSAGE);
          }else{
            
            JOptionPane.showMessageDialog(new JFrame(), "White is in Check", "Dialog",
                                          JOptionPane.ERROR_MESSAGE);
          }
        }
        
        if(this.blackChecked == true){
          
          if(lookForCheckmate(1) == true){
            //end the game
            this.isCheckMate = true;
            JOptionPane.showMessageDialog(new JFrame(), "Black is in Checkmate, White Wins!", "Dialog",
                                          JOptionPane.ERROR_MESSAGE);
          }else{
            
            JOptionPane.showMessageDialog(new JFrame(), "Black is in Check", "Dialog",
                                          JOptionPane.ERROR_MESSAGE);
          }
        }
        
      }
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
    
    if(r != null){
      int rookX = r.getX();
      int rookY = r.getY();
      
      
      if(r.getPlayer() != k.getPlayer()){
        return false;
      }
      else if(r.getMoveCount() > 0){
        return false;
      }
      else if(k.getMoveCount() > 0){
        return false;
      }
      else if(clearPath(rookX,rookY,k) == false){
        return false;
      }
      else{
        return true;
      }
    }
    return false;
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
        getTiles()[2][0].setIcon(getTiles()[k.getX()][k.getY()].getIcon());  //sets the text on the new square to the text of the old square
        getTiles()[k.getX()][k.getY()].setIcon(null);                          //sets the text of the old square to null
        k.setPosition(2 , 0);                                 //the piece now knows its own position
        k.setMoved(k.getMoveCount()+1);
      }
      else if(rookX == 7 && rookY == 0){
        move(5,0,r);
        getTiles()[k.getX()][k.getY()].setPiece(null);        //sets origional square piece to null
        getTiles()[6][0].setPiece(k);                         //sets new square piece to the piece which moved
        getTiles()[6][0].setIcon(getTiles()[k.getX()][k.getY()].getIcon());  //sets the text on the new square to the text of the old square
        getTiles()[k.getX()][k.getY()].setIcon(null);                          //sets the text of the old square to null
        k.setPosition(6 , 0);                                 //the piece now knows its own position
        k.setMoved(k.getMoveCount()+1);
      }
      else if(rookX == 0 && rookY ==7){
        move(3,7,r);
        getTiles()[k.getX()][k.getY()].setPiece(null);        //sets origional square piece to null
        getTiles()[2][7].setPiece(k);                         //sets new square piece to the piece which moved
        getTiles()[2][7].setIcon(getTiles()[k.getX()][k.getY()].getIcon());  //sets the text on the new square to the text of the old square
        getTiles()[k.getX()][k.getY()].setIcon(null);                          //sets the text of the old square to null
        k.setPosition(2 , 7);                                 //the piece now knows its own position
        k.setMoved(k.getMoveCount()+1);
      }
      else if(rookX == 7 && rookY ==7){
        move(5,7,r);
        getTiles()[k.getX()][k.getY()].setPiece(null);        //sets origional square piece to null
        getTiles()[6][7].setPiece(k);                         //sets new square piece to the piece which moved
        getTiles()[6][7].setIcon(getTiles()[k.getX()][k.getY()].getIcon());  //sets the text on the new square to the text of the old square
        getTiles()[k.getX()][k.getY()].setIcon(null);                          //sets the text of the old square to null
        k.setPosition(6 , 7);                                 //the piece now knows its own position
        k.setMoved(k.getMoveCount()+1);
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
  public void lookForCheck(int player){
    
    //find the king of the currently defending player
    
    piece k; //king variable
    
    k = findKing(player);
    
    
    //location of king
    int kingX = k.getX();
    int kingY = k.getY();
    
    
    tile[][] board = getTiles();
    
    
    setwhiteChecked(false);
    setblackChecked(false);
    
    
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        
        if(board[i][j].getPiece() != null && board[i][j].getPiece().getPlayer() != player){ //if we find a friendly piece check if it can attack the king next turn
          if(board[i][j].getPiece().validMove(kingX,kingY) == true && clearPath(kingX, kingY,board[i][j].getPiece()) && board[i][j].getPiece().getType() != "Knight"){
            
            if(player == 0){
              this.whiteChecked = true;
            }
            else{
              this.blackChecked = true;
            }
            
          }
          else if(board[i][j].getPiece().validMove(kingX,kingY) == true && board[i][j].getPiece().getType() == "Knight") {
            
            if(player == 0){
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
  
  
  
  /**
   * Moves a piece if the move is valid
   */
  public void testMove( int x, int y, piece p){
    //first store the piece that is about to be taken
    pieceTakenLastTurn = null;
    if(tiles[x][y].getPiece() != null && tiles[x][y].getPiece().getPlayer() != getWhoseMove()){
      pieceTakenLastTurn = tiles[x][y].getPiece(); 
    }
    
    if(tiles[x][y].getPiece() == null || (tiles[x][y].getPiece() != null && tiles[x][y].getPiece().getPlayer() != getWhoseMove())){
      
      if(p.validMove(x , y) && p.type == "Knight" && p.getPlayer() == getWhoseMove()){            //case of valid move
        getTiles()[p.getX()][p.getY()].setPiece(null);        //sets origional square piece to null
        getTiles()[x][y].setPiece(p);                         //sets new square piece to the piece which moved
        
        takenPieceIcon = getTiles()[x][y].getIcon();//save icon of taken piece
        
        getTiles()[x][y].setIcon(getTiles()[p.getX()][p.getY()].getIcon());  //sets the icon on the new square to the text of the old square
        getTiles()[p.getX()][p.getY()].setIcon(null);                          //sets the icon of the old square to null
        
        
        lookForCheck(getWhoseMove());
        if((getWhoseMove() == 0 && this.whiteChecked == true) || (getWhoseMove() == 1 && this.blackChecked == true)){      //Make sure the move doesn't put the player in check
          getTiles()[p.getX()][p.getY()].setPiece(p);        
          getTiles()[x][y].setPiece(null);
          
          getTiles()[p.getX()][p.getY()].setIcon(getTiles()[x][y].getIcon()); 
          getTiles()[x][y].setIcon(takenPieceIcon);
          
          
          this.blackChecked = false;
          this.whiteChecked = false;
        }else{
          this.pieceLastMoved = p;
          this.pieceLastMovedFromX = p.getX();
          this.pieceLastMovedFromY = p.getY();
          p.setPosition(x , y);         //the piece now knows its own position
          p.setMoved(p.getMoveCount()+1);
          setWhoseMove((getWhoseMove() +1) % 2);
          if(this.getWhoseMove() ==0){
            actionMenu.setMoveText("White's Move");
          }
          else{
            actionMenu.setMoveText("Black's Move");
          }
          lookForCheck(getWhoseMove());
          
        }
      }
      
      
      if(p.validMove(x , y) && p.type == "King" && p.getPlayer() == getWhoseMove()){            //case of valid move
        piece takenPiece = getTiles()[x][y].getPiece(); //save piece to be taken
        getTiles()[p.getX()][p.getY()].setPiece(null);        //sets origional square piece to null
        getTiles()[x][y].setPiece(p);                         //sets new square piece to the piece which moved
        
        takenPieceIcon = getTiles()[x][y].getIcon();//save icon of taken piece
        
        getTiles()[x][y].setIcon(getTiles()[p.getX()][p.getY()].getIcon());  //sets the icon on the new square to the text of the old square
        getTiles()[p.getX()][p.getY()].setIcon(null);                          //sets the icon of the old square to null
        
        
        int origionalX = p.getX();                                          //remember origional coordinates for piece
        int origionalY = p.getY();
        p.setPosition(x , y);                                               //bc of implementation of lookForCheck we have to update the kings postion
        
        lookForCheck(getWhoseMove());
        if((getWhoseMove() == 0 && this.whiteChecked == true) || (getWhoseMove() == 1 && this.blackChecked == true)){ //Make sure the move doesn't put the player in check
          getTiles()[origionalX][origionalY].setPiece(p);        
          getTiles()[x][y].setPiece(takenPiece);
          
          getTiles()[origionalX][origionalY].setIcon(getTiles()[x][y].getIcon()); 
          getTiles()[x][y].setIcon(takenPieceIcon);
          
          
          this.blackChecked = false;
          this.whiteChecked = false;
          p.setPosition(origionalX,origionalY);
        }else{
          this.pieceLastMoved = p;
          moveList[mv].setPieceLastMoved(p);
          this.pieceLastMovedFromX = origionalX;
          moveList[mv].setPieceLastMovedFromX(origionalX);
          this.pieceLastMovedFromY = origionalY;
          moveList[mv].setPieceLastMovedFromY(origionalY);
          p.setPosition(x , y);                                 //the piece now knows its own position
          p.setMoved(p.getMoveCount()+1);
          setWhoseMove((getWhoseMove() +1) % 2);
          if(this.getWhoseMove() ==0){
            actionMenu.setMoveText("White's Move");
          }
          else{
            actionMenu.setMoveText("Black's Move");
          }
          lookForCheck(getWhoseMove());
        }
      }else if(Math.abs(x - p.getX()) == 2 && p.type == "King" && p.getPlayer() == getWhoseMove()){
        
        castle((king)p , 0 , 0);
        castle((king)p , 0 , 7);
        castle((king)p , 7 , 0);
        castle((king)p , 7 , 7);
        lookForCheck(getWhoseMove());
      }
      
      
      
      
      if(x == p.getX()){
        if(p.validMove(x , y) && p.type == "Pawn" && !isOccupied(x , y) && clearPath( x , y , p ) && p.getPlayer() == getWhoseMove()){            //case of valid move
          getTiles()[p.getX()][p.getY()].setPiece(null);        //sets origional square piece to null
          getTiles()[x][y].setPiece(p);                         //sets new square piece to the piece which moved
          
          takenPieceIcon = getTiles()[x][y].getIcon();//save icon of taken piece
          
          getTiles()[x][y].setIcon(getTiles()[p.getX()][p.getY()].getIcon());  //sets the icon on the new square to the text of the old square
          getTiles()[p.getX()][p.getY()].setIcon(null);                          //sets the icon of the old square to null
          
          
          lookForCheck(getWhoseMove());
          if((getWhoseMove() == 0 && this.whiteChecked == true) || (getWhoseMove() == 1 && this.blackChecked == true)){   //Make sure the move doesn't put the player in check
            getTiles()[p.getX()][p.getY()].setPiece(p);        
            getTiles()[x][y].setPiece(null);
            
            getTiles()[p.getX()][p.getY()].setIcon(getTiles()[x][y].getIcon()); 
            getTiles()[x][y].setIcon(takenPieceIcon);
            
            
            this.blackChecked = false;
            this.whiteChecked = false;
          }else{
            this.pieceLastMoved = p;
            this.pieceLastMovedFromX = p.getX();
            this.pieceLastMovedFromY = p.getY();
            p.setPosition(x , y);                                 //the piece now knows its own position
            p.setMoved(p.getMoveCount()+1);
            setWhoseMove((getWhoseMove() +1) % 2);
            if(this.getWhoseMove() ==0){
              actionMenu.setMoveText("White's Move");
            }
            else{
              actionMenu.setMoveText("Black's Move");
            }
            lookForCheck(getWhoseMove());
          }
        }
      }
      else if(Math.abs(x - p.getX()) == 1 && isOccupied(x , y)){
        if(p.validMove(x , y) && p.type == "Pawn" && p.getPlayer() == getWhoseMove()){            //case of valid move
          getTiles()[p.getX()][p.getY()].setPiece(null);        //sets origional square piece to null
          getTiles()[x][y].setPiece(p);                         //sets new square piece to the piece which moved
          
          takenPieceIcon = getTiles()[x][y].getIcon();//save icon of taken piece
          
          getTiles()[x][y].setIcon(getTiles()[p.getX()][p.getY()].getIcon());  //sets the icon on the new square to the text of the old square
          getTiles()[p.getX()][p.getY()].setIcon(null);                          //sets the icon of the old square to null
          
          
          lookForCheck(getWhoseMove());
          if((getWhoseMove() == 0 && this.whiteChecked == true) || (getWhoseMove() == 1 && this.blackChecked == true)){   //Make sure the move doesn't put the player in check
            getTiles()[p.getX()][p.getY()].setPiece(p);        
            getTiles()[x][y].setPiece(null);
            
            getTiles()[p.getX()][p.getY()].setIcon(getTiles()[x][y].getIcon()); 
            getTiles()[x][y].setIcon(takenPieceIcon);
            
            this.blackChecked = false;
            this.whiteChecked = false;
          }else{
            this.pieceLastMoved = p;
            this.pieceLastMovedFromX = p.getX();
            this.pieceLastMovedFromY = p.getY();
            p.setPosition(x , y);                                 //the piece now knows its own position
            p.setMoved(p.getMoveCount()+1);
            setWhoseMove((getWhoseMove() +1) % 2);
            if(this.getWhoseMove() ==0){
              actionMenu.setMoveText("White's Move");
            }
            else{
              actionMenu.setMoveText("Black's Move");
            }
            lookForCheck(getWhoseMove());
          }
        }
      }
      
      
      if(p.validMove(x , y) && p.type == "Rook" && clearPath( x , y , p ) && p.getPlayer() == getWhoseMove()){            //case of valid move
        getTiles()[p.getX()][p.getY()].setPiece(null);        //sets origional square piece to null
        getTiles()[x][y].setPiece(p);                         //sets new square piece to the piece which moved
        
        takenPieceIcon = getTiles()[x][y].getIcon();//save icon of taken piece
        
        getTiles()[x][y].setIcon(getTiles()[p.getX()][p.getY()].getIcon());  //sets the icon on the new square to the text of the old square
        getTiles()[p.getX()][p.getY()].setIcon(null);                          //sets the icon of the old square to null
        
        
        lookForCheck(getWhoseMove());
        if((getWhoseMove() == 0 && this.whiteChecked == true) || (getWhoseMove() == 1 && this.blackChecked == true)){   //Make sure the move doesn't put the player in check
          getTiles()[p.getX()][p.getY()].setPiece(p);        
          getTiles()[x][y].setPiece(null);
          
          getTiles()[p.getX()][p.getY()].setIcon(getTiles()[x][y].getIcon()); 
          getTiles()[x][y].setIcon(takenPieceIcon);
          
          
          this.blackChecked = false;
          this.whiteChecked = false;
        }else{
          this.pieceLastMoved = p;
          this.pieceLastMovedFromX = p.getX();
          this.pieceLastMovedFromY = p.getY();
          p.setPosition(x , y);                                 //the piece now knows its own position
          p.setMoved(p.getMoveCount()+1);
          setWhoseMove((getWhoseMove() +1) % 2);
          if(this.getWhoseMove() ==0){
            actionMenu.setMoveText("White's Move");
          }
          else{
            actionMenu.setMoveText("Black's Move");
          }
          lookForCheck(getWhoseMove());
        }
      }
      
      
      if(p.validMove(x , y) && p.type == "Bishop" && clearPath( x , y , p ) && p.getPlayer() == getWhoseMove()){            //case of valid move
        getTiles()[p.getX()][p.getY()].setPiece(null);        //sets origional square piece to null
        getTiles()[x][y].setPiece(p);                         //sets new square piece to the piece which moved
        
        takenPieceIcon = getTiles()[x][y].getIcon();//save icon of taken piece
        
        getTiles()[x][y].setIcon(getTiles()[p.getX()][p.getY()].getIcon());  //sets the icon on the new square to the text of the old square
        getTiles()[p.getX()][p.getY()].setIcon(null);                          //sets the icon of the old square to null
        
        lookForCheck(getWhoseMove());
        if((getWhoseMove() == 0 && this.whiteChecked == true) || (getWhoseMove() == 1 && this.blackChecked == true)){   //Make sure the move doesn't put the player in check
          getTiles()[p.getX()][p.getY()].setPiece(p);        
          getTiles()[x][y].setPiece(null);
          
          getTiles()[p.getX()][p.getY()].setIcon(getTiles()[x][y].getIcon()); 
          getTiles()[x][y].setIcon(takenPieceIcon);
          
          this.blackChecked = false;
          this.whiteChecked = false;
        }else{
          this.pieceLastMoved = p;
          this.pieceLastMovedFromX = p.getX();
          this.pieceLastMovedFromY = p.getY();
          p.setPosition(x , y);                                 //the piece now knows its own position
          p.setMoved(p.getMoveCount()+1);
          setWhoseMove((getWhoseMove() +1) % 2);
          if(this.getWhoseMove() ==0){
            actionMenu.setMoveText("White's Move");
          }
          else{
            actionMenu.setMoveText("Black's Move");
          }
          lookForCheck(getWhoseMove());
        }
      }
      
      
      if(p.validMove(x , y) && p.type == "Queen" && clearPath( x , y , p )&& p.getPlayer() == getWhoseMove()){            //case of valid move
        getTiles()[p.getX()][p.getY()].setPiece(null);        //sets origional square piece to null
        getTiles()[x][y].setPiece(p);                         //sets new square piece to the piece which moved
        
        takenPieceIcon = getTiles()[x][y].getIcon();//save icon of taken piece
        
        getTiles()[x][y].setIcon(getTiles()[p.getX()][p.getY()].getIcon());  //sets the icon on the new square to the text of the old square
        getTiles()[p.getX()][p.getY()].setIcon(null);                          //sets the icon of the old square to null
        
        
        lookForCheck(getWhoseMove());
        if((getWhoseMove() == 0 && this.whiteChecked == true) || (getWhoseMove() == 1 && this.blackChecked == true)){    //Make sure the move doesn't put the player in check
          getTiles()[p.getX()][p.getY()].setPiece(p);        
          getTiles()[x][y].setPiece(null);
          
          getTiles()[p.getX()][p.getY()].setIcon(getTiles()[x][y].getIcon()); 
          getTiles()[x][y].setIcon(takenPieceIcon);
          
          
          this.blackChecked = false;
          this.whiteChecked = false;
        }else{
          this.pieceLastMoved = p;
          this.pieceLastMovedFromX = p.getX();
          this.pieceLastMovedFromY = p.getY();
          p.setPosition(x , y);                                 //the piece now knows its own position
          p.setMoved(p.getMoveCount()+1);
          setWhoseMove((getWhoseMove() +1) % 2);
          if(this.getWhoseMove() ==0){
            actionMenu.setMoveText("White's Move");
          }
          else{
            actionMenu.setMoveText("Black's Move");
          }
          lookForCheck(getWhoseMove());
        }
      }
    }
  }
  
  
  public boolean lookForCheckmate(int player){
    tile[][] board = getTiles();
    piece k = findKing(player);
    int kXCoor = k.getX();
    int kYCoor = k.getY();
    piece plm = pieceLastMoved;
    this.pieceLastMoved = null;     //set last piece moved to null in order to not make it dissapear 
    
    
    //first see if we can move the king directly out of check
    if(kXCoor == 0 && kYCoor == 0){
      
      //different directions
      testMove(kXCoor + 1, kYCoor , k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor, kYCoor + 1, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor + 1, kYCoor + 1, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      
    }else if(kXCoor == 0 && kYCoor > 0 && kYCoor < 7){
      
      //different directions
      testMove(kXCoor + 1, kYCoor , k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor + 1, kYCoor + 1, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor + 1, kYCoor - 1, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor, kYCoor + 1, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor, kYCoor - 1, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      
    }else if(kXCoor == 0 && kYCoor == 7){
      
      //different directions
      testMove(kXCoor + 1, kYCoor , k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor, kYCoor - 1, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor + 1, kYCoor - 1, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
    }else if(kXCoor > 0 && kXCoor < 7 && kYCoor == 0){
      
      //different directions
      testMove(kXCoor + 1, kYCoor + 1, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor, kYCoor + 1, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor - 1, kYCoor + 1, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor + 1, kYCoor, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor - 1 , kYCoor, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
    }else if(kXCoor > 0 && kXCoor < 7 && kYCoor == 7){
      
      //different directions
      testMove(kXCoor + 1, kYCoor - 1, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor, kYCoor - 1, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor - 1, kYCoor - 1, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor + 1, kYCoor, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor - 1 , kYCoor, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
    }else if(kXCoor > 0 && kXCoor < 7 && kYCoor > 0 && kYCoor < 7){
      
      //different directions
      testMove(kXCoor + 1, kYCoor + 1, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor, kYCoor + 1, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor - 1, kYCoor + 1, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor + 1, kYCoor, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor - 1 , kYCoor, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor + 1, kYCoor - 1, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor , kYCoor - 1, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor - 1, kYCoor - 1, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
    }else if(kXCoor == 7 && kYCoor == 0){
      
      //different directions
      testMove(kXCoor - 1, kYCoor , k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor, kYCoor + 1, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor - 1, kYCoor + 1, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
    }else if(kXCoor == 7 && kYCoor == 7){
      
      //different directions
      testMove(kXCoor - 1, kYCoor , k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor, kYCoor - 1, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor - 1, kYCoor - 1, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
    }else if(kXCoor == 7 && kYCoor > 0 && kYCoor < 7){
      
      //different directions
      testMove(kXCoor , kYCoor + 1, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor, kYCoor - 1, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor + 1, kYCoor + 1, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor, kYCoor + 1, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
      testMove(kXCoor - 1, kYCoor + 1, k);
      lookForCheck(player);
      if(whiteChecked == false && player == 0){
        unduMove();
        return false;
      }else if(blackChecked == false && player == 1){ 
        unduMove();
        return false;
      }
      unduMove();
      
    }
    
    
    //Next we can see if any piece can take the attacking piece or block
    
    
    
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        //for every space we need to see if there is a piece 
        if(board[i][j].getPiece() != null && board[i][j].getPiece().getPlayer() == player){
          //and if there is see if any possible moves take the king out of check
          
          // Following code is glitchy and causes stack overflow exceptions  */
          for(int v = 0; v < 8; v++){
            for(int c = 0; c < 8; c++){
              if(board[i][j].getPiece() != null && board[i][j].getPiece().validMove(v,c) == true){
                
                if(board[v][c].getPiece() != null && board[v][c].getPiece().getPlayer() != board[i][j].getPiece().getPlayer()){
                  
                  testMove(v,c,board[i][j].getPiece());                                      
                  lookForCheck(player);
                  if(this.whiteChecked == false && player == 0){
                    unduMove();
                    return false;
                  }
                  if(this.blackChecked == false && player == 1){
                    unduMove();
                    return false;
                  }
                  whiteChecked = false;
                  blackChecked = false;
                  unduMove();
                }
                if(board[v][c].getPiece() == null){
                  
                  testMove(v,c,board[i][j].getPiece());                                      
                  lookForCheck(player);
                  if(this.whiteChecked == false && player == 0){
                    unduMove();
                    return false;
                  }
                  if(this.blackChecked == false && player == 1){
                    unduMove();
                    return false;
                  }
                  whiteChecked = false;
                  blackChecked = false;
                  unduMove();
                }
                
              }
            }
          } 
          
          
        }
        
      }
    }
    return true;
    
    
  }
  
  
  
  
  
  /**
   * getter setters for checked statuses
   */
  
  public boolean getwhiteChecked(){
    return this.whiteChecked;
  }
  
  public boolean getblackChecked(){
    return this.blackChecked;
  }
  
  public void setwhiteChecked(boolean x){
    this.whiteChecked = x;
  }
  
  public void setblackChecked(boolean x){
    this.blackChecked = x;
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