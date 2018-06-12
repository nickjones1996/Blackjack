import java.util.ArrayList;
import java.util.List;

public class Player {
	private String name;
	private int score;
	private int wins;
	private int losses;
	private boolean bust;
	private List<Card> cards;
	public Player()
	{}
	public Player(String name)
	{
		wins = 0;
		losses = 0;
		bust = false;
		score = 0;
		cards = new ArrayList<Card>();
	}
	public void takeCard(Card card)
	{
		cards.add(card);
		score += card.getValue();
		if(score == 21)
			GameController.DeclareWinner(this);
		else if(score > 21)
		{
			bust = true;
			GameController.AddLoser(this);			
		}
	}
	public boolean takeHit()
	{
		return score < 17;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getWins() {
		return wins;
	}
	public void setWins(int wins) {
		this.wins = wins;
	}
	public int getLosses() {
		return losses;
	}
	public void setLosses(int losses) {
		this.losses = losses;
	}
	public boolean isBust() {
		return bust;
	}
	public void setBust(boolean bust) {
		this.bust = bust;
	}
	public List<Card> getCards() {
		return cards;
	}
	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
	public String getName()
	{
		return name;		
	}
}
