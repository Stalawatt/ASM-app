package app;

import javax.swing.*;
import java.awt.*;

public class HomePage {

    private final JPanel content;
    private final Dimension screenSize;

    public HomePage(Dimension screenSize) {
        this.screenSize = screenSize;
        content = new JPanel();
        content.setSize(screenSize);
        content.setBounds(0, 0, screenSize.width, screenSize.height);
        content.setBackground(Color.BLACK);
        createUIComponents();
        content.setLayout(null);

    }

    public JPanel getDisplay() {
        return content;
    }

    private void createUIComponents() {
        // Title
        JLabel title = new JLabel("TITLE - ASM APP"); // Change this to the actual title that I have yet to come up with
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 50));
        Dimension preferredSize = title.getPreferredSize();
        title.setBounds(screenSize.width / 2 - preferredSize.width/2, 150, preferredSize.width, preferredSize.height);
        content.add(title);

        // Editor Button
        JButton editorButton = new JButton("Little Man Computer");
        editorButton.setForeground(Color.WHITE);
        editorButton.setBackground(Color.BLACK);
        editorButton.setFont(new Font("Arial", Font.BOLD, 20));
        Dimension editorButtonSize = editorButton.getPreferredSize();
        editorButton.setBounds(screenSize.width/2 - editorButtonSize.width/2, 250, editorButtonSize.width, editorButtonSize.height);
        editorButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        editorButton.setFocusPainted(false);
        editorButton.addActionListener(_ -> PageManager.changePage(PageManager.Page.LittleManComputerEditor));
        content.add(editorButton);





    }
}
