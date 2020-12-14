package chat.handler;

public interface AuthService {
    String getNicknameByLoginAndPassword(String login, String password);
}
