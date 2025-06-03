package LMC_Interpreter;

public class Main {
    public static void main(String[] args) throws Exception {
        String code = """ 
                LDA 15
                BRA END
                LDA 2
                STA HELLO
                LDA 9
                LDA HELLO
                END OUT
                HLT
                HELLO DAT 69
                """;
        Interpreter.Run(code);
    }
}
