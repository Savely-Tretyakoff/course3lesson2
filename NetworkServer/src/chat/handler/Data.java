package chat.handler;

public class Data {
    public String login;
    public String password;


    public Data(String login, String password){
        this.login = login;
        this.password = password;
    }
    public String Returner(){
        return String.format(this.password, this.login);
    }
}
