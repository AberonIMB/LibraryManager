package org.example;

import org.example.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        BookService bookService = new BookService();
        commandsProcess(bookService);
    }

    /**
     * Обрабатывает команды пользователя из консоли
     * @param bookService
     */
    private static void commandsProcess(BookService bookService) {
        while (true) {
            String[] command = readCommand();
            switch (command[0]) {
                case "add-book":
                    bookService.addBook(command[1], command[2], Integer.parseInt(command[3]));
                    break;
                case "list-book":
                    bookService.getListBooks();
                    break;
                case "edit-book":
                    bookService.editBook(Integer.parseInt(command[1]),
                            command[2],
                            command[3],
                            Integer.parseInt(command[4]));
                    break;
                case "delete-book":
                    bookService.deleteBook(Integer.parseInt(command[1]));
                    break;
//                case "get-book":
//                    bookService.getBook(Integer.parseInt(command[1]));
//                    break;
                case "help":
                    showHelp();
                    break;
                default:
                    showDefaultErrorMessage();
                    break;
            }
        }
    }

    /**
     * Выводит сообщение об ошибке при вводе несуществующей команды
     */
    private static void showDefaultErrorMessage() {
        //TODO
    }

    /**
     * Считывает команду пользователя и разбивает её на название и параметры
     * @return
     */
    private static String[] readCommand() {
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        List<String> args = new ArrayList<>();

        Pattern pattern = Pattern.compile("[\"“](.*?)[\"”]|(\\S+)");
        // выбирает либо выражения в ковычках, либо выражения без пробела
        Matcher matcher = pattern.matcher(command);
        while(matcher.find()) {
            String subString = matcher.group(1);
            if (subString != null) {
                args.add(subString);
            } else {
                args.add(matcher.group(2));
            }
        }

        return args.toArray(new String[0]);
    }

    /**
     * Выводит справку по всем командам
     */
    private static void showHelp() {
        //TODO
    }
}