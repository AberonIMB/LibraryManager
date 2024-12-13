package commandHandlersTest;

import org.example.Command;
import org.example.commandHandlers.HelpCommandHandler;
import org.example.commandValidators.CommandValidator;
import org.example.exceptions.ArgumentsCountException;
import org.example.exceptions.InvalidIdException;
import org.example.exceptions.InvalidYearException;
import org.example.util.IOHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * тесты для обработчика команды получения справки
 */
@ExtendWith(MockitoExtension.class)
public class HelpCommandHandlerTest {

    private final IOHandler ioHandlerMock;
    private final CommandValidator commandValidatorMock;
    private final HelpCommandHandler helpCommandHandler;

    private final Command command = new Command("help");

    /**
     * Конструктор, в котором происходит инициализация полей
     */
    public HelpCommandHandlerTest(@Mock IOHandler ioHandler, @Mock CommandValidator commandValidator) {
        this.ioHandlerMock = ioHandler;
        this.commandValidatorMock = commandValidator;

        this.helpCommandHandler = new HelpCommandHandler(ioHandler, commandValidator);
    }

    /**
     * Проверяет корректность обработки команды получения справки с корректными данными
     */
    @Test
    public void testHandleCorrectHelpCommand() {
        helpCommandHandler.executeCommand(command);

        Mockito.verify(ioHandlerMock).print("""
                Доступные команды:
                \t- add-book "<название>" "<автор>" <год издания> – Добавить книгу
                \t- list-books – Просмотреть список книг
                \t- edit-book <ID книги> “<название>” “<автор>” <год издания> - Изменить книгу
                \t- delete-book <ID книги> – Удалить книгу
                \t- help – Справка
                \t- stop - Завершить работу""");
    }

    /**
     * Проверяет корректность обработки команды получения справки с некорректными данными
     */
    @Test
    public void testHandleIncorrectHelpCommand() throws ArgumentsCountException, InvalidYearException, InvalidIdException {
        Mockito.doThrow(new ArgumentsCountException(0, 2))
                .when(commandValidatorMock).validateCommand(command);

        helpCommandHandler.executeCommand(command);

        Mockito.verify(ioHandlerMock)
                .print("Неверное количество аргументов команды: должно быть 0, представлено 2.");

        Mockito.verify(ioHandlerMock, Mockito.never()).print("""
                Доступные команды:
                \t- add-book "<название>" "<автор>" <год издания> – Добавить книгу
                \t- list-books – Просмотреть список книг
                \t- edit-book <ID книги> “<название>” “<автор>” <год издания> - Изменить книгу
                \t- delete-book <ID книги> – Удалить книгу
                \t- help – Справка
                \t- stop - Завершить работу""");
    }
}