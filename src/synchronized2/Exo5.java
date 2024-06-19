package synchronized2;

import java.util.ArrayList;

class Buffer {
	ArrayList<Integer> tab = new ArrayList<Integer>();
	int arrayLength;
	
	public Buffer (int arrayLength) {
		this.arrayLength = arrayLength;
	}
	
	public Integer getElm(int consumerId) {
		synchronized(this.tab) {
			if (this.tab.size() > 0) {
				Integer nb = this.tab.remove(0);
				System.out.println("Consumer " + consumerId + " got " + nb);
				
				return nb;
			}
		}
		
		return null;
	}
	
	public boolean setElm(Integer nb, int producerId) {
		synchronized(this.tab) {
			if (this.tab.size() == this.arrayLength) {
				System.out.println("Producer " + producerId + ": the buffer is full. " + this.tab);
				return false;
			}
			
			this.tab.add(nb);
			System.out.println("Producer " + producerId + " add " + nb);
			
			return true;
		}
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
		System.out.println("Producer " + id + " is created ");
	}

	@Override
	public void run() {
		int index = 0;
		while (true) {
			if  (this.nb == index) {
				break;
			}
			
			int nb = (int)Math.floor(Math.random() * (100 - 10 + 1) + 10);
			boolean result = this.buffer.setElm(nb, this.id);
			if (result == true) {
				//System.out.println("Producer " + this.id + " add " + nb);
				index++;
			} else {
				//System.out.println("Producer " + this.id + ": the buffer is full. " + this.buffer);
				//break;
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Thread.yield();
		}
	}
}

class Consumer implements Runnable{
	private Buffer buffer;
	private Integer nb;
	private int id;
	
	public Consumer (Buffer buffer, int id) {
		this.buffer = buffer;
		this.id = id;
		System.out.println("Consumer " + id + " is created ");
	}
	
	public void run() {
		while (true) {
			this.nb = this.buffer.getElm(this.id);
			//System.out.println("Consumer " + this.id + " got " + this.nb);
			
			if (this.nb != null) {
				break;
			}
		}
	}
}


public class Exo5 {

	public static void main(String[] args) throws InterruptedException {
		Buffer buffer = new Buffer(3);
		
		Producer tr1, tr2, tr3;
		Thread t1, t2, t3;
		tr1 = new Producer(1, 3, buffer);
		t1 = new Thread(tr1);
		tr2 = new Producer(2, 3, buffer);
		t2 = new Thread(tr2);
		tr3 = new Producer(3, 3, buffer);
		t3 = new Thread(tr3);
		
		t1.start();
		t2.start();
		t3.start();
	
		
		for (int i = 0; i < 6; i++) {
			Thread t = new Thread(new Consumer(buffer, i + 1));
			t.start();
		}
		
		//System.out.println(buffer.toString());
		
	}
}
