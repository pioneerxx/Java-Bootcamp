package ex02;


class Program {
    public static void main(String args[]) {
        UsersArrayList list = new UsersArrayList();
        list.AddUser(new User("John", 500));
        list.AddUser(new User("Mike", 240));
        System.out.println(list.GetUserById(1));
        System.out.println(list.GetUserByIndex(0));
        System.out.println(list.GetUserAmount());
    }
}