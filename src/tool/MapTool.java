package tool;

import model.*;

import java.awt.*;


/**
 *
 * @author 教徒
 */
public class MapTool {
    public static Map buildMap(int levels){

        Map map=new Map();
        map.setLevels(levels);

        /*
          在绝对布局中，同样位置，先加入的组件展示在最上层，后加入的在下面
          和现实当中的直觉相反（直觉：后加入的应该在上层）
         */
        Layer layer1 = LayerTool.buildLayer(3,3);
        Layer layer2 = LayerTool.buildLayer(3,3);
        //Layer layer3 = LayerTool.buildLayer(3,3);

        //构建图层的链式关系,第一层的parent默认为空，等价于，parent为空，说明已经到顶层，循环/递归结束
      //  layer3.setParent(layer2);
        layer2.setParent(layer1);
        layer1.setParent(null);

        layer1.setOffsetX(40);
        layer2.setOffsetX(20);
       // layer3.setOffsetX(10);

        map.getList().add(layer1);
        map.getList().add(layer2);
       // map.getList().add(layer3);

        return map;
    }
    /**
     *  判断当前牌是否和某一层的牌是否有交集
     *  true 就是有交集，当前牌为灰色
     *  false 没有交集，显示彩色
     */
    public static boolean compare(Card card, Layer layer){

        Cell[][] cells=layer.getCells();
        for (int row = 0; row < cells.length; row++) {
            for(int column=0;column<cells[row].length;column++){

                //如果当前牌单元格为空，不用比较
                Cell cell=cells[row][column];
                if(cell.isState()){
                    //单元格有牌，需要比较
                    /*
                        temp 上级图层中的牌的属性; rect 当前牌属性; result 遮盖判定
                     */
                    Rectangle temp=cell.getCard().getBounds();
                    Rectangle rect=card.getBounds();
                    boolean result=rect.intersects(temp);

                    if(result){
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
}
