import org.example.Command;
import org.example.commandValidators.*;
import org.example.exceptions.commandExceptions.ArgumentsCountException;
import org.example.exceptions.commandExceptions.InvalidIdException;
import org.example.exceptions.commandExceptions.InvalidYearException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 * Общие тесты для всех валидаторов команд
 */
public class CommandValidatorsTest {
    
    private final CommandValidator addBookCommandValidator = new AddBookCommandValidator();
    private final CommandValidator deleteBookCommandValidator = new OnlyIdCommandValidator();
    private final CommandValidator editBookCommandValidator = new EditBookCommandValidator();
    private final CommandValidator commandWithoutParamsValidator = new CommandsWithoutParamsValidator();

    /**
     * Проверка валидаторов с корректными данными
     */
    @Test
    void validateCommandsWithCorrectParams() {
        testNothingToThrows(
                addBookCommandValidator,
                new Command("addBook title author 2023")
        );

        testNothingToThrows(
                deleteBookCommandValidator,
                new Command("deleteBook 1")
        );

        testNothingToThrows(
                editBookCommandValidator,
                new Command("editBook 1 newTitle newAuthor 2024")
        );

        testNothingToThrows(
                commandWithoutParamsValidator,
                new Command("help")
        );
    }

    /**
     * Проверка валидатора добавления книги с некорректным количеством параметров
     */
    @Test
    public void validateAddCommandWithIncorrectParamsCount() {
        testThrowsArgumentsCountException(
                addBookCommandValidator,
                new Command("addBook title"),
                3
        );
    }

    /**
     * Проверка валидатора добавления книги с некорректным значением года
     */
    @Test
    public void validateAddCommandWithIncorrectParams() {
        testThrowsInvalidYearException(
                addBookCommandValidator,
                new Command("addBook title author invalidYear")
        );
    }

    /**
     * Проверка валидатора удаления книги с некорректным количеством параметров
     */
    @Test
    public void validateDeleteCommandWithIncorrectParamsCount() {
        testThrowsArgumentsCountException(
                deleteBookCommandValidator,
                new Command("deleteBook 1 extraParam"),
                1
        );
    }

    /**
     * Проверка валидатора удаления книги с некорректным ID
     */
    @Test
    public void validateDeleteCommandWithIncorrectParams() {
        testThrowsInvalidIdException(
                deleteBookCommandValidator,
                new Command("deleteBook invalidId")
        );
    }

    /**
     * Проверка валидатора изменеия книги с некорректным количеством параметров
     */
    @Test
    public void validateEditCommandWithIncorrectParamsCount() {
        testThrowsArgumentsCountException(
                editBookCommandValidator,
                new Command("editBook 1 newTitle"),
                4
        );
    }

    /**
     * Проверка валидатора изменеия книги с некорректным параметром ID
     */
    @Test
    public void validateEditCommandWithIncorrectIDParam() {
        testThrowsInvalidIdException(
                editBookCommandValidator,
                new Command("edit-book invalidId title author 2024")
        );
    }

    /**
     * Проверка валидатора изменеия книги с некорректным значением года
     */
    @Test
    public void validateEditCommandWithIncorrectYearParam() {
        testThrowsInvalidYearException(
                editBookCommandValidator,
                new Command("edit-book 1 title author InvalidYear")
        );
    }

    /**
     * Проверка валидатора без параметров с некорректным количеством параметров
     */
    @Test
    public void validateWithoutParamsCommandWithIncorrectParamsCount() {
        testThrowsArgumentsCountException(
                commandWithoutParamsValidator,
                new Command("help extraParam"),
                0
        );
    }

    /**
     * Метод, который проверяет, что валидатор не выбрасывает исключения
     */
    private void testNothingToThrows(CommandValidator validator, Command command) {
        Assertions.assertDoesNotThrow(() -> validator.validateCommand(command));
    }

    /**
     * Метод для проверки выброса исключения ArgumentsCountException
     */
    private void testThrowsArgumentsCountException(CommandValidator validator,
                                                   Command command,
                                                   int expectedParamsCount) {
        Exception e = Assertions.assertThrows(ArgumentsCountException.class, () -> validator.validateCommand(command));

        Assertions.assertEquals("Неверное количество аргументов команды: должно быть %d, представлено %d."
                .formatted(expectedParamsCount, command.getParams().size()), e.getMessage());
    }

    /**
     * Метод для проверки выброса исключения InvalidIdException
     */
    private void testThrowsInvalidIdException(CommandValidator validator,
                                                   Command command) {
        Exception e = Assertions.assertThrows(InvalidIdException.class, () -> validator.validateCommand(command));

        Assertions.assertEquals("ID должен быть представлен числом.", e.getMessage());
    }

    /**
     * Метод для проверки выброса исключения InvalidYearException
     */
    private void testThrowsInvalidYearException(CommandValidator validator,
                                              Command command) {
        Exception e = Assertions.assertThrows(InvalidYearException.class, () -> validator.validateCommand(command));

        Assertions.assertEquals("Год должен быть представлен числом.", e.getMessage());
    }
}