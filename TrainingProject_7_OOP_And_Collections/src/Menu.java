//package ex05;

import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;

public class Menu {

    private static final String BOOT_MENU_DEV = "1. Добавить пользователя\n" +
            "2. Посмотреть баланс пользователя\n" +
            "3. Добавить транзакцию\n" +
            "4. Посмотреть все транзакции пользователя\n" +
            "5. DEV – удалить транзацию у пользователя по ID\n" +
            "6. DEV – посмотреть список не валидных транзакций\n" +
            "7. Завершить программу";

    private static final String BOOT_MENU_PRODUCT = "1. Добавить пользователя\n" +
            "2. Посмотреть баланс пользователя\n" +
            "3. Добавить транзакцию\n" +
            "4. Посмотреть все транзакции пользователя\n" +
            "7. Завершить программу";

    private static final String BOOT_ERROR_NO_PARAMETER = "\nОшибка: Отсутствует аргумент. Запуск программы должен осуществляться с одним аргументом -" +
            "--profile=production (стандартный режим) или --profile=dev(расширенный режим.\n" +
            "Пример запуска программы \"java Main --profile=dev\"\n" +
            "Пожалуйста перезапустите программу с корректным набором аргументов";

    private static final String BOOT_ERROR_MISTAKE_PARAMETER = "\nОшибка: Некорректный аргумент. Запуск программы должен осуществляться с одним аргументом -" +
            "--profile=production (стандартный режим) или --profile=dev(расширенный режим.\n" +
            "Пример запуска программы \"java Main --profile=dev\"\n" +
            "Пожалуйста перезапустите программу с корректным набором аргументов";


    private boolean isExitBootMenu = false;

    private boolean isExitMenu = false;


    private TransactionsService service = new TransactionsService();

