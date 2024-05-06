package ex04;

class Program {
    public static void main(String args[]) {
        User user1 = new User("Ali", 10000);
        User user2 = new User("Timur", 8000);
        TransactionsService transactionsService = new TransactionsService();
        transactionsService.AddUser(user1);
        transactionsService.AddUser(user2);
        transactionsService.MakeTransaction(user1.getIdentifier(), user2.getIdentifier(), 3000);
        transactionsService.MakeTransaction(user1.getIdentifier(), user2.getIdentifier(), 5000);
        System.out.println(user1);
        System.out.println(user2);
        System.out.println();
        System.out.println(user1.getName() + "'s transactions:");
        for (Transaction i : transactionsService.GetUsersList().GetUserById(user1.getIdentifier()).getTransactionsList().toArray()) {
            System.out.println(i);
        }
        System.out.println();
        System.out.println(user2.getName() + "'s transactions:");
        for (Transaction i : transactionsService.GetUsersList().GetUserById(user2.getIdentifier()).getTransactionsList().toArray()) {
            System.out.println(i);
        }
        transactionsService.RemoveTransactionById(user1.getIdentifier(), user1.getTransactionsList().toArray()[0].getIdentifier());
        System.out.println();
        System.out.println("Impaired transactions:");
        for (Transaction i : transactionsService.GetInvalidTransactions()) {
            System.out.println(i);
        }
    }
}