package cs1632;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

public class WarGameTest {

    @Test
    public void testDeckHas52Cards() {
        PlayingCardDeck deck = new PlayingCardDeck();

        int count = 0;
        while (!deck.isEmpty()) {
            deck.nextCard();
            count++;
        }

        assertEquals(52, count, "Deck should contain 52 cards");
    }

    @Test
    public void testNextCardRemovesCard() {
        PlayingCardDeck deck = new PlayingCardDeck();
        PlayingCard first = deck.nextCard();

        assertNotNull(first, "nextCard() should return a card");
        assertEquals(51, countRemaining(deck), "Deck should have 51 cards left");
    }

    private int countRemaining(PlayingCardDeck deck) {
        int count = 0;
        while (!deck.isEmpty()) {
            deck.nextCard();
            count++;
        }
        return count;
    }

    @Test
    public void testPlayersStartWith26Cards() {
        WarGame game = new WarGame();


        Queue<PlayingCard> p1 = getQueue(game, "p1");
        Queue<PlayingCard> p2 = getQueue(game, "p2");

        assertEquals(26, p1.size(), "Player 1 should start with 26 cards");
        assertEquals(26, p2.size(), "Player 2 should start with 26 cards");
    }

    @SuppressWarnings("unchecked")
    private Queue<PlayingCard> getQueue(WarGame game, String fieldName) {
        try {
            var field = WarGame.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (Queue<PlayingCard>) field.get(game);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSimpleRoundWinnerGetsCards() {
        Queue<PlayingCard> p1 = new LinkedList<>();
        Queue<PlayingCard> p2 = new LinkedList<>();

        // Player 1 wins (higher card value)
        p1.add(new PlayingCard(12, 0)); // Ace
        p2.add(new PlayingCard(5, 0));  // 7

        PlayingCard c1 = p1.poll();
        PlayingCard c2 = p2.poll();

        if (c1.playingCardValue() > c2.playingCardValue()) {
            p1.add(c1);
            p1.add(c2);
        }

        assertEquals(2, p1.size(), "Player 1 should collect both cards");
        assertEquals(0, p2.size(), "Player 2 should have no cards left");
    }

    @Test
    public void testWarEndsIfPlayerCannotContinue() {
        WarGame game = new WarGame();

        Queue<PlayingCard> p1 = getQueue(game, "p1");
        Queue<PlayingCard> p2 = getQueue(game, "p2");


        p1.clear();
        p2.clear();

        p1.add(new PlayingCard(5, 0)); // 7
        p2.add(new PlayingCard(5, 1)); // 7


        game.play();

        assertTrue(p1.isEmpty() || p2.isEmpty(),
                "One or both players should lose when they cannot continue a war");
    }
}
