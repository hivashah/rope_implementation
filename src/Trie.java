import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Trie {
    public static TrieNode root;
    public static Queue queue = new Queue();//queue for sorting suggestions

    public class TrieNode {
        HashMap<Character, TrieNode> children;
        char aChar;
        boolean isWord;//to see its end of the word

        public TrieNode(char aChar) {
            this.aChar = aChar;
            children = new HashMap<>();
            isWord = false;
        }

        public TrieNode() {
            children = new HashMap<>();
        }

        public void insert(String word) {
            if (word == null){ //there is no word to be added
                System.out.println("invalid string");
                return;
            }
            char firstChar = word.charAt(0);
            TrieNode child = children.get(firstChar);
            if (child == null){//its our first char to be added
                child = new TrieNode(firstChar);
                children.put(firstChar, child);
            }
            if (word.length() > 1) {
                child.insert(word.substring(1)); //recursive method for adding other chars
            }
            else {
                child.isWord = true;
            }
        }

    }

    public Trie(ArrayList<String> words) {
        root = new TrieNode();
        for (int i = 0; i < words.size(); i++) {
            root.insert(words.get(i));
        }
    }

    public static ArrayList<String> insertFile(String name) throws IOException {
        ArrayList<String> words = new ArrayList<>();
        File file = new File(
                "/Users/mac/Desktop/trie/" + name);//path of the file that has valid words
        BufferedReader br
                = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null) {
            words.add(st);
        }
        return words;
    }


    public void writeFile(String name){ //to creat a file in case we wanted to have new Trie with new words

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

    public static RopeNode findingRope(String s){ //find rope by string
        for (int i = 0; i < Rope.ropes.size(); i++) {
            if (Rope.ropes.get(i).right.data.equals(s)) {
                return Rope.ropes.get(i);
            }
        }
        return null;
    }

    public static ArrayList<String> suggest(String prefix){ //returms an arraylist with all suggested words
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

    public static void autocomplete(String s) throws IOException {
        ArrayList<String> words = new ArrayList<>();
        Scanner sc = new Scanner(System.in);//to choose the word
        words = insertFile("filename.txt");
        Trie trie = new Trie(words);
        ArrayList<String> suggestions = sort(suggest(s));
        int i = sc.nextInt();
        switch (i) {
            case 1:
                if (findingRope(suggestions.get(0)) != null) {
                    findingRope(suggestions.get(0)).countSuggestions++;//this word has been chosen 1 more time
                } else {
                    Rope rope = new Rope();
                    rope.add(suggestions.get(0));
                    rope.root.countSuggestions++;//this word has been chosen 1 more time

                }
                break;
            case 2:
                if (findingRope(suggestions.get(1)) != null) {
                    findingRope(suggestions.get(1)).countSuggestions++;//this word has been chosen 1 more time

                } else {
                    Rope rope = new Rope();
                    rope.add(suggestions.get(1));
                    rope.root.countSuggestions++;

                }
                break;
            case 3:
                if (findingRope(suggestions.get(2)) != null) {
                    findingRope(suggestions.get(2)).countSuggestions++;//this word has been chosen 1 more time

                } else {
                    Rope rope = new Rope();
                    rope.add(suggestions.get(2));
                    rope.root.countSuggestions++;//this word has been chosen 1 more time
                }
                break;
        }
    }

    public static ArrayList<String> sort(ArrayList<String> su) {
        ArrayList<String> arr = new ArrayList<>();
        if (findingRope(su.get(0)) != null) //check if the suggestion have been chosen before
        {
            Node node = queue.newNode(su.get(0), findingRope(su.get(0)).countSuggestions);//findingRope(su.get(0)).countSuggestion checks how many times this word has been chosen
            if (su.size() > 1) {
                for (int i = 1; i < su.size(); i++) {
                    if (findingRope(su.get(i)) != null) {
                        node = queue.push(node, su.get(i), findingRope(su.get(i)).countSuggestions);
                    } else {
                        node = queue.push(node, su.get(i), 0);
                    }
                }
                for (int i = 0; i < 3; i++) //we need only 3 words
                {
                    if (queue.isEmpty(node) == 0) {
                        arr.add(queue.peek(node));
                        node = queue.pop(node);
                    }
                }
            }
        } else {
            Node node = queue.newNode(su.get(0), 0);// the word has never been selected
            if (su.size() > 1) {
                for (int i = 1; i < su.size(); i++) {
                    node = queue.push(node, su.get(i), 0);
                }
            }
            for (int i = 0; i < 3; i++) {
                if (queue.isEmpty(node) == 0) {
                    arr.add(queue.peek(node));
                    node = queue.pop(node);
                }
            }
        }
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(i + 1 + " : " + arr.get(i));
        }
        return arr;
    }
}