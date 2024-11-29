package org.example.commandHandlers;

import org.example.service.LibraryService;

/**
 * Обрабатывает команду выдачи книги читателю
 * Если команда корректна - добавляет читателя в библиотеку
 */
public class CheckoutBookCommandHandler implements CommandHandler {
    @Override
    public void executeCommand(LibraryService libraryService, String[] command) {
        if (isCommandCorrect(libraryService, command)) {
            libraryService.checkoutBook(Long.parseLong(command[1]),
                    Long.parseLong(command[2]));
        }
    }

    /**
     * Проверяет, что параметры команды корректны
     * @param libraryService сервис библиотеки
     * @param command название команды и параметры
     * @return true, если команда корректна, иначе false
     */

    private boolean isCommandCorrect(LibraryService libraryService, String[] command) {
        return libraryService.getSyntaxChecker().checkCheckoutCommand(command);
    }
}
