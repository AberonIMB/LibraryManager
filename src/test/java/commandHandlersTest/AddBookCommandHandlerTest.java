package commandHandlersTest;

import org.example.Command;
import org.example.commandHandlers.AddBookCommandHandler;
import org.example.commandHandlers.CommandHandler;
import org.example.commandValidators.AddBookCommandValidator;
import org.example.commandValidators.CommandValidator;
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

    private final CommandValidator commandValidatorMock;

    private final LibraryService libraryServiceMock;

    private final CommandHandler commandHandler;

    private final Command addCommand = new Command("add-book \"title\" \"author\" 2023");

    private final Book book = new Book("title", "author", 2023);

    /**
     * Конструктор, в котором происходит инициализация полей
     */
    public AddBookCommandHandlerTest(@Mock AddBookCommandValidator commandValidator, @Mock LibraryService libraryService, @Mock IOHandler ioHandler) {
        this.ioHandlerMock = ioHandler;
        this.commandValidatorMock = commandValidator;
        this.libraryServiceMock = libraryService;

        this.commandHandler = new AddBookCommandHandler(
                commandValidatorMock, libraryServiceMock, ioHandlerMock);
    }

    /**
     * Проверяет корректность обработки команды добавления книги с корректными данными
     */
    @Test
    public void testHandleCorrectAddBookCommand() {

        Mockito.when(commandValidatorMock.validateCommand(addCommand)).thenReturn(true);

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
     * Проверяет корректность обработки команды добавления книги с некорректными данными
     */
    @Test
    public void testHandleIncorrectAddBookCommand() {
        Mockito.when(commandValidatorMock.validateCommand(addCommand)).thenReturn(false);

        commandHandler.executeCommand(addCommand);

        Mockito.verifyNoInteractions(libraryServiceMock, ioHandlerMock);
    }
}
