package todo;

import done.ClockOutput;
import se.lth.cs.realtime.semaphore.MutexSem;

public class Clock {

    private int time = 000000;
    private int alarm = 000000;
    private boolean alarmOn;
    private MutexSem mutSem;
    private ClockOutput output;
    private int count;
    private boolean beeping;

    public Clock(ClockOutput output) {
        mutSem = new MutexSem();
        this.output = output;
        count = 20;
        beeping = false;
        alarmOn = false;
    }

    public void alarmFlag(boolean flag) {
        mutSem.take();
        alarmOn = flag;
        mutSem.give();
    }

    public void alarmOff() {
        mutSem.take();
        alarmOn = false;
        beeping = false;
        count = 20;
        mutSem.give();
    }

    public void setAlarmTime(int value) {
        mutSem.take();
        alarm = value;
        mutSem.give();

    }

    public void setTime(int time) {
        mutSem.take();
        this.time = time;
        mutSem.give();
    }

    public void tick() {
        mutSem.take();
        time++;
        if (time % 100 > 59) {
            time += 100;
            time -= 60;
        }
        int sec = time % 10000;
        sec = sec / 100;
        if (sec > 59) {
            time += 10000;
            time -= 6000;
        }
        int hour = time / 10000;
        if (hour > 23) {
            time = 0;
        }
        if (time == alarm) {
            beeping = true;
        }
        output.showTime(time);
        if (beeping && alarmOn) {
            output.doAlarm();
            count--;
            if (count == 0) {
                alarmOff();
            }
        }
        mutSem.give();
    }

    public boolean isBeeping() {
        return beeping;
    }
}
