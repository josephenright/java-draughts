//

import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private static Board board;
    private static Piece selectedPiece;
    //private static ArrayList<Piece> canMoveTo = new ArrayList<>();
    private static List<Move> possibleMoves = new ArrayList<>();
    //private static enum MoveType { INVALID, MOVE, JUMP };

    private static boolean player1turn;
    private static GameMenu gameMenu;

    public static void main(String[] args) {
        setUpBoard(null);
    }

    public static void setUpBoard(Board b) {
        if (board != null)
            board.dispose();
        if (board == null) { //not working
            player1turn = true;
            Piece.resetPieceCount();
        }
            //board.setVisible(false);

        board = (b == null)? new Board() : b;
        possibleMoves.clear();

        //Add action listener to pieces
        PieceListener pieceListener = new PieceListener();
        for (ArrayList<Piece> list : board.getPieces()) {
            for (Piece p : list) {
                if (!p.isWhite())
                    p.addActionListener(pieceListener);
            }
        }
        gameMenu = new GameMenu();
        board.setJMenuBar(gameMenu.createMenu());
        board.setVisible(true);
    }


    private static void selectPiece(Piece p) {
        if (selectedPiece != null)
            selectedPiece.setBackground(Color.BLACK);
        selectedPiece = p;
        p.setBackground(Color.CYAN);
    }
    private static void deselectPiece() {
        if (selectedPiece != null) {
            selectedPiece.setBackground(Color.BLACK);
            if (selectedPiece.getStatus() == Piece.Status.NONE)
                selectedPiece.setEnabled(false);
            selectedPiece = null;
        }
    }

    public static void getMoves(Piece selected) {
        getMoves(selected, null);
    }
    public static void getMoves(Piece selected, Move m) {
        int x = selected.getPosition().getX();
        int y = selected.getPosition().getY();

        if (selected.getStatus() == Piece.Status.BLACK || selected.isKing()) {
            //get pieces at where == (x - 1) && (y == (y - 1) || y == (y + 1))

            //if x >= 0 for move
            if (x > 0) {// && y >= 0 && y < 8) {
                //only go right when on leftmost black tiles
                if (x % 2 == 1 && y == 0)
                    highlightFreePiece(x - 1, y + 1, selected, m);
                //only go left when on rightmost black tiles
                else if (x % 2 == 0 && y == 7)
                    highlightFreePiece(x - 1, y - 1, selected, m);
                //otherwise go both left and right
                else {
                    highlightFreePiece(x - 1, y - 1, selected, m);
                    highlightFreePiece(x - 1, y + 1, selected, m);
                }
            }
        }
        if (selected.getStatus() == Piece.Status.WHITE || selected.isKing()){
            //get pieces at where == (x + 1) && (y == (y - 1) || y == (y + 1))

            //if x <= 7 for move
            if (x < 7 && y >= 0 && y < 8) {
                //only go right when on leftmost black tiles
                if (x % 2 == 1 && y == 0)
                    highlightFreePiece(x + 1, y + 1, selected, m);
                //only go left when on rightmost black tiles
                else if (x % 2 == 0 && y == 7)
                    highlightFreePiece(x + 1, y - 1, selected, m);
                //otherwise go both left and right
                else {
                    highlightFreePiece(x + 1, y - 1, selected, m);
                    highlightFreePiece(x + 1, y + 1, selected, m);
                }
            }
        }
    }

    private static void highlightFreePiece(int x, int y, Piece p, Move m) {
        boolean jumpOnly = (m == null)? false : true;
        Move move = isPieceFree(x, y, p, m);
        if (move.getMoveType() == Move.MoveType.MOVE && !jumpOnly)
            highLightMove(move);
        else if (move.getMoveType() == Move.MoveType.JUMP) {
            highLightMove(move);
        }
    }

    //method for check move
    //if can't move: jump
    //if can't jump: move is invalid

    private static Move isPieceFree(int x, int y, Piece selected, Move loopedMove) {
        //if (x < 0 || x > 7 || y < 0 || y > 7)
            //return new Move();

        Piece destination = board.getPieces().get(x).get(y);
        //check is move is possible
        if (destination.getStatus() == Piece.Status.NONE) {
            //canMoveTo.add(p);               //
            //p.setEnabled(true);             // Do these in a different method
            //p.setBackground(Color.GREEN);   //
            return new Move(selected, destination);
        }
        //check if jump is possible
        //for loop - check how jumps possible
        else if (destination.getStatus() != selected.getStatus()) {

            //Stop invalid jumps (off board)
            if (x < 1 || x > 6)
                return new Move();

            Piece jumpable = destination;
            int jumpX = x - selected.getPosition().getX();
            int jumpY = y - selected.getPosition().getY();

            //Stop invalid jumps (off board)
            if (jumpY + y < 0 || jumpY + y > 7 || jumpX + x < 0 || jumpX + x > 7)
                return new Move();

            destination = board.getPieces().get(jumpX + x).get(jumpY + y);


            if (destination.getStatus() == Piece.Status.NONE) {
                Move m;
                if (loopedMove == null) {
                    ArrayList tempList = new ArrayList<Piece>();
                    tempList.add(jumpable);
                    m = new Move(selected, destination, tempList);
                }
                else {
                    //If jumpable piece is already in the list of jumpable pieces, this move is invalid.
                    //Pieces cannot be jumped twice
                    if (loopedMove.getJumpablePieces().contains(jumpable))
                        return new Move();

                    /*      .clone        */
                    ArrayList tempList = (ArrayList)loopedMove.getJumpablePieces().clone();
                    tempList.add(jumpable);
                    m = new Move(loopedMove.getDestinationPiece(), destination, tempList);
                    //m.addJumpablePiece(jumpable);
                    //m.setDestinationPiece(destination);
                }
                //System.out.println("Init: " + m);
                /*
                if (m.getJumpablePieces().size() < 9) {
                    Piece tempPiece = Piece.copyPiece(destination);
                    tempPiece = Piece.copyPieceKeepPosition(selected, destination);

                    //feed in the move m. if getMoves is fed null, will create it's own move.
                    ArrayList<Piece> tempList = (ArrayList)m.getJumpablePieces().clone();
                    getMoves(tempPiece, new Move(m.getSelectedPiece(), m.getDestinationPiece(), tempList));
                    //destination = Piece.copyPiece(tempPiece);
                }
                */
                System.out.println("Return: " + m);
                //
                return m;
            }
        }
        //else
        return new Move();
    }

    private static void highLightMove(Move m) {
        int x = m.getDestinationPiece().getPosition().getX();
        int y = m.getDestinationPiece().getPosition().getY();

        Piece p = board.getPieces().get(x).get(y);
            //canMoveTo.add(p);
            possibleMoves.add(m);
            p.setEnabled(true);
            p.setBackground(Color.GREEN);
        if (m.getJumpablePieces() != null) {
            for(Piece jumpable : m.getJumpablePieces())
                jumpable.setBackground(Color.YELLOW);
        }
    }

    private static void deselectCanMoveTo() {
        for (Move m : possibleMoves) {
            Piece p = m.getDestinationPiece();
            if (p.getStatus() == Piece.Status.NONE)
                p.setEnabled(false);
            p.setBackground(Color.BLACK);
            if (m.getJumpablePieces() != null) {
                for(Piece jumpable : m.getJumpablePieces())
                    jumpable.setBackground(Color.BLACK);
            }
        }
        //canMoveTo.clear();
        /*
        possibleMoves.clear();
        for (Move m : possibleMoves) {
            if (m.getDestinationPiece().getStatus() == Piece.Status.NONE)
                m.getDestinationPiece().setEnabled(false);
            m.getDestinationPiece().setBackground(Color.BLACK);
        }*/
        possibleMoves.clear();
    }



    static class PieceListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Piece p = (Piece)e.getSource();
            handleMove(p);
        }

        private void handleMove(Piece p) {
            handleMove(p,null);
        }
        private void handleMove(Piece p, Move loopedMove) {
            //if (p.getStatus() != Piece.Status.NONE) {
            if ((player1turn && p.getStatus() == Piece.Status.BLACK) || (!player1turn && p.getStatus() == Piece.Status.WHITE)) {
                deselectCanMoveTo();
                selectPiece(p);
                getMoves(p, loopedMove);
                if (possibleMoves.size() == 0) {
                    deselectPiece();
                    deselectCanMoveTo();
                    if (loopedMove != null)
                        changeTurn();
                }
                System.out.println("Possible moves:\t" + possibleMoves.size());
            }
            else {//if ((player1turn && p.getStatus() == Piece.Status.BLACK) || (!player1turn && p.getStatus() == Piece.Status.WHITE)) {
                Move move = new Move();
                //CHANGE THIS. CAN BE BETTER
                //System.out.println("Possible moves:\t" + possibleMoves.size());
                for(Move m : possibleMoves)
                    if (m.getDestinationPiece() == p) {// && m.getMoveType() == Move.MoveType.MOVE) {
                        move = m;
                        break;
                    }
                if (move.getMoveType() == Move.MoveType.MOVE) {
                    Piece.movePiece(move.getSelectedPiece(), p);
                    deselectCanMoveTo();
                    deselectPiece();
                    changeTurn();
                }
                else if (move.getMoveType() == Move.MoveType.JUMP) {
                    //do jump stuff
                    Piece.jumpPiece(move.getSelectedPiece(), move.getJumpablePieces(), move.getDestinationPiece());
                    gameMenu.setWhiteCount();
                    gameMenu.setBlackCount();
                    //remove moves that are no longer possible
                    //feed in the move m. if getMoves is fed null, will create it's own move.

                    deselectCanMoveTo();
                    deselectPiece();

                        //ArrayList<Piece> tempList = (ArrayList)move.getJumpablePieces().clone();
                        Move newMove = new Move(move.getSelectedPiece(), move.getDestinationPiece(), new ArrayList<Piece>());
                        //getMoves(move.getSelectedPiece(), newMove);
                        //getMoves(move.getDestinationPiece());

                        //trigger method again
                        handleMove(move.getDestinationPiece(), newMove);

                    //trigger actionPerformed
                    //if no more jumps available
                    // {
                    //deselectCanMoveTo();
                    //deselectPiece();
                    //}
                    //else highlight still available moves
                }
            }
        }
    }

    private static void changeTurn() {
        player1turn = !player1turn;
        gameMenu.changeTurn(player1turn);
    }

    public static Board getBoard() {
        return board;
    }
    public static boolean getTurn() {
        return player1turn;
    }
    public static void setPlayer1turn(boolean player1turn) {
        GameManager.player1turn = player1turn;
    }
}
