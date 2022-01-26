import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class Rope {
    static ArrayList<RopeNode> ropes = new ArrayList<>();
    RopeNode root = new RopeNode();

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
