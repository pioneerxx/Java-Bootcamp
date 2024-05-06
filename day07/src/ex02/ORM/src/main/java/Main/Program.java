package Main;

import Models.User;
import ORM.OrmManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

class Program {
    public static void main(String[] args) {
        try {
            OrmManager manager = new OrmManager(DBConnector.getDataSource().getConnection());
            manager.createTable();

            User user1 = new User(1L, "Ali", "Devletukaev", 22);
            User user2 = new User(2L, "Timur", "Fadeev", 20);
            User user3 = new User(3L, "Peter", "Griffin", 52);
            manager.save(user1);
            manager.save(user2);
            manager.save(user3);

            User userUpdated = new User(1L, "Alishbiy", "Devletukaev", 22);
            manager.update(userUpdated);

            User userFound = manager.findById(1L, User.class);
            System.out.println(userFound.toString());
        } catch (SQLException | IllegalAccessException | InstantiationException | NoSuchMethodException |
                 InvocationTargetException e) {
            System.err.println(e.getMessage());
        }
    }
}
