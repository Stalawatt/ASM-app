package LMC_Interpreter;

import Handlers.InputLock;

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
    }
    public static void ADD(int value) {
        Memory.setAcc(Memory.getAcc() + value);
    }

    public static void SUB(int value) {
        Memory.setAcc(Memory.getAcc() - value);
    }

    public static void LOAD(int value) {
        Memory.setAcc(value);
    }

    public static void STORE(Token location) {
        Interpreter.vars.put(location.getName(), Memory.getAcc());
    }

    public static void BRZ(int location) {
        if (Memory.getAcc() == 0) {
            Interpreter.pointer = location;
            Memory.setProgramCounter(location);
        }


    }
    public static void BRP(int location) {
        if (Memory.getAcc() >= 0) {
            Interpreter.pointer = location;
            Memory.setProgramCounter(location);
        }
    }

    public static void BRA(int location) {
        Interpreter.pointer = location;
        Memory.setProgramCounter(location);
    }

    public static void INP() {
        Memory.setAcc(InputLock.waitForInput());

    }

    public static void OUT(JTextArea outputBox) {
        outputBox.append(Memory.getAcc() + "\n");
    }


}
