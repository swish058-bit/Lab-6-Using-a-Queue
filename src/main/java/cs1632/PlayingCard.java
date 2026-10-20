package cs1632;

public class PlayingCard {
    public final static String [] CardValues = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    public final static String [] SuitValues = { "D", "C", "H", "S" };

    private int cardValue;
    private int suitValue;

    private PlayingCard() {}

    public PlayingCard(int cv, int sv) {
        cardValue = cv;
        suitValue = sv;
    }

    public String cardName() {
        return CardValues[cardValue] + " " + SuitValues[suitValue];
    }

    public int playingCardValue() {
        return cardValue;
    }
}
