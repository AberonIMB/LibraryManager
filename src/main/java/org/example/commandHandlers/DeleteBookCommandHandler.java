package org.example.commandHandlers;

import org.example.Command;
import org.example.commandValidators.CommandValidator;
import org.example.commandValidators.OnlyIdCommandValidator;
import org.example.exceptions.commandExceptions.CommandValidationException;
import org.example.exceptions.stateExceptions.StateValidationException;
import org.example.model.Book;
import org.example.service.LibraryService;
import org.example.stateValidators.DeleteBookStateValidator;
import org.example.util.IOHandler;

/**
 * Обрабатывает команду удаления книги
 * Если команда корректна - удаляет книгу из библиотеки
 */
public class DeleteBookCommandHandler implements CommandHandler {
    private final CommandValidator commandValidator;
    private final LibraryService libraryService;
    private final IOHandler ioHandler;
    private final DeleteBookStateValidator stateValidator;

    /**
     * Конструктор, который задает все необходимые поля
     */
    public DeleteBookCommandHandler(LibraryService libraryService, IOHandler ioHandler) {
        commandValidator = new OnlyIdCommandValidator();
        stateValidator = new DeleteBookStateValidator();
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

            libraryService.deleteBook(book);
            printInfo(book);
        } catch (CommandValidationException | StateValidationException e) {
            ioHandler.print(e.getMessage());
        }
    }

    /**
     * Выводит необходимую информацию об удаленной книге
     */
    private void printInfo(Book book) {
        ioHandler.print(String.format("Книга с ID %d успешно удалена.", book.getId()));
    }
}