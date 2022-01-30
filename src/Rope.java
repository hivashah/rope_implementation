import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Stack;

public class Rope {
    static ArrayList<RopeNode> ropes = new ArrayList<>();  // An arrayList to save ropes
    RopeNode root = new RopeNode();
    public static boolean splitNeed = true; // A field that is used to check if we need to add ropes or not just split is needed!




    // we can get the char of a special index with this method
    // this is a recursive method that works with values and comparing them.
    public static char index(RopeNode node, int i) {
        if (node.value <= i && node.right != null) {
            return index(node.right, i - node.value);
        }
        if (node.left != null) {
            return index(node.left, i);
        }
        return node.data.charAt(i);
    }


    //these two methods work together and they creat our rope
    public void structRoop(String str) {

        RopeNode nptr = new RopeNode(str);
        RopeNode newRoot = new RopeNode();
        newRoot.left = root;
        newRoot.right = nptr;
        newRoot.value = newRoot.left.value;
        if (newRoot.left.right != null)
            newRoot.value += newRoot.left.right.value;
        root = newRoot;
    }

    public void add(String newString) {
        while (newString != null) {
            // the string is divided to ropes' leaves by space. each word in one leaf node!
            if (newString.contains(" ")) {
                structRoop(newString.substring(0, newString.indexOf(' ')) + '_');
                newString = newString.substring(newString.indexOf(' ') + 1, newString.length());
            } else {
                structRoop(newString.substring(0, newString.length()));
                break;
            }
        }
        ropes.add(root);
    }



