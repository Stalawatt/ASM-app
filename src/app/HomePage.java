package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends Page {


    private final int windowWidth, windowHeight;

    private final PageManager pageManager;



    public HomePage(JFrame frame, PageManager pageManager) {
        super();
        this.windowWidth = frame.getWidth();
        this.windowHeight = frame.getHeight();


        this.pageManager = pageManager;
    }

    private void loadTitle(Graphics g) {
        String title = "ASM APP";
        g.setColor(Color.WHITE);
        Font font = new Font("Serif", Font.BOLD, 100);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        int width = fm.stringWidth(title);
        int height = fm.getHeight();
        g.drawString(title, this.windowWidth/2-width/2, this.windowHeight/10);
    }

    private void loadButtons() {
        // Load the button to send user to editor
        JButton editorButton = new JButton("Editor");
        editorButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                pageManager.goToEditorPage();
            }
        });

        editorButton.setBounds(this.windowWidth/2-editorButton.getWidth()/2, this.windowHeight/5, editorButton.getWidth(), editorButton.getHeight());

        super.add(editorButton);

    }


    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);

        super.setBackground(Color.BLACK);
        loadTitle(g);
        loadButtons();

    }



}
