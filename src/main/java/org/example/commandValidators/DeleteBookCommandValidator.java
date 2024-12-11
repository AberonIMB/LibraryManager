package org.example.commandValidators;

import org.example.Command;
import org.example.util.IOHandler;

/**
 * Проверяет корректность команды удаления книги
 */
public class DeleteBookCommandValidator extends DefaultCommandValidator implements CommandValidator {

    /**
     * Конструктор, в котором присваивается обработчик ввода/вывода
     */
    public DeleteBookCommandValidator(IOHandler ioHandler) {
        super(ioHandler);
    }

    @Override
    public boolean validateCommand(Command command) {
        return validateArgsCount(command.getParams().size(), 1)
                && validateArgIsNumber(
                        command.getParams().get(0),
                        "ID должен быть представлен числом.");
    }
}
