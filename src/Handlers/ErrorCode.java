package Handlers;

public enum ErrorCode implements Information {
    SuccessCode,
    IntegerOverflowError,
    IntegerUnderflowError,
    SyntaxError,
    LogicError,
    DivisionByZeroError,
    MemoryOutOfBoundsError
}
