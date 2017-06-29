package com.javarush.task.task30.task3008.client;

import com.javarush.task.task30.task3008.Connection;
import com.javarush.task.task30.task3008.ConsoleHelper;
import com.javarush.task.task30.task3008.Message;
import com.javarush.task.task30.task3008.MessageType;

import java.io.IOException;
import java.net.Socket;

public class Client {
    protected Connection connection;
    private volatile boolean clientConnected;

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

    public class SocketThread extends Thread {

        public void run () {
            try {
                Socket socket = new Socket(getServerAddress(), getServerPort());
                Client.this.connection = new Connection(socket);
                clientHandshake();
                clientMainLoop();
            } catch (IOException|ClassNotFoundException e) {
                notifyConnectionStatusChanged(false);
            }
        }
        protected void processIncomingMessage(String message) { //должен выводить текст message в консоль.
            ConsoleHelper.writeMessage(message);
        }
        protected void informAboutAddingNewUser(String userName) {
            ConsoleHelper.writeMessage(String.format("Участник с именем %s присоединился к чату.", userName));
         } //– должен выводить в консоль информацию о том, что участник с именем userName присоединился к чату.
        protected void informAboutDeletingNewUser(String userName) {
              ConsoleHelper.writeMessage(String.format("Участник с именем %s покинул чат.", userName));
          } //– должен выводить в консоль, что участник с именем userName покинул чат.
        protected void notifyConnectionStatusChanged(boolean clientConnected) {
            Client.this.clientConnected = clientConnected;
            synchronized (Client.this) {
                Client.this.notify();
            }
           } //– этот метод должен:
//        а) Устанавливать значение поля clientConnected внешнего объекта Client в соответствии с переданным параметром.
//        б) Оповещать (пробуждать ожидающий) основной поток класса Client.
        protected void clientHandshake() throws IOException, ClassNotFoundException {
            while (true) {
                Message message = connection.receive();
                if (message.getType() == MessageType.NAME_REQUEST) {
                    String name = getUserName();
                    connection.send(new Message(MessageType.USER_NAME, name));
                } else
                if (message.getType() == MessageType.NAME_ACCEPTED) {
                    notifyConnectionStatusChanged(true);
                    return;
                } else {
                    throw new IOException("Unexpected MessageType");
                }
            }
        }
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            while (true) {
                Message message = connection.receive();
                if (message.getType() == MessageType.TEXT) {
                    processIncomingMessage(message.getData());
                } else
                if (message.getType() == MessageType.USER_ADDED) {
                    informAboutAddingNewUser(message.getData());
                } else
                if (message.getType() == MessageType.USER_REMOVED) {
                    informAboutDeletingNewUser(message.getData());
                } else {
                    throw new IOException("Unexpected MessageType");
                }
            }
        }
    }
    public void run() {
        SocketThread socketThread = getSocketThread();
        socketThread.setDaemon(true);
        socketThread.start();
        try {
            synchronized (this) {
                this.wait();
            }
        } catch (Exception e) {
            ConsoleHelper.writeMessage("Произошла ошибка");
            return;
        }
        if (clientConnected) {
            ConsoleHelper.writeMessage("Соединение установлено. Для выхода наберите команду ‘exit’.");

        while (clientConnected) {
            String message = ConsoleHelper.readString();
            if (message.equals("exit")) {
                return;
            }
            if (shouldSendTextFromConsole()) {
                sendTextMessage(message);
            }
        }
        } else ConsoleHelper.writeMessage("Произошла ошибка во время работы клиента.");

    }
    protected String getServerAddress() {
        return ConsoleHelper.readString();
    }
//    – должен запросить ввод адреса сервера у пользователя и вернуть введенное значение.
//    Адрес может быть строкой, содержащей ip, если клиент и сервер запущен на разных машинах или ‘localhost’,
//    если клиент и сервер работают на одной машине.
     protected int getServerPort() {
        return ConsoleHelper.readInt();
    }
//    – должен запрашивать ввод порта сервера и возвращать его.
     protected String getUserName() {
        return ConsoleHelper.readString();
    }
//    – должен запрашивать и возвращать имя пользователя.
     protected boolean shouldSendTextFromConsole() {
        return true;
     }
//     – в данной реализации клиента всегда должен возвращать true (мы всегда отправляем текст введенный в консоль).
// Этот метод может быть переопределен, если мы будем писать какой-нибудь другой клиент, унаследованный от нашего,
// который не должен отправлять введенный в консоль текст.
      protected SocketThread getSocketThread() {
        return new SocketThread();
      }
//      – должен создавать и возвращать новый объект класса SocketThread.
       protected void sendTextMessage(String text) {
            try {
                connection.send(new Message(MessageType.TEXT, text));
            } catch (IOException e) {
                ConsoleHelper.writeMessage("Произошло исключение");
                clientConnected = false;
            }
       }
//       – создает новое текстовое сообщение, используя переданный текст и отправляет его серверу через соединение connection.
//    Если во время отправки произошло исключение IOException, то необходимо вывести
//    информацию об этом пользователю и присвоить false полю clientConnected.


}
