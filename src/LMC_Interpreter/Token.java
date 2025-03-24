package LMC_Interpreter;

class Token {

    TokenType type;
    int value;

    enum TokenType {
        LDA,      // LOAD
        STA,      // STORE
        ADD,      // ADD
        SUB,      // SUBTRACT
        INP,      // USER INPUT
        OUT,      // OUTPUT TO USER
        HLT,      // END PROGRAM
        BRA,      // BRANCH UNCONDITIONALLY
        BRZ,      // BRANCH IF ZERO
        BRP,      // BRANCH IF ZERO OR POSITIVE
        DAT,      // DATA STORAGE
        INT,      // INT GIVEN BY USER
        LABEL,    // LABEL
        UNDEFINED // ERROR
    }

    public Token(TokenType type) {
        this.type = type;
    }
    public Token(TokenType type, int value) {
        this.type = type;
        this.value = value;
    }
    public TokenType getType() {
        return type;
    }

    public int getValue() {
        return value;
    }
}