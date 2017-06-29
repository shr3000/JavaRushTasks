package com.javarush.task.task30.task3008;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    public static void main(String[] args) {
            try (ServerSocket serverSocket = new ServerSocket(ConsoleHelper.readInt())) {
                ConsoleHelper.writeMessage("Сервер запущен.");
                while (true) {
                    Handler handler = new Handler(serverSocket.accept());
                    handler.start();
                }
            }
        catch (Exception e) {
            ConsoleHelper.writeMessage("Произошла ошибка сервера.");
        }
    }
    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    static void sendBroadcastMessage(Message message) {
        for (Map.Entry<String, Connection> pair: connectionMap.entrySet()) {
            try {
                pair.getValue().send(message);
            }catch (IOException e) {
                ConsoleHelper.writeMessage("Cообщение не отправлено для " + pair.getKey());
            }
        }
    }
    private static class Handler extends Thread{
        Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        public void run () {
            ConsoleHelper.writeMessage("Установлено новое соединение с удаленным адресом " + socket.getRemoteSocketAddress());
            String newUser = null;
            try (Connection connection = new Connection(socket)){

                newUser = serverHandshake(connection);
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, newUser));
                sendListOfUsers(connection, newUser);
                serverMainLoop(connection, newUser);
            } catch (IOException|ClassNotFoundException e) {
                ConsoleHelper.writeMessage("Произошла ошибка при обмене данными с удаленным адресом");
            }
            if (newUser != null) {
                connectionMap.remove(newUser);
                sendBroadcastMessage(new Message(MessageType.USER_REMOVED, newUser));
            }
            ConsoleHelper.writeMessage("Соединение с сервером закрыто.");

        }
        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            while (true) {
                connection.send(new Message(MessageType.NAME_REQUEST));
                Message message = connection.receive();
                if (message.getType().equals(MessageType.USER_NAME)) {
                    String userName = message.getData();
                    if (!userName.isEmpty() && !connectionMap.containsKey(userName)) {
                        connectionMap.put(userName, connection);
                        connection.send(new Message(MessageType.NAME_ACCEPTED));
                        return userName;
                    }
                }
            }
        }
        private void sendListOfUsers(Connection connection, String userName) throws IOException {
            for (Map.Entry<String, Connection> pair: connectionMap.entrySet()) {
                if (!pair.getKey().equals(userName)) {
                    connection.send(new Message(MessageType.USER_ADDED, pair.getKey()));
                }
            }
        }
        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            while (true) {
                Message message = connection.receive();
                if (message.getType() == MessageType.TEXT){
                    sendBroadcastMessage(new Message(MessageType.TEXT,userName + ": " + message.getData()));
                } else {
                    ConsoleHelper.writeMessage("Ошибка сообщения.");
                }
            }
        }
    }
}
