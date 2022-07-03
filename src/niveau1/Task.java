package niveau1;

public class Task implements Runnable{
	private String title;
	
	public Task(String title) {
		this.title = title;
	}
	
	public void run() {
		int index = 0;
		while (!Thread.interrupted() && index < 1) {
			System.out.println(this.title);
			index++;
		}
	}
	
	//public static void main(String[] args) {}
}
