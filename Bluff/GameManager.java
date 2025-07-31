import java.util.*;

public class GameManager
{
    private final Player human;
    private final Player ai;
    private final Scanner scanner;
    private final Stack<Card> pile;

    private String currentDeclaredRank;
    private boolean isHumanTurn;

    public GameManager()
    {
        Deck deck = new Deck();
        human = new Player("You", deck.dealHalfDeck());
        ai = new Player("AI", deck.dealHalfDeck());

        scanner = new Scanner(System.in);

        pile = new Stack<>();

        isHumanTurn = true;
    }

    public void startGame()
    {
        while (true)
        {
            GameRenderer.clearScreen();

            System.out.println("🎮 Welcome to Bluff (Cheat)!");
            System.out.println("Pile size: " + pile.size());

            GameRenderer.showPile(pile);

            if(isHumanTurn)
            humanTurn();

            else
            aiTurn();

            if(human.hasNoCards())
            {
                GameRenderer.showWinner("You");
                break;
            }
            else if(ai.hasNoCards())
            {
                GameRenderer.showWinner("AI");
                break;
            }

            isHumanTurn = !isHumanTurn;
        }
    }

    private void humanTurn()
    {
        System.out.println("\nYour Turn");
        GameRenderer.showPlayerHand(human.getHand());

        System.out.print("Enter number of cards to play (or 0 to PASS): ");

        int count = scanner.nextInt();
        scanner.nextLine();

        if(count == 0)
        {
            pile.clear();

            System.out.println("You passed! Pile cleared.");

            return;
        }

        System.out.print("Enter rank you're claiming (A, 2...K): ");

        String claimedRank = scanner.nextLine().trim().toUpperCase();

        currentDeclaredRank = claimedRank;

        List<Card> selectedCards = selectCardsFromHand(human, count);

        pile.addAll(selectedCards);

        human.removeCards(selectedCards);

        GameRenderer.showDeclaration("You", count, claimedRank);

        if(aiDecidesToCallBluff(claimedRank, count))
        {
            System.out.println("\nAI calls your bluff!");

            pause();

            if(isBluff(selectedCards, claimedRank))
            {
                GameRenderer.showBluffResult("Bluff successful! You pick up the pile.");

                human.addCards(new ArrayList<>(pile));
            }
            else
            {
                GameRenderer.showBluffResult("You were truthful. AI picks up the pile.");
                ai.addCards(new ArrayList<>(pile));
            }

            pile.clear();
        }
        else
        System.out.println("AI lets it go.");

        pause();
    }

    private void aiTurn()
    {
        System.out.println("\nAI's Turn");

        List<Card> aiHand = ai.getHand();

        Map<String, List<Card>> rankMap = new HashMap<>();

        for(Card card : aiHand)
        rankMap.computeIfAbsent(card.getRank(), k -> new ArrayList<>()).add(card);

        String selectedRank;

        List<Card> selectedCards;

        boolean bluffing = Math.random() < 0.25;

        if(!bluffing)
        {
            Optional<Map.Entry<String, List<Card>>> maybePlay = rankMap.entrySet().stream().filter(e -> e.getValue().size() >= 1).findAny();

            if(maybePlay.isPresent())
            {
                selectedRank = maybePlay.get().getKey();
                selectedCards = maybePlay.get().getValue().subList(0, 1);
            }
            else
            {
                bluffing = true;
                selectedRank = getRandomRank();
                selectedCards = Collections.singletonList(aiHand.get(0));
            }
        }
        else
        {
            selectedRank = getRandomRank();
            selectedCards = Collections.singletonList(aiHand.get(0));
        }

        pile.addAll(selectedCards);
        ai.removeCards(selectedCards);
        currentDeclaredRank = selectedRank;

        GameRenderer.showDeclaration("AI", selectedCards.size(), selectedRank);

        System.out.print("Do you want to call bluff? (y/n): ");

        String call = scanner.nextLine().trim().toLowerCase();

        if(call.equals("y"))
        {
            if(isBluff(selectedCards, selectedRank))
            {
                GameRenderer.showBluffResult("You caught AI bluffing! AI picks up the pile.");
                ai.addCards(new ArrayList<>(pile));
            }
            else
            {
                GameRenderer.showBluffResult("Wrong call. You pick up the pile.");
                human.addCards(new ArrayList<>(pile));
            }

            pile.clear();
        }
        else
        System.out.println("You let it go.");

        pause();
    }

    private List<Card> selectCardsFromHand(Player player, int count)
    {
        List<Card> hand = player.getHand();
        List<Card> selected = new ArrayList<>();

        while(selected.size() < count)
        {
            System.out.print("Enter index of card " + (selected.size() + 1) + ": ");

            int index = scanner.nextInt();

            scanner.nextLine();

            if(index >= 0 && index < hand.size())
            selected.add(hand.get(index));
            else
            System.out.println("Invalid index.");
        }

        return selected;
    }

    private boolean aiDecidesToCallBluff(String claimedRank, int count)
    {
        long aiCount = ai.getHand().stream().filter(c -> c.getRank().equals(claimedRank)).count();
        return aiCount >= (4 - count);
    }

    private boolean isBluff(List<Card> cards, String claimedRank)
    {
        return cards.stream().anyMatch(c -> !c.getRank().equals(claimedRank));
    }

    private String getRandomRank()
    {
        String[] ranks =
        {
            "A", "2", "3", "4", "5", "6", "7",
            "8", "9", "10", "J", "Q", "K"
        };

        return ranks[new Random().nextInt(ranks.length)];
    }

    private void pause()
    {
        try
        {
            Thread.sleep(1500);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }
}
