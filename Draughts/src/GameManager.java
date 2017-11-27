//

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

    public static void main(String[] args) {
        board = new Board();
        board.setVisible(true);
        player1turn = false;

        //Add action listener to pieces
        PieceListener pieceListener = new PieceListener();
        for(ArrayList<Piece> list : board.getPieces()) {
            for (Piece p : list) {
                if (!p.isWhite())
                    p.addActionListener(pieceListener);
            }
        }
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
            if (x > 0) {
                //only go right when on leftmost black tiles
                if (x % 2 == 1 && y == 0)
                    highlightFreePiece(x - 1, y - 1, selected, m);
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
            if (x < 7) {
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

            //Stop invalid jumps
            if (x < 1 || x > 6)
                return new Move();

            Piece jumpable = destination;
            int jumpX = x - selected.getPosition().getX();
            int jumpY = y - selected.getPosition().getY();

            //Stop invalid jumps
            if (jumpY + y < 0 || jumpY + y > 7 || jumpX + x < 0 || jumpX + x > 7)
                return new Move();

            destination = board.getPieces().get(jumpX + x).get(jumpY + y);
            ArrayList<Piece> list = new ArrayList<Piece>();
            //list.add(jumpable);

            //Move m = null;
            if (destination.getStatus() == Piece.Status.NONE) {
                Move m;
                if (loopedMove == null) {
                    list.add(jumpable);
                    m = new Move(selected, destination, list);
                }
                else {
                    //m = move;
                    m = new Move(loopedMove.getSelectedPiece(), loopedMove.getDestinationPiece(), loopedMove.getJumpablePieces());
                    //m.addJumpablePiece(jumpable);
                    m.setDestinationPiece(destination);
                }
                if (m.getJumpablePieces().size() < 3) {
                    m.addJumpablePiece(jumpable);
                    //Piece tempPiece = Piece.copyPiece(destination);
                    //destination = Piece.copyPieceKeepPosition(selected, destination);

                    Piece tempPiece = Piece.copyPiece(destination);
                    tempPiece = Piece.copyPieceKeepPosition(selected, destination);

                    //feed in the move m. if getMoves is fed null, will create it's own move.
                    getMoves(tempPiece, m);
                    //destination = Piece.copyPiece(tempPiece);
                }
                return m;
            }


            //if (isPieceFree(x, y, p).getMoveType() == Move.MoveType.MOVE)
                //highLightMove(isPieceFree(x, y, p));
            //get copy of a piece

            /*
            p = board.getPieces().get(jumpX + x).get(jumpY + y);
            if (p.getStatus() == Piece.Status.NONE) {
                ArrayList<Piece> jumpablePieces = new ArrayList<Piece>();
                jumpablePieces.add(jumpable);
                availableJumps.add(new Jump(jumpablePieces, p));
                p.setEnabled(true);
                p.setBackground(Color.GREEN);

                /*
                //copy selectedPiece's info to p, to find jumps from that point
                Piece tempPiece = Piece.copyPiece(p);
                p = Piece.copyPieceKeepPosition(selected, p);
                getMoveToPieces(p, selected); //p.getStatus() != selectedPiece.getStatus()
                p = Piece.copyPiece(tempPiece);
                * /
            }

            //isPieceFree((jumpX * 2) + x, (jumpY * 2) + y);
            */
            //return MoveType.JUMP;
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
        possibleMoves.clear();
        for (Move m : possibleMoves) {
            if (m.getDestinationPiece().getStatus() == Piece.Status.NONE)
                m.getDestinationPiece().setEnabled(false);
            m.getDestinationPiece().setBackground(Color.BLACK);
        }
        possibleMoves.clear();
    }



    static class PieceListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Piece p = (Piece)e.getSource();
            if (p.getStatus() != Piece.Status.NONE) {
                deselectCanMoveTo();
                selectPiece(p);
                getMoves(p);
            }
            else {
                Move move = new Move();
                for(Move m : possibleMoves)
                    if (m.getDestinationPiece() == p) {// && m.getMoveType() == Move.MoveType.MOVE) {
                        move = m;

                    }
                    if (move.getMoveType() == Move.MoveType.MOVE) {
                        Piece.movePiece(move.getSelectedPiece(), p);
                        deselectCanMoveTo();
                        deselectPiece();
                    }
                    else if (move.getMoveType() == Move.MoveType.JUMP) {
                        //do jump stuff
                        Piece.jumpPiece(move.getSelectedPiece(), move.getJumpablePieces(), move.getDestinationPiece());
                        deselectCanMoveTo();
                        deselectPiece();
                    }
            }

        }
    }


}
