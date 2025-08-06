package Popup;


import app.LMCEditor;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class CodeErrorMessage {

    private JPanel content;
    private JTextPane textPane;
    private JButton exitButton;


    private String message;

    public CodeErrorMessage(String message) {
        // Add the wrapper for the error message
        this.message = message;
        content = new JPanel();
        content.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 10, 10), 5),
                BorderFactory.createEmptyBorder(5,5,5,5)
        ));
        content.setBounds(LMCEditor.content.getWidth() / 4, LMCEditor.content.getHeight() / 4, LMCEditor.content.getWidth() / 2, LMCEditor.content.getHeight() / 2);
        content.setBackground(new Color(255,0,0));
        content.setLayout(null);

        // Add the text area to display the error message
        textPane = new JTextPane();
        textPane.setBounds(250,100,500,300);
        textPane.setBorder(BorderFactory.createEmptyBorder(20,100,100,100));
        StyledDocument doc = textPane.getStyledDocument(); // centre the text
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        textPane.setFont(new Font("Arial", Font.PLAIN, 20)); // add the text
        textPane.setEditable(false);
        textPane.setFocusable(false);
        textPane.setText(message);
        textPane.setForeground(Color.BLACK);

        // Exit error message button
        exitButton = new JButton("Close");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 12));
        exitButton.setBounds(850, 15, 75,50);
        exitButton.setBackground(new Color(200,150,150));
        exitButton.setForeground(Color.BLACK);
        exitButton.setFocusable(false);
        exitButton.addActionListener(e -> {
            Container parent = content.getParent();
            parent.remove(content);
            parent.revalidate();
            parent.repaint();
        });
        content.add(exitButton);



        content.add(textPane);
    }

    public JPanel getErrorPanel() {
        return content;
    }



}
