package tool;


import model.Card;
import model.Cell;
import model.Layer;

/**
 * 创建图层
 * @author 教徒
 */
public class LayerTool {
    public static Layer buildLayer(int rowNum,int colNum){

        Layer layer=null;
        try{
            layer=new Layer(rowNum,colNum);
        }catch(Exception e){
            e.printStackTrace();
        }


        assert layer != null;
        Card[] cards= CardTool.buildCards(layer.getLargestNumber());
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

                Card card1=cards[flag++];

                Cell cell=new Cell();
                cell.setState(true);
                //这个set让单元格对象找到牌
                cell.setCard(card1);

                //这句set能让牌访问他的单元格
                card1.setCell(cell);

                //将卡片装入layer中的cell数组中
                cells[row][column]=cell;
            }
        }
        return layer;
    }
}
