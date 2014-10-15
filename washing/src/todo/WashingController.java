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
			wp = new WashingProgram1(theMachine, theSpeed, tc, wc, sc);
			break;
		case 2:
			wp = new WashingProgram2(theMachine, theSpeed, tc, wc, sc);
			break;
		case 3:
			wp = new WashingProgram3(theMachine, theSpeed, tc, wc, sc);
			break;
		default:
			wp = new WashingProgram0(theMachine, theSpeed, tc, wc, sc);

		}
		wp.start();
	}
}
