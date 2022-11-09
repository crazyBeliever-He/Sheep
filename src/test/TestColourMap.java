package test;


import model.*;
import tool.*;

import javax.swing.*;
import java.util.List;

/**
 * 每个功能对应一个方法，方便以后的阅读维护等
 */
/**
 * @author 教徒
 * 功能： 测试渲染一个图层的数据
 */
public class TestColourMap extends JFrame {

        public static Map map= MapTool.buildMap(2);

       // private Layer layer= LayerTool.buildLayer(3,3);

        public TestColourMap(){

                //1.初始化窗口的基本信息
                creat();

                //2.渲染图层
                //一 默认下card的左上坐标是0，0      应改变牌的坐标
                //二 布局方式， 默认swing 添加组件  提供了多种布局方式，网格，流， 绝对布局
                List<Layer> list=map.getList();
                for (int i = 0; i < list.size(); i++) {
                        colourLayer(list.get(i));
                }

                //游戏开始时调用一次将需要置灰的牌置灰
                map.compareAll();

                //3.自动刷新
                autoRefresh();
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

        private void creat(){
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
        private void autoRefresh(){
                JFrame start=this;
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
                new TestColourMap();

        }

}
