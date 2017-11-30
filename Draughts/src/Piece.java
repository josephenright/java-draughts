import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Piece extends JButton implements Serializable {

    private static ImageIcon whiteIcon;
    private static ImageIcon blackIcon;
    private static int whitePieces = 0;
    private static int blackPieces = 0;

    enum Status {NONE, WHITE, BLACK};

    private boolean white;
    private Status status;
    private Position position;
    private boolean king;

    public Piece() {
        //this();
    }

    public Piece(boolean white, int x, int y) {
        this(white, x, y, Status.NONE);
    }

    public Piece(boolean white, int x, int y, Status status) {
        super();
        if (whiteIcon == null)
            setWhiteIcon();
        if (blackIcon == null)
            setBlackIcon();

        if (white)
            setBackground(Color.LIGHT_GRAY);
            //setBackground(Color.WHITE);
        else
            setBackground(Color.BLACK);
        setWhite(white);
        if (status == Status.BLACK)
            updatePieceCount(false, true);
        else if (status == Status.WHITE)
            updatePieceCount(true, true);
        setPosition(x, y);

        setStatus(status);
        setKing(false);
    }

    /***    Start of instance methods   ***/
    public void setWhite(boolean white) {
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

        if (status == Status.WHITE) {
            setIcon(whiteIcon);
            setEnabled(true);
        }
        else if (status == Status.BLACK) {
            setIcon(blackIcon);
            setEnabled(true);
        }
        else
            setEnabled(false);
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

    public boolean isKing() {
        return king;
    }
    public void setKing(boolean king) {
        this.king = king;
        /*
        //text over button?
        if (king)
            setText("^^");
        else
            setText("");
        */
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

    private static void updatePieceCount(boolean white, boolean add) {
        int change = (add)? 1 : -1;
        if (white)
            whitePieces += change;
        else
            blackPieces += change;
    }


    /***    End of instance methods   ***/


    /***    Start of static methods   ***/
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

    //Move piece to destination
    public static void movePiece(Piece piece, Piece destination) {
        if (destination.getStatus() == Piece.Status.NONE) {
            //piece.setBackground(Color.BLACK);
            destination.setIcon(piece.getIcon());
            piece.setIcon(null);
            destination.setStatus(piece.getStatus());
            piece.setStatus(Status.NONE);
            destination.setKing(piece.isKing());
            piece.setKing(false);
            piece.setEnabled(false);
            if ((destination.getStatus() == Status.BLACK && destination.getPosition().getX() == 0) ||
                    (destination.getStatus() == Status.WHITE && destination.getPosition().getX() == 7))
                destination.setKing(true);
        }
    }

    //Move piece to destination, and remove draughts from jumpedPieces
    public static void jumpPiece(Piece piece, ArrayList<Piece> jumpedPieces, Piece destination) {
        movePiece(piece, destination);
        //destination.setBackground(Color.BLACK);
        for (Piece p : jumpedPieces) {
            boolean pieceIsWhite = (p.getStatus() == Status.WHITE)? true : false;
            p.setStatus(Status.NONE);
            p.setIcon(null);
            p.setEnabled(false);
            updatePieceCount(pieceIsWhite, false);
        }
    }

    public static Piece copyPiece(Piece p) {
        Piece piece = new Piece(p.isWhite(), p.getPosition().getX(), p.getPosition().getY(), p.getStatus());
        piece.setKing(p.isKing());
        return piece;
    }
    public static Piece copyPieceKeepPosition(Piece original, Piece copy) {
        Piece piece = new Piece(original.isWhite(), copy.getPosition().getX(),
                copy.getPosition().getY(), original.getStatus());
        piece.setKing(original.isKing());
        return piece;
    }

    public static int getWhitePieces() {
        return whitePieces;
    }
    public static int getBlackPieces() {
        return blackPieces;
    }

    public static void setWhitePieces(int whitePieces) {
        Piece.whitePieces = whitePieces;
    }
    public static void setBlackPieces(int blackPieces) {
        Piece.blackPieces = blackPieces;
    }

    public static void resetPieceCount() {
        whitePieces = 0;
        blackPieces = 0;
    }

    /***    End of static methods   ***/
}
