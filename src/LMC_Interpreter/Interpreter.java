package LMC_Interpreter;

import Handlers.ErrorCode;
import Handlers.ErrorHandler;
import Handlers.Information;
import Handlers.TokenType;
import app.LMCEditor;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    public static List<Token> buffer = new ArrayList<>();
    // Points to the current token
    public static int pointer = 0;
    // Points to the last token in the buffer
    private static int endPointer;
    // Hashmap for labels - Token Name -> Pointer
    public static HashMap<String, Integer> labels = new HashMap<>();
    // Hashmap for variables - Token Name -> Value
    public static HashMap<String, Integer> vars = new HashMap<>();
    // Program halted
    private static boolean isHalted = false;
    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public static final JPanel editorPage = LMCEditor.content;
    private static JTextArea outputBox;


    // wait time from tickspeed is found by 1000ms/tickspeed to find the tps
    public static int tickSpeed = 20;
    /**
     * Runs the interpreter
     * @param code The LMC code input string
     */
    public static void Run(String code) {
        Memory.memory = new RAM(LMCEditor.memoryBoxesArray);
        for (Component c : editorPage.getComponents()) {
            try{
            if (c.getName().equals("outputArea")) {
                outputBox = (JTextArea) c;
            }} catch (Exception e) {
                //
            }
        }
        reset();
        pointer = 0;
        isHalted = false;
        // Fill buffer with tokens
        ErrorCode returnCode = tokenize(code); // Get the return code from tokenization
        if (returnCode == ErrorCode.SuccessCode); // Great success! Do not send error message
        else { // Not great success :( send error message
             ErrorHandler.showError(returnCode, -1);
        }
        // Fill memory
        Memory.fill();
        // Set end pointer
        endPointer = buffer.size() - 1;


        if (ErrorHandler.scanForErrors()) executor.scheduleAtFixedRate(() -> {
            try {
                if (pointer < endPointer && !isHalted) executeToken();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }}, 1000/tickSpeed, 1000/tickSpeed, TimeUnit.MILLISECONDS);


    }

    /**
     * Fill the buffer with tokens
     * @param code the string of LMC code
     */
    private static ErrorCode tokenize(String code)  {
        boolean isComment = false;
        code = code.toLowerCase(); // lowercase the code so that it is usable in getTokenType()

        // Tokenize all instr in passed code, other than the labels and variables
        for (String line : code.split("\n")) {
            for (String word : line.split(" ")) {
                Token nextToken = new Token((TokenType)(getTokenType(word)));

                // labels and variables are left as undefined for now, later categorised
                // the word is saved into the token

                if (isComment) {
                    String[] splitLine = line.split(" ");
                    if (word.equals(splitLine[splitLine.length - 1])) {
                        isComment = false;
                    }else {
                        continue;
                    }
                }

                if (nextToken.type == TokenType.UNDEFINED) {
                    nextToken.name = word;
                }

                // when a comment starts, just skip to the next newline and continue from there
                if (nextToken.type == TokenType.COMMENT) {
                    isComment = true;
                    continue;
                }

                // if is an INT token, then add the int value to the token
                if (nextToken.getType() == TokenType.INT) {
                    nextToken.value = Integer.parseInt(word);
                }



                buffer.add(nextToken);

            }
            buffer.add(new Token(TokenType.NEWLINE));
        }

        // Scan for UNDEFINED tokens and assign label or variable
        for (int i = 0; i < buffer.size(); i++) {
            Token token = buffer.get(i);
            if (token.type == TokenType.UNDEFINED) {
                if (i == 0 && !Objects.equals(token.name, "#")) {
                    token.type = TokenType.LABEL;
                    labels.put(token.name, i);
                    continue;
                }

                if (buffer.get(i + 1).getType() == TokenType.DAT) { // variable DAT value
                    token.type = TokenType.VARIABLE;

                        vars.put(token.name, buffer.get(i + 2).getType() == TokenType.INT ? buffer.get(i + 2).getValue() : 0);


                } else if (buffer.get(i - 1).getType() == TokenType.NEWLINE) { // NEWLINE LABEL <other instr>
                    token.type = TokenType.LABEL;
                    labels.put(token.name, i);
                }

                switch(buffer.get(i-1).getType()){


                    case TokenType.BRA, TokenType.BRZ, TokenType.BRP :

                        token.type = TokenType.LABEL; break;


                    case TokenType.LDA, TokenType.STA, TokenType.ADD, TokenType.SUB :

                        token.type = TokenType.VARIABLE; break;


                }
            }
        }

        for (int i = 0; i < buffer.size(); i++) {
            if (buffer.get(i).getType() == TokenType.UNDEFINED) {
                return ErrorCode.SyntaxError;
            }
        }

        buffer.add(new Token(TokenType.NEWLINE));

        return ErrorCode.SuccessCode;
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
        Memory.incrementProgramCounter();
        pointer++;
    }

    /**
     * Takes in a token string, and returns the token type
     * @param token The token string
     * @return the token type
     */
    private static Information getTokenType(String token) {
        try {

            if (Integer.parseInt(token) > 0) return TokenType.INT;
        } catch (NumberFormatException e) {

        }


        return switch (token) {
            case "lda" -> TokenType.LDA;
            case "sta" -> TokenType.STA;
            case "add" -> TokenType.ADD;
            case "sub" -> TokenType.SUB;
            case "inp" -> TokenType.INP;
            case "out" -> TokenType.OUT;
            case "hlt" -> TokenType.HLT;
            case "bra" -> TokenType.BRA;
            case "brz" -> TokenType.BRZ;
            case "brp" -> TokenType.BRP;
            case "dat" -> TokenType.DAT;
            case "#" -> TokenType.COMMENT;
            default -> TokenType.UNDEFINED;
        };
    }


    public static void reset() {
        pointer = 0;
        endPointer = 1;
        buffer.clear();
        labels.clear();
        vars.clear();
        isHalted = true;

    }

    private static void executeToken() throws Exception {
        Token token = buffer.get(pointer);
        switch (token.type) {
            case TokenType.HLT -> Instructions.HALT();
            case TokenType.ADD -> Instructions.ADD(peek().getValue());
            case TokenType.SUB -> Instructions.SUB(peek().getValue());
            case TokenType.LDA -> Instructions.LOAD(peek().getValue());
            case TokenType.STA -> Instructions.STORE(peek());
            case TokenType.BRA -> Instructions.BRA(peek().getValue());
            case TokenType.BRZ -> Instructions.BRZ(peek().getValue());
            case TokenType.BRP -> Instructions.BRP(peek().getValue());
            case TokenType.INP -> Instructions.INP();
            case TokenType.OUT -> Instructions.OUT(outputBox);
        }
        incrementPointer();
    }

    public static void setTickspeed(int tps) {
        tickSpeed = tps;
    }


}
