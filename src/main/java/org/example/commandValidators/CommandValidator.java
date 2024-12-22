package org.example.commandValidators;


import org.example.Command;
import org.example.exceptions.commandExceptions.ArgumentsCountException;
import org.example.exceptions.commandExceptions.InvalidIdException;
import org.example.exceptions.commandExceptions.InvalidYearException;

/**
 * Содержит общую логику проверки параметров команд для валидаторов
 */
public abstract class CommandValidator {

    /**
     * Проверяет корректность команды
     * @throws ArgumentsCountException ошибка при неправильном количестве аргументов
     * @throws InvalidYearException ошибка при некорректном значении года
     * @throws InvalidIdException ошибка при некорректном значении ID
     */
    public abstract void validateCommand(Command command) throws ArgumentsCountException, InvalidYearException, InvalidIdException;

    /**
     * Проверяет, что количество аргументов команды соответствует ожидаемому
     * @return true, если количество аргументов команды соответствует ожидаемому
     */
    protected boolean validateArgsCount(int expectedCount, int paramsCount) {
        return  expectedCount == paramsCount;
    }

    /**
     * Проверяет, что аргумент команды является числом
     * @return true, если аргумент команды является числом
     */
    protected boolean validateArgIsNumber(String arg) {
        return arg.matches("\\d+");
    }
}