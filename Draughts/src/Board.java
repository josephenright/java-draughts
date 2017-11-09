import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Board extends JFrame {

    private List<List<Piece>> spaces = new ArrayList<>();

    private ImageIcon whitePiece;
    private ImageIcon blackPiece;

    public Board() {
        super("Draughts");
        setSize(800, 800);
        setResizable(false);

        //setLayout(new FlowLayout());
        //Container gameBoard = new Container();
        setLayout(new GridLayout(8, 8));
        //add(gameBoard);

        whitePiece = new ImageIcon("images/white.png");
        blackPiece = new ImageIcon("images/red.png");

        for (int x = 0; x <8; x++) {
            List<Piece> list = new ArrayList<>();
            for (int y = 0; y < 8; y++) {
                Piece jb;
                if (x % 2 == y % 2) {
                    if (x < 3)
                        jb = new Piece(false, x, y, whitePiece);
                    else if (x >= 5)
                        jb = new Piece(false, x, y, blackPiece);
                    else
                        jb = new Piece(false, x, y);

                    //jb.addActionListener(new PieceListener());

                    list.add(jb);
                }
                else
                    jb = new Piece();

                add(jb);


            }
            spaces.add(list);
        }


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public List<List<Piece>> getPieces() {
        return spaces;
    }



}
