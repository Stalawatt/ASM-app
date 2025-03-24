package LMC_Interpreter;

public class Memory {

    // 100 memory locations from 0 to 99
    public static int[] RAM = new int[100];

    // Output from ALU
    private static int ACC = 0;

    // Registers
    private static int addrReg = 0;
    private static int instrReg = 0;
    private static int programCounter = 0;

    public static void init() {
        for (int i = 0; i < 100; i++) {
            RAM[i] = 0;
        }
    }

    public static void setMemoryLocation(int index, int value) {
        RAM[index] = value;
    }
    public static int getMemoryLocation(int index) {
        return RAM[index];
    }
    public static int getAddrReg() {
        return addrReg;
    }
    public static void setAddrReg(int addrReg) {
        addrReg = addrReg;
    }
    public static int getInstrReg() {
        return instrReg;
    }
    public static void setInstrReg(int instrReg) {
        instrReg = instrReg;
    }
    public static int getProgramCounter() {
        return programCounter;
    }
    public static void setProgramCounter(int programCounter) {
        programCounter = programCounter;
    }
    public static void incrementProgramCounter() {
        programCounter++;
    }
    public static int getAcc() {
        return ACC;
    }
    public static void setAcc(int acc) {
        ACC = acc;
    }


}
