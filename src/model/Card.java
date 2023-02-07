package model;

import view.Start;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Card: 游戏基本数据形式
 * @author 教徒
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
    static private final String BJ ="background";
    static private final String EB ="eliminateBox";
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

                if(card.getGray()|| BJ.equals(card.getName()) ||EB.equals(card.getName())){
                    //灰色,什么都不做，点到背景，消除框也是什么都不做
                    return;
                }else{

                    //System.out.println(card.getName()+"被点击");
                    //删除牌-> 将牌移动到消除框
                    //很多限制条件在消除框中执行
                    myMap.eliminateBox.addToSlot(card);

                    //保留最近点击的卡牌的数据
                    Start.revokeCell=card.getCell();
                    Start.revokeCard=new Card(card.getName());
                    //解决问题：既要删除UI中的组件，也要删除数据模型中的数据和对应状态
                    //所以在card类中添加了新的属性：cell，内容是包含了自身的哪个cell
                    card.getCell().setState(false);
                    card.getCell().setCard(null);

                    //这里需要 重新判定 整个Map中 哪些牌 需要 置灰;暂时将map设置为静态变量，然后通过包名+类名访问
                    view.Start.gameMap.compareAll();

                    //检查是否赢了
                    view.Start.gameMap.ifWin();

                }
            }
        });
    }

    /**
     * 生成的卡牌要被绘制成什么颜色
     * @param g  the <code>Graphics</code> context in which to paint
     */
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
