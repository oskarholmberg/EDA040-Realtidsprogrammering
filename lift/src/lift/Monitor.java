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

	synchronized public void enterFloor(int floor) {
		waitEntry[floor]++;
		lv.drawLevel(floor, waitEntry[floor]);
	}

	synchronized private boolean canEnter(int floor) {
		return load < 4 && next == here && floor == here;
	}

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

	synchronized private void exitLift() {
		waitExit[here]--;
		load--;
		lv.drawLift(here, load);
		notifyAll();
	}

	synchronized public void moveLift() {
		nextLevel();
		lv.moveLift(here, next);
		haltLift();
	}

	synchronized private void haltLift() {
		here = next;
		notifyAll();
	}

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

	synchronized public int getCurrentFloor() {
		return here;
	}

	synchronized public void waitABit() {
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}