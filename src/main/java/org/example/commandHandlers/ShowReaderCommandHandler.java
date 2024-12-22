package org.example.commandHandlers;

import org.example.Command;
import org.example.commandValidators.CommandValidator;
import org.example.commandValidators.OnlyIdCommandValidator;
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

    public ShowReaderCommandHandler(LibraryService libraryService, IOHandler ioHandler) {
        commandValidator = new OnlyIdCommandValidator();
        stateValidator = new ReaderNotNullStateValidator();
        this.libraryService = libraryService;
        this.ioHandler = ioHandler;
    }

    @Override
    public void executeCommand(Command command) {
        try {
            commandValidator.validateCommand(command);

            Long id = Long.parseLong(command.getParams().get(0));
            Reader reader = libraryService.getReaderById(id);

            stateValidator.validateState(id, reader);

            printInfo(reader);
        } catch (CommandValidationException | StateValidationException e) {
            ioHandler.print(e.getMessage());
        }
    }

    private void printInfo(Reader reader) {
        ioHandler.print(reader.getReaderInfo());
    }
}
