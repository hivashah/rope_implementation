public class Queue {

    public Node head=new Node() ;

    public static Node newNode(String d, int p){
        Node temp = new Node();
        temp.data = d;
        temp.priority = p;
        temp.next = null;

        return temp;
    }

    public static String peek(Node head){
        return (head).data;
    }

    public static Node pop(Node head){
        Node temp = head;
        (head) = (head).next;
        return head;
    }

    public static Node push(Node head, String d, int p){
        Node start = (head);
        Node temp = newNode(d, p);
        if ((head).priority <p) {
            temp.next = head;
            (head) = temp;
        }
        else {

            while (start.next != null &&
                    start.next.priority > p) {
                start = start.next;
            }

            temp.next = start.next;
            start.next = temp;
        }
        return head;
    }

    static int isEmpty(Node head)
    {
        return ((head) == null)?1:0;
    }


}