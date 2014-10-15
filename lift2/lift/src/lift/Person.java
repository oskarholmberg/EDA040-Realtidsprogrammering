package lift;

public class Person extends Thread{
	int startFloor, endFloor, waitTime;
	LiftMonitor lm;

	public Person(LiftMonitor lm){
		this.lm = lm;
	}

	/**
	 * En person startar från en slumpmässig våning och slutar på en annan slumpmässig våning skild från startvåningen
	 * Väntar en slumpmässig tid innan personen ställer sig i kö. Personen ställer sig i kö, kliver på hissen och går av 
	 */
	public void run(){
		while(true){
			waitTime = 1000*(( int )( Math.random ()*46.0)); // sleep need milliseconds
			try {
				sleep(waitTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			startFloor = (int)(Math.random()*7);	
			do{
				endFloor = (int)(Math.random()*7);
			}while(endFloor == startFloor);

			lm.addToEntryQueue(startFloor);
			lm.enterLift(startFloor, endFloor);
			lm.exitLift(endFloor);
		}
	}
}
