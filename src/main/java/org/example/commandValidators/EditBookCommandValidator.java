package org.example.commandValidators;

import org.example.Command;
import org.example.util.IOHandler;

/**
 * Проверяет корректность команды редактирования книги
 */
public class EditBookCommandValidator extends DefaultCommandValidator implements CommandValidator {

    /**
     * Конструктор, в котором присваивается обработчик ввода/вывода
     */
    public EditBookCommandValidator(IOHandler ioHandler) {
        super(ioHandler);
    }

    @Override
    public boolean validateCommand(Command command) {
        return validateArgsCount(command.getParams().size(), 4)
                && validateArgIsNumber(
                        command.getParams().get(0),
                        "ID должен быть представлен числом.")
                && validateArgIsNumber(
                        command.getParams().get(3),
                        "Год публикации должен быть представлен числом.");
    }
}
