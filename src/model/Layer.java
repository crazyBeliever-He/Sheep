package model;

/**
 * 图层类
 * 二维表格
 */
public class Layer {


    /**
    行列
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
     * 图层主体
     */
    private Cell[][] cells=null;

    public Layer(int rowNum,int columnNum){
        this.rowNumber=rowNum;
        this.columnNumber=columnNum;
        this.largestNumber=rowNum*columnNum;
        this.cells=new Cell[rowNum][columnNum];

        this.preNumber=0;
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
