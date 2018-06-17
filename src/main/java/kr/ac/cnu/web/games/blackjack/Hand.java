package kr.ac.cnu.web.games.blackjack;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rokim on 2018. 5. 26..
 */
public class Hand {
    private Deck deck;
    @Getter
    private List<Card> cardList = new ArrayList<>();

    public Hand(Deck deck) {
        this.deck = deck;
    }

    public Card drawCard() {
        Card card = deck.drawCard();
        cardList.add(card);
        return card;
    }
    /**Jack, Queen, King, Ace 의 계산*/
    public int getCardSum() {
        int sum = 0;
        for(int i=0; i<cardList.size();i++){
            if(cardList.get(i).getRank()>10){
                sum=sum+10;
            }else if(cardList.get(i).getRank()==1){
                if (sum > 10) {
                    sum +=1;
                } else {
                    sum += 11;
                } 
            }else {
                sum=sum+cardList.get(i).getRank();
           }
        }
        return sum;
    }

    public void reset() {
        cardList.clear();
    }
}
