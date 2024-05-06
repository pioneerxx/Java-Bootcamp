package ex00;

class Program {
    public static void main(String args[]) {
        User John = new User(0, "John", 500);
        User Mike = new User(1, "Mike", 0);
        System.out.println(John);
        System.out.println(Mike);
        Transaction transaction1 = new Transaction(John, Mike, Transaction.TransactionType.OUTCOME, -200);
        Transaction transaction2 = new Transaction(Mike, John, Transaction.TransactionType.INCOME, 200);
        System.out.println(transaction1);
        System.out.println(transaction2);
        System.out.println(John);
        System.out.println(Mike);
    }
}