    public void startProgram(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException(BOOT_ERROR_NO_PARAMETER);
        }
        Scanner sc = new Scanner(System.in);
        if (args[0].equals("--profile=production")) {
            selectBootMenuProduct(sc);
        } else if (args[0].equals("--profile=dev")) {
            selectBootMenuDev(sc);
        } else {
            throw new IllegalArgumentException(BOOT_ERROR_MISTAKE_PARAMETER);
        }
        sc.close();
    }

    private void selectBootMenuProduct(Scanner sc) {
        while (!this.isExitBootMenu) {
            isExitMenu = false;
            System.out.println(BOOT_MENU_PRODUCT);
            if (sc.hasNextInt()) {
                caseMenuProduct(sc);
            } else {
                sc.nextLine();
                System.out.println("Введён некорректный параметр, повторите попытку");
            }
        }
    }

    private void selectBootMenuDev(Scanner sc) {
        while (!this.isExitBootMenu) {
            isExitMenu = false;
            System.out.println(BOOT_MENU_DEV);
            if (sc.hasNextInt()) {
                caseMenuDev(sc);
            } else {
                sc.nextLine();
                System.out.println("Введён некорректный параметр, повторите попытку");
            }
        }
    }

    private void caseMenuProduct(Scanner sc) {
        int menuSection = sc.nextInt();
        switch (menuSection) {
            case 1:
                addUser(sc);
                break;
            case 2:
                balanceUserByID(sc);
                break;
            case 3:
                addTransaction(sc);
                break;
            case 4:
                viewAllTransactionUser(sc);
                break;
            case 7:
                isExitBootMenu = true;
                break;
            default:
                System.out.println("Введён некорректный параметр, повторите попытку");
                break;
        }
    }

    private void caseMenuDev(Scanner sc) {
        int menuSection = sc.nextInt();
        switch (menuSection) {
            case 1:
                addUser(sc);
                break;
            case 2:
                balanceUserByID(sc);
                break;
            case 3:
                addTransaction(sc);
                break;
            case 4:
                viewAllTransactionUser(sc);
                break;
            case 5:
                removeUserTransaction(sc);
                break;
            case 6:
                viewNotValidTransaction(sc);
                break;
            case 7:
                isExitBootMenu = true;
                break;
            default:
                System.out.println("Введён некорректный параметр, повторите попытку");
                break;
        }
    }


    private void addUser(Scanner sc) {
        while (!isExitMenu) {
            System.out.println("Введите имя пользователя и баланс");
            String name = null;
            int balance = -1;
            int countInput = 0;
            boolean isValidInput = true;

            if (sc.hasNext()) {
                name = sc.next();
                ++countInput;
            }
            if (sc.hasNextInt()) {
                balance = sc.nextInt();
                ++countInput;
            }

            String temp = sc.nextLine();
            if (!temp.isEmpty() || countInput != 2) {
                System.out.printf("Ошибка: Количество параметров должно быть равно 2(имя и баланс).\n" +
                        "Баланс должен быть положительным целым числом.\n" +
                        "Повторите попытку и введите корректные данные.\n\n");
                isValidInput = false;
            }
            succesAddUser(isValidInput, name, balance);
        }
    }

    private void succesAddUser(boolean isValidInput, String name, int balance) {
        if (isValidInput) {
            try {
                User user = new User(name, balance);
                service.addUser(user);
                System.out.printf("Пользователь с ID = %d успешно добавлен\n", user.getID());
                System.out.println("---------------------------------------------------------");
                isExitMenu = true;
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }


    private void balanceUserByID(Scanner sc) {
        while (!isExitMenu) {
            System.out.println("Введите ID пользователя");
            int id = 0;
            int countInput = 0;
            boolean isValidInput = true;

            if (sc.hasNextInt()) {
                id = sc.nextInt();
                ++countInput;
            }

            String temp = sc.nextLine();
            if (!temp.isEmpty() || countInput != 1) {
                System.out.printf("Ошибка: Количество параметров должно быть равно 1 (ID пользователя).\n" +
                        "ID должно быть целым числом больше 0\n" +
                        "Повторите попытку и введите корректные данные.\n\n");
                isValidInput = false;
            }
            succesBalanceUser(isValidInput, id);
        }
    }

    private void succesBalanceUser(boolean isValidInput, int id) {
        if (isValidInput) {
            try {
                User user = service.getUserList().getUserById(id);
                System.out.printf("%s - %d\n", user.getName(), user.getBalance());
                System.out.println("---------------------------------------------------------");
                isExitMenu = true;
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }


    private void addTransaction(Scanner sc) {
        while (!isExitMenu) {
            System.out.println("Введите ID отправителя, ID получателя и сумму перевода");
            int sender = 0;
            int recepient = 0;
            int amountTransaction = 0;
            int countInput = 0;
            boolean isValidInput = true;

            if (sc.hasNextInt()) {
                sender = sc.nextInt();
                ++countInput;
            }
            if (sc.hasNextInt()) {
                recepient = sc.nextInt();
                ++countInput;
            }
            if (sc.hasNextInt()) {
                amountTransaction = sc.nextInt();
                ++countInput;
            }

            String temp = sc.nextLine();
            if (!temp.isEmpty() || countInput != 3) {
                System.out.printf("Ошибка: Количество параметров должно быть равно 3 (имя отправителя, имя получателя, сумма транзакции).\n" +
                        "сумма транзакции должно быть целым числом больше 0\n" +
                        "Повторите попытку и введите корректные данные.\n\n");
                isValidInput = false;
            }
            succesTransaction(isValidInput, sender, recepient, amountTransaction);
        }
    }

    private void succesTransaction(boolean isValidInput, int sender, int recepient, int amountTransaction) {
        if (isValidInput) {
            try {
                this.service.performTransfer(sender, recepient, amountTransaction);
                System.out.println("Перевод завершен");
                System.out.println("---------------------------------------------------------");
                isExitMenu = true;
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }


    private void viewAllTransactionUser(Scanner sc) {
        while (!isExitMenu) {
            System.out.println("Введите ID пользователя");
            int id = 0;
            int countInput = 0;
            boolean isValidInput = true;

            if (sc.hasNextInt()) {
                id = sc.nextInt();
                ++countInput;
            }

            String temp = sc.nextLine();
            if (!temp.isEmpty() || countInput != 1) {
                System.out.printf("Ошибка: Количество параметров должно быть равно 1 (ID пользователя).\n" +
                        "ID должно быть целым числом больше 0\n" +
                        "Повторите попытку и введите корректные данные.\n\n");
                isValidInput = false;
            }
            printAllTransactionUser(isValidInput, id);
        }
    }

    private void printAllTransactionUser(boolean isValidInput, int id) {
        if (isValidInput) {
            try {
                System.out.println(this.service.allUserTransaction(service.getUserList().getUserById(id)));
                System.out.println("---------------------------------------------------------");
                isExitMenu = true;
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }


    private void removeUserTransaction(Scanner sc) {
        System.out.println("Введите ID пользователя и UUID транзакции");
        String transactionID = null;
        int userID = 0;
        int countInput = 0;
        boolean isValidInput = true;

        if (sc.hasNextInt()) {
            userID = sc.nextInt();
            ++countInput;
        }
        if (sc.hasNext()) {
            transactionID = sc.next();
            ++countInput;
        }

        String temp = sc.nextLine();
        if (!temp.isEmpty() || countInput != 2) {
            System.out.printf("Ошибка: Количество параметров должно быть равно 1 (UUID транзакции).\n" +
                    "Повторите попытку и введите корректные данные.\n\n");
            isValidInput = false;
        }
        deleteUserTransaction(isValidInput, userID, UUID.fromString(transactionID));
    }

    private void deleteUserTransaction(boolean isValidInput, int userID, UUID transactionID) {
        if (isValidInput) {
            try {
                User user = service.getUserList().getUserById(userID);
                int amountTransaction = user.getTransactions().getAmountTransactionByUUID(transactionID);
                service.removedUserTransactionById(transactionID, userID);
                System.out.printf("Транзакция пользователя %s(id=%d) на сумму %d удалена\n", user.getName(), user.getID(), amountTransaction);
                System.out.println("---------------------------------------------------------");
                isExitMenu = true;
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private void viewNotValidTransaction(Scanner sc) {
        int userID = 0;
        boolean isValidInput = true;

        String temp = sc.nextLine();
        if (!temp.isEmpty()) {
            System.out.printf("Ошибка: опция запускается без параметров.\n" +
                    "Повторите попытку и введите корректные данные.\n\n");
            isValidInput = false;
        }
        checkValidTransaction(isValidInput);
    }

    private void checkValidTransaction(boolean isValidInput) {
        if (isValidInput) {
            try {
                System.out.println("Результат проверки:");
                Transaction[] notValidTransaction = service.searchNotValidTransactions();
                if (notValidTransaction != null) {
                    for (int i = 0; i < notValidTransaction.length; ++i) {
                        String sender = notValidTransaction[i].getSender();

                        UUID transactionID = notValidTransaction[i].getID();
                        String recipient = notValidTransaction[i].getRecipient();

                        int amountTransaction = notValidTransaction[i].getTransferAmount();

                        System.out.printf("%s имеет неподтвержденный перевод id = %s от %s на сумму %d\n",
                                sender, transactionID, recipient, amountTransaction);
                    }
                } else {
                    System.out.print("Невалидные транзакции отсутствуют");
                }
                System.out.println("\n---------------------------------------------------------");
                isExitMenu = true;
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

}
