package org.example;

import org.example.commandHandlers.*;
import org.example.commandValidators.*;
import org.example.service.LibraryService;
import org.example.util.IOHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс хранит команды и их обработчики
 */
public class CommandFactory {

    /**
     * Хранилище команд и их обработчиков
     */
    private final Map<String, CommandHandler> commands = new HashMap<>();

    /**
     * В хранилище добавляются пары команда-обработчик
     */
    public CommandFactory(IOHandler ioHandler, LibraryService libraryService) {
        CommandValidator addBookCommandValidator = new AddBookCommandValidator(ioHandler);
        CommandValidator deleteBookCommandValidator = new DeleteBookCommandValidator(ioHandler);
        CommandValidator editBookCommandValidator = new EditBookCommandValidator(ioHandler);
        CommandValidator commandValidator = new CommandsWithoutParamsValidator(ioHandler);

        commands.put("unknown", new UnknownCommandHandler(ioHandler));
        commands.put("add-book", new AddBookCommandHandler(addBookCommandValidator, libraryService, ioHandler));
        commands.put("list-books", new GetBookListCommandHandler(libraryService, ioHandler, commandValidator));
        commands.put("edit-book", new EditBookCommandHandler(editBookCommandValidator, libraryService, ioHandler));
        commands.put("delete-book", new DeleteBookCommandHandler(deleteBookCommandValidator, libraryService, ioHandler));
        commands.put("help", new HelpCommandHandler(ioHandler, commandValidator));
        commands.put("stop", new StopCommandHandler(commandValidator, ioHandler));
    }

    /**
     * Получение обработчика команды по названию
     * Если команда не существует, то возвращается обработчик UnknownCommandHandler
     * @param command команда
     */
    public CommandHandler getCommandHandler(Command command) {
        return commands.getOrDefault(command.getName(), commands.get("unknown"));
    }
}