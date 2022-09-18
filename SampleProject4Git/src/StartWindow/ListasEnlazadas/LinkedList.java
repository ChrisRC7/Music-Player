package StartWindow.ListasEnlazadas;

public class LinkedList {
    private Node head;
    private int size;

    public LinkedList(){
        this.head= null;
        this.size= 0;
    }

    public boolean isEmpty(){
        return this.head==null;
    }
    
    public int size(){
        return this.size;
    }

    public void insertFirst(Object data){
        Node newNode= new Node(data);
        newNode.setNext(this.head);
        this.head= newNode;
        this.size++;
    }

    public Node deleFirts(){
        if(this.head!=null){
            Node temp= this.head;
            this.head= this.head.getNext();
            this.size--;
            return temp;
        } else{
            return null;
        }
    }

    public void displayList(){
        Node current= this.head;
        while (current!=null){
            System.out.println(current.getData());
            current= current.getNext();
        }
    }
}
