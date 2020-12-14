package chat.handler;

public class DBClients implements AuthService{
    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        return DBConnection.getNicknameByLoginAndPassword(login, password);
    }
}
