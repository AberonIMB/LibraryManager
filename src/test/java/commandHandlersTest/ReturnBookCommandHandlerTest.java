package commandHandlersTest;

import org.example.Command;
import org.example.commandHandlers.CommandHandler;
import org.example.commandHandlers.ReturnBookCommandHandler;
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
 * Тесты для обработчика команды возврата книги
 */
@ExtendWith(MockitoExtension.class)
public class ReturnBookCommandHandlerTest {

    private final LibraryService libraryServiceMock;
    private final IOHandler ioHandlerMock;
    private final CommandHandler commandHandler;
    private final Book book = new Book("Title", "Author", 2024);


    /**
     * Конструктор, в котором происходит инициализация полей
     */
    public ReturnBookCommandHandlerTest(@Mock LibraryService libraryService, @Mock IOHandler ioHandler) {
        libraryServiceMock = libraryService;
        ioHandlerMock = ioHandler;
        commandHandler = new ReturnBookCommandHandler(libraryService, ioHandler);
    }

    /**
     * Проверяет корректность обработки команды возврата книги с корректными данными
     */
    @Test
    public void testHandleCorrectReturnBookCommand() {
        Command correctCommand = new Command("return-book 1");
        book.setReader(new Reader("Name"));
        Mockito.when(libraryServiceMock.getBookById(1L))
                .thenReturn(book);


        commandHandler.executeCommand(correctCommand);

        Mockito.verify(libraryServiceMock)
                .returnBook(book);

        Mockito.verify(ioHandlerMock)
                .print(String.format("Книга \"%s\" возвращена в библиотеку", book.getTitle()));
    }

    /**
     * Проверяет корректность обработки команды возврата книги с несуществующей книгой
     */
    @Test
    void testHandleReturnBookCommandHandlerWithNullBook() {
        Command command = new Command("return-book 1");

        testCommandWithIncorrectStateValidation(null, command, "Книга с ID 1 не найдена.");
    }

    /**
     * Проверяет корректность обработки команды возврата книги с несуществующим книгой
     */
    @Test
    void testHandleReturnBookCommandHandlerWithAlreadyReturnedBook() {
        Command command = new Command("return-book 1");
        book.setReader(null);

        testCommandWithIncorrectStateValidation(book, command,
                "Невозможно выполнить операцию, так как книга \"Title\" уже находится в библиотеке.");
    }

    /**
     * Проверяет корректность обработки команды возврата книги с неправильным количеством параметров
     */
    @Test
    public void testHandleReturnBookCommandHandlerWithIncorrectArgsCount() {
        Command incorrectCommand = new Command("return-book 1 3");

        testIncorrectCommand(incorrectCommand,
                "Неверное количество аргументов команды: должно быть 1, представлено 2.");
    }

    /**
     * Проверяет корректность обработки команды возврата книги с неправильным типом ID книги
     */
    @Test
    public void testHandleReturnBookCommandHandlerWithIncorrectBookId() {
        Command incorrectCommand = new Command("return-book a");

        testIncorrectCommand(incorrectCommand,
                "ID должен быть представлен числом.");
    }

    /**
     * Тестирует выполнение некорректной команды
     *
     * @param incorrectCommand Команда
     * @param exceptionMessage сообщение об ошибке валидатора команды
     */
    private void testIncorrectCommand(Command incorrectCommand, String exceptionMessage) {
        commandHandler.executeCommand(incorrectCommand);

        Mockito.verifyNoInteractions(libraryServiceMock);

        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .print(exceptionMessage);
    }

    /**
     * Тестирует выполнение корректной команды с правалом валидации состояния
     */
    private void testCommandWithIncorrectStateValidation(Book book, Command command, String exceptionMessage) {
        Mockito.when(libraryServiceMock.getBookById(1L))
                .thenReturn(book);

        commandHandler.executeCommand(command);

        Mockito.verify(libraryServiceMock, Mockito.never())
                .returnBook(Mockito.any(Book.class));

        Mockito.verify(ioHandlerMock)
                .print(exceptionMessage);
    }
}
