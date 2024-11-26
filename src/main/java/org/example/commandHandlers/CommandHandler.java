package org.example.commandHandlers;

import org.example.service.LibraryService;

/**
 * Обработчик команды
 */
public interface CommandHandler {
    /**
     * Выполняет команду
     * @param libraryService Сервис библиотеки
     * @param command название команды и параметры
     */
    void executeCommand(LibraryService libraryService, String[] command);
}
