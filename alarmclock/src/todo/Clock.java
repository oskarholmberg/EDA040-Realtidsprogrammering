package todo;

public class Clock {
	
	private int time = 000000;
	private int alarm = 000000;
	private boolean alarmOn = false;
	
	public Clock(){
		
	}

	public void alarmOn() {
		alarmOn=true;
	}
	
	public void alarmOff(){
		alarmOn = false;
	}

	public void setAlarmTime(int value) {
		alarm = value;
		
	}

	public void setTime(int time) {
		this.time = time;
		
	}

	public boolean alarmIsOn() {
		return alarmOn;
	}
	
	public int getTime(){
		return time;
	}
	
	public int getAlarmTime(){
		return alarm;
	}

}
