package ex03;

class UserNotFoundException extends RuntimeException {}

public class UsersArrayList implements UsersList {
    private User[] Users;
    private Integer UserAmount;

    public UsersArrayList() {
        UserAmount = 0;
    }

    @Override
    public void AddUser(User user) {
        UserAmount++;
        User[] tmp = new User[UserAmount];
        for (int i = 0; i < UserAmount - 1; i++) {
            tmp[i] = Users[i];
        }
        tmp[UserAmount - 1] = user;
        Users = tmp;
    }

    @Override
    public User GetUserById(Integer id) {
        User res = null;
        for (User user : Users) {
            if (user.getIdentifier() == id) {
                res = user;
                break;
            }
        }
        if (res == null) {
            throw new UserNotFoundException();
        }
        return res;
    }

    @Override
    public User GetUserByIndex(Integer index) {
        User res = null;
        if (index >= 0 && index < UserAmount) {
            res = Users[index];
        } else {
            throw new UserNotFoundException();
        }
        return res;
    }

    @Override
    public Integer GetUserAmount() {
        return UserAmount;
    }
}
