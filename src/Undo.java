public class Undo {
    public static int size = 0;
    public static String word = "";
    public static void undo() {
        String lastCommand = LineReader.stack.peek();//getting the last command
        String[] strings;

        strings = lastCommand.trim().split("[ ]+");
        LineReader.stack.pop();
        //whatever the command is we do the opposite
        if (strings[0].equals("new")) {
            Rope.ropes.remove(Rope.ropes.size() - 1);
        } else if (strings[0].equals("split")) {
            int s = Integer.parseInt(strings[1]);
            Rope.concat(Rope.ropes.get(s), Rope.ropes.get(s + 1));
        } else if (strings[0].equals("concat")) {
            int length = Rope.ropes.get(Integer.parseInt(strings[1])).value;
            Rope.splitInMethod(Rope.ropes.get(Integer.parseInt(strings[1])), length);
        } else if (strings[0].equals("insert")) {
            System.out.println(Integer.parseInt(strings[2]) + Character.getNumericValue(lastCommand.charAt(lastCommand.length() - 1)) + "pooof");
            System.out.println(Integer.parseInt(strings[1]) + "poaf");
            Rope.delete(Integer.parseInt(strings[2]), Integer.parseInt(strings[2]) + Character.getNumericValue(lastCommand.charAt(lastCommand.length() - 1)), Rope.ropes.get(Integer.parseInt(strings[1])), Integer.parseInt(strings[1]));
        } else if (strings[0].equals("delete")) {
            //delete s1 i j
            //insert s1 s2 i
            Rope rope = new Rope();
            rope.add(word);
            System.out.println(word + "word");
            Rope.insert(Rope.ropes.get(Integer.parseInt(strings[1])), rope.root, 3);
            word = "";
        }
    }

    public static void getSize(RopeNode root)//getting the size of the string by head ropeNode
    {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            if (root.data != null) {  //ke null print nakone hey
//                System.out.print(root.data);
                size += root.data.length();
                return;
            }
        }
        if (root.left != null) {
            getSize(root.left);
        }
        if (root.right != null) {
            getSize(root.right);
        }
    }

    public static void getString(RopeNode root)//getting the string by head ropeNode
    {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            if (root.data != null) {  //ke null print nakone hey
//                System.out.print(root.data);
                word += root.data;
                return;
            }
        }
        if (root.left != null) {
            getString(root.left);
        }
        if (root.right != null) {
            getString(root.right);
        }
    }
}
