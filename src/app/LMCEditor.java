package app;

import LMC_Interpreter.Interpreter;
import Handlers.InputLock;
import Objects.PolygonOutline;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class LMCEditor {

    private final JPanel content;

    public LMCEditor(Dimension screenSize) {
        content = new JPanel();
        content.setSize(screenSize);
        content.setBounds(0, 0, screenSize.width , screenSize.height);
        content.setBackground(Color.BLACK);
        createUIComponents();
        content.setLayout(null);

    }

    public JPanel getDisplay() {
        return content;
    }

    private void createUIComponents() {

        // Add the code input box
        JTextArea codeInputArea = getTextArea(15,30, 400, PageManager.percentOfScreen(100,80).height );
        content.add(codeInputArea);

        // Output box
        JTextArea outputArea = getTextArea(430, 30, 200, PageManager.percentOfScreen(100,40).height );
        content.add(outputArea);

        // User input box
        JTextArea userInputArea = getTextArea(430, 60 + outputArea.getHeight(), 200, 33);
        userInputArea.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "submit");
        userInputArea.getActionMap().put("submit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InputLock.provideInput(userInputArea.getText().strip());
                userInputArea.setText("");
            }
        });
        content.add(userInputArea);

        // Label for the code input box

        JLabel codeLabel = getLabel(25, 10, "Your Code Here :");
        content.add(codeLabel);

        // Label for output box
        JLabel outputLabel = getLabel(435, 10, "Output : ");
        content.add(outputLabel);

        // User input label
        JLabel userInputLabel = getLabel(435, userInputArea.getY() - 25,"User Input : ");
        content.add(userInputLabel);

        // Waiting for input label
        JLabel waitingLabel = getLabel(445 + userInputLabel.getWidth(), userInputArea.getY() - 25, "Waiting");
        waitingLabel.setForeground(Color.WHITE);

        // Run button
        JButton quickRunButton = new JButton("Run");
        quickRunButton.setFont(new Font("Arial", Font.PLAIN, 20));
        Dimension quickRunButtonSize = quickRunButton.getPreferredSize();
        int quickRunButtonYCoord = 30 + codeInputArea.getHeight() + 10;
        quickRunButton.setBounds(20, quickRunButtonYCoord, 195, quickRunButtonSize.height);
        quickRunButton.setForeground(Color.WHITE);
        quickRunButton.setBackground(Color.BLACK);
        quickRunButton.setBorder(BorderFactory.createLineBorder(Color.GREEN,2));
        quickRunButton.setFocusPainted(false);
        quickRunButton.addActionListener(_ -> {
            // Quickly run the interpreter
            new Thread(() -> {
                try {
                    outputArea.setText("");
                    Interpreter.Run(codeInputArea.getText(), outputArea);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }).start();
        });
        content.add(quickRunButton);

        new Thread(() -> {
            while (true) {
                Component[] components = content.getComponents();
                boolean hasLabel = Arrays.asList(components).contains(waitingLabel);
                if (InputLock.isWaiting && !hasLabel ) {
                    content.add(waitingLabel);
                    content.revalidate();
                    content.repaint();
                } else if (!InputLock.isWaiting && hasLabel) {
                    content.remove(waitingLabel);
                    content.revalidate();
                    content.repaint();
                }
            }
        }).start();




        // CPU ---------------------------------------------------------------------------------------------------

        // Create CPU outline
        PolygonOutline cpuOutline = getCPUOutline(outputArea, userInputArea, codeInputArea);
        content.add(cpuOutline);

        /* NEEDED
        *
        * Registers :
        *   Program Counter Register
        *   Accumulator
        *
        *
        * Memory :
        *   100 Locations
        *   
        * */
    }

    private static PolygonOutline getCPUOutline(JTextArea outputArea, JTextArea userInputArea, JTextArea codeInputArea) {
        int[] xPoints = {
                outputArea.getX() + outputArea.getWidth() + 20,
                outputArea.getX() + outputArea.getWidth() + 220,
                outputArea.getX() + outputArea.getWidth() + 220,
                userInputArea.getX(),
                userInputArea.getX(),
                outputArea.getX() + outputArea.getWidth() + 20,
        };

        int[] yPoints = {
                outputArea.getY(),
                outputArea.getY(),
                codeInputArea.getY() + codeInputArea.getHeight(),
                codeInputArea.getY() + codeInputArea.getHeight(),
                userInputArea.getY() + userInputArea.getHeight() + 20,
                userInputArea.getY() + userInputArea.getHeight() + 20,
        };

        int nPoints = 6;

        PolygonOutline cpuOutline = new PolygonOutline(xPoints, yPoints, nPoints, Color.WHITE);
        Dimension cpuOutlineSize = cpuOutline.getPreferredSize();
        cpuOutline.setOpaque(false);
        cpuOutline.setBounds(0,0,1920,1080);
        return cpuOutline;
    }

    private static JTextArea getTextArea(int x, int y, int width, int height) {

        JTextArea textArea = new JTextArea();
        textArea.setEditable(true);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBounds(
                x,
                y,
                width,
                height
        );
        textArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY,3),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return textArea;
    }

    private static JLabel getLabel(int x, int y, String text) {
        JLabel Label = new JLabel(text);
        Label.setFont(new Font("Arial", Font.PLAIN, 15));
        Dimension LabelSize = Label.getPreferredSize();
        Label.setBounds(x, y, LabelSize.width, LabelSize.height);
        Label.setForeground(Color.WHITE);
        return Label;
    }


}