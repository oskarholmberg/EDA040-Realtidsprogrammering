package todo;

import done.*;
import se.lth.cs.realtime.semaphore.Semaphore;

public class AlarmClock extends Thread {
	private static ClockInput input;
	private static ClockOutput output;
	private static Semaphore sem;
	private static Clock clock;

	public AlarmClock(ClockInput i, ClockOutput o) {
		input = i;
		output = o;
		sem = input.getSemaphoreInstance();
		clock = new Clock(output);
	}

	public void run() {
		Ticker tick = new Ticker(clock);
		tick.start();
		while (true) {
			sem.take();
			clock.alarmFlag(input.getAlarmFlag());
			if (clock.isBeeping()) {
				clock.alarmOff();
			}
			if (input.getChoice() == ClockInput.SET_ALARM) {
				clock.setAlarmTime(input.getValue());
			}
			if (input.getChoice() == ClockInput.SET_TIME) {
				clock.setTime(input.getValue());
			}
		}
	}
}
