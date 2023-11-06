import java.awt.*;

public class RedBlackTreeNode {
    private int data;
    private RedBlackTreeNode left;
    private RedBlackTreeNode right;
    private RedBlackTreeNode parent;
    private boolean isBlackNode;

    public RedBlackTreeNode(int data) {
        this.data = data;
        left = right = parent = null;
        isBlackNode = false;
    }

    public RedBlackTreeNode() {
        left = right = parent = null;
        isBlackNode = false;
    }

    public void setLeftNode(RedBlackTreeNode left) {
        this.left = left;

        if (left != null) {
            left.setParent(this);
        }
    }

    public void setRightNode(RedBlackTreeNode right) {
        this.right = right;

        if (right != null) {
            right.setParent(this);
        }
    }

    public int getData() {return data;}

    public RedBlackTreeNode getLeft() {return left;}

    public RedBlackTreeNode getRight() {return right;}

    public RedBlackTreeNode getParent() {return parent;}

    public void setParent(RedBlackTreeNode parent) {this.parent = parent;}

    public void setColor(Color newColor) {
        if (newColor == Color.red) {
            isBlackNode = false;
        } else if (newColor == Color.BLACK) {
            isBlackNode = true;
        }
    }

    public void setIsBlack(boolean newValue) {
        isBlackNode = newValue;
    }

    public String getColor() {
        if (isBlackNode)
            return "Black";
        else
            return "Red";
    }

    public boolean isBlack() {return isBlackNode;}
}
