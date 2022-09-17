package StartWindow.ListasEnlazadas;

public class Double_CircularLinkedList {
    private Node head;
    private Node last;
    private int size;

    public Double_CircularLinkedList(){
        this.head = null;
        this.last = null;
        this.size = 0;

    }

    public boolean isEmpty(){
       if (this.head == this.last){
        if (this.head == null){
            return this.last==null;
        } else{
            return false;
        }
       } else{
        return false;
       }
    }

    public int size(){
        return this.size;
    }

    public void insertFirst(Object data){
        Node newNode= new Node(data);

        if (this.isEmpty()){
            this.head= this.last = newNode;
        } else{
            this.head.setPrevious(newNode);
            newNode.setNext(this.head);
            newNode.setPrevious(this.last);
            this.head = newNode;
        }
        this.size++;
    }

    public void insertLast(Object data){
        Node newNode= new Node(data);

        if (this.isEmpty()){
            this.head = this.last = newNode;
        } else {
            this.last.setNext(newNode);
            newNode.setNext(this.head);
            newNode.setPrevious(this.last);
            this.last= newNode;
        }
        this.size++;
    }

    public Node delete (Object searchValue){
        Node current = this.head;
        Node previous = this.head;

        while(current!=null){
            if (current.getData().equals(searchValue)){
                if (current==this.head) {
                    this.head.getNext().setPrevious(this.head.getPrevious());
                    this.head.getPrevious().setNext(this.head.getNext());
                    this.head= this.head.getNext();
                } else {
                    previous.setNext(current.getNext());
                    current.getNext().setPrevious(previous);
                }
                return current; 
            }else{
                previous = current;
                current = current.getNext();
            }
        }
        return null;
    }

    
}
