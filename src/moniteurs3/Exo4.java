package moniteurs3;

import java.util.ArrayList;
import java.util.HashMap;

class Room {
	Group[][] places;
	int rangs, columns;
	private final Object ob = new Object();
	private HashMap<Integer, ArrayList<int[]>> store = new HashMap<Integer, ArrayList<int[]>>();
	
	public Room(int n, int m) {
		this.rangs = n;
		this.columns = m;
		this.places = new Group[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				this.places[i][j] = null;
			}
		}
	}
	
	public boolean reserve(Group group) throws InterruptedException {
		synchronized(this.ob) {
			while (!this.capaciteOK(group.number)) {
				System.out.println("Groupe " + group.id + " : j'attends " + group.number + " places");
				this.ob.wait();
			}
			ArrayList<int[]> result = this.reserverContigues(group.number);
			this.store.put(group.id, result);
			System.out.println("Groupe " + group.id + " : j'ai réservé " + group.number + " places");
			this.displayArray2D(result);
			for (int i = 0; i < result.size(); i++) {
		       this.places[result.get(i)[0]][result.get(i)[1]] = group;
		    }
			
			return true;
		}
	}
	
	public void cancel(Group group) {
		synchronized(this.ob) {
			ArrayList<int[]> result = this.store.get(group.id);
			for (int i = 0; i < result.size(); i++) {
		       this.places[result.get(i)[0]][result.get(i)[1]] = null;
		    }
			this.store.remove(group.id);
			System.out.println("Groupe " + group.id + " : j'ai annulé ma réservation pour libérer " + group.number + " places");
				
			this.ob.notifyAll();
		}
	}
	
	private boolean capaciteOK(int n) {
		int librePlaces = 0;
		for (int i = 0; i < this.rangs; i++) {
			for (int j = 0; j < this.columns; j++) {
				if (this.places[i][j] == null) {
					librePlaces++;
				} 
			}
		}
		
		
		if (librePlaces < n) {
			return false;
		}
		
		return true;
	}
	
	private ArrayList<int[]> reserverContigues(int nb) {
		Integer column = null, len, len2 = 0;
		ArrayList<int[]> places = new ArrayList<int[]>();
		for (int i = 0; i < this.rangs; i++) {
			len = 0;
			for (int j = 0; j < this.columns; j++) {
				if (this.places[i][j] == null) {
					int[] tab = {i, j};
					places.add(tab);
					len2++;
					if (len2 == nb) {
						return places;
					}
					
					if (column == null) {
						column = j;
					}
					len++;
					if (len == nb) {
						ArrayList<int[]> list = new ArrayList<int[]>();
						int[] tab1 = {i, j - nb};
						list.add(tab1);
						int[] tab2 = {i, j};
						list.add(tab2);

						return list;
					}
				} else {
					column = null;
					len = 0;
				}
			}
		}
		
		return null;
	}
	
	public static void displayArray2D(ArrayList<int[]> list){
		for (int i = 0; i < list.size(); i++) {
	        for (int j = 0; j < list.get(i).length; j++) {
	            System.out.print(list.get(i)[j] + " ");
	        }
	        System.out.println();
	     }
	}
}

class Group implements Runnable {
	public int id;
	public int number;
	public Room room;
	public boolean toCancel = false;
	
	public Group(int id, int n, Room room, boolean toCancel) {
		this.id = id;
		this.number = n;
		this.room = room;
		this.toCancel = toCancel;
	}

	@Override
	public void run() {
		try {
			boolean result = this.room.reserve(this);
			if (result == true && this.toCancel == true) {
				Thread.sleep(3000);
				this.room.cancel(this);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}


public class Exo4 {

	public static void main(String[] args) {
		Room room = new Room(3, 4);

		Group g1, g2, g3;
		Thread t1, t2, t3;
		
		g1 = new Group(1, 6, room, false);
		t1 = new Thread(g1);
		g2 = new Group(2, 4, room, true);
		t2 = new Thread(g2);
		g3 = new Group(3, 5, room, false);
		t3 = new Thread(g3);
		
		t1.start();
		t2.start();
		t3.start();
	}

}
