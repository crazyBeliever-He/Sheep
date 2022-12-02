package tool;

import model.Card;

import java.util.Random;

/**
 * @author 教徒
 * 唯一功能：创建随机的card数组
 */
public class CardTool {
    static Random random=new Random();
    public static String[] cardNames={
           "bottle","brush","bucket","cabbage","carrot","corn","fire",
            "fork","gloves","grass","meat","scissors","straw","wool",
    };
    /**
     *  getCardName 每次调用随机获取牌的名字
     */
    public static String getCardName(){

        int rIndex=random.nextInt(cardNames.length);
        return cardNames[rIndex];
    }

    /**
     * 创建随机牌的数组
     */
    public static Card[] buildCards(int largestNumber){

        Card[] cards=new Card[largestNumber];

        //游戏卡牌容量为三的倍数
        final int loop=3;
        for (int i = 0; i < cards.length; i+=loop) {

            String rCardName=getCardName();

            Card card1=new Card(rCardName);
            Card card2=new Card(rCardName);
            Card card3=new Card(rCardName);
            cards[i]=card1;
            cards[i+1]=card2;
            cards[i+2]=card3;
        }
        //交换牌的位置，制造随机
        for (int i = 0; i < cards.length; i++) {
            //获取当前位置卡牌
            Card card1=cards[i];

            //随机挑选位置进行交换
            int rIndex=random.nextInt(cards.length);
            Card card2=cards[rIndex];

            //交换
            cards[i]=card2;
            cards[rIndex]= card1;
        }

        return cards;
    }
}
