import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<CardType> list = new ArrayList<>();
    public void addCard2() {

        list.add(new CardType(CardNumber.PLUS_TWO,CardColor.BLUE));
        list.add(new CardType(CardNumber.PLUS_FOUR,CardColor.SUPER));
        list.add(new CardType(CardNumber.PLUS_TWO,CardColor.GREEN));

        list.add(new CardType(CardNumber.PLUS_TWO,CardColor.YELLOW));

        list.add(new CardType(CardNumber.PLUS_TWO,CardColor.RED));
        list.add(new CardType(CardNumber.TWO,CardColor.RED));
    }
    public void addCard(){
        for(CardColor c1 : CardColor.values()){
            if(c1 == CardColor.SUPER) continue;
            for(CardNumber c2 : CardNumber.values()){
                if(c2!=CardNumber.PLUS_FOUR &&  c2!=CardNumber.WILD && c2 != CardNumber.NONE) {
                    list.add(new CardType(c2,c1));
                }
            }
        }
        for(int i=0;i<4;i++){
            list.add(new CardType(CardNumber.PLUS_FOUR,CardColor.SUPER));
            list.add(new CardType(CardNumber.WILD,CardColor.SUPER));
        }
        Collections.shuffle(list);
        System.out.println(list.size());
//        for(CardType card : list){
//            System.out.println(card.getColor() +" "+ card.getNumber());
//        }
    }

    int i = 0;
    public CardType getCard(){
        if(i==list.size()){
            Collections.shuffle(list);
            i = 0;
        }
        return list.get(i++);
    }
    public CardType getNumberCard(){

        for(int i=0;i<list.size();i++){
            CardType card = list.get(i);
            if(card.getColor()!=CardColor.SUPER && card.getNumber()!=CardNumber.DUMMY && card.getNumber()!=CardNumber.REVERSE && card.getNumber()!=CardNumber.SKIP && card.getNumber()!=CardNumber.PLUS_TWO){
                CardType temp = list.get(i);
                list.set(i,list.get(0));
                list.set(0,temp);
            }
        }
        return this.getCard();
    }
}
