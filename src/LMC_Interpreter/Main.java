package LMC_Interpreter;

public class Main {
    public static void main(String[] args) {
        String code = """ 
                START Hello there
                Josh is so cool
                """;
        Interpreter.Run(code);
    }
}
