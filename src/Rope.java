import java.util.ArrayList;
import java.util.Arrays;

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
        newRoot.value = ropeNode1.value;
        newRoot.left = ropeNode1;
        newRoot.right = ropeNode2;
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

        public static void main (String[]args){
            Rope rope = new Rope();
            rope.add("Hiva");
            Rope rope1 = new Rope();
            rope1.add("Mehran");
            System.out.println(concat(rope.root,rope1.root).data);

            System.out.println(index(rope.root , 5));
            System.out.println(search(rope.root,5));

//            System.out.println(ropes.get(1).value);
//            System.out.println(ropes.get(1).data);


        }

    }
