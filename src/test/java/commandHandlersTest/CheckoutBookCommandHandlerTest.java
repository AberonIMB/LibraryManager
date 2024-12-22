package commandHandlersTest;

import org.example.Command;
import org.example.commandHandlers.CheckoutBookCommandHandler;
import org.example.commandHandlers.CommandHandler;
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
 * Тесты для обработчика команды выдачи книги
 */
@ExtendWith(MockitoExtension.class)
public class CheckoutBookCommandHandlerTest {

    private final LibraryService libraryServiceMock;
    private final IOHandler ioHandlerMock;
    private final CommandHandler commandHandler;
    private final Book book = new Book("Title", "Author", 2024);
    private final Reader reader = new Reader("Name");


    /**
     * Конструктор, в котором происходит инициализация полей
     */
    public CheckoutBookCommandHandlerTest(@Mock LibraryService libraryService, @Mock IOHandler ioHandler) {
        libraryServiceMock = libraryService;
        ioHandlerMock = ioHandler;
        commandHandler = new CheckoutBookCommandHandler(libraryService, ioHandler);
    }

    /**
     * Проверяет корректность обработки команды выдачи книги с корректными данными
     */
    @Test
    public void testHandleCorrectCheckoutBookCommand() {
        Command correctCommand = new Command("Checkout-book 1 1");

        Mockito.when(libraryServiceMock.getBookById(1L))
                .thenReturn(book);
        Mockito.when(libraryServiceMock.getReaderById(1L))
                .thenReturn(reader);

        commandHandler.executeCommand(correctCommand);

        Mockito.verify(libraryServiceMock)
                .checkoutBook(book, reader);

        Mockito.verify(ioHandlerMock)
                .print(String.format("Книга \"%s\" выдана читателю \"%s\"",
                        book.getTitle(), reader.getReaderShortInfo()));
    }


    /**
     * Проверяет корректность обработки команды выдачи книги с несуществующим читателем
     */
    @Test
    void testHandleCheckoutBookCommandHandlerWithNullReader() {
        Command command = new Command("Checkout-book 1 1");

        testCommandWithIncorrectStateValidation(book, null, command, "Читатель с ID 1 не найден.");
    }

    /**
     * Проверяет корректность обработки команды выдачи книги с несуществующей книгой
     */
    @Test
    void testHandleCheckoutBookCommandHandlerWithNullBook() {
        Command command = new Command("Checkout-book 1 1");

        testCommandWithIncorrectStateValidation(null, reader, command, "Книга с ID 1 не найдена.");
    }

    /**
     * Проверяет корректность обработки команды выдачи книги с несуществуюми книгой, читателем
     */
    @Test
    void testHandleCheckoutBookCommandHandlerWithNullBookAndReader() {
        Command command = new Command("Checkout-book 1 1");

        testCommandWithIncorrectStateValidation(null, null, command, "Книга с ID 1 не найдена.");
    }

    /**
     * Проверяет корректность обработки команды выдачи книги с неправильным количеством параметров
     */
    @Test
    public void testHandleCheckoutBookCommandHandlerWithIncorrectArgsCount() {
        Command incorrectCommand = new Command("checkout-book 1 2 3");

        testIncorrectCommand(incorrectCommand,
                "Неверное количество аргументов команды: должно быть 2, представлено 3.");
    }

    /**
     * Проверяет корректность обработки команды выдачи книги с неправильным типом ID книги
     */
    @Test
    public void testHandleCheckoutBookCommandHandlerWithIncorrectBookId() {
        Command incorrectCommand = new Command("checkout-book a 2");

        testIncorrectCommand(incorrectCommand,
                "ID должен быть представлен числом.");
    }

    /**
     * Проверяет корректность обработки команды выдачи книги с неправильным типом ID читателя
     */
    @Test
    public void testHandleCheckoutBookCommandHandlerWithIncorrectReaderId() {
        Command incorrectCommand = new Command("checkout-book 1 a");

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
    private void testCommandWithIncorrectStateValidation(Book book, Reader reader,
                                                         Command command, String exceptionMessage) {
        Mockito.when(libraryServiceMock.getBookById(1L))
                .thenReturn(book);
        Mockito.when(libraryServiceMock.getReaderById(1L))
                .thenReturn(reader);

        commandHandler.executeCommand(command);

        Mockito.verify(libraryServiceMock, Mockito.never())
                .checkoutBook(Mockito.any(Book.class), Mockito.any(Reader.class));

        Mockito.verify(ioHandlerMock)
                .print(exceptionMessage);
    }
}
