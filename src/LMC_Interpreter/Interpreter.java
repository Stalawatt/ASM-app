package LMC_Interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
BRP - branch if positive
DAT - data storage
*/

public class Interpreter {
    // Stores the tokens
    private static List<String> buffer = new ArrayList<String>();
    // Points to the current token
    private static int pointer = 0;
    // Hashmap for labels
    private static HashMap<String, Integer> labels = new HashMap<>();




    public static void start(String code) {
        tokenize(code);

    }

    private static void tokenize(String code) {
        
    }

    private static String peek() throws Exception {
        throw new Exception("Not implemented yet");
    }
}
