import javax.swing.*;
import java.awt.*;

public class Piece extends JButton {

    static ImageIcon whiteIcon;
    static ImageIcon blackIcon;
    static int whitePieces = 0;
    static int blackPieces = 0;

    enum Status {NONE, WHITE, BLACK};

    private Status status;
    private Position position;

    public Piece() {
        //this();
    }

    public Piece(boolean white, int x, int y) {
        this(true, x, y, Status.NONE);
    }

    public Piece(boolean white, int x, int y, Status status) {
        super();
        if (whiteIcon == null)
            setWhiteIcon();
        if (blackIcon == null)
            setBlackIcon();

        if (white)
            setBackground(Color.WHITE);
        else
            setBackground(Color.BLACK);
        updatePieceCount(white, true);
        setPosition(x, y);

        if (status == Status.WHITE)
            setIcon(whiteIcon);
        else if (status == Status.BLACK)
            setIcon(blackIcon);
        setStatus(status);

    }

    public static ImageIcon getWhiteIcon() {
        return whiteIcon;
    }
    public static void setWhiteIcon(ImageIcon whiteIcon) {
        Piece.whiteIcon = whiteIcon;
    }

    public static ImageIcon getBlackIcon() {
        return blackIcon;
    }
    public static void setBlackIcon(ImageIcon blackIcon) {
        Piece.blackIcon = blackIcon;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }
    public void setPosition(int x, int y) {
        this.position = new Position(x, y);
    }

    @Override
    public String toString() {
        return "Piece{" +
                "status=" + status +
                ", position=" + position.toString() +
                '}';
    }


    private void setWhiteIcon() {
        whiteIcon = new ImageIcon("images/white.png");
    }
    private void setBlackIcon() {
        blackIcon = new ImageIcon("images/red.png");
    }

    private void updatePieceCount(boolean white, boolean add) {
        int change = (add)? 1 : -1;
        if (white)
            whitePieces += change;
        else
            blackPieces += change;
    }
}
