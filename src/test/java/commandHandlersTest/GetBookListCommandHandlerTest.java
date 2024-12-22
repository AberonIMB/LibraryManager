package commandHandlersTest;

import org.example.Command;
import org.example.commandHandlers.CommandHandler;
import org.example.commandHandlers.GetBookListCommandHandler;
import org.example.model.Book;
import org.example.service.LibraryService;
import org.example.util.IOHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

/**
 * Тесты для обработчика команды получения списка книг
 */
@ExtendWith(MockitoExtension.class)
public class GetBookListCommandHandlerTest {

    private final LibraryService libraryServiceMock;
    private final IOHandler ioHandlerMock;

    private final CommandHandler getBookListCommandHandler;

    private final Command getBookListCommand = new Command("list-books");

    private final List<Book> books = List.of(
            new Book("title", "author", 2023),
            new Book("title2", "author2", 2024));

    /**
     * Конструктор, в котором происходит инициализация полей
     */
    public GetBookListCommandHandlerTest(@Mock LibraryService libraryService, @Mock IOHandler ioHandler) {
        this.ioHandlerMock = ioHandler;
        this.libraryServiceMock = libraryService;

        this.getBookListCommandHandler = new GetBookListCommandHandler(
                libraryServiceMock, ioHandlerMock);
    }
    /**
     * Проверяет корректность обработки команды получения списка книг, когда они есть
     */
    @Test
    public void testHandleCorrectGetBookListCommandWithNotEmptyList() {
        Mockito.when(libraryServiceMock.getListBooks()).thenReturn(books);

        getBookListCommandHandler.executeCommand(getBookListCommand);

        Mockito.verify(libraryServiceMock, Mockito.times(1)).getListBooks();
        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .printFormatted("%d) %s", 1,
                        books.get(0).getBookShortInfo());

        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .printFormatted("%d) %s", 2,
                        books.get(1).getBookShortInfo());
    }

    /**
     * Проверяет корректность обработки команды получения списка книг, когда их нет
     */
    @Test
    public void testHandleCorrectGetBookListCommandWithEmptyList() {
        Mockito.when(libraryServiceMock.getListBooks()).thenReturn(List.of());

        getBookListCommandHandler.executeCommand(getBookListCommand);

        Mockito.verify(libraryServiceMock, Mockito.times(1)).getListBooks();
        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .print("Список книг пуст.");
    }

    /**
     * Проверяет корректность обработки команды получения списка книг с неправильным количеством параметров
     */
    @Test
    public void testHandleGetBookListCommandWithIncorrectArgsCount() {
        Command incorrectCommand = new Command("list-books \"title\"");

        getBookListCommandHandler.executeCommand(incorrectCommand);

        Mockito.verifyNoInteractions(libraryServiceMock);

        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .print("Неверное количество аргументов команды: должно быть 0, представлено 1.");
    }
}