package org.example;

import org.example.service.LibraryService;
import org.example.util.Printer;
import org.example.util.SyntaxChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс для запуска приложения
 */
public class LibraryConsoleApplication {

    /**
     * Метод запуска приложения
     */
    public static void main(String[] args) {
        Printer printer = new Printer();
        LibraryService libraryService = new LibraryService(printer);
        LibraryConsoleApplication app = new LibraryConsoleApplication();
        app.commandsProcess(libraryService, printer);
    }

    /**
     * Обрабатывает команды пользователя из консоли
     * @param libraryService сервсис библиотеки
     */
    private void commandsProcess(LibraryService libraryService, Printer printer) {
        SyntaxChecker syntaxChecker = new SyntaxChecker();
        while (true) {
            String[] command = readCommand();
            if (command.length == 0)
                continue;
            switch (command[0]) {
                case "add-book":
                    if (!syntaxChecker.checkAddBookCommandSyntax(command)) {
                        continue;
                    }
                    libraryService.addBook(command[1], command[2], Integer.parseInt(command[3]));
                    break;
                case "list-books":
                    libraryService.printBooks();
                    break;
                case "edit-book":
                    if (!syntaxChecker.checkEditBookCommandSyntax(command)) {
                        continue;
                    }
                    libraryService.editBook(Integer.parseInt(command[1]),
                            command[2],
                            command[3],
                            Integer.parseInt(command[4]));
                    break;
                case "delete-book":
                    if (!syntaxChecker.checkDeleteBookCommandSyntax(command)) {
                        continue;
                    }
                    libraryService.deleteBook(Integer.parseInt(command[1]));
                    break;
                case "help":
                    printer.printHelp();
                    break;
                default:
                    printer.printWrongCommand();
            }
        }
    }

    /**
     * Считывает команду пользователя и разбивает её на название и параметры
     */
    private String[] readCommand() {
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
}