package model;

import java.util.Random;

/**
 * 图层类，构造方法中能设置该层的卡牌数量
 * <p>
 * 每层的偏移量在这里随机生成
 * @author 教徒
 */
public class Layer {

    /**
     *   图层偏移量的设置
     */
    private int offsetX;
    private int offsetY;

    /**
     行列数
     */
    private int rowNumber;
    private int columnNumber;

    /**
     * 最大牌数 行*列
     * 当前牌数 牌添加和减少时需要修改该值
     * */
    private int largestNumber;

    private int preNumber;

    /**
     * 当前图层的上一层
     */
    private Layer parent;

    /**
     * 图层主体
     * 每个cell中都有可能装着一个Card
     */
    private Cell[][] cells;

    public Layer(int rowNum,int columnNum)throws Exception{
        this.rowNumber=rowNum;
        this.columnNumber=columnNum;
        this.largestNumber=rowNum*columnNum;

        if(this.largestNumber%3!=0){
            throw new Exception("容量出错！！！");
        }

        this.cells=new Cell[rowNum][columnNum];

        this.preNumber=0;

        this.offsetX=new Random().nextInt(50);
        this.offsetY=new Random().nextInt(50);
    }

    /**
     * 输出该层卡牌数据
     */
    public void showCells(){
        for (int row = 0; row < cells.length; row++) {
            for(int column=0;column<cells[row].length;column++){

                Card card=cells[row][column].getCard();
                if(card!=null){
                    System.out.print(card.getName()+" ");
                }else{
                    System.out.print("NULL ");
                }

            }
            System.out.println();
        }
    }

    /**
     *  检查该层是否有牌
     */
    public boolean isEmpty(){

        Cell[][] cells =this.getCells();

        for(int i = 0; i< this.getRowNumber(); i++){
            for(int j = 0; j< this.getColumnNumber(); j++) {
                if(cells[i][j].isState()){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 输出cell状态用的（是否有牌
     */
    public void state(){

        Cell[][] cells =this.getCells();

        for(int i = 0; i< this.getRowNumber(); i++){
            for(int j = 0; j< this.getColumnNumber(); j++) {
                System.out.print(cells[i][j].isState());
                }
            System.out.println();
            }
    }




    public Layer getParent() {
        return parent;
    }
    public void setParent(Layer parent) {
        this.parent = parent;
    }
    public int getOffsetY() {
        return offsetY;
    }
    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }
    public int getOffsetX() {
        return offsetX;
    }
    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }
    public int getRowNumber() {
        return rowNumber;
    }
    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }
    public int getColumnNumber() {
        return columnNumber;
    }
    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }
    public int getLargestNumber() {
        return largestNumber;
    }
    public void setLargestNumber(int largestNumber) {
        this.largestNumber = largestNumber;
    }
    public int getPreNumber() {
        return preNumber;
    }
    public void setPreNumber(int preNumber) {
        this.preNumber = preNumber;
    }
    public Cell[][] getCells() {
        return cells;
    }
    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }
}
