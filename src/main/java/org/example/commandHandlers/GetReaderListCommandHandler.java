package org.example.commandHandlers;

import org.example.Command;
import org.example.commandValidators.CommandValidator;
import org.example.commandValidators.CommandsWithoutParamsValidator;
import org.example.exceptions.commandExceptions.CommandValidationException;
import org.example.model.Reader;
import org.example.service.LibraryService;
import org.example.util.IOHandler;

import java.util.List;

/**
 * Обрабатывает команду получения списка читателей
 */
public class GetReaderListCommandHandler implements CommandHandler {

    private final LibraryService libraryService;
    private final CommandValidator commandValidator;
    private final IOHandler ioHandler;

    /**
     * Конструктор, который задает все необходимые поля
     */
    public GetReaderListCommandHandler(LibraryService libraryService, IOHandler ioHandler) {
        commandValidator = new CommandsWithoutParamsValidator();
        this.libraryService = libraryService;
        this.ioHandler = ioHandler;
    }

    @Override
    public void executeCommand(Command command) {
        try {
            commandValidator.validateCommand(command);

            List<Reader> readers = libraryService.getListReaders();
            printInfo(readers);
        } catch (CommandValidationException e) {
            ioHandler.print(e.getMessage());
        }
    }

    /**
     * Выводит необходимую информацию о списке читателей
     */
    private void printInfo(List<Reader> readers) {
        if (readers.isEmpty()) {
            ioHandler.print("Список читателей пуст.");
            return;
        }

        for (int i = 0; i < readers.size(); i++) {
            Reader reader = readers.get(i);
            ioHandler.printFormatted("%d) %s", i + 1, reader.getReaderShortInfo());
        }
    }
}