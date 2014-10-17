package todo;

import se.lth.cs.realtime.*;
import se.lth.cs.realtime.event.RTEvent;
import done.AbstractWashingMachine;

public class TemperatureController extends PeriodicThread {
	private AbstractWashingMachine mach;
	private double temp;
	private boolean ack, heat;
	private TemperatureEvent te;

	public TemperatureController(AbstractWashingMachine mach, double speed) {
		super((long) (1000 / speed)); // TODO: replace with suitable period
		this.mach = mach;
		temp = 0;
		ack = heat = false;
	}

	public void perform() {
		RTEvent rte = mailbox.tryFetch();
		if (rte != null) {
			te = (TemperatureEvent) rte;
			if (te.getMode() == te.TEMP_IDLE) {
				mach.setHeating(false);
				temp = 0;
				heat = ack = false;
			} else {
				heat = ack = true;
				temp = te.getTemperature();
				if (mach.getTemperature() < temp && mach.getWaterLevel() > 0) {
					mach.setHeating(true);
				}
			}
		} else if (heat && temp > 0 && mach.getTemperature() < temp - 1.8
				&& mach.getWaterLevel() > 0) {
			mach.setHeating(true);
		}

		else if (heat && temp <= mach.getTemperature()) {
			mach.setHeating(false);
			if (ack) {
				ack = false;
				((WashingProgram) (te.getSource()))
						.putEvent(new AckEvent(this));
			}

		}
	}
}