    // with this method we can print all leaves' data next to each other
    public static void printLeafNodes(RopeNode root) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            if (root.data!=null){
                System.out.print(root.data);
                return;
            }
        }
        //recursion is used here
        if (root.left != null){
            printLeafNodes(root.left);}
        if (root.right != null){
            printLeafNodes(root.right);
        }
    }


    // prints all of the strings that are saved in ropes.
    public static void status(){
        for (int i = 0; i < ropes.size(); i++) {
            printLeafNodes(ropes.get(i));
            System.out.println("\n");
        }

    }


    public static RopeNode concat(RopeNode ropeNode1 , RopeNode ropeNode2){
        RopeNode newRoot = new RopeNode(); // creating a new ropeNode and concat 2 other rope nodes to its right and left.
        newRoot.value = ropeNode1.value+ropeNode1.right.value;
        newRoot.left = ropeNode1;
        newRoot.right = ropeNode2;
        int x= ropes.indexOf(ropeNode1);//finding the index of first string
        ropes.add(x,newRoot);//add the new string in proper place
        ropes.remove(x+1);//remove the first string
        ropes.remove(ropeNode2);// remove the second string
        return newRoot;
    }

    // a method to find a node by giving it a character's index.
    public static RopeNode search(RopeNode node,int i){
        if (node.value < i && node.right!=null){
            // recursion
            return search(node.right, i - node.value);
        }
        if (node.left!=null) {
            return search(node.left, i);
        }
        return node;
    }

    // a method to find a index position in its own ropeNode.
    public static int getIndexInANode(RopeNode root, int i ) {
        int count = 0;
        int res=0;
        ArrayList<Integer> saveNodeValue = new ArrayList<>();
        Stack<RopeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty() ) { // travers all nodes and add their value until we get to appropriate node
            RopeNode node = stack.pop();
            if (node.left != null) {
                stack.add(node.left);
            }
            if (node.right != null) {
                stack.add(node.right);
            } if (node.isLeaf()) { // check if the node is leaf node or not!
                saveNodeValue.add(node.value);
            }
        }

        Collections.reverse(saveNodeValue);
        for (int j = 0; j < saveNodeValue.size() ; j++) {
            if (count+saveNodeValue.get(j)<i) {
                count += saveNodeValue.get(j);
            }else {
                break;
            }
        }
        res = i-count ; // to return index position in its own ropeNode.
        return res;
    }

    public static ArrayList<RopeNode> splitInMethod(RopeNode realRoot , int index){ // we need the root and the index
        ArrayList<RopeNode>nodes = new ArrayList<>(); // because we wanna return 2 roots of our two new Ropes.
        RopeNode appropriateNode = search(realRoot , index); // this node is the node that we wanna do split in it.
        RopeNode rightSplitedRopeRoot = new RopeNode(); // the root of the right part of our splitted Rope.
        RopeNode temp1 = new RopeNode(); // the ropeNode that will save the rightSplitedRopeRoot and is used for traversal in that tree.
        RopeNode rightProcessor = new RopeNode(); // the ropeNode that will be used in special situations and is used for traversal in that tree.
        RopeNode tmp = realRoot ;  // the ropeNode that will save the realRoot and is used for traversal in that tree.
        int x = ropes.indexOf(realRoot); // this will help us add the rope in the appropriate place.
        ropes.remove(realRoot);
        int count = 0;
        boolean isTrue = false;
        int positionInNode = getIndexInANode(realRoot,index) ;

        temp1 = rightSplitedRopeRoot;
        while (tmp.right != search(realRoot, index)){ // according to our rope structure, we continue till we get to our WANTED ropeNode!

            if (realRoot.right.right==search(realRoot, index)){ // special situation.
                index = index - tmp.value; // because we went right.
                tmp=tmp.right; // continue traversing.
                isTrue=true;
                rightSplitedRopeRoot.right = tmp.right; // tmp.right will be the new roots right.
                temp1 = rightSplitedRopeRoot;
                break;
            }

            // if we go to the left subtree of the main tree:
            if (index < tmp.value){
                if (rightSplitedRopeRoot.right == null) {
                    // add deleted nodes to the right of rightSplitedRoot
                    rightSplitedRopeRoot.right = tmp.right;
                    // continue traversing by creating a node and connect it to the left of rightSplitedRoot.
                    rightSplitedRopeRoot.left = new RopeNode();
                    temp1 = rightSplitedRopeRoot;
                    temp1=temp1.left;
                }
                else {
                    if (temp1.left!= null && temp1.left.right!=null) {
                        // updating the values
                        temp1.value = temp1.left.value + temp1.left.right.value;
                    }
                    temp1.right = tmp.right;
                    tmp.right=null;
                    temp1.left = new RopeNode();
                }
                if (tmp.left != null){
                    if (count==0) { //if we are in left subtrees!
                        //continuing traversal!
                        tmp = tmp.left;
                        //updating realRoot
                        realRoot = tmp;
                    }else { //if we are in right subtrees!
                        rightProcessor=tmp ;
                        tmp.right = null;
                        rightProcessor.left.right=null;
                        rightProcessor=rightProcessor.left.left;
                        rightProcessor.left.right=null;
                        break;
                    }
                }
            }
            // if we go to the right subtree of the main tree:
            else {
                count++;
                index = index - tmp.value;
                if (tmp.right != null){
                    tmp = tmp.right;
                }else {
                    break;
                }

            }
        }
        RopeNode ropeNodeL = new RopeNode();
        RopeNode ropeNodeR = new RopeNode();

        // checking different situations of the position of the index we wanna split.
        if (positionInNode == appropriateNode.data.length()){ // if the index be at last of the node
            realRoot = concatInMethod(realRoot , tmp.right);
            tmp.right = null;
        }else if(tmp.value - index==0){ // if the index be at first of the node
            if (isTrue){
                rightSplitedRopeRoot.right = realRoot.right.right;
                tmp.right = null;
            }else {
                rightSplitedRopeRoot = concatInMethod(tmp.right, rightSplitedRopeRoot);
                tmp.right = null;
            }
        }
        else {
            // if the index is not at first or last of the node
            ropeNodeL.data = appropriateNode.data.substring(0,positionInNode);
            ropeNodeL.value = appropriateNode.data.substring(0,positionInNode).length();
            tmp.right = ropeNodeL;
            ropeNodeR.data = appropriateNode.data.substring(positionInNode,appropriateNode.data.length());
            ropeNodeR.value = appropriateNode.data.substring(positionInNode,appropriateNode.data.length()).length();
            if (isTrue){
                rightProcessor.right = ropeNodeR;
                rightSplitedRopeRoot = rightProcessor;
            }else {
                temp1.left = ropeNodeR;
            }
        }
        // add created ropes to be able to return them
        nodes.add(realRoot);
        nodes.add(rightSplitedRopeRoot);

        // if it is a REAL split! we add the roots to our main ropes arrayList.
        if (splitNeed) {
            ropes.add(x, realRoot);
            ropes.add(x + 1, rightSplitedRopeRoot);
        }

        return nodes;
    }


    // this method doesn't add any rope to ropes, but does concat! this is what we need somewhere!
    public static RopeNode concatInMethod(RopeNode ropeNode1 , RopeNode ropeNode2){
        RopeNode newRoot = new RopeNode();
        newRoot.value = ropeNode1.value ;
        newRoot.left = ropeNode1;
        newRoot.right = ropeNode2;
        return newRoot;
    }

    public static void insert(RopeNode ropeNode1, RopeNode ropeNode2, int i){
        // we don't need to add somthing to ropes, so, splitNeed that is used in split adding part, should be false!
        splitNeed=false;
        int x = ropes.indexOf(ropeNode1);
        int y = ropes.indexOf(ropeNode2);
        // returns 2 rope roots that are created during splitting.
        ArrayList<RopeNode> ropeNodes = splitInMethod(ropeNode1,i);
        // insertion with several concatenations!
        ropes.add(x, concatInMethod(concatInMethod(ropeNodes.get(0),ropeNode2),ropeNodes.get(1)));
        ropes.remove(ropeNode2);
        splitNeed=true;
    }

    public static void delete(int i,int j,RopeNode root ,int whichString){
        splitNeed=false;
        ArrayList<RopeNode>nodes=new ArrayList<>();
        ArrayList<RopeNode>nodes1=new ArrayList<>();
        // insertion with several splits and concatenations!
        nodes=splitInMethod(root,j);
        nodes1=splitInMethod(nodes.get(0),i);
        //because we need to replace deleted rope's place with its previous position
        ropes.add( whichString ,concatInMethod(nodes1.get(0),nodes.get(1)));
        splitNeed=true;
    }

}


