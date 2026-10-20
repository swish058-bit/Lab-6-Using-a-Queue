package cs1632;

import java.util.LinkedList;
import java.util.Queue;

public class WarGame {

    private Queue<PlayingCard> p1 = new LinkedList<>();
    private Queue<PlayingCard> p2 = new LinkedList<>();

    public WarGame() {
        PlayingCardDeck deck = new PlayingCardDeck();

        for (int i = 0; i < 26 && !deck.isEmpty(); i++) {
            p1.add(deck.nextCard());
            p2.add(deck.nextCard());
        }
    }

    public void play() {
        int rounds = 0;

        System.out.println("Starting the game of WAR!");
        System.out.println("-------------------------------------------");

        while (!p1.isEmpty() && !p2.isEmpty()) {
            rounds++;

            PlayingCard c1 = p1.poll();
            PlayingCard c2 = p2.poll();

            System.out.println("Round " + rounds);
            System.out.println("Player 1 plays " + c1.cardName());
            System.out.println("Player 2 plays " + c2.cardName());

            if (c1.playingCardValue() > c2.playingCardValue()) {
                System.out.println("Player 1 wins round.");
                p1.add(c1);
                p1.add(c2);
            } else if (c2.playingCardValue() > c1.playingCardValue()) {
                System.out.println("Player 2 wins round.");
                p2.add(c1);
                p2.add(c2);
            } else {
                System.out.println("WAR!");
                doWar(c1, c2);
            }

            System.out.println("Player 1 cards: " + p1.size());
            System.out.println("Player 2 cards: " + p2.size());
            System.out.println("-------------------------------------------");
        }

        if (p1.isEmpty() && p2.isEmpty()) {
            System.out.println("Tie game!");
        } else if (p1.isEmpty()) {
            System.out.println("Player 2 wins after " + rounds + " rounds.");
        } else {
            System.out.println("Player 1 wins after " + rounds + " rounds.");
        }
    }

    private void doWar(PlayingCard c1, PlayingCard c2) {
        Queue<PlayingCard> warPile = new LinkedList<>();
        warPile.add(c1);
        warPile.add(c2);

        while (true) {

            if (p1.size() < 4 || p2.size() < 4) {
                System.out.println("A player cannot continue the war.");

                if (p1.size() < 4 && p2.size() < 4) {
                    p1.clear();
                    p2.clear();
                } else if (p1.size() < 4) {
                    p1.clear();
                } else {
                    p2.clear();
                }
                return;
            }

            for (int i = 0; i < 3; i++) {
                warPile.add(p1.poll());
                warPile.add(p2.poll());
            }

            PlayingCard w1 = p1.poll();
            PlayingCard w2 = p2.poll();

            System.out.println("Player 1 war card: " + w1.cardName());
            System.out.println("Player 2 war card: " + w2.cardName());

            warPile.add(w1);
            warPile.add(w2);

            if (w1.playingCardValue() > w2.playingCardValue()) {
                System.out.println("Player 1 wins the war!");
                p1.addAll(warPile);
                return;
            } else if (w2.playingCardValue() > w1.playingCardValue()) {
                System.out.println("Player 2 wins the war!");
                p2.addAll(warPile);
                return;
            } else {
                System.out.println("WAR AGAIN!");
            }
        }
    }
}
