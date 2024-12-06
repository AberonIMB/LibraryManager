package org.example.commandHandlers;

import org.example.Command;
import org.example.commandValidators.CommandValidator;
import org.example.model.Book;
import org.example.service.LibraryService;
import org.example.util.IOHandler;

/**
 * Обрабатывает команду редактирования книги
 * Если команда корректна - редактирует книгу
 */
public class EditBookCommandHandler implements CommandHandler {

    private final CommandValidator commandValidator;
    private final LibraryService libraryService;
    private final IOHandler ioHandler;

    public EditBookCommandHandler(CommandValidator commandValidator, LibraryService libraryService, IOHandler ioHandler) {
        this.commandValidator = commandValidator;
        this.libraryService = libraryService;
        this.ioHandler = ioHandler;
    }

    @Override
    public void executeCommand(Command command) {
        if (commandValidator.validateCommand(command)) {
            Book book = libraryService.editBook(command.getParams());
            printInfo(book, command);
        }
    }

    /**
     * Выводит необходимателенную информацию об измененной книге
     */
    private void printInfo(Book book, Command command) {
        if (book == null) {
            ioHandler.printFormatted("Книга с ID %s не найдена.", command.getParams().get(0));
            return;
        }
        ioHandler.print("Изменена книга:");
        ioHandler.print(book.getBookInfo());
    }
}
