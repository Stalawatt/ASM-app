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
        JTextArea codeInputArea = new JTextArea();
        codeInputArea.setEditable(true);
        codeInputArea.setLineWrap(true);
        codeInputArea.setWrapStyleWord(true);
        codeInputArea.setBounds(
                15,
                30,
                400,
                PageManager.percentOfScreen(100,80).height
                );
        codeInputArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY,3),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        content.add(codeInputArea);

        // Label for the code input box
        JLabel codeLabel = new JLabel("Your Code Here : ");
        codeLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        Dimension codeLabelSize = codeLabel.getPreferredSize();
        codeLabel.setBounds(25, 10, codeLabelSize.width, codeLabelSize.height);
        codeLabel.setForeground(Color.WHITE);
        content.add(codeLabel);


        // Output box
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setBounds(430, 30, 200, PageManager.percentOfScreen(100,40).height);
        outputArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY,3),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        outputArea.setFocusable(false);
        content.add(outputArea);

        // Label for output box
        JLabel outputLabel = new JLabel("Output : ");
        outputLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        Dimension outputLabelSize = outputLabel.getPreferredSize();
        outputLabel.setBounds(435, 10, outputLabelSize.width, outputLabelSize.height);
        outputLabel.setForeground(Color.WHITE);
        content.add(outputLabel);

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

        // User input box
        JTextArea userInputArea = new JTextArea();
        userInputArea.setEditable(true);
        userInputArea.setLineWrap(true);
        userInputArea.setWrapStyleWord(true);
        userInputArea.setBounds(430, 60 + outputArea.getHeight(), 200, 33);
        userInputArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY,3),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        userInputArea.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "submit");
        userInputArea.getActionMap().put("submit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InputLock.provideInput(userInputArea.getText().strip());
                userInputArea.setText("");
            }
        });
        content.add(userInputArea);

        // User input label
        JLabel userInputLabel = new JLabel("User Input : ");
        userInputLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        Dimension userInputLabelSize = userInputLabel.getPreferredSize();
        userInputLabel.setBounds(435, userInputArea.getY() - 25, userInputLabelSize.width, userInputLabelSize.height);
        userInputLabel.setForeground(Color.WHITE);
        content.add(userInputLabel);

        // Waiting for input label
        JLabel waitingLabel = new JLabel("Waiting");
        waitingLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        Dimension waitingLabelSize = waitingLabel.getPreferredSize();
        waitingLabel.setBounds(445 + userInputLabelSize.width, userInputArea.getY() - 25, waitingLabelSize.width, waitingLabelSize.height );
        waitingLabel.setForeground(Color.WHITE);


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


        // Create CPU outline
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
        content.add(cpuOutline);
    }
}