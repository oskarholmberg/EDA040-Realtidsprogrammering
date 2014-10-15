package todo;

/*
 * Real-time and concurrent programming course, laboratory 3
 * Department of Computer Science, Lund Institute of Technology
 *
 * PP 980812 Created
 * PP 990924 Revised
 */

import done.*;

/**
 * Program 3 of washing machine. Does the following:
 * <UL>
 * <LI>Switches off heating
 * <LI>Switches off spin
 * <LI>Pumps out water
 * <LI>Unlocks the hatch.
 * </UL>
 */
class WashingProgram2 extends WashingProgram {

	// ------------------------------------------------------------- CONSTRUCTOR

	/**
	 * @param mach
	 *            The washing machine to control
	 * @param speed
	 *            Simulation speed
	 * @param tempController
	 *            The TemperatureController to use
	 * @param waterController
	 *            The WaterController to use
	 * @param spinController
	 *            The SpinController to use
	 */
	public WashingProgram2(AbstractWashingMachine mach, double speed,
			TemperatureController tempController,
			WaterController waterController, SpinController spinController) {
		super(mach, speed, tempController, waterController, spinController);
	}

	// ---------------------------------------------------------- PUBLIC METHODS

	/**
	 * This method contains the actual code for the washing program. Executed
	 * when the start() method is called.
	 */
	protected void wash() throws InterruptedException {
		myMachine.setLock(true);
		myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_FILL,
				0.5));
		mailbox.doFetch();
		myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_IDLE,
				0.5));
		myTempController.putEvent(new TemperatureEvent(this,
				TemperatureEvent.TEMP_SET, 40));
		mailbox.doFetch();
		mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_SLOW));
		sleep(10000);
		mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_OFF));
		myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_DRAIN,
				0.0));
		myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_FILL,
				0.5));
		mailbox.doFetch();
		myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_IDLE,
				0.5));

		myTempController.putEvent(new TemperatureEvent(this,
				TemperatureEvent.TEMP_SET, 90));
		mailbox.doFetch();
		mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_SLOW));
		sleep(10000);
		mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_OFF));
		myTempController.putEvent(new TemperatureEvent(this,
				TemperatureEvent.TEMP_IDLE, 0.0));
		myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_DRAIN,
				0.0));
		mailbox.doFetch();
		for (int i = 0; i < 5; i++) {
			myWaterController.putEvent(new WaterEvent(this,
					WaterEvent.WATER_FILL, 0.5));
			mailbox.doFetch();
			mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_SLOW));
			sleep(5000);
			mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_OFF));
			myWaterController.putEvent(new WaterEvent(this,
					WaterEvent.WATER_DRAIN, 0.0));
			mailbox.doFetch();
		}
		mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_FAST));
		sleep(10000);
		mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_OFF));
		myMachine.setLock(false);
	}
}
