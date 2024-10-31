package org.example;

import org.example.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс для запуска прилоежния
 */
public class LibraryConsoleApplication {

    /**
     * Метод запуска приложенения
     */
    public static void main(String[] args) {
        BookService bookService = new BookService();
        LibraryConsoleApplication app = new LibraryConsoleApplication();
        app.commandsProcess(bookService);
    }

    /**
     * Обрабатывает команды пользователя из консоли
     *
     * @param bookService сервсис обработки книг
     */
    private void commandsProcess(BookService bookService) {
        while (true) {
            String[] command = readCommand();
            if (command.length == 0)
                continue;
            switch (command[0]) {
                case "add-book":
                    if (command.length != 4 || !command[3].matches("\\d+")) {
                        showDefaultErrorMessage();
                        continue;
                    }
                    bookService.addBook(command[1], command[2], Integer.parseInt(command[3]));
                    break;
                case "list-books":
                    bookService.printBooks();
                    break;
                case "edit-book":
                    if (command.length != 5
                            || !command[1].matches("\\d+")
                            || !command[4].matches("\\d+")) {
                        showDefaultErrorMessage();
                        continue;
                    }
                    bookService.editBook(Integer.parseInt(command[1]),
                            command[2],
                            command[3],
                            Integer.parseInt(command[4]));
                    break;
                case "delete-book":
                    if (command.length != 2 || !command[1].matches("\\d+")) {
                        showDefaultErrorMessage();
                        continue;
                    }
                    bookService.deleteBook(Integer.parseInt(command[1]));
                    break;
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
        System.out.println("Некоректный ввод команды");
    }

    /**
     * Считывает команду пользователя и разбивает её на название и параметры
     */
    private static String[] readCommand() {
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        List<String> args = new ArrayList<>();

        Pattern pattern = Pattern.compile("[\"“](.*?)[\"”]|(\\S+)");
        // выбирает либо выражения в ковычках, либо выражения без пробела
        Matcher matcher = pattern.matcher(command);
        while (matcher.find()) {
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
    private void showHelp() {
        System.out.println("""
                Доступные команды:
                \t- add-book "<название>" "<автор>" <год издания> – Добавить книгу
                \t- list-books – Просмотреть список книг
                \t- edit-book <ID книги> “<название>” “<автор>” <год издания> - Изменить книгу
                \t- delete-book <ID книги> – Удалить книгу
                \t- help – Справка""");
    }
}