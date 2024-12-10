import org.example.Command;
import org.example.commandValidators.*;
import org.example.util.IOHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


/**
 * Общие тесты для всех валидаторов команд
 */
@ExtendWith(MockitoExtension.class)
public class CommandValidatorsTest {

    @Mock
    private IOHandler ioHandlerMock;

    /**
     * Проверка валидаторов с корректными данными
     */
    @Test
    void validateCommandsWithCorrectParams() {
        validateWithValidator(
                new AddBookCommandValidator(ioHandlerMock),
                new Command("addBook title author 2023"),
                true
        );

        validateWithValidator(
                new DeleteBookCommandValidator(ioHandlerMock),
                new Command("deleteBook 1"),
                true
        );

        validateWithValidator(
                new EditBookCommandValidator(ioHandlerMock),
                new Command("editBook 1 newTitle newAuthor 2024"),
                true
        );

        validateWithValidator(
                new CommandsWithoutParamsValidator(ioHandlerMock),
                new Command("help"),
                true
        );
    }

    /**
     * Проверка валидатора добавления книги с некорректным количеством параметров
     */
    @Test
    public void validateAddCommandWithIncorrectParamsCount() {
        validateWithValidatorFormattedPrint(
                new AddBookCommandValidator(ioHandlerMock),
                new Command("addBook title"),
                false,
                "Неверное количество аргументов команды: должно быть %d, представлено %d.",
                3, 1
        );
    }

    /**
     * Проверка валидатора удаления книги с некорректным количеством параметров
     */
    @Test
    public void validateDeleteCommandWithIncorrectParamsCount() {
        validateWithValidatorFormattedPrint(
                new DeleteBookCommandValidator(ioHandlerMock),
                new Command("deleteBook 1 extraParam"),
                false,
                "Неверное количество аргументов команды: должно быть %d, представлено %d.",
                1, 2
        );
    }

    /**
     * Проверка валидатора изменеия книги с некорректным количеством параметров
     */
    @Test
    public void validateEditCommandWithIncorrectParamsCount() {
        validateWithValidatorFormattedPrint(
                new EditBookCommandValidator(ioHandlerMock),
                new Command("editBook 1 newTitle"),
                false,
                "Неверное количество аргументов команды: должно быть %d, представлено %d.",
                4, 2
        );

    }

    /**
     * Проверка валидатора без параметров с некорректным количеством параметров
     */
    @Test
    public void validateWithoutParamsCommandWithIncorrectParamsCount() {
        validateWithValidatorFormattedPrint(
                new CommandsWithoutParamsValidator(ioHandlerMock),
                new Command("help extraParam"),
                false,
                "Неверное количество аргументов команды: должно быть %d, представлено %d.",
                0, 1
        );
    }

    /**
     * Проверка валидатора добавления книги с некорректным параметрами
     */
    @Test
    public void validateAddCommandWithIncorrectParams() {
        validateWithValidatorPrint(
                new AddBookCommandValidator(ioHandlerMock),
                new Command("addBook title author invalidYear"),
                false,
                "Год публикации должен быть представлен числом."
        );
    }

    /**
     * Проверка валидатора удаления книги с некорректным параметрами
     */
    @Test
    public void validateDeleteCommandWithIncorrectParams() {
        validateWithValidatorPrint(
                new DeleteBookCommandValidator(ioHandlerMock),
                new Command("deleteBook invalidId"),
                false,
                "ID должен быть представлен числом."
        );

    }

    /**
     * Проверка валидатора изменеия книги с некорректным параметром ID
     */
    @Test
    public void validateEditCommandWithIncorrectIDParam() {
        validateWithValidatorPrint(
                new DeleteBookCommandValidator(ioHandlerMock),
                new Command("deleteBook invalidId"),
                false,
                "ID должен быть представлен числом."
        );
    }

    /**
     * Проверка валидатора без параметров с некорректным параметром года
     */
    @Test
    public void validateEditCommandWithIncorrectYearParam() {
        validateWithValidatorPrint(
                new EditBookCommandValidator(ioHandlerMock),
                new Command("editBook 1 title author invalidYear"),
                false,
                "Год публикации должен быть представлен числом."
        );

    }


    /**
     * Общий метод для проверки валидатора с ожидаемым результатом
     */
    private void validateWithValidator(CommandValidator validator, Command command, boolean expected) {
        Assertions.assertEquals(expected, validator.validateCommand(command));
    }

    /**
     * Общий метод для проверки валидатора с форматированным сообщением
     */
    private void validateWithValidatorFormattedPrint(CommandValidator validator,
                                                     Command command,
                                                     boolean expected,
                                                     String message,
                                                     Object... args) {
        Assertions.assertEquals(expected, validator.validateCommand(command));
        if (!expected) {
            Mockito.verify(ioHandlerMock).printFormatted(message, args);
        }
    }

    /**
     * Общий метод для проверки валидатора с обычным сообщением
     */
    private void validateWithValidatorPrint(CommandValidator validator,
                                            Command command,
                                            boolean expected,
                                            String message) {
        Assertions.assertEquals(expected, validator.validateCommand(command));
        if (!expected) {
            Mockito.verify(ioHandlerMock).print(message);
        }
    }
}

