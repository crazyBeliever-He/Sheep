package model;

/**
 * 单元格
 * state 两种状态，有牌true，无牌false
 * @author 教徒
 */
public class Cell {
    private boolean state=false;
    private Card card;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
