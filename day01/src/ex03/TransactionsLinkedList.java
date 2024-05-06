package ex03;

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
        if (start == null) {
            Node tmp = new Node(transaction, null, null);
            start = tmp;
            end = tmp;
            start.setNext(end);
            end.setPrev(start);
        } else {
            Node tmp = new Node(transaction, end, null);
            end.setNext(tmp);
            end = tmp;
        }
        NodeCount++;
    }

    @Override
    public void RemoveTransactionById(UUID Identifier) {
        for (Node i = start; i != null; i = i.next) {
            if (i.getTransaction().getIdentifier().equals(Identifier)) {
                if (i.getPrev() != null) {
                    i.getPrev().setNext(i.getNext());
                }
                if (i.getNext() != null) {
                    i.getNext().setPrev(i.getPrev());
                }
                NodeCount--;
                break;
            }
        }
    }

    @Override
    public Transaction[] toArray() {
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
