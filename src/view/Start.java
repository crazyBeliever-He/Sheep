package view;

import model.*;
import tool.MapTool;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


/**
 * @author 教徒
 * 游戏的启动入口
 * 游戏窗口初始化设置
 */
public class Start extends JFrame {
    /**
     * 每局游戏的入口
     */
    public static void main(String[] args) {
        new Start();
    }

    /**
     * 这里更改地图数据（难度）
     * <p>
     * 生成地图数据
     */
    public static Map gameMap = MapTool.buildMap(3);


    /**
     * 游戏诞生的地方
     */
    public Start() throws HeadlessException {
        //1.游戏界面
        creat();
        //2.自动刷新
        autoRefresh();
    }
    /**
     * 创建简单窗口
     */
    public void creat(){

        //title
        this.setTitle("羊了个羊diy");
        //size
        this.setSize(450,800);
        //关闭窗口时动作
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置 绝对布局
        this.setLayout(null);
        this.setBounds(0,0,450,800);
        //居中
        this.setLocationRelativeTo(null);
        //可视
        this.setVisible(true);
        //加载道具
        setTool();
        //绘制游戏界面,游戏数据在Map中，这里只是绘制
        colourMap();
        //绘制背景和消除框，后加载的图片显示在下面
        setBackground();

        this.setVisible(true);
    }


    /**
     * 绘制地图
     */
    public void  colourMap(){
        List<Layer> list= gameMap.getList();
        for (Layer layer : list) {
            colourLayer(layer);
        }
       colourRemove(Map.remove);
        //置灰判定
        gameMap.compareAll();
    }
    /**
     * 绘制地图需要的方法：绘制图层
     * @param layer 需要绘制的图层
     */
    private void colourLayer(Layer layer){
        Cell[][] cells=layer.getCells();
        for (int row = 0; row < cells.length; row++) {
            for(int column=0;column<cells[row].length;column++){

                Card card=cells[row][column].getCard();
                if(card!=null){
                    //50是该层牌与牌之间的距离，offsetX是整层的偏移量
                    int x=column*50+layer.getOffsetX();
                    int y=row*50+layer.getOffsetY();

                    card.setBounds(x,y,50,50);

                    //将牌放入窗口中
                    this.add(card);
                }

            }
        }
    }
    /**
     * @param layer 道具layer
     */
    private void colourRemove(Layer layer){
        Cell[][] cells=layer.getCells();
        for (int column = 0; column < cells[0].length; column++) {
            Card card = cells[0][column].getCard();
            if(card!=null){
                int x=column*50;
                int y=440;
                card.setBounds(x,y,50,50);
                this.add(card);
            }
        }
    }
    /**
     * 重新绘制游戏界面
     */
    public void recolour(){
        colourMap();
        reSetBackground();
    }


    private static final Card BACKGROUND =new Card("background");
    private static final Card ELIMINATE_BOX =new Card("eliminateBox");
    /**
     * 加载背景和消除框
     */
    private void setBackground(){
        BACKGROUND.setBounds(0,0,450,800);
        ELIMINATE_BOX.setBounds(5,500,450,100);
        this.getContentPane().add(ELIMINATE_BOX);
        this.getContentPane().add(BACKGROUND);
    }
    /**
     * 重置背景
     */
    private void reSetBackground(){
        this.remove(BACKGROUND);
        this.remove(ELIMINATE_BOX);
        setBackground();
    }



    /**
     * 加载道具
     */
    public void setTool(){
        JButton disorganize=new JButton("打乱");
        JButton revoke=new JButton("撤销");
        JButton remove=new JButton("上移");

        disorganize.setBounds(60,600,70,50);
        revoke.setBounds(190,600,70,50);
        remove.setBounds(320,600,70,50);

        disorganize.addActionListener(e -> disorganize());
        revoke.addActionListener(e -> revoke());
        remove.addActionListener(e -> remove());

        this.getContentPane().add(disorganize);
        this.getContentPane().add(revoke);
        this.getContentPane().add(remove);
    }

    /**
     * 功能——打乱
     * <p>
     *     操作：将cell里面的card属性打乱，然后重新绘制游戏界面，还要更改card对应的cell
     */
    private void disorganize(){
        List<Layer> list;
        list= gameMap.getList();
        LinkedList<Card> allCard=new LinkedList<>();
        //获取所有卡牌
        for (Layer layer : list) {
            Cell[][] cell = layer.getCells();
            for (Cell[] cells : cell) {
                for (Cell value : cells) {
                    if (value.isState()) {
                        value.getCard().setGray(false);
                    }
                    allCard.add(value.getCard());
                }
            }
        }
        Random random=new Random();
        //打乱卡牌
        for (int i = 0; i < allCard.size(); i++) {
            //获取当前位置卡牌
            Card card1=allCard.get(i);
            //随机挑选位置进行交换
            int rIndex=random.nextInt(allCard.size());
            Card card2=allCard.get(rIndex);
            //交换
            allCard.set(i,card2);
            allCard.set(rIndex,card1);
        }
        int flag=0;
        //重置卡牌
        for (Layer layer : list) {
            Cell[][] cell = layer.getCells();
            for (Cell[] cells : cell) {
                for (Cell value : cells) {
                    if (allCard.get(flag) == null) {
                        value.setState(false);
                    } else {
                        allCard.get(flag).setCell(value);
                        value.setState(true);
                    }
                    value.setCard(allCard.get(flag));
                    flag++;
                }
            }
        }
        //重绘
        recolour();
    }


    public static Cell revokeCell;
    public static Card revokeCard;
    /**
     * 功能——撤销
     * <p>
     *     撤销的实现：彻底删除EliminateBox中的卡片，并在原位置添加一个新的卡片
     * <p>
     *     目前只实现了连续一次的撤销，连续多次的撤销有需要的话可以实现，把存储记录做多一点就好了
     */
    private void revoke(){
        List<Card> slot=Map.eliminateBox.getSlot();
        if(slot.isEmpty()||EliminateBox.revokeCard==null){
            JOptionPane.showMessageDialog(null,"没有可以撤销的卡牌");
            return;
        }
        Map.eliminateBox.boxRevoke();
        revokeCell.setState(true);
        revokeCell.setCard(revokeCard);
        revokeCard.setCell(revokeCell);
        //重绘
        recolour();
    }


    /**
     * 功能——上移/复活
     * <p>
     * 可以看作撤销的高级版
     * <p>
     *     复活也是用的这个，但是复活的实现过程必须优化（目前是通过一个线程不断检查实现的）
     */
    public void remove(){
        //专门构建一层Layer供上移和撤销使用
        //这里有bug，但是只要上移和复活道具只能使用一次就不会出现bug
        Layer layer= Map.remove;
        EliminateBox eliminateBox=Map.eliminateBox;

        List<String> temp=eliminateBox.boxRemove();
        Cell [][] special=layer.getCells();
        int flag=0;

        for(int i=0;i<temp.size();i++){
            while(special[0][i+flag].isState()){
                flag++;
            }
            Card card=new Card(temp.get(i));
            special[0][i+flag].setCard(card);
            card.setCell(special[0][i+flag]);
            special[0][i+flag].setState(true);

        }
        //重绘
        recolour();
    }


    /**
     * 每隔40ms重绘窗口
     */
    private void autoRefresh(){
        Start start=this;
        //noinspection AlibabaAvoidManuallyCreateThread
        new Thread(() -> {
            while(true){
                start.repaint();
                if(EliminateBox.revive){
                    remove();
                }
                try{
                    Thread.sleep(40);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}