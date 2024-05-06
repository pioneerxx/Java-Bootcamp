package ex04;

import java.util.UUID;

public interface TransactionsList {
    public void AddTransaction(Transaction transaction);
    public void RemoveTransactionById(UUID Identifier);
    public Transaction[] toArray();
}
