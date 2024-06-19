package thread1;

import java.util.ArrayList;

class Buffer {
	ArrayList<Integer> tab = new ArrayList<Integer>();
	int arrayLength;
	
	public Buffer (int arrayLength) {
		this.arrayLength = arrayLength;
	}
	
	public String toString() {
		return tab.size() + " number(s) : " + tab.toString();
	}
}

class Producer implements Runnable {
	int id;
	int nb;
	Buffer buffer;
	
	public Producer(int id, int nb, Buffer buffer) {
		this.id = id;
		this.nb = nb;
		this.buffer = buffer;
	}

	@Override
	public void run() {
		int index = 0;
		while (true) {
			if  (this.nb == index) {
				break;
			}
			
			if (this.buffer.tab.size() < this.buffer.arrayLength) {
				nb = (int)Math.floor(Math.random() * (100 - 10 + 1) + 10);
				this.buffer.tab.add(nb);
				System.out.println("Producer " + this.id + " add " + nb);
				index++;
			} else {
				System.out.println("Producer " + this.id + ": the buffer is full");
				break;
			}
			Thread.yield();
		}
	}
	
}

public class Exo4 {

	public static void main(String[] args) throws InterruptedException {
		Buffer buffer = new Buffer(4);
		
		Producer tr1, tr2, tr3;
		Thread t1, t2, t3;
		tr1 = new Producer(1, 2, buffer);
		t1 = new Thread(tr1);
		tr2 = new Producer(2, 1, buffer);
		t2 = new Thread(tr2);
		tr3 = new Producer(3, 3, buffer);
		t3 = new Thread(tr3);
		
		t1.start();
		t2.start();
		t3.start();
		
		t1.join();
		t2.join();
		t3.join();
		
		System.out.println(buffer.toString());
	}

}
