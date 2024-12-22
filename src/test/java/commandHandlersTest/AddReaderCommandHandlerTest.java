package commandHandlersTest;

import org.example.Command;
import org.example.commandHandlers.AddReaderCommandHandler;
import org.example.commandHandlers.CommandHandler;
import org.example.model.Reader;
import org.example.service.LibraryService;
import org.example.util.IOHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Тесты для обработчика команды добавления читателя
 */
@ExtendWith(MockitoExtension.class)
public class AddReaderCommandHandlerTest {

    private final IOHandler ioHandlerMock;
    private final LibraryService libraryServiceMock;
    private final CommandHandler commandHandler;

    /**
     * Конструктор, в котором происходит инициализация полей
     */
    public AddReaderCommandHandlerTest(@Mock LibraryService libraryService, @Mock IOHandler ioHandler) {
        this.ioHandlerMock = ioHandler;
        this.libraryServiceMock = libraryService;

        this.commandHandler = new AddReaderCommandHandler(libraryServiceMock, ioHandlerMock);
    }

    /**
     * Проверяет корректность обработки команды добавления читателя
     */
    @Test
    public void testHandleCorrectAddReaderCommand() {
        Command addCommand = new Command("add-reader Name");
        Reader reader = new Reader("Name");


        Mockito.when(libraryServiceMock.addReader("Name"))
                .thenReturn(reader);

        commandHandler.executeCommand(addCommand);

        Mockito.verify(libraryServiceMock, Mockito.times(1))
                .addReader("Name");

        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .print("Читатель добавлен в систему:");
        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .print(reader.getReaderShortInfo());
    }

    /**
     * Проверяет корректность обработки команды добавления читателя с неправильным количеством параметров
     */
    @Test
    public void testHandleAddReaderCommandWithIncorrectArgsCount() {
        Command incorrectCommand = new Command("add-reader \"Name1\" \"Name2\"");
        commandHandler.executeCommand(incorrectCommand);

        Mockito.verifyNoInteractions(libraryServiceMock);

        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .print("Неверное количество аргументов команды: должно быть 1, представлено 2.");
    }
}