package commandHandlersTest;

import org.example.Command;
import org.example.commandHandlers.CommandHandler;
import org.example.commandHandlers.GetBookListCommandHandler;
import org.example.commandValidators.CommandValidator;
import org.example.exceptions.ArgumentsCountException;
import org.example.exceptions.InvalidIdException;
import org.example.exceptions.InvalidYearException;
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
 * тесты для обработчика команды получения списка книг
 */
@ExtendWith(MockitoExtension.class)
public class GetBookListCommandHandlerTest {

    private final LibraryService libraryServiceMock;
    private final IOHandler ioHandlerMock;
    private final CommandValidator commandValidatorMock;

    private final CommandHandler getBookListCommandHandler;

    private final Command getBookListCommand = new Command("list-books");

    private final List<Book> books = List.of(
            new Book("title", "author", 2023),
            new Book("title2", "author2", 2024));

    /**
     * Конструктор, в котором происходит инициализация полей
     */
    public GetBookListCommandHandlerTest(@Mock CommandValidator commandValidator, @Mock LibraryService libraryService, @Mock IOHandler ioHandler) {
        this.ioHandlerMock = ioHandler;
        this.commandValidatorMock = commandValidator;
        this.libraryServiceMock = libraryService;

        this.getBookListCommandHandler = new GetBookListCommandHandler(
                libraryServiceMock, ioHandlerMock, commandValidatorMock);
    }
    /**
     * Проверяет корректность обработки команды получения списка книг с непустым списком
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
     * Проверяет корректность обработки команды получения списка книг с пустым списком
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
     * Проверяет корректность обработки команды получения списка книг с некорректными данными
     */
    @Test
    public void testHandleIncorrectGetBookListCommand() throws ArgumentsCountException, InvalidYearException, InvalidIdException {
        Mockito.doThrow(new ArgumentsCountException(0, 2))
                .when(commandValidatorMock).validateCommand(getBookListCommand);

        getBookListCommandHandler.executeCommand(getBookListCommand);

        Mockito.verifyNoInteractions(libraryServiceMock);

        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .print("Неверное количество аргументов команды: должно быть 0, представлено 2.");
    }
}
