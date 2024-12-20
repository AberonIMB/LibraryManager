package org.example.commandHandlers;

import org.example.Command;
import org.example.commandValidators.CommandValidator;
import org.example.commandValidators.CommandsWithoutParamsValidator;
import org.example.exceptions.ArgumentsCountException;
import org.example.exceptions.InvalidIdException;
import org.example.exceptions.InvalidYearException;
import org.example.model.Book;
import org.example.service.LibraryService;
import org.example.util.IOHandler;

import java.util.List;

/**
 * Обрабатывает команду получения списка книг
 */
public class GetBookListCommandHandler implements CommandHandler {

    private final LibraryService libraryService;
    private final IOHandler ioHandler;
    private final CommandValidator commandValidator;

    /**
     * Конструктор, который задает все необходимые поля
     */
    public GetBookListCommandHandler(LibraryService libraryService, IOHandler ioHandler) {
        this.libraryService = libraryService;
        this.ioHandler = ioHandler;
        commandValidator = new CommandsWithoutParamsValidator();
    }

    @Override
    public void executeCommand(Command command) {
        try {
            commandValidator.validateCommand(command);
            printInfo(libraryService.getListBooks());
        } catch (ArgumentsCountException | InvalidIdException | InvalidYearException e) {
            ioHandler.print(e.getMessage());
        }
    }

    /**
     * Выводит необходимую информацию о списке книг
     */
    private void printInfo(List<Book> books) {
        if (books.isEmpty()) {
            ioHandler.print("Список книг пуст.");
            return;
        }

        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            ioHandler.printFormatted("%d) %s", i + 1, book.getBookShortInfo());
        }
    }
}