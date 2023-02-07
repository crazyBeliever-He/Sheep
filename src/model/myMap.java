package model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 地图类，有多个图层
 * <p>
 * 附带游戏胜利的判断方法和置灰的判断
 * <p>
 * 在创建一个Map对象时，各种卡牌数据就已经生成了
 * @author 教徒
 */
public class myMap {
    private int levels;
    private List<Layer> list=new ArrayList<>();
    public static EliminateBox eliminateBox=new EliminateBox();
    public static Layer remove;
    static {
        try {
            remove = new Layer(1,6);
            Cell [][]temp=remove.getCells();
            for(int i=0;i<temp[0].length;i++){
                temp[0][i]=new Cell();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *  方法compareAll的作用：
     *  判断当前map中所有牌是否需要置灰
     * <p>
     *  判断思路：
     *  对每个非顶层的牌，依次判断上层是否有牌遮住他了
     *  性能非常差，牌越多速度越慢
     *  <p>
     *  调用时间：
     *  1.游戏开始
     *  2.牌被点击后
     */
    public void compareAll(){

        //i=0是最顶层layer，不需要判断
        //遍历该层上面的所有层
        for (int i = 1; i <list.size() ; i++) {
            Layer layer=list.get(i);
            Cell[][] cells=layer.getCells();

            //遍历该层的Cell，判断是否有含牌的cell遮住了正在查找的card
            for (Cell[] value : cells) {
                for (Cell cell : value) {

                    if (cell.isState()) {
                        //该空间有牌，需要判定
                        Card card = cell.getCard();
                        boolean result = compare(card, layer.getParent());

                        //写入该牌是否要置灰
                        card.setGray(result);

                    }
                }
            }

        }
    }

    /**
     *
     * @param card 被检查的牌
     * @param layer 被检查的牌的所在层的上面的某一层
     * @return 是否被该层遮盖
     */
    public  boolean compare(Card card, Layer layer){

        Cell[][] cells=layer.getCells();
        for (Cell[] value : cells) {
            for (Cell cell : value) {

                //如果当前牌单元格为空，不用比较
                if (cell.isState()) {
                    //单元格有牌，需要比较
                    //temp 上级图层中的牌的属性; rect 当前牌属性; result 遮盖判定
                    Rectangle temp = cell.getCard().getBounds();
                    Rectangle rect = card.getBounds();
                    boolean result = rect.intersects(temp);

                    if (result) {
                        //有交集，当前牌被盖住了.return结束整个判定
                        return true;
                    }

                }
            }
        }
        //到这里意味当前牌和该图层无交集，应该去更高层进行比较判断
        if(layer.getParent()!=null){
            return compare(card,layer.getParent());
        }else{
            //如果parent为空，说明完全没有遮盖，该牌显示正常
            return false;
        }
    }

    /**
     * 判断游戏是否赢了
     */
    public void ifWin(){
        if(view.Start.gameMap.isEmpty()){
            JOptionPane.showMessageDialog(null,"游戏结束,你赢了！");
            System.exit(0);
        }
    }

    /**
     * 检查map中是否还有牌
     */
    public boolean isEmpty(){

        for (Layer layer : this.list) {
            if (!layer.isEmpty()) {
                return false;
            }
        }
        return true;
    }



    /**
     * 输出cell状态用的（是否有牌
     */
    public void state(){
        for (Layer layer : this.list) {
            layer.state();
            System.out.println();
        }
    }

    public int getLevels() {
        return levels;
    }

    public void setLevels(int levels) {
        this.levels = levels;
    }

    public List<Layer> getList() {
        return list;
    }

    public void setList(ArrayList<Layer> list) {
        this.list = list;
    }
}
