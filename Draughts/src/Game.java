//Main driver class

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Game {

    static Board board;
    static boolean p1Turn;
    static List<Piece> highlighted = new ArrayList<>();
    static Piece selectedPiece;
    static Piece jumpablePiece;
    static JMenuItem turn;

    public static void main(String[] args) {
        p1Turn = true;
        board = new Board();
        board.setVisible(true);
        for (List<Piece> l : board.getPieces()) {
            for (JButton j : l) {
                j.addActionListener(new PieceListener());
            }
        }
    }


    static class PieceListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Piece.Status status = Piece.getStatus((Piece)e.getSource());

            if (p1Turn) {
                if (status == Piece.Status.BLACK) {
                    blankBoard();
                    canMove((Piece) e.getSource());
                } else if (status == Piece.Status.NONE) {
                    Piece.movePiece(selectedPiece, (Piece) e.getSource());
                    if (jumpablePiece != null) {
                        Piece.pieceJumped(jumpablePiece);
                        jumpablePiece = null;
                    }
                    changeTurn();
                    blankBoard();
                }
            }/*
            else //{
                //do ai movement
                //changeTurn();

                //player can move for testing
                if (status == Piece.Status.WHITE) {
                    blankBoard();
                    canMove((Piece) e.getSource());
                } else if (status == Piece.Status.NONE) {
                    Piece.movePiece(selectedPiece, (Piece) e.getSource());
                    if (jumpablePiece != null) {
                        Piece.pieceJumped(jumpablePiece);
                        jumpablePiece = null;
                    }
                    changeTurn();
                    blankBoard();
                }
            //}
            */
        }
    }

    private static void blankBoard() {
        //Unselect selected piece
        if (selectedPiece != null)
            setSelected(selectedPiece, false);

        //Unhighlight all highlighted pieces
        for (Piece p : highlighted) {
            setHightlight(p, false);
        }
        highlighted.clear();
    }

    private static void canMove(Piece p) {
        //Do code to check if pieces are kings

        int x = p.getPosition().getX();
        int y = p.getPosition().getY();
        boolean left = (x % 2 == 0 && y == 0)? true : false;
        boolean right = (x % 2 == 1 && y == 7)? true : false;

        if (left) {
            //check only the piece to the right
            if (setUpPiece(getFrontRight(p)))
                setSelected(p, true);
        }
        else if (right) {
            //check only the piece to the left
            if (setUpPiece(getFrontLeft(p)))
                setSelected(p, true);
        }
        else {
            //check both
            if (setUpPiece(getFrontLeft(p)))
                setSelected(p, true);

            if (setUpPiece(getFrontRight(p)))
                setSelected(p, true);
        }
    }

    private static boolean setUpPiece(Piece p) {
        if (p.getStatus() == Piece.Status.NONE) {
            setHightlight(p, true);
            return true;
        }
        return false;
    }

    //front left/right
    //prevent jumping off the top/bottom of board
    //king when reaching the end
    private static Piece getFrontLeft(Piece p) {
        int x = p.getPosition().getX();
        int y = p.getPosition().getY();

        Piece other;

        if (p.getStatus() == Piece.Status.BLACK) {
            other = board.getPieces().get(x - 1).get(y - 1);
            if (other.getStatus() == Piece.Status.WHITE && y > 1) {
                //canJump = true;
                jumpablePiece = other;
                other = board.getPieces().get(x - 2).get(y - 2);
            }
        }
        else {
            other = board.getPieces().get(x + 1).get(y - 1);
            if (other.getStatus() == Piece.Status.BLACK && y > 1) {
                //canJump = true;
                jumpablePiece = other;
                other = board.getPieces().get(x + 2).get(y - 2);
            }
        }
        return other;
    }

    private static Piece getFrontRight(Piece p) {
        int x = p.getPosition().getX();
        int y = p.getPosition().getY();

        Piece other;

        if (p.getStatus() == Piece.Status.BLACK) {
            other = board.getPieces().get(x - 1).get(y + 1);
            if (other.getStatus() == Piece.Status.WHITE && y < 6) {
                //canJump = true;
                jumpablePiece = other;
                other = board.getPieces().get(x - 2).get(y + 2);
            }
        }
        else {
            other = board.getPieces().get(x + 1).get(y + 1);
            if (other.getStatus() == Piece.Status.BLACK && y < 6) {
                //canJump = true;
                jumpablePiece = other;
                other = board.getPieces().get(x + 2).get(y + 2);
            }
        }
        return other;
    }

    private static void setHightlight(Piece p, boolean highlight) {
        p.setHighlight(highlight);
        if (p.getStatus() == Piece.Status.NONE)
            p.setEnabled(highlight);
        if (highlight)
            highlighted.add(p);
    }
    private static void setSelected(Piece p, boolean selected) {
        p.setSelected(selected);
        if (selected)
            selectedPiece = p;
        else
            selectedPiece = null;
    }

    private static void changeTurn() {
        p1Turn = (p1Turn)? false : true;
        board.setTurn(p1Turn);
    }
}
