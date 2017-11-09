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
        //setSize(1055, 915);
        setSize(800, 800);
        setResizable(false);

        //setLayout(new FlowLayout());
        //Container gameBoard = new Container();
        //gameBoard.setLayout(new GridLayout(8, 8));
        setLayout(new GridLayout(8, 8));

        whitePiece = new ImageIcon("images/white.png");
        blackPiece = new ImageIcon("images/red.png");

        JMenuBar menu = new JMenuBar();
        JMenuItem jmi = new JMenuItem("Game");
        //jmi.
        menu.add(jmi);
        //add(menu);
        //add(gameBoard);

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
                }
                else
                    jb = new Piece();

                //gameBoard.add(jb);
                add(jb);


                list.add(jb);
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
