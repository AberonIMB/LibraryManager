package org.example;

import org.example.service.BookService;

import java.util.Scanner;

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

    }

    /**
     * Считывает команду пользователя и разбивает её на название и параметры
     * @return
     */
    private static String[] readCommand() {
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        return command.split(" ");
    }

    /**
     * Выводит справку по всем командам
     */
    private static void showHelp() {

    }
}