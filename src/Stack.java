class Stack {

    private class Node {
        String data;
        Node link;
    }

    Node top;

    Stack() {
        this.top = null;
    }

    public void push(String x) {
        Node temp = new Node();

        if (temp == null) {
            System.out.println("stack is full");
            return;
        }

        temp.data = x;
        temp.link = top;
        top = temp;
    }

    public boolean isEmpty()
    {
        return top == null;
    }

    public String peek() {
        if (!isEmpty()) {
            return top.data;
        }
        else {
            System.out.println("Stack is empty");
            return null;
        }
    }

    public void pop() {
        if (top == null) {
            System.out.println("Stack is empty");
            return;
        }

        top = (top).link;
    }

}