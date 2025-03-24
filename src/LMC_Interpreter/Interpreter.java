package LMC_Interpreter;

import java.util.*;

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

public class Interpreter {

    // Stores the tokens
    private static final List<Token> buffer = new ArrayList<>();
    // Points to the current token
    private static int pointer = 0;
    // Points to the last token in the buffer
    private static int endPointer;
    // Hashmap for labels
    private static HashMap<String, Integer> labels = new HashMap<>();
    // Memory for program
    Memory memory = new Memory();


    /**
     * Runs the interpreter
     * @param code The LMC code input string
     */
    public static void Run(String code) {
        // Fill buffer with tokens
        tokenize(code);
        // Set end pointer
        endPointer = buffer.size() - 1;
    }

    /**
     * Fill the buffer with tokens
     * @param code the string of LMC code
     */
    private static void tokenize(String code) {
        String[] lines = code.split("\n");

        for (String line : lines) {
            for (String token : line.split(" ")) {
                Token.TokenType tokenType = getTokenType(token.toLowerCase());
                if (tokenType == Token.TokenType.INT) {
                    buffer.add(new Token(tokenType, Integer.parseInt(token)));
                } else {
                    buffer.add(new Token(tokenType));
                }
            }
        }

    }

    /**
     * Get the next token
     * @return The token at buffer index of pointer + 1
     * @throws Exception if current pointer is equal to or larger than end pointer
     */
    private static Token peek() throws Exception {
        if (pointer >= endPointer) {
            throw new Exception("End of input reached");
        }
        return buffer.get(pointer + 1);
    }

    /**
     * Sets the pointer
     * @param newPointer new pointer value
     * @throws Exception if new pointer value exceeds end pointer
     */
    private static void setPointer(int newPointer) throws Exception {
        if (newPointer > endPointer) {
            throw new Exception("End pointer exceeded in setPointer");
        }
        pointer = newPointer;
    }

    /**
     * Increments the pointer
     * @throws Exception if next pointer exceeds end pointer
     */
    private static void incrementPointer() throws Exception {
        if (pointer + 1 > endPointer) {
            throw new Exception("End pointer exceeded in nextPointer");
        }
        pointer++;
    }

    /**
     * Resets the buffer and pointer
     */
    private static void resetBuffer() {
        buffer.clear();
        pointer = 0;
    }

    /**
     * Takes in a token string, and returns the token type
     * @param token The token string
     * @return the token type
     */
    private static Token.TokenType getTokenType(String token) {
        try {
            Integer.parseInt(token);
            return Token.TokenType.INT;
        } catch (Exception e) {
            // Not an integer token
        }

        return switch (token) {
            case "lda" -> Token.TokenType.LDA;
            case "sta" -> Token.TokenType.STA;
            case "add" -> Token.TokenType.ADD;
            case "sub" -> Token.TokenType.SUB;
            case "inp" -> Token.TokenType.INP;
            case "out" -> Token.TokenType.OUT;
            case "hlt" -> Token.TokenType.HLT;
            case "brz" -> Token.TokenType.BRZ;
            case "brp" -> Token.TokenType.BRP;
            case "dat" -> Token.TokenType.DAT;
            default -> Token.TokenType.LABEL;
        };
    }
}
