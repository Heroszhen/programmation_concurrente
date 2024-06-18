package thread1;

class TaskThread extends Thread {
	String name;
	
	public TaskThread(String name) {
		this.name = name;
	}
	
	public void run() {
		System.out.println("Bonjour " + this.name);
		//applique une pause à l'exécution du thread courant, afin de libérer le processeur pour d'autres unités d'exécution en attente
		Thread.yield();
		System.out.println("Au revoir " + this.name);
	}
}

class TaskRunnable implements Runnable {
	String name;
	
	public TaskRunnable(String name) {
		this.name = name;
	}

	public void run() {
		while (true) {
			System.out.println(this.name + ": coucou");
			if (Thread.interrupted()) {
				break;
			}
		}
	}
}

public class Lesson {

	public static void main(String[] args) {
		System.out.println(Runtime.getRuntime().availableProcessors());
		
		/*
		//Thread
		TaskThread tt1, tt2, tt3;
		tt1 = new TaskThread("t1");
		tt2 = new TaskThread("t2");
		tt3 = new TaskThread("t3");
		
		tt1.start();
		tt2.start();
		tt3.start();
		*/
		
		//runnable
		TaskRunnable tr1, tr2, tr3;
		Thread t1, t2, t3;
		tr1 = new TaskRunnable("t1");
		t1 = new Thread(tr1);
		tr2 = new TaskRunnable("t2");
		t2 = new Thread(tr2);
		tr3 = new TaskRunnable("t3");
		t3 = new Thread(tr3);
		
		t1.start();
		t2.start();
		t3.start();
		
		t3.interrupt();
		t2.interrupt();
		t1.interrupt();
	}

}
