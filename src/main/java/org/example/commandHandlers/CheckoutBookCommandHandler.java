package org.example.commandHandlers;

import org.example.Command;
import org.example.commandValidators.CheckoutBookCommandValidator;
import org.example.commandValidators.CommandValidator;
import org.example.exceptions.commandExceptions.CommandValidationException;
import org.example.exceptions.stateExceptions.StateValidationException;
import org.example.model.Book;
import org.example.model.Reader;
import org.example.service.LibraryService;
import org.example.stateValidators.CheckoutStateValidator;
import org.example.util.IOHandler;

/**
 * Обрабатывает команду выдачи книги читателю
 * Если команда корректна - добавляет читателя в библиотеку
 */
public class CheckoutBookCommandHandler implements CommandHandler {

    private final LibraryService libraryService;
    private final CommandValidator commandValidator;
    private final CheckoutStateValidator stateValidator;
    private final IOHandler ioHandler;

    public CheckoutBookCommandHandler(LibraryService libraryService, IOHandler ioHandler) {
        this.libraryService = libraryService;
        commandValidator = new CheckoutBookCommandValidator();
        this.stateValidator = new CheckoutStateValidator();
        this.ioHandler = ioHandler;
    }

    @Override
    public void executeCommand(Command command) {
        try {
            commandValidator.validateCommand(command);

            Long bookId = Long.parseLong(command.getParams().get(0));
            Long readerId = Long.parseLong(command.getParams().get(1));

            Book book = libraryService.getBookById(bookId);
            Reader reader = libraryService.getReaderById(readerId);

            stateValidator.validateState(bookId, readerId, book, reader);

            libraryService.checkoutBook(book, reader);

            printInfo(book, reader);
        } catch (CommandValidationException | StateValidationException e) {
            ioHandler.print(e.getMessage());
        }
    }

    private void printInfo(Book book, Reader reader) {
        ioHandler.print(String.format("Книга \"%s\" выдана читателю \"%s\"",
                book.getTitle(), reader.getReaderShortInfo()));
    }
}