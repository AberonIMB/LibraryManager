package org.example.commandHandlers;

import org.example.Command;
import org.example.commandValidators.CommandValidator;
import org.example.commandValidators.EditBookCommandValidator;
import org.example.exceptions.ArgumentsCountException;
import org.example.exceptions.InvalidIdException;
import org.example.exceptions.InvalidYearException;
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

    /**
     * Конструктор, который задает все необходимые поля
     */
    public EditBookCommandHandler(LibraryService libraryService, IOHandler ioHandler) {
        commandValidator = new EditBookCommandValidator();
        this.libraryService = libraryService;
        this.ioHandler = ioHandler;
    }

    @Override
    public void executeCommand(Command command) {
        try {
            commandValidator.validateCommand(command);
            Book book = libraryService.editBook(Long.parseLong(command.getParams().get(0)),
                    command.getParams().get(1),
                    command.getParams().get(2),
                    Integer.parseInt(command.getParams().get(3)));
            printInfo(book, command);
        } catch (ArgumentsCountException | InvalidIdException | InvalidYearException e) {
            ioHandler.print(e.getMessage());
        }
    }

    /**
     * Выводит необходимую информацию об измененной книге
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