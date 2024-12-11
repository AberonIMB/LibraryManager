package org.example.commandValidators;

import org.example.Command;
import org.example.util.IOHandler;

/**
 * Проверяет корректность команды добавления книги
 */
public class AddBookCommandValidator extends DefaultCommandValidator implements CommandValidator {

    /**
     * Конструктор, в котором присваивается обработчик ввода/вывода
     */
    public AddBookCommandValidator(IOHandler ioHandler) {
        super(ioHandler);
    }

    @Override
    public boolean validateCommand(Command command) {
        return validateArgsCount(command.getParams().size(), 3)
                && validateArgIsNumber(
                        command.getParams().get(2),
                        "Год публикации должен быть представлен числом.");
    }
}