package com.javarush.task.task31.task3110;

import com.javarush.task.task31.task3110.exception.WrongZipFileException;

import java.io.*;

public class Archiver {
    public static void main(String[] args) throws Exception{
        Operation operation;

        while (true) {

            try {
                operation = askOperation();
                CommandExecutor.execute(operation);
                if (operation.ordinal() == Operation.EXIT.ordinal()) break;
            } catch (WrongZipFileException e) {
                ConsoleHelper.writeMessage("Вы не выбрали файл архива или выбрали неверный файл.");
            } catch (Exception e) {
                ConsoleHelper.writeMessage("Произошла ошибка. Проверьте введенные данные.");
            }
        }
    }

    public static Operation askOperation() throws IOException {
        String oper = "Выберите операцию:\n" +
                Operation.CREATE.ordinal() + " - упаковать файлы в архив\n" +
                Operation.ADD.ordinal() + " - добавить файл в архив\n" +
                Operation.REMOVE.ordinal() + " - удалить файл из архива\n" +
                Operation.EXTRACT.ordinal() + " - распаковать архив\n" +
                Operation.CONTENT.ordinal() + " - просмотреть содержимое архива\n" +
                Operation.EXIT.ordinal() + " – выход";
        ConsoleHelper.writeMessage(oper);
        Operation [] operations = Operation.values();
        return operations[ConsoleHelper.readInt()];
    }
}
