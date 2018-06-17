package kr.ac.cnu.web.games.blackjack;

import java.util.Map;

/**
 * Created by rokim on 2018. 5. 27..
 */
public class Evaluator {
    private Map<String, Player> playerMap;
    private Dealer dealer;

    public Evaluator(Map<String, Player> playerMap, Dealer dealer) {
        this.playerMap = playerMap;
        this.dealer = dealer;
    }

    public boolean evaluate() {
        if (playerMap.values().stream().anyMatch(player -> player.isPlaying())) {
            return false;
        }

        int dealerResult = dealer.getHand().getCardSum();

        if (dealerResult > 21) {
            playerMap.forEach((s, player) -> player.win());

            return true;
        }
        /**
         * 2. 내가 25가 나왔는데도 이겼네요? 21이 넘어가면 무조건 져야 해요.
         * 4. Blackjack 이면 1.5배를 돌려받아야 할 것 같아요.
         * */
        playerMap.forEach((s, player) -> {
            int playerResult = player.getHand().getCardSum();
            if (playerResult > 21) {
                player.lost();
                player.stand();
            } else if (playerResult > dealerResult) {
                player.win();
            } else if (playerResult == dealerResult) {
                player.tie();
            } else if (playerResult == 21){
                player.blackjack();
            }
            else {
                player.lost();
            }
        });

        return true;
    }


}
