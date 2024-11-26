package org.example;

import org.example.commandHandlers.*;
import org.example.util.Printer;

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
     * Класс для вывода сообщений в консоль
     */
    private final Printer printer;

    /**
     * В хранилище добавляются пары команда-обработчик
     * @param printer класс для вывода текста в консоль
     */
    public CommandFactory(Printer printer) {
        this.printer = printer;
        commands.put("add-book", new AddBookCommandHandler());
        commands.put("list-books", new GetBookListCommandHandler(printer));
        commands.put("edit-book", new EditBookCommandHandler());
        commands.put("delete-book", new DeleteBookCommandHandler());
        commands.put("help", new HelpCommandHandler(printer));
    }

    /**
     * Получение обработчика команды по названию
     * Если команда не существует, то возвращается обработчик UnknownCommandHandler
     * @param command команда
     * @return обработчик команды
     */
    public CommandHandler getCommandHandler(String command) {
        return commands.getOrDefault(command, new UnknownCommandHandler(printer));
    }
}