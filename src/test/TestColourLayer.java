package test;
/**
 * 每个功能对应一个方法，方便以后的阅读维护等
 */

import model.Card;
import model.Cell;
import model.Layer;
import tool.LayerTool;
import view.Start;

import javax.swing.*;

/**
 * @author 教徒
 * 功能： 测试渲染一个图层的数据
 */
public class TestColourLayer extends JFrame {

        private Layer layer= LayerTool.buildLayer(3,3);

        public  TestColourLayer(){

                //1.初始化窗口的基本信息
                creat();

                //2.渲染图层
                //一 默认下card的左上坐标是0，0      应改变牌的坐标
                //二 布局方式， 默认swing 添加组件  提供了多种布局方式，网格，流， 绝对布局
                colourLayer();


                //3.自动刷新
                autoRefresh();
        }

        private void colourLayer(){
                Cell[][] cells=layer.getCells();
                for (int row = 0; row < cells.length; row++) {
                        for(int column=0;column<cells[row].length;column++){

                                Card card=cells[row][column].getCard();
                                int x=column*50;
                                int y=row*50;
                                card.setBounds(x,y,50,50);

                                this.getContentPane().add(card);
                        }
                        System.out.println();
                }
        }

        private void creat(){
                //title
                this.setTitle("羊了个羊diy");
                //size
                this.setSize(450,800);
                //关闭窗口时动作
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                //居中
                this.setLocationRelativeTo(null);

                //设置 绝对布局
                this.setLayout(null);
                this.setBounds(0,0,450,800);

                //可视
                this.setVisible(true);
        }
        private void autoRefresh(){
                JFrame start=this;
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
                new TestColourLayer();
        }
}
