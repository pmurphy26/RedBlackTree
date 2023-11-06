import java.util.LinkedList;
import java.util.Queue;

public class RedBlackTree {
    private RedBlackTreeNode root;
    private RedBlackTreeNode NIL;

    public RedBlackTree() {
        root = null;
        NIL = new RedBlackTreeNode();
        NIL.setIsBlack(true);
    }

    public RedBlackTree(RedBlackTreeNode root) {
        this.root = root;
        NIL = new RedBlackTreeNode();
        NIL.setIsBlack(true);
    }

    public RedBlackTreeNode getRoot() {return root;}

    public void printTree() {
        int level = 0;
        int nodesInLevel = 1;
        int counter = 0;
        boolean isNextRow = true;

        Queue<RedBlackTreeNode> queue = new LinkedList<RedBlackTreeNode>();
        queue.add(root);

        while (!queue.isEmpty()) {
            RedBlackTreeNode node = queue.remove();

            if (node != null) {
                if (node.getLeft() != null) {
                    if (node.getRight() != null) { //both exist
                        System.out.print(node.getData() + "(" + node.getColor() + ", " +
                                node.getLeft().getData() + " ," + node.getRight().getData() + ") ");
                    } else { //only left exists
                        System.out.print(node.getData() + "(" + node.getColor() + ", " +
                                node.getLeft().getData() + " ," + node.getRight() + ") ");
                    }
                } else {
                    if (node.getRight() != null) { //only right exists
                        System.out.print(node.getData() + "(" + node.getColor() + ", " +
                                node.getLeft() + " ," + node.getRight().getData() + ") ");
                    } else { //neither exist
                        System.out.print(node.getData() + "(" + node.getColor() + ", " +
                                node.getLeft() + " ," + node.getRight() + ") ");
                    }
                }
            }

            counter++;
            if (counter == nodesInLevel) {
                if (!isNextRow)
                    return;

                System.out.println("");
                level++;
                nodesInLevel = (int) Math.pow(2, level);
                counter = 0;
                isNextRow = false;
            }

            if (node != null) {
                queue.add(node.getLeft());
                queue.add(node.getRight());
                if (node.getRight() != null || node.getLeft() != null)
                    isNextRow = true;
            } else {
                queue.add(null);
                queue.add(null);
            }
        }
    }

    public void LeftRotate(RedBlackTreeNode leftNode) {
        RedBlackTreeNode rightNode = leftNode.getRight();
        leftNode.setRightNode(rightNode.getLeft());

        if (rightNode.getLeft() != null) {
            rightNode.getLeft().setParent(leftNode);
        }
        rightNode.setParent(leftNode.getParent());

        if (leftNode.getParent() == null) { //if node is root
            root = rightNode;
        } else if (leftNode == leftNode.getParent().getLeft())
        { //if left node is left child of parent
            leftNode.getParent().setLeftNode(rightNode);
        } else {
            leftNode.getParent().setRightNode(rightNode);
        }

        rightNode.setLeftNode(leftNode);
        leftNode.setParent(rightNode);
    }

    public void RightRotate(RedBlackTreeNode rightNode) {
        RedBlackTreeNode leftNode = rightNode.getLeft();
        rightNode.setLeftNode(leftNode.getRight());

        if (rightNode.getLeft() != null) {
            leftNode.getRight().setParent(rightNode);
        }
        leftNode.setParent(rightNode.getParent());

        if (rightNode.getParent() == null) { //if node is root
            root = leftNode;
        } else if (rightNode == rightNode.getParent().getRight())
        { //if left node is left child of parent
            rightNode.getParent().setRightNode(leftNode);
        } else {
            rightNode.getParent().setLeftNode(leftNode);
        }

        leftNode.setRightNode(rightNode);
        rightNode.setParent(leftNode);
    }

