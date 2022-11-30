package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import model.*;
import tool.*;

import javax.swing.*;
import java.awt.*;
import java.util.Random;


/**
 * @author 教徒
 * 游戏的启动入口
 * 游戏窗口初始化设置
 */
public class Start extends JFrame {
    /**
     * 每局游戏的入口
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        new Start();
    }

    /**
     * 这里更改地图数据（难度）
     * <p>
     * 生成地图数据
     */
    public static Map map= MapTool.buildMap(3);

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
    }

    /**
     * 绘制地图
     */
    public void  colourMap(){
        List<Layer> list=map.getList();
        for (int i = 0; i < list.size(); i++) {
            colourLayer(list.get(i));
        }
        //置灰判定
        map.compareAll();
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
        JButton remove=new JButton("移出");
        disorganize.setBounds(60,600,70,50);
        revoke.setBounds(190,600,70,50);
        remove.setBounds(320,600,70,50);
        disorganize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disorganize();
                //重绘
                recolour();
            }
        });
        revoke.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                revoke();
                //重绘
                recolour();
            }
        });
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove();
                //重绘
                recolour();
            }
        });


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
        list=map.getList();
        LinkedList<Card> allCard=new LinkedList<>();
        //获取所有卡牌
        for (Layer layer : list) {
            Cell[][] cell = layer.getCells();
            for(int i=0;i<cell.length;i++){
                for(int j=0;j<cell[i].length;j++){
                    if(cell[i][j].isState()){
                        cell[i][j].getCard().setGray(false);
                    }
                    allCard.add(cell[i][j].getCard());
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
    }

    public static Cell revokeCell;
    public static Card revokeCard;
    /**
     * 功能——撤销
     * <p>
     *     撤销的实现：删除EliminateBox中的卡片，并在原位置添加一个新的卡片
     * <p>
     *     目前只实现了连续一次的撤销，连续多次的撤销有需要的话可以实现，把存储记录做多一点就好了
     */
    private void revoke(){
        List<Card> slot=EliminateBox.getSlot();
        if(slot.isEmpty()){
            JOptionPane.showMessageDialog(null,"没有可以撤销的卡牌");
            return;
        }
        EliminateBox.boxRevoke();
        revokeCell.setState(true);
        revokeCell.setCard(revokeCard);
        revokeCard.setCell(revokeCell);
        recolour();
    }

    /**
     * 功能——移出
     * <p>
     *    可以看作撤销的高级版
     */
    public void remove(){
        /*
            对消除框做处理
            三张牌放哪？
            map上是layer，layer中是二维数组，可以再构建两层layer，分别是remove和revive层？都是1×3的cell
            compare时，单独对这两层compare？
         */
    }

    /**
     * 每隔40ms重绘窗口
     */
    private void autoRefresh(){
        Start start=this;
        //noinspection AlibabaAvoidManuallyCreateThread
        new Thread(new Runnable(){
            @Override
            public void run(){
                while(true){
                    start.repaint();
                    try{
                        Thread.sleep(40);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


}