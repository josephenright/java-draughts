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
        /*
        Status s = (icon.getDescription() == "images/white.png")? Status.WHITE : Status.BLACK;
        setStatus(s);
        setEnabled(true);*/
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


}
