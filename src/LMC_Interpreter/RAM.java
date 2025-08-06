package LMC_Interpreter;

import javax.swing.*;

public class RAM {
    private final int[] memory = new int[100];
    private final JTextArea[] memoryBoxesArray;
    private int counter = 0;


    public RAM(JTextArea[] boxesArray) {
        memoryBoxesArray = boxesArray;
        reset();

    }

    public void reset() {
        for (int i = 0; i < 100; i++) { // fill the memory cells with zeroes
            memory[i] = 0;
        }
    }

    public void setMemoryValue(int index, int value) {
        memoryBoxesArray[counter++].setText(String.valueOf(value));
        memory[index] = value;
    }

    public int getMemoryValue(int index) {
        return memory[index];
    }




}
