//package ex05;

public class UserIdsGenerator {

    private static UserIdsGenerator instance = new UserIdsGenerator();
    private static int countID = 0;


    private UserIdsGenerator() {
    }


    public static UserIdsGenerator getInstance() {
        if (instance == null) {
            instance = new UserIdsGenerator();
        }
        return instance;
    }


    public int generateId() {
        return ++countID;
    }

}
