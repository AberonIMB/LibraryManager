import org.example.Command;
import org.example.CommandFactory;
import org.example.commandHandlers.AddBookCommandHandler;
import org.example.commandHandlers.CommandHandler;
import org.example.commandHandlers.UnknownCommandHandler;
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

        Assertions.assertNotNull(handler, "Handler for 'add-book' should not be null");
        Assertions.assertInstanceOf(AddBookCommandHandler.class, handler,
                "Handler for 'add-book' should AddBookCommandHandler");
    }

    /**
     * Проверяет, что для неизвестной команды возвращается обработчик "unknown"
     */
    @Test
    void shouldReturnUnknownHandlerForUnknownCommand() {
        Command unknownCommand = new Command("some-command");
        CommandHandler handler = commandFactory.getCommandHandler(unknownCommand);

        Assertions.assertNotNull(handler, "Handler for unknown command should not be null");
        Assertions.assertInstanceOf(UnknownCommandHandler.class, handler,
                "Handler for unknown command should be of type UnknownCommandHandler");
    }

    /**
     * Проверяет, что правильные обработчики создаются для всех зарегистрированных команд
     */
    @Test
    void shouldReturnHandlersForAllCommands() {
        String[] expectedCommands = {"add-book", "list-books", "edit-book", "delete-book", "help", "stop", "unknown"};
        for (String commandName : expectedCommands) {
            Command command = new Command(commandName);
            CommandHandler handler = commandFactory.getCommandHandler(command);
            Assertions.assertNotNull(handler, "Handler for %s should not be null".formatted(commandName));
        }
    }
}
