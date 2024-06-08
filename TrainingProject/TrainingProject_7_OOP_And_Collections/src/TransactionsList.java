//package ex05;

import java.util.UUID;

public interface TransactionsList {

    public void addTransaction(Transaction transaction);

    public void removeTransactionByUUID(UUID identifierUUID) throws Exception;

    public Transaction[] toArray();

}
