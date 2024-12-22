package org.example.commandHandlers;

import org.example.Command;
import org.example.commandValidators.CommandValidator;
import org.example.commandValidators.EditReaderCommandValidator;
import org.example.exceptions.commandExceptions.CommandValidationException;
import org.example.exceptions.stateExceptions.StateValidationException;
import org.example.model.Reader;
import org.example.service.LibraryService;
import org.example.stateValidators.ReaderNotNullStateValidator;
import org.example.util.IOHandler;

/**
 * Обрабатывает команду редактирования читателя
 * Если команда корректна - редактирует читателя
 */
public class EditReaderCommandHandler implements CommandHandler {

    private final LibraryService libraryService;
    private final CommandValidator commandValidator;
    private final ReaderNotNullStateValidator stateValidator;
    private final IOHandler ioHandler;


    public EditReaderCommandHandler(LibraryService libraryService, IOHandler ioHandler) {
        this.libraryService = libraryService;
        this.commandValidator = new EditReaderCommandValidator();
        this.stateValidator = new ReaderNotNullStateValidator();
        this.ioHandler = ioHandler;
    }

    @Override
    public void executeCommand(Command command) {
        try {
            commandValidator.validateCommand(command);

            Long id = Long.parseLong(command.getParams().get(0));
            Reader reader = libraryService.getReaderById(id);

            stateValidator.validateState(id, reader);

            libraryService.editReader(reader, command.getParams().get(1));

            printInfo(reader);
        } catch (CommandValidationException | StateValidationException e) {
            ioHandler.print(e.getMessage());
        }
    }

    private void printInfo(Reader reader) {
        ioHandler.print(String.format("Имя читателя %d изменено на %s", reader.getId(), reader.getName()));
    }
}
