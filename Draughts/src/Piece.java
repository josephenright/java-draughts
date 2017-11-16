//Piece.java
//A piece represents a square of the board

import javax.swing.*;
import java.awt.*;

public class Piece extends JButton {

    enum Status { NONE, WHITE, BLACK };

    private boolean white;
    private Status status;
    private Position position;

    public Piece() {
        this(true, 0, 0);
    }

    public Piece(boolean white, int x, int y) {
        super();

        setWhite(white);
        if (white) {
            setBackground(Color.WHITE);
        }
        else
            setBackground(Color.BLACK);

        setEnabled(false);
        setStatus(Status.NONE);
        setPosition(x, y);

        //setSize(50, 50);
        //jb.setLocation(x * 60, y * 60);
    }

    public Piece(boolean white, int x, int y, ImageIcon icon) {
        this(white, x, y);
        setIcon(icon);
        if (icon.getDescription() == "images/white.png")
            setStatus(Status.WHITE);
        else {
            setStatus(Status.BLACK);
            setEnabled(true);
        }
        //enable for testing
        //setEnabled(true);
    }


    private void setWhite(boolean white) {
        this.white = white;
    }
    public boolean isWhite() {
        return white;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public void setPosition(int x, int y) {
        this.position = new Position(x, y);
    }
    public Position getPosition() {
        return position;
    }

    public static Status getStatus(Piece p) {
        return p.getStatus();
    }

    public void setHighlight(boolean highlight) {
        if (highlight)
            setBackground(Color.GREEN);
        else if (white)
            setBackground(Color.WHITE);
        else
            setBackground(Color.BLACK);
    }
    public void setSelected(boolean selected) {
        if (selected)
            setBackground(Color.CYAN);
        else if (white)
            setBackground(Color.WHITE);
        else
            setBackground(Color.BLACK);
    }

    public static void movePiece(Piece piece, Piece target){
        if (piece.getStatus() == Piece.Status.NONE)
            return;
        //Icons
        target.setIcon(piece.getIcon());
        piece.setIcon(null);
        //Status
        target.setStatus(piece.getStatus());
        piece.setStatus(Status.NONE);


        piece.setEnabled(false);
    }
    public static void pieceJumped(Piece p) {
        p.setIcon(null);
        p.setStatus(Status.NONE);
        p.setEnabled(false);
    }
}
