package org.example.commandHandlers;

import org.example.Command;
import org.example.commandValidators.CommandValidator;
import org.example.exceptions.ArgumentsCountException;
import org.example.exceptions.InvalidIdException;
import org.example.exceptions.InvalidYearException;
import org.example.model.Book;
import org.example.service.LibraryService;
import org.example.util.IOHandler;

/**
 * Обрабатывает команду удаления книги
 * Если команда корректна - удаляет книгу из библиотеки
 */
public class DeleteBookCommandHandler implements CommandHandler {
    private final CommandValidator commandValidator;
    private final LibraryService libraryService;
    private final IOHandler ioHandler;

    /**
     * Конструктор, который задает все необходимые поля
     */
    public DeleteBookCommandHandler(CommandValidator commandValidator, LibraryService libraryService, IOHandler ioHandler) {
        this.commandValidator = commandValidator;
        this.libraryService = libraryService;
        this.ioHandler = ioHandler;
    }

    @Override
    public void executeCommand(Command command) {
        try {
            commandValidator.validateCommand(command);
            Book book = libraryService.deleteBook(Long.parseLong(command.getParams().get(0)));
            printInfo(book, command);
        } catch (ArgumentsCountException | InvalidIdException | InvalidYearException e) {
            ioHandler.print(e.getMessage());
        }
    }

    /**
     * Выводит необходимую информацию об удаленной книге
     */
    private void printInfo(Book book, Command command) {
        String id = command.getParams().get(0);

        if (book == null) {
            ioHandler.printFormatted("Книга с ID %s не найдена.", id);
            return;
        }

        ioHandler.printFormatted("Книга с ID %s успешно удалена.", id);
    }
}