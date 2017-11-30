import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Board extends JFrame implements Serializable {

    private ArrayList<ArrayList<Piece>> pieces = new ArrayList<>();

    public Board() {
        this(800, 800, Piece.Status.BLACK);
    }

    public Board(Piece.Status p1Colour) {
        this(800, 800, p1Colour);
    }

    public Board(int width, int height, Piece.Status p1Colour) {
        super("Draughts");
        setLayout(new GridLayout(8, 8));
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        //DO JMENUBAR
        /*
        JMenuBar bar = new JMenuBar();
        setJMenuBar(bar);
        JMenu jMenu = new JMenu("Game");
        jMenu.add(new JMenuItem("Item"));
        */
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
        System.out.println("done");
    }

    public ArrayList<ArrayList<Piece>> getPieces() {
        return pieces;
    }
}
