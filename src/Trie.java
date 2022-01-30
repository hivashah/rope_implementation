
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
            if (word.length() > 1)
                child.insert(word.substring(1));
            else
                child.isWord = true;
        }

    }


    public Trie(ArrayList<String> words) {
        root = new TrieNode();
        for (String word : words)
            root.insert(word);

    }
    public static ArrayList<String> insertFile(String name) throws IOException {
        ArrayList<String> words = new ArrayList<>();
        File file = new File(
                "/Users/mac/Desktop/trie/" + name);
        BufferedReader br
                = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null) {
            words.add(st);
        }
        return words;
    }


    public void writeFile(String name) {
        try {
            File myObj = new File(name);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static RopeNode findingRope(String s) {
        for (int i = 0; i < Rope.ropes.size(); i++) {
            if (Rope.ropes.get(i).right.data.equals(s)) {
                return Rope.ropes.get(i);
            }
        }
        return null;
    }

    public static ArrayList<String> suggest(String prefix) {
        ArrayList<String> list = new ArrayList<>();
        TrieNode lastNode = root;
        String curr = new String();
        for (int i = 0; i < prefix.length(); i++) {
            lastNode = lastNode.children.get(prefix.charAt(i));
            if (lastNode == null)
                return list;
            curr += prefix.charAt(i);
        }
        suggestByRoot(lastNode, list, curr);
        return list;
    }

    public static void suggestByRoot(TrieNode root, ArrayList<String> list, String curr) {
        if (root.isWord) {
            list.add(curr.substring(0, curr.length()));
        }
        if (root.children == null)
            return;
        for (TrieNode child : root.children.values()) {
            suggestByRoot(child, list, curr + (child.aChar));
        }
    }
}