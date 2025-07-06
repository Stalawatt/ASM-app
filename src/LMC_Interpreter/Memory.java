package LMC_Interpreter;

import app.LMCEditor;

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
    public static void setAddrReg(int aReg) {
        addrReg = aReg;
    }
    public static int getInstrReg() {
        return instrReg;
    }
    public static void setInstrReg(int iReg) {
        instrReg = iReg;
    }
    public static int getProgramCounter() {
        return programCounter;
    }
    public static void setProgramCounter(int pc) {
        programCounter = pc;
    }
    public static void incrementProgramCounter() {
        programCounter++;
    }
    public static int getAcc() {
        return ACC;
    }
    public static void setAcc(int acc) {
        ACC = acc;
        LMCEditor.setAccReg(acc);
    }

    public static void fill() {
        try {
            for (int i = 0; i < 100; i++) {
                Token currToken = Interpreter.buffer.get(i);
                Token nextToken = Interpreter.buffer.get(i + 1);

                RAM[i] = toMachineCode(currToken, nextToken);
            }
        } catch (Exception e) {
            // End of program
        }
    }

    private static int toMachineCode(Token token, Token nextToken) {
        /*
        MNEMONICS TO MACHINE CODE :
        (xx means location of variable)
        ADD - 1xx
        SUB - 2xx
        STA - 3xx
        LDA - 5xx
        BRA - 6xx
        BRZ - 7xx
        BRP - 8xx
        INP - 901
        OUT - 902
        HLT - 000
        DAT - None
     */
        return switch (token.type) {
            case Token.TokenType.ADD -> 100 + nextToken.getValue();
            case Token.TokenType.SUB -> 200 + nextToken.getValue();
            case Token.TokenType.STA -> 300 + nextToken.getValue();
            case Token.TokenType.LDA -> 500 + nextToken.getValue();
            case Token.TokenType.BRA -> 600 + nextToken.getValue();
            case Token.TokenType.BRZ -> 700 + nextToken.getValue();
            case Token.TokenType.BRP -> 800 + nextToken.getValue();
            case Token.TokenType.INP -> 901;
            case Token.TokenType.OUT -> 902;
            default -> 0;
        };


    }


}
