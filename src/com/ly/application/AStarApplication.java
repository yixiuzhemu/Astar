package com.ly.application;/**
 * Created by Administrator on 2018/4/13.
 */

import com.ly.bean.Node;
import com.ly.util.Astar;

import java.util.List;

/**
 * @author Administrator
 * @create 2018-04-13 15:26
 * @desc
 **/
public class AStarApplication {
    public static  void main(String[] args){
        Astar astar = new Astar();
        Node node = new Node(astar.getStartX(), astar.getStartY());
        node.setPath("0");
        astar.caculatePath(node);
        List<List<Node>> path = astar.getPath(node);
        if(path == null){
            System.out.println("该目的地不可达!");
            return;
        }
        System.out.println("-----------------------------");
        for(int k = 0;k<path.size();k++){
            for(int i = 0;i<astar.getCharMap().length;i++){
                for(int j = 0;j<astar.getCharMap()[i].length;j++){
                    Boolean is = false;
                    List<Node> nodes = path.get(k);
                    for(int m = 0;m<nodes.size();m++){
                        Node node1 = nodes.get(m);
                        if(node1.getX() == i && node1.getY() == j){
                            if(node1.equals(astar.getStartNode()) || node1.equals(astar.getEndNode())){
                                is = false;
                            }else{
                                System.out.print('▽');
                                is = true;
                            }
                        }
                    }
                    if(!is){
                        System.out.print(astar.getCharMap()[i][j]);
                    }
                }
                System.out.println();
            }
            System.out.println("-----------------------------");
        }
        System.out.println();
    }
}
