//package ex05;

import java.util.UUID;

public class Transaction {

    private UUID ID;
    private final String RECIPIENT;
    private final String SENDER;
    private final int TRANSFER_AMOUNT;
    private final transferCategory TRANSFER_CATEGORY;


    public enum transferCategory {DEBITS, CREDITS}


    public Transaction(String sender, String recipient, transferCategory category, int transferAmount) {
        checkedTransferAmount(category, transferAmount);
        this.ID = UUID.randomUUID();
        this.RECIPIENT = recipient;
        this.SENDER = sender;
        this.TRANSFER_AMOUNT = transferAmount;
        this.TRANSFER_CATEGORY = category;
    }


    @Override
    public String toString() {
        return SENDER + "\t" + RECIPIENT + "  \t" + TRANSFER_AMOUNT + "  \t" + TRANSFER_CATEGORY + "  \t" + ID;
    }


    public UUID getID() {
        return this.ID;
    }

    public String getRecipient() {
        return this.RECIPIENT;
    }

    public String getSender() {
        return this.SENDER;
    }

    public int getTransferAmount() {
        return this.TRANSFER_AMOUNT;
    }

    public transferCategory getTransferCategory() {
        return this.TRANSFER_CATEGORY;
    }


    public void setID(UUID ID) {
        this.ID = ID;
    }


    private static void checkedTransferAmount(transferCategory category, int transferAmount) {
        if (category.equals(transferCategory.DEBITS) && (transferAmount < 0)) {
            throw new IllegalArgumentException("Illegal Argument: сумма транзакции для дебета должна быть больше 0");
        } else if (category.equals(transferCategory.CREDITS) && (transferAmount > 0)) {
            throw new IllegalArgumentException("Illegal Argument: сумма транзакции для кредита должна быть меньше 0");
        }
    }

}
