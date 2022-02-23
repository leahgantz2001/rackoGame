
import java.util.ArrayList;

public class Pile<T> implements StackInterface<T>{
	private int numCards;
	private ArrayList<T> pile;
	
	public Pile(){
		pile = new ArrayList<T>();
	}
	
	public Pile(int numCards) {
		pile = new ArrayList<T>(numCards);
	}
	
	@Override
	public void pop() throws StackEmptyException {
		if (!isEmpty()) {
		    pile.remove(pile.size() -1);
		}
		else {
		 throw new StackEmptyException();
		}
		
	}

	@Override
	public void push(T t) {
	pile.add(t);
		
	}

	@Override
	public T top() throws StackEmptyException {
		if (!isEmpty()) {
			 return pile.get(pile.size() -1);
			}
			else {
			 throw new StackEmptyException();
			}
	}

	@Override
	public boolean isEmpty() {
		return pile.isEmpty();
	}

}
