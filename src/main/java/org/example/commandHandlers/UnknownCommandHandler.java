package org.example.commandHandlers;

import org.example.Command;
import org.example.util.IOHandler;

/**
 * Обрабатывает неизвестную команду
 */
public class UnknownCommandHandler implements CommandHandler {
    private final IOHandler ioHandler;

    public UnknownCommandHandler(IOHandler ioHandler) {
        this.ioHandler = ioHandler;
    }

    @Override
    public void executeCommand(Command command) {
        ioHandler.print("Введена несуществующая команда.");
    }
}