package game;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        MasterWindow masterWin = MasterWindow.getInstance();
        masterWin.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        masterWin.setExtendedState(JFrame.MAXIMIZED_BOTH);
        masterWin.setUndecorated(true);
        masterWin.setVisible(true);
        masterWin.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        masterWin.goToHomeScreen();
    }
}
