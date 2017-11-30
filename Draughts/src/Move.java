import java.util.ArrayList;

public class Move {
    public enum MoveType {INVALID, MOVE, JUMP };

    private Piece selectedPiece;
    private Piece destinationPiece;
    private Piece jumpablePiece;
    private MoveType moveType;


    /* Start of constructors */

    /**
     * Constructor for Moves of type INVALID.
     */
    public Move() {
        this(null, null, null);
        setMoveType(MoveType.INVALID);
    }

    /**
     * Constructor for Moves of type MOVE.
     * @param selectedPiece The start piece of the move.
     * @param destinationPiece The destination of the move.
     */
    public Move(Piece selectedPiece, Piece destinationPiece) {
        this(selectedPiece, destinationPiece, null);
        setMoveType(MoveType.MOVE);
    }

    /**
     * Constructor for Moves of type JUMP.
     * @param selectedPiece The start piece of the move.
     * @param destinationPiece The destination of the move.
     * @param jumpablePiece The Piece to be jumped.
     */
    public Move(Piece selectedPiece, Piece destinationPiece, Piece jumpablePiece) {
        setSelectedPiece(selectedPiece);
        setDestinationPiece(destinationPiece);
        setJumpablePieces(jumpablePiece);
        setMoveType(MoveType.JUMP);
    }

    /* End of constructors */


    /* Start of instance methods */

    /**
     * Get the Piece that this Move starts from.
     * @return The start piece of the move.
     */
    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    /**
     * Set the Piece that this Move starts from.
     * @param selectedPiece The start piece of the move.
     */
    public void setSelectedPiece(Piece selectedPiece) {
        this.selectedPiece = selectedPiece;
    }


    /**
     * Get the Piece that this Move goes to.
     * @return The destination of the move.
     */
    public Piece getDestinationPiece() {
        return destinationPiece;
    }

    /**
     * Set the Piece that this Move goes to.
     * @param destinationPiece The destination of the move.
     */
    public void setDestinationPiece(Piece destinationPiece) {
        this.destinationPiece = destinationPiece;
    }


    /**
     * Get the Piece that this Move can jump over. (Only works if moveType is set to JUMP.
     * @return The Piece to be jumped.
     */
    public Piece getJumpablePieces() {
        return jumpablePiece;
    }
    /**
     * Set the Piece that this Move can jump over. (Only works if moveType is set to JUMP.
     * @param jumpablePiece The Piece to be jumped.
     */
    public void setJumpablePieces(Piece jumpablePiece) {
        this.jumpablePiece = jumpablePiece;
    }


    /**
     * Get the MoveType of this Move to INVALID, MOVE, or JUMP.
     * @return The MoveType of the Move.
     */
    public MoveType getMoveType() {
        return moveType;
    }

    /**
     * Set the MoveType of this Move to INVALID, MOVE, or JUMP.
     * @param moveType The MoveType to set it to.
     */
    private void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }


    /**
     * Display the attributes of the Move class in a String.
     * @return The String containing Move information.
     */
    @Override
    public String toString() {
        String s = "From: " + getSelectedPiece().toString() + "\tTo: " + getDestinationPiece().toString() + "\nJumps:";
        if (getJumpablePieces() != null)
            s += "\n\t" + getJumpablePieces().toString();
        else
            s += "\n\t(none)";
        s += "\nMove Type: " + getMoveType();
        return s;
    }

    /* End of instance methods */
}