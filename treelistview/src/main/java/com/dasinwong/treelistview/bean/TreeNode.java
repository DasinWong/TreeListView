package com.dasinwong.treelistview.bean;

public class TreeNode {

    private String nodeID; //节点ID
    private String parentNodeID; //父节点ID（根节点为0）
    private String nodeContent; //节点内容
    private int nodeOrder; //节点在同级别中的排序
    private int nodeLevel; //节点层级（根节点为0）
    private boolean isLeaf; //是否是叶子节点
    private boolean isExpand; //是否展开
    
    public TreeNode(String nodeID, String parentNodeID, String nodeContent, int nodeOrder) {
        this.nodeID = nodeID;
        this.parentNodeID = parentNodeID;
        this.nodeContent = nodeContent;
        this.nodeOrder = nodeOrder;
        nodeLevel = 0;
        isLeaf = false;
        isExpand = false;
    }

    public String getNodeID() {
        return nodeID;
    }

    public void setNodeID(String nodeID) {
        this.nodeID = nodeID;
    }

    public String getParentNodeID() {
        return parentNodeID;
    }

    public void setParentNodeID(String parentNodeID) {
        this.parentNodeID = parentNodeID;
    }

    public String getNodeContent() {
        return nodeContent;
    }

    public void setNodeContent(String nodeContent) {
        this.nodeContent = nodeContent;
    }

    public int getNodeOrder() {
        return nodeOrder;
    }

    public void setNodeOrder(int nodeOrder) {
        this.nodeOrder = nodeOrder;
    }

    public int getNodeLevel() {
        return nodeLevel;
    }

    public void setNodeLevel(int nodeLevel) {
        this.nodeLevel = nodeLevel;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "nodeID='" + nodeID + '\'' +
                ", parentNodeID='" + parentNodeID + '\'' +
                ", nodeContent='" + nodeContent + '\'' +
                ", nodeOrder=" + nodeOrder +
                ", nodeLevel=" + nodeLevel +
                ", isLeaf=" + isLeaf +
                ", isExpand=" + isExpand +
                '}';
    }
}
