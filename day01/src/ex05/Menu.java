package ex05;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;


class Menu {
    TransactionsService Service;
    Scanner scanner;
    boolean flag = false;
    public Menu() {
        Service = new TransactionsService();
        scanner = new Scanner(System.in);
    }
    public void StartDev() {
        String prompt;
        flag = true;
        while(true) {
            PrintOptions(flag);
            prompt = scanner.next();
            switch (prompt) {
                case "1":
                    AddUser();
                    break;
                case "2":
                    CheckBalance();
                    break;
                case "3":
                    PerformTransfer();
                    break;
                case "4":
                    ViewTransactions();
                    break;
                case "5":
                    RemoveTransfer();
                    break;
                case "6":
                    CheckValidity();
                    break;
                case "7":
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Incorrect input");
                    break;
            }
        }
    }

    public void PrintOptions(boolean flag) {
        System.out.println("1. Add a user");
        System.out.println("2. View user balances");
        System.out.println("3. Perform a transfer");
        System.out.println("4. View all transactions for a specific user");
        if (flag) {
            System.out.println("5. DEV - remove a transfer by ID");
            System.out.println("6. DEV - check transfer validity");
            System.out.println("7. Finish execution");
        } else {
            System.out.println("5. Finish execution");
        }
    }
    
    public void StartProd() {
        String prompt;
        while(true) {
            PrintOptions(false);
            prompt = scanner.next();
            switch (prompt) {
                case "1":
                    AddUser();
                    break;
                case "2":
                    CheckBalance();
                    break;
                case "3":
                    PerformTransfer();
                    break;
                case "4":
                    ViewTransactions();
                    break;
                case "5":
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Incorrect input");
                    break;
            }
        }
    }

    private void AddUser() {
        System.out.println("Enter a user name and balance");
        String prompt = scanner.next();
        int balance;
        User user;
        if (!prompt.equals("EXIT")) {
            balance = scanner.nextInt();
            try {
                user = new User(prompt, balance);
                Service.AddUser(user);
                System.out.println("User with id = " + user.getIdentifier() + " is added");
            } catch (IllegalUserDataException e) {
                System.out.println("Illegal data for user");
                AddUser();
            }
        }
    }

    private void CheckBalance() {
        System.out.println("Enter a user ID");
        String prompt = scanner.next();
        int id;
        int balance;
        if (!prompt.equals("EXIT")) {
            try {
                id = Integer.parseInt(prompt);
                balance = Service.CheckUserBalance(Service.GetUsersList().GetUserById(id));
                System.out.println(Service.GetUsersList().GetUserById(id).getName() + " - " + balance);
            } catch (NumberFormatException e) {
                System.out.println("Illegal user ID");
                CheckBalance();
            } catch (UserNotFoundException e) {
                System.out.println("No user with such ID");
                CheckBalance();
            }
        }
    }

    private void PerformTransfer() {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        String prompt = scanner.next();
        if (!prompt.equals("EXIT")) {
            try {
                Service.MakeTransaction(Integer.parseInt(prompt), scanner.nextInt(), scanner.nextInt());
                System.out.println("The transfer in completed");
            } catch (InputMismatchException e) {
                System.out.println("Incorrect data");
                PerformTransfer();
            } catch (UserNotFoundException e) {
                System.out.println("No user with such ID");
                PerformTransfer();
            } catch (IllegalTransactionException e) {
                System.out.println("Illegal data for transaction");
                PerformTransfer();
            } catch (NumberFormatException e) {
                System.out.println("Incorrect data");
                PerformTransfer();
            }
        }
    }

    private void ViewTransactions() {
        System.out.println("Enter a user ID");
        String prompt = scanner.next();
        if (!prompt.equals("EXIT")) {
            try {
                for (Transaction i : Service.GetUsersList().GetUserById(Integer.parseInt(prompt)).getTransactionsList().toArray()) {
                    System.out.println((i.getTransactionCategory() == Transaction.TransactionType.OUTCOME ? "To " : "From ") + 
                    i.getRecipient().getName() + "(id = " + i.getRecipient().getIdentifier() + ") " + i.getTransferAmount() + " with id = " + i.getIdentifier());
                }
            } catch (NumberFormatException e) {
                System.out.println("Incorrect data");
                ViewTransactions();
            } catch (UserNotFoundException e) {
                System.out.println("No user with such ID");
                ViewTransactions();
            }
        }
    }

    private void RemoveTransfer() {
        System.out.println("Enter a user ID and a transfer ID");
        String prompt = scanner.next();
        Transaction tr;
        Integer userid;
        UUID transID;
        if (!prompt.equals("EXIT")) {
            try {
                userid = Integer.parseInt(prompt);
                transID = UUID.fromString(scanner.next());
                tr = Service.GetUsersList().GetUserById(userid).getTransactionsList().GetTransactionById(transID);
                System.out.println("Transfer " + (tr.getTransactionCategory() == Transaction.TransactionType.OUTCOME ? "to " : "from ") 
                + tr.getRecipient().getName() + "(id = " + tr.getRecipient().getIdentifier() + ") " + 
                (tr.getTransferAmount() < 0 ? -tr.getTransferAmount() : tr.getTransferAmount()) + " removed");
                Service.RemoveTransactionById(userid, transID);
            } catch (NumberFormatException e) {
                System.out.println("Incorrect data");
                RemoveTransfer();
            } catch (IllegalArgumentException e) {
                System.out.println("Incorrect data");
                RemoveTransfer();
            } catch (UserNotFoundException e) {
                System.out.println("No user with such ID");
                RemoveTransfer();
            } catch (TransactionNotFoundException e) {
                System.out.println("No transfer with such ID");
                RemoveTransfer();
            }
        }
    }

    private void CheckValidity() {
        System.out.println("Check results:");
        Transaction[] tr = Service.GetInvalidTransactions();
        if (tr.length == 0) {
            System.out.println("No invalid transactions");
        } else {
            for (Transaction i : tr) {
                System.out.println(i.getSender().getName() + "(id = " + 
                i.getSender().getIdentifier() + ") has an unacknowledged transfer id = " + 
                i.getIdentifier() + (i.getTransactionCategory() == Transaction.TransactionType.INCOME ? " from " : " to ") + 
                i.getRecipient().getName() + "(id = " + i.getRecipient().getIdentifier() + ") for " + i.getTransferAmount());
            }
        }
    }
}