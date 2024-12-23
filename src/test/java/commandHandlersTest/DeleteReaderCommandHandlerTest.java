package commandHandlersTest;

import org.example.Command;
import org.example.commandHandlers.CommandHandler;
import org.example.commandHandlers.DeleteReaderCommandHandler;
import org.example.model.Book;
import org.example.model.Reader;
import org.example.service.LibraryService;
import org.example.util.IOHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Тесты для обработчика команды удаления читателя
 */
@ExtendWith(MockitoExtension.class)
public class DeleteReaderCommandHandlerTest {
    private final IOHandler ioHandlerMock;
    private final LibraryService libraryServiceMock;
    private final CommandHandler commandHandler;

    private final Command deleteCommand = new Command("delete-reader 1");

    /**
     * Конструктор, в котором происходит инициализация полей
     */
    public DeleteReaderCommandHandlerTest(@Mock LibraryService libraryService, @Mock IOHandler ioHandler) {
        this.ioHandlerMock = ioHandler;
        this.libraryServiceMock = libraryService;

        this.commandHandler = new DeleteReaderCommandHandler(libraryServiceMock, ioHandlerMock);
    }


    /**
     * Проверяет корректность обработки команды удаления существующего читателя без книг
     */
    @Test
    public void testHandleCorrectDeleteReaderCommand() {
        Reader reader = new Reader("Name");

        Mockito.when(libraryServiceMock.getReaderById(Long.parseLong(deleteCommand.getParams().get(0))))
                .thenReturn(reader);

        commandHandler.executeCommand(deleteCommand);

        Mockito.verify(libraryServiceMock, Mockito.times(1))
                .deleteReader(reader);

        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .print("Читатель ID: null ФИО: Name успешно удален.");
    }

    /**
     * Проверяет корректность обработки команды удаления читателя с корректной командой, но с несуществующим читателем
     */
    @Test
    public void testHandleCorrectDeleteBookCommandWithReaderNull() {
        Mockito.when(libraryServiceMock.getReaderById(Long.parseLong(deleteCommand.getParams().get(0))))
                .thenReturn(null);

        commandHandler.executeCommand(deleteCommand);

        Mockito.verify(libraryServiceMock, Mockito.never())
                .deleteReader(Mockito.any(Reader.class));

        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .print("Читатель с ID 1 не найден.");
    }

    /**
     * Проверяет корректность обработки команды удаления читателя с книгами
     */
    @Test
    public void testHandleDeleteReaderCommandWithBook() {
        Reader reader = new Reader("Name");
        reader.getBooks().add(new Book("title", "author", 2023));
        //напрямую т.к. добавление книг у читателя происходит через бд

        Mockito.when(libraryServiceMock.getReaderById(Long.parseLong(deleteCommand.getParams().get(0))))
                .thenReturn(reader);

        commandHandler.executeCommand(deleteCommand);

        Mockito.verify(libraryServiceMock, Mockito.never()).deleteReader(Mockito.any(Reader.class));

        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .print("Невозможно удалить читателя ID: null ФИО: Name, так как у него есть выданные книги.");
    }

    /**
     * Проверяет корректность обработки команды удаления читетеля с неправильным количеством параметров
     */
    @Test
    public void testHandleDeleteReaderCommandWithIncorrectArgCount() {
        Command incorrectCommand = new Command("delete-reader 1 2");

        commandHandler.executeCommand(incorrectCommand);

        Mockito.verifyNoInteractions(libraryServiceMock);

        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .print("Неверное количество аргументов команды: должно быть 1, представлено 2.");
    }

    /**
     * Проверяет корректность обработки команды удаления читетеля с неправильным типом ID
     */
    @Test
    public void testHandleDeleteReaderCommandWithIncorrectID() {
        Command incorrectCommand = new Command("delete-reader a");

        commandHandler.executeCommand(incorrectCommand);

        Mockito.verifyNoInteractions(libraryServiceMock);

        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .print("ID должен быть представлен числом.");
    }
}
