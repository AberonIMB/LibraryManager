package org.example.commandHandlers;

import org.example.Command;
import org.example.commandValidators.CommandValidator;
import org.example.commandValidators.EditBookCommandValidator;
import org.example.exceptions.commandExceptions.CommandValidationException;
import org.example.exceptions.stateExceptions.StateValidationException;
import org.example.model.Book;
import org.example.service.LibraryService;
import org.example.stateValidators.EditBookStateValidator;
import org.example.util.IOHandler;

/**
 * Обрабатывает команду редактирования книги
 * Если команда корректна - редактирует книгу
 */
public class EditBookCommandHandler implements CommandHandler {

    private final CommandValidator commandValidator;
    private final LibraryService libraryService;
    private final IOHandler ioHandler;
    private final EditBookStateValidator stateValidator;

    /**
     * Конструктор, который задает все необходимые поля
     */
    public EditBookCommandHandler(LibraryService libraryService, IOHandler ioHandler) {
        commandValidator = new EditBookCommandValidator();
        stateValidator = new EditBookStateValidator();
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

            libraryService.editBook(book,
                    command.getParams().get(1),
                    command.getParams().get(2),
                    Integer.parseInt(command.getParams().get(3)));

            printInfo(book);
        } catch (CommandValidationException | StateValidationException e) {
            ioHandler.print(e.getMessage());
        }
    }

    /**
     * Выводит необходимую информацию об измененной книге
     */
    private void printInfo(Book book) {
        ioHandler.print("Изменена книга:");
        ioHandler.print(book.getBookInfo());
    }
}