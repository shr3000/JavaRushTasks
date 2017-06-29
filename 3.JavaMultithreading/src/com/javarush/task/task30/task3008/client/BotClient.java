package com.javarush.task.task30.task3008.client;

import com.javarush.task.task30.task3008.ConsoleHelper;
import com.javarush.task.task30.task3008.Message;
import com.javarush.task.task30.task3008.MessageType;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class BotClient extends Client {

        public static void main(String[] args) {
            BotClient botClient = new BotClient();
            botClient.run();
        }
        public class BotSocketThread extends SocketThread {
            @Override
            protected void clientMainLoop() throws IOException, ClassNotFoundException {
                sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
                super.clientMainLoop();
            }

            @Override
            protected void processIncomingMessage(String message) {
                ConsoleHelper.writeMessage(message);
                String name = "";
                String messageText;
                if (message.contains(": ")) {
                    name = message.substring(0, message.indexOf(": "));
                    messageText = message.substring(message.indexOf(": ") + 2);
                } else messageText = message;

                String answer = "";
                 if ("дата".equalsIgnoreCase(messageText)) answer = "d.MM.YYYY";
                 else if ("день".equalsIgnoreCase(messageText)) answer = "d";
                 else if ("месяц".equalsIgnoreCase(messageText)) answer = "MMMM";
                 else if ("год".equalsIgnoreCase(messageText)) answer = "YYYY";
                 else if ("время".equalsIgnoreCase(messageText)) answer = "H:mm:ss";
                 else if ("час".equalsIgnoreCase(messageText)) answer = "H";
                 else if ("минуты".equalsIgnoreCase(messageText)) answer = "m";
                 else if ("секунды".equalsIgnoreCase(messageText)) answer = "s";


                 if (!answer.isEmpty()){
                     SimpleDateFormat dateFormat = new SimpleDateFormat(answer);

                     sendTextMessage(String.format("Информация для %s: %s",
                             name, dateFormat.format(Calendar.getInstance().getTime())));
                 }

            }
        }

        @Override
        protected SocketThread getSocketThread() {
            return new BotSocketThread();
        }

        @Override
        protected boolean shouldSendTextFromConsole() {
            return false;
        }

        @Override
        protected String getUserName() {
            return String.format("date_bot_%s", (int)(Math.random()*100));
        }
    }
