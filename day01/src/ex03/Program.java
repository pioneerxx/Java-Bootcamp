package ex03;

class Program {
    public static void main(String args[]) {
        User user1 = new User("John", 10000);
        User user2 = new User("Mike", 8000);
        TransactionsLinkedList transactions1 = user1.getTransactionsList();
        TransactionsLinkedList transactions2 = user2.getTransactionsList();
        Transaction trans1 = new Transaction(user1, user2, Transaction.TransactionType.OUTCOME, -5000);
        Transaction trans2 = new Transaction(user2, user1, Transaction.TransactionType.INCOME, 5000);
        Transaction trans3 = new Transaction(user2, user1, Transaction.TransactionType.OUTCOME, -500);
        Transaction trans4 = new Transaction(user1, user2, Transaction.TransactionType.INCOME, 500);

        System.out.println(user1 + " transactions:");
        for(Object i : transactions1.toArray()) {
            System.out.println(i);
        }

        System.out.println(user2 + " transactions:");
        for(Object i : transactions2.toArray()) {
            System.out.println(i);
        }

        System.out.println(user1.getName() + " has " + transactions1.size() + " transactions");
        transactions1.RemoveTransactionById(trans1.getIdentifier());
        System.out.println(user1.getName() + " has " + transactions1.size() + " transactions");
        transactions1.RemoveTransactionById(trans4.getIdentifier());
        System.out.println(user1.getName() + " has " + transactions1.size() + " transactions");
        System.out.println(user2.getName() + " has " + transactions2.size() + " transactions");
        transactions2.RemoveTransactionById(trans2.getIdentifier());
        System.out.println(user2.getName() + " has " + transactions2.size() + " transactions");
        transactions2.RemoveTransactionById(trans3.getIdentifier());
        System.out.println(user2.getName() + " has " + transactions2.size() + " transactions");
    }
}