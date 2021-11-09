/*
 * Name: Kevin Morales-Nguyen
 * PID:  A17186624
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Search Engine implementation.
 * 
 * @author Kevin Morales-Nguyen
 * @since  11/8/21
 */
public class SearchEngine {

    /**
     * Populate BSTrees from a file
     * 
     * @param movieTree  - BST to be populated with actors
     * @param studioTree - BST to be populated with studios
     * @param ratingTree - BST to be populated with ratings
     * @param fileName   - name of the input file
     * @returns false if file not found, true otherwise
     */
    public static boolean populateSearchTrees(
            BSTree<String> movieTree, BSTree<String> studioTree,
            BSTree<String> ratingTree, String fileName
    ) {
        // open and read file
        File file = new File(fileName);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                // read 5 lines per batch:
                // movie, cast, studios, rating, trailing hyphen
                String movie = scanner.nextLine().trim().toLowerCase();
                String cast[] = scanner.nextLine().split(" ");
                String studios[] = scanner.nextLine().split(" ");
                String rating = scanner.nextLine().trim();
                scanner.nextLine();

                //for all the movie and rating trees add keys to tree
                for(int i = 0; i < cast.length;i++) {
                    movieTree.insert(cast[i]);
                    ratingTree.insert(cast[i]);
                    movieTree.insertData(cast[i], movie);
                    //don't add duplicate ratings
                    if (!ratingTree.findDataList(cast[i]).contains(rating)) {
                        ratingTree.insertData(cast[i], rating);
                    }
                }
                    // add all the movies to studios as keys
                for(int l = 0; l < studios.length;l++){
                    studioTree.insert(studios[l]);
                    studioTree.insertData(studios[l], movie);
                }

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    /**
     * Search a query in a BST
     * 
     * @param searchTree - BST to be searched
     * @param query      - query string
     */
    public static void searchMyQuery(BSTree<String> searchTree, String query) {


        String[] keys = query.toLowerCase().split(" ");

        // search and output intersection results
        // hint: list's addAll() and retainAll() methods could be helpful
        LinkedList<String> full_data = new LinkedList<String>();


        try { // check if key is not part of tree
            if (keys.length == 1 && !searchTree.findKey(keys[0])) {
                print(query,  new LinkedList<String>());
                return;
            }
        }
        catch(IllegalArgumentException e){
            print(query, searchTree.findDataList(keys[0]));
        }


        try { // if there is only one arg then query it and end method call
            for (int i = 0; i < keys.length; i++) {
                full_data.addAll(searchTree.findDataList(keys[i]));
            }
        }catch(IllegalArgumentException e){

        }

        try { // retain shared data from keys
            for (int l = 0; l < keys.length; l++) {
                for(int i = 0; i< searchTree.findDataList(keys[l]).size(); i++){
                }
               full_data.retainAll(searchTree.findDataList(keys[l]));
            }
        }catch(IllegalArgumentException e){

        }

        full_data = removeDuplicates(full_data);

        print(query, full_data);



            for (int l = 0; l < keys.length; l++) { // print out individual queries
                //without duplicates
                LinkedList<String> indv_data = new LinkedList<String>();
                try {
                    indv_data = searchTree.findDataList(keys[l]);

                indv_data.removeAll(full_data);// remove previously mentioned movies
                full_data.addAll(indv_data); // update previously mentioned with newly mentioned movies
                if(indv_data.isEmpty()){
                    continue;
                }
                if(!indv_data.isEmpty() && full_data.isEmpty()){
                    continue;
                }

                } catch (IllegalArgumentException e) {

                }
                print(keys[l], indv_data);
            }


    }


    /**
     * This helper method removes duplicates from and linkedlist
     * @param list list to remove duplicates from
     * @return
     */
    public  static LinkedList<String> removeDuplicates(LinkedList<String> list) {
        LinkedList<String> newList = new LinkedList<String>();

        for (String element : list) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }

        return newList;
    }



        // search and output individual results
        // hint: list's addAll() and removeAll() methods could be helpfu

    /**
     * Print output of query
     * 
     * @param query     Query used to search tree
     * @param documents Output of documents from query
     */
    public static void print(String query, LinkedList<String> documents) {
        if (documents == null || documents.isEmpty())
            System.out.println("The search yielded no results for " + query);
        else {
            Object[] converted = documents.toArray();
            Arrays.sort(converted);
            System.out.println("Documents related to " + query
                    + " are: " + Arrays.toString(converted));
        }
    }

    /**
     * Main method that processes and query the given arguments
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {

        /* TODO */
        // initialize search trees
        BSTree<String> actors_movies = new BSTree<String>();
        BSTree<String> studios_movies = new BSTree<String>();
        BSTree<String> actors_ratings = new BSTree<String>();

        // process command line arguments
        String fileName = args[0];
        int searchKind = Integer.parseInt(args[1]);
        String query = "";

        for(int p = 2; p < args.length;p++){
            if(p == args.length - 1){
                query = query.concat(args[p]);
                continue;
            }
            query = query.concat(args[p].concat(" "));
        }

        // populate search trees
        populateSearchTrees(actors_movies,studios_movies, actors_ratings, fileName);
        // choose the right tree to query
        switch(searchKind){
            case(0):
                searchMyQuery(actors_movies, query);
                break;
            case(1):
                searchMyQuery(studios_movies, query);
                break;
            case(2):
                searchMyQuery(actors_ratings, query);
                break;

            default:
            break;
        }

    }
}
