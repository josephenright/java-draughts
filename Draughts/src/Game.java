//Main driver class

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Game {

    static Board board;
    static boolean p1Turn;

    public static void main(String[] args) {
        boolean p1Turn = true;
        board = new Board();
        for (List<Piece> l : board.getPieces()) {
            for (JButton j : l) {
                j.addActionListener(new PieceListener());
            }
        }

    }


    static class PieceListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Piece.Status status = Piece.getStatus((Piece)e.getSource());
            System.out.println("Click: " + status);
            //if piece is white and there is a piece in the spaces of the row + 1
            //or if piece is black and there is a piece in the spaces of the row -1
            if (status == Piece.Status.BLACK && hasFriendlyPiece((Piece)e.getSource(), false)) {
                System.out.print("PIECE");
            }
        }
    }

    static boolean hasFriendlyPiece(Piece p) {
        return hasFriendlyPiece(p, false);
    }

    static boolean hasFriendlyPiece(Piece p, boolean isKing) {
        if (isKing) {
            if (hasFriendlyPiece(false, p) && hasFriendlyPiece(true, p))
                return true;
            return false;
        }
        else {
            return hasFriendlyPiece(false, p);
        }
    }

    private static boolean hasFriendlyPiece(boolean flipped, Piece p) {
        int x = p.getPosition().getX();
        int y = p.getPosition().getY();

        int multiplier = (flipped)? -1 : 1;
        x += (p.isWhite())? 1 * multiplier : -1 * multiplier;

        int i = (x % 2 == 0)? 1 : -1;


        System.out.println(p.getPosition().toString());


        System.out.println((Piece.getStatus(board.getPieces().get(x).get(y)) ));//== p.getStatus())
            //return true;
        return false;
    }
}
