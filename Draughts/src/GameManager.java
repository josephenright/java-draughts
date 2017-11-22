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
    private static void getMoveToPieces(Piece p) {
        int x = p.getPosition().getX();
        int y = p.getPosition().getY();

        //If black, or can move either direction
        if (p.getStatus() == Piece.Status.BLACK || p.isKing()) {
            //get pieces at where == (x - 1) && (y == (y - 1) || y == (y + 1))

            //if x >= 0 for move
            if (x >= 0) {
                //only go right when on leftmost black tiles
                if (x % 2 == 0 && y == 0) {

                }
                //only go left when on rightmost black tiles
                else if (x % 2 == 1 && y == 7) {

                }
                //otherwise go both left and right
                else {

                }
            }
            //if (x > 0 for jump)
        }
        if (p.getStatus() == Piece.Status.WHITE || p.isKing()){
            //get pieces at where == (x + 1) && (y == (y - 1) || y == (y + 1))

            //if x <= 7 for move
            if (x <= 7) {
                //only go right when on leftmost black tiles
                if (x % 2 == 0 && y == 0) {

                }
                //only go left when on rightmost black tiles
                else if (x % 2 == 1 && y == 7) {

                }
                //otherwise go both left and right
                else {

                }
            }
            //if (x < 7 for jump)
        }
    }

    static class PieceListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            selectPiece((Piece)e.getSource());
        }
    }
}
