package LMC_Interpreter;

class Token {

    TokenType type;
    Integer value;
    String name;


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
        VARIABLE, // VARIABLE DEFINED VIA DAT BY USER
        NEWLINE, // '\n' FOR NEW LINE
        UNDEFINED // FOR ERRORS
    }

    public Token(TokenType type) {
        this.type = type;
        this.value = null;
    }
    public Token(TokenType type, int value) {
        this.type = type;
        this.value = value;
    }

    public Token(TokenType type, String name) {
        this.type = type;
        this.name = name;
    }



    public TokenType getType() {
        return type;
    }

    public Integer getValue() {
        if (type == TokenType.VARIABLE) {
            return Interpreter.vars.get(name);
        }

        if (type == TokenType.LABEL) {
            return Interpreter.labels.get(name);
        }
        return value;
    }

    public String getName() {
        return name;
    }

    public int hashCode() {

        return 0;
    }
}