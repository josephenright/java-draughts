//Main driver class

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Game {

    static Board board;

    public static void main(String[] args) {
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
            if (status == Piece.Status.BLACK && hasFriendlyPiece((Piece)e.getSource())) {
                System.out.print("PIECE");
            }
        }
    }

    static boolean hasFriendlyPiece(Piece p) {
        int x = p.getPosition().getX();
        int y = p.getPosition().getY();

        if (Piece.getStatus(board.getPieces().get(x).get(y)) == p.getStatus())
            return true;
        return false;
    }
}
