//package ex05;

import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {

    private NodeTransaction head = null;
    private NodeTransaction tail = null;
    private int countNode = 0;

    public TransactionsLinkedList() {
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        NodeTransaction temp = this.head;

        for (int i = 0; i < this.countNode; i++) {
            result.append(temp.getTransaction()).append(" \n");
            temp = temp.getNext();
        }
        result.append("\b");
        return result.toString();
    }


    @Override
    public void addTransaction(Transaction transaction) {
        NodeTransaction nodeTransaction = new NodeTransaction(transaction);
        if (this.head == null) {
            this.head = nodeTransaction;
        } else {

            this.tail.setNext(nodeTransaction);
            nodeTransaction.setPrev(this.tail);
        }
        this.tail = nodeTransaction;
        ++this.countNode;
    }

    @Override
    public void removeTransactionByUUID(UUID identifierUUID) throws Exception {
        NodeTransaction nodeRemove = this.head;

        if (this.countNode != 0) {
            for (int i = 1; i <= this.countNode; ++i) {
                if (nodeRemove.getTransaction().getID().equals(identifierUUID)) {
                    deleteNode(nodeRemove);
                    return;
                }
                nodeRemove = nodeRemove.getNext();
            }
        }
        outputException(identifierUUID);
    }


    @Override
    public Transaction[] toArray() {
        Transaction[] transactions = new Transaction[this.countNode];
        NodeTransaction node = this.head;
        for (int i = 0; i < this.countNode; ++i) {
            transactions[i] = node.getTransaction();
            node = node.getNext();
        }
        return transactions;
    }


    public NodeTransaction getHead() {
        return head;
    }

    public NodeTransaction getTail() {
        return tail;
    }

    public int getCountNode() {
        return countNode;
    }

    public int getAmountTransactionByUUID(UUID transactionID) throws Exception {
        NodeTransaction nodeAmount = this.head;
        if (this.countNode != 0) {
            for (int i = 1; i <= this.countNode; ++i) {
                if (nodeAmount.getTransaction().getID().equals(transactionID)) {
                    return nodeAmount.getTransaction().getTransferAmount();
                }
                nodeAmount = nodeAmount.getNext();
            }
        }
        outputException(transactionID);
        return 0;
    }


    public void setHead(NodeTransaction head) {
        this.head = head;
    }

    public void setTail(NodeTransaction tail) {
        this.tail = tail;
    }

    public void setCountNode(int countNode) {
        this.countNode = countNode;
    }

    private void deleteNode(NodeTransaction removeNode) {
        if (removeNode.getPrev() != null)
            removeNode.getPrev().setNext(removeNode.getNext());
        else
            head = removeNode.getNext();
        if (removeNode.getNext() != null)
            removeNode.getNext().setPrev(removeNode.getPrev());
        else
            tail = removeNode.getPrev();
        --this.countNode;
    }

    private void outputException(UUID identifierUUID) throws Exception {
        throw new UserNotFoundException("Ошибка: запрашиваемый идентификатор " + identifierUUID + " не найден");
    }
}
