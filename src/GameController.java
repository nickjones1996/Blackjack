import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
@ManagedBean
@SessionScoped
public class GameController {
private int numberOfPlayers = 0;
private static List<Player> stillPlaying;
private static List<Player> lost;
private static List<Card> deck;
private static List<Card> yourCards;
private int yourScore = 0;
private static int cardsLeft = 51;
private String message = "";
/*
 * 
 */
public GameController()
{
	stillPlaying = new ArrayList<Player>();
	lost = new ArrayList<Player>();
	deck = new ArrayList<Card>();
	yourCards = new ArrayList<Card>();
	InitializeGame();
	}
/*
 * 
 */
public int getNumberOfPlayers() {
	return numberOfPlayers;
}

/*
 * 
 */public void setNumberOfPlayers(int numberOfPlayers) {
	this.numberOfPlayers = numberOfPlayers;
}
 /*
  * 
  */
public void InitializeGame()
{
	Player p = new Player("Dealer");
	stillPlaying.add(p);
	for(int i = 0; i < numberOfPlayers - 1; i++)
	{
		p = new Player("CPU " + (i+1));
		stillPlaying.add(p);
	}
	deck = initializeDeck();
	Collections.shuffle(deck);
	Deal();
	Deal();
	getCardForPlayer();
	getCardForPlayer();
}
/*
 * 
 */
public void Deal() {
	for(int i = 0; i < numberOfPlayers; i++)
	{
		if(cardsLeft >= 0)
		{
			stillPlaying.get(i).takeCard(deck.get(cardsLeft));
			cardsLeft--;
		}
		else {
			Collections.shuffle(deck);
			cardsLeft = 51;
			stillPlaying.get(i).takeCard(deck.get(cardsLeft));
			cardsLeft--;			
		}
	}
}
/*
 * 
 */
private List<Card> initializeDeck() {
	String[] suits = {"Spades", "Diamonds", "Hearts", "Clubs"};
	String [] face = {"King", "Queen", "Jack", "Ten", "Nine", "Eight", "Seven", "Six","Five", "Four", "Three", "Two", "Ace"};
	int[] values = {10, 10, 10, 10, 9, 8, 7, 6, 5, 4, 3, 2, 11}; 
	List<Card> returnable = new ArrayList<Card>();
	for(int i = 0; i < 4; i++)
		for(int j = 0; j < 13; j++)
		{
			Card temp = new Card( values[j], face[j] + " of " + suits[i]);
			returnable.add(temp);
		}
	return returnable;
}
/*
 * 
 */
public static String DeclareWinner(Player player) {	
	String str = "";
	str += "Player: " + player.getName() + " wins!";
	while(!lost.isEmpty())
		stillPlaying.add(lost.remove(0));
	
	return str;
}
/*
 * 
 */
public static void AddLoser(Player player) {
	lost.add(player);
	stillPlaying.remove(player);	
}
/*
 * 
 */
public static void resetDeck()
{
	cardsLeft = 51;
	Collections.shuffle(deck);
	Collections.shuffle(deck);//one more time for good measure
}
/*
 * 
 */
public static Card getCard()
{
	if(cardsLeft >= 0)
	{
		cardsLeft--;
		return deck.get(cardsLeft);
	}
	else
	{
		resetDeck();
		return deck.get(cardsLeft);
	}
}
public  void getCardForPlayer()
{
	if(cardsLeft >= 0)
	{
		Card card = deck.get(cardsLeft);
		yourCards.add(card);
		yourScore += card.getValue();
		cardsLeft--;
	}
	else
	{
		resetDeck();
		Card card = deck.get(cardsLeft);
		yourScore += card.getValue();
		yourCards.add(card);
	}
	if(yourScore == 21)
		youWin();
	else if(yourScore > 21)
		youLose();
	getMessage();
}
public void youLose() {
	resetDeck();
	yourCards.clear();
	stillPlaying.clear();
	lost.clear();
	yourScore = 0;
	message = "sorry you lose";
}
	

public void youWin() {
	resetDeck();
	yourCards.clear();
	stillPlaying.clear();
	lost.clear();
	yourScore = 0;
	message = "congrats you win!";
}
public  List<Card> getYourCards() {
	return yourCards;
}
public String getMessage()
{
	generateMessage();
	return message;
}
public void generateMessage() {

	message = "";
	for(Card c : yourCards)
		message += c.getName() + System.lineSeparator();
	message += "your score: " + yourScore + System.lineSeparator();
}
public int getYourScore() {
	return yourScore;
}
public void setYourScore(int yourScore) {
	this.yourScore = yourScore;
}

}
