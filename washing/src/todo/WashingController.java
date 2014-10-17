package todo;

import done.*;

public class WashingController implements ButtonListener {
	private AbstractWashingMachine theMachine;
	private double theSpeed;
	private TemperatureController tc;
	private WaterController wc;
	private SpinController sc;
	private WashingProgram wp;

	public WashingController(AbstractWashingMachine theMachine, double theSpeed) {
		this.theMachine = theMachine;
		this.theSpeed = theSpeed;
		sc = new SpinController(theMachine, theSpeed);
		wc = new WaterController(theMachine, theSpeed);
		tc = new TemperatureController(theMachine, theSpeed);
		sc.start();
		wc.start();
		tc.start();
	}

	public void processButton(int theButton) {
		switch (theButton) {
		case 1:
			if (wp == null || !wp.isAlive()) {
				wp = new WashingProgram1(theMachine, theSpeed, tc, wc, sc);
				wp.start();
			}
			break;
		case 2:
			if (wp == null || !wp.isAlive()) {
				wp = new WashingProgram2(theMachine, theSpeed, tc, wc, sc);
				wp.start();
			}
			break;
		case 3:
			if (wp == null || !wp.isAlive()) {
				wp = new WashingProgram3(theMachine, theSpeed, tc, wc, sc);
				wp.start();
			}
			break;
		default:
			if (wp != null && wp.isAlive()) {
				wp.interrupt();
			}
		}
	}
}
