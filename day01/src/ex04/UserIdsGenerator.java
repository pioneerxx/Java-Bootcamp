package ex04;

public class UserIdsGenerator {
    
    private static UserIdsGenerator instance;
    private UserIdsGenerator() {}
    static {
        instance = null;
    }
    private static Integer id = 0;
    public Integer generateId() {
        return ++id;
    }
    public static UserIdsGenerator getInstance() {
        if (instance == null) {
            instance = new UserIdsGenerator();
        }
        return instance;
    }
}

