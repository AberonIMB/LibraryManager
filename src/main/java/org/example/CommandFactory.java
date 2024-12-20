package org.example;

import org.example.commandHandlers.*;
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
        commands.put("unknown", new UnknownCommandHandler(ioHandler));
        commands.put("add-book", new AddBookCommandHandler(libraryService, ioHandler));
        commands.put("list-books", new GetBookListCommandHandler(libraryService, ioHandler));
        commands.put("edit-book", new EditBookCommandHandler(libraryService, ioHandler));
        commands.put("delete-book", new DeleteBookCommandHandler(libraryService, ioHandler));
        commands.put("help", new HelpCommandHandler(ioHandler));
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