package model;

import view.Start;

import javax.swing.*;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 功能: 消除区域，
 * <p>
 * 判定游戏失败的地方
 * @author 教徒
 */
public class EliminateBox {

    private static List<Card> slot = new ArrayList<>();

    final static int SEVEN =7;
    public static Card revokeCard;

    /**
     * 消除区添加卡牌
     * @param card 向消除区添加的卡牌
     */
    public void addToSlot(Card card){
        slot.add(card);
        revokeCard=card;
        //吞掉作用在消除框中卡牌上的鼠标点击事件->消除区域框里图形无法点击
        MouseListener[] mouseListeners = card.getMouseListeners();
        if (mouseListeners!=null){
            for (MouseListener mouseListener : mouseListeners) {
                card.removeMouseListener(mouseListener);
            }
        }
        //牌的重新排序
        slot.sort(Comparator.comparing(Card::getName));
        //获取牌的名称,根据牌的名字进行消除，方法：获取牌的名字及所有同名牌对象，牌名为Map的键，同名牌对象存在list中作为对应值
        Map<String,List<Card>> map=slot.stream().collect(Collectors.groupingBy(Card::getName));
        //获取Map的键值，然后遍历寻找是否有够三个的同名集合，有就用迭代器清除slot中所有的同名对象
        Set<String> key=map.keySet();
        for(String s:key){
            List<Card> cards=map.get(s);
            if(cards.size()==3){
                deleteCardName(s);
                revokeCard=null;
                break;
            }
        }
        //每次加牌后都要重绘以及判断是否游戏失败/成功
        paint();
        //判断游戏是否失败（判断游戏是否成功写在了Map中，在Card的鼠标点击中执行）
        gameOver();
    }

    /**
     * 迭代器删除slot的list中的三个同名对象
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


    /**
     * 撤销操作中EliminateBox中应有的操作
     */
    public void boxRevoke(){
        revokeCard.getParent().remove(revokeCard);
        slot.remove(revokeCard);
        paint();
    }


    private static int remove=1;
    public ArrayList<String> boxRemove(){

        ArrayList<String> three=new ArrayList<>();
        if(remove==1 || revive){
            //remove是是上移，revive是复活
            remove=0;
            revive=false;
        }else{
            JOptionPane.showMessageDialog(null,"你的次数用完了,请充值！");
            return three;
        }
        int max=Math.min(3,slot.size());

        if (max > 0) {
            for(int i=0;i<max;i++){
                three.add(slot.get(0).getName());
                slot.get(0).getParent().remove(slot.get(0));
                slot.remove(slot.get(0));
            }
        paint();
        }
        else{
            JOptionPane.showMessageDialog(null,"没有可以上移的牌");
        }
        return three;
    }


    private static int replay =1;
    public static boolean revive=false;
    /**
     * 判断游戏是否失败
     */
    public void gameOver(){
        if(slot.size()>=SEVEN){
            if(replay ==0){
                JOptionPane.showMessageDialog(null,"    游戏结束,你输了！\n想再次复活请给作者充值 ʘᴗʘ");
                System.exit(0);
            }else{
                replay =0;

                String []options={"是","否","充值"};
                int rs = JOptionPane.showOptionDialog(null,"是否复活？","复活",
                        JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
                if(rs==0){
                    revive=true;

                }else if(rs==1){
                    JOptionPane.showMessageDialog(null,"游戏结束,你输了!");
                    System.exit(0);
                } else {
                    JOptionPane.showMessageDialog(null,"QQ：728683007");
                    System.exit(0);
                }
            }

        }

    }


    /**
     * 每次向消除区添加牌之后都要重绘消除区
     */
    public  void paint(){
        for (int i = 0; i < slot.size(); i++) {
            Card card=slot.get(i);
            //牌在消除框的位置
            int x=i*card.getWidth()+10;
            //消除区域牌的布局
            card.setBounds(x,525,50,50);
        }
    }

    public  List<Card> getSlot() {
        return slot;
    }
    public void setSlot(List<Card> slot) {
        EliminateBox.slot = slot;
    }
}
