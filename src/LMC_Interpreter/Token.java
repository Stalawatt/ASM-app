package LMC_Interpreter;

class Token {

    TokenType type;
    Integer value;
    String text;


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
        UNDEFINED // FOR ERRORS
    }

    public Token(TokenType type) {
        this.type = type;
        this.value = null;
    }
    public Token(TokenType type, int value, String text) {
        this.type = type;
        this.value = value;
    }
    public TokenType getType() {
        return type;
    }

    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    public int hashCode() {

        return 0;
    }
}