public class RopeNode {
    int value;
    String data;
    RopeNode left, right;
    public  int countSuggestions=0; // This field is used in our sort method and our priority Queue.

    public RopeNode(int key, String data, RopeNode left, RopeNode right) {
        this.value = key;
        this.data = data;
        this.left = left;
        this.right = right;
    }

    public RopeNode(String data) {
        this.data = data;
        left = null;
        right = null;
        value = data.length();
    }

    public RopeNode(){
        data = null;
        left = null;
        right = null;
        value = 0;
    }

    boolean isLeaf() {
        return left == null ? right == null : false;
    }



}