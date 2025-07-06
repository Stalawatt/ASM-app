package Handlers;

public class TickHandler {
    private final Object lock = new Object();
    private boolean tickReady = true;
    // wait time from tickspeed is found by 1/tickspeed to find the tps
    private int tickSpeed = 1;

    public void setTickspeed(int tps) {
        tickSpeed = tps;
    }



    public int start() {
        synchronized (lock) {
            while(true){
                tickReady = false;
                lock.notify();
                try {
                    Thread.sleep(1000 / tickSpeed);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                tickReady = true;
                lock.notify();

            }
        }
    }

    public boolean waitTick() throws InterruptedException {
        synchronized (lock) {
            while(!tickReady) {
                lock.wait();
            }
            return true;
        }
    }
}
