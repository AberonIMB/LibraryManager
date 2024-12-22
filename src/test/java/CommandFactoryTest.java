import org.example.Command;
import org.example.CommandFactory;
import org.example.commandHandlers.*;
import org.example.service.LibraryService;
import org.example.util.IOHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Тестирует класс CommandFactory
 */
@ExtendWith(MockitoExtension.class)
public class CommandFactoryTest {

    private final CommandFactory commandFactory;

    /**
     * Конструктор для инициализации commandFactory
     */
    public CommandFactoryTest(@Mock IOHandler ioHandlerMock, @Mock LibraryService libraryServiceMock) {
        commandFactory = new CommandFactory(ioHandlerMock, libraryServiceMock);
    }

    /**
     * Проверяет, что для существующей команды возвращается корректный обработчик
     */
    @Test
    void shouldReturnCorrectHandlerForKnownCommand() {
        Command addBookCommand = new Command("add-book");
        CommandHandler handler = commandFactory.getCommandHandler(addBookCommand);

        Assertions.assertNotNull(handler, "Обработчик для 'add-book' не должен быть null");
        Assertions.assertInstanceOf(AddBookCommandHandler.class, handler,
                "Обработчик для 'add-book' должен быть класса AddBookCommandHandler");
    }

    /**
     * Проверяет, что для неизвестной команды возвращается обработчик "unknown"
     */
    @Test
    void shouldReturnUnknownHandlerForUnknownCommand() {
        Command unknownCommand = new Command("some-command");
        CommandHandler handler = commandFactory.getCommandHandler(unknownCommand);

        Assertions.assertNotNull(handler, "Обработчик для неизвестной команды не должен быть null");
        Assertions.assertInstanceOf(UnknownCommandHandler.class, handler,
                "Обработчик для неизвестной команды должен быть класса UnknownCommandHandler");
    }

    /**
     * Проверяет, что правильные обработчики создаются для всех зарегистрированных команд
     */
    @Test
    void shouldReturnHandlersForAllCommands() {
        String[] expectedCommands = {"add-book", "list-books", "edit-book", "delete-book", "help", "unknown"};
        var expectedClasses = new Class[]{AddBookCommandHandler.class,
                GetBookListCommandHandler.class,
                EditBookCommandHandler.class,
                DeleteBookCommandHandler.class,
                HelpCommandHandler.class,
                UnknownCommandHandler.class};
        for (int i = 0; i < expectedClasses.length; i++) {
            Command command = new Command(expectedCommands[i]);
            CommandHandler handler = commandFactory.getCommandHandler(command);
            Assertions.assertInstanceOf(expectedClasses[i], handler);
        }
    }
}
