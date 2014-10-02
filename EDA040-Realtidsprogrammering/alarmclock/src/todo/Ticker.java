package todo;

public class Ticker extends Thread {

    private long time;
    private static Clock clock;

    public Ticker(Clock clock) {

        this.clock = clock;
    }

    public void run() {
        time = System.currentTimeMillis();
        long diff;
        while (true) {
            time += 1000;
            diff = time - System.currentTimeMillis();
            if (diff > 0) {
                try {
                    sleep(diff);
                } catch (InterruptedException e) {

                }
                clock.tick();
            }
        }
    }
}
