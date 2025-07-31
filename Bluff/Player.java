import java.util.*;

public class Player
{
    private final String name;
    private final List<Card> hand;

    public Player(String name, List<Card> initialHand)
    {
        this.name = name;
        this.hand = new ArrayList<>(initialHand);
    }

    public String getName()
    {
        return name;
    }

    public List<Card> getHand()
    {
        return hand;
    }

    public void addCards(List<Card> cards)
    {
        hand.addAll(cards);
    }

    public void removeCards(List<Card> cardsToRemove)
    {
        hand.removeAll(cardsToRemove);
    }

    public boolean hasNoCards()
    {
        return hand.isEmpty();
    }

    public void printHand()
    {
        System.out.println(name + "'s Hand: " + hand);
    }
}
