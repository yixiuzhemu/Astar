package com.ly.bean;/**
 * Created by Administrator on 2018/4/10.
 */

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author Administrator
 * @create 2018-04-10 11:06
 * @desc
 **/
public class Node {
    private int x ;
    private int y;
    private int f;
    private int g;
    private int h;
    private String path;
    private Node parentNode;
    private List<Node> childNodes;
    private Boolean end = false;
    public List<Node> getChildNodes() {
        return childNodes;
    }

    public Node(){}

    /**
     * 构造方法，通过传入终点坐标,计算当前点到重点的曼哈顿估值
     * @param x
     * @param y
     * @param currentX
     * @param currentY
     * @param endX
     * @param endY
     */
    public Node(int x, int y, int currentX, int currentY, int endX, int endY){
        this.x = x;
        this.y = y;
        this.h = (Math.abs(x-endX) + Math.abs(y-endY)) * 10;
        if(Math.abs(currentX - x) == 1 && Math.abs(currentY - y) == 1){
            this.g = 14;
        }else{
            this.g = 10;
        }
        this.f = this.h + this.g;
    }

    /**
     * 判断该节点是否越界
     * @param width
     * @param height
     * @return
     */
    public boolean isNode(int width,int height){
        if(x >= width || x < 0 || y >= height || y < 0){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 获取目标节点的周围节点
     * @param currentX
     * @param currentY
     * @param endX
     * @param endY
     * @param width
     * @param height
     * @return
     */
    public List<Node> getTrueNode(int currentX,int currentY,int endX,int endY,int width,int height){
        List<Node> trueNodes = Lists.newArrayList();
        Boolean isN;
        Node eastNode = new Node(currentX, currentY + 1, currentX, currentY, endX, endY);
        isN = eastNode.isNode(width, height);
        if(isN){
            trueNodes.add(eastNode);
        }
        Node eastAndNorthNode = new Node(currentX - 1, currentY + 1, currentX, currentY, endX, endY);
        isN = eastAndNorthNode.isNode(width, height);
        if(isN){
            trueNodes.add(eastAndNorthNode);
        }
        Node northNode = new Node(currentX - 1, currentY , currentX, currentY, endX, endY);
        isN = northNode.isNode(width, height);
        if(isN){
            trueNodes.add(northNode);
        }
        Node westAndNorthNode = new Node(currentX - 1, currentY - 1, currentX, currentY, endX, endY);
        isN = westAndNorthNode.isNode(width, height);
        if(isN){
            trueNodes.add(westAndNorthNode);
        }
        Node westNode = new Node(currentX, currentY - 1, currentX, currentY, endX, endY);
        isN = westNode.isNode(width, height);
        if(isN){
            trueNodes.add(westNode);
        }
        Node westAndSouthNode = new Node(currentX + 1, currentY - 1, currentX, currentY, endX, endY);
        isN = westAndSouthNode.isNode(width, height);
        if(isN){
            trueNodes.add(westAndSouthNode);
        }
        Node southNode = new Node(currentX + 1, currentY, currentX, currentY, endX, endY);
        isN = southNode.isNode(width, height);
        if(isN){
            trueNodes.add(southNode);
        }
        Node eastAndSouthNode = new Node(currentX + 1, currentY + 1, currentX, currentY, endX, endY);
        isN = eastAndSouthNode.isNode(width, height);
        if(isN){
            trueNodes.add(eastAndSouthNode);
        }

        return trueNodes;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    public void setChildNodes(List<Node> childNodes) {
        this.childNodes = childNodes;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public Node(int x, int y){
        this.x = x;
        this.y = y;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (x != node.x) return false;
        return y == node.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    public Boolean getEnd() {
        return end;
    }

    public void setEnd(Boolean end) {
        this.end = end;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
