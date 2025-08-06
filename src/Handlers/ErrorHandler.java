package Handlers;

import LMC_Interpreter.Interpreter;
import LMC_Interpreter.Token;
import Popup.CodeErrorMessage;
import app.LMCEditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ErrorHandler {

    private static final HashMap<ErrorCode, String> errorDialogue = new HashMap<>();

    static {
        errorDialogue.put(ErrorCode.SyntaxError, "You have a syntax error!\nThis means that your code is not structured correctly.");
        errorDialogue.put(ErrorCode.MemoryOutOfBoundsError, "You have no HLT instruction!\nThis instruction tells the interpreter to stop running and is needed at the end of your program (before any DAT instructions).");
    }

    public static void showError(ErrorCode errorCode, int lineNum) {
        CodeErrorMessage message = new CodeErrorMessage("Error : %s \n\n %s ".formatted(
                errorCode.toString(),
                errorDialogue.get(errorCode),
                Integer.toString(lineNum)));
        Interpreter.editorPage.add(message.getErrorPanel()
        );
        Interpreter.editorPage.setComponentZOrder(message.getErrorPanel(), 1);
        Interpreter.editorPage.validate();
        Interpreter.editorPage.repaint();
    }

    public static boolean scanForErrors() {
        // Scans for different types of errors
        boolean hasHALTinstr = false;
        int lineCounter = 0;
        TokenType prevPointer;
        TokenType nextPointer;

        for (int pointer = 0; pointer < Interpreter.buffer.size(); pointer++) {

            if (pointer == 0){
                prevPointer = TokenType.NEWLINE;
            }else {
                prevPointer = Interpreter.buffer.get(pointer-1).getType();
            }

            if (pointer == Interpreter.buffer.size()-1) {
                nextPointer = TokenType.NEWLINE;
            } else {
                nextPointer = Interpreter.buffer.get(pointer+1).getType();
            }
            switch (Interpreter.buffer.get(pointer).getType()) {

                case TokenType.HLT -> {
                    // nothing before, nothing after
                    hasHALTinstr = true;

                    if ((prevPointer == TokenType.NEWLINE) && (nextPointer == TokenType.NEWLINE)) {
                        continue;
                    }
                    showError(ErrorCode.SyntaxError, lineCounter);
                    return false;
                }

                case TokenType.NEWLINE -> lineCounter++;

                case TokenType.ADD, TokenType.SUB, TokenType.STA, TokenType.LDA -> {
                    // nothing OR label before, nothing OR int OR variable after
                    if ((prevPointer == TokenType.NEWLINE || prevPointer == TokenType.LABEL) && (nextPointer == TokenType.NEWLINE || nextPointer == TokenType.VARIABLE || nextPointer == TokenType.INT)) {
                        continue;
                    }
                    showError(ErrorCode.SyntaxError, lineCounter);
                    return false;
                }

                case TokenType.INP -> {
                    // newline or label before, nothing after
                    if ((prevPointer == TokenType.NEWLINE || prevPointer == TokenType.LABEL) && (nextPointer == TokenType.NEWLINE)) {
                        continue;
                    }
                    showError(ErrorCode.SyntaxError, lineCounter);
                    return false;
                }

                case TokenType.BRZ, TokenType.BRA, TokenType.BRP -> {
                    // nothing OR label before, label after
                    if ((prevPointer == TokenType.NEWLINE || prevPointer == TokenType.LABEL) && (nextPointer == TokenType.LABEL)) {
                        continue;
                    }
                    showError(ErrorCode.SyntaxError, lineCounter);
                    return false;
                }

                case TokenType.DAT -> {
                    // variable before, int or nothing after
                    if ((prevPointer == TokenType.VARIABLE) && (nextPointer == TokenType.INT || nextPointer == TokenType.NEWLINE)) {
                        continue;
                    }
                    showError(ErrorCode.SyntaxError, lineCounter);
                    return false;
                }

                case TokenType.OUT -> {
                    // Nothing before, nothing after
                    if ((prevPointer == TokenType.NEWLINE) && (nextPointer == TokenType.NEWLINE )) {
                        continue;
                    }
                    showError(ErrorCode.SyntaxError, lineCounter);
                    return false;
                }
            }
        }
        if (!hasHALTinstr) {
            showError(ErrorCode.MemoryOutOfBoundsError, lineCounter);
            return false;
        }
        return true;
    }
}
