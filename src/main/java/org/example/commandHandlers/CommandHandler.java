package org.example.commandHandlers;

import org.example.Command;

/**
 * Обработчик команды
 */
public interface CommandHandler {
    /**
     * Выполняет команду
     */
    void executeCommand(Command command);
}
