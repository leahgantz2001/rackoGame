


public class Board {
	private Pile<Integer> stockPile;
	private int[] cards;
	
	//constructor  
	public Board(Pile<Integer> stockPile) throws StackEmptyException {
		this.cards = new int[10];
		this.stockPile = stockPile;
		for (int j = 0; j < cards.length; j++) {
			cards[j] = (int) (stockPile.top());
			stockPile.pop();
		}
		
	}
	
	//setter / getter
	public void setBoard(int[] cards) {
		this.cards = cards;
	}
	
	public int[] getBoard(){
		return cards;
	}
}


