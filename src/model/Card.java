package model;
import imgs.*;
import java.awt.*;

/**
 * @author 教徒
 * Card: 游戏基本属性
*/
public class Card {
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
    private int x;
    private int y;
    private int width;
    private int height;

    public String getName() {
        return name;
    }

    //name要和图片名字对应

    public void setName(String name) {
        this.name = name;
        this.image=Toolkit.getDefaultToolkit().getImage("imgs\\"+name+".png");
        this.grayImage=Toolkit.getDefaultToolkit().getImage("imgs\\"+name+".png");


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
        return garyImage;
    }

    public void setGaryImage(Image garyImage) {
        this.garyImage = garyImage;
    }

    public int getX() {
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
    }
}
