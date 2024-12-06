package org.example.commandValidators;

import org.example.Command;
import org.example.util.IOHandler;

/**
 * Проверяет корректность команды удаления книги
 */
public class DeleteBookCommandValidator implements CommandValidator {

    private final IOHandler ioHandler;

    /**
     * Конструктор, в котором присваивается обработчик ввода/вывода
     */
    public DeleteBookCommandValidator(IOHandler ioHandler) {
        this.ioHandler = ioHandler;
    }
    @Override
    public boolean validateCommand(Command command) {
        int paramsCount = command.getParams().size();
        if (command.getParams().size() != 1) {
            ioHandler.printFormatted("Неверное количество аргументов команды: должно быть %d, представлено %d.",
                    1, paramsCount);
            return false;
        }
        if (!command.getParams().get(0).matches("\\d+")) {
            ioHandler.print("ID должен быть представлен числом.");
            return false;
        }
        return true;
    }
}
