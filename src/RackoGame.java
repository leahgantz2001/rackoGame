
import java.util.*;

public class RackoGame {
	private Pile<Integer> stockPile;
	private Pile<Integer> discardPile;
	private ArrayList<Player> players;
	private Scanner keyboard;
	
	public RackoGame() {
		this.stockPile = null;
		this.discardPile = new Pile<Integer>();
		this.players = null;
		this.keyboard = new Scanner(System.in);
			
	}

	public void setupGame() throws StackEmptyException {		
		//get the number of players
		System.out.println("How many players will be playing? ");
		int numPlayers = keyboard.nextInt();
		while(numPlayers > 4 || numPlayers < 2) {
			System.out.println("You must play with 2, 3, or 4 players!!");
			System.out.println("How many players will be playing? ");
			numPlayers = keyboard.nextInt();
		}
		players = new ArrayList<Player>(numPlayers);
		
		
		//set up the cards 
		if(numPlayers == 2) {
			int numCards = 40;
			stockPile = setupPile(numCards);
			}
		
		else if(numPlayers == 3) {
			int numCards = 50;
			stockPile = setupPile(numCards);
		}  
		
		else {
			int numCards = 60;
			stockPile = setupPile(numCards);
		}
		
		for(int i = 0; i < numPlayers; i++) {
			Board board = new Board(stockPile);
			Player player = new Player(board);
			players.add(player);
		}
		
				
		takeTurn(players, stockPile);
	}
	
	
	
	public Pile<Integer> setupPile(int numCards) {
		stockPile = new Pile<Integer>(numCards);
		Random rand = new Random();
		int num;
		//arraylist to make sure that no card is being added twice
		ArrayList<Integer> inPile = new ArrayList<Integer>();
		for(int i = 1; i <= numCards; i++) {
			num = rand.nextInt(numCards) + 1;
			while(inPile.contains(num)) {
				num = rand.nextInt(numCards) + 1;
			}
			inPile.add(num);
			stockPile.push(num);
		}
		return stockPile;
	}
	
	
	public void takeTurn(ArrayList<Player> players, Pile<Integer> stockPile) throws StackEmptyException {	
		boolean isWinner = false;
		//looping through the players array giving each person a turn
		for(int i = 1; i <= players.size(); i++) {
			Player currentPlayer = players.get(i - 1);
			System.out.println("Player " + (i));
			isWinner = playGame(players, currentPlayer, stockPile, discardPile, i);
			if(isWinner == true) {
				System.out.println("Horray!! Player " + i + " won the game!!");
				break;
			}
			if(i == (players.size())) {
				i = 0;
			}
		}
		
	}
	
	public boolean playGame(ArrayList<Player> players, Player currentPlayer, Pile<Integer> stockPile, Pile<Integer> discardPile, int i) throws StackEmptyException {
		//print the current player's board
		for(int j = 0; j < currentPlayer.getBoard().getBoard().length; j++) {
			System.out.println((j + 1) + ": " + currentPlayer.getBoard().getBoard()[j] + " ");
		}
		System.out.println();
		String choice; 
		String change;
		boolean isWinner;
		int card;
		int cardOut;
		int topCard;
		int loop = 0;
		keyboard.nextLine();
		//if the discard pile is not empty, the player can choose from there or the stock pile if not they must choose from the stock pile
		if(!(discardPile.isEmpty())) {
			System.out.println("Please enter 's' if you would like to choose from the stock pile or 'd' if you would like to choose from the discard pile.");
			choice = keyboard.nextLine();
			while ((!choice.equals("s")) && (!choice.equals("d"))) {
				System.out.println("That was invalid, please enter 's' or 'd'");
				choice = keyboard.nextLine();
			}
		}
		else {
			choice = "s";
		}
		
		//take the top card from the pile of the player's choice
		if (choice.equals("s")) {
			//if the stockpile is empty, then put the discard pile cards onto the stock pile
			if (stockPile.isEmpty()){
				while(!(discardPile.isEmpty())) {
				topCard = Integer.parseInt(discardPile.top().toString());
				discardPile.pop();
				stockPile.push(topCard);
				}
			}
			
			System.out.println("The card you got from the stock pile is " + stockPile.top());
			//if they chose from the stock pile, then ask the player if they want to switch the card
			System.out.println("Would you like to switch this card with a card on your board? (y/n) ");
			change = keyboard.nextLine();
			while((!(change.equals("y"))) && (!(change.equals("n")))) {
				System.out.println("That was invalid input. Please enter 'y' or 'n' ");
				change = keyboard.nextLine();
				}
			
		}	
		
		else {
			System.out.println("The card you got from the discard pile is: " + discardPile.top());
			change = "y";
		}
		
		//switch the card	
		if(change.equals("y")) {
			System.out.println("Which card do you want to switch it with? ");
			card = keyboard.nextInt();
			while (card > 10 || card < 1) {
				System.out.println("You must choose a card between one and ten!");
				System.out.println("Please enter another card: ");
				card = keyboard.nextInt();
			}
			//get the card we want switched out and add it to the discard pile
			cardOut = currentPlayer.getBoard().getBoard()[card - 1];
			
			if(choice.equals("s")) {
				//put the other card in instead from the correct pile
				topCard = Integer.parseInt(stockPile.top().toString());
				stockPile.pop();
				discardPile.push(cardOut);
			}
			else {
				topCard = Integer.parseInt(discardPile.top().toString());
				discardPile.pop();
				discardPile.push(cardOut);
			}
			currentPlayer.getBoard().getBoard()[card - 1] =  topCard;
			currentPlayer.setBoard(currentPlayer.getBoard());
			//print the current player's board again so we see the switch
			System.out.println("Player " + i + "'s  board now looks like: ");
			for(int j = 0; j < currentPlayer.getBoard().getBoard().length; j++) {
				System.out.println((j+1) + ": " + currentPlayer.getBoard().getBoard()[j] + " ");
						}
			System.out.println();
			System.out.println();
		}
		//if the card wasnt switched then just add it to the discard pile
		else {
			cardOut = Integer.parseInt(stockPile.top().toString());
			stockPile.pop();
			discardPile.push(cardOut);
		}
		System.out.println("Stock Pile");
		System.out.println("Discard Pile: " +  discardPile.top());
		
	
		//check for a winner
		for(int j = 1; j < currentPlayer.getBoard().getBoard().length; j++) {
			if(!(currentPlayer.getBoard().getBoard()[j] > currentPlayer.getBoard().getBoard()[j - 1])) {
				break;
			}
			else {
				loop++;
			}
		}
		
		if(loop == 9) {
			return isWinner = true;
		}
	return isWinner = false;
	
	}
}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
