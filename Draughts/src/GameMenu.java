import javax.swing.*;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;

public class GameMenu {

    private static Board board;

    public static JMenuBar createMenu() {
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

        return bar;
    }

    public static class MenuListener implements ActionListener {

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

    public static void saveToFile() throws IOException {
        ObjectOutputStream os;
        //LocalDateTime dateTime = LocalDateTime.now(); //https://stackoverflow.com/questions/5175728/how-to-get-the-current-date-time-in-java
        os = new ObjectOutputStream(new FileOutputStream("saves/draughts.save"));
        os.writeObject(GameManager.getBoard());
        os.close();
    }

    private static void openFromFile() {
        try {
            ObjectInputStream is;
            is = new ObjectInputStream(new FileInputStream("saves/draughts.save"));
            board = (Board)is.readObject();
            is.close();
            //GameManager.main();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error encountered while loading file",
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

    }

}
