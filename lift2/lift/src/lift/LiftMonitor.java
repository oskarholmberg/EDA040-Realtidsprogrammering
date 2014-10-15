package lift;

public class LiftMonitor {
	int here, next, load;
	int[] waitEntry;
	int[] waitExit;
	LiftView lv;

	public LiftMonitor(LiftView lv){
		this.lv = lv;
		waitEntry = new int[7];		// 6 våningar
		waitExit = new int[7];		// 6 våningar
		here = 0;
		next = 1;
		load = 0;
	}

	/**
	 * Ökar antalet personer som väntar på våning startFloor med en
	 * Ritar upp våningen startFloor med rätt antal personer
	 * @param startFloor
	 */
	protected synchronized void addToEntryQueue(int startFloor){
		waitEntry[startFloor]++;
		lv.drawLevel(startFloor, waitEntry[startFloor]);
		notifyAll();
	}

	/**
	 * Anropas då en person vill gå in i hissen
	 * Så länge hissen är på fel våning eller den är full väntar man
	 * Uppdaterar waitEntry och waitExit
	 * @param startFloor
	 * @param endFloor
	 */
	protected synchronized void enterLift(int startFloor, int endFloor){
		while(!(isHere(startFloor) && load < 4)){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		waitEntry[here]--;	
		waitExit[endFloor]++;
		load++;
		lv.drawLevel(here, waitEntry[here]);
		lv.drawLift(here, load);
		notifyAll();
	}

	/**
	 * Anropas då en person är i hissen
	 * Så länge hissen är på fel våning väntar man
	 * Uppdaterar waitEntry och waitExit
	 * @param endFloor
	 */
	protected synchronized void exitLift(int endFloor) {
		while(!isHere(endFloor))
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		waitExit[here]--;
		load--;
		lv.drawLift(here, load);
		notifyAll();
	}

	/**
	 * Anropas när hissen står still. Den väntar med att köra tills alla som vill gå av har gått av eller om det inte finns någon som vill åka. 
	 */
	protected synchronized void waitForPassengers(){
		int sum = 0;
		for(int i = 0; i < 7; i++) sum += waitEntry[i];
		if(waitExit[here] != 0 || (load == 0 && sum == 0)){
			try {
				wait();
//				for(int i = 0; i < 7; i++) sum += waitEntry[i];
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Räknar ut nästa våning
	 */
	protected synchronized void calculateNext(){
		if(next > here){
			here = next;
			if(next == 6){
				next--;
			}else{
				next++;
			}
		}else{
			here = next;
			if(next == 0){
				next++;
			}else{
				next--;
			}
		}
		notifyAll();
	}

	/**
	 * Kollar om hissen är på våningen floor
	 * @param floor
	 * @return true om hissen är på våning floor, false annars
	 */
	private boolean isHere(int floor) {
		return  here == floor;
	}

	/**
	 * 
	 * @return var hissen är och var den är på väg
	 */
	public synchronized int[] getFloors() {
		int[] floors = {here, next};
		return floors;
	}


}
