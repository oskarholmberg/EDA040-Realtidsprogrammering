package todo;
import done.*;
import se.lth.cs.realtime.semaphore.Semaphore;
import se.lth.cs.realtime.semaphore.MutexSem;

public class AlarmClock extends Thread {

	private static ClockInput	input;
	private static ClockOutput	output;
	private static Semaphore	sem;
    private int alarmTime;
    private boolean alarmOn = false;


	public AlarmClock(ClockInput i, ClockOutput o) {
		input = i;
		output = o;
		sem = input.getSemaphoreInstance();
	}

	// The AlarmClock thread is started by the simulator. No
	// need to start it by yourself, if you do you will get
	// an IllegalThreadStateException. The implementation
	// below is a simple alarmclock thread that beeps upon
	// each keypress. To be modified in the lab.
	public void run() {
        Ticker tick = new Ticker(input, output);
        tick.start();

        while (true) {
            sem.give();
            if(input.getChoice()==1){
                alarmTime = input.getValue();
            }
            if(input.getAlarmFlag()&&alarmTime==tick.getTime()){
                alarmOn=true;
            }
            sem.take();
        }
    }
}
