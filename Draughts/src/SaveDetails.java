import java.io.Serializable;

public class SaveDetails implements Serializable {

    boolean turn;
    int whiteCount;
    int blackCount;


    public SaveDetails(boolean player1Turn, int whiteCount, int blackCount) {
        this.turn = player1Turn;
        this.whiteCount = whiteCount;
        this.blackCount = blackCount;
    }


    //Get whether or not it's player one's turn
    public boolean isPlayer1Turn() {
        return turn;
    }

    //Get the number of black pieces on the board.
    public int getWhiteCount() {
        return whiteCount;
    }

    //Get the number of black pieces on the board.
    public int getBlackCount() {
        return blackCount;
    }

}