package org.example.commandHandlers;

import org.example.Command;
import org.example.commandValidators.AddBookCommandValidator;
import org.example.commandValidators.CommandValidator;
import org.example.exceptions.commandExceptions.CommandValidationException;
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

    /**
     * Конструктор, который задает все необходимые поля
     */
    public AddBookCommandHandler(LibraryService libraryService, IOHandler ioHandler) {
        commandValidator = new AddBookCommandValidator();
        this.libraryService = libraryService;
        this.ioHandler = ioHandler;
    }

    @Override
    public void executeCommand(Command command) {
        try {
            commandValidator.validateCommand(command);
            Book book = libraryService.addBook(command.getParams().get(0),
                    command.getParams().get(1),
                    Integer.parseInt(command.getParams().get(2)));

            printInfo(book);
        } catch (CommandValidationException e) {
            ioHandler.print(e.getMessage());
        }
    }

    /**
     * Выводит необходимую информацию о добавленной книге
     */
    private void printInfo(Book book) {
        ioHandler.print("Добавлена книга:");
        ioHandler.print(book.getBookInfo());
    }
}