package org.example.commandHandlers;

import org.example.Command;
import org.example.commandValidators.CommandValidator;
import org.example.commandValidators.OnlyIdCommandValidator;
import org.example.exceptions.commandExceptions.CommandValidationException;
import org.example.exceptions.stateExceptions.StateValidationException;
import org.example.model.Reader;
import org.example.service.LibraryService;
import org.example.stateValidators.DeleteReaderStateValidator;
import org.example.util.IOHandler;

/**
 * Обрабатывает команду удаления читателя
 * Если команда корректна - удаляет читателя из библиотеки
 */
public class DeleteReaderCommandHandler implements CommandHandler {

    private final LibraryService libraryService;
    private final CommandValidator commandValidator;
    private final DeleteReaderStateValidator stateValidator;
    private final IOHandler ioHandler;

    public DeleteReaderCommandHandler(LibraryService libraryService, IOHandler ioHandler) {
        this.libraryService = libraryService;
        this.commandValidator = new OnlyIdCommandValidator();
        this.stateValidator = new DeleteReaderStateValidator();
        this.ioHandler = ioHandler;
    }

    @Override
    public void executeCommand(Command command) {
        try {
            commandValidator.validateCommand(command);

            Long id = Long.parseLong(command.getParams().get(0));
            Reader reader = libraryService.getReaderById(id);

            stateValidator.validateState(id, reader);

            libraryService.deleteReader(reader);
            printInfo(reader);

        } catch (CommandValidationException | StateValidationException e) {
            ioHandler.print(e.getMessage());
        }
    }

    private void printInfo(Reader reader) {
        ioHandler.print(String.format("Читатель %s успешно удален", reader.getReaderShortInfo()));
    }
}
