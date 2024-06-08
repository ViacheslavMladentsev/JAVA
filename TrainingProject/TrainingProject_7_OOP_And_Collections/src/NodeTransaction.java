//package ex05;

public class NodeTransaction {

    private NodeTransaction next = null;
    private NodeTransaction prev = null;
    private final Transaction TRANSACTION;

    public NodeTransaction(Transaction value) {
        this.TRANSACTION = value;
    }


    @Override
    public String toString() {
        return this.TRANSACTION.toString();
    }


    public NodeTransaction getNext() {
        return this.next;
    }

    public NodeTransaction getPrev() {
        return this.prev;
    }

    public Transaction getTransaction() {
        return this.TRANSACTION;
    }


    public void setNext(NodeTransaction next) {
        this.next = next;
    }

    public void setPrev(NodeTransaction prev) {
        this.prev = prev;
    }
}
