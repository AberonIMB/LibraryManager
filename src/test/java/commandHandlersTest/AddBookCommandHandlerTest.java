package commandHandlersTest;

import org.example.Command;
import org.example.commandHandlers.AddBookCommandHandler;
import org.example.commandHandlers.CommandHandler;
import org.example.model.Book;
import org.example.service.LibraryService;
import org.example.util.IOHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Тесты для обработчика команды добавления книги
 */
@ExtendWith(MockitoExtension.class)
public class AddBookCommandHandlerTest {

    private final IOHandler ioHandlerMock;

    private final LibraryService libraryServiceMock;

    private final CommandHandler commandHandler;

    private final Book book = new Book("title", "author", 2023);

    /**
     * Конструктор, в котором происходит инициализация полей
     */
    public AddBookCommandHandlerTest(@Mock LibraryService libraryService, @Mock IOHandler ioHandler) {
        this.ioHandlerMock = ioHandler;
        this.libraryServiceMock = libraryService;

        this.commandHandler = new AddBookCommandHandler(libraryServiceMock, ioHandlerMock);
    }

    /**
     * Проверяет корректность обработки команды добавления книги с корректными данными
     */
    @Test
    public void testHandleCorrectAddBookCommand() {
        Command addCommand = new Command("add-book \"title\" \"author\" 2023");

        Mockito.when(libraryServiceMock.addBook(
                        addCommand.getParams().get(0),
                        addCommand.getParams().get(1),
                        Integer.parseInt(addCommand.getParams().get(2))))
                .thenReturn(book);

        commandHandler.executeCommand(addCommand);

        Mockito.verify(libraryServiceMock, Mockito.times(1))
                .addBook("title", "author", 2023);

        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .print("Добавлена книга:");
        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .print(book.getBookInfo());
    }

    /**
     * Проверяет корректность обработки команды добавления книги с неправильным количеством параметров
     */
    @Test
    public void testHandleAddBookCommandWithIncorrectArgsCount() {
        Command incorrectCommand = new Command("add-book \"title\" \"author\"");

        testIncorrectCommand(incorrectCommand,
                "Неверное количество аргументов команды: должно быть 3, представлено 2.");
    }

    /**
     * Проверяет корректность обработки команды добавления книги с неправилньым типом year
     */
    @Test
    public void testHandleAddBookCommandWithIncorrectYear() {
        Command incorrectCommand = new Command("add-book \"title\" \"author\" \"year\"");

        testIncorrectCommand(incorrectCommand, "Год должен быть представлен числом.");
    }

    /**
     * Тестирует выполнение неправилньой команды
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