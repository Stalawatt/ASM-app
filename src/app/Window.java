package app;

import javax.swing.*;

public class Window extends JFrame {
    public Window() {
        super("ASM Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Displays the page that is currently loaded in pageManager.currentPage
     */
    public void displayPage(Page page) {
        this.add(page);
        this.setVisible(true);
        this.repaint();
    }

}
