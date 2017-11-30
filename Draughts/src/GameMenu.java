import javax.swing.*;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;

public class GameMenu {

    private Board board;
    private JMenuItem blackCount;
    private JMenuItem whiteCount;
    private JMenuItem turnTracker;

    public JMenuBar createMenu() {
        JMenuBar bar = new JMenuBar();

        JMenu jMenu = new JMenu("Game");
        MenuListener menuListener = new MenuListener();

        bar.add(jMenu);


        JMenuItem item = new JMenuItem("New Game");
        jMenu.add(item);
        item.addActionListener(menuListener);

        item = new JMenuItem("Save");
        jMenu.add(item);
        item.addActionListener(menuListener);

        item = new JMenuItem("Load");
        jMenu.add(item);
        item.addActionListener(menuListener);

        item = new JMenuItem("Exit");
        //item.addActionListener(menuListener);
        jMenu.add(item);
        item.addActionListener(menuListener);

        whiteCount = new JMenuItem("White left: " + Piece.getWhitePieces());
        bar.add(whiteCount);
        whiteCount.setEnabled(false);
        blackCount = new JMenuItem("Black left: " + Piece.getBlackPieces());
        bar.add(blackCount);
        blackCount.setEnabled(false);
        turnTracker = new JMenuItem("Black's turn");
        bar.add(turnTracker);



        return bar;
    }

    public class MenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //System.out.println("MENU");
            if (e.getActionCommand().equals("New Game")) {
                if (JOptionPane.showConfirmDialog(null, "Start a new game?\n" +
                        "You will lose any unsaved progress") == JOptionPane.YES_OPTION) {
                    GameManager.setUpBoard(null);
                }
            }
            else if (e.getActionCommand().equals("Save")) {
                //System.out.println("Save Game\t" + e.getSource().toString());
                //JMenuItem item = (JMenuItem)e.getSource();
                try {
                    saveToFile();
                }
                catch (IOException exception) {
                    JOptionPane.showMessageDialog(null, "Error encountered while saving to file",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    exception.printStackTrace();
                }
            }
            else if (e.getActionCommand().equals("Load")) {
                openFromFile();
                if (board != null)
                    GameManager.setUpBoard(board);
            }
            else if (e.getActionCommand().equals("Exit")) {
                System.out.println("Quit Game");
                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?\n" +
                    "You will lose any unsaved progress") == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        }
    }

    public void saveToFile() throws IOException {
        ObjectOutputStream os;
        //LocalDateTime dateTime = LocalDateTime.now(); //https://stackoverflow.com/questions/5175728/how-to-get-the-current-date-time-in-java
        os = new ObjectOutputStream(new FileOutputStream("saves/draughts.save"));
        os.writeObject(GameManager.getBoard());
        os.writeObject(new SaveDetails(GameManager.getTurn(), Piece.getWhitePieces(), Piece.getWhitePieces()));
        os.close();
    }

    private void openFromFile() {
        try {
            ObjectInputStream is;
            is = new ObjectInputStream(new FileInputStream("saves/draughts.save"));
            board = (Board)is.readObject();
            SaveDetails details = (SaveDetails)is.readObject();
            is.close();
            //GameManager.main();
            GameManager.setPlayer1turn(details.isPlayer1Turn());
            Piece.setBlackPieces(details.getBlackCount());
            Piece.setWhitePieces(details.getWhiteCount());
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error encountered while loading file",
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

    }

    public void changeTurn(boolean player1Turn) {
        String s = (player1Turn)? "Black's turn" : "White's turn";
        turnTracker.setText(s);
    }

    public void setWhiteCount() {
        whiteCount.setText("White left: " + Piece.getWhitePieces());
    }
    public void setBlackCount() {
        blackCount.setText("Black left: " + Piece.getBlackPieces());
    }


}
