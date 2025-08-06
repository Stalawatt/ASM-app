package Handlers;

public enum TokenType implements Information{
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
    COMMENT, // Starts a comment on that line
    UNDEFINED // FOR ERRORS
}
