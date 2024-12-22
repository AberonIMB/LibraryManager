package org.example.commandHandlers;

import org.example.Command;
import org.example.commandValidators.CommandValidator;
import org.example.commandValidators.OnlyIdCommandValidator;
import org.example.exceptions.commandExceptions.CommandValidationException;
import org.example.exceptions.stateExceptions.StateValidationException;
import org.example.model.Book;
import org.example.service.LibraryService;
import org.example.stateValidators.ReturnStateValidator;
import org.example.util.IOHandler;

/**
 * Обрабатывает команду возврата книги
 */
public class ReturnBookCommandHandler implements CommandHandler {

    private final LibraryService libraryService;
    private final CommandValidator commandValidator;
    private final ReturnStateValidator stateValidator;
    private final IOHandler ioHandler;

    public ReturnBookCommandHandler(LibraryService libraryService, IOHandler ioHandler) {
        commandValidator = new OnlyIdCommandValidator();
        stateValidator = new ReturnStateValidator();
        this.libraryService = libraryService;
        this.ioHandler = ioHandler;
    }

    @Override
    public void executeCommand(Command command) {
        try {
            commandValidator.validateCommand(command);

            Long id = Long.parseLong(command.getParams().get(0));
            Book book = libraryService.getBookById(id);

            stateValidator.validateState(id, book);

            libraryService.returnBook(book);

            printInfo(book);
        } catch (CommandValidationException | StateValidationException e) {
            ioHandler.print(e.getMessage());
        }
    }

    private void printInfo(Book book) {
        ioHandler.print(String.format("Книга \"%s\" возвращена в библиотеку", book.getTitle()));
    }
}
