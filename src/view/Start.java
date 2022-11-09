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


    public static Map map= MapTool.buildMap(2);
    public Start() throws HeadlessException {
        //1.初始化窗口的基本信息
        creat();

        //2.渲染地图
        colourMap();

        //3.自动刷新
        autoRefresh();

    }
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

    public void colourMap(){
        List<Layer> list=map.getList();
        for (int i = 0; i < list.size(); i++) {
            colourLayer(list.get(i));
        }
        //置灰判定
        map.compareAll();
    }
    private void colourLayer(Layer layer){
        //将cell添加到窗口中
        Cell[][] cells=layer.getCells();
        for (int row = 0; row < cells.length; row++) {
            for(int column=0;column<cells[row].length;column++){

                Card card=cells[row][column].getCard();

                //70是该层牌与牌之间的距离，offsetX是整层的偏移量
                int x=column*50+layer.getOffsetX();
                int y=row*50+layer.getOffsetY();
                card.setBounds(x,y,50,50);

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

    public static void main(String[] args) {
        new Start();
    }
}
