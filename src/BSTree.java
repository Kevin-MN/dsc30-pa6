/*
 * Name: Kevin Morales-Nguyen
 * PID:  A17186624
 */

import java.util.*;

/**
 * Binary search tree implementation.
 * 
 * @author Kevin Morales-Nguyen
 * @since  11/6/21
 */
public class BSTree<T extends Comparable<? super T>> implements Iterable {

    /* * * * * BST Instance Variables * * * * */

    private int nelems; // number of elements stored
    private BSTNode root; // reference to root node

    /* * * * * BST Node Inner Class * * * * */

    protected class BSTNode {

        T key;
        LinkedList<T> dataList;
        BSTNode left;
        BSTNode right;

        /**
         * A constructor that initializes the BSTNode instance variables.
         *
         * @param left     Left child
         * @param right    Right child
         * @param dataList Linked list of related info
         * @param key      Node's key
         */
        public BSTNode(BSTNode left, BSTNode right, LinkedList<T> dataList, T key) {
            this.left = left;
            this.right = right;
            this.key = key;
            this.dataList = dataList;
        }

        /**
         * A constructor that initializes BSTNode variables. Note: This constructor is
         * used when you want to add a key with no related information yet. In this
         * case, you must create an empty LinkedList for the node.
         *
         * @param left  Left child
         * @param right Right child
         * @param key   Node's key
         */
        public BSTNode(BSTNode left, BSTNode right, T key) {
            this.left = left;
            this.right = right;
            this.key = key;
            this.dataList = new LinkedList<T>();
        }

        /**
         * Return the key
         *
         * @return The key
         */
        public T getKey() {
            return this.key;
        }

        /**
         * Return the left child of the node
         *
         * @return The left child of the node
         */
        public BSTNode getLeft() {
            return this.left;
        }

        /**
         * Return the right child of the node
         *
         * @return The right child of the node
         */
        public BSTNode getRight() {
            return this.right;
        }

        /**
         * Return the linked list of the node
         *
         * @return The linked list of the node
         */
        public LinkedList<T> getDataList() {
            return this.dataList;
        }

        /**
         * Setter for left child of the node
         *
         * @param newleft New left child
         */
        public void setleft(BSTNode newleft) {
            this.left = newleft;
        }

        /**
         * Setter for right child of the node
         *
         * @param newright New right child
         */
        public void setright(BSTNode newright) {
            this.right = newright;
        }

        /**
         * Setter for the linked list of the node
         *
         * @param newData New linked list
         */
        public void setDataList(LinkedList<T> newData) {
            this.dataList = newData;
        }

        /**
         * Append new data to the end of the existing linked list of the node
         *
         * @param data New data to be appended
         */
        public void addNewInfo(T data) {
            this.dataList.add(data);
        }

        /**
         * Remove 'data' from the linked list of the node and return true. If the linked
         * list does not contain the value 'data', return false.
         *
         * @param data Info to be removed
         * @return True if data was found, false otherwise
         */
        public boolean removeInfo(T data) {
            if(this.dataList.contains(data)){
                this.dataList.remove(data);
                return true;
            }else{
                return false;
            }
        }
    }

    /* * * * * BST Methods * * * * */

    /**
     * 0-arg constructor that initializes root to null and nelems to 0
     */
    public BSTree() {
        this.root = null;
        this.nelems = 0;
    }

    /**
     * Return the root of BSTree. Returns null if the tree is empty
     *
     * @return The root of BSTree, null if the tree is empty
     */
    public BSTNode getRoot() {
        return this.root;
    }

    /**
     * Return the BST size
     *
     * @return The BST size
     */
    public int getSize() {
        return this.nelems;
    }

    /**
     * Insert a key into BST
     * 
     * @param key
     * @return true if insertion is successful and false otherwise
     */
    public boolean insert(T key) {
        if(key == null){ // check if data is null
            throw new NullPointerException();
        }

        if(root == null){
            this.root = new BSTNode(null,null,key);
            this.nelems++;
            return true;
        }

        if(this.findKey(key)){ // check if tree already contains key, no duplicates
            return false;
        }

        boolean add_attempt = recur_add(this.root, key);

        return add_attempt;
    }


