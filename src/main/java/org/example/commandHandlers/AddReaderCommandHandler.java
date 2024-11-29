package org.example.commandHandlers;

import org.example.service.LibraryService;

/**
 * Обрабатывает команду добавления читателя
 * Если команда корректна - добавляет читателя в библиотеку
 */
public class AddReaderCommandHandler implements CommandHandler{
    @Override
    public void executeCommand(LibraryService libraryService, String[] command) {
        if (isCommandCorrect(libraryService, command)) {
            libraryService.addReader(command[1]);
        }
    }

    /**
     * Проверяет, что параметры команды корректны
     * @param libraryService сервис библиотеки
     * @param command название команды и параметры
     * @return Если команда корректна - true, иначе false
     */
    private boolean isCommandCorrect(LibraryService libraryService, String[] command) {
        return libraryService.getSyntaxChecker().checkAddReaderCommandSyntax(command);
    }
}
