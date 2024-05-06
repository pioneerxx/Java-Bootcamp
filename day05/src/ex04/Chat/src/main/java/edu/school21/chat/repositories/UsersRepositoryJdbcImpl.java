package edu.school21.chat.repositories;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private Connection connection;
    public UsersRepositoryJdbcImpl(DataSource dataSource) throws SQLException {
        connection = dataSource.getConnection();
    }

    @Override
    public List<User> findAll(int page, int size) throws SQLException {
        List<User> userList = new ArrayList<>();
        List<Chatroom> chatroomList = new ArrayList<>();
        User tmpUser = null;
        Chatroom tmpChatroom = null;
        PreparedStatement preparedStatement = connection.prepareStatement("WITH created_rooms AS (\n" +
                "\tSELECT chat.users.id AS user_id, chat.chatrooms.name AS created_name, chat.chatrooms.id AS created_id\n" +
                "\tFROM chat.chatrooms\n" +
                "\tLEFT JOIN chat.users ON chat.chatrooms.owner = chat.users.id\n" +
                "), used_rooms AS (\n" +
                "\tSELECT chat.users.id AS user_id, chat.chatrooms.id AS used_id, chat.chatrooms.name AS used_name\n" +
                "\tFROM chat.messages\n" +
                "\tLEFT JOIN chat.users ON chat.users.id = chat.messages.author\n" +
                "\tLEFT JOIN chat.chatrooms ON chat.chatrooms.id = chat.messages.room\n" +
                ")\n" +
                "\n" +
                "SELECT DISTINCT chat.users.id, chat.users.login, chat.users.password, created_rooms.created_id, created_rooms.created_name, used_rooms.used_id, used_rooms.used_name\n" +
                "FROM chat.users\n" +
                "FULL JOIN created_rooms ON chat.users.id = created_rooms.user_id\n" +
                "FULL JOIN used_rooms ON chat.users.id = used_rooms.user_id LIMIT " + size + " OFFSET " + page * size);
        ResultSet set = preparedStatement.executeQuery();
        while (set.next()) {
            tmpUser = findUserById(userList, set.getLong("id"));
            if (tmpUser == null) {
                userList.add(new User(set.getLong("id"), set.getString("login"),
                        set.getString("password"), new ArrayList<Chatroom>(), new ArrayList<Chatroom>()));
                tmpUser = userList.get(userList.size() - 1);
            }
            tmpChatroom = findChatroomById(chatroomList, set.getLong("created_id"));
            if (tmpChatroom == null) {
                tmpUser.getCreatedChatroomList().add(new Chatroom(set.getLong("created_id"),
                        set.getString("created_name"), tmpUser, new ArrayList<Message>()));
                tmpChatroom = findChatroomById(tmpUser.getCreatedChatroomList(), set.getLong("created_id"));
                chatroomList.add(tmpChatroom);
            } else if (findChatroomById(tmpUser.getCreatedChatroomList(), tmpChatroom.getId()) == null) {
                tmpUser.getCreatedChatroomList().add(tmpChatroom);
            }
            tmpChatroom = findChatroomById(chatroomList, set.getLong("used_id"));
            if (tmpChatroom == null) {
                tmpUser.getUsedChatroomList().add(new Chatroom(set.getLong("used_id"),
                        set.getString("used_name"), tmpUser, new ArrayList<Message>()));
                tmpChatroom = findChatroomById(tmpUser.getUsedChatroomList(), set.getLong("used_id"));
                chatroomList.add(tmpChatroom);
            } else if (findChatroomById(tmpUser.getUsedChatroomList(), tmpChatroom.getId()) == null) {
                tmpUser.getUsedChatroomList().add(tmpChatroom);
            }
        }
        return userList;
    }

    private User findUserById(List<User> userList, Long id) {
        User result = null;
        for (User user : userList) {
            if (user.getId() == id) {
                result = user;
                break;
            }
        }
        return result;
    }
    private Chatroom findChatroomById(List<Chatroom> chatroomList, Long id) {
        Chatroom result = null;
        for (Chatroom room : chatroomList) {
            if (room.getId() == id) {
                result = room;
                break;
            }
        }
        return result;
    }


}
