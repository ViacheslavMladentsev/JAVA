//package ex05;

public class UsersArrayList implements UsersList {

    private int sizeList;
    private int coutUser;
    private User[] currentArray;

    public UsersArrayList() {
        this.sizeList = 10;
        this.coutUser = 0;
        currentArray = new User[sizeList];
    }


    @Override
    public String toString() {
        String result = "Размер списка = " + this.sizeList + "\n" +
                "Количество записей = " + this.coutUser + "\n";
        for (int i = 0; i < this.coutUser; ++i) {
            result = result.concat(this.currentArray[i] + "\n");
        }


        return result;
    }


    @Override
    public void addUser(User user) {
        resizeArray();
        this.currentArray[this.coutUser] = user;
        ++coutUser;
    }

    @Override
    public User getUserById(int id) {
        boolean isThrow = true;
        int i = 0;
        for (; i < this.coutUser; ++i) {
            if (this.currentArray[i].getID() == id) {
                isThrow = false;
                break;
            }
        }
        if (isThrow) {
            throw new UserNotFoundException("Ошибка: запрашиваемый идентификатор " + id + " не найден");
        }
        return this.currentArray[i];
    }

    @Override
    public User getUserByIndex(int index) {
        checkedExceptionIndex(index);
        return this.currentArray[index];
    }

    @Override
    public int getNumberOfUser() {
        return this.coutUser;
    }


    private void resizeArray() {
        if (this.currentArray.length == coutUser) {
            User[] tempArray = new User[this.currentArray.length * 2];
            System.arraycopy(this.currentArray, 0, tempArray, 0, this.currentArray.length);
            this.currentArray = tempArray;
            this.sizeList *= 2;
        }
    }

    private void checkedExceptionIndex(int index) {
        if ((index >= this.currentArray.length) || (index > this.coutUser) || (index < 0)) {
            throw new ArrayIndexOutOfBoundsException("Ошибка: запрашиваемый " + index + " находится за пределами массива");
        }
    }

}