    public void insert(int n) {
        RedBlackTreeNode insertedNode = createNode(n);
        RedBlackTreeNode y = null;
        RedBlackTreeNode x = root;

        while (x != null) { // finding leaf node to add to
            y = x;
            if (insertedNode.getData() < x.getData()) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }

        insertedNode.setParent(y);
        if (y == null) { // if tree is empty
            root = insertedNode;
        } else if (insertedNode.getData() < y.getData()) {
            y.setLeftNode(insertedNode);
        } else {
            y.setRightNode(insertedNode);
        }

        insertedNode.setLeftNode(null);
        insertedNode.setRightNode(null);
        InsertFixUp(insertedNode);
    }

    public void InsertFixUp(RedBlackTreeNode z) {
        while (z != root && !z.getParent().isBlack()) { //while parent is red
            if (z.getParent() == z.getParent().getParent().getLeft()) {//if z's parent is left child
                RedBlackTreeNode y = z.getParent().getParent().getRight(); //uncle node
                if (y != null && !y.isBlack()) { //if y is red
                    z.getParent().setIsBlack(true);
                    y.setIsBlack(true);
                    z.getParent().getParent().setIsBlack(false);
                    z = z.getParent().getParent();
                } else { //if y is black
                    if (z == z.getParent().getRight()) { //makes left node
                        z = z.getParent();
                        LeftRotate(z);
                    }
                    z.getParent().setIsBlack(true);
                    z.getParent().getParent().setIsBlack(false);
                    RightRotate(z.getParent().getParent());
                }
            } else {
                RedBlackTreeNode y = z.getParent().getParent().getLeft(); //uncle node
                if (y != null && !y.isBlack()) { //if y is red
                    z.getParent().setIsBlack(true);
                    y.setIsBlack(true);
                    z.getParent().getParent().setIsBlack(false);
                    z = z.getParent().getParent();
                } else { //if y is black
                    if (z == z.getParent().getLeft()) { //makes right node
                        z = z.getParent();
                        RightRotate(z);
                    }
                    z.getParent().setIsBlack(true);
                    z.getParent().getParent().setIsBlack(false);
                    LeftRotate(z.getParent().getParent());
                }
            }

        }

        root.setIsBlack(true);
    }

    public void delete(RedBlackTreeNode deletedNode) {
        RedBlackTreeNode y = deletedNode;
        RedBlackTreeNode x;
        boolean yOrigBlack = y.isBlack();

        if (deletedNode.getLeft() == null) {
            x = deletedNode.getRight();
            if (x == null) {
                x = setNIL(deletedNode);
                x.getParent().setRightNode(NIL);
            }
            transplant(deletedNode, deletedNode.getRight());
        } else if (deletedNode.getRight() == null) {
            x = deletedNode.getLeft();
            if (x == null) {
                x = setNIL(deletedNode);
                x.getParent().setLeftNode(NIL);
            }
            transplant(deletedNode, deletedNode.getLeft());
        } else {
            y = TreeMin(deletedNode.getRight());
            yOrigBlack = y.isBlack();
            x = y.getRight();
            if (x == null) {
                x = setNIL(y);
                y.setRightNode(x);
            }
            if (y.getParent() == deletedNode) {
                x.setParent(y);
            } else {
                transplant(y, y.getRight());
                y.setRightNode(deletedNode.getRight());
                if (y.getRight() != null)
                    y.getRight().setParent(y);
            }

            transplant(deletedNode, y);
            y.setLeftNode(deletedNode.getLeft());
            if (y.getLeft() != null)
                y.getLeft().setParent(y);
            y.setIsBlack(deletedNode.isBlack());
        }

        if (yOrigBlack)
            DeleteFixUp(x);
        clearNIL();
    }

