package org.example.commandHandlers;

import org.example.service.LibraryService;

/**
 * Обрабатывает команду редактирования книги
 * Если команда корректна - редактирует книгу
 */
public class EditBookCommandHandler implements CommandHandler {

    @Override
    public void executeCommand(LibraryService libraryService, String[] command) {
        if (isCommandCorrect(libraryService, command)) {
            libraryService.editBook(Long.parseLong(command[1]),
                    command[2],
                    command[3],
                    Integer.parseInt(command[4]));
        }
    }

    /**
     * Проверяет, что параметры команды корректны
     * @param libraryService сервис библиотеки
     * @param command название команды и параметры
     * @return true, если команда корректна, иначе false
     */
    private boolean isCommandCorrect(LibraryService libraryService, String[] command) {
        return libraryService.getSyntaxChecker().checkEditBookCommandSyntax(command);
    }
}
