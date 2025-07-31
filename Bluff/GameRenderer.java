import java.util.*;

public class GameRenderer
{

    public static void clearScreen()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void showPlayerHand(List<Card> hand)
    {
        System.out.println("Your Hand:");
        System.out.println(getGroupedCardDisplay(hand));
    }

    public static void showPile(Stack<Card> pile)
    {
        System.out.println("\n--- PILE ---");

        System.out.println("Pile has " + pile.size() + " card(s).");

        if(!pile.isEmpty())
        {
            Card top = pile.peek();
            System.out.println("Top Card: [ " + top.getRank() + " ] (face down)");
        }
        System.out.println("------------\n");
    }

    public static void showDeclaration(String player, int count, String rank)
    {
        System.out.println(player + " declared: " + count + "x " + rank + "\n");
        System.out.println(getCardVisual(rank, count));
    }

    public static void showBluffResult(String result)
    {
        System.out.println("\n" + result + "\n");
    }

    public static void showWinner(String winner)
    {
        System.out.println(winner + " has won the game!\n");
        System.out.println("Thanks for playing!");
    }

    private static String getGroupedCardDisplay(List<Card> hand)
    {
        StringBuilder sb = new StringBuilder();

        int[] freq = new int[13];

        for(Card c : hand)
        {
            int idx = getIndex(c.getRank());

            if(idx != -1)
            freq[idx]++;
        }

        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        for(int i = 0; i < 13; i++)
        {
            if(freq[i] > 0)
            sb.append(freq[i]).append("x ").append(ranks[i]).append("   ");
        }
        return sb.toString();
    }

    private static String getCardVisual(String rank, int count)
    {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < count; i++)
        sb.append("+-----+ ");

        sb.append("\n");

        for(int i = 0; i < count; i++)
        sb.append("|  ").append(String.format("%-2s", rank)).append(" | ");

        sb.append("\n");

        for(int i = 0; i < count; i++)
        sb.append("|     | ");

        sb.append("\n");

        for(int i = 0; i < count; i++)
        sb.append("|  ").append(String.format("%2s", rank)).append(" | ");

        sb.append("\n");

        for(int i = 0; i < count; i++)
        sb.append("+-----+ ");

        sb.append("\n");

        return sb.toString();
    }

    private static int getIndex(String rank)
    {
        switch (rank)
        {
            case "A":
            return 0;

            case "2":
            return 1;

            case "3":
            return 2;

            case "4":
            return 3;

            case "5":
            return 4;

            case "6":
            return 5;

            case "7":
            return 6;

            case "8":
            return 7;

            case "9":
            return 8;

            case "10":
            return 9;

            case "J":
            return 10;

            case "Q":
            return 11;

            case "K":
            return 12;

            default:
            return -1;
        }
    }
}
