import java.util.ArrayList;

public class Move {
    public enum MoveType {INVALID, MOVE, JUMP };

    private Piece selectedPiece;
    private Piece destinationPiece;
    private ArrayList<Piece> jumpablePieces;
    private MoveType moveType;
    private int jumpCount;

    public Move() {
        this(null, null, null);
        setMoveType(MoveType.INVALID);
    }

    public Move(Piece selectedPiece, Piece destinationPiece) {
        this(selectedPiece, destinationPiece, null);
        setMoveType(MoveType.MOVE);
    }

    public Move(Piece selectedPiece, Piece destinationPiece, ArrayList<Piece> jumpablePieces) {
        setSelectedPiece(selectedPiece);
        setDestinationPiece(destinationPiece);
        setJumpablePieces(jumpablePieces);
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

    public ArrayList<Piece> getJumpablePieces() {
        return jumpablePieces;
    }
    public void setJumpablePieces(ArrayList<Piece> jumpablePieces) {
        this.jumpablePieces = jumpablePieces;
    }
    public void addJumpablePiece(Piece p) {
        jumpablePieces.add(p);
    }

    public MoveType getMoveType() {
        return moveType;
    }
    public void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }

    public int getJumpCount() {
        return jumpCount;
    }
    public void setJumpCount(int jumpCount) {
        this.jumpCount = jumpCount;
    }

    @Override
    public String toString() {
        String s = "From: " + getSelectedPiece().toString() + "\tTo: " + getDestinationPiece().toString() + "\nJumps:";
        if (getJumpablePieces() != null)
            for (Piece p : getJumpablePieces())
                s += "\n\t" + p.toString();
        else
            s += "\n\t(none)";
        s += "\nMove Type: " + getMoveType();
        return s;
    }
}