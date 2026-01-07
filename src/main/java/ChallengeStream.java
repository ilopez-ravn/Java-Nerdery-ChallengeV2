/* (C)2024 */
import java.util.*;
import java.util.stream.Collectors;

import mocks.CallCostObject;
import mocks.CallSummary;
import mocks.CardWinner;
import mocks.TotalSummary;

import static java.util.stream.Collectors.*;

public class ChallengeStream {

    /**
     * One stack containing five numbered cards from 0-9 are given to both players. Calculate which hand has winning number.
     * The winning number is calculated by which hard produces the highest two-digit number.
     *
     * calculateWinningHand([2, 5, 2, 6, 9], [3, 7, 3, 1, 2]) ➞ true
     *  P1 can make the number 96
     *  P2 can make the number 73
     *  P1 win the round since 96 > 73
     *
     * The function must return which player hand is the winner and the two-digit number produced. The solution must contain streams.
     *
     * @param player1  hand, player2 hand
     */
    public CardWinner calculateWinningHand(List<Integer> player1, List<Integer> player2) {
        String hand1 = player1.stream()
                .sorted((a, b) -> b.compareTo(a) )
                .limit(2)
                .map(Object::toString)
                .collect(Collectors.joining(""));

        String hand2 = player2.stream()
                .sorted((a, b) -> b.compareTo(a) )
                .limit(2)
                .map(Object::toString)
                .collect(Collectors.joining(""));

        CardWinner winner;
        int hand1Int = Integer.parseInt(hand1),
                hand2Int = Integer.parseInt(hand2);
        if (hand1Int > hand2Int)
            winner = new CardWinner("P1", hand1Int);
        else if (hand2Int > hand1Int)
            winner = new CardWinner("P2", hand2Int);
        else
            winner = new CardWinner("TIE", hand1Int);

        return winner;
    }

    /**
     * Design a solution to calculate what to pay for a set of phone calls. The function must receive an
     * array of objects that will contain the identifier, type and duration attributes. For the type attribute,
     * the only valid values are: National, International and Local
     *
     * The criteria for calculating the cost of each call is as follows:
     *
     * International: first 3 minutes $ 7.56 -> $ 3.03 for each additional minute
     * National: first 3 minutes $ 1.20 -> $ 0.48 per additional minute
     * Local: $ 0.2 per minute.
     *
     * The function must return the total calls, the details of each call (the detail received + the cost of the call)
     * and the total to pay taking into account all calls. The solution must be done only using streams.
     *
     * @param {Call[]} calls - Call's information to be processed
     *
     * @returns {CallsResponse}  - Processed information
     */
    public TotalSummary calculateCost(List<CallCostObject> costObjectList) {
         Integer totalCalls =
                costObjectList.stream()
                        .collect(
                                groupingBy((callCostObject -> {
                                    String type = callCostObject.getType();
                                    String identifier = callCostObject.getIdentifier().split("-")[0];
                                    return type+"-"+identifier;
                                }))
                        ).size();

        List<CallSummary> callSummary = costObjectList.stream()
               .map(call -> {
                    int duration = call.getDuration();
                    double _totalCost = 0.0;
                    String type = call.getType();
                    if (type.equals("Local"))
                        _totalCost =  duration * 0.2;
                    else if (type.equals("National")) {
                        if(duration >= 3)
                            _totalCost =  3*1.2+(duration-3)*0.48;
                        else
                            _totalCost =  duration*1.2;
                    } else if (type.equals("International")) { // Intern
                        if(duration >= 3)
                            _totalCost =  3*7.56+(duration-3)*3.03;
                        else
                            _totalCost =  duration*7.56;
                    }


                    return new CallSummary(call, _totalCost);

                }).toList();

        Double totalCost = callSummary.stream()
                .map(CallSummary::getTotalCost)
                .reduce(0.0, Double::sum);

        return new TotalSummary(callSummary, totalCalls, totalCost);
    }
}
