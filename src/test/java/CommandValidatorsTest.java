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
    private final CommandValidator onlyIdCommandValidator = new OnlyIdCommandValidator();
    private final CommandValidator editBookCommandValidator = new EditBookCommandValidator();
    private final CommandValidator addReaderCommandValidator = new AddReaderCommandValidator();
    private final CommandValidator editReaderCommandValidator = new EditReaderCommandValidator();
    private final CommandValidator checkoutBookCommandValidator = new CheckoutBookCommandValidator();
    private final CommandValidator commandWithoutParamsValidator = new CommandsWithoutParamsValidator();

    /**
     * Проверка валидаторов с корректными данными
     */
    @Test
    void validateCommandsWithCorrectParams() {
        Assertions.assertDoesNotThrow(() -> addBookCommandValidator
                .validateCommand(new Command("add-book title author 2023")));

        Assertions.assertDoesNotThrow(() -> onlyIdCommandValidator
                .validateCommand(new Command("delete-book 1")));

        Assertions.assertDoesNotThrow(() -> editBookCommandValidator
                .validateCommand(new Command("edit-book 1 newTitle newAuthor 2024")));

        Assertions.assertDoesNotThrow(() -> addReaderCommandValidator
                .validateCommand(new Command("add-reader name")));

        Assertions.assertDoesNotThrow(() -> editReaderCommandValidator
                .validateCommand(new Command("edit-reader 1 newName")));

        Assertions.assertDoesNotThrow(() -> checkoutBookCommandValidator
                .validateCommand(new Command("checkout-book 1 1")));

        Assertions.assertDoesNotThrow(() -> commandWithoutParamsValidator
                .validateCommand(new Command("help")));

    }

    /**
     * Проверка валидатора добавления книги с некорректным количеством параметров
     */
    @Test
    public void validateAddBookCommandWithIncorrectParamsCount() {
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
    public void validateAddBookCommandWithIncorrectParams() {
        testThrowsInvalidYearException(
                addBookCommandValidator,
                new Command("addBook title author invalidYear")
        );
    }

    /**
     * Проверка валидатора удаления книги с некорректным количеством параметров
     */
    @Test
    public void validateDeleteBookCommandWithIncorrectParamsCount() {
        testThrowsArgumentsCountException(
                onlyIdCommandValidator,
                new Command("deleteBook 1 extraParam"),
                1
        );
    }

    /**
     * Проверка валидатора удаления книги с некорректным ID
     */
    @Test
    public void validateDeleteBookCommandWithIncorrectParams() {
        testThrowsInvalidIdException(
                onlyIdCommandValidator,
                new Command("deleteBook invalidId")
        );
    }

    /**
     * Проверка валидатора изменеия книги с некорректным количеством параметров
     */
    @Test
    public void validateEditBookCommandWithIncorrectParamsCount() {
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
    public void validateEditBookCommandWithIncorrectIDParam() {
        testThrowsInvalidIdException(
                editBookCommandValidator,
                new Command("edit-book invalidId title author 2024")
        );
    }

    /**
     * Проверка валидатора изменеия книги с некорректным значением года
     */
    @Test
    public void validateEditBookCommandWithIncorrectYearParam() {
        testThrowsInvalidYearException(
                editBookCommandValidator,
                new Command("edit-book 1 title author InvalidYear")
        );
    }

    /**
     * Проверка валидатора добавления читателя с некорректным количеством параметров
     */
    @Test
    public void validateAddReaderCommandWithIncorrectParamsCount() {
        testThrowsArgumentsCountException(
                addReaderCommandValidator,
                new Command("addBook name surname"),
                1
        );
    }

    /**
     * Проверка валидатора изменения читателя с некорректным значением ID
     */
    @Test
    public void validateEditReaderCommandWithIncorrectIdParam() {
        testThrowsInvalidIdException(
                editReaderCommandValidator,
                new Command("addBook a name")
        );
    }

    /**
     * Проверка валидатора изменения читателя с неправильным количеством параметров
     */
    @Test
    public void validateEditReaderCommandWithIncorrectParamsCount() {
        testThrowsArgumentsCountException(
                editReaderCommandValidator,
                new Command("addBook 1 name surname"),
                2
        );
    }

    /**
     * Проверка валидатора изменения читателя с неправильным типом ID книги
     */
    @Test
    public void validateCheckoutBookCommandWithIncorrectBookIdParam() {
        testThrowsInvalidIdException(
                checkoutBookCommandValidator,
                new Command("checkout-book book 1")
        );
    }

    /**
     * Проверка валидатора изменения читателя с неправильным типом ID читателя
     */
    @Test
    public void validateCheckoutBookCommandWithIncorrectReaderIdParam() {
        testThrowsInvalidIdException(
                checkoutBookCommandValidator,
                new Command("checkout-book 1 reader")
        );
    }

    /**
     * Проверка валидатора выдачи книги с неправильным количеством параметров
     */
    @Test
    public void validateCheckoutBookCommandWithIncorrectParamsCount() {
        testThrowsArgumentsCountException(
                checkoutBookCommandValidator,
                new Command("checkout-book 1 2 excess"),
                2
        );
    }

    /**
     * Проверка валидатора без параметров с неправильным количеством параметров
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