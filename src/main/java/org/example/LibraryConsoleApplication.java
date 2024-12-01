package org.example;

import org.example.commandHandlers.CommandHandler;
import org.example.service.LibraryService;
import org.example.util.Printer;

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
        CommandFactory commandFactory = new CommandFactory(printer);
        LibraryConsoleApplication app = new LibraryConsoleApplication();
        app.commandsProcess(libraryService, commandFactory);
    }

    /**
     * Обрабатывает команды пользователя из консоли
     * @param libraryService сервсис библиотеки
     */
    private void commandsProcess(LibraryService libraryService, CommandFactory commandFactory) {
        while (true) {
            String[] command = readCommand();
            if (command.length == 0)
                continue;

            CommandHandler commandHandler = commandFactory.getCommandHandler(command[0]);
            commandHandler.executeCommand(libraryService, command);
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