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


    private Card card=new Card("grass");




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


        //添加组件的方法
        //可以添加自定义的组件到当前窗口
        this.getContentPane().add(card);


        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Start();
    }
}
