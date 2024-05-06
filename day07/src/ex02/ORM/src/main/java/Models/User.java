package Models;

import ORM.OrmColumn;
import ORM.OrmColumnId;
import ORM.OrmEntity;

@OrmEntity(table = "simple_user")
public class User {
    public User(Long id, String firstName, String lastName, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
    public User() {}
    @OrmColumnId
    private Long id;
    @OrmColumn(name = "first_name", length = 20)
    private String firstName;
    @OrmColumn(name = "last_name", length = 20)
    private String lastName;
    @OrmColumn(name = "age")
    private Integer age;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }
}
