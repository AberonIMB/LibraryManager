package org.example.commandHandlers;

import org.example.Command;
import org.example.commandValidators.CommandValidator;
import org.example.model.Book;
import org.example.service.LibraryService;
import org.example.util.IOHandler;

/**
 * Обрабатывает команду добавления книги
 * Если команда корректна - добавляет книгу в библиотеку
 */
public class AddBookCommandHandler implements CommandHandler {
    private final CommandValidator commandValidator;
    private final LibraryService libraryService;
    private final IOHandler ioHandler;

    public AddBookCommandHandler(CommandValidator commandValidator, LibraryService libraryService, IOHandler ioHandler) {
        this.commandValidator = commandValidator;
        this.libraryService = libraryService;
        this.ioHandler = ioHandler;
    }

    @Override
    public void executeCommand(Command command) {
        if (commandValidator.validateCommand(command)) {
            Book book = libraryService.addBook(command.getParams());
            printInfo(book);
        }
    }

    /**
     * Выводит необходимателенную информацию о добавленной книге
     */
    private void printInfo(Book book) {
        ioHandler.print("Добавлена книга:");
        ioHandler.print(book.getBookInfo());
    }
}