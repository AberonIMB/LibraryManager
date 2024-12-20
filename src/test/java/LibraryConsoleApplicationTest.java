import org.example.Command;
import org.example.CommandFactory;
import org.example.LibraryConsoleApplication;
import org.example.commandHandlers.CommandHandler;
import org.example.util.IOHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Тестирует LibraryConsoleApplication 
 */
@ExtendWith(MockitoExtension.class)
public class LibraryConsoleApplicationTest {

    private final LibraryConsoleApplication libraryConsoleApplication;

    private final CommandFactory commandFactoryMock;

    private final CommandHandler commandHandlerMock;

    private final IOHandler ioHandlerMock;

    /**
     * Констрктор для инициализации libraryConsoleApplication, commandFactoryMock, commandHandlerMock, ioHandlerMock
     */
    public LibraryConsoleApplicationTest(@Mock CommandFactory commandFactoryMock,
                                         @Mock IOHandler ioHandlerMock,
                                         @Mock CommandHandler commandHandlerMock) {
        this.commandFactoryMock = commandFactoryMock;
        this.ioHandlerMock = ioHandlerMock;
        libraryConsoleApplication = new LibraryConsoleApplication(ioHandlerMock,
                commandFactoryMock);
        this.commandHandlerMock = commandHandlerMock;
    }

    /**
     * Проверяет, что при команде вызывается getCommandHandler у commandFactory с соответсвующей командой
     */
    @Test
    public void shouldCallGetCommandHandlerFromCommandFactory() {
        Command commandAddBook = new Command("add-book title author 0");

        Mockito.when(commandFactoryMock.getCommandHandler(commandAddBook))
                .thenReturn(commandHandlerMock);

        Mockito.when(ioHandlerMock.readInput())
                .thenReturn("add-book title author 0")
                .thenReturn("stop"); // Чтобы выйти из бесконечного цикла

        libraryConsoleApplication.start();
        Mockito.verify(commandFactoryMock).getCommandHandler(Mockito.eq(commandAddBook));
    }

    /**
     * Проверяет, что start вышел из цикла при команде stop
     * (getCommandHandler у commandFactory не вызывается)
     */
    @Test
    public void shouldNotCallGetCommandHandlerIfStop() {
        Mockito.when(ioHandlerMock.readInput())
                .thenReturn("stop");

        libraryConsoleApplication.start();
        Mockito.verify(commandFactoryMock, Mockito.never()).getCommandHandler(Mockito.any(Command.class));
    }

    /**
     * Проверяет, что при пустой команде цикл делает continue
     * (getCommandHandler вызывается всего 1 раз с командой следующей после пустой команды)
     */
    @Test
    public void shouldNotCallGetCommandHandlerIfNull() {
        Command commandAddBook = new Command("add-book title author 0");
        Command blankCommand = new Command("");

        Mockito.when(commandFactoryMock.getCommandHandler(commandAddBook))
                .thenReturn(commandHandlerMock);

        Mockito.when(ioHandlerMock.readInput())
                .thenReturn("")
                .thenReturn("add-book title author 0")
                .thenReturn("stop");

        libraryConsoleApplication.start();

        Mockito.verify(commandFactoryMock, Mockito.never()).getCommandHandler(Mockito.eq(blankCommand));
        Mockito.verify(commandFactoryMock).getCommandHandler(Mockito.eq(commandAddBook));
    }
}