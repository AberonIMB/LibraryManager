package commandHandlersTest;

import org.example.Command;
import org.example.commandHandlers.CommandHandler;
import org.example.commandHandlers.GetReaderListCommandHandler;
import org.example.model.Reader;
import org.example.service.LibraryService;
import org.example.util.IOHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

/**
 * Тесты для обработчика команды получения списка читателей
 */
@ExtendWith(MockitoExtension.class)
public class GetReaderListCommandHandlerTest {

    private final LibraryService libraryServiceMock;
    private final IOHandler ioHandlerMock;
    private final CommandHandler getReaderListCommandHandler;
    private final Command getReaderListCommand = new Command("list-readers");
    private final List<Reader> readers = List.of(
            new Reader("Name1"),
            new Reader("Name2"));

    /**
     * Конструктор, в котором происходит инициализация полей
     */
    public GetReaderListCommandHandlerTest(@Mock LibraryService libraryService, @Mock IOHandler ioHandler) {
        this.ioHandlerMock = ioHandler;
        this.libraryServiceMock = libraryService;

        this.getReaderListCommandHandler = new GetReaderListCommandHandler(
                libraryServiceMock, ioHandlerMock);
    }
    /**
     * Проверяет корректность обработки команды получения списка читателей, когда они есть
     */
    @Test
    public void testHandleCorrectGetBookListCommandWithNotEmptyList() {
        Mockito.when(libraryServiceMock.getListReaders()).thenReturn(readers);

        getReaderListCommandHandler.executeCommand(getReaderListCommand);

        Mockito.verify(libraryServiceMock, Mockito.times(1)).getListReaders();
        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .printFormatted("%d) %s", 1,
                        readers.get(0).getReaderShortInfo());

        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .printFormatted("%d) %s", 2,
                        readers.get(1).getReaderShortInfo());
    }

    /**
     * Проверяет корректность обработки команды получения списка читателей, когда их нет
     */
    @Test
    public void testHandleCorrectGetBookListCommandWithEmptyList() {
        Mockito.when(libraryServiceMock.getListReaders()).thenReturn(List.of());

        getReaderListCommandHandler.executeCommand(getReaderListCommand);

        Mockito.verify(libraryServiceMock, Mockito.times(1)).getListReaders();
        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .print("Список читателей пуст.");
    }

    /**
     * Проверяет корректность обработки команды получения списка книг с неправильным количеством параметров
     */
    @Test
    public void testHandleGetBookListCommandWithIncorrectArgsCount() {
        Command incorrectCommand = new Command("list-readers \"list\"");

        getReaderListCommandHandler.executeCommand(incorrectCommand);

        Mockito.verifyNoInteractions(libraryServiceMock);

        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .print("Неверное количество аргументов команды: должно быть 0, представлено 1.");
    }
}
