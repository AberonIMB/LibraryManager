package commandHandlersTest;

import org.example.Command;
import org.example.commandHandlers.CommandHandler;
import org.example.commandHandlers.DeleteBookCommandHandler;
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

        Mockito.when(libraryServiceMock.getBookById(Long.parseLong(deleteCommand.getParams().get(0))))
                .thenReturn(book);

        commandHandler.executeCommand(deleteCommand);

        Mockito.verify(libraryServiceMock, Mockito.times(1))
                .deleteBook(book);

        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .print("Книга с ID null успешно удалена.");
    }

    /**
     * Проверяет корректность обработки команды удаления книги с корректными данными, но с несуществующей книгой
     */
    @Test
    public void testHandleCorrectDeleteBookCommandWithBookNull() {
        Mockito.when(libraryServiceMock.getBookById(Long.parseLong(deleteCommand.getParams().get(0))))
                .thenReturn(null);

        commandHandler.executeCommand(deleteCommand);

        Mockito.verify(libraryServiceMock, Mockito.never())
                .deleteBook(Mockito.any(Book.class));

        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .print("Книга с ID 1 не найдена.");
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
     * Проверяет корректность обработки команды удаления книги, которая выдана читателю
     */
    @Test
    public void testDeleteBookWithReader() {
        Book book = new Book("title", "author", 2023);
        book.setReader(new Reader("reader"));

        Mockito.when(libraryServiceMock.getBookById(Long.parseLong(deleteCommand.getParams().get(0))))
                .thenReturn(book);

        commandHandler.executeCommand(deleteCommand);

        Mockito.verify(libraryServiceMock, Mockito.never()).deleteBook(Mockito.any(Book.class));

        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .print("Невозможно выполнить операцию, так как книга \"title\" выдана читателю ID: null ФИО: reader.");
    }
}