    public void DeleteFixUp(RedBlackTreeNode x) {
        while (x != root && x.isBlack()) {
            RedBlackTreeNode w;
            boolean wRightIsBlack = false;
            boolean wLeftIsBlack = false;
            if (x == x.getParent().getLeft()) { //if x is left child
                w = x.getParent().getRight(); // setting w to sibling node
                if (w == null)
                    w = setNIL(x.getParent());

                if (!w.isBlack()) { //if w is red, case 1
                    w.setIsBlack(true);
                    x.getParent().setIsBlack(false);
                    LeftRotate(x.getParent());
                    w = x.getParent().getRight();
                    if (w == null)
                        w = setNIL(x.getParent());
                }

                if (w.getRight() == null) {
                    wRightIsBlack = true;
                } else {
                    if (w.getRight().isBlack())
                        wRightIsBlack = true;
                }
                if (w.getLeft() == null) {
                    wLeftIsBlack = true;
                } else {
                    if (w.getLeft().isBlack())
                        wLeftIsBlack = true;
                }

                if (wRightIsBlack && wLeftIsBlack) {//is w's children are black, case 2
                    w.setIsBlack(false);
                    x = x.getParent();
                } else {
                    if (wRightIsBlack) { //case 3, only right child is black
                        w.getLeft().setIsBlack(true);
                        w.setIsBlack(false);
                        RightRotate(w);
                        w = x.getParent().getRight();
                        if (w == null)
                            w = setNIL(x.getParent());
                    }
                    //case 4
                    w.setIsBlack(x.getParent().isBlack());
                    x.getParent().setIsBlack(true);
                    w.getRight().setIsBlack(true);
                    LeftRotate(x.getParent());
                    x = root;
                }
            } else { //if x is right child
                w = x.getParent().getLeft(); // setting w to sibling node
                if (w == null)
                    w = setNIL(x.getParent());
                if (!w.isBlack()) { //if w is red, case 1
                    w.setIsBlack(true);
                    x.getParent().setIsBlack(false);
                    RightRotate(x.getParent());
                    w = x.getParent().getLeft();
                    if (w == null)
                        w = setNIL(x.getParent());
                }
                if (w.getRight() == null) {
                    wRightIsBlack = true;
                } else {
                    if (w.getRight().isBlack())
                        wRightIsBlack = true;
                }

                if (w.getLeft() == null) {
                    wLeftIsBlack = true;
                } else {
                    if (w.getLeft().isBlack())
                        wLeftIsBlack = true;
                }

                if (wRightIsBlack && wLeftIsBlack) {//is w's children are black, case 2
                    w.setIsBlack(false);
                    x = x.getParent();
                } else {
                    if (wLeftIsBlack) { //case 3, if left child is black and right is red
                        w.getRight().setIsBlack(true);
                        w.setIsBlack(false);
                        LeftRotate(w);
                        w = x.getParent().getLeft();
                        if (w == null)
                            w = setNIL(x.getParent());
                    }
                    //case 4
                    w.setIsBlack(x.getParent().isBlack());
                    x.getParent().setIsBlack(true);
                    w.getLeft().setIsBlack(true);
                    RightRotate(x.getParent());
                    x = root;
                }
            }

        }

        x.setIsBlack(true);
    }

    public void transplant(RedBlackTreeNode currentNode, RedBlackTreeNode newNode) {
        if (currentNode.getParent() == null) { //is root
            root = newNode;
        } else if (currentNode == currentNode.getParent().getLeft()) {
            currentNode.getParent().setLeftNode(newNode);
        } else {
            currentNode.getParent().setRightNode(newNode);
        }
        if (newNode != null) {
            newNode.setParent(currentNode.getParent());
        }
    }

    public RedBlackTreeNode TreeMin(RedBlackTreeNode node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }

        return node;
    }

    public RedBlackTreeNode createNode(int n) {
        return new RedBlackTreeNode(n);
    }

    public RedBlackTreeNode setNIL(RedBlackTreeNode parent) {
        NIL.setParent(parent);
        //NIL.setRightNode(NIL);
        //NIL.setLeftNode(NIL);
        NIL.setIsBlack(true);
        return NIL;
    }

    public void clearNIL() {
        NIL.setIsBlack(true);
        if (NIL.getParent() != null) {
            if (NIL == NIL.getParent().getLeft())
                NIL.getParent().setLeftNode(null);
            else if (NIL == NIL.getParent().getRight())
                NIL.getParent().setRightNode(null);
        }
        NIL.setParent(null);
    }
}
