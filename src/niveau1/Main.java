package niveau1;

public class Main {

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws InterruptedException {
		Task ta1 = new Task("email");
		Thread t1 = new Thread(ta1);
		Task ta2 = new Task("sms");
		Thread t2 = new Thread(ta2);
		Task ta3 = new Task("websocket");
		Thread t3 = new Thread(ta3);
		
		System.out.println("résultat");
		
		t1.start();
		//t1.yield();
		//t1.interrupt();
		t2.start();
		//t2.yield();
		t3.start();
		//t3.yield();
		
	}

}
