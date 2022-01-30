
import java.util.ArrayList;
import java.util.HashMap;
public class Trie {
    public static TrieNode root;

    public class TrieNode {
        HashMap<Character, TrieNode> children;
        char aChar;
        boolean isWord;

        public TrieNode(char aChar) {
            this.aChar = aChar;
            children = new HashMap<>();
            isWord = false;
        }

        public TrieNode() {
            children = new HashMap<>();
        }

        public void insert(String word) {
            if (word == null) {
                System.out.println("invalid string");
                return;
            }
            char firstChar = word.charAt(0);
            TrieNode child = children.get(firstChar);
            if (child == null) {
                child = new TrieNode(firstChar);
                children.put(firstChar, child);
            }
                child.insert(word.substring(1));
        }
    }
    public Trie(ArrayList<String> words) {
        root = new TrieNode();
        for (String word : words)
            root.insert(word);

    }


}