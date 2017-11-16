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
    private JMenuItem turn;

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

        createMenu();
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
        //setVisible(true);
    }

    private void createMenu() {
        //JMenuListener menuListener = new JMenuListener();
        JMenuBar bar = new JMenuBar();
        setJMenuBar(bar);

        JMenu menu = new JMenu("Game");
        JMenuItem item = new JMenuItem("Save");
        menu.add(item);

        item = new JMenuItem("Load");
        menu.add(item);

        item = new JMenuItem("Exit");
        //item.addActionListener(menuListener);
        menu.add(item);

        bar.add(menu);
        turn = new JMenuItem("Turn");
        bar.add(turn);
        turn.setEnabled(false);
        setTurn(true);
    }

    public List<List<Piece>> getPieces() {
        return spaces;
    }

    public void setTurn(boolean p1turn) {
        String t = (p1turn)? "Red's Turn" : "White's Turn";
        turn.setText(t);
    }

    /*
    class JMenuListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Exit"))
                System.Exit(0);
        }
    }*/
}
