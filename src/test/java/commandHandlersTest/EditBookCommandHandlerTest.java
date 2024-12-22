package commandHandlersTest;

import org.example.Command;
import org.example.commandHandlers.CommandHandler;
import org.example.commandHandlers.EditBookCommandHandler;
import org.example.model.Book;
import org.example.service.LibraryService;
import org.example.util.IOHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * тесты для обработчика команды редактирования книги
 */
@ExtendWith(MockitoExtension.class)
public class EditBookCommandHandlerTest {

    private final IOHandler ioHandlerMock;
    private final LibraryService libraryServiceMock;

    private final CommandHandler commandHandler;

    private final Command editCommand = new Command("edit-book 1 \"title\" \"author\" 2023");

    private final Book book = new Book("title", "author", 2023);

    /**
     * Конструктор, в котором происходит инициализация полей
     */
    public EditBookCommandHandlerTest(@Mock LibraryService libraryService, @Mock IOHandler ioHandler) {
        this.ioHandlerMock = ioHandler;
        this.libraryServiceMock = libraryService;

        this.commandHandler = new EditBookCommandHandler(libraryServiceMock, ioHandlerMock);
    }

    /**
     * Проверяет корректность обработки команды редактирования книги с корректными данными и существующей книгой
     */
    @Test
    public void testHandleCorrectEditBookCommand() {
        Mockito.when(libraryServiceMock.getBookById(Long.parseLong(editCommand.getParams().get(0))))
                .thenReturn(book);

        commandHandler.executeCommand(editCommand);

        Mockito.verify(libraryServiceMock, Mockito.times(1)).editBook(
                book,
                editCommand.getParams().get(1),
                editCommand.getParams().get(2),
                Integer.parseInt(editCommand.getParams().get(3)));

        Mockito.verify(ioHandlerMock, Mockito.times(1)).print("Изменена книга:");
        Mockito.verify(ioHandlerMock, Mockito.times(1)).print(book.getBookInfo());
    }

    /**
     * Проверяет корректность обработки команды редактирования книги с корректными данными, но с несуществующей книгой
     */
    @Test
    public void testHandleCorrectEditBookCommandWithBookNull() {
        Mockito.when(libraryServiceMock.getBookById(Long.parseLong(editCommand.getParams().get(0))))
                .thenReturn(null);

        commandHandler.executeCommand(editCommand);


        Mockito.verify(libraryServiceMock, Mockito.never()).editBook(
                Mockito.any(Book.class),
                Mockito.any(String.class),
                Mockito.any(String.class),
                Mockito.anyInt());

        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .print("Книга с ID 1 не найдена.");
    }

    /**
     * Проверяет корректность обработки команды редактирования книги с неправильным количеством параметров
     */
    @Test
    public void testHandleEditBookCommandWithIncorrectArgsCount() {
        Command incorrectCommand = new Command("edit-book 1 \"title\"");

        testIncorrectCommand(incorrectCommand,
                "Неверное количество аргументов команды: должно быть 4, представлено 2.");
    }

    /**
     * Проверяет корректность обработки команды редактирования книги с неправильным ID
     */
    @Test
    public void testHandleEditBookCommandWithIncorrectID() {
        Command incorrectCommand = new Command("edit-book ID \"title\" \"author\" 2023");

        testIncorrectCommand(incorrectCommand, "ID должен быть представлен числом.");
    }

    /**
     * Проверяет корректность обработки команды редактирования книги с неправильным типом year
     */
    @Test
    public void testHandleEditBookCommandWithIncorrectYear() {
        Command incorrectCommand = new Command("edit-book 1 \"title\" \"author\" \"year\"");

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