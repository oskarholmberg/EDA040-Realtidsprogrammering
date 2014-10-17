package todo;

import se.lth.cs.realtime.*;
import se.lth.cs.realtime.event.RTEvent;
import done.AbstractWashingMachine;

public class CopyOfTemperatureController extends PeriodicThread {
	private AbstractWashingMachine mach;
	private double temp;

	public CopyOfTemperatureController(AbstractWashingMachine mach, double speed) {
		super((long) (1000 / speed)); // TODO: replace with suitable period
		this.mach = mach;
		temp = 0;
	}

	public void perform() {
		RTEvent rte = mailbox.tryFetch();
		if (rte != null) {
			TemperatureEvent te = (TemperatureEvent) rte;
			if (te.getMode() == te.TEMP_IDLE) {
				mach.setHeating(false);
				temp = 0;
			} else {
				temp = te.getTemperature();
				while (mach.getTemperature() < temp) {
					mach.setHeating(true);
					if (mailbox.tryFetch() != null) {
						mach.setHeating(false);
						temp = 0;
						return;
					}
				}
				((WashingProgram) (rte.getSource()))
						.putEvent(new AckEvent(this));
				mach.setHeating(false);
			}
		} else if (temp > 0 && mach.getTemperature() < temp - 2) {
			while (mach.getTemperature() < temp) {
				mach.setHeating(true);
				if (mailbox.tryFetch() != null) {
					mach.setHeating(false);
					temp = 0;
					return;
				}
			}
			mach.setHeating(false);
		}
	}
}