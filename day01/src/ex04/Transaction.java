package ex04;

import java.util.UUID;

class TransactionNotCreated extends RuntimeException {}

class Transaction {

    enum TransactionType {
        INCOME,
        OUTCOME
    }
    private UUID Identifier;
    private User Recipient;
    private User Sender;
    private TransactionType TransferCategory;
    private Integer TransferAmount;

    public Transaction(User Sender, User Recipient, TransactionType TransactionCategory, Integer TransferAmount) {
        if ((TransactionCategory == TransactionType.OUTCOME && TransferAmount > 0) ||
        (TransactionCategory == TransactionType.INCOME && TransferAmount < 0)) {
            System.err.println(TransactionCategory + " Transaction can't have the amount " + TransferAmount);
            throw new TransactionNotCreated();
        } else if (TransactionCategory == TransactionType.OUTCOME && Sender.getBalance() < -TransferAmount) {
            System.err.println("User " + Sender.getName() + " doesn't have the required amount");
            throw new TransactionNotCreated();
        } else {
            this.Identifier = UUID.randomUUID();
            this.Recipient = Recipient;
            this.Sender = Sender;
            this.TransferCategory = TransactionCategory;
            this.TransferAmount = TransferAmount;
            Sender.setBalance(Sender.getBalance() + TransferAmount);
        }
    }

    public UUID getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(UUID Identifier) {
        this.Identifier = Identifier;
    }

    public User getRecipient() {
        return Recipient;
    }

    public void setRecipient(User Recipient) {
        this.Recipient = Recipient;
    }

    public User getSender() {
        return Sender;
    }

    public void setSender(User Sender) {
        this.Sender = Sender;
    }

    public TransactionType getTransactionCategory() {
        return TransferCategory;
    }

    public void setTransactionCategory(TransactionType TransactionCategory) {
        this.TransferCategory = TransactionCategory;
    }

    public Integer getTransferAmount() {
        return TransferAmount;
    }

    public void setTransferAmount(Integer TransferAmount){
        this.TransferAmount = TransferAmount;
    }

    @Override
    public String toString() {
        return Sender.getName() + " -> " + 
        Recipient.getName() + ", " + 
        TransferAmount + ", " + 
        TransferCategory + ", " + 
        Identifier;
    }



}
