package view;

import java.util.List;
import model.*;
import tool.*;

import javax.swing.*;
import java.awt.*;


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
     */
    public static Map map= MapTool.buildMap(3);
    public Start() throws HeadlessException, InterruptedException {
        //1.初始化窗口的基本信息
        creat();

        //2.绘制卡片
        colourMap();

        //3.自动刷新
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
    }

    /**
     * 绘制地图
     */
    public void colourMap(){
        List<Layer> list=map.getList();
        for (int i = 0; i < list.size(); i++) {
            colourLayer(list.get(i));
        }
        //置灰判定
        map.compareAll();

        //绘制背景和消除框，后加载的图片显示在下面
        Card background=new Card("background");
        Card eliminateBox=new Card("eliminateBox");
        background.setBounds(0,0,450,800);
        eliminateBox.setBounds(0,575,450,800);
        this.getContentPane().add(eliminateBox);
        this.getContentPane().add(background);
    }

    /**
     * 绘制地图需要的方法：绘制图层
     * @param layer 需要绘制的图层
     */
    private void colourLayer(Layer layer){
        //将cell添加到窗口中
        Cell[][] cells=layer.getCells();
        for (int row = 0; row < cells.length; row++) {
            for(int column=0;column<cells[row].length;column++){

                Card card=cells[row][column].getCard();

                //50是该层牌与牌之间的距离，offsetX是整层的偏移量
                int x=column*50+layer.getOffsetX();
                int y=row*50+layer.getOffsetY();
                card.setBounds(x,y,50,50);

                //将牌放入窗口中
                this.getContentPane().add(card);
            }
        }
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
