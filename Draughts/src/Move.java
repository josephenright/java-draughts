import java.util.ArrayList;

public class Move {
    public enum MoveType {INVALID, MOVE, JUMP };

    private Piece selectedPiece;
    private Piece destinationPiece;
    private Piece jumpablePiece;
    private MoveType moveType;

    public Move() {
        this(null, null, null);
        setMoveType(MoveType.INVALID);
    }

    public Move(Piece selectedPiece, Piece destinationPiece) {
        this(selectedPiece, destinationPiece, null);
        setMoveType(MoveType.MOVE);
    }

    public Move(Piece selectedPiece, Piece destinationPiece, Piece jumpablePiece) {
        setSelectedPiece(selectedPiece);
        setDestinationPiece(destinationPiece);
        setJumpablePieces(jumpablePiece);
        setMoveType(MoveType.JUMP);
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }
    public void setSelectedPiece(Piece selectedPiece) {
        this.selectedPiece = selectedPiece;
    }

    public Piece getDestinationPiece() {
        return destinationPiece;
    }
    public void setDestinationPiece(Piece destinationPiece) {
        this.destinationPiece = destinationPiece;
    }

    public Piece getJumpablePieces() {
        return jumpablePiece;
    }
    public void setJumpablePieces(Piece jumpablePiece) {
        this.jumpablePiece = jumpablePiece;
    }
    public void addJumpablePiece(Piece p) {
        jumpablePiece.add(p);
    }

    public MoveType getMoveType() {
        return moveType;
    }
    private void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }

    public boolean isEqual(Move other) {
        if (getSelectedPiece() != other.getSelectedPiece())
            return false;
        if (getDestinationPiece() != other.getDestinationPiece())
            return false;
        if (!getJumpablePieces().equals(other.getJumpablePieces()))
            return false;
        return true;
    }

    /**
     *
     * @return
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
}