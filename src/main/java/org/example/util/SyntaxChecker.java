package org.example.util;


/**
 * Класс для проверки синтаксиса команд
 */
public class SyntaxChecker {

    private final Printer printer = new Printer();

    /**
     * Проверка синтаксиса команды addBook
     *
     * @return true - если соответствует, иначе false
     */
    public boolean checkAddBookCommandSyntax(String[] command) {
        if (command.length != 4) {
            printer.printNotEnoughArgsError(command.length - 1, 3);
            return false;
        }
        if (!command[3].matches("\\d+")) {
            printer.printYearNotNumberError();
            return false;
        }
        return true;
    }

    /**
     * Проверка синтаксиса команды editBook
     *
     * @return true - если соответствует, иначе false
     */
    public boolean checkEditBookCommandSyntax(String[] command) {
        if (command.length != 5) {
            printer.printNotEnoughArgsError(command.length - 1, 4);
            return false;
        }
        if (!command[1].matches("\\d+")) {
            printer.printIdNotNumberError();
            return false;
        }
        if (!command[4].matches("\\d+")) {
            printer.printYearNotNumberError();
            return false;
        }

        return true;
    }

    /**
     * Проверка синтаксиса команды deleteBook
     *
     * @return true - если соответствует, иначе false
     */
    public boolean checkDeleteBookCommandSyntax(String[] command) {
        if (command.length != 2) {
            printer.printNotEnoughArgsError(command.length - 1, 1);
            return false;
        }
        if (!command[1].matches("\\d+")) {
            printer.printIdNotNumberError();
            return false;
        }
        return true;
    }
}
