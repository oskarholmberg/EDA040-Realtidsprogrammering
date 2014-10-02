package lift;

public class Lift extends Thread {

	private Monitor m;

	public Lift(Monitor monitor) {
		m = monitor;
	}

	public void run() {
		while (true) {
			try {
				sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			m.moveLift();
		}
	}

}
