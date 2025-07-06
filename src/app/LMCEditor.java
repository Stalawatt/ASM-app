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
    private final static JTextArea[] memoryBoxesArray = new JTextArea[100];

    private static JTextArea accumulatorTextArea;

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
        outputArea.setEditable(false);
        outputArea.setFocusable(false);
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
        *   current instr reg
        *   mem addr reg
        *   dat addr reg
        *
        *
        * Memory :
        *   100 Locations - numbered 0 to 99
        *   
        * */

        // Registers

        // Label showing where registers are

        JLabel registersLabel = getLabel(outputArea.getX() + outputArea.getWidth() + 85, outputArea.getY() + 10, "Registers");
        registersLabel.setAlignmentX(getCentredComponentFromPoints(registersLabel,outputArea.getX() + outputArea.getWidth() + 20,outputArea.getX() + outputArea.getWidth() + 220 ));
        content.add(registersLabel);

        // PC register
        JTextArea programCounterRegisterArea = getTextArea(outputArea.getX() + outputArea.getWidth() + 45, outputArea.getY() + 60, 150, 33);
        programCounterRegisterArea.setBounds(
                getCentredComponentFromPoints(programCounterRegisterArea, outputArea.getX() + outputArea.getWidth() + 20,outputArea.getX() + outputArea.getWidth() + 220),
                programCounterRegisterArea.getY(),
                programCounterRegisterArea.getWidth(),
                programCounterRegisterArea.getHeight()
        );
        programCounterRegisterArea.setEditable(false);
        programCounterRegisterArea.setFocusable(false);
        content.add(programCounterRegisterArea);

        // Label for PC register
        JLabel programCounterRegisterLabel = getLabel(0, programCounterRegisterArea.getY() - 20, "Program Counter"); // to be moved in X to be centred above the textarea
        programCounterRegisterLabel.setBounds(
                getCentredXFromTextArea(programCounterRegisterArea) - programCounterRegisterLabel.getPreferredSize().width / 2, // Centre in X
                programCounterRegisterLabel.getY(),
                programCounterRegisterLabel.getPreferredSize().width,
                programCounterRegisterLabel.getPreferredSize().height
                );
        content.add(programCounterRegisterLabel);



        // CIR

        JTextArea currentInstructionRegisterTextArea = getTextArea(0, programCounterRegisterArea.getY() + programCounterRegisterArea.getHeight() + 30, 150, 33);
        currentInstructionRegisterTextArea.setBounds(
                getCentredComponentFromPoints(currentInstructionRegisterTextArea, outputArea.getX() + outputArea.getWidth() + 20,outputArea.getX() + outputArea.getWidth() + 220),
                currentInstructionRegisterTextArea.getY(),
                currentInstructionRegisterTextArea.getWidth(),
                currentInstructionRegisterTextArea.getHeight()
                ); // centre in the cpu register area
        currentInstructionRegisterTextArea.setEditable(false);
        currentInstructionRegisterTextArea.setFocusable(false);
        content.add(currentInstructionRegisterTextArea);

        // CIR LABEL

        JLabel currentInstructionRegisterLabel = getLabel(0, currentInstructionRegisterTextArea.getY() - 20, "Current Instruction Register");
        currentInstructionRegisterLabel.setBounds(
                getCentredXFromTextArea(currentInstructionRegisterTextArea) - currentInstructionRegisterLabel.getPreferredSize().width / 2, // Centre in X
                currentInstructionRegisterLabel.getY(),
                currentInstructionRegisterLabel.getPreferredSize().width,
                currentInstructionRegisterLabel.getPreferredSize().height
        );
        content.add(currentInstructionRegisterLabel);



        // MAR text area

        JTextArea memoryAddressRegisterTextArea = getTextArea(0, currentInstructionRegisterTextArea.getY() + currentInstructionRegisterTextArea.getHeight() + 30, 150, 33);
        memoryAddressRegisterTextArea.setBounds(
                getCentredComponentFromPoints(memoryAddressRegisterTextArea, outputArea.getX() + outputArea.getWidth() + 20,outputArea.getX() + outputArea.getWidth() + 220),
                memoryAddressRegisterTextArea.getY(),
                memoryAddressRegisterTextArea.getWidth(),
                memoryAddressRegisterTextArea.getHeight()
        ); // centre in the cpu register area
        memoryAddressRegisterTextArea.setEditable(false);
        memoryAddressRegisterTextArea.setFocusable(false);
        content.add(memoryAddressRegisterTextArea);

        // MAR label

        JLabel memoryAddressRegisterLabel = getLabel(0, memoryAddressRegisterTextArea.getY() - 20, "Memory Address Register");
        memoryAddressRegisterLabel.setBounds(
                getCentredXFromTextArea(memoryAddressRegisterTextArea) - memoryAddressRegisterLabel.getPreferredSize().width / 2, // Centre in X
                memoryAddressRegisterLabel.getY(),
                memoryAddressRegisterLabel.getPreferredSize().width,
                memoryAddressRegisterLabel.getPreferredSize().height
        );
        content.add(memoryAddressRegisterLabel);

        // MDR text area

        JTextArea memoryDataRegisterTextArea = getTextArea(0, memoryAddressRegisterTextArea.getY() + memoryAddressRegisterTextArea.getHeight() + 30, 150, 33);
        memoryDataRegisterTextArea.setBounds(
                getCentredComponentFromPoints(memoryDataRegisterTextArea, outputArea.getX() + outputArea.getWidth() + 20,outputArea.getX() + outputArea.getWidth() + 220),
                memoryDataRegisterTextArea.getY(),
                memoryDataRegisterTextArea.getWidth(),
                memoryDataRegisterTextArea.getHeight()
        ); // centre in the cpu register area
        memoryDataRegisterTextArea.setEditable(false);
        memoryDataRegisterTextArea.setFocusable(false);
        content.add(memoryDataRegisterTextArea);

        // MDR label

        JLabel memoryDataRegisterLabel = getLabel(0, memoryDataRegisterTextArea.getY() - 20, "Memory Data Register");
        memoryDataRegisterLabel.setBounds(
                getCentredXFromTextArea(memoryAddressRegisterTextArea) - memoryDataRegisterLabel.getPreferredSize().width / 2, // Centre in X
                memoryDataRegisterLabel.getY(),
                memoryDataRegisterLabel.getPreferredSize().width,
                memoryDataRegisterLabel.getPreferredSize().height
        );
        content.add(memoryDataRegisterLabel);



        // Accumulator text area

        accumulatorTextArea = getTextArea(0, memoryDataRegisterTextArea.getY() + memoryDataRegisterTextArea.getHeight() + 30, 150, 33);
        accumulatorTextArea.setBounds(
                getCentredComponentFromPoints(accumulatorTextArea, outputArea.getX() + outputArea.getWidth() + 20,outputArea.getX() + outputArea.getWidth() + 220),
                accumulatorTextArea.getY(),
                accumulatorTextArea.getWidth(),
                accumulatorTextArea.getHeight()
        ); // centre in the cpu register area
        accumulatorTextArea.setEditable(false);
        accumulatorTextArea.setFocusable(false);
        content.add(accumulatorTextArea);

        // ACC label

        JLabel accumulatorLabel = getLabel(0, accumulatorTextArea.getY() - 20, "Accumulator");
        accumulatorLabel.setBounds(
                getCentredXFromTextArea(accumulatorTextArea) - accumulatorLabel.getPreferredSize().width / 2, // Centre in X
                accumulatorLabel.getY(),
                accumulatorLabel.getPreferredSize().width,
                accumulatorLabel.getPreferredSize().height
        );
        content.add(accumulatorLabel);

        // Memory cells

        // 0 to 99, in square that takes up right side of screen

        // leftmost pixel is outputArea.getX() + outputArea.getWidth() + 260, 30 px gap horizontally and 3px gap vertically

        Point topLeft = new Point( outputArea.getX() + outputArea.getWidth() + 260, outputArea.getY() );
        int cellWidth = 40;
        int cellHeight = 30;
        JTextArea memoryBox;
        for (int i = 0; i <= 19; i++) {

            for (int j = 0; j < 5; j++) {
                memoryBox = getTextArea((int) topLeft.getX() + cellWidth * j + 30 * j, (int) ((int) topLeft.getY() * i + 3 * i  + topLeft.getY()), cellWidth, cellHeight);
                memoryBox.setFocusable(false);
                memoryBox.setEditable(false);
                memoryBox.setText("000");
                memoryBoxesArray[i * 5 + j] = memoryBox;
                content.add(memoryBox);
            }
        }

        // Add labels to the left of the memory boxes
        JLabel memoryLabel;
        for (int i = 0; i < 100; i++) {
            memoryBox = memoryBoxesArray[i];
            memoryLabel = getLabel(memoryBox.getX() - 15, memoryBox.getY() + 5, Integer.toString(i));
            content.add(memoryLabel);
        }


        // Add label for memory section

        JLabel memorySectLabel = getLabel(0,0, "Ram :");
        int xPosMemorySectLabel = getCentredComponentFromPoints(memorySectLabel, memoryBoxesArray[0].getX(), memoryBoxesArray[4].getX() + cellWidth );
        memorySectLabel.setBounds(xPosMemorySectLabel, 10, memorySectLabel.getPreferredSize().width, memorySectLabel.getPreferredSize().height);
        content.add(memorySectLabel);




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
        Label.setFont(new Font("Arial", Font.PLAIN, 10));
        Dimension LabelSize = Label.getPreferredSize();
        Label.setBounds(x, y, LabelSize.width, LabelSize.height);
        Label.setForeground(Color.WHITE);
        return Label;
    }

    private static int getCentredXFromTextArea(JTextArea panel) {
        return panel.getX() + panel.getWidth() / 2;
    }



    private static <T extends JComponent> int getCentredComponentFromPoints(T component, int x1, int x2) {
        int halfComponentWidth = component.getWidth() / 2;
        return ((x1 + x2) / 2) - halfComponentWidth;
    }

    public static void setMemoryNodeValue(int nodeNumber, int value) {
        memoryBoxesArray[nodeNumber].setText(Integer.toString(value));
    }

    public static void setAccReg(int value) {
        accumulatorTextArea.setText(Integer.toString(value));
    }


}