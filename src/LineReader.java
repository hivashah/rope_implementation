import java.util.Scanner;

public class LineReader {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        while (!command.equals("+")) {
            String[] strings;
            strings = command.trim().split("[ ]+");
            if (command.equals("status")) {
                Rope.status();
            } else if (command.startsWith("new")) {
                String string = command.substring(5, command.length() - 1);
                Rope rope = new Rope();
                rope.add(string);
            } else if (strings[0].equals("index")) {
                int s = Integer.parseInt(strings[1]);
                int i = Integer.parseInt(strings[2]);
                System.out.println(Rope.index(Rope.ropes.get(s), i));
            } else if (strings[0].equals("concat")) {
                int i = Integer.parseInt(strings[1]);
                int j = Integer.parseInt(strings[2]);
                Rope.concat(Rope.ropes.get(i), Rope.ropes.get(j));
            }
            command = scanner.nextLine();
        }
    }
}
