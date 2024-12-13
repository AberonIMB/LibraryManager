package commandHandlersTest;

import org.example.Command;
import org.example.commandHandlers.CommandHandler;
import org.example.commandHandlers.DeleteBookCommandHandler;
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

/**
 * тесты для обработчика команды удаления книги
 */
@ExtendWith(MockitoExtension.class)
public class DeleteBookCommandHandlerTest {

    private final IOHandler ioHandlerMock;
    private final CommandValidator commandValidatorMock;
    private final LibraryService libraryServiceMock;

    private final CommandHandler commandHandler;

    private final Command deleteCommand = new Command("delete-book 1");

    private final Book book = new Book("title", "author", 2023);

    /**
     * Конструктор, в котором происходит инициализация полей
     */
    public DeleteBookCommandHandlerTest(@Mock CommandValidator commandValidator, @Mock LibraryService libraryService, @Mock IOHandler ioHandler) {
        this.ioHandlerMock = ioHandler;
        this.commandValidatorMock = commandValidator;
        this.libraryServiceMock = libraryService;

        this.commandHandler = new DeleteBookCommandHandler(
                commandValidatorMock, libraryServiceMock, ioHandlerMock);
    }


    /**
     * Проверяет корректность обработки команды удаления книги с корректными данными и с существующей книгой
     */
    @Test
    public void testHandleCorrectDeleteBookCommandWithBookNotNull() {
        Mockito.when(libraryServiceMock.deleteBook(Long.parseLong(deleteCommand.getParams().get(0)))).thenReturn(book);

        commandHandler.executeCommand(deleteCommand);

        Mockito.verify(libraryServiceMock, Mockito.times(1))
                .deleteBook(Long.parseLong(deleteCommand.getParams().get(0)));

        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .printFormatted("Книга с ID %s успешно удалена.", deleteCommand.getParams().get(0));
    }

    /**
     * Проверяет корректность обработки команды удаления книги с корректными данными, но с несуществующей книгой
     */
    @Test
    public void testHandleCorrectDeleteBookCommandWithBookNull() {
        Mockito.when(libraryServiceMock.deleteBook(Long.parseLong(deleteCommand.getParams().get(0)))).thenReturn(null);

        commandHandler.executeCommand(deleteCommand);

        Mockito.verify(libraryServiceMock, Mockito.times(1))
                .deleteBook(Long.parseLong(deleteCommand.getParams().get(0)));

        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .printFormatted("Книга с ID %s не найдена.", deleteCommand.getParams().get(0));
    }

    /**
     * Проверяет корректность обработки команды удаления книги с некорректными данными
     */
    @Test
    public void testHandleIncorrectDeleteBookCommand() throws ArgumentsCountException, InvalidYearException, InvalidIdException {
        Mockito.doThrow(new ArgumentsCountException(1, 2))
                .when(commandValidatorMock).validateCommand(deleteCommand);

        commandHandler.executeCommand(deleteCommand);

        Mockito.verifyNoInteractions(libraryServiceMock);

        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .print("Неверное количество аргументов команды: должно быть 1, представлено 2.");
    }
}