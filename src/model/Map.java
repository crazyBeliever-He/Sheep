package model;

import tool.MapTool;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：一个地图下面有多个图层，层层遮盖
 * @author 教徒
 */
public class Map {
    private int levels;
    private List<Layer> list=new ArrayList<>();

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

    /**
     *  判断当前map中所有牌是否置灰
     *  性能非常差，牌越多速度越慢
     *  调用时间：
     *  1.游戏开始
     *  2.牌被点击后
     */
    public void compareAll(){

        //i=0是最顶层layer，不需要判断
        for (int i = 1; i <list.size() ; i++) {
            Layer layer=list.get(i);
            Cell[][] cells=layer.getCells();

            for (int row = 0; row < cells.length; row++) {
                for(int column=0;column<cells[row].length;column++){

                    Cell cell=cells[row][column];
                    if(cell.isState()){
                        //该空间有牌，需要判定
                        Card card=cell.getCard();
                        boolean result=MapTool.compare(card,layer.getParent());

                        card.setGray(result);
                    }
                }

            }

        }
    }
}
