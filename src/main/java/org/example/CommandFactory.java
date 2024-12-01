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
     * В хранилище добавляются пары команда-обработчик
     * @param printer класс для вывода текста в консоль
     */
    public CommandFactory(Printer printer) {
        commands.put("unknown", new UnknownCommandHandler(printer));
        commands.put("add-book", new AddBookCommandHandler());
        commands.put("list-books", new GetBookListCommandHandler(printer));
        commands.put("edit-book", new EditBookCommandHandler());
        commands.put("delete-book", new DeleteBookCommandHandler());
        commands.put("help", new HelpCommandHandler(printer));
        commands.put("add-reader", new AddReaderCommandHandler());
        commands.put("list-readers", new GetReaderListCommandHandler(printer));
        commands.put("edit-reader", new EditReaderCommandHandler());
        commands.put("delete-reader", new DeleteReaderCommandHandler());
        commands.put("show-reader", new ShowReaderCommandHandler(printer));
        commands.put("checkout-book", new CheckoutBookCommandHandler());
        commands.put("return-book", new ReturnBookCommandHandler());
    }

    /**
     * Получение обработчика команды по названию
     * Если команда не существует, то возвращается обработчик UnknownCommandHandler
     * @param command команда
     */
    public CommandHandler getCommandHandler(String command) {
        return commands.getOrDefault(command, commands.get("unknown"));
    }
}