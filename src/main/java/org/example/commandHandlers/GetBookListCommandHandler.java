package org.example.commandHandlers;

import org.example.Command;
import org.example.commandValidators.CommandValidator;
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

    public GetBookListCommandHandler(LibraryService libraryService, IOHandler ioHandler, CommandValidator commandValidator) {
        this.libraryService = libraryService;
        this.ioHandler = ioHandler;
        this.commandValidator = commandValidator;
    }

    @Override
    public void executeCommand(Command command) {
        if (commandValidator.validateCommand(command)) {
            printInfo(libraryService.getListBooks());
        }
    }

    /**
     * Выводит необходимателенную информацию о списке книг
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