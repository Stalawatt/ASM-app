package LMC_Interpreter;

import Handlers.TokenType;

public class Token {

    TokenType type;
    Integer value;
    String name;



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