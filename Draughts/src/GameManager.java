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
    private static List<Move> possibleMoves = new ArrayList<>();

    private static boolean player1turn; //true = player 1's turn. false = player 2's turn
    private static GameMenu gameMenu; //JMenu creation and handler

    public static void main(String[] args) {
        setUpBoard(null);
    }

    //Create and display Board. Feeding an argument will load that board
    //(ie when loading from file). null argument creates a new board.
    public static void setUpBoard(Board b) {
        //If there is already a Board, close it
        if (board != null) {
            board.dispose();
        }
        //Default values, for when a new game is started
        if (b == null) {
            player1turn = true;
            Piece.resetPieceCount();
        }
        //else //JFrame will not change location when loading games
            //b.setLocation(board.getLocation());

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
        //Create and add JMenu
        gameMenu = new GameMenu();
        board.setJMenuBar(gameMenu.createMenu());

        board.setVisible(true);
    }



    /* Start of movement methods */

    //Part 1 of getting possible moves for the selected Piece.
    //use (Move m = null) for first call of the method
    //Moves are looped in for multi-jumps
    private static void getMoves(Piece selected, Move m) {
        int x = selected.getPosition().getX();
        int y = selected.getPosition().getY();

        if (selected.getStatus() == Piece.Status.BLACK || selected.isKing()) {
            //if x > 0 for moving up the board
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
            //if x <=7 for moving down the board
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

    //Part 2 of getting possible moves for the selected Piece.
    //use (Move m = null) for first call of the method
    //Moves are looped in for multi-jumps
    private static void highlightFreePiece(int x, int y, Piece p, Move m) {
        boolean jumpOnly = (m == null)? false : true;
        Move move = isPieceFree(x, y, p);
        //If move is of type Move, and this is a multi-jump, or it's a jump move
        if ((move.getMoveType() == Move.MoveType.MOVE && !jumpOnly) ||
            (move.getMoveType() == Move.MoveType.JUMP))
            highLightMove(move);
        /*
        else if (move.getMoveType() == Move.MoveType.JUMP) {
            highLightMove(move);
        }*/
    }

    //Part 3 (final) of getting possible moves for the selected Piece.
    //use (Move m = null) for first call of the method
    //Moves are looped in for multi-jumps
    private static Move isPieceFree(int x, int y, Piece selected) {
        Piece destination = board.getPieces().get(x).get(y);

        //if regular move is possible, return Move
        if (destination.getStatus() == Piece.Status.NONE)
            return new Move(selected, destination);

        //if jump is possible, return Move
        else if (destination.getStatus() != selected.getStatus()) {
            //Stop invalid jumps (off board)
            if (x < 1 || x > 6)
                return new Move();

            //assign piece to be jumped, and get the position behind jumpable
            Piece jumpable = destination;
            int jumpX = x - selected.getPosition().getX();
            int jumpY = y - selected.getPosition().getY();

            //Stop invalid jumps (off board)
            if (jumpY + y < 0 || jumpY + y > 7 || jumpX + x < 0 || jumpX + x > 7)
                return new Move();

            //get piece at the position behind jumpable
            destination = board.getPieces().get(jumpX + x).get(jumpY + y);

            //If the piece is free, return the jump Move
            if (destination.getStatus() == Piece.Status.NONE) {
                return new Move(selected, destination, jumpable);
            }
        }
        //otherwise move is invalid
        return new Move();
    }

    /* End of movement methods */



    /* Start of selection and highlight methods */

    //Highlight the Move generated by isPieceFree
    private static void highLightMove(Move m) {
        int x = m.getDestinationPiece().getPosition().getX();
        int y = m.getDestinationPiece().getPosition().getY();

        Piece p = board.getPieces().get(x).get(y);
            //canMoveTo.add(p);
            possibleMoves.add(m);
            p.setEnabled(true);
            p.setBackground(Color.GREEN);
        if (m.getJumpablePieces() != null)
            m.getJumpablePieces().setBackground(Color.YELLOW);
    }

    //Highlight and create a reference to selected piece
    private static void selectPiece(Piece p) {
        if (selectedPiece != null)
            selectedPiece.setBackground(Color.BLACK);
        selectedPiece = p;
        p.setBackground(Color.CYAN);
    }

    //Unhighlight and unselect the currently selected piece
    private static void deselectPiece() {
        if (selectedPiece != null) {
            selectedPiece.setBackground(Color.BLACK);
            if (selectedPiece.getStatus() == Piece.Status.NONE)
                selectedPiece.setEnabled(false);
            selectedPiece = null;
        }
    }

    //Deselct/unhighlight moves
    private static void deselectCanMoveTo() {
        for (Move m : possibleMoves) {
            Piece p = m.getDestinationPiece();
            if (p.getStatus() == Piece.Status.NONE)
                p.setEnabled(false);
            p.setBackground(Color.BLACK);
            if (m.getJumpablePieces() != null)
                    m.getJumpablePieces().setBackground(Color.BLACK);
        }
        possibleMoves.clear();
    }

    /* End of selection and highlight methods */

    private static void changeTurn() {
        player1turn = !player1turn;
        gameMenu.changeTurn(player1turn);
    }


    //Get game board/all pieces
    public static Board getBoard() {
        return board;
    }

    public static boolean getTurn() {
        return player1turn;
    }

    //Set player1Turn - Used when loading game from file
    public static void setPlayer1turn(boolean player1turn) {
        GameManager.player1turn = player1turn;
    }



    //ActionListener for the game board
    static class PieceListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Piece p = (Piece)e.getSource();
            handleMove(p);
        }

        //handleMove called by actionPerformed.
        private void handleMove(Piece p) {
            handleMove(p,null);
        }

        //handleMove called from within itself
        private void handleMove(Piece p, Move loopedMove) {
            //If piece is black and it's blacks turn, or vice versa
            if ((player1turn && p.getStatus() == Piece.Status.BLACK) || (!player1turn && p.getStatus() == Piece.Status.WHITE)) {
                //Deselect any currently selected moves, and get moves for this piece
                deselectCanMoveTo();
                selectPiece(p);
                getMoves(p, loopedMove);

                //If there are no available moves, don't highlight anything
                if (possibleMoves.size() == 0) {
                    deselectPiece();
                    deselectCanMoveTo();

                    //If there are no available moves, and there is a looped move, switch turns
                    if (loopedMove != null) {
                        changeTurn();

                        //if all enemy pieces are gone, player wins
                        //do this
                        if (Piece.getWhitePieces() == 0) {
                            JOptionPane.showMessageDialog(null, "Black wins!");
                            gameOver();
                        }
                        else if (Piece.getBlackPieces() == 0) {
                            JOptionPane.showMessageDialog(null, "White wins!");
                            gameOver();
                        }
                    }
                }
            }
            else {
                //Dummy move must be assigned to keep compiler happy
                Move move = new Move();

                //move = the first available move
                for(Move m : possibleMoves) {
                    if (m.getDestinationPiece() == p) {// && m.getMoveType() == Move.MoveType.MOVE) {
                        move = m;
                        break;
                    }
                }
                if (move != null) {
                    if (move.getMoveType() == Move.MoveType.MOVE) {
                        //Move the piece, and switch turn
                        Piece.movePiece(move.getSelectedPiece(), p);
                        deselectCanMoveTo();
                        deselectPiece();
                        changeTurn();
                    }
                    else if (move.getMoveType() == Move.MoveType.JUMP) {
                        //do jump stuff
                        Piece.jumpPiece(move.getSelectedPiece(), move.getJumpablePieces(), move.getDestinationPiece());

                        //update the UI counters
                        gameMenu.setWhiteCount();
                        gameMenu.setBlackCount();

                        //deselect the selected pieces
                        deselectCanMoveTo();
                        deselectPiece();

                        Move moveToLoop = new Move(move.getSelectedPiece(), move.getDestinationPiece(), null);

                        //loop the move (find out if multi-jumps are available)
                        handleMove(move.getDestinationPiece(), moveToLoop);
                    }
                }
            }
        }

        private void gameOver() {
            if (JOptionPane.showConfirmDialog(null, "Would you like " +
                "to start a new game?") == JOptionPane.YES_OPTION)
                setUpBoard(null);
            else {
                JOptionPane.showMessageDialog(null, "Thanks for playing");
                System.exit(0);
            }
        }
    }

}
