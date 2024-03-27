//package ex05;

import java.util.UUID;

public class TransactionsService {

    private final UsersArrayList userList = new UsersArrayList();
    private final TransactionsLinkedList allTransactions = new TransactionsLinkedList();


    @Override
    public String toString() {
        return "TransactionsService{" +
                "userList=" + userList +
                "}";
    }


    public UsersArrayList getUserList() {
        return userList;
    }

    public TransactionsLinkedList getAllTransactions() {
        return allTransactions;
    }


    public void addUser(User user) {
        userList.addUser(user);
    }

    public int getBalanceUser(User user) throws Exception {
        for (int i = 0; i < this.userList.getNumberOfUser(); ++i) {
            if (user.getID() == this.userList.getUserByIndex(i).getID()) {
                return this.userList.getUserByIndex(i).getBalance();
            }
        }
        throw new UserNotFoundException("Ошибка: запрашиваемый пользователь " + user.toString() + " не найден");
    }

    public void performTransfer(int idSender, int idRecepient, int sumTransaction) throws Exception {
        checkedSumTransaction(sumTransaction);
        checkedDifferentUser(idSender, idRecepient);
        User sender = userList.getUserById(idSender);
        User recepient = userList.getUserById(idRecepient);
        checkedUserBalanceForTransaction(sender, sumTransaction);

        Transaction transactionSender = new Transaction(sender.getName(), recepient.getName(), Transaction.transferCategory.CREDITS, -sumTransaction);
        Transaction transactionRecepient = new Transaction(recepient.getName(), sender.getName(), Transaction.transferCategory.DEBITS, sumTransaction);
        transactionRecepient.setID(transactionSender.getID());

        sender.getTransactions().addTransaction(transactionSender);
        recepient.getTransactions().addTransaction(transactionRecepient);

        updateBalanceUsersAfterTransaction(sender, recepient, sumTransaction);

        this.allTransactions.addTransaction(transactionSender);
        this.allTransactions.addTransaction(transactionRecepient);
    }

    public TransactionsLinkedList allUserTransaction(User user) {
        return user.getTransactions();
    }

    public void removedUserTransactionById(UUID transactionID, int userID) throws Exception {
        User userForRemoveTransaction = userList.getUserById(userID);
        userForRemoveTransaction.getTransactions().removeTransactionByUUID(transactionID);
        removeTransaction(userForRemoveTransaction, transactionID);
    }

    public Transaction[] searchNotValidTransactions() {
        TransactionsLinkedList notValidTransaction = new TransactionsLinkedList();
        chackedValidTransaction(notValidTransaction);
        return allTransactions.getCountNode() == 0 ? null : notValidTransaction.toArray();
    }


    private static void checkedSumTransaction(int sumTransaction) {
        if (sumTransaction < 1) {
            throw new IllegalTransactionException("Ошибка: сумма транзакции должна быть больше 0");
        }
    }

    private static void checkedDifferentUser(int idSender, int idRecepient) {
        if (idSender == idRecepient) {
            throw new IllegalArgumentException("Ошибка: отправитель и получатель должны быть разными пользователями");
        }
    }

    private static void checkedUserBalanceForTransaction(User user, int sumTransaction) {
        if (user.getBalance() < sumTransaction) {
            throw new IllegalArgumentException("Ошибка: сумма транзакции превышает баланс отправителя " + user);
        }
    }

    private static void updateBalanceUsersAfterTransaction(User sender, User recepient, int sumTransaction) {
        sender.setBalance(sender.getBalance() - sumTransaction);
        recepient.setBalance(recepient.getBalance() + sumTransaction);
    }

    private void removeTransaction(User sender, UUID ID) {
        NodeTransaction nodeRemove = allTransactions.getHead();

        if (allTransactions.getCountNode() != 0) {
            for (int i = 1; i <= allTransactions.getCountNode(); ++i) {
                if (nodeRemove.getTransaction().getSender().equals(sender.getName()) && (nodeRemove.getTransaction().getID().equals(ID))) {
                    deleteNode(nodeRemove);
                    return;
                }
                nodeRemove = nodeRemove.getNext();
            }
        }
    }

    private void deleteNode(NodeTransaction removeNode) {
        if (removeNode.getPrev() != null) {
            removeNode.getPrev().setNext(removeNode.getNext());
        } else {
            allTransactions.setHead(removeNode.getNext());
        }
        if (removeNode.getNext() != null) {
            removeNode.getNext().setPrev(removeNode.getPrev());
        } else {
            allTransactions.setTail(removeNode.getPrev());
        }

        allTransactions.setCountNode(allTransactions.getCountNode() - 1);
    }

    private void chackedValidTransaction(TransactionsLinkedList notValidTransaction) {
        NodeTransaction nodeNotValid = allTransactions.getHead();
        for (int i = 0; i < allTransactions.getCountNode(); ++i) {
            boolean isValid = true;
            if (nodeNotValid.getPrev() == null) {
                if (nodeNotValid.getNext() == null) {
                    isValid = false;
                } else {
                    if (!nodeNotValid.getTransaction().getID().equals(nodeNotValid.getNext().getTransaction().getID())) {
                        isValid = false;
                    }
                }
            } else {
                if (!nodeNotValid.getTransaction().getID().equals(nodeNotValid.getPrev().getTransaction().getID())) {
                    if (nodeNotValid.getNext() == null) {
                        isValid = false;
                    } else {
                        if (!nodeNotValid.getTransaction().getID().equals(nodeNotValid.getNext().getTransaction().getID())) {
                            isValid = false;
                        }
                    }
                }
            }

            if (!isValid) {
                notValidTransaction.addTransaction(nodeNotValid.getTransaction());
            }

            nodeNotValid = nodeNotValid.getNext();
        }
    }

}
