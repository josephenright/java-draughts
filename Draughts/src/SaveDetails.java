import java.io.Serializable;

public class SaveDetails implements Serializable {
    boolean turn;
    int whiteCount;
    int blackCount;

    public SaveDetails(boolean turn, int whiteCount, int blackCount) {
        this.turn = turn;
        this.whiteCount = whiteCount;
        this.blackCount = blackCount;
    }

    public boolean isPlayer1Turn() {
        return turn;
    }
    public int getWhiteCount() {
        return whiteCount;
    }
    public int getBlackCount() {
        return blackCount;
    }
}