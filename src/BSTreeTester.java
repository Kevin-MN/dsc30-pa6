import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BSTreeTester {
    BSTree<Integer> test;
    BSTree<Integer> sorted;

    @Before
    public void setup(){
        test = new BSTree<Integer>();
        sorted = new BSTree<Integer>();
    }

    @Test
    public void constructor_BSTree_test1(){
        BSTree test1 = new BSTree();
        assertEquals(-1, test1.findHeight());
    }

    @Test
    public void constructor_BSTree_test2(){
        BSTree test2 = new BSTree();
        test2.insert(40);
        assertEquals(0, test2.findHeight());
    }

    @Test
    public void constructor_BSTree_test3(){
        BSTree test3 = new BSTree();
        assertEquals(0, test3.getSize());
        test3.insert(4);
        test3.insert(4);
        assertEquals(1, test3.getSize());
        test3.insert(10);
        test3.insert(15);
        assertEquals(3, test3.getSize());
    }

    @Test
    public void getRoot_BSTree_test1(){
        assertEquals(null, test.getRoot());
    }

    @Test
    public void getRoot_BSTree_test2(){
        Integer add = new Integer(10);
        test.insert(add);
        assertEquals(add, test.getRoot().getKey());
    }

    @Test
    public void getRoot_BSTree_test3(){
        Integer add = new Integer(10);
        assertEquals(null, test.getRoot());
        test.insert(add);
        assertEquals(add, test.getRoot().getKey());
        test.insert(100);
        assertEquals(add, test.getRoot().getKey());
    }


    @Test
    public void insert_BSTree_test1(){
        assertEquals(-1, test.findHeight());
        test.insert(new Integer(1));
        assertEquals(0, test.findHeight());
    }

    @Test
    public void insert_BSTree_test2(){
        test.insert(1);
        assertEquals(false, test.insert(1));
        assertEquals(0,  test.findHeight());
        test.insert(80);
        assertEquals(1, test.findHeight());
    }


    @Test(expected = NullPointerException.class)
    public void insert_BSTree_test3(){
        test.insert(null);
    }


    @Test
    public void insert_BSTree_test4(){
        assertEquals(-1, test.findHeight());
        test.insert(new Integer(1));
        test.insertData(new Integer(1), new Integer(100));
        assertEquals(0, test.findHeight());
    }



    @Test(expected = NullPointerException.class)
    public void findKey_BSTree_test1(){
        test.findKey(null);
    }

    @Test
    public void findKey_BSTree_test2(){
        Integer add = new Integer(10);
        test.insert(add);
        assertEquals(true, test.findKey(add));
    }

    @Test
    public void findKey_BSTree_test3(){
        Integer add = new Integer(10);
        test.insert(add);
        assertEquals(false, test.findKey(new Integer(100)));
    }

    @Test
    public void findKey_BSTree_test4(){
        Integer add = new Integer(10);
        test.insert(add);
        test.insert(add);
        assertEquals(false, test.findKey(new Integer(100)));
        test.insert(100);
        assertEquals(true, test.findKey(new Integer(100)));
    }



    @Test(expected = NullPointerException.class)
    public void findDataList_BSTree_test1(){
       test.findDataList(null);
    }

    @Test(expected = NullPointerException.class)
    public void findDataList_BSTree_test2(){

    }

    @Test(expected = IllegalArgumentException.class)
    public void findDataList_BSTree_test3(){

    }




}