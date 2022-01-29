import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class Rope {
    static ArrayList<RopeNode> ropes = new ArrayList<>();
    RopeNode root = new RopeNode();
    public static boolean splitNeed = true;
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


    public static char index(RopeNode node, int i) {
        if (node.value <= i && node.right != null) {
            return index(node.right, i - node.value);
        }
        if (node.left != null) {
            return index(node.left, i);
        }
        return node.data.charAt(i);
    }


    static void printLeafNodes(RopeNode root) {

        if (root == null)
            return;

        if (root.left == null &&
                root.right == null)
        {
            System.out.print(root.data);
            return;
        }
        if (root.left != null&&root.right != null){
            printLeafNodes(root.left);}

        if (root.right != null&&root.left != null){
            printLeafNodes(root.right);}
    }


    public static void status(){
        for (int i = 0; i < ropes.size(); i++) {
            printLeafNodes(ropes.get(i));
            System.out.println("\n");
        }

    }


    public static RopeNode concat(RopeNode ropeNode1 , RopeNode ropeNode2){
        RopeNode newRoot = new RopeNode();
        newRoot.value = ropeNode1.value+ropeNode1.right.value;
        newRoot.left = ropeNode1;
        newRoot.right = ropeNode2;
        int x= ropes.indexOf(ropeNode1);//finding the index of first string
        ropes.add(x,newRoot);//add the new string in proper place
        ropes.remove(x+1);//remove the first string
        ropes.remove(ropeNode2);// remove the second string
        return newRoot;
    }

    public static RopeNode search(RopeNode node,int i){
        if (node.value < i && node.right!=null){
            return search(node.right, i - node.value);
        }
        if (node.left!=null) {
            return search(node.left, i);
        }
        return node;
    }

    public static int getIndexInANode(RopeNode root, int i ) {
        int count = 0;
        int res=0;
        ArrayList<Integer> saveNodeValue = new ArrayList<>();
        Stack<RopeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty() ) {
            RopeNode node = stack.pop();
            if (node.left != null) {
                stack.add(node.left);
            }
            if (node.right != null) {
                stack.add(node.right);
            } if (node.isLeaf()) {
//                System.out.print(node.value);
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
        res = i-count ;
        return res;
    }

//    public static ArrayList<RopeNode> split(RopeNode root , int i , RopeNode realRoot){
//        ArrayList<RopeNode>nodes=new ArrayList<>();
//        RopeNode firstStringRoot=new RopeNode();
//
//        int positionInNode=getIndexInANode(realRoot,i);
//
//        RopeNode temp = realRoot ;
//        if (positionInNode==root.data.length()){
//            if(realRoot.right==root){
//                firstStringRoot = temp.left ;
//                temp.left = null ;
//                ropes.add(firstStringRoot);
//                temp.value -= firstStringRoot.value;
//                ropes.remove(realRoot);
//                ropes.add(realRoot);
//            }
//            System.out.println("akhareshe");
//            while (temp.left != null){
//
//                if (temp.left.right==root){
//                    firstStringRoot = temp.left ;
//                    temp.left = null ;
//
//                    ropes.add(firstStringRoot);
//
//                    temp.value -= firstStringRoot.value;
//
//                    ropes.remove(realRoot);
//                    ropes.add(realRoot);
//                    break;
//                }else {
//                    temp = temp.left;
//                }
//            }
//        }else {
//            if (realRoot.right == root) {
//                RopeNode ropeNodeL = new RopeNode();
//                RopeNode ropeNodeR = new RopeNode();
//                System.out.println(root.data+"shah");
//                ropeNodeL.data = root.data.substring(0, positionInNode);
//                ropeNodeL.value = root.data.substring(0, positionInNode).length();
//                ropeNodeR.data = root.data.substring(positionInNode, root.data.length());
//                ropeNodeR.value = root.data.substring(positionInNode, root.data.length()).length();
//                firstStringRoot = temp.left;
//                temp.left = null;
//                temp.right = null;
//                firstStringRoot = concatInMethod(firstStringRoot, ropeNodeL);
//
//                ropes.add(firstStringRoot);
//                temp.value -= firstStringRoot.value;
//                ropes.remove(realRoot);
//                realRoot = concatInMethod(ropeNodeR, realRoot);
//                ropes.add(realRoot);
//
//            }
//
//            RopeNode ropeNodeL = new RopeNode();
//            RopeNode ropeNodeR = new RopeNode();
//
//            System.out.println(positionInNode+"adad");
//            ropeNodeL.data = root.data.substring(0,positionInNode);
//            ropeNodeL.value = root.data.substring(0,positionInNode).length();
//
//            ropeNodeR.data = root.data.substring(positionInNode,root.data.length());
//            ropeNodeR.value = root.data.substring(positionInNode,root.data.length()).length();
//
//
//            while (temp.left != null){
//
//                if (temp.left.right==root){
//                    firstStringRoot = temp.left ;
//                    temp.left = null ;
//                    firstStringRoot.right=null;
//                    firstStringRoot = concatInMethod(firstStringRoot,ropeNodeL);
//                    ropes.add(firstStringRoot);
//
//                    temp.value -= firstStringRoot.value;
//
//                    ropes.remove(realRoot);
//                    realRoot = concatInMethod(ropeNodeR , realRoot);
//                    ropes.add(realRoot);
//                    break;
//                }else {
//                    temp = temp.left;
//                }
//            }
//        }
//        nodes.add(firstStringRoot);
//        nodes.add(realRoot);
//        return nodes;
//    }


    public static ArrayList<RopeNode> splitInMethod(RopeNode realRoot , int index){
        ArrayList<RopeNode>nodes = new ArrayList<>();
        RopeNode appropriateNode = search(realRoot , index);
        RopeNode rightSplitedRopeRoot = new RopeNode();
        RopeNode temp1 = new RopeNode();
        RopeNode tmp = realRoot ;
        int count = 0;
        int positionInNode = getIndexInANode(realRoot,index) ;
        boolean isTrue = false;
        while (index!=0){
            if (index < tmp.value){
                if (rightSplitedRopeRoot.right == null) {
                    rightSplitedRopeRoot.right = tmp.right;
                    temp1 = rightSplitedRopeRoot;
                }
                else {
                    temp1.left = new RopeNode();
                }
                if (tmp.left != null){
                    if (count==0) {
                        tmp = tmp.left;
                        realRoot = tmp;
                    }
                }
            }
            else {
                index = index - tmp.value;
                if (tmp.right != null){
                    tmp = tmp.right;
                }
            }
        }
        RopeNode ropeNodeL = new RopeNode();
        RopeNode ropeNodeR = new RopeNode();
        if (positionInNode == appropriateNode.data.length()){
            realRoot = concatInMethod(realRoot , tmp.right);
        }
        else {
            ropeNodeL.data = appropriateNode.data.substring(0,positionInNode);
            ropeNodeR.data = appropriateNode.data.substring(positionInNode,appropriateNode.data.length());
            temp1.left = ropeNodeR;
        }
        nodes.add(realRoot);
        nodes.add(rightSplitedRopeRoot);

        return nodes;
    }

    public static RopeNode concatInMethod(RopeNode ropeNode1 , RopeNode ropeNode2){
        RopeNode newRoot = new RopeNode();
        newRoot.value = ropeNode1.value ;
        newRoot.left = ropeNode1;
        newRoot.right = ropeNode2;
        return newRoot;
    }

    public static void insert(RopeNode ropeNode1, RopeNode ropeNode2, int i){
        splitNeed=false;
        int x = ropes.indexOf(ropeNode1);
        int y = ropes.indexOf(ropeNode2);
        ArrayList<RopeNode> ropeNodes = splitInMethod(ropeNode1,i);
        ropes.add(x, concatInMethod(concatInMethod(ropeNodes.get(0),ropeNode2),ropeNodes.get(1)));
        ropes.remove(ropeNode2);//////////////////
        splitNeed=true;
    }

    public static void delete(int i,int j,RopeNode root ,int whichString){
        splitNeed=false;
        ArrayList<RopeNode>nodes=new ArrayList<>();
        ArrayList<RopeNode>nodes1=new ArrayList<>();
        nodes=splitInMethod(root,j);
        nodes1=splitInMethod(nodes.get(0),i);
        ropes.add( whichString ,concatInMethod(nodes1.get(0),nodes.get(1))); //vase in ke har string baade delete, biad sar jay khodesh, ta ghabl in harekat, miraft akhar arrayList.
        splitNeed=true;
    }

    public static void main (String[]args){
        Rope rope = new Rope();
        rope.add("Hiva");
        Rope rope1 = new Rope();
        rope1.add("Mehran");
        status();
        System.out.println("---------------------------1");
        System.out.println(index(ropes.get(0),3));
        System.out.println(index(ropes.get(1),3));
        status();
        System.out.println("---------------------------2");
        concat(ropes.get(0),ropes.get(1));

        status();
        System.out.println("---------------------------3");


    }

}
