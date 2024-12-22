package commandHandlersTest;

import org.example.Command;
import org.example.commandHandlers.ShowReaderCommandHandler;
import org.example.model.Reader;
import org.example.service.LibraryService;
import org.example.util.IOHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Тесты для обработчика команды просмотра читателя
 */
@ExtendWith(MockitoExtension.class)
public class ShowReaderCommandHandlerTest {

    private final LibraryService libraryServiceMock;
    private final IOHandler ioHandlerMock;
    private final ShowReaderCommandHandler commandHandler;
    private final Command correctCommand = new Command("Show-reader 1");

    /**
     * Конструктор, в котором происходит инициализация полей
     */
    public ShowReaderCommandHandlerTest(@Mock LibraryService libraryService, @Mock IOHandler ioHandler) {
        this.libraryServiceMock = libraryService;
        this.ioHandlerMock = ioHandler;
        commandHandler = new ShowReaderCommandHandler(libraryService, ioHandler);
    }

    /**
     * Проверяет корректность обработки команды показа читателя с существующим читателем
     */
    @Test
    public void testHandleCorrectShowReaderCommand() {
        Reader reader = new Reader("Name");

        Mockito.when(libraryServiceMock.getReaderById(1L))
                .thenReturn(reader);

        commandHandler.executeCommand(correctCommand);

        Mockito.verify(libraryServiceMock).getReaderById(1L);
        Mockito.verify(ioHandlerMock).print(reader.getReaderInfo());
    }

    /**
     * Проверяет корректность обработки команды показа читателя с несуществующим читателем
     */
    @Test
    public void testHandleCorrectShowReaderCommandWithReaderNull() {

        Mockito.when(libraryServiceMock.getReaderById(1L))
                .thenReturn(null);

        commandHandler.executeCommand(correctCommand);

        Mockito.verify(libraryServiceMock).getReaderById(1L);
        Mockito.verify(ioHandlerMock).print("Читатель с ID 1 не найден.");
    }

    /**
     * Проверяет корректность обработки команды показа читателя с неправильным количеством параметров
     */
    @Test
    public void testHandleShowReaderCommandWithIncorrectArgsCount() {
        Command incorrectCommand = new Command("show-reader 1 2");

        testIncorrectCommand(incorrectCommand,
                "Неверное количество аргументов команды: должно быть 1, представлено 2.");
    }

    /**
     * Проверяет корректность обработки команды показа читателя с неправильным типом ID
     */
    @Test
    public void testHandleShowReaderCommandWithIncorrectID() {
        Command incorrectCommand = new Command("show-reader a");

        testIncorrectCommand(incorrectCommand, "ID должен быть представлен числом.");
    }

    /**
     * Тестирует выполнение команды с правалом валидации команды
     *
     * @param incorrectCommand Команда
     * @param exceptionMessage сообщение об ошибке
     */
    private void testIncorrectCommand(Command incorrectCommand, String exceptionMessage) {
        commandHandler.executeCommand(incorrectCommand);

        Mockito.verifyNoInteractions(libraryServiceMock);

        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .print(exceptionMessage);
    }
}
