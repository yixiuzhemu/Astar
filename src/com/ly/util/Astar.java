package com.ly.util;
/**
 * Created by Administrator on 2018/4/10.
 */
import com.google.common.collect.Lists;
import com.ly.bean.Node;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author Administrator
 * @create 2018-04-10 10:43
 * @desc
 **/
public class Astar {
    /**
     * 地图宽度
     */
    private int width;
    /**
     * 地图高度
     */
    private int height;
    /**
     * 地图
     */
    private char[][] charMap;
    /**
     * 最小长度
     */
    private final int MIN_LENGTH = 5;
    /**
     * 起点X坐标
     */
    private int startX;
    /**
     * 起点Y坐标
     */
    private int startY;
    /**
     * 终点X坐标
     */
    private int endX;
    /**
     * 终点Y坐标
     */
    private int endY;
    /**
     * 终点
     */
    private Node endNode;
    /**
     * 起点
     */
    private Node startNode;
    /**
     * 过道
     */
    private char aisle;
    /**
     * 墙壁
     */
    private char wall;
    /**
     * 起点
     */
    private char startPoint;
    /**
     * 终点
     */
    private char endPoint;
    /**
     * 初始化地图
     */
    public Astar(){
        aisle = '□';
        wall = '▓';
        startPoint = '☆';
        endPoint = '◎';
        Double w = Math.random() * 10;
        if(w < MIN_LENGTH){
            w += MIN_LENGTH;
        }width = w.intValue() + 1;
        Double h = Math.random() * 8;
        if(h < MIN_LENGTH){
            h += MIN_LENGTH;
        }
        height = h.intValue() + 1;
        charMap = new char[width][height];
        for(int i = 0;i<charMap.length;i++){
            for(int j = 0;j<charMap[i].length;j++){
               Double random =  Math.random() * 2;
               if(random > 1.7D){
                   charMap[i][j] = wall;
               }else if(j == charMap[i].length - 1 || i == charMap.length - 1 || i == 0 || j == 0){
                   charMap[i][j] = wall;
               }
               else{
                    charMap[i][j] = aisle;
                }
            }
        }
        while (((startX + startY) == (endX + endY)) || charMap[startX][startY] == wall || charMap[endX][endY] == wall){
            Double si  = Math.random() * width;
            startX = si.intValue();
            Double sj = Math.random() * height;
            startY = sj.intValue();
            Double ei = Math.random() * width;
            endX = ei.intValue();
            Double ej = Math.random() * height;
            endY = ej.intValue();
        }
        charMap[startX][startY] = startPoint;
        endNode = new Node(endX,endY);
        startNode = new Node(startX,startY);
        charMap[endX][endY] =endPoint;
        for(int i = 0;i<charMap.length;i++){
            for(int j = 0;j<charMap[i].length;j++){
                System.out.print(charMap[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * 计算起点到终点的路径
     * @param currentNode
     */
    public void caculatePath(Node currentNode){
       List<Node> childNode = getChildNode(currentNode);
       if(childNode.size() <= 0){
           return;
       }
       currentNode.setChildNodes(childNode);
       for(int i = 0;i<childNode.size();i++){
           Node node = childNode.get(i);
           //如果终点已经存在一条路径，并且当前节点属于终点，则记录该条路径
           if(StringUtils.isNotBlank(node.getPath()) && node.getEnd()){
               node.setPath(node.getPath() + ":"+currentNode.getPath()+","+i);
           }else{
               node.setPath(currentNode.getPath() + ","+ i);
           }

           if(node.getEnd()){
               return;
           }
           caculatePath(node);
       }
       return;
    }

    /**
     * 获取子节点
     * @param node
     * @return
     */
    public List<Node> getChildNode(Node node){
        int x = node.getX();
        int y = node.getY();
        Node newNode = new Node();
        List<Node> trueNodes = newNode.getTrueNode(x, y, endX, endY, width, height);
        List<Node> minNodes = Lists.newArrayList();
        int minF = 0;
        if(trueNodes.contains(endNode)){
            minNodes.add(endNode);
            endNode.setEnd(true);
            return minNodes;
        }
        //存储可达到的节点
        List<Node> objectiveNodes = Lists.newArrayList();
        //存储不可达到的节点
        List<Node> unArriveNodes = Lists.newArrayList();
        for(Node n : trueNodes){
            int nx = n.getX();
            int ny = n.getY();
            if(charMap[nx][ny] == aisle){
                objectiveNodes.add(n);
            }else{
                unArriveNodes.add(n);
            }
        }
        List<Node> nodes = needExceptNodes(unArriveNodes, node);
        if(nodes != null){
            for(Node n : nodes){
                objectiveNodes.remove(n);
            }
        }
        for(Node n : objectiveNodes){
            if(minF == 0 || minF > n.getF()){
                minF = n.getF();
            }
        }
        for(Node n : objectiveNodes){
            if(minF == n.getF() && !n.equals(node.getParentNode())){
                n.setParentNode(node);
                minNodes.add(n);
            }
        }
        return minNodes;

    }

    /**
     * 判断当前节点是否存在对角障碍，如果存在对角障碍，则将对角障碍相邻的节点排除
     * @param unArriveNodes
     * @param currentNode
     * @return
     */
   public List<Node>  needExceptNodes(List<Node> unArriveNodes,Node currentNode) {
       //如果需要排除的节点数小于2 则不需要排除节点
       if(unArriveNodes.size() < 2){
           return null;
       }
       List<Node> exceptNodes = Lists.newArrayList();
       int x = currentNode.getX();
       int y = currentNode.getY();
       Node nodeNorth = new Node(x - 1, y);
       Node nodeSouth = new Node(x+1,y);
       Node nodeWest = new Node(x,y-1);
       Node nodeEast = new Node(x,y+1);
       if(unArriveNodes.contains(nodeNorth) && unArriveNodes.contains(nodeWest)){
           exceptNodes.add(new Node(x-1,y-1));
       }
       if(unArriveNodes.contains(nodeNorth) && unArriveNodes.contains(nodeEast)){
           exceptNodes.add(new Node(x-1,y+1));
       }
       if(unArriveNodes.contains(nodeSouth) && unArriveNodes.contains(nodeWest)){
           exceptNodes.add(new Node(x+1,y-1));
       }
       if(unArriveNodes.contains(nodeSouth) && unArriveNodes.contains(nodeEast)){
           exceptNodes.add(new Node(x+1,y+1));
       }
       return exceptNodes;
   }

    /**
     * 获取抵达终点的路径
     * @param node
     * @return
     */
   public String getEndPath(Node node){
       if(node.getEnd()){
           return node.getPath();
       }else{
           List<Node> childNodes = node.getChildNodes();
           if(childNodes == null && !node.getEnd()){
               return null;
           }
           for(int i = 0;i<childNodes.size();i++){
               return getEndPath(childNodes.get(i));
           }
       }
       return null;
   }

    /**
     * 获取所有的可达路径的可能
     * @param node
     * @return
     */
   public List<List<Node>> getPath(Node node){
       String endPath = getEndPath(node);
       if(endPath == null){
           return null;
       }
       List<List<Node>> allPath = Lists.newArrayList();
       String[] paths = endPath.split(":");
       for(int i = 0;i<paths.length;i++){
           List<Node> onePath = getOnePath(node, paths[i]);
           allPath.add(onePath);
       }
       return allPath;
   }

    /**
     * 获取一条路径
     * @param node
     * @param path
     * @return
     */
   public List<Node> getOnePath(Node node,String path){
       String[] nodeId = path.split(",");
       List<Node> nodes = Lists.newArrayList();
       int count = 1;
       nodes.add(node);
       getNodes(nodes,node,nodeId,count);
       return nodes;
   }

    /**
     * 获取路径的所有节点
     * @param nodes
     * @param node
     * @param nodesId
     * @param count
     */
   public void getNodes(List<Node> nodes,Node node,String[] nodesId,int count){
       List<Node> childNodes = node.getChildNodes();
       Integer oneNodeId = Integer.valueOf(nodesId[count++]);
       Node oneNode = childNodes.get(oneNodeId);
       nodes.add(oneNode);
       if(nodesId.length > count){
           getNodes(nodes,oneNode,nodesId,count);
       }
   }


    public Node getEndNode() {
        return endNode;
    }

    public Node getStartNode() {
        return startNode;
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

    public char[][] getCharMap() {
        return charMap;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }
}
