import java.util.HashMap;
import java.util.Map;

public class Node {
    Node prev;
    Node next;
    Map<CardType,Integer> map1 = new HashMap<>();
    int index;
    public Node(Node prev,Node next){
        this.prev = prev;
        this.next = next;
    }
    public void setIndex(int index){
        this.index = index;
    }
    public void add(CardType card){
        if(map1.containsKey(card)){
            map1.put(card,map1.get(card)+1);
        }else{
            map1.put(card,1);
        }
    }
}

