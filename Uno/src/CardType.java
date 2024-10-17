public class CardType {
     CardNumber number;
     CardColor color;
    public CardType(CardNumber number,CardColor color){
        this.number = number;
        this.color = color;
    }
    public void setColor(CardColor color){
        this.color = color;
    }
    public CardColor getColor(){
        return this.color;
    }
    public void setNumber(CardNumber number){
        this.number = number;
    }
    public CardNumber getNumber(){
        return this.number;
    }

    @Override
    public String toString() {
        return "CardType = " + this.color + "|" + this.number;
    }
}
