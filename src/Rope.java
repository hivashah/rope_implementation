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
        newRoot.value = newRoot.left.value ;
        if (newRoot.left.right != null)
            newRoot.value += newRoot.left.right.value;
        root = newRoot;
    }

    public void add(String newString){
        while (newString!=null){
            if (newString.contains(" ")) {
                structRoop(newString.substring(0, newString.indexOf(' '))+'_');
                newString = newString.substring(newString.indexOf(' ') + 1, newString.length());
            }else {
                structRoop(newString.substring(0, newString.length()));
                break;
            }
        }
        ropes.add(root);
    }


    public static char index(RopeNode node,int i){
        if (node.value <= i && node.right!=null){
            return index(node.right, i - node.value);
        }
        if (node.left!=null) {
            return index(node.left, i);
        }
        return node.data.charAt(i);
    }


    //bug full
    void printLeafNodes(RopeNode root) {

        if (root.left == null && root.right == null) {
//            System.out.print(root.data);
//            return;
//        }
        if (root.left != null){
            printLeafNodes(root.left);
        }
        if (root.right != null){
            return printLeafNodes(root.right);
        }
    }


    public static void main(String[] args) {
        Rope rope = new Rope();
        rope.add("hiva");
        rope.add("shahrokh");
        System.out.println(ropes.get(1).value);
        System.out.println(ropes.get(1).data);


    }

}