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

Other token types :

LABEL - labels for loops and branching
VARIABLE - variables made via DAT
NEWLINE - end of an instruction
*/

public class Interpreter {

    // Stores the tokens
    public static final List<Token> buffer = new ArrayList<>();
    // Points to the current token
    public static int pointer = 0;
    // Points to the last token in the buffer
    private static int endPointer;
    // Hashmap for labels - Token Name -> Pointer
    public static final HashMap<String, Integer> labels = new HashMap<>();
    // Hashmap for variables - Token Name -> Value
    public static final HashMap<String, Integer> vars = new HashMap<>();


    /**
     * Runs the interpreter
     * @param code The LMC code input string
     */
    public static void Run(String code) throws Exception {
        pointer = 0;
        Memory.init();
        // Fill buffer with tokens
        tokenize(code);
        // Fill memory
        Memory.fill();
        // Set end pointer
        endPointer = buffer.size() - 1;

        while (pointer < endPointer) {
            Token token = buffer.get(pointer);
            switch (token.type) {
                case Token.TokenType.HLT -> Instructions.HALT();
                case Token.TokenType.ADD -> Instructions.ADD(peek().getValue());
                case Token.TokenType.SUB -> Instructions.SUB(peek().getValue());
                case Token.TokenType.LDA -> Instructions.LOAD(peek().getValue());
                case Token.TokenType.STA -> Instructions.STORE(peek());
                case Token.TokenType.BRA -> Instructions.BRA(peek().getValue());
                case Token.TokenType.BRZ -> Instructions.BRZ(peek().getValue());
                case Token.TokenType.BRP -> Instructions.BRP(peek().getValue());
                case Token.TokenType.INP -> Instructions.INP();
                case Token.TokenType.OUT -> Instructions.OUT();
            }
            pointer++;
        }
        System.exit(1);
    }

    /**
     * Fill the buffer with tokens
     * @param code the string of LMC code
     */
    private static void tokenize(String code) throws Exception {

        code = code.toLowerCase(); // lowercase the code so that it is usable in getTokenType()

        // Tokenize all instr in passed code, other than the labels and variables
        for (String line : code.split("\n")) {
            for (String word : line.split(" ")) {
                Token nextToken = new Token(getTokenType(word));
                // labels and variables are left as undefined for now, later categorised
                // the word is saved into the token
                if (nextToken.type == Token.TokenType.UNDEFINED) {
                    nextToken.name = word;
                }


                // if is an INT token, then add the int value to the token
                if (nextToken.getType() == Token.TokenType.INT) {
                    nextToken.value = Integer.parseInt(word);
                }

                buffer.add(nextToken);

            }
            buffer.add(new Token(Token.TokenType.NEWLINE));
        }

        // Scan for UNDEFINED tokens and assign label or variable
        for (int i = 0; i < buffer.size(); i++) {
            Token token = buffer.get(i);
            if (token.type == Token.TokenType.UNDEFINED) {
                if (i == 0) {
                    token.type = Token.TokenType.LABEL;
                    labels.put(token.name, i);
                    continue;
                }

                if (buffer.get(i + 1).getType() == Token.TokenType.DAT && buffer.get(i + 2).getType() == Token.TokenType.INT) { // variable DAT value
                    token.type = Token.TokenType.VARIABLE;
                    vars.put(token.name, buffer.get(i + 2).getValue());

                } else if (buffer.get(i - 1).getType() == Token.TokenType.NEWLINE) { // NEWLINE LABEL <other instr>
                    token.type = Token.TokenType.LABEL;
                    labels.put(token.name, i);
                }

                switch(buffer.get(i-1).getType()){


                    case Token.TokenType.BRA, Token.TokenType.BRZ, Token.TokenType.BRP :

                        token.type = Token.TokenType.LABEL; break;


                    case Token.TokenType.LDA, Token.TokenType.STA, Token.TokenType.ADD, Token.TokenType.SUB :

                        token.type = Token.TokenType.VARIABLE; break;


                }
            }
        }

        System.out.println(buffer);

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

    private static Token peekBack() throws Exception {
        if (pointer <= 0) {
            return new Token(Token.TokenType.UNDEFINED);
        }
        return buffer.get(pointer - 1);
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
        Memory.incrementProgramCounter();
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
            case "bra" -> Token.TokenType.BRA;
            case "brz" -> Token.TokenType.BRZ;
            case "brp" -> Token.TokenType.BRP;
            case "dat" -> Token.TokenType.DAT;
            default -> Token.TokenType.UNDEFINED;
        };
    }

    private static Token.TokenType isVariableOrLabel(String token) {
        return Token.TokenType.LABEL;
    }
}
