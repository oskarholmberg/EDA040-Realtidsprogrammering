package lift;

public class Person extends Thread {
	private int currentFloor;
	private int targetFloor;
	private Monitor monitor;

	public Person(Monitor monitor) {
		this.monitor = monitor;
		currentFloor = (int) (Math.random() * 7);
		targetFloor = nextFloor();
	}

	public void run() {
		monitor.enterFloor(currentFloor);
		while (true) {
			monitor.liftAction(targetFloor, currentFloor);
			try {
				sleep(1000 * ((int) (Math.random() * 46)));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			currentFloor = targetFloor;
			targetFloor = nextFloor();
			monitor.enterFloor(currentFloor);
		}
	}

	private int nextFloor() {
		int n = (int) (Math.random() * 7);
		while (n == currentFloor) {
			n = (int) (Math.random() * 7);
		}
		return n;
	}
}
