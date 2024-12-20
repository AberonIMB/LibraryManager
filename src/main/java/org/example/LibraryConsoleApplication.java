package org.example;

import org.example.commandHandlers.CommandHandler;
import org.example.service.LibraryService;
import org.example.util.IOConsoleHandler;
import org.example.util.IOHandler;

/**
 * Класс для запуска приложения
 */
public class LibraryConsoleApplication {
    private CommandFactory commandFactory;
    private final IOHandler ioHandler;

    /**
     * Конструктор задающий IOHandler, CommandFactory
     */
    public LibraryConsoleApplication() {
        this(new IOConsoleHandler(), null);
        this.commandFactory = new CommandFactory(ioHandler, new LibraryService());
    }

    /**
     * Конструктор для тестов
     */
    public LibraryConsoleApplication(IOHandler ioHandler, CommandFactory commandFactory) {
        this.ioHandler = ioHandler;
        this.commandFactory = commandFactory;
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
    public void start() {
        while (true) {
            Command command = readCommand();
            if (command.getName() == null)
                continue;

            //Убрали обработчик для команды "stop", чтобы можно было протестировать класс
            if (command.getName().equals("stop")) {
                ioHandler.print("Завершение работы.");
                break;
            }

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