package LMC_Interpreter;

import Handlers.TokenType;
import app.LMCEditor;

public class Memory {



    // 100 memory locations from 0 to 99
    public static RAM memory = new RAM(LMCEditor.memoryBoxesArray);;

    // Output from ALU
    private static int ACC = 0;

    // Registers
    private static int addrReg = 0;
    private static int instrReg = 0;
    private static int programCounter = 0;


    public static void setMemoryLocation(int index, int value) {
        memory.setMemoryValue(index,value);
    }
    public static int getMemoryLocation(int index) {
        return memory.getMemoryValue(index);
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

                // Ensure any non-instruction is not loaded to memory
                switch (Interpreter.buffer.get(i).type) {
                    case TokenType.NEWLINE, TokenType.INT, TokenType.LABEL, TokenType.COMMENT,
                         TokenType.VARIABLE, TokenType.UNDEFINED, TokenType.DAT -> {
                        continue;
                    }
                }
                Token currToken = Interpreter.buffer.get(i);
                Token nextToken = Interpreter.buffer.get(i + 1);

                memory.setMemoryValue(i,toMachineCode(currToken, nextToken));
            }
        } catch (Exception e) {
            //
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
            case TokenType.ADD -> 100 + nextToken.getValue();
            case TokenType.SUB -> 200 + nextToken.getValue();
            case TokenType.STA -> 300 + nextToken.getValue();
            case TokenType.LDA -> 500 + nextToken.getValue();
            case TokenType.BRA -> 600 + nextToken.getValue();
            case TokenType.BRZ -> 700 + nextToken.getValue();
            case TokenType.BRP -> 800 + nextToken.getValue();
            case TokenType.INP -> 901;
            case TokenType.OUT -> 902;
            default -> 0;
        };


    }


}
