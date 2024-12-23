package org.example.commandHandlers;

import org.example.Command;
import org.example.commandValidators.CommandValidator;
import org.example.commandValidators.CommandsWithoutParamsValidator;
import org.example.exceptions.commandExceptions.CommandValidationException;
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
        commandValidator = new CommandsWithoutParamsValidator();
        this.ioHandler = ioHandler;
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
                \t- add-reader "<ФИО читателя>” – Добавить читателя
                \t- edit-reader <ID читателя> “<ФИО читателя>” – Изменить читателя
                \t- show-reader <ID читателя> – Просмотреть читателя
                \t- list-readers – Просмотреть список читателей
                \t- delete-reader <ID читателя> – Удалить читателя
                \t- checkout-book <ID книги> <ID читателя> – Выдать книгу
                \t- return-book <ID книги> - Вернуть книгу
                \t- help – Справка
                \t- stop - Завершить работу""");
        } catch (CommandValidationException e) {
            ioHandler.print(e.getMessage());
        }
    }
}