import java.util.*;

public class Deck
{
    private final List<Card> cards = new ArrayList<>();

    private static final String[] RANKS =
    {
        "A", "2", "3", "4", "5", "6", "7",
        "8", "9", "10", "J", "Q", "K"
    };

    public Deck()
    {
        for(String rank : RANKS)
        {
            for(int i = 0; i < 4; i++)
            cards.add(new Card(rank));
        }
        Collections.shuffle(cards);
    }

    public List<Card> dealHalfDeck()
    {
        List<Card> half = new ArrayList<>(cards.subList(0, 26));
        cards.subList(0, 26).clear();
        
        return half;
    }
}
