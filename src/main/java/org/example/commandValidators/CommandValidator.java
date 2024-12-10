package org.example.commandValidators;

import org.example.Command;

/**
 * Проверяет корректность синтаксиса команды
 */
public interface CommandValidator {
    /**
     * Проверяет корректность параметров команды
     * @return true, если параметры команды корректны
     */
    boolean validateCommand(Command command);
}
