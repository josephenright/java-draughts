import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class GameMenu {

    private Board board;
    private JMenuItem blackCount;
    private JMenuItem whiteCount;
    private JMenuItem turnTracker;

    public JMenuBar createMenu() {
        JMenuBar bar = new JMenuBar();

        //create JMenu
        JMenu jMenu = new JMenu("Game");
        MenuListener menuListener = new MenuListener();
        bar.add(jMenu);

        //create JMenuItems
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
        jMenu.add(item);
        item.addActionListener(menuListener);


        //create counters and turn tracker
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


    //ActionListener for the game's menu
    public class MenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Warn the user, then start a new game
            if (e.getActionCommand().equals("New Game")) {
                if (JOptionPane.showConfirmDialog(null, "Start a new game?\n" +
                        "You will lose any unsaved progress") == JOptionPane.YES_OPTION) {
                    GameManager.setUpBoard(null);
                }
            }
            //Save the current game to file
            else if (e.getActionCommand().equals("Save")) {
                try {
                    saveToFile();
                }
                catch (IOException exception) {
                    JOptionPane.showMessageDialog(null, "Error encountered while saving to file",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    exception.printStackTrace();
                }
            }
            //Load the game saved to file
            else if (e.getActionCommand().equals("Load")) {
                openFromFile();
                if (board != null)
                    GameManager.setUpBoard(board);
            }
            //Warn the user, then exit the game.
            else if (e.getActionCommand().equals("Exit")) {
                System.out.println("Quit Game");
                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?\n" +
                    "You will lose any unsaved progress") == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        }
    }



    //Save data to file
    public void saveToFile() throws IOException {
        ObjectOutputStream os;
        os = new ObjectOutputStream(new FileOutputStream("saves/draughts.save"));
        os.writeObject(GameManager.getBoard());
        os.writeObject(new SaveDetails(GameManager.getTurn(), Piece.getWhitePieces(), Piece.getWhitePieces()));
        os.close();
    }

    //Load data from file
    private void openFromFile() {
        try {
            ObjectInputStream is;
            is = new ObjectInputStream(new FileInputStream("saves/draughts.save"));
            board = (Board)is.readObject();
            SaveDetails details = (SaveDetails)is.readObject();
            is.close();

            //Use loaded data to set up game
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



    /* Start of UI methods */

    //Update the turn tracker on the JMenu
    public void changeTurn(boolean player1Turn) {
        String s = (player1Turn)? "Black's turn" : "White's turn";
        turnTracker.setText(s);
    }

    //Update the white piece counter on the UI.
    public void setWhiteCount() {
        whiteCount.setText("White left: " + Piece.getWhitePieces());
    }

    //Update the black piece counter on the UI.
    public void setBlackCount() {
        blackCount.setText("Black left: " + Piece.getBlackPieces());
    }

    /* End of UI methods */
}
