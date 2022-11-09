package view;


import model.*;

import javax.swing.*;
import java.awt.*;


/**
 * @author 教徒
 * 游戏的启动入口
 * 游戏窗口初始化设置
 */
public class Start extends JFrame {

    public Start() throws HeadlessException {

        //title
        this.setTitle("羊了个羊diy");
        //size
        this.setSize(400,800);
        //关闭窗口时动作
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //居中
        this.setLocationRelativeTo(null);
        //可视
        //this.setVisible(true);

        //添加组件的方法
        //可以添加自定义的组件到当前窗口
        //添加图片



        //自动刷新线程
        autoRefresh();
        this.setVisible(true);

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
