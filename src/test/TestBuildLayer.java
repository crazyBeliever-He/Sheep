package test;

import model.*;

import java.util.Random;

/**
 * 测试图形数据构建
 * @author 教徒
 */
public class TestBuildLayer {
    static Random random=new Random();
    public static String[] cardNames={
            "carrot","corn","grass"
    };
    /**
     *  getCardName 每次调用随机获取牌的名字
     */
    public static String getCardName(){

        int rIndex=random.nextInt(cardNames.length);
        return cardNames[rIndex];
    }


    public static void main(String[] args) {

        Layer layer=null;
        try{
            layer=new Layer(3,3);
        }catch(Exception e){
            e.printStackTrace();
        }


        assert layer != null;

        Card[] cards=new Card[layer.getLargestNumber()];

        /*
          游戏卡牌容量为三的倍数
         */
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
        for (int i = 0; i < cards.length; i++) {
            System.out.print(cards[i].getName()+" ");
        }

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
        System.out.println();
        for (int i = 0; i < cards.length; i++) {
            System.out.print(cards[i].getName()+" ");
        }

        /*
            向图层里面的最小单元中装填卡牌
            # # #
            # # #
            # # #
         */
        Cell[][] cells=layer.getCells();
        int flag=0;
        for (int row = 0; row < cells.length; row++) {
            for(int column=0;column<cells[row].length;column++){

                Cell cell=new Cell();
                cell.setState(true);
                cell.setCard(cards[flag++]);

                //将卡片装入layer中的cell数组中
                cells[row][column]=cell;
            }
        }
        System.out.println();
        for (int row = 0; row < cells.length; row++) {
            for(int column=0;column<cells[row].length;column++){

               Card card=layer.getCells()[row][column].getCard();
                System.out.print(card.getName()+" ");
            }
            System.out.println();
        }

    }
}
