package org.example.commandHandlers;

import org.example.Command;
import org.example.commandValidators.CommandValidator;
import org.example.exceptions.commandExceptions.CommandValidationException;
import org.example.exceptions.stateExceptions.StateValidationException;
import org.example.model.Reader;
import org.example.service.LibraryService;
import org.example.stateValidators.ReaderNotNullStateValidator;
import org.example.util.IOHandler;

/**
 * Обрабатывает команду просмотра читателя
 */
public class ShowReaderCommandHandler implements CommandHandler {

    private final LibraryService libraryService;
    private final CommandValidator commandValidator;
    private final ReaderNotNullStateValidator stateValidator;
    private final IOHandler ioHandler;

    public ShowReaderCommandHandler(CommandValidator commandValidator, LibraryService libraryService, IOHandler ioHandler, ReaderNotNullStateValidator stateValidator) {
        this.libraryService = libraryService;
        this.commandValidator = commandValidator;
        this.stateValidator = stateValidator;
        this.ioHandler = ioHandler;
    }

    @Override
    public void executeCommand(Command command) {
        try {
            commandValidator.validateCommand(command);
            Reader reader = libraryService.getReaderById(Long.parseLong(command.getParams().get(0)));

            stateValidator.validateState(command, reader);

            printInfo(reader);
        } catch (CommandValidationException | StateValidationException e) {
            ioHandler.print(e.getMessage());
        }
    }

    private void printInfo(Reader reader) {
        ioHandler.print(reader.getReaderInfo());
    }
}
