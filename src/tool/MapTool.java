package tool;

import model.Map;
import model.Layer;

import java.util.ArrayList;
import java.util.List;


/**
 *  唯一功能：生成关卡
 *  <p>
 *  能设置每层的卡牌数
 *  也可以设置每层固定的偏移量
 * @author 教徒
 */
public class MapTool {
    public static Map buildMap(int levels) {
        Map map=new Map();
        map.setLevels(levels);
        /*
          在绝对布局中，同样位置，先加入的组件展示在最上层，后加入的在下面
          和现实当中的直觉相反（直觉：后加入的应该在上层）
         */
        List<Layer> layers=new ArrayList<>();
        for(int i=0;i<levels;i++){
            layers.add(LayerTool.buildLayer(3,5));
        }
        //构建图层的链式关系,第一层的parent默认为空，等价于，parent为空，说明已经到顶层，循环/递归结束
        layers.get(0).setParent(null);
        for(int i=1;i<layers.size();i++){
            layers.get(i).setParent(layers.get(i-1));
        }

        for (Layer layer : layers) {
            map.getList().add(layer);
        }


        return map;
    }
}
