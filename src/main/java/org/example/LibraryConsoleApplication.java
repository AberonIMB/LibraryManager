package org.example;

import org.example.commandHandlers.CommandHandler;
import org.example.service.LibraryService;
import org.example.util.IOConsoleHandler;
import org.example.util.IOHandler;

/**
 * Класс для запуска приложения
 */
public class LibraryConsoleApplication {
    private final LibraryService libraryService;
    private final CommandFactory commandFactory;
    private final IOHandler ioHandler;

    public LibraryConsoleApplication() {
        this.ioHandler = new IOConsoleHandler();
        this.libraryService = new LibraryService();
        this.commandFactory = new CommandFactory(ioHandler, libraryService);
    }

    /**
     * Метод запуска приложения
     */
    public static void main(String[] args) {
        LibraryConsoleApplication app = new LibraryConsoleApplication();
        app.start();
    }

    /**
     * Обрабатывает команды пользователя из консоли
     */
    private void start() {
        while (true) {
            Command command = readCommand();
            if (command.getName() == null)
                continue;

            CommandHandler commandHandler = commandFactory.getCommandHandler(command);
            commandHandler.executeCommand(command);
        }
    }

    /**
     * Считывает команду пользователя
     */
    private Command readCommand() {
        String commandString = ioHandler.readInput();
        return new Command(commandString);
    }
}