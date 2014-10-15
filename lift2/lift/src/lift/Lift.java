package lift;

public class Lift extends Thread{
	LiftMonitor lm;
	LiftView lv;
	public Lift(LiftMonitor lm, LiftView lv){
		this.lm = lm;
		this.lv = lv;
	}
	public void run(){
		while(true){
//			try {
//				sleep(1000);				//Stanna minst 1 sek på varje våning
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			moveLift();					//Flytta hissen
		}
	}
	
	/**
	 * Väntar på att hissen är redo att köra, kör sedan hissen och uppdaterar nästa våning
	 */
	protected void moveLift(){
		lm.waitForPassengers();
		int[] floors = lm.getFloors();
		lv.moveLift(floors[0], floors[1]);
		lm.calculateNext();
		
	}
	
}
