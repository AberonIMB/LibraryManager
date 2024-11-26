package org.example.commandHandlers;

import org.example.service.LibraryService;

/**
 * Обрабатывает команду удаления книги
 * Если команда корректна - удаляет книгу из библиотеки
 */
public class DeleteBookCommandHandler implements CommandHandler {
    @Override
    public void executeCommand(LibraryService libraryService, String[] command) {
        if (isCommandCorrect(libraryService, command)) {
            libraryService.deleteBook(Long.parseLong(command[1]));
        }
    }

    /**
     * Проверяет, что параметры команды корректны
     * @param libraryService сервис библиотеки
     * @param command название команды и параметры
     * @return true, если команда корректна, иначе false
     */
    private boolean isCommandCorrect (LibraryService libraryService, String[] command) {
        return libraryService.getSyntaxChecker().checkDeleteBookCommandSyntax(command);
    }
}
