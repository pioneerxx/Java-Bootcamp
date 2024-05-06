package ex05;

import java.util.UUID;

class TransactionNotFoundException extends RuntimeException {}

public class TransactionsLinkedList implements TransactionsList {

    private Node start;
    private Node end;
    private Integer NodeCount = 0;

    private class Node {
        private Transaction transaction;
        private Node next = null;
        private Node prev = null;

        public Node(Transaction transaction, Node prev, Node next) {
            this.transaction = transaction;
            this.next = next;
            this.prev = prev;
        }

        public Transaction getTransaction() {
            return transaction;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

    }

    public TransactionsLinkedList() {}

    @Override
    public void AddTransaction(Transaction transaction) {
        Node tmp = new Node(transaction, end, null);
        if (end == null) {
            end = tmp;
        } else {
            end.next = tmp;
            end = tmp;
        }
        if (start == null) {
            start = end;
        }
        NodeCount++;
    }

    @Override
    public void RemoveTransactionById(UUID Identifier) {
        Node i = start;
        for (; i != null; i = i.next) {
            if (i.getTransaction().getIdentifier().equals(Identifier)) {
                if (i.getPrev() != null && i.getNext() != null) {
                    i.getPrev().setNext(i.getNext());
                    i.getNext().setPrev(i.getPrev());
                } else if (i.getPrev() == null) {
                    start = i.getNext();
                    i.getNext().setPrev(null);
                } else {
                    end = i.getPrev();
                    i.getPrev().setNext(null);
                }
                NodeCount--;
                break;
            }
        }
        if (i == null) {
            throw new TransactionNotFoundException();
        }
    }

    public Transaction GetTransactionById(UUID Identifier) {
        Transaction tr = null;
        Node i = start;
        for (; i != null; i = i.getNext()) {
            if (i.getTransaction().getIdentifier().equals(Identifier)) {
                tr = i.getTransaction();
                break;
            }
        }
        if (i == null) {
            throw new TransactionNotFoundException();
        }
        return tr;
    }

    @Override
    public Transaction[] toArray() {
        if (NodeCount == 0) {
            System.err.println("Transactions list is empty");
            return null;
        }
        Transaction[] arr = new Transaction[NodeCount];
        int j = 0;
        for (Node i = start; i != null; i = i.next) {
            arr[j] = i.getTransaction();
            j++;
        }
        return arr;
    }

    public Integer size() {
        return NodeCount;
    }
}
