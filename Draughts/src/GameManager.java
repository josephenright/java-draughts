//

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameManager {

    private static Board board;
    private static Piece selectedPiece;
    private static ArrayList<Piece> canMoveTo = new ArrayList<>();

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
            if (x >= 0) {
                //only go right when on leftmost black tiles
                if (x % 2 == 1 && y == 0) {
                    //canMoveTo.add();
                    isPieceFree(x - 1, y + 1);
                }
                //only go left when on rightmost black tiles
                else if (x % 2 == 0 && y == 7) {
                    isPieceFree(x - 1, y - 1);
                }
                //otherwise go both left and right
                else {
                    isPieceFree(x - 1, y - 1);
                    isPieceFree(x - 1, y + 1);
                }
            }
            //if (x > 0 for jump)
        }
        if (p.getStatus() == Piece.Status.WHITE || p.isKing()){
            //get pieces at where == (x + 1) && (y == (y - 1) || y == (y + 1))

            //if x <= 7 for move
            if (x <= 7) {
                //only go right when on leftmost black tiles
                if (x % 2 == 1 && y == 0) {
                    //canMoveTo.add();
                    isPieceFree(x + 1, y + 1);
                }
                //only go left when on rightmost black tiles
                else if (x % 2 == 0 && y == 7) {
                    isPieceFree(x + 1, y - 1);
                }
                //otherwise go both left and right
                else {
                    isPieceFree(x + 1, y - 1);
                    isPieceFree(x + 1, y + 1);
                }
            }
            //if (x < 7 for jump)
        }
    }

    private static void isPieceFree(int x, int y) {
        Piece p = board.getPieces().get(x).get(y);
        if (p.getStatus() == Piece.Status.NONE) {
            canMoveTo.add(p);
            p.setEnabled(true);
            p.setBackground(Color.GREEN);
        }
        else {
            //get piece 'behind' p
            //if this piece is free
        }
    }

    private static void deselectCanMoveTo() {
        for (Piece p : canMoveTo) {
            if (p.getStatus() == Piece.Status.NONE)
                p.setEnabled(false);
            p.setBackground(Color.BLACK);
        }
        canMoveTo.clear();
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
        }
    }

    private static class Jump {
        private Piece jumpablePiece;
        private Piece destination;

        public Jump() {

        }

        public Jump(Piece jumpablePiece, Piece destination) {
            setJumpablePiece(jumpablePiece);
            setDestination(destination);
        }

        public Piece getJumpablePiece() {
            return jumpablePiece;
        }
        public void setJumpablePiece(Piece jumpablePiece) {
            this.jumpablePiece = jumpablePiece;
        }

        public Piece getDestination() {
            return destination;
        }
        public void setDestination(Piece destination) {
            this.destination = destination;
        }
    }
}
