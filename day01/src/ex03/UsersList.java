package ex03;

public interface UsersList {
    void AddUser(User user);
    User GetUserById(Integer id);
    User GetUserByIndex(Integer Index);
    Integer GetUserAmount();
}
