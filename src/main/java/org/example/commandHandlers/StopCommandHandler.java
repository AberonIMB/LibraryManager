package org.example.commandHandlers;

import org.example.Command;
import org.example.commandValidators.CommandValidator;
import org.example.util.IOHandler;

/**
 * Обрабатывает команду остановки программы
 */
public class StopCommandHandler implements CommandHandler {
    private final CommandValidator commandValidator;
    private final IOHandler ioHandler;

    /**
     * Конструктор, который задает все необходимые поля
     */
    public StopCommandHandler(CommandValidator commandValidator, IOHandler ioHandler) {
        this.commandValidator = commandValidator;
        this.ioHandler = ioHandler;
    }

    @Override
    public void executeCommand(Command command) {
        if (commandValidator.validateCommand(command)) {
            ioHandler.print("Остановка работы сервиса.");
            System.exit(0);
        }
    }
}
