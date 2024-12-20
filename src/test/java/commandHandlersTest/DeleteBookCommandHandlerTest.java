package commandHandlersTest;

import org.example.Command;
import org.example.commandHandlers.CommandHandler;
import org.example.commandHandlers.DeleteBookCommandHandler;
import org.example.model.Book;
import org.example.service.LibraryService;
import org.example.util.IOHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * тесты для обработчика команды удаления книги
 */
@ExtendWith(MockitoExtension.class)
public class DeleteBookCommandHandlerTest {

    private final IOHandler ioHandlerMock;
    private final LibraryService libraryServiceMock;
    private final CommandHandler commandHandler;

    private final Command deleteCommand = new Command("delete-book 1");

    /**
     * Конструктор, в котором происходит инициализация полей
     */
    public DeleteBookCommandHandlerTest(@Mock LibraryService libraryService, @Mock IOHandler ioHandler) {
        this.ioHandlerMock = ioHandler;
        this.libraryServiceMock = libraryService;

        this.commandHandler = new DeleteBookCommandHandler(libraryServiceMock, ioHandlerMock);
    }


    /**
     * Проверяет корректность обработки команды удаления книги с корректными данными и с существующей книгой
     */
    @Test
    public void testHandleCorrectDeleteBookCommandWithBookNotNull() {
        Book book = new Book("title", "author", 2023);
        testCorrectCommand(book, "Книга с ID %s успешно удалена.");
    }

    /**
     * Проверяет корректность обработки команды удаления книги с корректными данными, но с несуществующей книгой
     */
    @Test
    public void testHandleCorrectDeleteBookCommandWithBookNull() {
        testCorrectCommand(null, "Книга с ID %s не найдена.");
    }

    /**
     * Проверяет корректность обработки команды удаления книги с некорректными данными
     */
    @Test
    public void testHandleIncorrectDeleteBookCommand() {
        Command incorrectCommand = new Command("delete-book 1 2");

        commandHandler.executeCommand(incorrectCommand);

        Mockito.verifyNoInteractions(libraryServiceMock);

        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .print("Неверное количество аргументов команды: должно быть 1, представлено 2.");
    }

    /**
     * Тестирует корректную команду
     * @param book Книга
     * @param exceptionMessage Сообщение об ошибке
     */
    private void testCorrectCommand(Book book, String exceptionMessage) {
        Mockito.when(libraryServiceMock.deleteBook(Long.parseLong(deleteCommand.getParams().get(0)))).thenReturn(book);

        commandHandler.executeCommand(deleteCommand);

        Mockito.verify(libraryServiceMock, Mockito.times(1))
                .deleteBook(Long.parseLong(deleteCommand.getParams().get(0)));

        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .printFormatted(exceptionMessage, deleteCommand.getParams().get(0));
    }
}