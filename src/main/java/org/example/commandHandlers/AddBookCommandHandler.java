package org.example.commandHandlers;

import org.example.service.LibraryService;

/**
 * Обрабатывает команду добавления книги
 * Если команда корректна - добавляет книгу в библиотеку
 */
public class AddBookCommandHandler implements CommandHandler {

    @Override
    public void executeCommand(LibraryService libraryService, String[] command) {
        if (isCommandCorrect(libraryService, command)) {
            libraryService.addBook(command[1], command[2], Integer.parseInt(command[3]));
        }
    }

    /**
     * Проверяет, что параметры команды корректны
     * @param libraryService сервис библиотеки
     * @param command название команды и параметры
     * @return Если команда корректна - true, иначе false
     */
    private boolean isCommandCorrect(LibraryService libraryService, String[] command) {
        return libraryService.getSyntaxChecker().checkAddBookCommandSyntax(command);
    }
}