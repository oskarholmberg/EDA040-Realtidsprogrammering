package lift;

public class Monitor {

	private int here, next;
	private int[] waitEntry;
	private int[] waitExit;
	private int load;
	private LiftView lv;
	private boolean moveUp;

	public Monitor(LiftView lv) {
		moveUp = true;
		here = 0;
		next = 0;
		waitEntry = new int[7];
		waitExit = new int[7];
		load = 0;
		this.lv = lv;

	}


																				//floor = the floor that the person is at
	//																			//Tells the monitor that there is one more person at floor "floor"
	synchronized public void enterFloor(int floor) {	
		waitEntry[floor]++;
		lv.drawLevel(floor, waitEntry[floor]);
	}
																	
																				//floor = the floor that the person wants to enter the lift at
	//																			//Checks if the person can enter the lift at floor "floor"
	synchronized private boolean canEnter(int floor) {	
		return load < 4 && next == here && floor == here;
	}

	
																				//nextFloor = the floor that the person wants to go to
																				//currentFloor = the floor that the person is at
																				//Makes a person wait for the lift and then enter the lift when possible and finally leaves the lift when at the target floor
	synchronized public void liftAction(int nextFloor, int currentFloor) {	
		while (!canEnter(currentFloor)) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		load++;
		waitEntry[here]--;
		waitExit[nextFloor]++;
		lv.drawLevel(here, waitEntry[here]);
		lv.drawLift(here, load);
		while (here != nextFloor) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		exitLift();
	}
																		//A person leaves the floor
	synchronized private void exitLift() {	
		waitExit[here]--;
		load--;
		lv.drawLift(here, load);
		notifyAll();
	}
																		//Gets the next floor to go to and starts riding there
	synchronized public void moveLift() {	
		nextLevel();
		lv.moveLift(here, next);
		haltLift();
	}
																		//The lift has arrived at the next floor
	synchronized private void haltLift() {
		here = next;
		notifyAll();
	}
																		//Sets the variable next to the next floor, it's either +1 or -1 depending on the current direction of the lift
	synchronized private void nextLevel() {
		if (moveUp) {
			next++;
			if (next == 6) {
				moveUp = false;
			}
		} else {
			next--;
			if (next == 0) {
				moveUp = true;
			}
		}
	}
																	//Returns the current floor of the lift
	synchronized public int getCurrentFloor() {
		return here;
	}
																	//I don't think we're using this
	synchronized public void waitABit() {
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
