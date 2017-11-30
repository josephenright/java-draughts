import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class Piece extends JButton implements Serializable {

    private static ImageIcon whiteIcon;
    private static ImageIcon blackIcon;
    private static ImageIcon whiteKing;
    private static ImageIcon blackKing;
    private static int whitePieces = 0;
    private static int blackPieces = 0;

    enum Status {NONE, WHITE, BLACK};

    private boolean white;
    private Status status;
    private Position position;
    private boolean king;


    /**
     * Blank constructor - only here because IntelliJ will generate one anyway.
     */
    public Piece() {

    }

    /**
     * Constructor for the white squares.
     * Status defaults to NONE.
     * @param white Whether or not the square is white. White squares cannot be moved to.
     * @param x The X co-ordinate of the Piece.
     * @param y The Y co-ordinate of the Piece.
     */
    public Piece(boolean white, int x, int y) {
        this(white, x, y, Status.NONE);
    }

    /**
     * Constructor for the squares and pieces.
     * @param white Whether or not the square is white. White squares cannot be moved to.
     * @param x The X co-ordinate of the Piece.
     * @param y The Y co-ordinate of the Piece.
     * @param status The Status of the Piece. (NONE, WHITE, BLACK).
     */
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




    /*    Start of instance methods   */


    /**
     * Set whether or not the square is white.
     * @param white True is the square is white, false if the square is black.
     */
    public void setWhite(boolean white) {
        this.white = white;
    }

    /**
     * Whether or not the square is white.
     * @return True is the square is white, false if the square is black.
     */
    public boolean isWhite() {
        return white;
    }


    /**
     * Get the Status of the Piece. (NONE, WHITE, BLACK).
     * @return The Status of the Piece.
     */
    public Status getStatus() {
        return status;
    }
    /**
     * Set the Status of the pieces to either NONE, WHITE, BLACK
     * @param status The Status to set it to.
     */
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


    /**
     * Get the Position of the Piece.
     * @return The position of the piece.
     */
    public Position getPosition() {
        return position;
    }
    /**
     * Set the Position of the Piece.
     * @param position The Position to set.
     */
    public void setPosition(Position position) {
        this.position = position;
    }
    /**
     * Set the Position of the Piece
     * @param x The X co-ordinate to set.
     * @param y The Y co-ordinate to set.
     */
    public void setPosition(int x, int y) {
        this.position = new Position(x, y);
    }


    /**
     * Get whether or not the Piece is a King.
     * @return True is the Piece is a King, false if the Piece is not.
     */
    public boolean isKing() {
        return king;
    }
    /**
     * Change whether or not the Piece is a King.
     * @param king True is the Piece is a King, false if the Piece is not.
     */
    public void setKing(boolean king) {
        this.king = king;
        if (king) {
            if (getStatus() == Status.WHITE)
                setIcon(whiteKing);
            else if (getStatus() == Status.BLACK)
                setIcon(blackKing);
        }
        else {

            if (getStatus() == Status.WHITE)
                setIcon(whiteIcon);
            else if (getStatus() == Status.BLACK)
                setIcon(blackIcon);
        }
    }


    /**
     * Set the White ImageIcon to 'images/white.png'.
     */
    private void setWhiteIcon() {
        whiteIcon = new ImageIcon("images/white.png");
        whiteKing = new ImageIcon("images/whiteKing.png");
    }
    /**
     * Set the Black ImageIcon to 'images/black.png'.
     */
    private void setBlackIcon() {
        blackIcon = new ImageIcon("images/red.png");
        blackKing = new ImageIcon("images/redKing.png");
    }


    /**
     * Update the white or black piece count, either adding or subtracting
     * @param white If true, update the white piece count, otherwise update the black piece count.
     * @param add If true, add 1 to the count, otherwise subtract 1 from the count;
     */
    private static void updatePieceCount(boolean white, boolean add) {
        int change = (add)? 1 : -1;
        if (white)
            whitePieces += change;
        else
            blackPieces += change;
    }


    /**
     * The Piece in string format
     * @return Returns the String version of the the Piece.
     */
    @Override
    public String toString() {
        return "Piece{" +
                "status=" + status +
                ", position=" + position.toString() +
                '}';
    }


    /*    End of instance methods   */




    /*    Start of static methods   */


    /**
     * Move from start to destination.
     * @param start The piece making the move.
     * @param destination The destination of the move.
     */
    public static void movePiece(Piece start, Piece destination) {
        if (destination.getStatus() == Piece.Status.NONE) {
            //piece.setBackground(Color.BLACK);
            destination.setIcon(start.getIcon());
            start.setIcon(null);
            destination.setStatus(start.getStatus());
            start.setStatus(Status.NONE);
            destination.setKing(start.isKing());
            start.setKing(false);
            start.setEnabled(false);
            if ((destination.getStatus() == Status.BLACK && destination.getPosition().getX() == 0) ||
                    (destination.getStatus() == Status.WHITE && destination.getPosition().getX() == 7))
                destination.setKing(true);
        }
    }
    /**
     * Jump from start to destination, removing jumpedPieces.
     * @param start The piece making the jump.
     * @param jumpedPieces The pieces being jumped over.
     * @param destination The destination of the jump.
     */
    public static void jumpPiece(Piece start, Piece jumpedPieces, Piece destination) {
        movePiece(start, destination);
        //destination.setBackground(Color.BLACK);
        boolean pieceIsWhite = (jumpedPieces.getStatus() == Status.WHITE)? true : false;
        jumpedPieces.setStatus(Status.NONE);
        jumpedPieces.setIcon(null);
        jumpedPieces.setEnabled(false);
        updatePieceCount(pieceIsWhite, false);
    }


    /**
     * Get the number of whitePieces on the board.
     * @return The number of white pieces on the board.
     */
    public static int getWhitePieces() {
        return whitePieces;
    }
    /**
     * Get the number of blackPieces on the board
     * @return The number of black pieces on the board.
     */
    public static int getBlackPieces() {
        return blackPieces;
    }


    /**
     * Set the number of whitePieces on the board.
     * @param count The amount to set whitePieces to.
     */
    public static void setWhitePieces(int count) {
        Piece.whitePieces = count;
    }
    /**
     * Set the number of blackPieces on the board.
     * @param count The amount to set blackPieces to.
     */
    public static void setBlackPieces(int count) {
        Piece.blackPieces = count;
    }


    /**
     * Set the piece count for both colours to 0.
     */
    public static void resetPieceCount() {
        whitePieces = 0;
        blackPieces = 0;
    }

    /*    End of static methods   */
}
