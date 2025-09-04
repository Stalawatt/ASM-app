package LMC_Interpreter;

import Handlers.InputLock;
import Handlers.ProgramCounter;

/*
Instructions :

LDA - Load
STA - Store
ADD - add
SUB - subtract
INP - input
OUT - output
HLT - end program
BRZ - branch if zero
BRA - branch unconditionally
BRP - branch if zero or positive
DAT - data storage
*/

import javax.swing.*;
import java.util.Scanner;

public class Instructions {
    public static void HALT() {
        // Reset the interpreter
        Interpreter.reset();
        // Halt the program
        Interpreter.isHalted = true;
    }
    public static void ADD(int value) throws Exception {
        Memory.setAcc(Memory.getAcc() + value);
        Interpreter.updateProgramCounter(ProgramCounter.getValue() + 1);
    }

    public static void SUB(int value) throws Exception {
        Memory.setAcc(Memory.getAcc() - value);
        Interpreter.updateProgramCounter(ProgramCounter.getValue() + 1);
    }

    public static void LOAD(int value) throws Exception {
        Memory.setAcc(value);
        Interpreter.updateProgramCounter(ProgramCounter.getValue() + 1);
    }

    public static void STORE(Token location) throws Exception {
        Interpreter.vars.put(location.getName(), Memory.getAcc());
        Interpreter.updateProgramCounter(ProgramCounter.getValue() + 1);
    }

    public static void BRZ(int location) throws Exception {
        if (Memory.getAcc() == 0) {
            Interpreter.updatePointer(location);
            Interpreter.updateProgramCounter(location);
            Memory.setProgramCounter(location);
        }


    }
    public static void BRP(int location) throws Exception {
        if (Memory.getAcc() >= 0) {
            Interpreter.updatePointer(location);
            Memory.setProgramCounter(location);
            Interpreter.updateProgramCounter(location);
        }
    }

    public static void BRA(int location) throws Exception {
        Interpreter.updatePointer(location);
        Memory.setProgramCounter(location);
        Interpreter.updateProgramCounter(location);
    }

    public static void INP() throws Exception {
        Memory.setAcc(InputLock.waitForInput());
        Interpreter.updateProgramCounter(ProgramCounter.getValue() + 1);

    }

    public static void OUT(JTextArea outputBox) throws Exception {
        outputBox.append(Memory.getAcc() + "\n");
        Interpreter.updateProgramCounter(ProgramCounter.getValue() + 1);
    }


}
