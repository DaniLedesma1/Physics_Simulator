package tp.examples.threads.ex5;

public class List {

	private Cell data;

	class Cell {
		Integer value;
		Cell next;
	}

	// make this method synchronized and run Test again
	//
	public void add(Integer x) {
		Cell tmp = new Cell();
		tmp.value = x;
		tmp.next = data;
		data = tmp;
	}

	public int length() {
		int i = 0;
		Cell aux = data;
		while (aux != null) {
			aux = aux.next;
			i++;
		}
		return i;

	}

	public Integer get(int i) {
		Cell tmp = data;

		while (i > 0 && tmp != null) {
			tmp = tmp.next;
		}

		return tmp == null ? null : tmp.value;
	}

}
