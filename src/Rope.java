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

    public static void main(String[] args) {
        Rope rope = new Rope();
        rope.add("hiva");
        rope.add("shahrokh");
        System.out.println(ropes.get(1).value);
        System.out.println(ropes.get(1).data);


    }

}