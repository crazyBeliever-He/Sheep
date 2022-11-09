package model;

import javax.swing.*;
import java.util.*;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 功能: 消除区域
 * @author 教徒
 */
public class EliminateBox {

    /**
     * 存放被点击的牌的数据
     */
    private static List<Card> slot = new ArrayList<>();

    /**
     * 迭代器清空集合
     */
    private void deleteCardName(String name){
        Iterator<Card> iterator=slot.iterator();
        while(iterator.hasNext()){
            Card next=iterator.next();
            if(next.getName().equals(name)){
                next.getParent().remove(next);
                iterator.remove();
            }
        }
    }

    public void addSlot(Card card){
        slot.add(card);

        //消除框中牌的排序
        slot.sort(Comparator.comparing(Card::getName));

        //获取牌的名称,根据牌的名字进行消除
        //获取每个牌名字及对应的cards。放在Map中
        Map<String,List<Card>> map=slot.stream().collect(Collectors.groupingBy(Card::getName));


        Set<String> key=map.keySet();
        //获取Map的键值，然后
        //遍历寻找是否有够三个的同名集合，
        // 有就删除，删除操作在slot上进行，用迭代器
        for(String s:key){
            List<Card> cards=map.get(s);
            if(cards.size()==3){
                //用迭代器清空集合
                deleteCardName(s);
                break;
            }
        }
        paint();
        gameOver(card);

    }

    /**
     * 将牌绘制到消除框
     */
    private void paint(){
        for (int i = 0; i < slot.size(); i++) {
            Card card=slot.get(i);

            //牌在消除框的位置
            int x=i*card.getWidth()+10;

            //消除区域牌的布局
            card.setBounds(x,600,50,50);


        }
    }

    /**
     * 判断游戏是否失败
     */
    void gameOver(Card card){
        if(slot.size()>=7){
            JOptionPane.showMessageDialog(card,"游戏结束");
            System.exit(0);
        }
    }
}
