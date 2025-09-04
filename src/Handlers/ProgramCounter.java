package Handlers;

public class ProgramCounter {

    public static int value = 0;

    public static int getValue() {
        return value;
    }

    public static void setValue(int value) {
        ProgramCounter.value = value;
    }


}
