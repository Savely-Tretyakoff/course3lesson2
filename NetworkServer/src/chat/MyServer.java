package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import chat.handler.*;
import chat.handler.AuthService;

public class MyServer {

    private ServerSocket serverSocket;
    private  AuthService authService;
    private DBConnection dbConnection = new DBConnection();

    private final int PORT = 8189;
    private Socket socket;
    private List<ClientHandler> clients = new ArrayList<>();

    public MyServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.dbConnection = new DBConnection();
    }

    public AuthService getAuthService(){
        return authService;
    }



    public void Serever(){
        clients = new CopyOnWriteArrayList<>();

        if(!DBConnection.connect()){
            throw new RuntimeException("Не удалось подключиться к БД");
        }
        authService = new DBClients();

        try{
            serverSocket = new ServerSocket(PORT);
            System.out.println("серв запущен");

            while (true){
                socket = serverSocket.accept();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void start() throws IOException {
        System.out.println("Сервер запущен!");

        try {
            while (true) {
                waitAndProcessNewClientConnection();
            }
        } catch (IOException e) {
            System.out.println("Ошибка создания нового подключения");
            e.printStackTrace();
        } finally {
            serverSocket.close();
        }
    }

    private void waitAndProcessNewClientConnection() throws IOException {
        System.out.println("Ожидание пользователя...");
        Socket clientSocket = serverSocket.accept();
        System.out.println("Клиент подключился!");
        processClientConnection(clientSocket);
    }

    private void processClientConnection(Socket clientSocket) throws IOException {
        ClientHandler clientHandler = new ClientHandler(this, clientSocket);
        clientHandler.handle();
    }

//    public AuthService getAuthService() {
//        return dbConnection;
//    }

    public boolean isUsernameBusy(String clientUsername) {
        for (ClientHandler client : clients) {
            if (client.getClientUsername().equals(clientUsername)) {
                return true;
            }
        }
        return false;
    }

    public void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);

    }

//    private List<String> getAllUsernames() {
//        List<String> usernames = new ArrayList<>();
//        for (ClientHandler client : clients) {
//            usernames.add(client.getClientUsername());
//        }
//        return usernames;
//    }

    public void unSubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    public void broadcastMessage(String s, ClientHandler sender) throws IOException {
        for (ClientHandler client : clients) {
            if (client == sender) {
                continue;
            }
            client.sendMessage(s, sender.getClientUsername());
        }
    }

    public void broadcastPrivateMessage(String s) throws IOException {


    }

    public void privateMassage(ClientHandler from, String nickTo, String msg) throws IOException {
        for (ClientHandler o : clients) {
            if (o.getClientUsername().equals(nickTo)) {
                o.sendMessage(msg, from.getClientUsername());
                from.sendMessage(msg, from.getClientUsername());
                return;
            }
        }
    }
}