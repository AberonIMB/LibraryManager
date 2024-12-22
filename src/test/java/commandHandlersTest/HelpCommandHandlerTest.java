package commandHandlersTest;

import org.example.Command;
import org.example.commandHandlers.HelpCommandHandler;
import org.example.util.IOHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Тесты для обработчика команды получения справки
 */
@ExtendWith(MockitoExtension.class)
public class HelpCommandHandlerTest {

    private final IOHandler ioHandlerMock;
    private final HelpCommandHandler helpCommandHandler;

    private final Command command = new Command("help");

    /**
     * Конструктор, в котором происходит инициализация полей
     */
    public HelpCommandHandlerTest(@Mock IOHandler ioHandler) {
        this.ioHandlerMock = ioHandler;

        this.helpCommandHandler = new HelpCommandHandler(ioHandlerMock);
    }

    /**
     * Проверяет корректность обработки команды получения справки с корректными данными
     */
    @Test
    public void testHandleCorrectHelpCommand() {
        helpCommandHandler.executeCommand(command);

        Mockito.verify(ioHandlerMock).print("""
                Доступные команды:
                \t- add-book "<название>" "<автор>" <год издания> – Добавить книгу
                \t- list-books – Просмотреть список книг
                \t- edit-book <ID книги> “<название>” “<автор>” <год издания> - Изменить книгу
                \t- delete-book <ID книги> – Удалить книгу
                \t- add-reader "<ФИО читателя>” – Добавить читателя
                \t- edit-reader <ID читателя> “<ФИО читателя>” – Изменить читателя
                \t- show-reader <ID читателя> – Просмотреть читателя
                \t- list-readers – Просмотреть список читателей
                \t- delete-reader <ID читателя> – Удалить читателя
                \t- checkout-book <ID книги> <ID читателя> – Выдать книгу
                \t- return-book <ID книги> - Вернуть книгу
                \t- help – Справка
                \t- stop - Завершить работу""");
    }

    /**
     * Проверяет корректность обработки команды получения справки с некорректными данными
     */
    @Test
    public void testHandleHelpCommandWithIncorrectArgsCount() {
        Command incorrectCommand = new Command("help 1 2");

        helpCommandHandler.executeCommand(incorrectCommand);

        Mockito.verify(ioHandlerMock)
                .print("Неверное количество аргументов команды: должно быть 0, представлено 2.");

        Mockito.verify(ioHandlerMock, Mockito.never()).print("""
                Доступные команды:
                \t- add-book "<название>" "<автор>" <год издания> – Добавить книгу
                \t- list-books – Просмотреть список книг
                \t- edit-book <ID книги> “<название>” “<автор>” <год издания> - Изменить книгу
                \t- delete-book <ID книги> – Удалить книгу
                \t- add-reader "<ФИО читателя>” – Добавить читателя
                \t- edit-reader <ID читателя> “<ФИО читателя>” – Изменить читателя
                \t- show-reader <ID читателя> – Просмотреть читателя
                \t- list-readers – Просмотреть список читателей
                \t- delete-reader <ID читателя> – Удалить читателя
                \t- checkout-book <ID книги> <ID читателя> – Выдать книгу
                \t- return-book <ID книги> - Вернуть книгу
                \t- help – Справка
                \t- stop - Завершить работу""");
    }
}