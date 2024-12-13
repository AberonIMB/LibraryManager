package commandHandlersTest;

import org.example.Command;
import org.example.commandHandlers.CommandHandler;
import org.example.commandHandlers.EditBookCommandHandler;
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
 * тесты для обработчика команды редактирования книги
 */
@ExtendWith(MockitoExtension.class)
public class EditBookCommandHandlerTest {

    private final IOHandler ioHandlerMock;
    private final CommandValidator commandValidatorMock;
    private final LibraryService libraryServiceMock;

    private final CommandHandler commandHandler;

    private final Command editCommand = new Command("edit-book 1 \"title\" \"author\" 2023");

    private final Book book = new Book("title", "author", 2023);

    /**
     * Конструктор, в котором происходит инициализация полей
     */
    public EditBookCommandHandlerTest(@Mock CommandValidator commandValidator, @Mock LibraryService libraryService, @Mock IOHandler ioHandler) {
        this.ioHandlerMock = ioHandler;
        this.commandValidatorMock = commandValidator;
        this.libraryServiceMock = libraryService;

        this.commandHandler = new EditBookCommandHandler(
                commandValidatorMock, libraryServiceMock, ioHandlerMock);
    }

    /**
     * Проверяет корректность обработки команды редактирования книги с корректными данными и существующей книгой
     */
    @Test
    public void testHandleCorrectEditBookCommand() {
        Mockito.when(libraryServiceMock.editBook(
                        Long.parseLong(editCommand.getParams().get(0)),
                        editCommand.getParams().get(1),
                        editCommand.getParams().get(2),
                        Integer.parseInt(editCommand.getParams().get(3))))
                .thenReturn(book);

        commandHandler.executeCommand(editCommand);

        Mockito.verify(libraryServiceMock, Mockito.times(1)).editBook(
                Long.parseLong(editCommand.getParams().get(0)),
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
        Mockito.when(libraryServiceMock.editBook(
                        Long.parseLong(editCommand.getParams().get(0)),
                        editCommand.getParams().get(1),
                        editCommand.getParams().get(2),
                        Integer.parseInt(editCommand.getParams().get(3))))
                .thenReturn(null);

        commandHandler.executeCommand(editCommand);

        Mockito.verify(libraryServiceMock, Mockito.times(1)).editBook(
                Long.parseLong(editCommand.getParams().get(0)),
                editCommand.getParams().get(1),
                editCommand.getParams().get(2),
                Integer.parseInt(editCommand.getParams().get(3)));

        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .printFormatted("Книга с ID %s не найдена.", editCommand.getParams().get(0));
    }

    /**
     * Проверяет корректность обработки команды редактирования книги с некорректными данными
     */
    @Test
    public void testHandleIncorrectEditBookCommand() throws ArgumentsCountException, InvalidYearException, InvalidIdException {
        Mockito.doThrow(new ArgumentsCountException(4, 2))
                .when(commandValidatorMock).validateCommand(editCommand);

        commandHandler.executeCommand(editCommand);

        Mockito.verifyNoInteractions(libraryServiceMock);

        Mockito.verify(ioHandlerMock, Mockito.times(1))
                .print("Неверное количество аргументов команды: должно быть 4, представлено 2.");
    }
}
