package LMC_Interpreter;

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

import java.util.Scanner;

public class Instructions {
    public static void HALT() {
        System.exit(0);
    }
    public static void ADD(int value) {
        Memory.setAcc(Memory.getAcc() + value);
    }

    public static void SUB(int value) {
        Memory.setAcc(Memory.getAcc() - value);
    }

    public static void LOAD(int location) {
        Memory.setAcc(Memory.getMemoryLocation(location));
    }

    public static void STORE(int location) {
        Memory.setMemoryLocation(location, Memory.getAcc());
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
        System.out.println("Enter Value : ");
        Memory.setAcc(Integer.parseInt(new Scanner(System.in).nextLine()));

    }

    public static void OUT() {
        System.out.println(Memory.getAcc());
    }


}
