import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    Scanner scan = new Scanner(System.in);
    CardType previousCard;
    Deck deck = new Deck();
    int addFour;
    int addTwo;
    int k ;
    int n;
    int direction = 1;
    Node CurrentPlayer;

    public static void main(String[] args) {
        new Main().start();
    }

    public void start() {

        System.out.println("Enter the Number of Players within the range of 2 to 8 : ");
        n = scan.nextInt();
        k = n;
        deck.addCard();
        previousCard = deck.getNumberCard();
        this.initiateGame(n);
        while(k!=1){
            gameLogic();
        }

    }

    public void initiateGame(int n) {
        Node prevNode = null;
        Node nextNode = null;
        Node currentNode = new Node(prevNode,nextNode);
        Node firstNode = currentNode;
        CurrentPlayer = firstNode;
        for(int i=0;i<n;i++){
            currentNode.setIndex(i);
            for(int j=0;j<7;j++){
                CardType card = deck.getCard();
                currentNode.add(card);
            }
            if(i==n-1){
                currentNode.next = firstNode;
            }else {
                prevNode = currentNode;
                nextNode = new Node(prevNode, null);
                currentNode.next = nextNode;
                currentNode = nextNode;
            }
        }
    }
    public CardType getCardFromPlayer(){
        List<CardType> list = new ArrayList<>(CurrentPlayer.map1.keySet());
        showCurrentPlayerCards(list);
        int chooseIndex = scan.nextInt();
        while(chooseIndex<-1 || chooseIndex>=list.size()){
            System.out.println("Invalid Index Choosen :" + chooseIndex);
            chooseIndex = scan.nextInt();
        }
        if(chooseIndex == -1) {
            if (addTwo > 0) {
                addCardsTwo();
                return null;
            } else if (addFour > 0) {
                addCardsFour();
                return null;
            }
            CardType ct = deck.getCard();
            CurrentPlayer.map1.put(ct,CurrentPlayer.map1.getOrDefault(ct,0)+1);
            selectCard();
            return null;
        }
        return list.get(chooseIndex);
    }

    public void showCurrentPlayerCards(List<CardType> list) {
        System.out.println("Player " + CurrentPlayer.index +" turn ");
        for (int i = 0;i < list.size(); ++i) {
            System.out.println(i +"  >>  " + list.get(i) + "<> COUNT : " + CurrentPlayer.map1.get(list.get(i)));
        }
    }

    public void selectCard() {
        System.out.println("Choose card:");
        List<CardType> list = new ArrayList<>(CurrentPlayer.map1.keySet());
        showCurrentPlayerCards(list);
        int chooseIndex = scan.nextInt();
        if(chooseIndex>-1 && chooseIndex< list.size()){
            CardType choosenCard = list.get(chooseIndex);
            if(SpecialCase(choosenCard)) return;
            if(isMathch(choosenCard)) {
                if(choosenCard.getNumber()==CardNumber.PLUS_TWO){
                    addTwo += 2;
                }
                removeCard(choosenCard);
                previousCard = choosenCard;
                if(choosenCard.getNumber()==CardNumber.DUMMY){
                    getNumber();
                }
                if(CurrentPlayer.map1.isEmpty()) {
                    CurrentPlayer.prev.next = CurrentPlayer.next;
                    CurrentPlayer.next.prev = CurrentPlayer.prev;
                    k--;
                }
                if(previousCard.getNumber()==CardNumber.SKIP){
                    goToNextPlayer();
                } else if (previousCard.getNumber()==CardNumber.REVERSE) {
                    if(k==2) return;
                    direction *= -1;
                }
            }
        }
        goToNextPlayer();
    }

    public boolean isMathch(CardType playerChoosenCard){
        return previousCard.getColor() == playerChoosenCard.getColor() || previousCard.getNumber() == playerChoosenCard.getNumber();
    }
    public void gameLogic(){
        System.out.println("Previous card is " + previousCard);
        CardType choosenCard = getCardFromPlayer();
        if(SpecialCase(choosenCard)){
            return;
        }
        boolean match = isMathch(choosenCard);
        if(match){
            if(choosenCard.getNumber()==CardNumber.PLUS_TWO){
                addTwo += 2;
            }
            if(choosenCard.getNumber()==CardNumber.PLUS_FOUR){
                addFour += 4;
            }
            removeCard(choosenCard);
            previousCard = choosenCard;
            if(choosenCard.getNumber()==CardNumber.DUMMY){
                getNumber();
            }
            if(CurrentPlayer.map1.isEmpty()){
                CurrentPlayer.prev.next = CurrentPlayer.next;
                CurrentPlayer.next.prev = CurrentPlayer.prev;
                k--;
            }
            if(previousCard.getNumber()==CardNumber.SKIP){
                goToNextPlayer();
            } else if (previousCard.getNumber()==CardNumber.REVERSE) {
                if(k==2) return;
                direction *= -1;
            }
            goToNextPlayer();

        }else{
            System.out.println("Card does not match ");
            gameLogic();
        }
    }
    public void goToNextPlayer(){
        CurrentPlayer = CurrentPlayer.next;
    }
    public CardType getSampleCard(CardColor cc) {
        return new CardType(CardNumber.NONE,cc);
    }
    public void changeColor(){
        char input = 'U';
        while (input != 'R' && input != 'G' && input != 'B' && input != 'Y') {
            System.out.println("Choose one of the color : R for Red, B for Blue, G for Green, Y for Yellow");
            input = scan.next().charAt(0);
        }
        previousCard = switch (input) {
            case 'R' -> getSampleCard(CardColor.RED);
            case 'G' -> getSampleCard(CardColor.GREEN);
            case 'B' -> getSampleCard(CardColor.BLUE);
            default  -> getSampleCard(CardColor.YELLOW);
        };
    }
    public void addCardsFour(){
        while(addFour-->0){
            CardType card = deck.getCard();
            CurrentPlayer.map1.put(card,CurrentPlayer.map1.getOrDefault(card,0)+1);
        }
        addFour = 0;
    }
    public void addCardsTwo(){
        while(addTwo-->0){
            CardType card = deck.getCard();
            CurrentPlayer.map1.put(card,CurrentPlayer.map1.getOrDefault(card,0)+1);
        }
        addTwo = 0;
    }
    public  void removeCard(CardType card){
        int freq = CurrentPlayer.map1.get(card);
        if(freq==1){
            CurrentPlayer.map1.remove(card);
        }else{
            CurrentPlayer.map1.put(card,--freq);
        }
    }
    public void getNumber(){
        PrintStream p = System.out;
        char input = 'U';
        while (input < '0' || input > '9') {
            p.println("Enter the Number 0 to 9");
            input = scan.next().charAt(0);
        }
        previousCard.setNumber(switch (input) {
            case '0' -> CardNumber.ZERO;
            case '1' -> CardNumber.ONE;
            case '2' -> CardNumber.TWO;
            case '3' -> CardNumber.THREE;
            case '4' -> CardNumber.FOUR;
            case '5' -> CardNumber.FIVE;
            case '6' -> CardNumber.SIX;
            case '7' -> CardNumber.SEVEN;
            case '8' -> CardNumber.EIGHT;
            default -> CardNumber.NINE;
        });
    }
    public boolean SpecialCase(CardType choosenCard){
        if(choosenCard == null) {
            return true;
        }
        if(choosenCard.getNumber()!=CardNumber.PLUS_TWO && addTwo>0){
            addCardsTwo();
        }
        if(choosenCard.getNumber() == CardNumber.PLUS_FOUR){
            addFour += 4;
            changeColor();
            removeCard(choosenCard);
            goToNextPlayer();
            return true;
        }
        if(addFour>0){
            addCardsFour();
        }
        if(choosenCard.getNumber()==CardNumber.WILD) {
            changeColor();
            removeCard(choosenCard);
            goToNextPlayer();
            return true;
        }
        return false;
    }
}