public class Card
{
    private final String rank;

    public Card(String rank)
    {
        this.rank = rank;
    }

    public String getRank()
    {
        return rank;
    }

    @Override
    public String toString()
    {
        return rank;
    }
}