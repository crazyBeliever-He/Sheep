package model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author 教徒
 * Card: 游戏基本属性
*/
public class Card extends JComponent {
    //继承是为了能使牌通过add的方法添加到当前窗口
    //事件机制
    /**
     * name: 很关键
     * isGray
     * image
     * grayImage
     */
    private String name;
    private Boolean isGray;
    private Image image;
    private Image grayImage;

    /**
     * x,y,牌在框体中位置
     * width,height牌的长宽
     */
    private Integer x;
    private Integer y;
    private Integer width;
    private Integer height;

    private Cell cell;

    EliminateBox eliminateBox=new EliminateBox();


    private String bj="background";
    private String eb="eliminateBox";
    public Card(String name){
        this.name = name;

        this.image=Toolkit.getDefaultToolkit().getImage("picture\\"+name+".png");
        this.grayImage=Toolkit.getDefaultToolkit().getImage("picture\\"+name+"_gray.png");

        this.isGray=false;

        this.height=50;
        this.width=50;



        this.x=0;
        this.y=0;

        /*
          鼠标事件监听，然后做出相应的动作
         */
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                Card card=(Card)e.getSource();//获取当前组件

                if(card.getGray()|| bj.equals(card.getName()) ||eb.equals(card.getName())){
                    //灰色,什么都不做
                    //点到背景，消除框，消除框里面的图片也是什么都不做
                    return;
                }else{
                    //问题:通过parent.remove只是在窗口中删除了该card对象,但是cell中状态state和card并没有删除
                    //card.getParent().remove(card);//调用上层容器删除自己。通过父容器删掉自己（一般树形使用这样的方式）

                    //删除牌-> 将牌移动到消除框
                    eliminateBox.addSlot(card);


                    //解决问题：既要删除UI中的组件，也要删除数据模型中的数据和对应状态
                    //所以在card类中添加了新的属性：cell，内容是包含了自身的哪个cell
                    cell.setState(false);
                    cell.setCard(null);

                    //这里需要 重新判定 整个Map中 哪些牌 需要 置灰
                    // 暂时将map设置为静态变量，然后通过包名+类名访问
                    view.Start.map.compareAll();
                }
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        if(this.isGray){
            //灰色
            g.drawImage(this.grayImage,this.x,this.y,null);
        }else{
            //正常
            g.drawImage(this.image,this.x,this.y,null);
        }
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    @Override
   public String getName() {
        return name;
    }

    //name要和图片名字对应

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Boolean getGray() {
        return isGray;

    }

    public void setGray(Boolean gray) {
        isGray = gray;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getGaryImage() {
        return grayImage;
    }

    public void setGaryImage(Image garyImage) {
        this.grayImage = garyImage;
    }

/*    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }*/
}
