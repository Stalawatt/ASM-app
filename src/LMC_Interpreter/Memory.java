package LMC_Interpreter;

public class Memory {

    int[] memory = new int[100];

    public Memory() {
        for (int i = 0; i < memory.length; i++) {
            memory[i] = 0;
        }
    }
    public void set(int index, int value) {
        memory[index] = value;
    }
    public int get(int index) {
        return memory[index];
    }
}
