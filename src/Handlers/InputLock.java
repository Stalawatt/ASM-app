package Handlers;

public class InputLock {
    private static final Object lock = new Object();
    private static boolean inputReady = false;
    private static int userInput;

    public static boolean isWaiting = false;

    public static int waitForInput() {
        try {
            inputReady = false;
            isWaiting = true;
            synchronized (lock) {
                while (!inputReady) {
                    lock.wait();
                }
                return userInput;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Integer.MAX_VALUE;
    }

    public static void provideInput(String input) {
        isWaiting = false;
        synchronized (lock) {
            userInput = Integer.parseInt(input);
            inputReady = true;
            lock.notify();
        }
    }
}
