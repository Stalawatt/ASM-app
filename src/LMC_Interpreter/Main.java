package LMC_Interpreter;

public class Main {
    public static void main(String[] args) throws Exception {
        String code = """ 
                INP
                ADD 3
                BRA END
                ADD 5
                STA 10
                LDA 10
                END
                OUT
                HLT
                """;
        Interpreter.Run(code);
    }
}
