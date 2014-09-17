package todo;
import done.*;
import se.lth.cs.realtime.semaphore.Semaphore;
import se.lth.cs.realtime.semaphore.MutexSem;

public class AlarmClock extends Thread {

	private static ClockInput	input;
	private static ClockOutput	output;
	private static Semaphore	sem;
    private int alarmTime;
    private Clock clock = new Clock();


	public AlarmClock(ClockInput i, ClockOutput o) {
		input = i;
		output = o;
		sem = input.getSemaphoreInstance();
	}

	public void run() {
        Ticker tick = new Ticker(input, output, clock);
        tick.start();

        while (true) {
            sem.give();
            if(input.getChoice()==1){
            	clock.setAlarmTime(input.getValue());
            }
            if(input.getAlarmFlag()&&clock.getAlarmTime()==tick.getTime()){
                clock.alarmOn();
            }
            sem.take();
        }
    }
}
