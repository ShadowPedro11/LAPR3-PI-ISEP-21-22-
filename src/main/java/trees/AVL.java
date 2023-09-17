package trees;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class AVL <E extends Comparable<E>> extends BST<E> {


    private int balanceFactor(Node<E> node){
        return height(node.getRight()) - height(node.getLeft());
    }

    private Node<E> rightRotation(Node<E> node){
        Node<E> leftSon = node.getLeft();
        node.setLeft(leftSon.getRight());
        leftSon.setRight(node);

        return leftSon;
    }

    private Node<E> leftRotation(Node<E> node){
        Node<E> rightSon = node.getRight();
        node.setRight(rightSon.getLeft());
        rightSon.setLeft(node);

        return rightSon;
    }

    private Node<E> twoRotations(Node<E> node, int bf){
        if(balanceFactor(node) < 0){
            node.setLeft(leftRotation(node.getLeft()));
            node = rightRotation(node);
        }else{
            node.setRight(rightRotation(node.getRight()));
            node = leftRotation(node);
        }
        return node;
    }

    private Node<E> balanceNode(Node<E> node) {
        int bf = balanceFactor(node);

        if(bf >= -1 && bf <= 1)
            return node;

        if(bf > 1){
            if(balanceFactor(node.getRight()) >= 0)
                return leftRotation(node);
            else
                return twoRotations(node, bf);
        }else {
            if (balanceFactor(node.getLeft()) <= 0)
                return rightRotation(node);
            else
                return twoRotations(node, bf);
        }
    }

    @Override
    public void insert(E element){
        if(element == null)
            return;
        root = insert(element, root);
        size++;
    }
    private Node<E> insert(E element, Node<E> node){

        if(node == null)
            return new Node<>(element, null, null);

        int comp = element.compareTo(node.getElement());

        if(comp == 0) return node;
        if(comp < 0) node.setLeft(insert(element, node.getLeft()));
        else node.setRight(insert(element, node.getRight()));

        return balanceNode(node);
    }

    @Override
    public void remove(E element){
        if(element == null)
            return;
        root = remove(element, root());
        size--;
    }

    private Node<E> remove(E element, Node<E> node) {

        if(node == null) return null;

        int comp = element.compareTo(node.getElement());

        if(comp == 0)
            return removeNodeSet(node);
        else if(comp < 0)
            node.setLeft(remove(element, node.getLeft()));
        else
            node.setRight(remove(element, node.getRight()));

        return balanceNode(node);
    }


    public boolean equals(Object otherObj) {

        if (this == otherObj)
            return true;

        if (otherObj == null || this.getClass() != otherObj.getClass())
            return false;

        AVL<E> second = (AVL<E>) otherObj;
        return equals(root, second.root);
    }

    public boolean equals(Node<E> root1, Node<E> root2) {
        if (root1 == null && root2 == null)
            return true;
        else if (root1 != null && root2 != null) {
            if (root1.getElement().compareTo(root2.getElement()) == 0) {
                return equals(root1.getLeft(), root2.getLeft())
                        && equals(root1.getRight(), root2.getRight());
            } else
                return false;
        }
        else return false;
    }

    public AVL<E> clone(){
        AVL<E> clone = new AVL<>();
        for(E element : this.preOrder())
            clone.insert(element);
        return clone;
    }

}
