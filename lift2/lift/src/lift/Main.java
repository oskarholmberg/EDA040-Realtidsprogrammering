package lift;

public class Main {
	
	public static void main(String[] args) {
		LiftView lv= new LiftView();
		LiftMonitor lm = new LiftMonitor(lv);			//Skapa en LiftMonitor 
		new Lift(lm, lv
				).start();								//Starta hisstråden med en LiftMonitor som parameter 
		for(int i = 0; i < 20; i++){					//Skapa 20 persontrådar och starta dem 
			new Person(lm).start();
		}
	}
}
