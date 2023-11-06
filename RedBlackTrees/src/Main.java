public class Main {
    public static void main(String[] args) {
        createRandTree(10).printTree();
    }

    public static RedBlackTree createRandTree(int numNodes) {
        RedBlackTree tree = new RedBlackTree();

        for (int i = 0; i < numNodes - 1; i++) {
            int data = (int)(100 * Math.random()) -50;
            tree.insert(data);
        }

        return tree;
    }
}