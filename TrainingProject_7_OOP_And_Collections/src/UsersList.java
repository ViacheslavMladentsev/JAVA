//package ex05;

public interface UsersList {
    void addUser(User user);

    User getUserById(int id) throws Exception;

    User getUserByIndex(int index) throws Exception;

    int getNumberOfUser();

}
