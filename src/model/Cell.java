package model;

/**
 * 单元格
 * 两种状态，有牌，无牌
 */
public class Cell {
    private boolean state;
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
