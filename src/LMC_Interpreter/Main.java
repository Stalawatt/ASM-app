package LMC_Interpreter;

public class Main {
    public static void main(String[] args) throws Exception {
        String code = """ 
                BRA END
                ADD 5
                END
                HLT
                """;
        Interpreter.Run(code);
    }
}
