package todo;

import se.lth.cs.realtime.*;
import se.lth.cs.realtime.event.RTEvent;
import done.AbstractWashingMachine;

public class SpinController extends PeriodicThread {
	private AbstractWashingMachine mach;
	private int lastSpinDir;
	private long time;

	public SpinController(AbstractWashingMachine mach, double speed) {
		super((long) (1000 / speed)); // TODO: replace with suitable period
		this.mach = mach;
		lastSpinDir = 0;
		time = 0;
	}

	public void perform() {
		time++;
		RTEvent rte = mailbox.tryFetch();
		if (rte != null) {
			int mode = ((SpinEvent) rte).getMode();
			if (mode == SpinEvent.SPIN_OFF) {
				mach.setSpin(0);
				lastSpinDir = 0;
			} else if (mode == SpinEvent.SPIN_FAST) {
				mach.setSpin(3);
				lastSpinDir = 3;
			} else {
				mach.setSpin(1);
				lastSpinDir = 1;
				time = 1;
			}
		} else if ((lastSpinDir == 1 || lastSpinDir == 2) && time >= 60) {
			lastSpinDir++;
			if (lastSpinDir == 3) {
				lastSpinDir = 1;
			}
			mach.setSpin(lastSpinDir);
			time = 0;
		}
	}
}
