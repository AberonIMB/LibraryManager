package org.example.commandHandlers;

import org.example.Command;
import org.example.commandValidators.AddReaderCommandValidator;
import org.example.commandValidators.CommandValidator;
import org.example.exceptions.commandExceptions.CommandValidationException;
import org.example.model.Reader;
import org.example.service.LibraryService;
import org.example.util.IOHandler;

/**
 * Обрабатывает команду добавления читателя
 * Если команда корректна - добавляет читателя в библиотеку
 */
public class AddReaderCommandHandler implements CommandHandler {

    private final CommandValidator commandValidator;
    private final LibraryService libraryService;
    private final IOHandler ioHandler;

    public AddReaderCommandHandler(LibraryService libraryService, IOHandler ioHandler) {
        commandValidator = new AddReaderCommandValidator();
        this.libraryService = libraryService;
        this.ioHandler = ioHandler;
    }


    @Override
    public void executeCommand(Command command) {
        try {
            commandValidator.validateCommand(command);

            String name = command.getParams().get(0);
            Reader reader = libraryService.addReader(name);

            printInfo(reader);
        } catch (CommandValidationException e) {
            ioHandler.print(e.getMessage());
        }
    }

    /**
     * Выводит необходимую информацию о добавленном читателе
     */
    private void printInfo(Reader reader) {
        ioHandler.print("Читатель добавлен в систему:");
        ioHandler.print(reader.getReaderShortInfo());
    }
}