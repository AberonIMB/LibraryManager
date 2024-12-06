package org.example.commandValidators;

import org.example.Command;
import org.example.util.IOHandler;

/**
 * Проверяет корректность команды добавления книги
 */
public class AddBookCommandValidator implements CommandValidator {

    private final IOHandler ioHandler;

    /**
     * Конструктор, в котором присваивается обработчик ввода/вывода
     */
    public AddBookCommandValidator(IOHandler IOHandler) {
        this.ioHandler = IOHandler;
    }
    @Override
    public boolean validateCommand(Command command) {
        int paramsCount = command.getParams().size();
        if (paramsCount != 3) {
            ioHandler.printFormatted("Неверное количество аргументов команды: должно быть %d, представлено %d.",
                    3, paramsCount);
            return false;
        }
        if (!command.getParams().get(2).matches("\\d+")) {
            ioHandler.print("Год публикации должен быть представлен числом.");
            return false;
        }
        return true;
    }
}