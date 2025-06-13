package app;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class PageManager {

    private static final JFrame window = new JFrame("ASM APP");
    private static final Dimension screenSize;

    public enum Page{
        HOMEPAGE,
        LittleManComputerEditor,

    }

    public static HashMap<Page, JPanel> pages = new HashMap<>();


    static {

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.getContentPane().setLayout(null);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setVisible(true);

        screenSize = window.getSize();


        HomePage homePage = new HomePage(screenSize);
        LMCEditor lmcEditor = new LMCEditor(screenSize);

        pages.put(Page.HOMEPAGE, homePage.getDisplay());
        pages.put(Page.LittleManComputerEditor, lmcEditor.getDisplay());

        window.add(pages.get(Page.HOMEPAGE));


    }

    public static void changePage(Page page) {
        SwingUtilities.invokeLater(() -> {
           window.getContentPane().removeAll();
           window.getContentPane().add(pages.get(page));
           window.revalidate();
           window.repaint();
        });
    }


    public static Dimension percentOfScreen(int percentX, int percentY) {
        int pcX = (screenSize.width / 100) * percentX;
        int pcY = (screenSize.height / 100) * percentY;
        return new Dimension(pcX,pcY);
    }





}
