package StartWindow.ListasEnlazadas;

public class LinkedList {
    private Node head;
    private int size;

    public LinkedList(){
        this.head= null;
        this.size= 0;
    }

    
    /** 
     * @return boolean
     */
    public boolean isEmpty(){
        return this.head==null;
    }
    
    
    /** 
     * @return int
     */
    public int size(){
        return this.size;
    }

    
    /** 
     * @param data
     */
    public void insertFirst(Object data){
        Node newNode= new Node(data);
        newNode.setNext(this.head);
        this.head= newNode;
        this.size++;
    }

    
    /** 
     * @return Node
     */
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
