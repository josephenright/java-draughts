import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Board extends JFrame implements Serializable {

    private ArrayList<ArrayList<Piece>> pieces = new ArrayList<>();

    /**
     * Creates an object of type Board, with a default size of 800 pixels.
     */
    public Board() {
        this(800);
    }


    /** Creates a new object of type Board, where size is the length/width of the JFrame
     *
     * @param size The size (in pixels) of the JFrame window
     */
    public Board(int size) {
        super("Draughts");
        setLayout(new GridLayout(8, 8));
        setSize(size, size);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        //Generate 8x8 grid. (0,0) is top left
        for (int x = 0; x < 8; x++) {
            ArrayList<Piece> row = new ArrayList<>();
            for (int y = 0; y < 8; y++) {
                Piece p;
                if (x % 2 == y % 2)
                    p = new Piece(true, x, y);
                else {
                    if (x < 3)
                        p = new Piece(false, x, y, Piece.Status.WHITE);
                    else if (x > 4)
                        p = new Piece(false, x, y, Piece.Status.BLACK);
                    else
                        p = new Piece(false, x, y);
                }
                row.add(p);
                add(p);
            }
            pieces.add(row);
        }
    }

    /**
     * The Pieces that make up the board
     * @return the List of List of board pieces.
     */
    public ArrayList<ArrayList<Piece>> getPieces() {
        return pieces;
    }
}
