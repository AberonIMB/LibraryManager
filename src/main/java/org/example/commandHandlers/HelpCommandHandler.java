package org.example.commandHandlers;

import org.example.Command;
import org.example.commandValidators.CommandValidator;
import org.example.commandValidators.CommandsWithoutParamsValidator;
import org.example.exceptions.ArgumentsCountException;
import org.example.exceptions.InvalidIdException;
import org.example.exceptions.InvalidYearException;
import org.example.util.IOHandler;

/**
 * Обрабатывает команду help
 */
public class HelpCommandHandler implements CommandHandler {
    private final IOHandler ioHandler;
    private final CommandValidator commandValidator;

    /**
     * Конструктор, который задает все необходимые поля
     */
    public HelpCommandHandler(IOHandler ioHandler) {
        this.ioHandler = ioHandler;
        commandValidator = new CommandsWithoutParamsValidator();
    }

    @Override
    public void executeCommand(Command command) {
        try {
            commandValidator.validateCommand(command);
            ioHandler.print("""
                Доступные команды:
                \t- add-book "<название>" "<автор>" <год издания> – Добавить книгу
                \t- list-books – Просмотреть список книг
                \t- edit-book <ID книги> “<название>” “<автор>” <год издания> - Изменить книгу
                \t- delete-book <ID книги> – Удалить книгу
                \t- help – Справка
                \t- stop - Завершить работу""");
        } catch (ArgumentsCountException | InvalidIdException | InvalidYearException e) {
            ioHandler.print(e.getMessage());
        }
    }
}