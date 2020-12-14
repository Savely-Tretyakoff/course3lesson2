package chat.auth;

import chat.User;
import chat.handler.ClientHandler;
import chat.handler.DBConnection;

import java.util.ArrayList;
import java.util.List;


public class BaseAuthService implements AuthService {

//    private static final List<User> clients = List.of(
//            new User("user1", "1111", "Борис"),
//            new User("user2", "2222", "Тимофей"),
//            new User("user3", "3333", "Мартин")
//    );


    @Override
    public String getUsernameByLoginAndPassword(String login, String password) {
        return DBConnection.getNicknameByLoginAndPassword(login, password);
    }
}
