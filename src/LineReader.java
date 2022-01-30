import java.util.Scanner;

public class LineReader {
    public static Stack stack = new Stack();
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        while (!command.equals("+")) {
            String[] strings;
            strings = command.trim().split("[ ]+");
            if (command.equals("status")) {
                Rope.status();
            } else if (command.startsWith("new")) {
                stack.push(command);
                String string = command.substring(5, command.length() - 1);
                Rope rope = new Rope();
                rope.add(string);
            } else if (strings[0].equals("index")) {
                int s = Integer.parseInt(strings[1]);
                int i = Integer.parseInt(strings[2]);
                System.out.println(Rope.index(Rope.ropes.get(s), i));
            } else if (strings[0].equals("concat")) {
                stack.push(command);
                int i = Integer.parseInt(strings[1]);
                int j = Integer.parseInt(strings[2]);
                Rope.concat(Rope.ropes.get(i), Rope.ropes.get(j));
            } else if (strings[0].equals("insert")) {
                stack.push(command);
                int s1 = Integer.parseInt(strings[1]);
                int i = Integer.parseInt(strings[2]);
                int s2 = Integer.parseInt(strings[3]);
                Rope.insert(Rope.ropes.get(s1), Rope.ropes.get(s2), i);
            } else if (strings[0].equals("split")) {
                stack.push(command);
                int s = Integer.parseInt(strings[1]);
                int i = Integer.parseInt(strings[2]);
                Rope.splitInMethod(Rope.ropes.get(s),i);
            } else if (strings[0].equals("delete")){
                stack.push(command);
                int s = Integer.parseInt(strings[1]);
                int i = Integer.parseInt(strings[2]);
                int j = Integer.parseInt(strings[3]);
                Rope.delete(i,j,Rope.ropes.get(s),s);
            }
            command = scanner.nextLine();
        }
    }
}
