package todo;
import done.*;
import se.lth.cs.realtime.semaphore.Semaphore;

public class Ticker extends Thread {

    private static ClockInput input;
    private static ClockOutput output;
    private static Semaphore sem;
    private double nextTime;
    private int time;
    private Clock clock;
    private int count = 0;

    public Ticker(ClockInput i, ClockOutput o, Clock clock) {
        input = i;
        output = o;
        this.clock = clock;
        sem = input.getSemaphoreInstance();
    }
    public void run() {
        nextTime = System.currentTimeMillis()+1000;
        double currentTime;
        while (true) {
            sem.take();
            currentTime = System.currentTimeMillis();
            if(input.getChoice()==2){
                time = input.getValue();
                clock.setTime(time);
            }
            if(currentTime > nextTime){
                nextTime = System.currentTimeMillis()+1000;
                time = formatTime(time);
                output.showTime(time);
                time++;
                clock.setTime(time);
                if(clock.alarmIsOn()&&count<20){               	
                	output.doAlarm();
                	count++;
                }
                if(count>=20){
                	clock.alarmOff();
                	count = 0;
                }
            }
            sem.give();
        }
    }

    private int formatTime(int t){
        if(t%100>59){
            t+=100;
            t-=60;
        }
        int sec = t%10000;
        sec = sec/100;
        if(sec>59){
            t+=10000;
            t-=6000;
        }
        int hour = t/10000;
        if(hour>23){
            t=0;
        }
        return t;
    }

    public int getTime(){
        return time;
    }
}
