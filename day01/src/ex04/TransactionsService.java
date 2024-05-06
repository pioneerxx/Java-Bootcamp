package ex04;

import java.util.UUID;

class IllegalTransactionException extends RuntimeException {}

public class TransactionsService {
    private UsersList uList = new UsersArrayList();

    public TransactionsService() {}

    public void AddUser(User user) {
        uList.AddUser(user);
    }

    public UsersList GetUsersList() {
        return uList;
    }

    public Integer CheckUserBalance(User user) {
        return user.getBalance();
    }

    public void MakeTransaction(Integer SenderId, Integer RecipientId, Integer TransferAmount) {
        if (SenderId == RecipientId || TransferAmount < 0 || uList.GetUserById(SenderId).getBalance() < TransferAmount) {
            throw new IllegalTransactionException();
        }
        User sender = uList.GetUserById(SenderId);
        User recipient = uList.GetUserById(RecipientId);
        Transaction outcome = new Transaction(sender, recipient, Transaction.TransactionType.OUTCOME, -TransferAmount);
        Transaction income = new Transaction(recipient, sender, Transaction.TransactionType.INCOME, TransferAmount);
        income.setIdentifier(outcome.getIdentifier());
        uList.GetUserById(SenderId).getTransactionsList().AddTransaction(outcome);
        uList.GetUserById(RecipientId).getTransactionsList().AddTransaction(income);
    }

    public Transaction[] RetrieveTransactions(User user) {
        return user.getTransactionsList().toArray();
    }

    public void RemoveTransactionById(Integer UserId, UUID TransactionId) {
        uList.GetUserById(UserId).getTransactionsList().RemoveTransactionById(TransactionId);
    }

    public Transaction[] GetInvalidTransactions() {
        TransactionsList InvalidTransactions = new TransactionsLinkedList();
        boolean flag = false;
        Transaction[] AllTransactions = AllTransactions();
        if (AllTransactions.length == 1) {
            InvalidTransactions.AddTransaction(AllTransactions[0]);
        } else {
            for (int i = 0; i < AllTransactions.length; i++) {
                if (!flag && i != 0) {
                    InvalidTransactions.AddTransaction(AllTransactions[i - 1]);
                }
                flag = false;
                for (int j = 0; j < AllTransactions.length; j++) {
                    if (j == i) {
                        continue;
                    }
                    if (AllTransactions[i].getIdentifier() == AllTransactions[j].getIdentifier()) {
                        flag = true;
                        break;
                    }
                }
            }
        }
        return InvalidTransactions.toArray();
    }

    private User[] UsersArray() {
        User[] arr = new User[uList.GetUserAmount()];
        for (int i = 1; i <= uList.GetUserAmount(); i++) {
            arr[i-1] = uList.GetUserById(i);
        }
        return arr;
    }

    private Transaction[] AllTransactions() {
        if (uList.GetUserAmount() == 0) {
            System.err.println("The user list is empty");
            return null;
        }
        TransactionsList tList = new TransactionsLinkedList();
        User[] uArr = UsersArray();
        for (User user : uArr) {
            if (user.getTransactionsList().size() != 0) {
                for (Transaction j : user.getTransactionsList().toArray()) {
                    tList.AddTransaction(j);
                }
            }
        }
        return tList.toArray();
    }
}