    private boolean recur_add(BSTNode current,T key){


        int compare = key.compareTo(current.getKey());

        if(compare == 0){
            return false;
        }
        else if(compare < 0){
            if(current.getLeft() == null){
                current.setleft(new BSTNode(null,null,key));
                this.nelems++;
                return true;
            }
            else{
                return recur_add(current.getLeft(),key);
            }
        }
        else{
            if(current.getRight() == null){
                current.setright(new BSTNode(null,null,key));
                this.nelems++;
                return true;
            }
            else{
                return recur_add(current.getRight(),key);
            }
        }
    }


    /**
     * Return true if the 'key' is found in the tree, false otherwise
     *
     * @param key To be searched
     * @return True if the 'key' is found, false otherwise
     * @throws NullPointerException If key is null
     */
    public boolean findKey(T key) {
        if(key == null){
            throw new NullPointerException();
        }

        if(this.root == null){
            return false;
        }

        return findKey_helper(this.root, key);

    }


    public boolean findKey_helper(BSTNode current, T key){
        if(current == null){
            return false;
        }

        int compare = key.compareTo(current.getKey());

        if(compare == 0){
            return true;
        }
        else if(compare < 0){
                return findKey_helper(current.getLeft(),key);
        }
        else{
                return findKey_helper(current.getRight(),key);
        }
    }

    /**
     * Insert 'data' into the LinkedList of the node whose key is 'key'
     *
     * @param key  Target key
     * @param data To be added to key's LinkedList
     * @throws NullPointerException     If eaither key or data is null
     * @throws IllegalArgumentException If key is not found in the BST
     */
    public void insertData(T key, T data) {
        if(key == null || data == null){
            throw new NullPointerException();
        }

        if(!findKey(key)){
            throw new IllegalArgumentException();
        }

        LinkedList<T> target_add = findDataList(key);
        target_add.add(data);

    }

    /**
     * Return the LinkedList of the node with key value 'key'
     *
     * @param key Target key
     * @return LinkedList of the node whose key value is 'key'
     * @throws NullPointerException     If key is null
     * @throws IllegalArgumentException If key is not found in the BST
     */
    public LinkedList<T> findDataList(T key) {
        if(key == null){
            throw new NullPointerException();
        }

        if(!findKey(key)){
            throw new IllegalArgumentException();
        }

        LinkedList<T> target_data_list = findDataList_helper(this.root, key);
        return target_data_list;
    }

    public LinkedList<T> findDataList_helper(BSTNode node, T key) {
        if(node== null){
            return null;
        }

        int compare = key.compareTo(node.getKey());

        if(compare == 0){
            return node.getDataList();
        }
        else if(compare < 0){
            return findDataList_helper(node.getLeft(),key);
        }
        else{
            return findDataList_helper(node.getRight(),key);
        }
    }

    /**
     * Return the height of the tree
     *
     * @return The height of the tree, -1 if BST is empty
     */
    public int findHeight() {
        if(this.root == null){
            return -1;
        }

        if(this.nelems == 1){
            return 0;
        }

        return findHeightHelper(this.root);
    }

    /**
     * Helper for the findHeight method
     *
     * @param next Root node
     * @return The height of the tree, -1 if BST is empty
     */
    private int findHeightHelper(BSTNode next) {
        if(next == null){
            return -1;
        }
        return 1 + Math.max(findHeightHelper(next.left),findHeightHelper(next.right));
    }

    /* * * * * BST Iterator * * * * */

    public class BSTree_Iterator implements Iterator<T> {
        public BSTree_Iterator() {
            /* TODO */
        }

        public boolean hasNext() {
            /* TODO */
            return false;
        }

        public T next() {
            /* TODO */
            return null;
        }
    }

    public Iterator<T> iterator() {
        /* TODO */
        return null;
    }

    /* * * * * Extra Credit Methods * * * * */

    public ArrayList<T> intersection(Iterator<T> iter1, Iterator<T> iter2) {
        /* TODO */
        return null;
    }

    public T levelMax(int level) {
        /* TODO */
        return null;
    }
}
