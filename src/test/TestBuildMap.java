package test;

import model.Layer;
import model.Map;
import tool.LayerTool;

import java.util.List;


/**
 * 测试创建一个地图
 *
 * @author 教徒
 */
public class TestBuildMap {

    public static void main(String[] args) {

        Map map=new Map();

        map.setLevels(3);

        Layer layer1 = LayerTool.buildLayer(3,3);
        Layer layer2 = LayerTool.buildLayer(3,3);
        Layer layer3 = LayerTool.buildLayer(3,3);

        map.getList().add(layer1);
        map.getList().add(layer2);
        map.getList().add(layer3);

        List<Layer> list=map.getList();
        for (int i = 0; i < list.size(); i++) {
            System.out.println("第"+i+"个图层");
            list.get(i).showCells();
        }
    }
}
