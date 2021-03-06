package kr.ac.cnu.web.games.blackjack;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by rokim on 2018. 5. 26..
 */
public class GameRoom {
    @Getter
    private final String roomId;
    @Getter
    private final Dealer dealer;
    @Getter
    private final Map<String, Player> playerList;
    @Getter
    private final Deck deck;
    @Getter
    private boolean isFinished;
    private final Evaluator evaluator;

    public GameRoom(Deck deck) {
        this.roomId = UUID.randomUUID().toString();
        this.deck = deck;
        this.dealer = new Dealer(new Hand(deck));
        this.playerList = new HashMap<>();
        this.evaluator = new Evaluator(playerList, dealer);
        this.isFinished = true;
    }

    public void addPlayer(String playerName, long seedMoney) {
        Player player = new Player(seedMoney, new Hand(deck));

        playerList.put(playerName, player);
    }

    public void removePlayer(String playerName) {
        playerList.remove(playerName);
    }

    public void reset() {
        dealer.reset();
        playerList.forEach((s, player) -> player.reset());
    }

    public void bet(String name, long bet) {
        Player player = playerList.get(name);

        player.placeBet(bet);
    }

    public void deal() {
        this.isFinished = false;
        dealer.deal();
        playerList.forEach((s, player) -> player.deal());
    }

     /**
     * double down
     * */
    public void double_down() {
        this.isFinished = false;
        playerList.forEach((s, player) -> player.double_down());
        this.isFinished = true;
    }
    
   /**
 * 1. hit 후 21이 넘어가면 바로 Game이 끝나야 합니다.
      (추가)blackjack도 처리해줌
 */
public void hit(String name) {
    Player player = playerList.get(name);
    player.hitCard();
    if(player.getHand().getCardSum() >= 21) {
        evaluator.evaluate();
        if(player.getHand().getCardSum() == 21)
            player.blackjack();
        this.isFinished = true;
    }
}

    public void stand(String name) {
        Player player = playerList.get(name);

        player.stand();
    }

    public void playDealer() {
        dealer.play();
        evaluator.evaluate();
        this.isFinished = true;
    }

}
