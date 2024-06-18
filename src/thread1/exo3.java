package thread1;

class Task implements Runnable {
	int id;
	String town;
	
	public Task(int id, String town) {
		this.id = id;
		this.town = town;
	}
	
	public void run() {
		System.out.println("thread " + this.id + " salue " + this.town);
		
	}
}

public class exo3 {

	public static void main(String[] args) {
		Thread t1, t2, t3;
		t1 = new Thread(new Task(1, "Paris"));
		t2 = new Thread(new Task(2, "Londres"));
		t3 = new Thread(new Task(3, "Tokyo"));
		
		t1.start();
		t2.start();
		t3.start();
	}

}
