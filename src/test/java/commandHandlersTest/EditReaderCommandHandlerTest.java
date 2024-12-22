package commandHandlersTest;


import org.example.Command;
import org.example.commandHandlers.CommandHandler;
import org.example.commandHandlers.EditReaderCommandHandler;
import org.example.model.Reader;
import org.example.service.LibraryService;
import org.example.util.IOHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Тесты для обработчика команды редактирования читателя
 */
@ExtendWith(MockitoExtension.class)
public class EditReaderCommandHandlerTest {

    private final IOHandler ioHandlerMock;
    private final LibraryService libraryServiceMock;

    private final CommandHandler commandHandler;

    private final Command editCommand = new Command("edit-reader 1 \"NewName\"");

    private final Reader reader = new Reader("Name");

    /**
     * Конструктор, в котором происходит инициализация полей
     */
    public EditReaderCommandHandlerTest(@Mock LibraryService libraryService, @Mock IOHandler ioHandler) {
        this.ioHandlerMock = ioHandler;
        this.libraryServiceMock = libraryService;

        this.commandHandler = new EditReaderCommandHandler(libraryServiceMock, ioHandlerMock);
    }

    /**
     * Проверяет корректность обработки команды редактирования читателя с корректной командой и существующим читателем
     */
    @Test
    public void testHandleCorrectEditReaderCommand() {
        Mockito.when(libraryServiceMock.getReaderById(Long.parseLong(editCommand.getParams().get(0))))
                .thenReturn(reader);

        commandHandler.executeCommand(editCommand);

        Mockito.verify(libraryServiceMock, Mockito.times(1))
                .editReader(reader, "NewName");

        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .print(String.format("Имя читателя %d изменено на %s",reader.getId(), reader.getName()));
    }

    /**
     * Проверяет корректность обработки команды редактирования читателя с корректной командой, но с несуществующим читателем
     */
    @Test
    public void testHandleCorrectEditReaderCommandWithReaderNull() {
        Mockito.when(libraryServiceMock.getReaderById(Long.parseLong(editCommand.getParams().get(0))))
                .thenReturn(null);

        commandHandler.executeCommand(editCommand);


        Mockito.verify(libraryServiceMock, Mockito.never()).editReader(
                Mockito.any(Reader.class),
                Mockito.any(String.class));

        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .print("Читатель с ID 1 не найден.");
    }

    /**
     * Проверяет корректность обработки команды редактирования читателя  с неправильным количеством параметров
     */
    @Test
    public void testHandleEditReaderCommandWithIncorrectArgsCount() {
        Command incorrectCommand = new Command("edit-reader 1 \"NewName\" \"NewSurname\"");

        testIncorrectCommand(incorrectCommand,
                "Неверное количество аргументов команды: должно быть 2, представлено 3.");
    }

    /**
     * Проверяет корректность обработки команды редактирования читателя с неправильным типом ID
     */
    @Test
    public void testHandleEditReaderCommandWithIncorrectID() {
        Command incorrectCommand = new Command("edit-reader ID \"NewName\"");

        testIncorrectCommand(incorrectCommand, "ID должен быть представлен числом.");
    }


    /**
     * Тестирует выполнение некорректной команды
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
