package LMC_Interpreter;

public class Main {
    public static void main(String[] args) {
        String code = """ 
                DAT DAT DAT LDA STO ADD
                """;
        Interpreter.Run(code);
    }
}
