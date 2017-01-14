package Tree;

import java.util.ArrayList;

public class Node {

    private int value;
    private int weight;
    private Node parent = null;
    private ArrayList<Node> children = null;

    public Node() {
        children = new ArrayList<>();
    }

    public Node(int weight) {
        this.weight = weight;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void addChild(Node child) {
        if (child != null) {
            children.add(child);
            child.setParent(this);
        }
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
        for (Node child:
             this.children) {
            child.setParent(this);
        }
    }

    public ArrayList<Node> getChildren() {
        return this.children;
    }
}
