package org.example.commandHandlers;

import org.example.Command;
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

    public CheckoutBookCommandHandler(CommandValidator commandValidator, LibraryService libraryService, IOHandler ioHandler, CheckoutStateValidator stateValidator) {
        this.libraryService = libraryService;
        this.commandValidator = commandValidator;
        this.stateValidator = stateValidator;
        this.ioHandler = ioHandler;
    }

    @Override
    public void executeCommand(Command command) {
        try {
            commandValidator.validateCommand(command);

            Book book = libraryService.getBookById(Long.parseLong(command.getParams().get(0)));
            Reader reader = libraryService.getReaderById(Long.parseLong(command.getParams().get(1)));

            stateValidator.validateState(command, book, reader);

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