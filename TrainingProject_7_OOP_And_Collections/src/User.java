//package ex05;


public class User {

    private final int ID;
    private final String NAME;
    private int balance;


    private TransactionsLinkedList transactions = new TransactionsLinkedList();


    public User(String name, int balance) {
        checkedBalance(balance);
        this.ID = UserIdsGenerator.getInstance().generateId();
        this.NAME = name;
        this.balance = balance;
    }


    @Override
    public String toString() {
        return this.NAME + "(id=" + this.ID + ") balance = " + this.balance;
    }


    public int getID() {
        return this.ID;
    }

    public String getName() {
        return this.NAME;
    }

    public int getBalance() {
        return this.balance;
    }

    public TransactionsLinkedList getTransactions() {
        return transactions;
    }


    public void setBalance(int balance) {
        this.balance = balance;
    }


    private void checkedBalance(int balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Illegal Argument: изначальный баланс пользователя не может быть отрицательным");
        }
    }

}
