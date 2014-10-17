package todo;

import se.lth.cs.realtime.*;
import se.lth.cs.realtime.event.RTEvent;
import done.AbstractWashingMachine;

public class WaterController extends PeriodicThread {
	private AbstractWashingMachine mach;

	public WaterController(AbstractWashingMachine mach, double speed) {
		super((long) (1000 / speed)); // TODO: replace with suitable period
		this.mach = mach;
	}

	public void perform() {
		RTEvent rte = mailbox.tryFetch();
		if (rte != null) {
			WaterEvent we = (WaterEvent) rte;
			int mode = we.getMode();
			if (mode == 0) {
				mach.setDrain(false);
				mach.setFill(false);
			} else if (mode == 1) {
				mach.setDrain(false);
				while (mach.getWaterLevel() < we.getLevel()) {
					mach.setFill(true);
					if (mailbox.tryFetch() != null) {
						mach.setFill(false);
						return;
					}
				}
				((WashingProgram) (rte.getSource()))
						.putEvent(new AckEvent(this));
				mach.setFill(false);
			} else {
				mach.setFill(false);
				mach.setDrain(true);
				while (mach.getWaterLevel() > 0.0) {
					if (mailbox.tryFetch() != null) {
						mach.setFill(false);
						return;
					}
				}
				((WashingProgram) (rte.getSource()))
						.putEvent(new AckEvent(this));
			}
		}
	}
}
