package org.example.commandValidators;

import org.example.Command;
import org.example.util.IOHandler;

/**
 * Проверяет корректность команды редактирования книги
 */
public class EditBookCommandValidator implements CommandValidator {
    private final IOHandler ioHandler;

    /**
     * Конструктор, в котором присваивается обработчик ввода/вывода
     */
    public EditBookCommandValidator(IOHandler ioHandler) {
        this.ioHandler = ioHandler;
    }

    @Override
    public boolean validateCommand(Command command) {
        int paramsCount = command.getParams().size();
        if (paramsCount != 4) {
            ioHandler.printFormatted("Неверное количество аргументов команды: должно быть %d, представлено %d.",
                    4, paramsCount);
            return false;
        }
        if (!command.getParams().get(0).matches("\\d+")) {
            ioHandler.print("ID должен быть представлен числом.");
            return false;
        }
        if (!command.getParams().get(3).matches("\\d+")) {
            ioHandler.print("Год публикации должен быть представлен числом.");
            return false;
        }

        return true;
    }
}
