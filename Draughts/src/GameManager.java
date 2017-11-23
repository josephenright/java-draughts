//

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameManager {

    private static Board board;
    private static Piece selectedPiece;
    private static ArrayList<Piece> canMoveTo = new ArrayList<>();
    private static ArrayList<Jump> availableJumps = new ArrayList<>();
    private static enum MoveType { INVALID, MOVE, JUMP };

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

    private static void getMoveToPieces(Piece p) {
        int x = p.getPosition().getX();
        int y = p.getPosition().getY();

        //If black, or can move either direction
        if (p.getStatus() == Piece.Status.BLACK || p.isKing()) {
            //get pieces at where == (x - 1) && (y == (y - 1) || y == (y + 1))

            //if x >= 0 for move
            if (x > 0) {
                //only go right when on leftmost black tiles
                if (x % 2 == 1 && y == 0) {
                    //canMoveTo.add();
                    if (isPieceFree(x - 1, y + 1) == MoveType.MOVE)
                        highLightMove(x - 1, y + 1);
                }
                //only go left when on rightmost black tiles
                else if (x % 2 == 0 && y == 7) {
                    if (isPieceFree(x - 1, y - 1) == MoveType.MOVE)
                        highLightMove(x - 1, y - 1);
                }
                //otherwise go both left and right
                else {
                    if (isPieceFree(x - 1, y - 1) == MoveType.MOVE)
                        highLightMove(x - 1, y - 1);
                    if (isPieceFree(x - 1, y + 1) == MoveType.MOVE)
                        highLightMove(x - 1, y + 1);
                }
            }
            //if (x > 0 for jump)
            if (x > 0) {

            }
        }
        if (p.getStatus() == Piece.Status.WHITE || p.isKing()){
            //get pieces at where == (x + 1) && (y == (y - 1) || y == (y + 1))

            //if x <= 7 for move
            if (x < 7) {
                //only go right when on leftmost black tiles
                if (x % 2 == 1 && y == 0) {
                    //canMoveTo.add();
                    if (isPieceFree(x + 1, y + 1) == MoveType.MOVE)
                        highLightMove(x + 1, y + 1);
                }
                //only go left when on rightmost black tiles
                else if (x % 2 == 0 && y == 7) {
                    if (isPieceFree(x + 1, y - 1) == MoveType.MOVE)
                        highLightMove(x + 1, y - 1);
                }
                //otherwise go both left and right
                else {
                    if (isPieceFree(x + 1, y - 1) == MoveType.MOVE)
                        highLightMove(x + 1, y - 1);
                    if (isPieceFree(x + 1, y + 1) == MoveType.MOVE)
                        highLightMove(x + 1, y + 1);
                }
            }
            //if (x < 7 for jump)
        }
    }

    private static MoveType isPieceFree(int x, int y) {
        Piece p = board.getPieces().get(x).get(y);
        if (p.getStatus() == Piece.Status.NONE) {
            //canMoveTo.add(p);               //
            //p.setEnabled(true);             // Do these in a different method
            //p.setBackground(Color.GREEN);   //
            return MoveType.MOVE;
        }
        else if (p.getStatus() != selectedPiece.getStatus()) {
            //Stop invalid jumps
            if (x < 1 || x > 6)
                return MoveType.INVALID;

            //get piece 'behind' p
            //if this piece is free add to availableJumps

            //// ****** change selectedPiece to a parameter Piece
            Piece jumpable = p;
            int jumpX = (x - selectedPiece.getPosition().getX());
            int jumpY = (y - selectedPiece.getPosition().getY());

            //Stop invalid jumps
            if (jumpY + y < 0 || jumpY + y > 7)
                return MoveType.INVALID;

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
                p = Piece.copyPieceKeepPosition(selectedPiece, p);
                getMoveToPieces(p); //p.getStatus() != selectedPiece.getStatus()
                p = Piece.copyPiece(tempPiece);
                */
            }

            //isPieceFree((jumpX * 2) + x, (jumpY * 2) + y);
            return MoveType.JUMP;
        }
        else
            return MoveType.INVALID;
    }

    private static void highLightMove(int x, int y) {
        Piece p = board.getPieces().get(x).get(y);
            canMoveTo.add(p);
            p.setEnabled(true);
            p.setBackground(Color.GREEN);
    }
    /*
    private static void isPieceFree2(int x, int y) {
        Piece p = board.getPieces().get(x).get(y);
        if (p.getStatus() == Piece.Status.NONE) {
            canMoveTo.add(p);
            p.setEnabled(true);
            p.setBackground(Color.GREEN);
        }
        else if (p.getStatus() != selectedPiece.getStatus()){
            if (x < 1 || x > 6)
                return;
            //get piece 'behind' p
            //if this piece is free add to availableJumps
            Piece jumpable = p;
            int jumpX = (x - selectedPiece.getPosition().getX()) + x;
            int jumpY = (y - selectedPiece.getPosition().getY()) + y;

            if (jumpY < 0 || jumpY > 7)
                return;

            p = board.getPieces().get(jumpX).get(jumpY);
            if (p.getStatus() == Piece.Status.NONE) {
                ArrayList<Piece> jumpablePieces = new ArrayList<Piece>();
                jumpablePieces.add(jumpable);
                availableJumps.add(new Jump(jumpablePieces, p));
                p.setEnabled(true);
                p.setBackground(Color.GREEN);
            }
        }
    }
    */
    private static void deselectCanMoveTo() {
        for (Piece p : canMoveTo) {
            if (p.getStatus() == Piece.Status.NONE)
                p.setEnabled(false);
            p.setBackground(Color.BLACK);
        }
        canMoveTo.clear();
        for (Jump j : availableJumps) {
            if (j.getDestination().getStatus() == Piece.Status.NONE)
                j.getDestination().setEnabled(false);
            j.getDestination().setBackground(Color.BLACK);
        }
        availableJumps.clear();
    }



    static class PieceListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Piece p = (Piece)e.getSource();
            if (p.getStatus() != Piece.Status.NONE) {
                deselectCanMoveTo();
                selectPiece(p);
                getMoveToPieces(p);
            }
            else if (canMoveTo.contains(p)){
                Piece.movePiece(selectedPiece, p);
                deselectCanMoveTo();
                deselectPiece();
            }
            else {
                for (Jump j : availableJumps) {
                    if (p == j.getDestination()) {
                        Piece.jumpPiece(selectedPiece, j.getJumpablePiece(), j.getDestination());
                    }
                }
                deselectCanMoveTo();
                deselectPiece();
            }
        }
    }

    public static class Jump {
        private ArrayList<Piece> jumpablePieces;
        private Piece destination;

        public Jump() {
            setJumpablePiece(null);
            setDestination(null);
        }

        public Jump(ArrayList<Piece> jumpablePieces, Piece destination) {
            setJumpablePiece(jumpablePieces);
            setDestination(destination);
        }

        public ArrayList<Piece> getJumpablePiece() {
            return jumpablePieces;
        }
        public void setJumpablePiece(ArrayList<Piece> jumpablePieces) {
            this.jumpablePieces = jumpablePieces;
        }

        public Piece getDestination() {
            return destination;
        }
        public void setDestination(Piece destination) {
            this.destination = destination;
        }
    }
}
